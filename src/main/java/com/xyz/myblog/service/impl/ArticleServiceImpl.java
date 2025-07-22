package com.xyz.myblog.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xyz.myblog.dto.request.ArticleDTO;
import com.xyz.myblog.entity.Article;
import com.xyz.myblog.entity.Category;
import com.xyz.myblog.mapper.ArticleMapper;
import com.xyz.myblog.mapper.CategoryMapper;
import com.xyz.myblog.service.ArticleService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Service
public class ArticleServiceImpl implements ArticleService {
    @Autowired
    private ArticleMapper articleMapper;
    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public PageInfo<Article> getArticleList(int pageNum, int pageSize,
                                            String title, Long categoryId, String status) {
        PageHelper.startPage(pageNum, pageSize);
        List<Article> articles = articleMapper.selectArticleList(title, categoryId, status);

        // 填充分类名称和标签
        for (Article article : articles) {
            if (article.getCategoryId() != null) {
                Category category = categoryMapper.selectCategoryById(article.getCategoryId());
                if (category != null) {
                    article.setCategoryName(category.getName());
                }
            }
            article.setTags(articleMapper.selectTagsByArticleId(article.getId()));
        }

        return new PageInfo<>(articles);
    }

    @Override
    public Article getArticleById(Long id) {
        Article article = articleMapper.selectArticleById(id);
        if (article != null) {
            if (article.getCategoryId() != null) {
                Category category = categoryMapper.selectCategoryById(article.getCategoryId());
                if (category != null) {
                    article.setCategoryName(category.getName());
                }
            }
            article.setTags(articleMapper.selectTagsByArticleId(id));
        }
        return article;
    }

    @Override
    public void saveArticle(ArticleDTO articleDTO) {
        Article article = new Article();
        BeanUtils.copyProperties(articleDTO, article);
        article.setCreateTime(new Date());
        article.setUpdateTime(new Date());
        articleMapper.insertArticle(article);

        // 处理标签
        if (articleDTO.getTagIds() != null && !articleDTO.getTagIds().isEmpty()) {
            for (Long tagId : articleDTO.getTagIds()) {
                articleMapper.insertArticleTag(article.getId(), tagId);
            }
        }
    }

    @Override
    public void updateArticle(ArticleDTO articleDTO) {
        Article article = new Article();
        BeanUtils.copyProperties(articleDTO, article);
        article.setUpdateTime(new Date());
        articleMapper.updateArticle(article);

        // 更新标签 - 先删除旧标签，再添加新标签
        articleMapper.deleteArticleTags(article.getId());
        if (articleDTO.getTagIds() != null && !articleDTO.getTagIds().isEmpty()) {
            for (Long tagId : articleDTO.getTagIds()) {
                articleMapper.insertArticleTag(article.getId(), tagId);
            }
        }
    }

    @Override
    public void deleteArticle(Long id) {
        articleMapper.deleteArticleById(id);
    }

    @Override
    public void updateArticleStatus(Long id, String status) {
        articleMapper.updateArticleStatus(id, status);
    }
}