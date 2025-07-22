package com.xyz.myblog.service;

import com.github.pagehelper.PageInfo;
import com.xyz.myblog.dto.request.CategoryDTO;
import com.xyz.myblog.entity.Category;

import java.util.List;

public interface CategoryService {
    /**
     * 获取所有分类（带分页）
     */
    PageInfo<Category> getCategories(int pageNum, int pageSize);

    /**
     * 获取所有分类（不分页，用于下拉选择）
     */
    List<Category> getAllCategories();

    /**
     * 根据ID获取分类详情
     */
    Category getCategoryById(Long id);

    /**
     * 创建新分类
     */
    void createCategory(Category category);

    /**
     * 更新分类信息
     */
    void updateCategory(Category category);

    /**
     * 删除分类
     */
    void deleteCategory(Long id);

    /**
     * 获取分类下的文章数量
     */
    int getArticleCountByCategory(Long categoryId);
}