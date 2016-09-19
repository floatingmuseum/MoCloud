package com.floatingmuseum.mocloud.ui.login;

import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;

import com.daimajia.numberprogressbar.NumberProgressBar;
import com.floatingmuseum.mocloud.BuildConfig;
import com.floatingmuseum.mocloud.R;
import com.floatingmuseum.mocloud.base.BaseActivity;
import com.floatingmuseum.mocloud.dagger.presenter.DaggerLoginPresenterComponent;
import com.floatingmuseum.mocloud.dagger.presenter.LoginPresenterModule;
import com.floatingmuseum.mocloud.data.entity.TraktToken;
import com.floatingmuseum.mocloud.utils.ToastUtil;
import com.orhanobut.logger.Logger;
import com.wang.avi.AVLoadingIndicatorView;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Floatingmuseum on 2016/5/8.
 */
public class LoginActivity extends BaseActivity{
    @Bind(R.id.webview)
    WebView webView;
    @Bind(R.id.numberprogressbar)
    NumberProgressBar numberProgressBar;
    @Bind(R.id.loading_token_request)
    AVLoadingIndicatorView loading_token_request;
    @Bind(R.id.ll_web_request)
    LinearLayout ll_web_request;

    @Inject
    LoginPresenter loginPresenter;

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

        DaggerLoginPresenterComponent.builder()
                .repoComponent(getRepoComponent())
                .loginPresenterModule(new LoginPresenterModule(this))
                .build()
                .inject(this);

        initView();
    }

    @Override
    protected void initView() {
        removeCache();
        webView.setWebViewClient(new LoginWebViewClient());
        webView.setWebChromeClient(new LoginWebChromeClient());
        webView.loadUrl("https://trakt.tv/oauth/authorize?response_type=code&client_id="+BuildConfig.TraktID+"&redirect_uri="+REDIRECT_URI);
    }

    private void removeCache() {
        CookieManager cookieManager = CookieManager.getInstance();
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP){
            cookieManager.removeAllCookies(new ValueCallback<Boolean>() {
                @Override
                public void onReceiveValue(Boolean value) {
                    Logger.d("removeAllCookies..onReceiveValue:"+value);
                }
            });
        }else{
            cookieManager.removeAllCookie();
        }

        webView.clearCache(true);
    }

    public void requestTokenSuccess() {
        loading_token_request.setVisibility(View.GONE);
        ToastUtil.showToast("Login success");
        setResult(LoginActivity.LOGIN_SUCCESS_CODE);
        finish();
    }

    public void requestTokenFailed(){
        loading_token_request.setVisibility(View.GONE);
        // TODO: 2016/9/18 失败原因？retry按钮？关闭Activity？ 
    }

    private class LoginWebViewClient extends WebViewClient{
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            Logger.d("url:"+url);
            if(url!=null && url.startsWith("https://trakt.tv/oauth/authorize/")){
                Uri uri = Uri.parse(url);
                String code = uri.getQueryParameter(QUERY_CODE);
                Logger.d("Code:"+code);
                ll_web_request.setVisibility(View.GONE);
                loading_token_request.setVisibility(View.VISIBLE);
                loginPresenter.start(code);
//                exchangeAccessToken(code);
//                String code = url.substring(startUrl.length(),url.length());
            }
            return super.shouldOverrideUrlLoading(view, url);
        }
    }

    private class LoginWebChromeClient extends WebChromeClient{
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            super.onProgressChanged(view, newProgress);
            if(newProgress!=100){
                numberProgressBar.setVisibility(View.VISIBLE);
                numberProgressBar.setProgress(newProgress);
            }else{
                numberProgressBar.setProgress(newProgress);
                numberProgressBar.setVisibility(View.GONE);
            }
        }
    }
}
