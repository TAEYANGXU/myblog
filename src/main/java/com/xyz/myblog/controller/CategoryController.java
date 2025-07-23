package com.xyz.myblog.controller;

import com.github.pagehelper.PageInfo;
import com.xyz.myblog.dto.response.ApiResponse;
import com.xyz.myblog.entity.Article;
import com.xyz.myblog.entity.Category;
import com.xyz.myblog.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/admin/categories")
public class CategoryController {

    private final CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/list")
    public ApiResponse<Map<String, Object>> list(@RequestParam(defaultValue = "1") int pageNum,
                            @RequestParam(defaultValue = "10") int pageSize,
                                                 @RequestParam(required = false) String name) {
        PageInfo<Category> pageInfo = categoryService.getCategories(pageNum, pageSize,name);
        Map<String, Object> result = new HashMap<>();
        result.put("pageNum", pageInfo.getPageNum());
        result.put("pageSize", pageInfo.getPageSize());
        result.put("total", pageInfo.getTotal());
        result.put("list", pageInfo.getList());
        return ApiResponse.success(result);
    }

    @DeleteMapping("/{id}")
    public ApiResponse<String> delete(@PathVariable Long id) {
        categoryService.deleteCategory(id);
        return ApiResponse.success("");
    }
}