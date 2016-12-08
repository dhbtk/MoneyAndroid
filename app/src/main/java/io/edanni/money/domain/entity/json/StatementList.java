package io.edanni.money.domain.entity.json;

import io.edanni.money.domain.entity.Statement;


/**
 * Created by eduardo on 07/12/16.
 */
public class StatementList
{
    private Statement[] statements;

    public Statement[] getStatements()
    {
        return statements;
    }

    public void setStatements( Statement[] statements )
    {
        this.statements = statements;
    }
}
