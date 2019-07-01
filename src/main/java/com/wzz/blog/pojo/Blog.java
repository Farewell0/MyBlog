package com.wzz.blog.pojo;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author wzzap
 * @date 2019/6/21
 **/
@Entity
@Table(name = "t_blog")
public class Blog {

    @Id
    @GeneratedValue
    private Long id;

    private String title;
    /**
     * 大字段类型的内容，而且是当使用的时候加载
     */
    @Lob
    @Basic(fetch = FetchType.LAZY)
    private String content;

    private String firstPicture;    // 首图
    private String flag;            //
    private Integer views;          // 浏览次数
    private Boolean canAppreciate;  // 赞赏是否开启
    private Boolean canShare;       // 转载声明是否开启
    private Boolean canComment;     // 评论是否开启
    private Boolean published;      // 发布
    private Boolean canRecommend;   // 是否推荐

    // 博客描述
    private String description;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateTime;

    @ManyToOne
    private Type type;          // 多个博客对应一种分类

    @ManyToMany(cascade = {CascadeType.PERSIST})    // 级联新增，新增一个tag同时保存到数据库
    private List<Tag> tags = new ArrayList<>();     // 一个博客多个标签

    @ManyToOne
    private User user;

    @OneToMany(mappedBy = "blog")
    private List<Comment> comments = new ArrayList<>();

    @Transient
    private String tagIds;

    public String getTagIds() {
        return tagIds;
    }

    public void setTagIds(String tagIds) {
        this.tagIds = tagIds;
    }

    public Blog() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getFirstPicture() {
        return firstPicture;
    }

    public void setFirstPicture(String firstPicture) {
        this.firstPicture = firstPicture;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public Integer getViews() {
        return views;
    }

    public void setViews(Integer views) {
        this.views = views;
    }

    public Boolean getCanAppreciate() {
        return canAppreciate;
    }

    public void setCanAppreciate(Boolean canAppreciate) {
        this.canAppreciate = canAppreciate;
    }

    public Boolean getCanShare() {
        return canShare;
    }

    public void setCanShare(Boolean canShare) {
        this.canShare = canShare;
    }

    public Boolean getCanComment() {
        return canComment;
    }

    public void setCanComment(Boolean canComment) {
        this.canComment = canComment;
    }

    public Boolean getPublished() {
        return published;
    }

    public void setPublished(Boolean published) {
        this.published = published;
    }

    public Boolean getCanRecommend() {
        return canRecommend;
    }

    public void setCanRecommend(Boolean canRecommend) {
        this.canRecommend = canRecommend;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        return "Blog{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", firstPicture='" + firstPicture + '\'' +
                ", flag='" + flag + '\'' +
                ", views=" + views +
                ", canAppreciate=" + canAppreciate +
                ", canShare=" + canShare +
                ", canComment=" + canComment +
                ", published=" + published +
                ", canRecommend=" + canRecommend +
                ", description='" + description + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", type=" + type +
                ", tags=" + tags +
                ", user=" + user +
                ", comments=" + comments +
                ", tagIds='" + tagIds + '\'' +
                '}';
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public void init(){
        this.tagIds = tags2Ids(this.tags);
    }

    private String tags2Ids(List<Tag> tags){
        if(!tags.isEmpty()){
            StringBuffer stringBuffer = new StringBuffer();
            for(Tag tag : tags){
                stringBuffer.append(tag.getId()).append(",");
            }
            return stringBuffer.toString().substring(0, stringBuffer.length() - 1);
        }else{
            return tagIds;
        }
    }

}
