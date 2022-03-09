package com.koko.blog.service.impl;

import com.koko.blog.dao.BlogTagRelationDao;
import com.koko.blog.entity.BlogTagCount;
import com.koko.blog.utils.PageQueryUtil;
import com.koko.blog.utils.PageUtils;
import com.koko.blog.utils.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.koko.blog.dao.BlogTagDao;
import com.koko.blog.entity.BlogTagEntity;
import com.koko.blog.service.BlogTagService;
import org.springframework.util.CollectionUtils;


@Service("blogTagService")
public class BlogTagServiceImpl extends ServiceImpl<BlogTagDao, BlogTagEntity> implements BlogTagService {

    @Autowired
    private BlogTagDao blogTagDao;

    @Autowired
    private BlogTagRelationDao relationDao;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<BlogTagEntity> page = this.page(
                new Query<BlogTagEntity>().getPage(params),
                new QueryWrapper<BlogTagEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public int getTotalTags() {
        return blogTagDao.getTotalTags();
    }

    @Override
    public PageUtils queryTagPage(PageQueryUtil queryUtil) {
        List<BlogTagEntity> tags = blogTagDao.selectTagList(queryUtil);
        int totalTags = blogTagDao.getTotalTags();
        PageUtils pageUtils = new PageUtils(tags,totalTags,queryUtil.getLimit(),queryUtil.getPage());
        return pageUtils;
    }

    @Override
    public boolean saveTag(String tagName) {
       BlogTagEntity tag=blogTagDao.selectTagByName(tagName);
        if(tag==null){
            BlogTagEntity newtag = new BlogTagEntity();
            newtag.setTagName(tagName);
            return blogTagDao.insert(newtag)>0;
        }else{
            return false;
        }

    }

    @Override
    public boolean batchDelete(Integer[] ids) {
        //查询数组里面的id是否有关联数据
        List<Long> list=relationDao.selectDistinctTagIds(ids);
        if (CollectionUtils.isEmpty(list)){
            return blogTagDao.batchDelete(ids)>0;
        }
        return false;
    }

    @Override
    public List<BlogTagCount> getBlogTagCountForIndex() {
        return blogTagDao.gethotTotalTags();
    }

}