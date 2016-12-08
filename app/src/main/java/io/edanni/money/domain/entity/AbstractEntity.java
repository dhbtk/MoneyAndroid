package io.edanni.money.domain.entity;

import java.io.Serializable;
import java.util.Calendar;

/**
 * Created by eduardo on 23/11/16.
 */
public abstract class AbstractEntity implements Serializable
{
    private Integer id;
    private Calendar createdAt;
    private Calendar updatedAt;

    public Integer getId()
    {
        return id;
    }

    public void setId( Integer id )
    {
        this.id = id;
    }

    public Calendar getCreatedAt()
    {
        return createdAt;
    }

    public void setCreatedAt( Calendar createdAt )
    {
        this.createdAt = createdAt;
    }

    public Calendar getUpdatedAt()
    {
        return updatedAt;
    }

    public void setUpdatedAt( Calendar updatedAt )
    {
        this.updatedAt = updatedAt;
    }
}
