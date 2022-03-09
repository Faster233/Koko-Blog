package com.koko.blog.dao;

import com.koko.blog.entity.LinkEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.koko.blog.utils.PageQueryUtil;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 
 * 
 * @author Faster
 * @email Faster@shgogo.com
 * @date 2022-01-28 15:07:26
 */
@Mapper
public interface LinkDao extends BaseMapper<LinkEntity> {

    int getTotalLinks();

    List<LinkEntity> getLinksByPQUtil(PageQueryUtil pageQueryUtil);

    int insertLink(LinkEntity link);

    Boolean updateLinkById(LinkEntity link);

    int deleteBatch(Integer[] ids);
}
