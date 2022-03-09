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
 * @date 2022-01-28 15:07:27
 */
@Data
@TableName("tb_blog")
public class BlogEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 博客表主键id
	 */
	@TableId
	private Long blogId;
	/**
	 * 博客标题
	 */
	private String blogTitle;
	/**
	 * 博客自定义路径url
	 */
	private String blogSubUrl;
	/**
	 * 博客封面图
	 */
	private String blogCoverImage;
	/**
	 * 博客内容
	 */
	private String blogContent;
	/**
	 * 博客分类id
	 */
	private Integer blogCategoryId;
	/**
	 * 博客分类(冗余字段)
	 */
	private String blogCategoryName;
	/**
	 * 博客标签
	 */
	private String blogTags;
	/**
	 * 0-草稿 1-发布
	 */
	private Integer blogStatus;
	/**
	 * 阅读量
	 */
	private Long blogViews;
	/**
	 * 0-允许评论 1-不允许评论
	 */
	private Integer enableComment;
	/**
	 * 是否删除 0=否 1=是
	 */
	private Integer isDeleted;
	/**
	 * 添加时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
	private Date createTime;
	/**
	 * 修改时间
	 */
	private Date updateTime;

}
