package org.diorite.inventory.item.builder;

import java.util.List;

import org.diorite.inventory.item.meta.BookMeta;
import org.diorite.inventory.item.meta.BookMeta.GenerationEnum;

/**
 * Interface of builder of book item meta data.
 */
public interface IBookMetaBuilder<B extends IBookMetaBuilder<B, M>, M extends BookMeta> extends IMetaBuilder<B, M>
{
    /**
     * Sets the title of the book.<br>
     * Title should be max 16 characters long. <br>
     * NOTE: Diorite will not validate amount of characters.
     *
     * @param title the title to set
     *
     * @return builder for method chains.
     */
    default B setTitle(final String title)
    {
        this.meta().setTitle(title);
        return this.getBuilder();
    }

    /**
     * Sets the title of the book.<br>
     * Title should be max 16 characters long. <br>
     * NOTE: Diorite will not validate amount of characters.
     *
     * @param src source item meta to copy data from it.
     *
     * @return builder for method chains.
     */
    default B setTitle(final BookMeta src)
    {
        this.meta().setTitle(src.getTitle());
        return this.getBuilder();
    }

    /**
     * Removes title from book.
     *
     * @return builder for method chains.
     */
    default B removeTitle()
    {
        this.meta().removeTitle();
        return this.getBuilder();
    }

    /**
     * Sets the author of the book.
     *
     * @param author the author of the book
     *
     * @return builder for method chains.
     */
    default B setAuthor(final String author)
    {
        this.meta().setAuthor(author);
        return this.getBuilder();
    }

    /**
     * Sets the author of the book.
     *
     * @param src source item meta to copy data from it.
     *
     * @return builder for method chains.
     */
    default B setAuthor(final BookMeta src)
    {
        this.meta().setAuthor(src.getAuthor());
        return this.getBuilder();
    }

    /**
     * Removes author from book.
     *
     * @return builder for method chains.
     */
    default B removeAuthor()
    {
        this.meta().removeAuthor();
        return this.getBuilder();
    }

    /**
     * Sets the specified page in the book. Pages of the book must be
     * contiguous.
     * <br>
     * The data should be up to 256 characters in length.<br>
     * NOTE: Diorite will not validate amount of pages or characters.
     *
     * @param page the page number to set
     * @param data the data to set for that page
     *
     * @return builder for method chains.
     */
    default B setPage(final int page, final String data)
    {
        this.meta().setPage(page, data);
        return this.getBuilder();
    }

    /**
     * Clears the existing book pages, and sets the book to use the provided
     * pages. Maximum 50 pages with 256 characters per page.<br>
     * NOTE: Diorite will not validate amount of pages or characters.
     *
     * @param pages A list of pages to set the book to use
     *
     * @return builder for method chains.
     */
    default B setPages(final List<String> pages)
    {
        this.meta().setPages(pages);
        return this.getBuilder();
    }

    /**
     * Clears the existing book pages, and sets the book to use the provided
     * pages. Maximum 50 pages with 256 characters per page.<br>
     * NOTE: Diorite will not validate amount of pages or characters.
     *
     * @param src source item meta to copy data from it.
     *
     * @return builder for method chains.
     */
    default B setPages(final BookMeta src)
    {
        final List<String> strs = src.getPages();
        if (strs == null)
        {
            return this.getBuilder();
        }
        this.meta().setPages(strs);
        return this.getBuilder();
    }

    /**
     * Clears the existing book pages, and sets the book to use the provided
     * pages. Maximum 50 pages with 256 characters per page.<br>
     * NOTE: Diorite will not validate amount of pages or characters.
     *
     * @param pages A list of strings, each being a page
     *
     * @return builder for method chains.
     */
    default B setPages(final String... pages)
    {
        this.meta().setPages(pages);
        return this.getBuilder();
    }

    /**
     * Adds new pages to the end of the book. Up to a maximum of 50 pages with
     * 256 characters per page. <br>
     * NOTE: Diorite will not validate amount of pages or characters.
     *
     * @param pages A list of strings, each being a page
     *
     * @return builder for method chains.
     */
    default B addPages(final String... pages)
    {
        this.meta().addPages(pages);
        return this.getBuilder();
    }

    /**
     * Adds new pages to the end of the book. Up to a maximum of 50 pages with
     * 256 characters per page. <br>
     * NOTE: Diorite will not validate amount of pages or characters.
     *
     * @param src source item meta to copy data from it.
     *
     * @return builder for method chains.
     */
    default B addPages(final BookMeta src)
    {
        final List<String> strs = src.getPages();
        if ((strs == null) || strs.isEmpty())
        {
            return this.getBuilder();
        }
        this.meta().addPages(strs.toArray(new String[strs.size()]));
        return this.getBuilder();
    }


    /**
     * Set if book was resolved. <br>
     * Optional. Created and set to 1 when the book (or a book from the stack) is opened for the first time after signing. <br>
     * Used to determine whether to parse target selectors within JSON, because their selections become fixed at that point.
     *
     * @param resolved if book was resolved.
     *
     * @return builder for method chains.
     */
    default B resolved(final boolean resolved)
    {
        this.meta().setResolved(resolved);
        return this.getBuilder();
    }

    /**
     * Set if book was resolved. <br>
     * Optional. Created and set to 1 when the book (or a book from the stack) is opened for the first time after signing. <br>
     * Used to determine whether to parse target selectors within JSON, because their selections become fixed at that point.
     *
     * @param src source item meta to copy data from it.
     *
     * @return builder for method chains.
     */
    default B resolved(final BookMeta src)
    {
        this.meta().setResolved(src.isResolved());
        return this.getBuilder();
    }

    /**
     * Set generation of book. <br>
     * 0 = original, 1 = copy of original, 2 = copy of copy, 3 = tattered
     *
     * @param generation new generation of book.
     *
     * @return builder for method chains.
     */
    default B generation(final int generation)
    {
        this.meta().setGeneration(generation);
        return this.getBuilder();
    }

    /**
     * Set generation of book. <br>
     * 0 = original, 1 = copy of original, 2 = copy of copy, 3 = tattered
     *
     * @param src source item meta to copy data from it.
     *
     * @return builder for method chains.
     */
    default B generation(final BookMeta src)
    {
        this.meta().setGeneration(src.getGeneration());
        return this.getBuilder();
    }

    /**
     * Set generation of book using enum constant.
     *
     * @param generationState new generation of book.
     *
     * @return builder for method chains.
     */
    default B generation(final GenerationEnum generationState)
    {
        this.meta().setGenerationState(generationState);
        return this.getBuilder();
    }
}
