package io.edanni.money.infrastructure.rest;

import java.util.List;

/**
 * Created by eduardo on 08/12/16.
 */
public class Page<T>
{
    private List<T> content;
    private int page;
    private int totalPages;
    private int totalCount;

    public List<T> getContent()
    {
        return content;
    }

    public void setContent( List<T> content )
    {
        this.content = content;
    }

    public int getPage()
    {
        return page;
    }

    public void setPage( int page )
    {
        this.page = page;
    }

    public int getTotalPages()
    {
        return totalPages;
    }

    public void setTotalPages( int totalPages )
    {
        this.totalPages = totalPages;
    }

    public int getTotalCount()
    {
        return totalCount;
    }

    public void setTotalCount( int totalCount )
    {
        this.totalCount = totalCount;
    }
}
