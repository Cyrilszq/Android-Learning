package com.example.cyril.mvpdemo.articleList;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import com.example.cyril.mvpdemo.R;
import com.example.cyril.mvpdemo.data.Article;

import java.util.List;

/**
 * Created by Cyril on 2016/7/15.
 */
public class ArticleListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_FOOTER = 1;
    private static final int TYPE_ITEM = 0;
    List<Article.ResultsBean> mDatas;
    AdapterView.OnItemClickListener mItemClickListener;

    public ArticleListAdapter(List<Article.ResultsBean> datas) {
        mDatas = datas;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_FOOTER) {
            return new FootViewHolder(LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.footer, parent, false));
        } else if (viewType == TYPE_ITEM) {
            return new GankViewHolder(LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_article_list, parent, false));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof GankViewHolder) {
            if (mDatas.get(position).isRead) {
                ((GankViewHolder) holder).title.setTextColor(Color.GRAY);
                ((GankViewHolder) holder).title.setText(mDatas.get(position).desc);
                ((GankViewHolder) holder).author.setText(mDatas.get(position).who);
            } else {
                ((GankViewHolder) holder).title.setTextColor(Color.parseColor("#212121"));
                ((GankViewHolder) holder).title.setText(mDatas.get(position).desc);
                ((GankViewHolder) holder).author.setText(mDatas.get(position).who);
            }
        } else if (holder instanceof FootViewHolder) {
            ((FootViewHolder) holder).mTextView.setText("正在加载...");
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position + 1 == getItemCount()) {
            return TYPE_FOOTER;
        } else {
            return TYPE_ITEM;
        }
    }

    public void addMoreItem(List<Article.ResultsBean> moreDatas) {
        mDatas.addAll(moreDatas);
        notifyDataSetChanged();
    }

    public List<Article.ResultsBean> getDatas() {
        return mDatas;
    }

    @Override
    public int getItemCount() {
        return mDatas.size() + 1;
    }

    public void setSelected(int position) {
        mDatas.get(position).isRead = true;
    }

    public class GankViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        TextView author;

        public GankViewHolder(final View itemView) {
            super(itemView);
            if (mItemClickListener != null) {
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mItemClickListener.onItemClick(null, itemView, getPosition(), getItemId());
                    }
                });
            }
            title = (TextView) itemView.findViewById(R.id.article_title);
            author = (TextView) itemView.findViewById(R.id.article_author);

        }
    }

    public class FootViewHolder extends RecyclerView.ViewHolder {
        TextView mTextView;

        public FootViewHolder(View itemView) {
            super(itemView);
            mTextView = (TextView) itemView.findViewById(R.id.foot_text);
        }
    }

    public void setOnItemClickListener(AdapterView.OnItemClickListener listener) {
        this.mItemClickListener = listener;
    }
}

