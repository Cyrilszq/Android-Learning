package com.example.cyril.mvpdemo.articleList;

import com.example.cyril.mvpdemo.core.RetrofitSington;
import com.example.cyril.mvpdemo.data.Article;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Cyril on 2016/7/15.
 */
public class ArticleListPresenter implements ArticleListContract.Presenter {

    private ArticleListContract.View mView;

    //需要加载的文章类别（Android or iOS）
    private String category;
    //加载的页数
    private int num = 1;
    private Boolean isMore = false;

    public ArticleListPresenter(ArticleListContract.View view, String category) {
        mView = view;
        this.category = category;
        mView.setPresenter(this);
    }

    @Override
    public void getArticleFromNet() {
        if (isMore) {
            num++;
        } else {
            num = 1;
        }
        RetrofitSington.getApiService()
                .getArticle(category, num)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Article>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.showError();
                        mView.hideLoading();
                    }

                    @Override
                    public void onNext(Article article) {
                        if (isMore) {
                            mView.showMoreDatas(article);
                        } else {
                            mView.showArticleList(article);
                        }
                        mView.hideLoading();
                    }
                });

    }

    @Override
    public void getArticleFromCache() {
        //TODO article cache
    }

    @Override
    public void getMoreArticle() {
        isMore = true;
        getArticleFromNet();
    }

    @Override
    public void refreshData() {
        isMore = false;
    }


    @Override
    public void start() {
        mView.showLoading();
    }


}
