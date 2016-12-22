package io.edanni.money.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.math.BigDecimal;
import java.util.Calendar;

/**
 * Created by eduardo on 05/12/16.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({
    @JsonSubTypes.Type(value = Credit.class, name = "Credit"),

    @JsonSubTypes.Type(value = Debit.class, name = "Debit") }
)
public class Statement extends NamedEntity
{
    public Calendar date;
    public BigDecimal value;
    public Account account;
    public Integer categoryId;
    public Integer accountId;
    public String type;
}
