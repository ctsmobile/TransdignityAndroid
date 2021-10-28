package com.cts.removalspecialist.activities;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.cts.removalspecialist.R;
import com.cts.removalspecialist.databinding.ActivityWebviewBinding;


public class WebviewActivity extends AppCompatActivity {

    ActivityWebviewBinding binder;
    private String url;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binder = DataBindingUtil.setContentView(this, R.layout.activity_webview);
        init();
        //title  = getIntent().getExtras().getString("title");
        url = getIntent().getStringExtra("url");
       /* tv_toolbar_title.setText(getString(R.string.app_name));
        tv_toolbar_title.setAllCaps(true);*/
        binder.tvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        binder.webview.getSettings().setJavaScriptEnabled(true);
        binder.webview.setWebViewClient(new MyWebClient());
       binder.webview.getSettings().setDomStorageEnabled(true);
       binder.webview.getSettings().setAppCacheEnabled(true);
       binder.webview.getSettings().setAppCachePath(getApplicationContext().getFilesDir().getAbsolutePath() + "/cache");
       binder.webview.getSettings().setDatabaseEnabled(true);
       binder.webview.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);

       binder.webview.getSettings().setDatabasePath(getApplicationContext().getFilesDir().getAbsolutePath() + "/databases");
      //  binder.webview.setWebChromeClient(new WebChromeClient());
        binder.webview.loadUrl(url);
        binder.progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void onBackPressed() {
        if (binder.webview.canGoBack()) {
            binder.webview.goBack();
        } else {
            super.onBackPressed();
        }
    }

    private void init() {
       /* backPressLayout = (LinearLayout)findViewById(R.id.backPressLayout);
        tv_toolbar_title = (TextView)findViewById(R.id.tv_toolbar_title);*/
    }



    private class MyWebClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            super.shouldOverrideUrlLoading(view, url);
            view.loadUrl(url);
            binder.progressBar.setVisibility(View.VISIBLE);
            // view.loadUrl(url);
            return true;
            // return true;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            binder.progressBar.setVisibility(View.GONE);
            //  Log.e("URLs", url);
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            Log.e("URLs", url);

        }
        // open profile Activity after done payment
    }

}

