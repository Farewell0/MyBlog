package com.wzz.blog;

import com.wzz.blog.config.MyProps;
import com.wzz.blog.pojo.Comment;
import com.wzz.blog.service.CommentService;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @author wzzap
 * @date 2019/6/28
 **/
@RunWith(SpringRunner.class)
@SpringBootTest
public class Test {
    @Autowired
    private MyProps myProps;

    @Autowired
    private CommentService commentService;

    @org.junit.Test
    public void test1(){
        System.out.println(myProps.getPasswordSalt());
    }

    @org.junit.Test
    public void test2(){
        List<Comment> commentList = commentService.listCommentByBlogId((long) 15);
        for(Comment comment: commentList){
            System.out.print(comment.getId() + ": ");
            for(Comment c: comment.getReplyComments()){
                System.out.print(c.getId() + ", ");
            }
            System.out.println();
            System.out.println("=======================");
        }

    }
}
