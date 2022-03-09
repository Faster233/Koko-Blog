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
 * @date 2022-01-28 15:07:26
 */
@Data
@TableName("tb_blog_tag_relation")
public class BlogTagRelationEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 关系表id
	 */
	@TableId
	private Long relationId;
	/**
	 * 博客id
	 */
	private Long blogId;
	/**
	 * 标签id
	 */
	private Integer tagId;
	/**
	 * 添加时间
	 */
	private Date createTime;

}
