package com.koko.blog.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 
 * 
 * @author Faster
 * @email Faster@shgogo.com
 * @date 2022-01-28 15:07:27
 */
@Data
@TableName("tb_admin_user")
public class AdminUserEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 管理员id
	 */
	@TableId
	private Integer adminUserId;
	/**
	 * 管理员登陆名称
	 */
	private String loginUserName;
	/**
	 * 管理员登陆密码
	 */
	private String loginPassword;
	/**
	 * 管理员显示昵称
	 */
	private String nickName;
	/**
	 * 是否锁定 0未锁定 1已锁定无法登陆
	 */
	private Integer locked;

}
