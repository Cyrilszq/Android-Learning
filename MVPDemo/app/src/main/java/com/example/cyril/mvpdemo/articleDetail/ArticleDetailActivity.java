package com.example.cyril.mvpdemo.articleDetail;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.cyril.mvpdemo.R;
import com.example.cyril.mvpdemo.articleList.ArticleListFragment;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ArticleDetailActivity extends AppCompatActivity implements ArticleDetailContract.View {

    @Bind(R.id.web_progressbar)
    ProgressBar mProgressBar;
    @Bind(R.id.article_web)
    WebView mWebView;
    @Bind(R.id.web_toolbar)
    Toolbar mToolbar;
    ArticleDetailContract.Presenter mPresenter;
    String webUrl;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_detail);
        ButterKnife.bind(this);
        initToolbar();
        webUrl = getIntent().getStringExtra(ArticleListFragment.WEBURL);
        new ArticleDetailPresenter(this);
        mPresenter.start();
        initWebView();
    }

    private void initWebView() {
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.loadUrl(webUrl);
        mWebView.setWebChromeClient(new WebChromeClient() {

            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress == 100) {
                    hideLoading();
                }
                super.onProgressChanged(view, newProgress);
            }


        });
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
                showError();
                hideLoading();
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.meun_web, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //TODO 数据持久化
        switch (item.getItemId()){
            case R.id.waiting_read:
                mPresenter.waitToRead();
                break;
            case R.id.save:
                mPresenter.saveArticle();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initToolbar() {
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void showLoading() {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        mProgressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void setPresenter(ArticleDetailContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void showError() {
        Toast.makeText(this, "加载出错了", Toast.LENGTH_SHORT).show();
    }


}
