package io.edanni.money.ui.activity;

import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;
import android.widget.TextView;
import io.edanni.money.R;
import io.edanni.money.ui.fragment.StatementFragment_;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.OptionsMenu;
import org.androidannotations.annotations.ViewById;

@EActivity(R.layout.activity_main)
@OptionsMenu(R.menu.main)
public class MainActivity extends AppCompatActivity
    implements NavigationView.OnNavigationItemSelectedListener
{
    @ViewById(R.id.toolbar)
    Toolbar toolbar;
    @ViewById(R.id.drawer_layout)
    DrawerLayout drawer;
    @ViewById(R.id.nav_view)
    NavigationView navDrawer;
    @ViewById(R.id.drawer_email)
    TextView drawerEmail;

    @AfterViews
    void startup()
    {
        setSupportActionBar( this.toolbar );
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
            this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close );
        drawer.addDrawerListener( toggle );
        toggle.syncState();

        navDrawer.setNavigationItemSelectedListener( new NavigationView.OnNavigationItemSelectedListener()
        {
            @Override
            public boolean onNavigationItemSelected( @NonNull MenuItem item )
            {
                selectDrawerItem( item );
                return true;
            }
        } );

//        drawerEmail.setText( "teste@teste.com" );
    }

    @Override
    public boolean onNavigationItemSelected( @NonNull MenuItem item )
    {
        return false;
    }

    public void selectDrawerItem( @NonNull MenuItem item )
    {
        switch ( item.getItemId() )
        {
            case R.id.nav_statements:
                getSupportFragmentManager().beginTransaction().replace( R.id.content_main, new StatementFragment_() ).commit();
                break;
        }
        drawer.closeDrawer( Gravity.LEFT );
    }
}
