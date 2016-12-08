package io.edanni.money.domain.entity;

import java.io.Serializable;
import java.util.Calendar;

/**
 * Created by eduardo on 23/11/16.
 */
public abstract class AbstractEntity implements Serializable
{
    public Integer id;
    public Calendar createdAt;
    public Calendar updatedAt;
}
