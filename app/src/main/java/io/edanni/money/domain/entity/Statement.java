package io.edanni.money.domain.entity;

import java.math.BigDecimal;
import java.util.Calendar;

/**
 * Created by eduardo on 05/12/16.
 */
public class Statement extends AbstractEntity
{
    String name;
    Calendar date;
    BigDecimal value;
    Account account;
    Tag tag;
    Integer accountId;
}
