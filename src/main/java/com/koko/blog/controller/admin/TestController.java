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

import com.koko.blog.entity.TestEntity;
import com.koko.blog.service.TestService;


/**
 * 
 *
 * @author Faster
 * @email Faster@shgogo.com
 * @date 2022-01-28 15:07:26
 */
@RestController
@RequestMapping("blog/test")
public class TestController {
    @Autowired
    private TestService testService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("blog:test:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = testService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    //@RequiresPermissions("blog:test:info")
    public R info(@PathVariable("id") Long id){
		TestEntity test = testService.getById(id);

        return R.ok().put("test", test);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("blog:test:save")
    public R save(@RequestBody TestEntity test){
		testService.save(test);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("blog:test:update")
    public R update(@RequestBody TestEntity test){
		testService.updateById(test);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("blog:test:delete")
    public R delete(@RequestBody Long[] ids){
		testService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
