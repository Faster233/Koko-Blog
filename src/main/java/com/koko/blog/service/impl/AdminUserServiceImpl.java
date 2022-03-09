package com.koko.blog.service.impl;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.koko.blog.utils.MD5Util;
import com.koko.blog.utils.PageUtils;
import com.koko.blog.utils.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;


import com.koko.blog.dao.AdminUserDao;
import com.koko.blog.entity.AdminUserEntity;
import com.koko.blog.service.AdminUserService;

import javax.annotation.Resource;


@Service("adminUserService")
public class AdminUserServiceImpl extends ServiceImpl<AdminUserDao, AdminUserEntity> implements AdminUserService {

    @Autowired
    private AdminUserDao adminUserDao;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<AdminUserEntity> page = this.page(
                new Query<AdminUserEntity>().getPage(params),
                new QueryWrapper<AdminUserEntity>()
        );

        return new PageUtils(page);
    }

    /**
     * 登录方法实现
     * @param userName
     * @param password
     * @return
     */
    @Override
    public AdminUserEntity login(String userName, String password) {
        String md5password = MD5Util.MD5Encode(password, "UTF-8");
        return adminUserDao.login(userName,md5password);
    }

    @Override
    public AdminUserEntity getAdminUserById(int loginUserId) {
        return adminUserDao.getAdminUserById(loginUserId);
    }

    @Override
    public Boolean updateAdminUserPassword(String originalPassword, String newPassword, int loginUserId) {

        AdminUserEntity adminUser = adminUserDao.getAdminUserById(loginUserId);
        //修改用户必须存在
        if (adminUser !=null){
            String md5originalPassword = MD5Util.MD5Encode(originalPassword, "UTF-8");
            String md5newPassword = MD5Util.MD5Encode(newPassword, "UTF-8");
            //确认原密码是否正确
            if (md5originalPassword.equals(adminUser.getLoginPassword())){
                //通过用户id修改密码
                adminUser.setLoginPassword(md5newPassword);
                if(adminUserDao.updateUserByPrimaryKey(adminUser)>0){
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public Boolean updateAdminUserName(String loginUserName, String nickName, int loginUserId) {
        AdminUserEntity adminUser = adminUserDao.getAdminUserById(loginUserId);
        //修改用户必须存在
        if (adminUser !=null){
            adminUser.setLoginUserName(loginUserName);
            adminUser.setNickName(nickName);
            if (adminUserDao.updateUserByPrimaryKey(adminUser)>0){
                return true;
            }
        }
        return false;
    }
}