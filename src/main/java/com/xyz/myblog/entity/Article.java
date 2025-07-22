package com.xyz.myblog.entity;

import lombok.Data;
import java.util.Date;
import java.util.List;

@Data
public class Article {
    private Long id;
    private String title;
    private String content;
    private String summary;
    private String status; // "DRAFT" or "PUBLISHED"
    private Long categoryId;
    private String cover;
    private Integer viewCount;
    private Integer likeCount;
    private Date createTime;
    private Date updateTime;
    // 非数据库字段
    private String categoryName;
    private List<Tag> tags;
}