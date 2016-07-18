package com.example.cyril.mvpdemo.articleList;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.example.cyril.mvpdemo.R;
import com.example.cyril.mvpdemo.articleDetail.ArticleDetailActivity;
import com.example.cyril.mvpdemo.data.Article;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class ArticleListFragment extends Fragment implements ArticleListContract.View {
    @Bind(R.id.article_list_rv)
    RecyclerView mRecyclerView;
    @Bind(R.id.refresh)
    SwipeRefreshLayout mRefreshLayout;
    public static final String WEBURL = "web_url";
    //    public static final String webWho = "com.example.mygankdaily.ui.webwho";
//    public static final String webDesc = "com.example.mygankdaily.ui.webdesc";
    private static final String CATEGORY_URL = "url";
    private ArticleListAdapter mAdapter;
    private ArticleListContract.Presenter mPresenter;

    public static ArticleListFragment newInstance(String category) {
        Bundle args = new Bundle();
        args.putString(CATEGORY_URL, category);
        ArticleListFragment articleListFragment = new ArticleListFragment();
        articleListFragment.setArguments(args);
        return articleListFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_article_list, container, false);
        ButterKnife.bind(this, root);
        //实例化presenter,将view的引用传递过去，这样在presenter中就可以调用view中的方法，来操作view
        //并且在presenter的构造方法中设置view的presenter，下面view就可以调用presenter中的方法来加载数据
        new ArticleListPresenter(this, getArguments().getString(CATEGORY_URL));
        //从网络加载数据
        initSwipeRefresh();
        initRecView();
        mPresenter.start();
        mPresenter.getArticleFromNet();


        return root;
    }

    private void initRecView() {
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(layoutManager);
        //监听滑动到底部是触发加载更多
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            int lastItem = 0;

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE && lastItem + 1 == mAdapter.getItemCount()) {
                    mPresenter.getMoreArticle();
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                lastItem = layoutManager.findLastVisibleItemPosition();
            }
        });

    }

    private void initSwipeRefresh() {
        SwipeRefreshLayout.OnRefreshListener listener = new SwipeRefreshLayout.OnRefreshListener() {
            public void onRefresh() {
                mPresenter.refreshData();
                mPresenter.getArticleFromNet();
            }

        };

        mRefreshLayout.setColorSchemeResources(R.color.colorAccent);
        mRefreshLayout.setOnRefreshListener(listener);
    }

    @Override
    public void showArticleList(Article article) {
        mAdapter = new ArticleListAdapter(article.results);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //点击后相当于阅读过了 标题颜色变灰
                // 不过持久化不想做了...一刷新颜色会变回来
                mAdapter.setSelected(position);
                Intent intent = new Intent(getActivity(), ArticleDetailActivity.class);
                intent.putExtra(WEBURL, mAdapter.getDatas().get(position).url);
//                intent.putExtra(webWho,mAdapter.getDatas().get(position).who);
//                intent.putExtra(webDesc,mAdapter.getDatas().get(position).desc);
                startActivity(intent);
                mAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void setPresenter(ArticleListContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void showError() {
        Snackbar.make(mRecyclerView, "网络出现错误", Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void showLoading() {
        mRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                mRefreshLayout.setRefreshing(true);
            }
        });
    }

    @Override
    public void hideLoading() {
        mRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                mRefreshLayout.setRefreshing(false);
            }
        });

    }

    @Override
    public void showMoreDatas(Article article) {
        if (mAdapter != null) {
            mAdapter.addMoreItem(article.results);
        }
    }


}
