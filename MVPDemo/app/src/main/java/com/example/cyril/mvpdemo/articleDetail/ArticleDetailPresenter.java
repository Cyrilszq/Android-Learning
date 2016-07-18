package com.example.cyril.mvpdemo.articleDetail;

/**
 * Created by Cyril on 2016/7/18.
 */
public class ArticleDetailPresenter implements ArticleDetailContract.Presenter {
    ArticleDetailContract.View mView;

    public ArticleDetailPresenter(ArticleDetailContract.View view) {
        mView = view;
        mView.setPresenter(this);

    }


    @Override
    public void start() {
        mView.showLoading();
    }

    @Override
    public void waitToRead() {

    }

    @Override
    public void saveArticle() {

    }
}
