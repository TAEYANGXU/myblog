package com.xyz.myblog.entity;

import lombok.Data;
import java.util.Date;

@Data
public class Category {
    private Long id;
    private String name;
    private String description;
    private Date createTime;
}