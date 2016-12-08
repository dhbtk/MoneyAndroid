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
public class Statement extends AbstractEntity
{
    private String name;
    private Calendar date;
    private BigDecimal value;
    private Account account;
    private Integer tagId;
    private Integer accountId;
    private String type;

    public Statement() {}

    public String getName()
    {
        return name;
    }

    public void setName( String name )
    {
        this.name = name;
    }

    public Calendar getDate()
    {
        return date;
    }

    public void setDate( Calendar date )
    {
        this.date = date;
    }

    public BigDecimal getValue()
    {
        return value;
    }

    public void setValue( BigDecimal value )
    {
        this.value = value;
    }

    public Account getAccount()
    {
        return account;
    }

    public void setAccount( Account account )
    {
        this.account = account;
    }

    public Integer getTagId()
    {
        return tagId;
    }

    public void setTagId( Integer tagId )
    {
        this.tagId = tagId;
    }

    public Integer getAccountId()
    {
        return accountId;
    }

    public void setAccountId( Integer accountId )
    {
        this.accountId = accountId;
    }

    public String getType()
    {
        return type;
    }

    public void setType( String type )
    {
        this.type = type;
    }
}
