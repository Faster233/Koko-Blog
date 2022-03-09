package com.koko.blog.controller.blog;/*
@date   2022/2/6 - 16:52
@SH     Let's go! Fuck Everything!
*/

import com.koko.blog.controller.vo.BlogDetailVO;
import com.koko.blog.entity.BlogCommentEntity;
import com.koko.blog.entity.LinkEntity;
import com.koko.blog.service.*;
import com.koko.blog.utils.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

@Controller
public class MyBlogController {


    public static String theme = "amaze";

    @Autowired
    private BlogService blogService;
    @Autowired
    private BlogTagService blogTagService;
    @Autowired
    private ConfigService configService;
    @Autowired
    private BlogCommentService commentService;
    @Autowired
    private LinkService linkService;

    /**
     * 首页
     * @param request
     * @return
     */
    @GetMapping({"","/","/index","index.html"})
    public String index(HttpServletRequest request){
        return this.page(request,1);
    }

    /**
     * 首页分页数据
     * @param request
     * @param pageNum
     * @return
     */
    @GetMapping("/page/{pageNum}")
    public String page(HttpServletRequest request,@PathVariable("pageNum") int pageNum) {
        //获取首页文章列表
        PageUtils blogPageUtils=blogService.getBlogsForIndexPage(pageNum);
        if (blogPageUtils==null){
            return "error/error_404";
        }
        request.setAttribute("blogPageResult",blogPageUtils);
        // 首页侧边栏博客信息    type=1  最新发布
        request.setAttribute("newBlogs",blogService.getBlogListForIndexPage(1));
        // 首页侧边栏博客信息    type=0  点击最多
        request.setAttribute("hotBlogs",blogService.getBlogListForIndexPage(0));
        //获取热门的20个标签
        request.setAttribute("hotTags",blogTagService.getBlogTagCountForIndex());
        request.setAttribute("pageName", "首页");
        request.setAttribute("configurations",configService.getAllConfigs());
        return "blog/"+theme+"/index";
    }

    /**
     *  详情页
     * @param request
     * @param blogId
     * @return
     */
    @GetMapping("/blog/{blogId}")
    public String detail(HttpServletRequest request,
                         @PathVariable("blogId")Long blogId,
                         @RequestParam(value = "commentPage", required = false, defaultValue = "1") Integer commentPage){
        BlogDetailVO blogDetailVO =blogService.getBlogDetailById(blogId);
        if (blogDetailVO!=null){
            request.setAttribute("blogDetailVO",blogDetailVO);
        }
        request.setAttribute("commentPageResult", commentService.getCommentPageByBlogIdAndPageNum(blogId, commentPage));
        request.setAttribute("pageName", "详情");
        request.setAttribute("configurations", configService.getAllConfigs());
        return "blog/" + theme + "/detail";
    }

    /**
     * 标签列表页跳转
     * @param request
     * @param tagName
     * @return
     */
    @GetMapping("/tag/{tagName}")
    public String tag(HttpServletRequest request,
                    @PathVariable("tagName")String tagName){
        return tag(request,tagName,1);
    }

    /**
     * 标签列表页
     * @param request
     * @param tagName
     * @param page
     * @return
     */
    @GetMapping("/tag/{tagName}/{page}")
    public String tag(HttpServletRequest request,
                      @PathVariable("tagName")String tagName,
                                  @PathVariable("page")Integer page){
        PageUtils blogPageResult=blogService.getBlogPageByTag(tagName,page);
        request.setAttribute("blogPageResult", blogPageResult);
        request.setAttribute("pageName", "标签");
        request.setAttribute("pageUrl", "tag");
        request.setAttribute("keyword", tagName);
        request.setAttribute("newBlogs", blogService.getBlogListForIndexPage(1));
        request.setAttribute("hotBlogs", blogService.getBlogListForIndexPage(0));
        request.setAttribute("hotTags", blogTagService.getBlogTagCountForIndex());
        request.setAttribute("configurations", configService.getAllConfigs());
        return "blog/" + theme + "/list";
    }

    /**
     * 分类列表页跳转
     * @param request
     * @param categoryName
     * @return
     */
    @GetMapping("/category/{categoryName}")
    public String category(HttpServletRequest request,
                           @PathVariable("categoryName")String categoryName){
        return category(request,categoryName,1);

    }
    /**
     * 分类列表页
     * @param request
     * @param categoryName
     * @return
     */
    @GetMapping("/category/{categoryName}/{page}")
    public String category(HttpServletRequest request,
                           @PathVariable("categoryName")String categoryName,
                           @PathVariable("page")Integer page) {
        PageUtils blogVoResult=blogService.getBlogPageByCategory(categoryName,page);
        request.setAttribute("blogPageResult", blogVoResult);
        request.setAttribute("pageName", "分类");
        request.setAttribute("pageUrl", "category");
        request.setAttribute("keyword", categoryName);
        request.setAttribute("newBlogs", blogService.getBlogListForIndexPage(1));
        request.setAttribute("hotBlogs", blogService.getBlogListForIndexPage(0));
        request.setAttribute("hotTags", blogTagService.getBlogTagCountForIndex());
        request.setAttribute("configurations", configService.getAllConfigs());
        return "blog/" + theme + "/list";

    }

    /**
     * 查询列表页跳转
     * @param request
     * @param keyword
     * @return
     */
    @GetMapping("/search/{keyword}")
    public String search(HttpServletRequest request,
                         @PathVariable("keyword")String keyword){
        return search(request,keyword,1);
    }
    /**
     * 查询列表页
     * @param request
     * @param keyword
     * @return
     */
    @GetMapping("/search/{keyword}/{page}")
    public String search(HttpServletRequest request,
                         @PathVariable("keyword")String keyword,
                         @PathVariable("page")Integer page) {
        PageUtils blogVoResult=blogService.getBlogPageBySearch(keyword,page);
        request.setAttribute("blogPageResult", blogVoResult);
        request.setAttribute("pageName", "搜索");
        request.setAttribute("pageUrl", "search");
        request.setAttribute("keyword", keyword);
        request.setAttribute("newBlogs", blogService.getBlogListForIndexPage(1));
        request.setAttribute("hotBlogs", blogService.getBlogListForIndexPage(0));
        request.setAttribute("hotTags", blogTagService.getBlogTagCountForIndex());
        request.setAttribute("configurations", configService.getAllConfigs());
        return "blog/" + theme + "/list";
    }

    /**
     * 友情链接页
     *
     * @return
     */
    @GetMapping({"/link"})
    public String link(HttpServletRequest request){
        request.setAttribute("pageName","友情链接");
        Map<Integer, List<LinkEntity>> linkMap=linkService.getLinkForLinkPage();
        if (linkMap!=null){
            //判断友链类别并封装数据 0-友链 1-推荐 2-个人网站
            if (linkMap.containsKey(0)){
                request.setAttribute("favoriteLinks", linkMap.get(0));
            }
            if (linkMap.containsKey(1)){
                request.setAttribute("recommendLinks", linkMap.get(1));
            }
            if (linkMap.containsKey(2)){
                request.setAttribute("personalLinks", linkMap.get(2));
            }
        }
        request.setAttribute("configurations", configService.getAllConfigs());
        return "blog/" + theme + "/link";
    }

    @PostMapping("/blog/comment")
    @ResponseBody
    public Result comment(HttpServletRequest request,
                          HttpSession session,
                          @RequestParam("blogId")Long blogId,
                          @RequestParam("verifyCode")String verifyCode,
                          @RequestParam("commentator")String commentator,
                          @RequestParam("email")String email,
                          @RequestParam("commentBody")String commentBody){
        if (StringUtils.isEmpty(verifyCode)) {
            return ResultGenerator.genFailResult("验证码不能为空");
        }
        String  kaptchaCode = (String) session.getAttribute("verifyCode");
        if (StringUtils.isEmpty(kaptchaCode)) {
            return ResultGenerator.genFailResult("非法请求");
        }
        if (!verifyCode.equals(kaptchaCode)) {
            return ResultGenerator.genFailResult("验证码错误");
        }
        //验证请求来源,可以优化
        String ref = request.getHeader("Referer");
        if (StringUtils.isEmpty(ref)) {
            return ResultGenerator.genFailResult("非法请求");
        }
        if (null == blogId || blogId < 0) {
            return ResultGenerator.genFailResult("非法请求");
        }
        if (StringUtils.isEmpty(commentator)) {
            return ResultGenerator.genFailResult("请输入称呼");
        }
        if (StringUtils.isEmpty(email)) {
            return ResultGenerator.genFailResult("请输入邮箱地址");
        }
        if (!PatternUtil.isEmail(email)) {
            return ResultGenerator.genFailResult("请输入正确的邮箱地址");
        }
        if (StringUtils.isEmpty(commentBody)) {
            return ResultGenerator.genFailResult("请输入评论内容");
        }
        if (commentBody.trim().length() > 200) {
            return ResultGenerator.genFailResult("评论内容过长");
        }
        BlogCommentEntity blogComment = new BlogCommentEntity();
        blogComment.setBlogId(blogId);
        blogComment.setCommentator(MyBlogUtils.cleanString(commentator));
        blogComment.setEmail(email);
        blogComment.setCommentBody(MyBlogUtils.cleanString(commentBody));
        return ResultGenerator.genSuccessResult(commentService.saveBlogComment(blogComment));
    }
    /**
     * 关于
     */
    @GetMapping("/{subUrl}")
    public String detail(HttpServletRequest request,@PathVariable("subUrl")String subUrl){
        BlogDetailVO blogDetailVO=blogService.getBlogDetailBySubUrl(subUrl);
        if (blogDetailVO!=null){
            request.setAttribute("blogDetailVO", blogDetailVO);
            request.setAttribute("pageName", subUrl);
            request.setAttribute("configurations", configService.getAllConfigs());
            return "blog/" + theme + "/detail";
        } else {
            return "error/error_400";
        }

    }
}
