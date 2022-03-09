package com.koko.blog.dao;

import com.koko.blog.entity.AdminUserEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 
 * 
 * @author Faster
 * @email Faster@shgogo.com
 * @date 2022-01-28 15:07:27
 */
@Mapper
public interface AdminUserDao extends BaseMapper<AdminUserEntity> {
    AdminUserEntity login(@Param("username") String username,@Param("password") String password);

    AdminUserEntity getAdminUserById(@Param("userid")int loginUserId);

    int updateAdminUserPasswordById(@Param("userid") int loginUserId,@Param("newPassword") String newPassword);

    int updateUserByPrimaryKey(AdminUserEntity adminUser);
}
