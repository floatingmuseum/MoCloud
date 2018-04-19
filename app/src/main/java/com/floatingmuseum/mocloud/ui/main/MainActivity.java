package com.floatingmuseum.mocloud.ui.main;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.crashlytics.android.Crashlytics;
import com.floatingmuseum.mocloud.R;
import com.floatingmuseum.mocloud.base.BaseActivity;
import com.floatingmuseum.mocloud.data.bus.SyncEvent;
import com.floatingmuseum.mocloud.ui.movie.MovieFragment;
import com.floatingmuseum.mocloud.ui.show.ShowFragment;
import com.floatingmuseum.mocloud.ui.user.UserFragment;
import com.floatingmuseum.mocloud.utils.PermissionsUtil;
import com.floatingmuseum.mocloud.utils.SPUtil;
import com.floatingmuseum.mocloud.utils.ToastUtil;
import com.orhanobut.logger.Logger;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.fabric.sdk.android.Fabric;

/**
 * Created by Floatingmuseum on 2017/7/20.
 */

public class MainActivity extends BaseActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    //    @BindView(R.id.viewpager)
//    ViewPager vp;

//    @BindView(R.id.appBarLayout)
//    AppBarLayout appBarLayout;
//    @BindView(R.id.toolbar)
//    Toolbar toolbar;

    @BindView(R.id.fl_main)
    FrameLayout flMain;

    @BindView(R.id.nav_view)
    BottomNavigationView navigation;
    //    @BindView(R.id.app_bar_user)
//    AppBarLayout appBarUser;
//    @BindView(R.id.collapsing_toolbar)
//    CollapsingToolbarLayout collapsingToolbar;

    private MainPresenter mainPresenter;
    private final int REQUEST_CODE_ASK_PERMISSIONS = 233;
    private MovieFragment movieFragment;
    private ShowFragment showFragment;
    private UserFragment userFragment;
    private FragmentManager fragmentManager;


    @Override
    protected int currentLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        ButterKnife.bind(this);
        registerEventBusHere();
        initView();
        mainPresenter = new MainPresenter(this);
        initData();
        String text = "[spoiler]The chemistry between the two leads feels so natural [/spoiler]and [spoiler]it is because of this chemistry that Steve's sacrifice is so heartbreaking[/spoiler].";
        Logger.d("TextLength:" + text.length() + "...first:" + text.indexOf("[spoiler]") + "...second:" + text.indexOf("[/spoiler]") + "..." + text.indexOf("[spoiler]", text.indexOf("[/spoiler]") + 1));
        String text1 = "hello java java world.";
        int index = text1.indexOf("java") + "java".length();
        Logger.d("TextLength:" + text1.length() + "...java:" + text1.indexOf("java") + "...world:" + text1.indexOf("world") + "...fromIndex:" + index + "...secondJava:" + text1.indexOf("java", index));
    }

    @Override
    protected void initView() {
        fragmentManager = getSupportFragmentManager();
        movieFragment = new MovieFragment();
        showFragment = new ShowFragment();
        userFragment = new UserFragment();
        fragmentManager.beginTransaction()
                .add(R.id.fl_main, movieFragment, MovieFragment.TAG)
                .add(R.id.fl_main, showFragment, ShowFragment.TAG)
                .add(R.id.fl_main, userFragment, UserFragment.TAG)
                .hide(showFragment)
                .hide(userFragment)
                .commit();
        navigation.setOnNavigationItemSelectedListener(this);
    }

    private void setCurrentFragment(int position) {
        if (position == 0) {
            fragmentManager.beginTransaction().show(movieFragment).commit();
            fragmentManager.beginTransaction().hide(showFragment).commit();
            fragmentManager.beginTransaction().hide(userFragment).commit();
        } else if (position == 1) {
            fragmentManager.beginTransaction().show(showFragment).commit();
            fragmentManager.beginTransaction().hide(movieFragment).commit();
            fragmentManager.beginTransaction().hide(userFragment).commit();

        } else if (position == 2) {
            fragmentManager.beginTransaction().show(userFragment).commit();
            fragmentManager.beginTransaction().hide(showFragment).commit();
            fragmentManager.beginTransaction().hide(movieFragment).commit();
        }
    }

    private int getAttr() {
        TypedValue outValue = new TypedValue();
        getTheme().resolveAttribute(R.attr.actionBarSize, outValue, true);
        WindowManager wm = (WindowManager) getSystemService(WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        float out = outValue.getDimension(outMetrics);

        TypedValue tv = new TypedValue();
        getTheme().resolveAttribute(R.attr.actionBarSize, tv, true);
        Logger.d("ActionBarSize:" + tv.toString() + "..." + out);
        return (int) out;
    }

    private void hideAppBar(boolean isShow) {
        Logger.d("显示AppBar:" + isShow);
        if (isShow) {
            ViewGroup.LayoutParams params = appBarLayout.getLayoutParams();
            params.height = 0;
            params.width = 0;
            appBarLayout.setLayoutParams(params);
        } else {
            ViewGroup.LayoutParams params = appBarLayout.getLayoutParams();
            params.height = getAttr();
            params.width = ViewGroup.LayoutParams.MATCH_PARENT;
            appBarLayout.setLayoutParams(params);
        }
    }

    private void initData() {
        if (SPUtil.isLogin()) {
            mainPresenter.syncUserData(this);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
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
    protected boolean canGoBack() {
        return false;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.navigation_movie:
                setCurrentFragment(0);
//                vp.setCurrentItem(0, true);
                return true;
            case R.id.navigation_tv:
                setCurrentFragment(1);
//                vp.setCurrentItem(1, true);
                return true;
            case R.id.navigation_user:
                setCurrentFragment(2);
//                vp.setCurrentItem(2, true);
                return true;
        }
        return false;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onSyncEvent(SyncEvent syncEvent) {
        Logger.d("onSyncEvent:" + syncEvent.syncInfo);
        if ("Sync user settings finished.".equals(syncEvent.syncInfo)) {
//            updateUserRelatedViews();
        }
        if (syncEvent.syncFinished) {
            ToastUtil.showToast(syncEvent.syncInfo);
        }
    }

    @Override
    protected void onError(Exception e) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mainPresenter.unSubscription();
    }
}
