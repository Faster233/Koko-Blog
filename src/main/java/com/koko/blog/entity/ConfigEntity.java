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
@TableName("tb_config")
public class ConfigEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 配置项的名称
	 */
	@TableId
	private String configName;
	/**
	 * 配置项的值
	 */
	private String configValue;
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 修改时间
	 */
	private Date updateTime;

}
