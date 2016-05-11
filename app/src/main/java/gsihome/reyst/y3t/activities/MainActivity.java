package gsihome.reyst.y3t.activities;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import gsihome.reyst.y3t.R;
import gsihome.reyst.y3t.adapters.PagerAdapter;
import gsihome.reyst.y3t.data.IssueEntity;
import gsihome.reyst.y3t.data.State;
import gsihome.reyst.y3t.data.rest.RestClientHolder;
import gsihome.reyst.y3t.data.rest.TicketAPI;
import gsihome.reyst.y3t.fragments.RecyclerViewFragment;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.drawer_layout)
    DrawerLayout mDrawer;
    @BindView(R.id.fab)
    FloatingActionButton mFab;
    private List<Fragment> mFragments;
    private List<String> mFragmentNames;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            toolbar.setTitle(R.string.all_appeals);
        }
        setSupportActionBar(toolbar);
        initDrawer(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeAsUpIndicator(R.drawable.toolbar_icon);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        initFragments();
        initViewPager();
    }

    @OnClick(R.id.fab)
    public void onFabClick(View v) {

        TicketAPI client = RestClientHolder.getService(this);

        Call<List<IssueEntity>> call = client.getInProgress();

        call.enqueue(new Callback<List<IssueEntity>>() {
            @Override
            public void onResponse(Call<List<IssueEntity>> call, Response<List<IssueEntity>> response) {
                Log.d("REST", response.body().toString());
            }

            @Override
            public void onFailure(Call<List<IssueEntity>> call, Throwable t) {
                Log.d("REST ERROR", t.getLocalizedMessage());
            }
        });
    }

    private void initFragments() {

        mFragments = new ArrayList<>(3);
        mFragmentNames = new ArrayList<>(3);

        mFragments.add(RecyclerViewFragment.getInstance(State.IN_WORK));
        mFragmentNames.add(getString(R.string.first_tab));

        mFragments.add(RecyclerViewFragment.getInstance(State.DONE));
        mFragmentNames.add(getString(R.string.second_tab));

        mFragments.add(RecyclerViewFragment.getInstance(State.WAIT));
        mFragmentNames.add(getString(R.string.third_tab));

    }

    private void initViewPager() {

        final ViewPager viewPager = (ViewPager) findViewById(R.id.view_pager);
        final PagerAdapter adapter = new PagerAdapter(getSupportFragmentManager(), mFragments, mFragmentNames);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);

        if (tabLayout != null && viewPager != null) {
            viewPager.setAdapter(adapter);
            tabLayout.setupWithViewPager(viewPager);
            viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        }
    }

    private void initDrawer(Toolbar toolbar) {
        //mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        if (mDrawer != null) {
            mDrawer.addDrawerListener(toggle);
        }

        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        if (navigationView != null) {
            navigationView.setNavigationItemSelectedListener(this);
        }
    }

    @Override
    public void onBackPressed() {
        if (mDrawer.isDrawerOpen(GravityCompat.START)) {
            mDrawer.closeDrawer(GravityCompat.START);
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
        if (id == R.id.action_filter) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        switch (id) {
            case R.id.nav_all_appeals:
                break;
            case R.id.nav_appeals_on_map:
                break;
            case R.id.nav_login:
                break;
        }

        mDrawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
