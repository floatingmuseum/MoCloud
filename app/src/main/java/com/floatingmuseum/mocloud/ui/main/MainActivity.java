package com.floatingmuseum.mocloud.ui.main;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.floatingmuseum.mocloud.MainMovieAdapter;
import com.floatingmuseum.mocloud.R;
import com.floatingmuseum.mocloud.base.BaseActivity;
import com.floatingmuseum.mocloud.data.Repository;
import com.floatingmuseum.mocloud.data.bus.SyncEvent;
import com.floatingmuseum.mocloud.data.entity.User;
import com.floatingmuseum.mocloud.ui.about.AboutActivity;
import com.floatingmuseum.mocloud.ui.calendar.CalendarActivity;
import com.floatingmuseum.mocloud.ui.recommendations.RecommendationsActivity;
import com.floatingmuseum.mocloud.ui.login.LoginActivity;
import com.floatingmuseum.mocloud.ui.settings.SettingsActivity;
import com.floatingmuseum.mocloud.ui.user.UserActivity;
import com.floatingmuseum.mocloud.utils.PermissionsUtil;
import com.floatingmuseum.mocloud.utils.RxUtil;
import com.floatingmuseum.mocloud.utils.SPUtil;
import com.floatingmuseum.mocloud.utils.ToastUtil;
import com.orhanobut.logger.Logger;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import rx.Observable;
import rx.functions.Action1;

public class MainActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    @BindView(R.id.mainViewPager)
    ViewPager mainViewPager;
    @BindView(R.id.mainTabLayout)
    TabLayout mainTabLayout;
    @BindView(R.id.nav_view)
    NavigationView navView;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;

    private final int REQUEST_CODE_ASK_PERMISSIONS = 233;
    CircleImageView iv_avatar;
    TextView tv_username;

    MainPresenter mainPresenter;

    private MenuItem syncState;
    //    private ImageView iv_avatar;

    @Override
    protected int currentLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ButterKnife.bind(this);
        registerEventBusHere();
        mainPresenter = new MainPresenter(this);
        initView();
    }

    protected void initView() {
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.setDrawerListener(toggle);
        toggle.syncState();

        View nav_header_main = navView.getHeaderView(0);
        iv_avatar = ButterKnife.findById(nav_header_main, R.id.iv_avatar);
        tv_username = ButterKnife.findById(nav_header_main, R.id.tv_username);

        navView.setNavigationItemSelectedListener(this);

//        View navHeaderView = navView.getHeaderView(0);
//        iv_avatar = (ImageView) navHeaderView.findViewById(R.id.iv_avatar);
//        TextView tv_username = (TextView) navHeaderView.findViewById(R.id.tv_username);
        MainMovieAdapter adapter = new MainMovieAdapter(getSupportFragmentManager());
        mainViewPager.setAdapter(adapter);
        mainTabLayout.setupWithViewPager(mainViewPager);

        iv_avatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (SPUtil.isLogin()) {
                    startUserActivity();
                } else {
                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                    MainActivity.this.startActivityForResult(intent, LoginActivity.REQUEST_CODE);
                }
            }
        });

        updateUserRelatedViews();
        if (SPUtil.isLogin()) {
            mainPresenter.syncUserData(this);
        }
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            initPermissionRequestDialog();
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    private void initPermissionRequestDialog() {
        boolean hasPermission = PermissionsUtil.hasPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        boolean hasEverRequestWriteExternalStoragePermission = SPUtil.getBoolean("hasEverRequestWriteExternalStoragePermission", false);
        //如果有读写权限或者以前请求过，都不再请求
        if (hasPermission || hasEverRequestWriteExternalStoragePermission) {
            return;
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.write_external_permission)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SPUtil.editBoolean("hasEverRequestWriteExternalStoragePermission", true);
                        requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CODE_ASK_PERMISSIONS);
                    }
                }).setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                SPUtil.editBoolean("hasEverRequestWriteExternalStoragePermission", true);
                dialog.dismiss();
            }
        });
        builder.create().show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CODE_ASK_PERMISSIONS) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Logger.d("权限申请成功");
            } else {
//                ToastUtil.showToast(R.string.permissionDenied);
                Logger.d("权限申请拒绝");
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
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
        syncState = menu.findItem(R.id.sync_state);
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

        switch (id) {
            case R.id.nav_my:
                startUserActivity();
                break;
            case R.id.nav_recommendations:
                startRecommendationsActivity();
                break;
            case R.id.nav_calendar:
                startActivity(new Intent(this, CalendarActivity.class));
                break;
            case R.id.nav_setting:
                startActivity(new Intent(this, SettingsActivity.class));
                break;
            case R.id.nav_about:
                startActivity(new Intent(this, AboutActivity.class));
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    private void startRecommendationsActivity() {
        if (!SPUtil.isLogin()) {
            ToastUtil.showToast(R.string.not_login);
            return;
        }
        startActivity(new Intent(this, RecommendationsActivity.class));
    }

    private void startUserActivity() {
        if (!SPUtil.isLogin()) {
            ToastUtil.showToast(R.string.not_login);
            return;
        }
        User user = SPUtil.loadUserFromSp();
        Intent intent = new Intent(MainActivity.this, UserActivity.class);
        intent.putExtra(UserActivity.USER_OBJECT, user);
        startActivity(intent);
    }

    @Override
    protected boolean canGoBack() {
        return false;
    }

    @Override
    protected void onError(Exception e) {

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onSyncEvent(SyncEvent syncEvent) {
        Logger.d("onSyncEvent:" + syncEvent.syncInfo);
        if ("Sync user settings finished.".equals(syncEvent.syncInfo)) {
            updateUserRelatedViews();
        }
        if (syncEvent.syncFinished) {
            ToastUtil.showToast(syncEvent.syncInfo);
        }
    }

    private void updateUserRelatedViews() {
        if (SPUtil.isLogin()) {
            //已登录，获取头像和用户名
            String avatarUrl = SPUtil.getString(SPUtil.SP_USER_SETTINGS, "avatar", "");
            // TODO: 2017/2/26 昵称更新成功，头像不成功
//            ImageLoader.load(this, avatarUrl, iv_avatar, R.drawable.default_userhead);
            Glide.with(this).load(avatarUrl).into(iv_avatar);
            tv_username.setText(SPUtil.getString(SPUtil.SP_USER_SETTINGS, "username", ""));
            Logger.d("avatarUrl:" + avatarUrl + "...");
            // TODO: 2017/2/7 空指针
//            syncState.setVisible(true);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mainPresenter.unSubscription();
    }
}
