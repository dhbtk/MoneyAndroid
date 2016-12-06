package io.edanni.money.domain.entity;

import java.math.BigDecimal;

/**
 * Created by eduardo on 05/12/16.
 */
public class RecurringCredit extends AbstractEntity
{
    String name;
    Integer months;
    BigDecimal value;
    Tag tag;
    Integer accountId;
}
