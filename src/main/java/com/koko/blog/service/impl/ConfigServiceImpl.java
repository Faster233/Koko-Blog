package com.koko.blog.service.impl;


import com.koko.blog.utils.PageUtils;
import com.koko.blog.utils.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.koko.blog.dao.ConfigDao;
import com.koko.blog.entity.ConfigEntity;
import com.koko.blog.service.ConfigService;

import javax.annotation.Resource;


@Service("configService")
public class ConfigServiceImpl extends ServiceImpl<ConfigDao, ConfigEntity> implements ConfigService {

    @Autowired
    private ConfigDao configDao;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<ConfigEntity> page = this.page(
                new Query<ConfigEntity>().getPage(params),
                new QueryWrapper<ConfigEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public Map<String, String> getAllConfigs() {

        List<ConfigEntity> Configs = configDao.selectList(new QueryWrapper<ConfigEntity>());
        Map<String, String> ConfigMap = Configs.stream().collect(Collectors.toMap(ConfigEntity::getConfigName, ConfigEntity::getConfigValue));
        return ConfigMap;
    }

    @Override
    public int updateConfig(String ConfigName, String ConfigValue) {
        ConfigEntity config=configDao.getConfigByName(ConfigName);
        if (config!=null){
            config.setConfigValue(ConfigValue);
            config.setUpdateTime(new Date());
            return configDao.updateConfigByPrimaryKey(config);
        }
        return 0;
    }

}