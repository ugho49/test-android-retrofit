package fr.ughostephan.myappretrofit.activity;

import android.app.Fragment;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import fr.ughostephan.myappretrofit.R;
import fr.ughostephan.myappretrofit.fragment.MainFragment;
import fr.ughostephan.myappretrofit.fragment.OtherFragment;
import fr.ughostephan.myappretrofit.fragment.SecondFragment;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.content_frame)
    FrameLayout contentFrame;
    @BindView(R.id.nav_view)
    NavigationView navigationView;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        checkFirstItem();
    }

    /**
     * Check First Item at start
     */
    private void checkFirstItem() {
        // Check First Item at start
        final int firstItemId = navigationView.getMenu().getItem(0).getItemId();
        navigationView.setCheckedItem(firstItemId);
        navigationView.getMenu().performIdentifierAction(firstItemId, 0);
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        Fragment newFragment = null;

        if (id == R.id.nav_posts_recyclerview) {
            newFragment = new MainFragment();
        } else if (id == R.id.nav_posts_listview) {
            newFragment = new SecondFragment();
        } else if (id == R.id.nav_other_fragment) {
            newFragment = new OtherFragment();
        } else if (id == R.id.nav_share) {
            Toast.makeText(this, "Click on share button", Toast.LENGTH_LONG).show();
        } else if (id == R.id.nav_send) {
            Toast.makeText(this, "Click on send button", Toast.LENGTH_LONG).show();
        }

        if (newFragment != null) {
            getFragmentManager().beginTransaction()
                    .replace(contentFrame.getId(), newFragment)
                    .commit();
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
