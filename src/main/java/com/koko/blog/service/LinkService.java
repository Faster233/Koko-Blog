package com.koko.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.koko.blog.entity.LinkEntity;
import com.koko.blog.utils.PageQueryUtil;
import com.koko.blog.utils.PageUtils;
import com.koko.blog.utils.Result;

import java.util.List;
import java.util.Map;

/**
 * 
 *
 * @author Faster
 * @email Faster@shgogo.com
 * @date 2022-01-28 15:07:26
 */
public interface LinkService extends IService<LinkEntity> {

    PageUtils queryPage(Map<String, Object> params);

    int getTotalLinks();

    Result getBlogLinkPage(PageQueryUtil pageQueryUtil);

    Boolean saveLink(LinkEntity link);

    LinkEntity selectLinkById(Integer id);

    Boolean updateLinkById(LinkEntity link);

    boolean deleteBatch(Integer[] ids);

    /**
     * 获取友链页面信息
     * @return
     */
    Map<Integer, List<LinkEntity>> getLinkForLinkPage();
}

