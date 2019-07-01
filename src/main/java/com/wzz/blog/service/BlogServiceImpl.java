package com.wzz.blog.service;

import com.wzz.blog.NotFoundException;
import com.wzz.blog.dao.BlogRepository;
import com.wzz.blog.pojo.Blog;
import com.wzz.blog.pojo.Type;
import com.wzz.blog.utils.MarkdownUtil;
import com.wzz.blog.utils.MyBeanUtil;
import com.wzz.blog.vo.BlogSearch;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.*;
import java.util.*;

/**
 * @author wzzap
 * @date 2019/6/24
 **/
@Service
@Transactional(rollbackFor = Exception.class)
public class BlogServiceImpl implements BlogService {

    @Autowired
    private BlogRepository blogRepository;

    @Override
    public Blog getBlog(Long id) {
        return blogRepository.findById(id).orElse(null);
    }

    @Override
    public Blog getAndConvert(Long id) {
        Blog blog = getBlog(id);
        if(blog == null){
            throw new NotFoundException("该博客不存在");
        }
        Blog b = new Blog();
        BeanUtils.copyProperties(blog, b);
        b.setContent(MarkdownUtil.markdownToHtmlExtensions(b.getContent()));
        blogRepository.updateViews(b.getId());
        return b;
    }

    @Override
    public Page<Blog> listBlogs(Pageable pageable, Long tagId) {
        // 关联查询
        return blogRepository.findAll(new Specification<Blog>() {
            @Override
            public Predicate toPredicate(Root<Blog> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                Join join = root.join("tags");
                return criteriaBuilder.equal(join.get("id"), tagId);
            }
        }, pageable);
    }

    @Override
    public Page<Blog> listBlogs(Pageable pageable, BlogSearch blog) {
        // 关联查询
        return blogRepository.findAll(new Specification<Blog>() {
            @Override
            public Predicate toPredicate(Root<Blog> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();
                // 添加查询条件
                if(StringUtils.isNotBlank(blog.getTitle())){
                    predicates.add(criteriaBuilder.like(root.<String>get("title"), "%" + blog.getTitle() + "%"));
                }
                if(blog.getTypeId() != null){
                    predicates.add(criteriaBuilder.equal(root.<Type>get("type").get("id"), blog.getTypeId()));
                }
                if(blog.isRecommend()){
                    predicates.add(criteriaBuilder.equal(root.<Boolean>get("canRecommend"), blog.isRecommend()));
                }
                // 将查询条件转换成数组
                criteriaQuery.where(predicates.toArray(new Predicate[predicates.size()]));
                return null;
            }
        }, pageable);
    }

    @Override
    public Page<Blog> listBlogs(Pageable pageable) {
        return blogRepository.findAll(pageable);
    }

    @Override
    public Page<Blog> listBlogs(Pageable pageable, String query) {
        return blogRepository.fingByQuery(query, pageable);
    }

    @Override
    public List<Blog> listRecommendBlogTop(Integer size) {
        Sort sort = new Sort(Sort.Direction.DESC, "updateTime");
        Pageable pageable = PageRequest.of(0, size, sort);
        return blogRepository.findBlogTop(pageable);
    }

    @Override
    public Map<String, List<Blog>> archiveBlogByYear() {
        List<String> years = blogRepository.findGroupYear();
        Map<String, List<Blog>> map = new HashMap<>();
        for(String year : years){
            map.put(year, blogRepository.findByYear(year));
        }
        return map;
    }

    @Override
    public Long countBlog() {
        return blogRepository.count();
    }

    @Override
    public Blog saveBlog(Blog blog) {
        if(blog.getId() == null){
            blog.setViews(0);
            blog.setCreateTime(new Date());
            blog.setUpdateTime(new Date());
        }else{
            blog.setUpdateTime(new Date());
        }
        return blogRepository.save(blog);
    }

    @Override
    public Blog updateBlog(Long id, Blog blog) {
        Blog b = getBlog(id);
        if(b == null){
            throw new NotFoundException();
        }
        // 更新时未传递的属性过滤掉
        BeanUtils.copyProperties(blog, b, MyBeanUtil.getNullPropertyNames(blog));
        b.setUpdateTime(new Date());
        return blogRepository.save(b);
    }

    @Override
    public void deleteBlog(Long id) {
        blogRepository.deleteById(id);
    }
}
