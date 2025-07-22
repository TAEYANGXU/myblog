package com.xyz.myblog.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.List;

@Data
public class ArticleDTO {
    private Long id;
    @NotBlank(message = "标题不能为空")
    private String title;
    @NotBlank(message = "内容不能为空")
    private String content;
    private String summary;
    private String status;
    private Long categoryId;
    private String cover;
    private List<Long> tagIds;
}
