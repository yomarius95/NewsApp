package com.andrei.newsapp;

import java.util.ArrayList;

class News {

    private String mTitle;
    private String mSection;
    private ArrayList<String> mAuthor;
    private String mThumbnailImageUrl;
    private String mArticleUrl;
    private String mPublicationDate;

    News(String title, String section, ArrayList<String> author, String thumbnailImageUrl, String articleUrl, String publicationDate) {
        mTitle = title;
        mSection = section;
        mAuthor = author;
        mThumbnailImageUrl = thumbnailImageUrl;
        mArticleUrl = articleUrl;
        mPublicationDate = publicationDate;
    }

    String getTitle() {
        return mTitle;
    }

    String getSection() {
        return mSection;
    }

    ArrayList<String> getAuthor() { return mAuthor; }

    String getThumbnailImageUrl() {
        return mThumbnailImageUrl;
    }

    String getArticleUrl() {
        return mArticleUrl;
    }

    String getPublicationDate() {
        return mPublicationDate;
    }
}
