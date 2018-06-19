package com.example.bboyi.parsing;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.MyViewHolder>  {


    private ArrayList<NewsModel> newsModels = new ArrayList<>();
    private Context mActivity;

    private int lastPosition = -1;

    public NewsAdapter(Context context, ArrayList<NewsModel> newsModels) {
        this.mActivity = context;
        this.newsModels =newsModels;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ItemClickListener itemClickListener;
        RelativeLayout parentLayout ;
        private TextView tv_blog_title, tv_blog_theme, tv_blog_publishDate;

        public MyViewHolder(View view) {
            super(view);
            tv_blog_title = (TextView) view.findViewById(R.id.row_tv_blog_title);
            tv_blog_theme = (TextView) view.findViewById(R.id.row_tv_blog_author);
            tv_blog_publishDate = (TextView) view.findViewById(R.id.row_tv_blog_upload_date);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            this.itemClickListener.onItemClick(v,getLayoutPosition());
        }

        public void setItemClickListener(ItemClickListener ic)
        {
            this.itemClickListener=ic;

        }
    }

    @Override
    public NewsAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_data, parent, false);

        return new NewsAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final NewsModel nw= newsModels.get(position);
        holder.tv_blog_title.setText(nw.getNewsTitle());
        holder.tv_blog_theme.setText(nw.getTheme());
        holder.tv_blog_publishDate.setText(nw.getPublishDate());

        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onItemClick(View v, int pos) {
                System.out.println(pos);
                Intent intent = new Intent(mActivity, NewsActivity.class);
                intent.putExtra("title",nw.getNewsTitle());
                intent.putExtra("theme",nw.getTheme());
                intent.putExtra("date",nw.getPublishDate());
                intent.putExtra("link",nw.getNewsLink());

                mActivity.startActivity(intent);

            }
        });


    }



    @Override
    public int getItemCount() {
        return newsModels.size();
    }
}


