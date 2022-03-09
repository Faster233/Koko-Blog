package com.koko.blog.controller.admin;

import java.util.Arrays;
import java.util.Map;


import com.koko.blog.entity.BlogTagEntity;
import com.koko.blog.utils.*;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import com.koko.blog.service.BlogTagService;

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
public class BlogTagController {
    @Autowired
    private BlogTagService blogTagService;

    /**
     * 标签页面跳转
     * @param request
     * @return
     */
    @GetMapping("/tags")
    public String tags(HttpServletRequest request){
        request.setAttribute("path","tags");
        return "admin/tag";
    }

    /**
     * 页面数据查询
     * @param param
     * @return
     */
    @GetMapping("/tags/list")
    @ResponseBody
    public Result list(@RequestParam Map<String,Object> param){
        if (StringUtils.isEmpty(param.get("page")) || StringUtils.isEmpty(param.get("limit"))){
            return ResultGenerator.genFailResult("参数异常!");
        }
        PageQueryUtil queryUtil = new PageQueryUtil(param);
        //return ResultGenerator.genSuccessResult(blogTagService.queryPage(param));
        return ResultGenerator.genSuccessResult(blogTagService.queryTagPage(queryUtil));
    }

    @PostMapping("/tags/save")
    @ResponseBody
    public Result save(@RequestParam("tagName") String tagName){
        if (StringUtils.isEmpty(tagName)){
            return ResultGenerator.genFailResult("参数异常!");
        }
        if (blogTagService.saveTag(tagName)){
            return ResultGenerator.genSuccessResult();
        }else{
            return ResultGenerator.genFailResult("标签名称重复");
        }

    }
    @PostMapping("/tags/delete")
    @ResponseBody
    public Result delete(@RequestBody Integer[] ids){

        if (ids.length<1){
            return ResultGenerator.genFailResult("参数异常!");
        }

        if (blogTagService.batchDelete(ids)){
            return ResultGenerator.genSuccessResult();
        }else{
            return ResultGenerator.genFailResult("有关联数据请勿强行删除");
        }
    }


}
