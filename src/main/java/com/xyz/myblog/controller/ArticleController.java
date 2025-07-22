package com.xyz.myblog.controller;

import com.github.pagehelper.PageInfo;
import com.xyz.myblog.dto.request.ArticleDTO;
import com.xyz.myblog.dto.response.ApiResponse;
import com.xyz.myblog.dto.response.Result;
import com.xyz.myblog.entity.Article;
import com.xyz.myblog.service.ArticleService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/admin/articles")
public class ArticleController {
    @Autowired
    private ArticleService articleService;

    @GetMapping("/list")
    public ApiResponse listArticles(@RequestParam(defaultValue = "1") int pageNum,
                               @RequestParam(defaultValue = "10") int pageSize,
                               @RequestParam(required = false) String title,
                               @RequestParam(required = false) Long categoryId,
                               @RequestParam(required = false) String status) {
        PageInfo<Article> pageInfo = articleService.getArticleList(pageNum, pageSize, title, categoryId, status);
        Map<String, Object> result = new HashMap<>();
        result.put("pageNum", pageInfo.getPageNum());
        result.put("pageSize", pageInfo.getPageSize());
        result.put("total", pageInfo.getTotal());
        result.put("list", pageInfo.getList());
        return ApiResponse.success(result);
    }

    @GetMapping("/{id}")
    public Result getArticle(@PathVariable Long id) {
        Article article = articleService.getArticleById(id);
        return Result.success(article);
    }

    @PostMapping
    public Result createArticle(@Valid @RequestBody ArticleDTO articleDTO) {
        articleService.saveArticle(articleDTO);
        return Result.success();
    }

    @PutMapping("/{id}")
    public Result updateArticle(@PathVariable Long id, @Valid @RequestBody ArticleDTO articleDTO) {
        articleDTO.setId(id);
        articleService.updateArticle(articleDTO);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result deleteArticle(@PathVariable Long id) {
        articleService.deleteArticle(id);
        return Result.success();
    }

    @PutMapping("/{id}/status")
    public Result updateStatus(@PathVariable Long id, @RequestParam String status) {
        articleService.updateArticleStatus(id, status);
        return Result.success();
    }
}