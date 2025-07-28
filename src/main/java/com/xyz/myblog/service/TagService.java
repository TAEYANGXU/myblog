package com.xyz.myblog.service;

import com.github.pagehelper.PageInfo;
import com.xyz.myblog.entity.Tag;

import java.util.List;

public interface TagService {

    /**
     * 获取所有标签（带分页）
     */
    PageInfo<Tag> getTags(int pageNum, int pageSize,String name);

    /**
     * 获取所有标签（不分页，用于下拉选择）
     */
    List<Tag> getAllTags();

    /**
     * 根据ID获取标签详情
     */
    Tag getTagById(Long id);

    /**
     * 创建新标签
     */
    void createTag(Tag tag);

    /**
     * 更新标签信息
     */
    void updateTag(Tag tag);

    /**
     * 删除标签
     */
    void deleteTag(Long id);

    /**
     * 获取标签下的文章数量
     */
    int getArticleCountByTag(Long id);
    
}
