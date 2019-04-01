package com.andrei.newsapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import java.util.ArrayList;
import butterknife.BindView;
import butterknife.ButterKnife;

class NewsAdapter extends ArrayAdapter<News> {

    NewsAdapter (Context context, ArrayList<News> news) {
        super(context, 0, news);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View listItemView = convertView;
        ViewHolder holder;

        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);

            holder = new ViewHolder(listItemView);
            listItemView.setTag(holder);
        } else {
            holder = (ViewHolder) listItemView.getTag();
        }

        final News currentNews = getItem(position);

        if(currentNews != null) {
            Glide
                    .with(getContext())
                    .load(currentNews.getThumbnailImageUrl())
                    .fitCenter()
                    .into(holder.thumbnailImageView);
            holder.titleTextView.setText(currentNews.getTitle());
            holder.sectionTextView.setText(currentNews.getSection());
            String publicationDate = currentNews.getPublicationDate();
            holder.dateTextView.setText(publicationDate.substring(0 ,publicationDate.indexOf('T')));
            StringBuilder authors = new StringBuilder();
            for(int i = 0; i < currentNews.getAuthor().size(); i++) {
                authors.append(currentNews.getAuthor().get(i)).append("\n");
            }
            holder.authorsTextView.setText(authors.toString());

        }

        return listItemView;
    }

    static class ViewHolder {
        @BindView(R.id.thumbnail) ImageView thumbnailImageView;
        @BindView(R.id.title) TextView titleTextView;
        @BindView(R.id.section) TextView sectionTextView;
        @BindView(R.id.date) TextView dateTextView;
        @BindView(R.id.authors) TextView authorsTextView;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
