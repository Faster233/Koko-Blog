package com.koko.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.koko.blog.entity.BlogTagCount;
import com.koko.blog.entity.BlogTagEntity;
import com.koko.blog.utils.PageQueryUtil;
import com.koko.blog.utils.PageUtils;

import java.util.List;
import java.util.Map;

/**
 * 
 *
 * @author Faster
 * @email Faster@shgogo.com
 * @date 2022-01-28 15:07:26
 */
public interface BlogTagService extends IService<BlogTagEntity> {

    PageUtils queryPage(Map<String, Object> params);

    int getTotalTags();

    PageUtils queryTagPage(PageQueryUtil queryUtil);

    boolean saveTag(String tagName);

    boolean batchDelete(Integer[] ids);

    List<BlogTagCount> getBlogTagCountForIndex();
}

