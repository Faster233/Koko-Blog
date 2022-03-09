package com.koko.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.koko.blog.entity.ConfigEntity;
import com.koko.blog.utils.PageUtils;

import java.util.Map;

/**
 * 
 *
 * @author Faster
 * @email Faster@shgogo.com
 * @date 2022-01-28 15:07:26
 */
public interface ConfigService extends IService<ConfigEntity> {

    PageUtils queryPage(Map<String, Object> params);

    Map<String,String> getAllConfigs();

    int updateConfig(String ConfigName, String ConfigValue);
}

