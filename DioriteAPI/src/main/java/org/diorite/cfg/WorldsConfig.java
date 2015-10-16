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

package org.diorite.cfg;

import java.io.File;
import java.util.List;
import java.util.Map;

import org.diorite.Difficulty;
import org.diorite.GameMode;
import org.diorite.world.Dimension;
import org.diorite.world.HardcoreSettings.HardcoreAction;
import org.diorite.world.WorldType;

/**
 * Class representing diorite worlds configuration, most of params are read-only and some of them may not be updated (no visible changes on server) if changed. (or saved)
 */
public interface WorldsConfig
{
    /**
     * Returns name/location of directory used to store worlds.
     *
     * @return name/location of directory used to store worlds.
     */
    File getWorldsDir();

    /**
     * Returns name of default world.
     *
     * @return name of default world.
     */
    String getDefaultWorld();

    /**
     * Returns list of world groups.<br>
     *
     * @return list of world groups.
     */
    List<? extends WorldGroupConfig> getGroups();

    /**
     * Class representing diorite worlds group configuration, most of params are read-only and some of them may not be updated (no visible changes on server) if changed. (or saved)<br>
     * Each group contains own player data like inventory, exp etc..
     */
    interface WorldGroupConfig
    {
        /**
         * Returns name of this world group.
         *
         * @return name of this world group.
         */
        String getName();

        /**
         * Returns list of worlds in this group.
         *
         * @return list of worlds in this group.
         */
        List<? extends WorldConfig> getWorlds();
    }

    /**
     * Class representing diorite world configuration, most of params are read-only and some of them may not be updated (no visible changes on server) if changed. (or saved)
     */
    interface WorldConfig
    {
        /**
         * Returns name of this world.
         *
         * @return name of this world.
         */
        String getName();

        /**
         * Returns if world is enabled.<br>
         * World can be disabled so it will be not loaded on server, but configuration of it will stay in memory and file.
         *
         * @return if world is enabled.
         */
        boolean isEnabled();

        /**
         * Returns if world can be used on vanilla servers. <br>
         * Diorite may use some stuff that will make world incompatible with vanilla clients/servers,
         * like mods or custom NBT tags to seepd up loading time.
         *
         * @return if world can be used on vanilla servers.
         */
        boolean isVanillaCompatible();

        /**
         * Returns default gamemode for this world.
         *
         * @return default gamemode for this world.
         */
        GameMode getGamemode();

        /**
         * Returns if gamemode should be force changed on entry.
         *
         * @return if gamemode should be force changed on entry.
         */
        boolean isForceGamemode();

        /**
         * Returns difficulty on this world.
         *
         * @return difficulty on this world.
         */
        Difficulty getDifficulty();

        /**
         * Returns if pvp is enabled on this server.
         *
         * @return if pvp is enabled on this server.
         */
        boolean isPvp();

        /**
         * Retruns seed used by this world.
         *
         * @return seed used by this world.
         */
        long getSeed();

        /**
         * Returns {@link Dimension} of this world.
         *
         * @return {@link Dimension} of this world.
         */
        Dimension getDimension();

        /**
         * Returns {@link WorldType} of this world.
         *
         * @return {@link WorldType} of this world.
         */
        WorldType getWorldType();

        /**
         * Returns generator name for this world.
         *
         * @return generator name for this world.
         */
        String getGenerator();

        /**
         * Returns additional generator settings. <br>
         * Setting may be unique for chosen generator.
         *
         * @return additional generator settings.
         */
        Map<String, Object> getGeneratorSettings();

        /**
         * Returns if world is in hardcore mode.
         *
         * @return if world is in hardcore mode.
         */
        boolean isHardcore();

        /**
         * Returns action that will be used when player die if this is hardcore world.
         *
         * @return action that will be used when player die if this is hardcore world.
         */
        HardcoreAction getHardcoreAction();

        /**
         * Returns amount of chunks (radius) from spawn to be always loaded.
         *
         * @return amount of chunks (radius) from spawn to be always loaded.
         */
        byte getForceLoadedRadius();

        /**
         * Returns x coordinate of spawn location.
         *
         * @return x coordinate of spawn location.
         */
        double getSpawnX();

        /**
         * Returns y coordinate of spawn location.
         *
         * @return y coordinate of spawn location.
         */
        double getSpawnY();

        /**
         * Returns z coordinate of spawn location.
         *
         * @return z coordinate of spawn location.
         */
        double getSpawnZ();

        /**
         * Returns yaw coordinate of spawn location.
         *
         * @return yaw coordinate of spawn location.
         */
        float getSpawnYaw();

        /**
         * Returns pitch coordinate of spawn location.
         *
         * @return pitch coordinate of spawn location.
         */
        float getSpawnPitch();
    }
}
