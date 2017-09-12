package com.example.vishnu.tabact;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    Toolbar toolbar;
    AppBarLayout appBarLayout;
    private SectionsPagerAdapter mSectionsPagerAdapter;
    private int numTabs;
    private String[] namTabs;
    private int [] colorTabs,colorStatusBar;
    private String sharingText="Nothing to share";
    private ActionBarDrawerToggle mToggle;
    private DrawerLayout mdrawerLayout;
    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;
    private Window window;
    ImageButton btnShare;
    boolean dragState=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Name of the all tabs which you want to display
        namTabs=new String[]{"Today's word","History","Bookmarks","Random Word","Search","Alphabetically"};
        //Getting total number of tabs
        numTabs=namTabs.length;
        //getting colors for the actionbar for every tabs
        colorTabs=new int[]{R.color.first_actionBar,R.color.second_actionBar,R.color.third_actionBar,R.color.fourth_actionBar,R.color.fifth_actionBar,R.color.six_actionBar};
        //getting colors for the statusBar for every tabs
        colorStatusBar= new int[]{R.color.first_statusBar,R.color.second_statusBar,R.color.third_statusBar,R.color.fourth_statusBar,R.color.fifth_statusBar,R.color.six_statusBar};

        //Set up to change the color of starus bar for every tabs
        if(android.os.Build.VERSION.SDK_INT>=21) {
            window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(getResources().getColor(colorStatusBar[0])); // change color for first tab initial
        }
        btnShare= (ImageButton) findViewById(R.id.image);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each tabs
        // primary sections of the activity.

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        appBarLayout= (AppBarLayout) findViewById(R.id.appbar);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        mViewPager.setOffscreenPageLimit(numTabs);

        final TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE); // Set up the tab to scroll in horizontal direction
        tabLayout.setupWithViewPager(mViewPager);

        // Set up the button to open and close the drawer by clicking

        mdrawerLayout= (DrawerLayout) findViewById(R.id.drawer_layout);
        final ImageButton drawerBtn= (ImageButton) findViewById(R.id.drawer_button);
        final NavigationView drawerNavigation = (NavigationView) findViewById(R.id.nav_view);
        mdrawerLayout.setDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {

            }

            @Override
            public void onDrawerOpened(View drawerView) {
                dragState=true;
                drawerBtn.setImageResource(R.drawable.back_white);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                dragState=false;
                drawerBtn.setImageResource(R.drawable.ic_menu_white);
            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });
        // on click event for image button to open and close the drawer
        drawerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(dragState){
                    mdrawerLayout.closeDrawer(drawerNavigation);
                }
                else{
                    mdrawerLayout.openDrawer(drawerNavigation);
                }

            }
        });

        //Set up the changes when one tab is selected, unselected or reselected

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                if(android.os.Build.VERSION.SDK_INT>=21){
                        window.setStatusBarColor(getResources().getColor(colorStatusBar[tab.getPosition()])); // status bar color change for every tabs
                }
                appBarLayout.setBackgroundResource(colorTabs[tab.getPosition()]); // action bar color change for every tabs

                // Set up to change the color of shape used in share button
                Drawable background=btnShare.getBackground();
                GradientDrawable gradientDrawable=(GradientDrawable)background;
                gradientDrawable.setColor(getResources().getColor(colorTabs[tab.getPosition()]));

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }

    // Set up for share button when clicked
    public void toShare(View view){
        Intent shareIntent=new Intent(android.content.Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(android.content.Intent.EXTRA_SUBJECT,"floating Button");
        shareIntent.putExtra(android.content.Intent.EXTRA_TEXT, sharingText);
        startActivity(Intent.createChooser(shareIntent,"Share via"));
    }



    // To create option menu on the right side of the page
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    // For different option on the menu
    // Add more items using res/memu/memu_main.xml file
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
        if (id == R.id.action_about) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */


    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }


        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below)
            return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            // Show total number of pages = numTabs.
            return numTabs;
        }


        @Override
        public CharSequence getPageTitle(int position) {
            // For setting the name for different tabs using string array namTabs
            do{
                return namTabs[position];
            }
            while (numTabs>position);
        }

    }
}
