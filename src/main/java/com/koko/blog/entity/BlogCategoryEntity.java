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
@TableName("tb_blog_category")
public class BlogCategoryEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 分类表主键
	 */
	@TableId
	private Integer categoryId;
	/**
	 * 分类的名称
	 */
	private String categoryName;
	/**
	 * 分类的图标
	 */
	private String categoryIcon;
	/**
	 * 分类的排序值 被使用的越多数值越大
	 */
	private Integer categoryRank;
	/**
	 * 是否删除 0=否 1=是
	 */
	private Integer isDeleted;
	/**
	 * 创建时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
	private Date createTime;

}
