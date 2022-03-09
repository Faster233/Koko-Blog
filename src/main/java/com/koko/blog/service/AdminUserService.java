package com.koko.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;

import com.koko.blog.entity.AdminUserEntity;
import com.koko.blog.utils.PageUtils;

import java.util.Map;

/**
 * 
 *
 * @author Faster
 * @email Faster@shgogo.com
 * @date 2022-01-28 15:07:27
 */
public interface AdminUserService extends IService<AdminUserEntity> {

    PageUtils queryPage(Map<String, Object> params);

    /**
     * 登录方法
     * @param userName
     * @param password
     * @return
     */
    AdminUserEntity login(String userName, String password);

    /**
     * 根据用户id获取用户对象
     * @param loginUserId
     * @return
     */
    AdminUserEntity getAdminUserById(int loginUserId);

    /**
     * 修改用户密码
     * @param originalPassword
     * @param newPassword
     * @param loginUserId
     * @return
     */
    Boolean updateAdminUserPassword(String originalPassword, String newPassword, int loginUserId);

    Boolean updateAdminUserName(String loginUserName, String nickName, int loginUserId);
}

