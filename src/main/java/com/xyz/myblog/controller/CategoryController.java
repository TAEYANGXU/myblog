package com.xyz.myblog.controller;

import com.xyz.myblog.dto.request.CategoryDTO;
import com.xyz.myblog.dto.response.Result;
import com.xyz.myblog.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/categories")
public class CategoryController {

    private final CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public Result list(@RequestParam(defaultValue = "1") int pageNum,
                       @RequestParam(defaultValue = "10") int pageSize) {
        return Result.success(categoryService.getCategories(pageNum, pageSize));
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Long id) {
        categoryService.deleteCategory(id);
        return Result.success();
    }
}