package com.rssreader.app.alex.rss_reader_sgu.ui.activity;

import android.app.Fragment;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.rssreader.app.alex.rss_reader_sgu.R;
import com.rssreader.app.alex.rss_reader_sgu.ui.fragment.FavouriteNewsListFragment;
import com.rssreader.app.alex.rss_reader_sgu.ui.fragment.FavouriteNewsListFragmentContainer;
import com.rssreader.app.alex.rss_reader_sgu.ui.fragment.NewPreviewFragment;
import com.rssreader.app.alex.rss_reader_sgu.ui.fragment.NewsListFragment;
import com.rssreader.app.alex.rss_reader_sgu.ui.fragment.NewsListFragmentContainer;
import com.rssreader.app.alex.rss_reader_sgu.ui.fragment.PrefsFragment;
import com.rssreader.app.alex.rss_reader_sgu.ui.fragment.UpdateFrequencyFragment;
import com.rssreader.app.alex.rss_reader_sgu.model.Article;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        NewsListFragment.Listener,
        FavouriteNewsListFragment.Listener,
        PrefsFragment.Listener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .replace(R.id.main_container, new NewsListFragmentContainer())
                    .commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        Log.d("TAG", "onBackPressed: " + getFragmentManager().getBackStackEntryCount());
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (getFragmentManager().getBackStackEntryCount() > 0) {
            getFragmentManager().popBackStackImmediate();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.nav_news) {
            if (getFragmentManager().getBackStackEntryCount() > 0) {
                getFragmentManager().popBackStackImmediate();
            }
            Fragment newsListFragmentContainer;
            if (getFragmentManager().findFragmentById(R.id.container) == null) {
                newsListFragmentContainer = new NewsListFragmentContainer();
            } else {
                newsListFragmentContainer = getFragmentManager().findFragmentById(R.id.container);
            }
            getFragmentManager().beginTransaction()
                    .replace(R.id.main_container, newsListFragmentContainer)
                    .commit();
        } else if (id == R.id.nav_favourite) {
            if (getFragmentManager().getBackStackEntryCount() > 0) {
                getFragmentManager().popBackStackImmediate();
            }
            getFragmentManager().beginTransaction()
                    .replace(R.id.main_container, new FavouriteNewsListFragmentContainer())
                    .commit();
        } else if (id == R.id.nav_settings) {
            if (getFragmentManager().getBackStackEntryCount() > 0) {
                getFragmentManager().popBackStackImmediate();
            }
            Fragment prefsFragment;
            if (getFragmentManager().findFragmentById(R.id.prefsFragment) == null) {
                prefsFragment = new PrefsFragment();
            } else {
                prefsFragment = getFragmentManager().findFragmentById(R.id.prefsFragment);
            }
            getFragmentManager().beginTransaction()
                    .replace(R.id.main_container, prefsFragment)
                    .commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void OnArticleClicked(Article article) {
        if (article.description == null) {
            return;
        }
        NewPreviewFragment fragment = new NewPreviewFragment();
        fragment.getArguments().putString("url", article.imageUrl);
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            fragment.getArguments().putBoolean("delMenu", true);
        }
        getFragmentManager().beginTransaction()
                .replace(R.id.container, fragment)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void OnFavouriteArticleClicked(Article article) {
        if (article.description == null) {
            return;
        }
        NewPreviewFragment fragment = new NewPreviewFragment();
        fragment.getArguments().putString("url", article.imageUrl);
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            fragment.getArguments().putBoolean("delMenu", true);
        }
        getFragmentManager().beginTransaction()
                .replace(R.id.favourite_container, fragment)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onUpdateFrequencyClicked() {
        if (getFragmentManager().getBackStackEntryCount() > 0) {
            getFragmentManager().popBackStackImmediate();
        }
        UpdateFrequencyFragment frequencyFragment = new UpdateFrequencyFragment();
        getFragmentManager().beginTransaction()
                .replace(R.id.main_container, frequencyFragment)
                .addToBackStack(null)
                .commit();
    }
}
