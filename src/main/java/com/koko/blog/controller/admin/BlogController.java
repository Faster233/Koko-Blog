package com.koko.blog.controller.admin;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;


import com.koko.blog.entity.BlogCategoryEntity;
import com.koko.blog.service.BlogCategoryService;
import com.koko.blog.utils.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import com.koko.blog.entity.BlogEntity;
import com.koko.blog.service.BlogService;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * 
 *
 * @author Faster
 * @email Faster@shgogo.com
 * @date 2022-01-28 15:07:27
 */
@Controller
@RequestMapping("/admin")
public class BlogController {
    @Autowired
    private BlogService blogService;
    @Autowired
    private BlogCategoryService categoryService;
    @Value("${file.filePathWindow}")
    private String filePathWindow;
    @Value("${file.filePathLinux}")
    private String filePathLinux;

    @GetMapping("/blogs")
    public String blog(HttpServletRequest request){
        request.setAttribute("path","blogs");
        return "admin/blog";
    }

    @GetMapping("/blogs/edit")
    public String edit(HttpServletRequest request){
        request.setAttribute("path","edit");
        List<BlogCategoryEntity> list = categoryService.getAllCategory();
        request.setAttribute("categories",list);
        return "admin/edit";
    }

    @GetMapping("/blogs/edit/{blogId}")
    public String edit(HttpServletRequest request,@PathVariable("blogId") Long blogId){
        request.setAttribute("path","edit");
        BlogEntity blog=blogService.getBlogById(blogId);
        if (blog==null){
            return "error/error_400";
        }
        request.setAttribute("blog",blog);
        request.setAttribute("categories",categoryService.getAllCategory());
        return "admin/edit";
    }

    @GetMapping("/blogs/list")
    @ResponseBody
    public Result list(@RequestParam Map<String,Object> param){
        if (StringUtils.isEmpty(param.get("limit"))||StringUtils.isEmpty(param.get("page"))){
            return ResultGenerator.genSuccessResult("????????????");
        }
        PageQueryUtil queryUtil = new PageQueryUtil(param);
        if (!StringUtils.isEmpty(param.get("keyword"))){
            queryUtil.setKeyword((String) param.get("keyword"));
        }
        return ResultGenerator.genSuccessResult(blogService.queryBlogPage(queryUtil));


    }


    @PostMapping("/blogs/save")
    @ResponseBody
    public Result save(@RequestParam("blogTitle")String blogTitle,
                       @RequestParam("blogSubUrl")String blogSubUrl,
                       @RequestParam("blogCategoryId")Integer blogCategoryId,
                       @RequestParam("blogTags")String blogTags,
                       @RequestParam("blogContent")String blogContent,
                       @RequestParam("blogCoverImage")String blogCoverImage,
                       @RequestParam("blogStatus")Integer blogStatus,
                       @RequestParam("enableComment")Integer enableComment){
        if (StringUtils.isEmpty(blogTitle)){
            return ResultGenerator.genFailResult("?????????????????????");
        }
        if (blogTitle.trim().length() > 150){
            return ResultGenerator.genFailResult("??????????????????");
        }
        if (blogSubUrl.trim().length() > 150){
            return ResultGenerator.genFailResult("????????????");
        }
        if (StringUtils.isEmpty(blogTags)){
            return ResultGenerator.genFailResult("?????????????????????");
        }
        if (blogTags.trim().length() > 150){
            return ResultGenerator.genFailResult("??????????????????");
        }
        if (StringUtils.isEmpty(blogContent)){
            return ResultGenerator.genFailResult("?????????????????????");
        }
        if (blogContent.trim().length() > 100000){
            return ResultGenerator.genFailResult("??????????????????");
        }
        if (StringUtils.isEmpty(blogCoverImage)){
            return ResultGenerator.genFailResult("?????????????????????");
        }
        BlogEntity blog = new BlogEntity();
        blog.setBlogTitle(blogTitle);
        blog.setBlogSubUrl(blogSubUrl);
        blog.setBlogCategoryId(blogCategoryId);
        blog.setBlogTags(blogTags);
        blog.setBlogContent(blogContent);
        blog.setBlogCoverImage(blogCoverImage);
        blog.setBlogStatus(blogStatus);
        blog.setEnableComment(enableComment);
        String saveResult=blogService.saveBlog(blog);
        if ("success".equals(saveResult)){
            return ResultGenerator.genSuccessResult("????????????");
        }else{
            return ResultGenerator.genFailResult(saveResult);
        }

    }

    @PostMapping("/blogs/update")
    @ResponseBody
    public Result update(@RequestParam("blogId") Long blogId,
                         @RequestParam("blogTitle")String blogTitle,
                         @RequestParam("blogSubUrl")String blogSubUrl,
                         @RequestParam("blogCategoryId")Integer blogCategoryId,
                         @RequestParam("blogTags")String blogTags,
                         @RequestParam("blogContent")String blogContent,
                         @RequestParam("blogCoverImage")String blogCoverImage,
                         @RequestParam("blogStatus")Integer blogStatus,
                         @RequestParam("enableComment")Integer enableComment){
        if (StringUtils.isEmpty(blogTitle)){
            return ResultGenerator.genFailResult("?????????????????????");
        }
        if (blogTitle.trim().length() > 150){
            return ResultGenerator.genFailResult("??????????????????");
        }
        if (blogSubUrl.trim().length() > 150){
            return ResultGenerator.genFailResult("????????????");
        }
        if (StringUtils.isEmpty(blogTags)){
            return ResultGenerator.genFailResult("?????????????????????");
        }
        if (blogTags.trim().length() > 150){
            return ResultGenerator.genFailResult("??????????????????");
        }
        if (StringUtils.isEmpty(blogContent)){
            return ResultGenerator.genFailResult("?????????????????????");
        }
        if (blogContent.trim().length() > 100000){
            return ResultGenerator.genFailResult("??????????????????");
        }
        if (StringUtils.isEmpty(blogCoverImage)){
            return ResultGenerator.genFailResult("?????????????????????");
        }
        BlogEntity blog = new BlogEntity();
        blog.setBlogId(blogId);
        blog.setBlogTitle(blogTitle);
        blog.setBlogSubUrl(blogSubUrl);
        blog.setBlogCategoryId(blogCategoryId);
        blog.setBlogTags(blogTags);
        blog.setBlogContent(blogContent);
        blog.setBlogCoverImage(blogCoverImage);
        blog.setBlogStatus(blogStatus);
        blog.setEnableComment(enableComment);
        String updateResult=blogService.updateBlog(blog);
        if ("success".equals(updateResult)){
            return ResultGenerator.genSuccessResult("????????????");
        }else{
            return ResultGenerator.genFailResult(updateResult);
        }

    }

    @PostMapping("/blogs/delete")
    @ResponseBody
    public Result delete(@RequestBody Integer[] ids){
        if (ids.length<1){
            return ResultGenerator.genFailResult("????????????");
        }
        if (blogService.batchDelete(ids)){
            return ResultGenerator.genSuccessResult();
        }else{
            return ResultGenerator.genFailResult("????????????");
        }

    }

    @PostMapping("/blogs/md/uploadfile")
    public void uploadFileByEditormd(HttpServletRequest request,
                                     HttpServletResponse response,
                                     @RequestPart("editormd-image-file")MultipartFile file) throws IOException, URISyntaxException {
        if (file.isEmpty()){
            response.getWriter().write("{\"success\":0}");
        }
        //?????????????????????
        String filename = file.getOriginalFilename();
        String suffixName = filename.substring(filename.lastIndexOf("."));
        //?????????????????????
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
        Random random = new Random();
        StringBuilder tempName = new StringBuilder();
        tempName.append(sdf.format(new Date())).append(random.nextInt(100)).append(suffixName);
        String newFileName = tempName.toString();


        URI uri = new URI(request.getRequestURI()+"");
        String fileUrl=MyBlogUtils.getHost(uri)+ "/upload/" + newFileName;
        String filePath=null;
        String os = System.getProperty("os.name");
        if (os.toLowerCase().startsWith("win")){
            //win
            filePath=filePathWindow;
        }else{
            //linux
            filePath=filePathLinux;
        }
        File destFile = new File(filePath+ newFileName);
        File fileDir = new File(filePath);
        try {
        if (!fileDir.exists()){
            if (!fileDir.mkdir()){
                throw new IOException("?????????????????????,????????????" + fileDir);
            }
        }
        file.transferTo(destFile);
        request.setCharacterEncoding("utf-8");
        response.setHeader("Content-Type", "text/html");
        response.getWriter().write("{\"success\": 1, \"message\":\"success\",\"url\":\"" + fileUrl + "\"}");
        } catch (
        UnsupportedEncodingException e) {
            response.getWriter().write("{\"success\":0}");
        } catch (IOException e) {
            response.getWriter().write("{\"success\":0}");
        }
    }
}
