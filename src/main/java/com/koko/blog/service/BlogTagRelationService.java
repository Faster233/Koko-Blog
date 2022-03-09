package com.koko.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.koko.blog.entity.BlogTagRelationEntity;
import com.koko.blog.utils.PageUtils;

import java.util.Map;

/**
 * 
 *
 * @author Faster
 * @email Faster@shgogo.com
 * @date 2022-01-28 15:07:26
 */
public interface BlogTagRelationService extends IService<BlogTagRelationEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

