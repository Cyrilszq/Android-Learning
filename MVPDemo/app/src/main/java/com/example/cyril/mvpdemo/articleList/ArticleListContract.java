package com.example.cyril.mvpdemo.articleList;

import com.example.cyril.mvpdemo.BasePresenter;
import com.example.cyril.mvpdemo.BaseView;
import com.example.cyril.mvpdemo.data.Article;

/**
 * Created by Cyril on 2016/7/15.
 */
public interface ArticleListContract {
    interface Presenter extends BasePresenter {
        void getArticleFromNet();

        void getArticleFromCache();

        void getMoreArticle();

        void refreshData();
    }

    interface View extends BaseView<Presenter> {
        void showArticleList(Article article);

        void showLoading();

        void hideLoading();

        void showMoreDatas(Article article);
    }
}
