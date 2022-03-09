package com.koko.blog.controller.admin;

import java.util.Arrays;
import java.util.Map;


import com.koko.blog.utils.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import com.koko.blog.entity.LinkEntity;
import com.koko.blog.service.LinkService;

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
public class LinkController {
    @Autowired
    private LinkService linkService;

    @GetMapping("/links")
    public String linkJump(HttpServletRequest request){
        request.setAttribute("path","links");
        return "admin/link";
    }

    /**
     * 查询links表格内容
     */
    @GetMapping("/links/list")
    @ResponseBody
    public Result list(@RequestParam Map<String,Object> param){
        if (StringUtils.isEmpty(param.get("page")) || StringUtils.isEmpty(param.get("limit"))){
            return ResultGenerator.genFailResult("参数异常！");
        }
        PageQueryUtil pageQueryUtil = new PageQueryUtil(param);
        return linkService.getBlogLinkPage(pageQueryUtil);
    }

    /**
     * 新增链接内容
     */
    @PostMapping("/links/save")
    @ResponseBody
    public Result save(@RequestParam("linkType") Integer linkType,
                       @RequestParam("linkName") String linkName,
                       @RequestParam("linkUrl") String linkUrl,
                       @RequestParam("linkRank") Integer linkRank,
                       @RequestParam("linkDescription") String linkDescription){
        if (linkType == null ||linkType <0 || StringUtils.isEmpty(linkName) || StringUtils.isEmpty(linkUrl)|| linkRank == null ||linkRank <0 || StringUtils.isEmpty(linkDescription)){
            return ResultGenerator.genFailResult("参数异常！");
        }
        LinkEntity link = new LinkEntity();
        link.setLinkDescription(linkDescription);
        link.setLinkName(linkName);
        link.setLinkRank(linkRank);
        link.setLinkType(linkType);
        link.setLinkUrl(linkUrl);
        Boolean b = linkService.saveLink(link);
        return ResultGenerator.genSuccessResult(b);

    }

    /**
     * 修改信息回显
     * @param id
     * @return
     */
    @GetMapping("/links/info/{id}")
    @ResponseBody
    public Result info(@PathVariable("id") Integer id){
        LinkEntity link =linkService.selectLinkById(id);
        return ResultGenerator.genSuccessResult(link);
    }

    /**
     * 修改链接
     * @return
     * linkId: 16
     * linkType: 1
     * linkName: 实验楼
     * linkUrl: https://www.shiyanlou.com/
     * linkDescription: 一家专注于IT技术的在线实训平台
     * linkRank: 17
     */
    @PostMapping("/links/update")
    @ResponseBody
    public Result update(@RequestParam("linkId") Integer linkId,
                         @RequestParam("linkType") Integer linkType,
                         @RequestParam("linkName") String linkName,
                         @RequestParam("linkUrl") String linkUrl,
                         @RequestParam("linkDescription") String linkDescription,
                         @RequestParam("linkRank") Integer linkRank){
        LinkEntity link =linkService.selectLinkById(linkId);
        if (link==null){
            return ResultGenerator.genFailResult("无数据！");
        }
        if (linkType == null ||linkType <0 || StringUtils.isEmpty(linkName) || StringUtils.isEmpty(linkUrl)|| linkRank == null ||linkRank <0 || StringUtils.isEmpty(linkDescription)){
            return ResultGenerator.genFailResult("参数异常！");
        }
        link.setLinkType(linkType);
        link.setLinkName(linkName);
        link.setLinkUrl(linkUrl);
        link.setLinkDescription(linkDescription);
        link.setLinkRank(linkRank);
        Boolean b = linkService.updateLinkById(link);
        return ResultGenerator.genSuccessResult(b);
    }


    @PostMapping("/links/delete")
    @ResponseBody
    public Result delete(@RequestBody Integer[] ids){
        if (ids.length<1){
            return ResultGenerator.genFailResult("参数异常");
        }

        if(linkService.deleteBatch(ids)){
            return ResultGenerator.genSuccessResult();
        }else{
            return ResultGenerator.genFailResult("删除失败");
        }


    }
}
