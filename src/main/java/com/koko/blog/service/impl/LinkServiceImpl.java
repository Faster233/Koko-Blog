package com.koko.blog.service.impl;

import com.koko.blog.utils.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;


import com.koko.blog.dao.LinkDao;
import com.koko.blog.entity.LinkEntity;
import com.koko.blog.service.LinkService;


@Service("linkService")
public class LinkServiceImpl extends ServiceImpl<LinkDao, LinkEntity> implements LinkService {

    @Autowired
    private LinkDao linkDao;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<LinkEntity> page;
        page = this.page(
                new Query<LinkEntity>().getPage(params),
                new QueryWrapper<LinkEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public int getTotalLinks() {
        return linkDao.getTotalLinks();
    }



    @Override
    public Result getBlogLinkPage(PageQueryUtil pageQueryUtil) {
        //获取link分页数据集合
        List<LinkEntity> links= linkDao.getLinksByPQUtil(pageQueryUtil);
        //获取总记录数
        int totalLinks = linkDao.getTotalLinks();
        //设置分页数据
        PageUtils pageUtils = new PageUtils(links, totalLinks, pageQueryUtil.getLimit(), pageQueryUtil.getPage());
        return ResultGenerator.genSuccessResult(pageUtils);

    }

    @Override
    public Boolean saveLink(LinkEntity link) {
        return linkDao.insertLink(link)>0;
    }

    @Override
    public LinkEntity selectLinkById(Integer id) {
        return linkDao.selectById(id);
    }

    @Override
    public Boolean updateLinkById(LinkEntity link) {
        return linkDao.updateLinkById(link);
    }

    @Override
    public boolean deleteBatch(Integer[] ids) {
        return linkDao.deleteBatch(ids)>0;
    }

    @Override
    public Map<Integer, List<LinkEntity>> getLinkForLinkPage() {
        List<LinkEntity> links = linkDao.getLinksByPQUtil(null);
        Map<Integer, List<LinkEntity>> linkMap = links.stream().collect(Collectors.groupingBy(LinkEntity::getLinkType));

        return linkMap;
    }

}