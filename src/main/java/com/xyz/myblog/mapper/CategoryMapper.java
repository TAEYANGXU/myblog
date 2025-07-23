package com.xyz.myblog.mapper;

import com.xyz.myblog.entity.Category;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CategoryMapper {
    int insertCategory(Category category);
    int deleteCategoryById(Long id);
    int updateCategory(Category category);
    List<Category> selectAllCategories(@Param("name") String name);
    Category selectCategoryById(Long id);
    Category selectCategoryByName(String name);
}