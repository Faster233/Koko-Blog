package com.koko.blog.dao;

import com.koko.blog.entity.BlogCommentEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.koko.blog.utils.PageQueryUtil;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author Faster
 * @email Faster@shgogo.com
 * @date 2022-01-28 15:07:26
 */
@Mapper
public interface BlogCommentDao extends BaseMapper<BlogCommentEntity> {

    int getTotalComments();

    List<BlogCommentEntity> getCommentList(PageQueryUtil queryUtil);

    int checkDone(@Param("ids") Integer[] ids);

    int reply(BlogCommentEntity blogComment);

    int deleteBatch(@Param("ids") Integer[] ids);

    int getTotalBlogComments(Map<String, Object> params);

    List<BlogCommentEntity> queryCommentPage(PageQueryUtil queryUtil);

    int insertBlogComment(BlogCommentEntity blogComment);
}
