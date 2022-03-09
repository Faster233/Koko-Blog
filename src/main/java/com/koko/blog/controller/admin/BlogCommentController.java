package com.koko.blog.controller.admin;

import java.util.Arrays;
import java.util.Map;


import com.koko.blog.utils.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import com.koko.blog.entity.BlogCommentEntity;
import com.koko.blog.service.BlogCommentService;

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
public class BlogCommentController {
    @Autowired
    private BlogCommentService blogCommentService;

    @GetMapping("/comments")
    public String comments(HttpServletRequest request){
        request.setAttribute("path","comments");
        return "admin/comment";
    }

    /**
     * 页面数据查询
     * @param param
     * @return
     */
    @GetMapping("/comments/list")
    @ResponseBody
    public Result list(@RequestParam Map<String,Object> param){
        if (StringUtils.isEmpty(param.get("page"))||StringUtils.isEmpty(param.get("limit"))){
            ResultGenerator.genFailResult("参数异常");
        }
        PageQueryUtil queryUtil = new PageQueryUtil(param);
        return ResultGenerator.genSuccessResult(blogCommentService.queryCommentPage(queryUtil));
    }

    /**
     * 审核评论
     * @param ids
     * @return
     */
    @PostMapping("/comments/checkDone")
    @ResponseBody
    public Result check(@RequestBody Integer[] ids){
        if (ids.length<1){
            return ResultGenerator.genFailResult("参数异常");
        }
        if (blogCommentService.checkDone(ids)){
            return ResultGenerator.genSuccessResult();
        }else{
            return ResultGenerator.genFailResult("审批失败");
        }
    }
    /**
     * 回复评论
     */
    @PostMapping("/comments/reply")
    @ResponseBody
    public Result reply(@RequestParam("commentId") Integer commentId,
                        @RequestParam("replyBody")String replyBody){
        if (StringUtils.isEmpty(replyBody)||commentId==null || commentId < 1){
            return ResultGenerator.genFailResult("参数异常");
        }
        if (blogCommentService.reply(commentId,replyBody)){
            return ResultGenerator.genSuccessResult();
        }else{
            return ResultGenerator.genFailResult("回复失败");
        }
    }

    /**
     * 删除评论
     * @param ids
     * @return
     */
    @PostMapping("/comments/delete")
    @ResponseBody
    public Result delete(@RequestBody Integer[] ids){
        if (ids.length<1){
            return ResultGenerator.genFailResult("参数异常");
        }
        if (blogCommentService.deleteBatch(ids)){
            return ResultGenerator.genSuccessResult();
        }else{
            return ResultGenerator.genFailResult("删除失败");
        }
    }

}
