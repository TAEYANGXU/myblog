package com.xyz.myblog.controller;

import com.github.pagehelper.PageInfo;
import com.xyz.myblog.dto.request.ArticleDTO;
import com.xyz.myblog.dto.response.ApiResponse;
import com.xyz.myblog.entity.Article;
import com.xyz.myblog.service.ArticleService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/admin/articles")
public class ArticleController {
    @Autowired
    private ArticleService articleService;

    @GetMapping("/list")
    public ApiResponse<Map<String, Object>> listArticles(@RequestParam(defaultValue = "1") int pageNum,
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
    public ApiResponse<Article> getArticle(@PathVariable Long id) {
        Article article = articleService.getArticleById(id);
        return ApiResponse.success(article);
    }

    @PostMapping
    public ApiResponse<String> createArticle(@Valid @RequestBody ArticleDTO articleDTO) {
        articleService.saveArticle(articleDTO);
        return ApiResponse.success("");
    }

    @PutMapping("/update")
    public ApiResponse<String> updateArticle(@Valid @RequestBody ArticleDTO articleDTO) {
//        articleDTO.setId(id);
        articleService.updateArticle(articleDTO);
        return ApiResponse.success("");
    }

    @DeleteMapping("/{id}")
    public ApiResponse<String> deleteArticle(@PathVariable Long id) {
        articleService.deleteArticle(id);
        return ApiResponse.success("");
    }

    @PutMapping("/{id}/status")
    public ApiResponse<String> updateStatus(@PathVariable Long id, @RequestParam String status) {
        articleService.updateArticleStatus(id, status);
        return ApiResponse.success("");
    }
}