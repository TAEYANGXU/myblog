package com.xyz.myblog.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xyz.myblog.dto.request.CategoryDTO;
import com.xyz.myblog.entity.Article;
import com.xyz.myblog.entity.Category;
import com.xyz.myblog.mapper.CategoryMapper;
import com.xyz.myblog.service.CategoryService;
import io.micrometer.common.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public PageInfo<Category> getCategories(int pageNum, int pageSize,String name) {
        PageHelper.startPage(pageNum, pageSize);
        List<Category> categories = categoryMapper.selectAllCategories(name);
        return new PageInfo<>(categories);
    }

    @Override
    public List<Category> getAllCategories() {
        return categoryMapper.selectAllCategories("");
    }

    @Override
    public Category getCategoryById(Long id) {
        return categoryMapper.selectCategoryById(id);
    }

    
    @Override
    public void createCategory(Category category) {
        if (categoryMapper.selectCategoryByName(category.getName()) != null) {
            throw new RuntimeException("分类名称已存在");
        }
        categoryMapper.insertCategory(category);
    }

    @Override
    public void updateCategory(Category category) {
        Category existing = categoryMapper.selectCategoryByName(category.getName());
        if (existing != null && !existing.getId().equals(category.getId())) {
            throw new RuntimeException("分类名称已存在");
        }
        categoryMapper.updateCategory(category);
    }

    @Override
    public void deleteCategory(Long id) {
        // 检查是否有文章关联
        Category category = categoryMapper.selectCategoryById(id);
        if (category !=  null) {
            throw new RuntimeException("该分类下有文章，不能删除");
        }
        categoryMapper.deleteCategoryById(id);
    }

    @Override
    public int getArticleCountByCategory(Long categoryId) {
        return 0;
    }
}