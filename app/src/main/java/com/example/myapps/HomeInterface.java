package com.example.myapps;

import java.util.List;

public class HomeInterface {

    interface View {
        void setArticlesList(List<ArticlesModel> list);

        void setArticles(String title, String description, String publishedAt, String content, String urlToImage);
    }

    interface Presenter {
        void getArticles();
    }

}
