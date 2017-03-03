package com.floatingmuseum.mocloud.ui.login;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.daimajia.numberprogressbar.NumberProgressBar;
import com.floatingmuseum.mocloud.BuildConfig;
import com.floatingmuseum.mocloud.R;
import com.floatingmuseum.mocloud.base.BaseActivity;
import com.floatingmuseum.mocloud.data.SyncService;
import com.floatingmuseum.mocloud.data.bus.EventBusManager;
import com.floatingmuseum.mocloud.data.bus.SyncEvent;
import com.floatingmuseum.mocloud.utils.ResUtil;
import com.floatingmuseum.mocloud.utils.StringUtil;
import com.floatingmuseum.mocloud.utils.ToastUtil;
import com.orhanobut.logger.Logger;
import com.wang.avi.AVLoadingIndicatorView;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Floatingmuseum on 2016/5/8.
 */
public class LoginActivity extends BaseActivity {
    @BindView(R.id.webview)
    WebView webView;
    @BindView(R.id.numberprogressbar)
    NumberProgressBar numberProgressBar;
    @BindView(R.id.loading_token_request)
    AVLoadingIndicatorView loading_token_request;
    @BindView(R.id.ll_web_request)
    LinearLayout ll_web_request;
    @BindView(R.id.tv_info_show_container)
    LinearLayout tvInfoShowContainer;

    private LoginPresenter loginPresenter;

    public static final int REQUEST_CODE = 99;
    public static final int LOGIN_SUCCESS_CODE = 98;
    public static final String REDIRECT_URI = "urn:ietf:wg:oauth:2.0:oob";
    String QUERY_CODE = "code";


    @Override
    protected int currentLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ButterKnife.bind(this);
        EventBusManager.register(this);
        loginPresenter = new LoginPresenter(this);
        initView();
    }

    @Override
    protected void initView() {
        removeCache();
        webView.setWebViewClient(new LoginWebViewClient());
        webView.setWebChromeClient(new LoginWebChromeClient());
        webView.loadUrl("https://trakt.tv/oauth/authorize?response_type=code&client_id=" + BuildConfig.TraktID + "&redirect_uri=" + REDIRECT_URI);
    }

    private void removeCache() {
        CookieManager cookieManager = CookieManager.getInstance();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            cookieManager.removeAllCookies(new ValueCallback<Boolean>() {
                @Override
                public void onReceiveValue(Boolean value) {
                    Logger.d("removeAllCookies..onReceiveValue:" + value);
                }
            });
        } else {
            cookieManager.removeAllCookie();
        }

        webView.clearCache(true);
    }

    public void requestTokenSuccess() {
//        loading_token_request.smoothToHide();
        ToastUtil.showToast("Login success");
        updateSyncInfo("Login success");
        Intent intent = new Intent(this, SyncService.class);
        startService(intent);
    }

    public void requestTokenFailed() {
        loading_token_request.smoothToHide();
    }

    public String getCode(String url) {
        return url.substring(url.lastIndexOf("/") + 1);
    }

    private class LoginWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            Logger.d("url:" + url);
            if (url != null && url.startsWith("https://trakt.tv/oauth/authorize/")) {
//                Uri uri = Uri.parse(url);
//                String code = uri.getQueryParameter(QUERY_CODE);
                String code = getCode(url);
//                Logger.d("Code:"+code+"..."+getCode(url));
                ll_web_request.setVisibility(View.GONE);
                loading_token_request.smoothToShow();
                updateSyncInfo(ResUtil.getString(R.string.logging_in));
                loginPresenter.exchangeAccessToken(code);
            }
            return super.shouldOverrideUrlLoading(view, url);
        }
    }

    private void updateSyncInfo(String content) {
        TextView textView = new TextView(this);
        textView.setText(content);
        textView.setGravity(Gravity.CENTER);
        tvInfoShowContainer.addView(textView);
    }

    private class LoginWebChromeClient extends WebChromeClient {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            super.onProgressChanged(view, newProgress);
            if (newProgress != 100) {
                numberProgressBar.setVisibility(View.VISIBLE);
                numberProgressBar.setProgress(newProgress);
            } else {
                numberProgressBar.setProgress(newProgress);
                numberProgressBar.setVisibility(View.GONE);
            }
        }
    }

    @Override
    protected void onError(Exception e) {
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onSyncEvent(SyncEvent syncEvent) {
        if (syncEvent.syncFinished) {
            finish();
        }
        updateSyncInfo(syncEvent.syncInfo);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBusManager.unRegister(this);
    }
}
