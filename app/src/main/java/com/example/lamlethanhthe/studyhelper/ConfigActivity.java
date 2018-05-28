package com.example.lamlethanhthe.studyhelper;

import android.content.Intent;
import android.speech.RecognizerIntent;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.lamlethanhthe.studyhelper.DataModules.DataManager;
import com.example.lamlethanhthe.studyhelper.DataModules.Profile;
import com.example.lamlethanhthe.studyhelper.AdapterModules.ConfigTabHost;
import com.example.lamlethanhthe.studyhelper.AdapterModules.ConfigTabOthers;
import com.example.lamlethanhthe.studyhelper.AdapterModules.ConfigTabUnit;

import java.util.ArrayList;

public class ConfigActivity extends AppCompatActivity {
    private DataManager dat;
    private int curUnit;
    private String curHost;

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use units
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to units
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return units fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        dat = new DataManager(this);
        curUnit = dat.getUnitChoice();
        curHost = dat.getHostChoice();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_config, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify units parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        return super.onOptionsItemSelected(item);
    }

    /**
     * A {@link FragmentPagerAdapter} that returns units fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            switch (position) {
                case 0:
                    return new ConfigTabUnit();
                case 1:
                    return new ConfigTabHost();
                case 2:
                    return new ConfigTabOthers();
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Cụm Thi";
                case 1:
                    return "Đơn Vị";
                case 2:
                    return "Tuỳ Chọn";
            }
            return null;
        }
    }

    public void updateHosts(int newUnit) {
        if (newUnit != curUnit) {
            mViewPager.setAdapter(mSectionsPagerAdapter);
            curUnit = newUnit;
        }
        mViewPager.setCurrentItem(1);
    }

    public void updateHostChoice(String newHost) {
        if (newHost != null && !newHost.toLowerCase().equals(curHost)) {
            curHost = newHost;
        }
        mViewPager.setCurrentItem(2);
    }

    public void finalize(boolean[] params) {
        dat.updateProfile(new Profile(DataManager.curUser, params[0], curHost));
        dat.updateTestChoices_Evil(new boolean[] {params[1], params[2], params[3]});

        this.finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (data != null && resultCode == RESULT_OK)
            if (requestCode == 101) {
                mViewPager.setCurrentItem(0);
                ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                Toast.makeText(ConfigActivity.this, result.get(0), Toast.LENGTH_SHORT).show();
            }
    }
}
