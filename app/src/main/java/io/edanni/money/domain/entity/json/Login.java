package io.edanni.money.domain.entity.json;

/**
 * Created by eduardo on 07/12/16.
 */
public class Login
{
    public String email;
    public String password;

    public Login()
    {

    }

    public Login( String email, String Password )
    {
        this.email = email;
        this.password = password;
    }
}
