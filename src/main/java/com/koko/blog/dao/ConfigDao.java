package com.koko.blog.dao;

import com.koko.blog.entity.ConfigEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 
 * 
 * @author Faster
 * @email Faster@shgogo.com
 * @date 2022-01-28 15:07:26
 */
@Mapper
public interface ConfigDao extends BaseMapper<ConfigEntity> {

    ConfigEntity getConfigByName(@Param("configName") String configName);

    int updateConfigByPrimaryKey(ConfigEntity config);
}
