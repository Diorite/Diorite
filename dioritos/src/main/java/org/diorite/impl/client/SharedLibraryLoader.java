/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2015 Diorite (by Bartłomiej Mazur (aka GotoFinal))
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package org.diorite.impl.client;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.HashSet;
import java.util.UUID;
import java.util.zip.CRC32;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * Loads shared libraries from JAR files. Call {@link SharedLibraryLoader#load()} to load the
 * required LWJGL 3 native shared libraries.
 *
 * @author mzechner
 * @author Nathan Sweet
 */
@SuppressWarnings({"MagicNumber", "ClassHasNoToStringMethod"})
public class SharedLibraryLoader
{
    public static boolean isWindows = System.getProperty("os.name").contains("Windows");
    public static boolean isLinux   = System.getProperty("os.name").contains("Linux");
    public static boolean isMac     = System.getProperty("os.name").contains("Mac");
    public static boolean isIos     = false;
    public static boolean isAndroid = false;
    public static boolean isARM     = System.getProperty("os.arch").startsWith("arm");
    public static boolean is64Bit   = System.getProperty("os.arch").equals("amd64") || System.getProperty("os.arch").equals("x86_64");

    // JDK 8 only.
    public static final String abi = ((System.getProperty("sun.arch.abi") != null) ? System.getProperty("sun.arch.abi") : "");

    static
    {
        final String vm = System.getProperty("java.runtime.name");
        if ((vm != null) && vm.contains("Android Runtime"))
        {
            isAndroid = true;
            isWindows = false;
            isLinux = false;
            isMac = false;
            is64Bit = false;
        }
        if (! isAndroid && ! isWindows && ! isLinux && ! isMac)
        {
            isIos = true;
            is64Bit = false;
        }
    }

    static boolean load = true;

    static
    {
        // Don't extract natives if using JWS.
        try
        {
            final Method method = Class.forName("javax.jnlp.ServiceManager").getDeclaredMethod("lookup", String.class);
            method.invoke(null, "javax.jnlp.PersistenceService");
            load = false;
        } catch (final Throwable ex)
        {
            load = true;
        }
    }

    /**
     * Extracts the LWJGL native libraries from the classpath and sets the "org.lwjgl.librarypath" system property.
     */
    public static synchronized void load()
    {
        load(false);
    }

    /**
     * Extracts the LWJGL native libraries from the classpath and sets the "org.lwjgl.librarypath" system property.
     *
     * @param disableOpenAL if true, openAL will be disabled.
     */
    public static synchronized void load(final boolean disableOpenAL)
    {
        if (! load)
        {
            return;
        }

        final SharedLibraryLoader loader = new SharedLibraryLoader();
        File nativesDir = null;
        try
        {
            if (SharedLibraryLoader.isWindows)
            {
                nativesDir = loader.extractFile(SharedLibraryLoader.is64Bit ? "lwjgl.dll" : "lwjgl32.dll", null).getParentFile();
                if (! disableOpenAL)
                {
                    loader.extractFile(SharedLibraryLoader.is64Bit ? "OpenAL.dll" : "OpenAL32.dll", nativesDir.getName());
                }
            }
            else if (SharedLibraryLoader.isMac)
            {
                nativesDir = loader.extractFile("liblwjgl.dylib", null).getParentFile();
                if (! disableOpenAL)
                {
                    loader.extractFile("libopenal.dylib", nativesDir.getName());
                }
            }
            else if (SharedLibraryLoader.isLinux)
            {
                nativesDir = loader.extractFile(SharedLibraryLoader.is64Bit ? "liblwjgl.so" : "liblwjgl32.so", null).getParentFile();
                if (! disableOpenAL)
                {
                    loader.extractFile(SharedLibraryLoader.is64Bit ? "libopenal.so" : "libopenal32.so", nativesDir.getName());
                }
            }
        } catch (final Throwable ex)
        {
            throw new RuntimeException("Unable to extract LWJGL natives.", ex);
        }
        System.setProperty("org.lwjgl.librarypath", (nativesDir != null) ? nativesDir.getAbsolutePath() : "");
        load = false;
    }

    private static final Collection<String> loadedLibraries = new HashSet<>(10);

    private String nativesJar;

//    public SharedLibraryLoader()
//    {
//    }
//
//    /**
//     * Fetches the natives from the given natives jar file. Used for testing a shared lib on the fly.
//     *
//     * @param nativesJar
//     */
//    public SharedLibraryLoader(final String nativesJar)
//    {
//        this.nativesJar = nativesJar;
//    }

    /**
     * Returns a CRC of the remaining bytes in the stream.
     *
     * @param input input stream to get crc.
     *
     * @return CRC of the remaining bytes in the stream.
     */
    public String crc(final InputStream input)
    {
        if (input == null)
        {
            throw new IllegalArgumentException("input cannot be null.");
        }
        final CRC32 crc = new CRC32();
        final byte[] buffer = new byte[4096];
        try
        {
            while (true)
            {
                final int length = input.read(buffer);
                if (length == - 1)
                {
                    break;
                }
                crc.update(buffer, 0, length);
            }
        } catch (final Exception ex)
        {
            try
            {
                input.close();
            } catch (final IOException ignored)
            {
            }
        }
        return Long.toString(crc.getValue(), 16);
    }

    /**
     * Maps a platform independent library name to a platform dependent name.
     *
     * @param libraryName name of library to map.
     *
     * @return name of library, this same as given.
     */
    public String mapLibraryName(final String libraryName)
    {
        if (isWindows)
        {
            return libraryName + (is64Bit ? "64.dll" : ".dll");
        }
        if (isLinux)
        {
            return "lib" + libraryName + (isARM ? "arm" + abi : "") + (is64Bit ? "64.so" : ".so");
        }
        if (isMac)
        {
            return "lib" + libraryName + (is64Bit ? "64.dylib" : ".dylib");
        }
        return libraryName;
    }

    /**
     * Loads a shared library for the platform the application is running on.
     *
     * @param libraryName The platform independent library name. If not contain a prefix (eg lib) or suffix (eg .dll).
     */
    public synchronized void load(String libraryName)
    {
        // in case of iOS, things have been linked statically to the executable, bail out.
        if (isIos)
        {
            return;
        }

        libraryName = this.mapLibraryName(libraryName);
        if (loadedLibraries.contains(libraryName))
        {
            return;
        }

        try
        {
            if (isAndroid)
            {
                System.loadLibrary(libraryName);
            }
            else
            {
                this.loadFile(libraryName);
            }
        } catch (final Throwable ex)
        {
            throw new RuntimeException("Couldn't load shared library '" + libraryName + "' for target: " + System.getProperty("os.name") + (is64Bit ? ", 64-bit" : ", 32-bit"), ex);
        }
        loadedLibraries.add(libraryName);
    }

    private InputStream readFile(final String path)
    {
        if (this.nativesJar == null)
        {
            final InputStream input = SharedLibraryLoader.class.getResourceAsStream("/" + path);
            if (input == null)
            {
                throw new RuntimeException("Unable to read file for extraction: " + path);
            }
            return input;
        }

        try (ZipFile file = new ZipFile(this.nativesJar))
        {
            final ZipEntry entry = file.getEntry(path);
            if (entry == null)
            {
                throw new RuntimeException("Couldn't find '" + path + "' in JAR: " + this.nativesJar);
            }
            return file.getInputStream(entry);
        } catch (final IOException ex)
        {
            throw new RuntimeException("Error reading '" + path + "' in JAR: " + this.nativesJar, ex);
        }
    }

    /**
     * Extracts the specified file into the temp directory if it does not already exist or the CRC does not match. If file
     * extraction fails and the file exists at java.library.path, that file is returned.
     *
     * @param sourcePath The file to extract from the classpath or JAR.
     * @param dirName    The name of the subdirectory where the file will be extracted. If null, the file's CRC will be used.
     *
     * @return The extracted file.
     */
    public File extractFile(final String sourcePath, String dirName)
    {
        try
        {
            final String sourceCrc = this.crc(this.readFile(sourcePath));
            if (dirName == null)
            {
                dirName = sourceCrc;
            }

            final File extractedFile = this.getExtractedFile(dirName, new File(sourcePath).getName());
            return this.extractFile(sourcePath, sourceCrc, extractedFile);
        } catch (final RuntimeException ex)
        {
            // Fallback to file at java.library.path location, eg for applets.
            final File file = new File(System.getProperty("java.library.path"), sourcePath);
            if (file.exists())
            {
                return file;
            }
            throw ex;
        }
    }

    /**
     * Returns a path to a file that can be written. Tries multiple locations and verifies writing succeeds.
     *
     * @param dirName  The name of the subdirectory where the file will be extracted. If null, the file's CRC will be used.
     * @param fileName file in that directory.
     *
     * @return The extracted file.
     *
     * @throws IOException if any file operation will throw it.
     */
    private File getExtractedFile(final String dirName, final String fileName)
    {
        // Temp directory with username in path.
        final File idealFile = new File(System.getProperty("java.io.tmpdir") + "/libgdx" + System.getProperty("user.name") + "/" + dirName, fileName);
        if (this.canWrite(idealFile))
        {
            return idealFile;
        }

        // System provided temp directory.
        try
        {
            File file = File.createTempFile(dirName, null);
            if (file.delete())
            {
                file = new File(file, fileName);
                if (this.canWrite(file))
                {
                    return file;
                }
            }
        } catch (final IOException ignored)
        {
        }

        // User home.
        File file = new File(System.getProperty("user.home") + "/.libgdx/" + dirName, fileName);
        if (this.canWrite(file))
        {
            return file;
        }

        // Relative directory.
        file = new File(".temp/" + dirName, fileName);
        if (this.canWrite(file))
        {
            return file;
        }

        return idealFile; // Will likely fail, but we did our best.
    }

    /**
     * Returns true if the parent directories of the file can be created and the file can be written.
     */
    private boolean canWrite(final File file)
    {
        final File parent = file.getParentFile();
        final File testFile;
        if (file.exists())
        {
            if (! file.canWrite() || ! this.canExecute(file))
            {
                return false;
            }
            // Don't overwrite existing file just to check if we can write to directory.
            testFile = new File(parent, UUID.randomUUID().toString());
        }
        else
        {
            parent.mkdirs();
            if (! parent.isDirectory())
            {
                return false;
            }
            testFile = file;
        }
        try
        {
            try (final FileOutputStream s = new FileOutputStream(testFile))
            {
                s.close();
            }
            return this.canExecute(testFile);
        } catch (final Throwable ex)
        {
            return false;
        } finally
        {
            testFile.delete();
        }
    }

    private boolean canExecute(final File file)
    {
        try
        {
            final Method canExecute = File.class.getMethod("canExecute");
            if ((Boolean) canExecute.invoke(file))
            {
                return true;
            }

            final Method setExecutable = File.class.getMethod("setExecutable", boolean.class, boolean.class);
            setExecutable.invoke(file, true, false);

            return (Boolean) canExecute.invoke(file);
        } catch (final Exception ignored)
        {
        }
        return false;
    }

    private File extractFile(final String sourcePath, final String sourceCrc, final File extractedFile)
    {
        String extractedCrc = null;
        if (extractedFile.exists())
        {
            try
            {
                extractedCrc = this.crc(new FileInputStream(extractedFile));
            } catch (final FileNotFoundException ignored)
            {
            }
        }

        // If file doesn't exist or the CRC doesn't match, extract it to the temp dir.
        if ((extractedCrc == null) || ! extractedCrc.equals(sourceCrc))
        {
            try
            {
                extractedFile.getParentFile().mkdirs();
                try (final InputStream input = this.readFile(sourcePath); final FileOutputStream output = new FileOutputStream(extractedFile))
                {
                    final byte[] buffer = new byte[4096];
                    while (true)
                    {
                        final int length = input.read(buffer);
                        if (length == - 1)
                        {
                            break;
                        }
                        output.write(buffer, 0, length);
                    }
                }
            } catch (final IOException ex)
            {
                throw new RuntimeException("Error extracting file: " + sourcePath + "\nTo: " + extractedFile.getAbsolutePath(), ex);
            }
        }

        return extractedFile;
    }

    /**
     * Extracts the source file and calls System.load. Attemps to extract and load from multiple locations. Throws runtime
     * exception if all fail.
     */
    @SuppressWarnings("ThrowableResultOfMethodCallIgnored")
    private void loadFile(final String sourcePath)
    {
        final String sourceCrc = this.crc(this.readFile(sourcePath));

        final String fileName = new File(sourcePath).getName();

        // Temp directory with username in path.
        File file = new File(System.getProperty("java.io.tmpdir") + "/libgdx" + System.getProperty("user.name") + "/" + sourceCrc, fileName);
        final Throwable ex = this.loadFile(sourcePath, sourceCrc, file);
        if (ex == null)
        {
            return;
        }

        // System provided temp directory.
        try
        {
            file = File.createTempFile(sourceCrc, null);
            if (file.delete() && (this.loadFile(sourcePath, sourceCrc, file) == null))
            {
                return;
            }
        } catch (final Throwable ignored)
        {
        }

        // User home.
        file = new File(System.getProperty("user.home") + "/.libgdx/" + sourceCrc, fileName);
        if (this.loadFile(sourcePath, sourceCrc, file) == null)
        {
            return;
        }

        // Relative directory.
        file = new File(".temp/" + sourceCrc, fileName);
        if (this.loadFile(sourcePath, sourceCrc, file) == null)
        {
            return;
        }

        // Fallback to java.library.path location, eg for applets.
        file = new File(System.getProperty("java.library.path"), sourcePath);
        if (file.exists())
        {
            System.load(file.getAbsolutePath());
            return;
        }

        throw new RuntimeException(ex);
    }

    /**
     * @return null if the file was extracted and loaded.
     */
    private Throwable loadFile(final String sourcePath, final String sourceCrc, final File extractedFile)
    {
        try
        {
            System.load(this.extractFile(sourcePath, sourceCrc, extractedFile).getAbsolutePath());
            return null;
        } catch (final Throwable ex)
        {
            ex.printStackTrace();
            return ex;
        }
    }
}