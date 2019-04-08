package com.example.yongs.sharetrips.activity;

import android.content.Intent;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.yongs.sharetrips.R;
import com.example.yongs.sharetrips.fragment.HomeFragment;
import com.example.yongs.sharetrips.fragment.ProfileFragment;
import com.example.yongs.sharetrips.fragment.RecommendFragment;
import com.example.yongs.sharetrips.fragment.ReportFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {


    @BindView(R.id.toolbar)
    android.support.v7.widget.Toolbar toolbar;

    @BindView(R.id.tablayout)
    TabLayout tabLayout;

    @BindView(R.id.fab)
    FloatingActionButton fab;

    public final String FRAGMENT_HOME_TAG = "fragment_home";
    public final String FRAGMENT_REPORT_TAG = "fragment_report";
    public final String FRAGMENT_RECOMMEND_TAG = "fragment_recommend";
    public final String FRAGMENT_PROFILE_TAG = "fragment_profile";

    public final String TAG = MainActivity.class.getSimpleName();

    private FragmentManager mFragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);

        setFab();

        setTab();

        switchToHome();

    }

    private void setTab(){
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch(tab.getPosition()){
                    case 0:
                        switchToHome();
                        findViewById(R.id.fab).setVisibility(View.VISIBLE);
                        break;
                    case 1:
                        switchToReport();
                        findViewById(R.id.fab).setVisibility(View.VISIBLE);
                        break;
                    case 2:
                        switchToRecommend();
                        findViewById(R.id.fab).setVisibility(View.GONE);
                        break;
                    case 3:
                        switchToProfile();
                        findViewById(R.id.fab).setVisibility(View.GONE);
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void switchToHome(){
        getSupportActionBar().setTitle("홈");
        FragmentTransaction transaction = getFragmentManagerInstance().beginTransaction();
        HomeFragment homeFragment = new HomeFragment();
        transaction.replace(R.id.fragment_container, homeFragment, FRAGMENT_HOME_TAG).commitAllowingStateLoss();
    }

    private void switchToReport(){
        getSupportActionBar().setTitle("나의 여행");
        FragmentTransaction transaction = getFragmentManagerInstance().beginTransaction();
        ReportFragment reportFragment = new ReportFragment();
        transaction.replace(R.id.fragment_container, reportFragment, FRAGMENT_REPORT_TAG).commitAllowingStateLoss();
    }

    private void switchToRecommend(){
        getSupportActionBar().setTitle("여행 추천");
        FragmentTransaction transaction = getFragmentManagerInstance().beginTransaction();
        RecommendFragment recommendFragment = new RecommendFragment();
        transaction.replace(R.id.fragment_container, recommendFragment, FRAGMENT_RECOMMEND_TAG).commit();
    }

    private void switchToProfile(){
        getSupportActionBar().setTitle("프로필");
        FragmentTransaction transaction = getFragmentManagerInstance().beginTransaction();
        ProfileFragment profileFragment = new ProfileFragment();
        transaction.replace(R.id.fragment_container, profileFragment, FRAGMENT_PROFILE_TAG).commitAllowingStateLoss();
    }

    private FragmentManager getFragmentManagerInstance(){
        if(mFragmentManager==null){
            mFragmentManager = getSupportFragmentManager();
        }
        return mFragmentManager;
    }

    private void setFab(){
        fab.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ReportActivity.class);
                intent.putExtra("user",getIntent().getStringExtra("user"));
                startActivity(intent);
            }
        });
    }
}
