package com.koko.blog.entity;

import com.baomidou.mybatisplus.annotation.IdType;
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
@TableName("tb_link")
public class LinkEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 友链表主键id
	 */
	@TableId(type = IdType.AUTO)
	private Integer linkId;
	/**
	 * 友链类别 0-友链 1-推荐 2-个人网站
	 */
	private Integer linkType;
	/**
	 * 网站名称
	 */
	private String linkName;
	/**
	 * 网站链接
	 */
	private String linkUrl;
	/**
	 * 网站描述
	 */
	private String linkDescription;
	/**
	 * 用于列表排序
	 */
	private Integer linkRank;
	/**
	 * 是否删除 0-未删除 1-已删除
	 */

	private Integer isDeleted;
	/**
	 * 添加时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
	private Date createTime;

}
