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

package org.diorite.impl.inventory.item.meta;

import java.util.List;
import java.util.stream.Collectors;

import org.diorite.inventory.item.ItemStack;
import org.diorite.inventory.item.meta.BookMeta;
import org.diorite.nbt.NbtTagCompound;
import org.diorite.nbt.NbtTagList;
import org.diorite.nbt.NbtTagString;

public class BookMetaImpl extends SimpleItemMetaImpl implements BookMeta
{
    protected static final String RESOLVED   = "resolved";
    protected static final String GENERATION = "generation";
    protected static final String AUTHOR     = "author";
    protected static final String TITLE      = "title";
    protected static final String PAGES      = "pages";

    public BookMetaImpl(final NbtTagCompound tag)
    {
        super(tag);
    }

    public BookMetaImpl(final NbtTagCompound tag, final ItemStack itemStack)
    {
        super(tag, itemStack);
    }

    @Override
    public boolean hasTitle()
    {
        return (this.tag != null) && this.tag.containsTag(TITLE);
    }

    @Override
    public String getTitle()
    {
        return (this.tag == null) ? null : this.tag.getString(TITLE);
    }

    @Override
    public void setTitle(final String title)
    {
        this.setStringTag(TITLE, title);
    }

    @Override
    public boolean hasAuthor()
    {
        return (this.tag != null) && this.tag.containsTag(AUTHOR);
    }

    @Override
    public String getAuthor()
    {
        return (this.tag == null) ? null : this.tag.getString(AUTHOR);
    }

    @Override
    public void setAuthor(final String author)
    {
        this.setStringTag(AUTHOR, author);
    }

    @Override
    public boolean hasPages()
    {
        return (this.tag != null) && this.tag.containsTag(PAGES);
    }

    @Override
    public String getPage(final int page)
    {
        if (this.tag == null)
        {
            throw new IndexOutOfBoundsException("There is no pages in this book.");
        }
        final NbtTagList list = this.tag.getTag(PAGES, NbtTagList.class);
        if (list == null)
        {
            throw new IndexOutOfBoundsException("There is no pages in this book.");
        }
        if ((page < 0) || (page >= list.size()))
        {
            throw new IndexOutOfBoundsException("Invalid page index, page must be < 0 and >= size of book");
        }
        return ((NbtTagString) list.get(page)).getValue();
    }

    @Override
    public void setPage(final int page, final String data)
    {
        if (this.tag == null)
        {
            throw new IndexOutOfBoundsException("There is no pages in this book.");
        }
        final NbtTagList list = this.tag.getTag(PAGES, NbtTagList.class);
        if (list == null)
        {
            throw new IndexOutOfBoundsException("There is no pages in this book.");
        }
        if ((page < 0) || (page >= list.size()))
        {
            throw new IndexOutOfBoundsException("Invalid page index, page must be < 0 and >= size of book");
        }
        list.set(page, new NbtTagString(data));
        this.setDirty();
    }

    @Override
    public List<String> getPages()
    {
        if (this.tag == null)
        {
            return null;
        }
        final NbtTagList list = this.tag.getTag(PAGES, NbtTagList.class);
        if (list == null)
        {
            return null;
        }
        return list.getTags(NbtTagString.class).stream().map(NbtTagString::getValue).collect(Collectors.toList());
    }

    @Override
    public void setPages(final List<String> pages)
    {
        if (this.removeIfNeeded(PAGES, pages))
        {
            return;
        }
        this.checkTag(true);
        this.tag.removeTag(PAGES);
        this.tag.setList(PAGES, pages.stream().map(s -> new NbtTagString(null, s)).collect(Collectors.toList()));
        this.setDirty();
    }

    @Override
    public void setPages(final String... pages)
    {
        if (this.removeIfNeeded(PAGES, pages))
        {
            return;
        }
        this.checkTag(true);
        this.tag.removeTag(PAGES);
        final NbtTagList list = new NbtTagList(PAGES, pages.length);
        for (final String page : pages)
        {
            list.add(new NbtTagString(null, page));
        }
        this.tag.addTag(list);
        this.setDirty();
    }

    @Override
    public void addPages(final String... pages)
    {
        this.checkTag(true);
        NbtTagList list = this.tag.getTag(PAGES, NbtTagList.class);
        if (list == null)
        {
            list = new NbtTagList(PAGES, pages.length);
            this.tag.addTag(list);
        }
        for (final String page : pages)
        {
            list.add(new NbtTagString(null, page));
        }
        this.setDirty();
    }

    @Override
    public int getPageCount()
    {
        if (this.tag == null)
        {
            return 0;
        }
        final NbtTagList list = this.tag.getTag(PAGES, NbtTagList.class);
        if (list == null)
        {
            return 0;
        }
        return list.size();
    }

    @Override
    public boolean isResolved()
    {
        return (this.tag != null) && this.tag.getBoolean(RESOLVED);
    }

    @Override
    public void setResolved(final boolean resolved)
    {
        if (this.removeIfNeeded(RESOLVED, ! resolved))
        {
            return;
        }
        this.checkTag(true);
        this.tag.setBoolean(RESOLVED, resolved);
        this.setDirty();
    }

    @Override
    public int getGeneration()
    {
        if (this.tag == null)
        {
            return 0;
        }
        return this.tag.getInt(GENERATION);
    }

    @Override
    public void setGeneration(final int generation)
    {
        if (this.removeIfNeeded(GENERATION, generation == 0))
        {
            return;
        }
        this.tag.setInt(GENERATION, generation);
    }

    @SuppressWarnings("CloneDoesntCallSuperClone")
    @Override
    public BookMetaImpl clone()
    {
        return new BookMetaImpl((this.tag == null) ? null : this.tag.clone(), this.itemStack);
    }
}
