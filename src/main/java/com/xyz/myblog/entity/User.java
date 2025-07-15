package com.xyz.myblog.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class User {
    private Long id;
    private String username;
    private String password;
    private String role;
    private String avatar;
    private LocalDateTime create_time;
}
