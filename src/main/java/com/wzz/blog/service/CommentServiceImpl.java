package com.wzz.blog.service;

import com.wzz.blog.dao.CommentRepository;
import com.wzz.blog.pojo.Comment;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author wzzap
 * @date 2019/6/28
 **/
@Service
@Transactional(rollbackFor = Exception.class)
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Override
    public List<Comment> listCommentByBlogId(Long blogId) {
        Sort sort = new Sort(Sort.Direction.ASC, "createTime");
        // 将所有的评论列表进行处理，结构化
        List<Comment> comments = commentRepository.findByBlogId(blogId, sort);
        comments.removeIf(comment -> comment.getParentComment() != null);
        return eachComment(comments);
    }

    /**
     * 循环遍历每个顶级的评论节点
     * @param comments
     * @return
     */
    private List<Comment> eachComment(List<Comment> comments){
        List<Comment> commentsView = new ArrayList<>();
        // 拷贝
        for(Comment comment: comments){
            Comment c = new Comment();
            BeanUtils.copyProperties(comment, c);
            commentsView.add(c);
        }
        // 合并子节点
        combineChildren(commentsView);
        return commentsView;
    }

    /**
     * 合并评论的各层子代到第一级子代集合中
     * @param comments 根节点,即没有父节点的评论
     */
    private void combineChildren(List<Comment> comments){
        for(Comment comment: comments){
            for(Comment c: comment.getReplyComments()){
                recursively(c);
            }
            comment.setReplyComments(tempReplyList);
            tempReplyList = new ArrayList<>();
        }
    }

    /**
     * 存放递归找出的所有子代集合
     */
    private List<Comment> tempReplyList = new ArrayList<>();

    private void recursively(Comment comment){
        tempReplyList.add(comment);
        if(comment.getReplyComments().size() > 0){
            for(Comment c: comment.getReplyComments()){
                recursively(c);
//                tempReplyList.add(c);
//                if(c.getReplyComments().size() > 0){
//                    recursively(c);
//                }
            }
        }
    }


    @Override
    public Comment saveComment(Comment comment) {
        Long parentCommentId = comment.getParentComment().getId();
        // 前端提交表单时默认设置的值是-1
        if(parentCommentId != -1){
            // 这里表示这条评论有父级评论
            comment.setParentComment(commentRepository.findById(parentCommentId).orElse(null));
        }else{
            // 这里表示这条评论没有父级评论
            comment.setParentComment(null);
        }
        comment.setCreateTime(new Date());
        return commentRepository.save(comment);
    }
}
