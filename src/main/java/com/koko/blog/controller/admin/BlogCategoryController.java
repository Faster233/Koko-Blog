package com.koko.blog.controller.admin;

import java.util.Arrays;
import java.util.Map;


import com.koko.blog.utils.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import com.koko.blog.entity.BlogCategoryEntity;
import com.koko.blog.service.BlogCategoryService;

import javax.servlet.http.HttpServletRequest;


/**
 * 
 *
 * @author Faster
 * @email Faster@shgogo.com
 * @date 2022-01-28 15:07:26
 */
@Controller
@RequestMapping("/admin")
public class BlogCategoryController {
    @Autowired
    private BlogCategoryService blogCategoryService;

    @GetMapping("/categories")
    public String categories(HttpServletRequest request){
        request.setAttribute("path","categories");
        return "admin/category";
    }

    /**
     * 加载分类数据列表
     * @param param
     * @return
     */
    @GetMapping("/categories/list")
    @ResponseBody
    public Result list(@RequestParam Map<String,Object> param){
        if (StringUtils.isEmpty(param.get("limit")) || StringUtils.isEmpty(param.get("page"))){
            return ResultGenerator.genFailResult("参数异常");
        }
        PageQueryUtil queryUtil = new PageQueryUtil(param);
        PageUtils r=blogCategoryService.queryBlogCategoryPage(queryUtil);
        return ResultGenerator.genSuccessResult(r);
    }

    /**
     * 新增分类数据
     * @param categoryName
     * @param categoryIcon
     * @return
     */
    @PostMapping("/categories/save")
    @ResponseBody
    public Result save(@RequestParam("categoryName") String categoryName,
                       @RequestParam("categoryIcon") String categoryIcon){
        if (StringUtils.isEmpty(categoryName) || StringUtils.isEmpty(categoryIcon)){
            return ResultGenerator.genFailResult("参数异常");
        }
        if (blogCategoryService.saveBlogCategory(categoryName,categoryIcon)){
            return ResultGenerator.genSuccessResult();
        }else {
            return ResultGenerator.genFailResult("参数异常");
        }

    }
    @PostMapping("/categories/update")
    @ResponseBody
    public Result update(@RequestParam("categoryId") Integer categoryId,
                         @RequestParam("categoryName")String categoryName,
                         @RequestParam("categoryIcon")String categoryIcon){
        if (categoryId==null || StringUtils.isEmpty(categoryName)||StringUtils.isEmpty(categoryIcon)){
            return ResultGenerator.genFailResult("参数异常");
        }
        if (blogCategoryService.updateBlogCategory(categoryId,categoryName,categoryIcon)){
            return ResultGenerator.genSuccessResult();
        }else{
            return ResultGenerator.genFailResult("更新失败");
        }
    }

    @PostMapping("/categories/delete")
    @ResponseBody
    public Result delete(@RequestBody Integer[] ids){
        if (ids.length<1){
            return ResultGenerator.genFailResult("参数异常");
        }
        if (blogCategoryService.deleteBlogCategory(ids)){
            return ResultGenerator.genSuccessResult();
        }else{
            return ResultGenerator.genFailResult("删除失败");
        }

    }

}
