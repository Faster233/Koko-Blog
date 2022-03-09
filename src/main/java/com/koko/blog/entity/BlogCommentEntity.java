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
@TableName("tb_blog_comment")
public class BlogCommentEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键id
	 */
	@TableId
	private Long commentId;
	/**
	 * 关联的blog主键
	 */
	private Long blogId;
	/**
	 * 评论者名称
	 */
	private String commentator;
	/**
	 * 评论人的邮箱
	 */
	private String email;
	/**
	 * 网址
	 */
	private String websiteUrl;
	/**
	 * 评论内容
	 */
	private String commentBody;
	/**
	 * 评论提交时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
	private Date commentCreateTime;
	/**
	 * 评论时的ip地址
	 */
	private String commentatorIp;
	/**
	 * 回复内容
	 */
	private String replyBody;
	/**
	 * 回复时间
	 */
	private Date replyCreateTime;
	/**
	 * 是否审核通过 0-未审核 1-审核通过
	 */
	private Integer commentStatus;
	/**
	 * 是否删除 0-未删除 1-已删除
	 */
	private Integer isDeleted;

}
