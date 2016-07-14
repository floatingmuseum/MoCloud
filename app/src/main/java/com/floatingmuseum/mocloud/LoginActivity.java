package com.floatingmuseum.mocloud;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.floatingmuseum.mocloud.date.entity.TokenRequest;
import com.floatingmuseum.mocloud.date.entity.TraktToken;
import com.floatingmuseum.mocloud.date.net.MoCloudFactory;
import com.orhanobut.logger.Logger;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Floatingmuseum on 2016/5/8.
 */
public class LoginActivity extends AppCompatActivity{
    @Bind(R.id.webview)
    WebView wv;

    String TRAKT_ID = BuildConfig.TraktID;
    String TRAKT_SECRET = BuildConfig.TraktSecret;
    String REDIRECT_URI = "urn:ietf:wg:oauth:2.0:oob";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ButterKnife.bind(this);
        wv.clearCache(true);
        wv.setWebViewClient(new LoginWebViewClient());
        wv.loadUrl("https://trakt.tv/oauth/authorize?response_type=code&client_id="+TRAKT_ID+"&redirect_uri="+REDIRECT_URI);
    }

    private class LoginWebViewClient extends WebViewClient{
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            Logger.d("url:"+url);
            String startUrl = "https://trakt.tv/oauth/authorize/";
            if(url.startsWith("https://trakt.tv/oauth/authorize/")){

                String code = url.substring(startUrl.length(),url.length());
                TokenRequest tokenRequest = new TokenRequest();
                tokenRequest.setCode(code);
                tokenRequest.setClient_id(TRAKT_ID);
                tokenRequest.setClient_secret(TRAKT_SECRET);
                tokenRequest.setRedirect_uri(REDIRECT_URI);
                tokenRequest.setGrant_type("authorization_code");

                MoCloudFactory.getInstance().getToken(tokenRequest)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<TraktToken>() {
                            @Override
                            public void onCompleted() {

                            }

                            @Override
                            public void onError(Throwable e) {

                            }

                            @Override
                            public void onNext(TraktToken traktToken) {
                                Logger.d("access_token:"+traktToken.getAccess_token());
                            }
                        });
            }
            return super.shouldOverrideUrlLoading(view, url);
        }
    }
}
