package com.koko.blog.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

/**
 * 
 * 
 * @author Faster
 * @email Faster@shgogo.com
 * @date 2022-01-28 15:07:26
 */
@Data
@TableName("tb_blog_tag")
public class BlogTagEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 标签表主键id
	 */
	@TableId
	private Integer tagId;
	/**
	 * 标签名称
	 */
	private String tagName;
	/**
	 * 是否删除 0=否 1=是
	 */
	private Integer isDeleted;
	/**
	 * 创建时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GTM+8")
	private Date createTime;

}
