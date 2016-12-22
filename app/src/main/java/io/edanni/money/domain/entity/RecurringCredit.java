package io.edanni.money.domain.entity;

import java.math.BigDecimal;

/**
 * Created by eduardo on 05/12/16.
 */
public class RecurringCredit extends AbstractEntity
{
    public String name;
    public Integer months;
    public BigDecimal value;
    public Category category;
    public Integer accountId;
}
