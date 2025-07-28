package com.xyz.myblog.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xyz.myblog.controller.AuthController;
import com.xyz.myblog.entity.Category;
import com.xyz.myblog.entity.Tag;
import com.xyz.myblog.mapper.TagMapper;
import com.xyz.myblog.service.TagService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TagServiceImpl implements TagService {

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    private TagMapper tagMapper;

    @Override
    public PageInfo<Tag> getTags(int pageNum, int pageSize, String name) {

        logger.info("收到登录请求: pageNum={}, pageSize={}", pageNum, pageSize);
        PageHelper.startPage(pageNum, pageSize);
        List<Tag> tags = tagMapper.selectAllTags(name);
        logger.info("tags ------------ : tags={}", tags);
//        Tag tag = new Tag();
//        tag.setName("name");
//        List<Tag> tags1 = new ArrayList<>();
//        tags.add(tag);
        return new PageInfo<>(tags);
    }

    @Override
    public List<Tag> getAllTags() {
        return List.of();
    }

    @Override
    public Tag getTagById(Long id) {
        return null;
    }

    @Override
    public void createTag(Tag tag) {

    }

    @Override
    public void updateTag(Tag tag) {

    }

    @Override
    public void deleteTag(Long id) {

    }

    @Override
    public int getArticleCountByTag(Long id) {
        return 0;
    }
}
