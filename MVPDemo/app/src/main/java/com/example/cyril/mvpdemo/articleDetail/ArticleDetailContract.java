package com.example.cyril.mvpdemo.articleDetail;

import com.example.cyril.mvpdemo.BasePresenter;
import com.example.cyril.mvpdemo.BaseView;

/**
 * Created by Cyril on 2016/7/18.
 */
public interface ArticleDetailContract {

    interface Presenter extends BasePresenter {
        void waitToRead();

        void saveArticle();
    }

    interface View extends BaseView<ArticleDetailContract.Presenter> {
        void showLoading();

        void hideLoading();
    }
}
