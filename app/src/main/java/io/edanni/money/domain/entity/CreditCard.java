package io.edanni.money.domain.entity;

import java.math.BigDecimal;

/**
 * Created by eduardo on 05/12/16.
 */
public class CreditCard extends Account
{
    Integer expiration;
    Integer closing;
    BigDecimal interest;
    BigDecimal fine;
}
