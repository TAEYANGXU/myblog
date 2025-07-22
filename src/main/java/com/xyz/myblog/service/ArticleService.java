package com.xyz.myblog.service;

import com.github.pagehelper.PageInfo;
import com.xyz.myblog.dto.request.ArticleDTO;
import com.xyz.myblog.entity.Article;

public interface ArticleService {
    PageInfo<Article> getArticleList(int pageNum, int pageSize,
                                     String title, Long categoryId, String status);

    Article getArticleById(Long id);

    void saveArticle(ArticleDTO articleDTO);

    void updateArticle(ArticleDTO articleDTO);

    void deleteArticle(Long id);

    void updateArticleStatus(Long id, String status);
}