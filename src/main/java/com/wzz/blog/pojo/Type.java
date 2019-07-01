package com.wzz.blog.pojo;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

/**
 * @author wzzap
 * @date 2019/6/21
 **/
@Entity
@Table(name = "t_type")
public class Type {

    @Id
    @GeneratedValue
    private Long id;

    @NotBlank(message = "分类名称不能为空")
    private String name;

    @OneToMany(mappedBy = "type")   // 被维护的关系，由Blog中的type进行维护
    private List<Blog> blogs = new ArrayList<>();   // 一个分类可能有多篇博客

    public Type() {
    }

    @Override
    public String toString() {
        return "Type{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Blog> getBlogs() {
        return blogs;
    }

    public void setBlogs(List<Blog> blogs) {
        this.blogs = blogs;
    }
}
