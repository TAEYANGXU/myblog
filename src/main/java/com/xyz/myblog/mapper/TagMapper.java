package com.xyz.myblog.mapper;

import com.xyz.myblog.entity.Tag;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface TagMapper {
    int insertTag(Tag tag);
    int deleteTagById(Long id);
    int updateTag(Tag tag);
    List<Tag> selectAllTags(String name);  // 如果需要按名称过滤
    List<Tag> selectAllTags();             // 如果不需要参数
    Tag selectTagById(Long id);
    Tag selectTagByName(String name);
}