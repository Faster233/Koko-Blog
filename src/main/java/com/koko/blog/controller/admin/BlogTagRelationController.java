package com.koko.blog.controller.admin;

import java.util.Arrays;
import java.util.Map;


import com.koko.blog.utils.PageUtils;
import com.koko.blog.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.koko.blog.entity.BlogTagRelationEntity;
import com.koko.blog.service.BlogTagRelationService;



/**
 * 
 *
 * @author Faster
 * @email Faster@shgogo.com
 * @date 2022-01-28 15:07:26
 */
@RestController
@RequestMapping("blog/blogtagrelation")
public class BlogTagRelationController {
    @Autowired
    private BlogTagRelationService blogTagRelationService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("blog:blogtagrelation:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = blogTagRelationService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{relationId}")
    //@RequiresPermissions("blog:blogtagrelation:info")
    public R info(@PathVariable("relationId") Long relationId){
		BlogTagRelationEntity blogTagRelation = blogTagRelationService.getById(relationId);

        return R.ok().put("blogTagRelation", blogTagRelation);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("blog:blogtagrelation:save")
    public R save(@RequestBody BlogTagRelationEntity blogTagRelation){
		blogTagRelationService.save(blogTagRelation);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("blog:blogtagrelation:update")
    public R update(@RequestBody BlogTagRelationEntity blogTagRelation){
		blogTagRelationService.updateById(blogTagRelation);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("blog:blogtagrelation:delete")
    public R delete(@RequestBody Long[] relationIds){
		blogTagRelationService.removeByIds(Arrays.asList(relationIds));

        return R.ok();
    }

}
