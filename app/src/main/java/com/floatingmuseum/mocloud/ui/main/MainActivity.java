package com.floatingmuseum.mocloud.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.floatingmuseum.mocloud.MainMovieAdapter;
import com.floatingmuseum.mocloud.R;
import com.floatingmuseum.mocloud.base.BaseActivity;
import com.floatingmuseum.mocloud.dagger.presenter.DaggerMainPresenterComponent;
import com.floatingmuseum.mocloud.dagger.presenter.MainPresenterModule;
import com.floatingmuseum.mocloud.data.entity.UserSettings;
import com.floatingmuseum.mocloud.ui.about.AboutActivity;
import com.floatingmuseum.mocloud.ui.calendar.CalendarActivity;
import com.floatingmuseum.mocloud.ui.login.LoginActivity;
import com.floatingmuseum.mocloud.ui.settings.SettingsActivity;
import com.floatingmuseum.mocloud.ui.user.UserActivity;
import com.floatingmuseum.mocloud.utils.ImageLoader;
import com.floatingmuseum.mocloud.utils.SPUtil;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    @Bind(R.id.mainViewPager)
    ViewPager mainViewPager;
    @Bind(R.id.mainTablayout)
    TabLayout mainTabLayout;
    @Bind(R.id.nav_view)
    NavigationView navView;
    @Bind(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    @Bind(R.id.iv_avatar)
    ImageView iv_avatar;
    @Bind(R.id.tv_username)
    TextView tv_username;

    @Inject
    MainPresenter mainPresenter;


    private boolean isLogin;
//    private ImageView iv_avatar;

    @Override
    protected int currentLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ButterKnife.bind(this);

        DaggerMainPresenterComponent.builder()
                .repoComponent(getRepoComponent())
                .mainPresenterModule(new MainPresenterModule(this))
                .build()
                .inject(this);

        setSupportActionBar(toolbar);
        isLogin = SPUtil.isLogin();

        initView();
    }

    protected void initView() {
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.setDrawerListener(toggle);
        toggle.syncState();

        navView.setNavigationItemSelectedListener(this);

//        View navHeaderView = navView.getHeaderView(0);
//        iv_avatar = (ImageView) navHeaderView.findViewById(R.id.iv_avatar);
//        TextView tv_username = (TextView) navHeaderView.findViewById(R.id.tv_username);
        MainMovieAdapter adapter = new MainMovieAdapter(getSupportFragmentManager());
        mainViewPager.setAdapter(adapter);
        mainTabLayout.setupWithViewPager(mainViewPager);

        if (isLogin){
            //已登录，获取头像和用户名
            ImageLoader.load(this,SPUtil.getString("avatar_path","-1"),iv_avatar,R.drawable.default_userhead);
            tv_username.setText(SPUtil.getString("username","-1"));
            //请求用户最新信息
            //request user settings every time when app start,if login is true.
            mainPresenter.getUserSettings();
        }

        iv_avatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isLogin){
                    startActivity(new Intent(MainActivity.this,UserActivity.class));
                }else{
                    Intent intent = new Intent(MainActivity.this,LoginActivity.class);
                    MainActivity.this.startActivityForResult(intent, LoginActivity.REQUEST_CODE);
                }
            }
        });
    }

    public void refreshUserView(UserSettings userSettings) {
        if(userSettings==null){
            ImageLoader.loadFromDrawable(this,R.drawable.default_userhead,iv_avatar);
            tv_username.setText(R.string.click_to_login);
        }else{
            ImageLoader.load(this,userSettings.getImages().getAvatar().getFull(),iv_avatar,R.drawable.default_userhead);
            tv_username.setText(userSettings.getUsername());
        }
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
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
        if (id == R.id.action_type_switch) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        switch (id){
            case R.id.nav_my:
                startActivity(new Intent(this,UserActivity.class));
                break;
            case R.id.nav_calendar:
                startActivity(new Intent(this,CalendarActivity.class));
                break;
            case R.id.nav_setting:
                startActivity(new Intent(this,SettingsActivity.class));
                break;
            case R.id.nav_about:
                startActivity(new Intent(this,AboutActivity.class));
                break;
            case R.id.nav_logout:
                mainPresenter.logout();
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected boolean canGoBack() {
        return false;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == LoginActivity.REQUEST_CODE){
            if(resultCode==LoginActivity.LOGIN_SUCCESS_CODE){
                mainPresenter.getUserSettings();
                //登录成功，更新头像
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }
}
