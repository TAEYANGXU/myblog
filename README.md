# myblog
个人博客后端服务 (Spring Boot)

## 功能模块

| 功能模块   | 功能描述                                   |
| ---------- | ------------------------------------------ |
| 用户登录   | JWT + Spring Security 鉴权                |
| 仪表盘     | 显示文章数量、用户数、阅读量等统计图        |
| 文章管理   | 新增 / 编辑 / 删除 / 发布 / 草稿管理        |
| 分类管理   | 增删改查分类数据                           |
| 标签管理   | 增删改查标签数据                           |
| 评论管理   | 评论审核 / 删除 / 屏蔽等操作                |
| 留言管理   | 查看 & 删除访客留言                        |
| 用户管理   | 支持多管理员用户，角色分配                  |
| 系统设置   | 设置网站标题 / 关于我内容等配置项           |

## 技术栈
- Spring Boot
- Spring Security
- JWT
- MySQL
- JPA / MyBatis

## 快速开始
1. 克隆项目：
   ```bash
   git clone <仓库地址>
   ```
2. 配置数据库连接，修改 `src/main/resources/application.properties`。
3. 启动项目：
   ```bash
   ./gradlew bootRun
   ```

## 目录结构
- `src/main/java/com/xyz/myblog/`  核心后端代码
- `src/main/resources/`             配置文件及静态资源
- `build.gradle`                    构建配置

## 许可证
MIT License
