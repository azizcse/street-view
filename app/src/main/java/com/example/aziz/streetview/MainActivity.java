package com.example.aziz.streetview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    WebView myWebView;

    private double lat = 23.7496;
    private double lng = 90.3948;
    private static final String URL = "file:///android_asset/index.html";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_streetview);

        myWebView = findViewById(R.id.web_view);


        initWebView(lat, lng);
    }

    private void initWebView(final double lat, final double lng) {

        WebSettings webSettings = myWebView.getSettings();
        //Enable Javascript
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);
        myWebView.setWebChromeClient(new WebChromeClient());
        //Inject WebAppInterface methods into Web page by having Interface name 'Android'
        myWebView.addJavascriptInterface(new JavascriptHandler(), "Android");
        myWebView.loadUrl("file:///android_asset/index.html");
        myWebView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                myWebView.loadUrl("javascript:initialize('" + lat + "','" + lng + "')");
            }

        });

    }

    final class JavascriptHandler {
        JavascriptHandler() {
        }

        @JavascriptInterface
        public void streetViewLoadingFailed(String text) {
            Toast.makeText(MainActivity.this, "Hello ="+text, Toast.LENGTH_LONG).show();
            //showPopup();
            //mainProgressBar.setVisibility(View.GONE);
        }

        @JavascriptInterface
        public void streetViewLoadingSuccess(String success) {
            //mainProgressBar.setVisibility(View.GONE);
            Toast.makeText(MainActivity.this, "Success ="+success, Toast.LENGTH_LONG).show();
        }
    }
}
/*
Latitude‎: ‎23.911522	DMS Lat‎: ‎23° 54' 41.4792'' N
        Longitude‎: ‎90.388962
23.7496° N, 90.3948° E

        */
