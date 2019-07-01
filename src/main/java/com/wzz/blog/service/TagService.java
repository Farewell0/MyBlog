package com.wzz.blog.service;

import com.wzz.blog.pojo.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @author wzzap
 * @date 2019/6/23
 **/
public interface TagService {

    Tag getTag(Long id);

    Page<Tag> listTag(Pageable pageable);

    Tag saveTag(Tag tag);

    Tag updateTag(Long id, Tag tag);

    void removeTag(Long id);

    Tag getTagByName(String name);

    List<Tag> listTag();

    List<Tag> listTag(String ids);

    List<Tag> listTagTop(Integer size);
}
