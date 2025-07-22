package com.xyz.myblog.mapper;

import com.xyz.myblog.entity.Article;
import com.xyz.myblog.entity.Tag;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ArticleMapper {
    // 分页查询文章列表
    List<Article> selectArticleList(@Param("title") String title,
                                    @Param("categoryId") Long categoryId,
                                    @Param("status") String status);

    // 根据ID查询文章详情
    Article selectArticleById(Long id);

    // 新增文章
    int insertArticle(Article article);

    // 更新文章
    int updateArticle(Article article);

    // 删除文章
    int deleteArticleById(Long id);

    // 更新文章状态
    int updateArticleStatus(@Param("id") Long id, @Param("status") String status);

    // 插入文章标签关联
    int insertArticleTag(@Param("articleId") Long articleId, @Param("tagId") Long tagId);

    // 删除文章的所有标签关联
    int deleteArticleTags(Long articleId);

    // 获取文章的所有标签
    List<Tag> selectTagsByArticleId(Long articleId);
}