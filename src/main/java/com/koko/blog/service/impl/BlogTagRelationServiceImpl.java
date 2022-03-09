package com.koko.blog.service.impl;

import com.koko.blog.utils.PageUtils;
import com.koko.blog.utils.Query;
import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.koko.blog.dao.BlogTagRelationDao;
import com.koko.blog.entity.BlogTagRelationEntity;
import com.koko.blog.service.BlogTagRelationService;


@Service("blogTagRelationService")
public class BlogTagRelationServiceImpl extends ServiceImpl<BlogTagRelationDao, BlogTagRelationEntity> implements BlogTagRelationService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<BlogTagRelationEntity> page = this.page(
                new Query<BlogTagRelationEntity>().getPage(params),
                new QueryWrapper<BlogTagRelationEntity>()
        );

        return new PageUtils(page);
    }

}