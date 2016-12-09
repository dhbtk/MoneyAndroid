package io.edanni.money.ui.activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;
import io.edanni.money.R;
import io.edanni.money.infrastructure.security.CredentialsStore;
import io.edanni.money.ui.fragment.StatementListFragment_;
import org.androidannotations.annotations.*;

@EActivity(R.layout.activity_main)
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
    @Bean
    CredentialsStore store;

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
                return selectDrawerItem( item );
            }
        } );

        //        drawerEmail.setText( "teste@teste.com" );
    }

    private boolean selectDrawerItem( @NonNull MenuItem item )
    {
        switch ( item.getItemId() )
        {
            case R.id.nav_statements:
                changeToFragment( new StatementListFragment_() );
                break;
            default:
                drawer.closeDrawer( GravityCompat.START );
                return false;
        }
        drawer.closeDrawer( GravityCompat.START );
        return true;
    }

    @Override
    public boolean onNavigationItemSelected( @NonNull MenuItem item )
    {
        return false;
    }

    public void changeToFragment( Fragment fragment )
    {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace( R.id.content_main, fragment );
        ft.setTransition( FragmentTransaction.TRANSIT_FRAGMENT_OPEN );
        ft.addToBackStack( null );
        ft.commit();
    }

    void logout()
    {
        store.setEmail( null );
        store.setPassword( null );
        startActivity( new Intent( this, LoginActivity_.class ) );
        Toast.makeText( this, getString( R.string.sign_out_successful ), Toast.LENGTH_SHORT ).show();
        finish();
    }


}
