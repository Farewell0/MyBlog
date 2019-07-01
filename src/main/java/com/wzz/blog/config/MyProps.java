package com.wzz.blog.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author wzzap
 * @date 2019/6/28
 **/
@Component
@ConfigurationProperties(prefix = "my-props")
public class MyProps {

    private String passwordSalt;
    private String commentAvatar;
    private String typeBlogCount;
    private String tagBlogCount;
    private String recommendBlogCount;

    public String getPasswordSalt() {
        return passwordSalt;
    }

    public void setPasswordSalt(String passwordSalt) {
        this.passwordSalt = passwordSalt;
    }

    public String getCommentAvatar() {
        return commentAvatar;
    }

    public void setCommentAvatar(String commentAvatar) {
        this.commentAvatar = commentAvatar;
    }

    public String getTypeBlogCount() {
        return typeBlogCount;
    }

    public void setTypeBlogCount(String typeBlogCount) {
        this.typeBlogCount = typeBlogCount;
    }

    public String getTagBlogCount() {
        return tagBlogCount;
    }

    public void setTagBlogCount(String tagBlogCount) {
        this.tagBlogCount = tagBlogCount;
    }

    public String getRecommendBlogCount() {
        return recommendBlogCount;
    }

    public void setRecommendBlogCount(String recommendBlogCount) {
        this.recommendBlogCount = recommendBlogCount;
    }
}
