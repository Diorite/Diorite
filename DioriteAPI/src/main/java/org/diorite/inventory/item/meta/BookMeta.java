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

package org.diorite.inventory.item.meta;

import java.util.List;

/**
 * Represent book meta data, that can have author, title and content.
 */
public interface BookMeta extends ItemMeta
{
    /**
     * Checks for the existence of a title in the book.
     *
     * @return true if the book has a title
     */
    boolean hasTitle();

    /**
     * Gets the title of the book.
     *
     * @return the title of the book
     */
    String getTitle();

    /**
     * Sets the title of the book.<br>
     * Title should be max 16 characters long. <br>
     * NOTE: Diorite will not validate amount of characters.
     *
     * @param title the title to set
     */
    void setTitle(String title);

    /**
     * Removes title from book.
     */
    default void removeTitle()
    {
        this.setTitle(null);
    }

    /**
     * Checks for the existence of an author in the book.
     *
     * @return the author of the book
     */
    boolean hasAuthor();

    /**
     * Gets the author of the book.
     *
     * @return the author of the book
     */
    String getAuthor();

    /**
     * Sets the author of the book.
     *
     * @param author the author of the book
     */
    void setAuthor(String author);

    /**
     * Removes author from book.
     */
    default void removeAuthor()
    {
        this.setAuthor(null);
    }

    /**
     * Checks for the existence of pages in the book.
     *
     * @return true if the book has pages
     */
    boolean hasPages();

    /**
     * Gets the specified page in the book. The given page must exist.
     *
     * @param page the page number to get
     *
     * @return the page from the book
     */
    String getPage(int page);

    /**
     * Sets the specified page in the book. Pages of the book must be
     * contiguous.
     * <br>
     * The data should be up to 256 characters in length.<br>
     * NOTE: Diorite will not validate amount of pages or characters.
     *
     * @param page the page number to set
     * @param data the data to set for that page
     */
    void setPage(int page, String data);

    /**
     * Gets all the pages in the book.
     *
     * @return list of all the pages in the book
     */
    List<String> getPages();

    /**
     * Clears the existing book pages, and sets the book to use the provided
     * pages. Maximum 50 pages with 256 characters per page.<br>
     * NOTE: Diorite will not validate amount of pages or characters.
     *
     * @param pages A list of pages to set the book to use
     */
    void setPages(List<String> pages);

    /**
     * Clears the existing book pages, and sets the book to use the provided
     * pages. Maximum 50 pages with 256 characters per page.<br>
     * NOTE: Diorite will not validate amount of pages or characters.
     *
     * @param pages A list of strings, each being a page
     */
    void setPages(String... pages);

    /**
     * Adds new pages to the end of the book. Up to a maximum of 50 pages with
     * 256 characters per page. <br>
     * NOTE: Diorite will not validate amount of pages or characters.
     *
     * @param pages A list of strings, each being a page
     */
    void addPages(String... pages);

    /**
     * Gets the number of pages in the book.
     *
     * @return the number of pages in the book
     */
    int getPageCount();

    /**
     * Returns if book was resolved. <br>
     * Optional. Created and set to 1 when the book (or a book from the stack) is opened for the first time after signing. <br>
     * Used to determine whether to parse target selectors within JSON, because their selections become fixed at that point.
     *
     * @return if book was resolved.
     */
    boolean isResolved();

    /**
     * Set if book was resolved. <br>
     * Optional. Created and set to 1 when the book (or a book from the stack) is opened for the first time after signing. <br>
     * Used to determine whether to parse target selectors within JSON, because their selections become fixed at that point.
     *
     * @param resolved if book was resolved.
     */
    void setResolved(boolean resolved);

    /**
     * Returns generation of book. <br>
     * 0 = original, 1 = copy of original, 2 = copy of copy, 3 = tattered
     *
     * @return generation of book.
     */
    int getGeneration();

    /**
     * Set generation of book. <br>
     * 0 = original, 1 = copy of original, 2 = copy of copy, 3 = tattered
     *
     * @param generation new generation of book.
     */
    void setGeneration(int generation);

    /**
     * Returns generation of book as enum constant.
     *
     * @return generation of book.
     */
    default GenerationEnum getGenerationState()
    {
        final int i = this.getGeneration();
        if (i <= 0)
        {
            return GenerationEnum.ORIGINAL;
        }
        if (i >= 3)
        {
            return GenerationEnum.TATTERED;
        }
        if (i == 1)
        {
            return GenerationEnum.COPY_OF_ORIGINAL;
        }
        return GenerationEnum.COPY_OF_COPY;
    }

    /**
     * Set generation of book using enum constant.
     *
     * @param generationState new generation of book.
     */
    default void setGenerationState(final GenerationEnum generationState)
    {
        this.setGeneration(generationState.generation);
    }

    @Override
    BookMeta clone();

    /**
     * Generation enum.
     */
    enum GenerationEnum
    {
        /**
         * Represent original book.
         */
        ORIGINAL(0),
        /**
         * Represent copy of original book.
         */
        COPY_OF_ORIGINAL(1),
        /**
         * Represent copy of copy book.
         */
        COPY_OF_COPY(2),
        /**
         * Represent copy of copy of copy book.
         */
        TATTERED(3);
        private final byte generation;

        GenerationEnum(final int generation)
        {
            this.generation = (byte) generation;
        }

        /**
         * Returns int representation of generation. <br>
         * 0 = original, 1 = copy of original, 2 = copy of copy, 3 = tattered
         *
         * @return int representation of generation.
         */
        public byte getGeneration()
        {
            return this.generation;
        }
    }
}
