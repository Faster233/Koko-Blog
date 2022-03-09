package com.koko.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.koko.blog.entity.BlogCommentEntity;
import com.koko.blog.utils.PageQueryUtil;
import com.koko.blog.utils.PageUtils;
import com.koko.blog.utils.Result;

import java.util.Map;

/**
 * 
 *
 * @author Faster
 * @email Faster@shgogo.com
 * @date 2022-01-28 15:07:26
 */
public interface BlogCommentService extends IService<BlogCommentEntity> {

    PageUtils queryPage(Map<String, Object> params);

    int getTotalComments();

    PageUtils queryCommentPage(PageQueryUtil queryUtil);

    boolean checkDone(Integer[] ids);

    boolean reply(Integer commentId, String replyBody);

    boolean deleteBatch(Integer[] ids);

    PageUtils getCommentPageByBlogIdAndPageNum(Long blogId, Integer commentPage);

    boolean saveBlogComment(BlogCommentEntity blogComment);
}

