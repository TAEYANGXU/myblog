package com.xyz.myblog.mapper;

import com.xyz.myblog.entity.Category;
import org.apache.ibatis.annotations.*;
import java.util.List;

@Mapper
public interface CategoryMapper {

    // 新增分类
    @Insert("INSERT INTO category(name, description) VALUES(#{name}, #{description})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insertCategory(Category category);

    // 根据ID删除分类
    @Delete("DELETE FROM category WHERE id = #{id}")
    int deleteCategoryById(Long id);

    // 更新分类
    @Update("UPDATE category SET name=#{name}, description=#{description} WHERE id=#{id}")
    int updateCategory(Category category);

    // 查询所有分类
    @Select("SELECT * FROM category ORDER BY create_time DESC")
    List<Category> selectAllCategories();

    // 根据ID查询分类
    @Select("SELECT * FROM category WHERE id = #{id}")
    Category selectCategoryById(Long id);

    // 根据名称查询分类
    @Select("SELECT * FROM category WHERE name = #{name}")
    Category selectCategoryByName(String name);
}