package io.edanni.money.infrastructure.rest;

import java.util.List;

/**
 * Created by eduardo on 08/12/16.
 */
public class Page<T>
{
    public List<T> content;
    public int page;
    public int totalPages;
    public int totalCount;
}
