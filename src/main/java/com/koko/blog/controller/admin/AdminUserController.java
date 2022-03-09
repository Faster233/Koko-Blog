package com.koko.blog.controller.admin;

import java.util.Arrays;
import java.util.Map;


import com.koko.blog.service.*;
import com.koko.blog.utils.PageUtils;
import com.koko.blog.utils.R;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import com.koko.blog.entity.AdminUserEntity;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


/**
 * 
 *
 * @author Faster
 * @email Faster@shgogo.com
 * @date 2022-01-28 15:07:27
 */
@Controller
@RequestMapping("/admin")
public class AdminUserController {
    @Autowired
    private AdminUserService adminUserService;
    @Autowired
    private BlogCategoryService categoryService;
    @Autowired
    private BlogService blogService;
    @Autowired
    private LinkService linkService;
    @Autowired
    private BlogTagService tagService;
    @Autowired
    private BlogCommentService commentService;
    /**
     * 登录页面跳转
     * @return4
     */
    @GetMapping("/login")
    public String login(){
        return "admin/login";
    }

    /**
     * 登录方法
     * @param userName
     * @param password
     * @param verifyCode
     * @param session
     * @return
     */
    @PostMapping("/login")
    public String login(@RequestParam("userName") String userName,
                        @RequestParam("password") String password,
                        @RequestParam("verifyCode") String verifyCode,
                        HttpSession session){
        if (StringUtils.isEmpty(userName) || StringUtils.isEmpty(password)){
            session.setAttribute("errorMsg","用户名或者密码不能为空!");
            return "admin/login";
        }else if(StringUtils.isEmpty(verifyCode)){
            session.setAttribute("errorMsg","验证码不能为空!");
            return "admin/login";
        }
        String code = (String) session.getAttribute("verifyCode");
        if(!code.equals(verifyCode)){
            session.setAttribute("errorMsg","验证码输入不正确!");
            return "admin/login";
        }

        AdminUserEntity loginUser=adminUserService.login(userName,password);
        if (loginUser!=null){
            session.setAttribute("loginUser",loginUser.getNickName());
            session.setAttribute("loginUserId",loginUser.getAdminUserId());
            return "redirect:/admin/index";
        }else {
            session.setAttribute("errorMsg","用户名或者密码输入错误!");
            return "admin/login";
        }



    }

    /**
     * 跳转到首页
     * @param request
     * @return
     */
    @GetMapping({"","/","/index","/index.html"})
    public String index(HttpServletRequest request){
        // 设置path
        request.setAttribute("path","index");
        // 设置分类数量
        request.setAttribute("categoryCount",categoryService.getTotalCategoies());
        // 设置博客数量
        request.setAttribute("blogCount",blogService.getTotalBlogs());
        // 设置链接数量
        request.setAttribute("linkCount",linkService.getTotalLinks());
        //设置标签数量
        request.setAttribute("tagCount",tagService.getTotalTags());
        //设置评论数量
        request.setAttribute("commentCount",commentService.getTotalComments());

        return "admin/index";
    }

    /**
     * 修改个人信息页面跳转
     * @param request
     * @return
     */
    @GetMapping("/profile")
    public String profile(HttpServletRequest request){
        int loginUserId = (int) request.getSession().getAttribute("loginUserId");
        AdminUserEntity adminUser=adminUserService.getAdminUserById(loginUserId);
        if(adminUser==null){
            return "admin/login";
        }
        request.setAttribute("path","profile");
        request.setAttribute("loginUserName",adminUser.getLoginUserName());
        request.setAttribute("nickName",adminUser.getNickName());
        return "admin/profile";
    }

    /**
     * 修改用户密码
     * @param originalPassword
     * @param newPassword
     * @return
     */
    @ResponseBody
    @PostMapping("/profile/password")
    public String updatePassword(@RequestParam("originalPassword") String originalPassword,
                                 @RequestParam("newPassword") String newPassword,
                                 HttpServletRequest request){
        if (StringUtils.isEmpty(originalPassword) || StringUtils.isEmpty(newPassword)){
            return "参数不能为空";
        }
        int loginUserId = (int) request.getSession().getAttribute("loginUserId");
        Boolean flag = adminUserService.updateAdminUserPassword(originalPassword, newPassword, loginUserId);
        if (flag){
            request.getSession().removeAttribute("errorMsg");
            request.getSession().removeAttribute("loginUserId");
            request.getSession().removeAttribute("loginUser");
            return "success";
        }else {
            return "修改失败";
        }

    }

    /**
     * 修改昵称或登录名
     * @param loginUserName
     * @param nickName
     * @param request
     * @return
     */
    @ResponseBody
    @PostMapping("/profile/name")
    public String updateName(@RequestParam("loginUserName") String loginUserName,
                             @RequestParam("nickName") String nickName,
                             HttpServletRequest request){
        if (StringUtils.isEmpty(loginUserName) || StringUtils.isEmpty(nickName)){
            return "参数不能为空";
        }
        int loginUserId = (int) request.getSession().getAttribute("loginUserId");
        Boolean flag = adminUserService.updateAdminUserName(loginUserName, nickName, loginUserId);
        if (flag){
            request.getSession().setAttribute("loginUser",nickName);
            return "success";
        }else {
            return "修改失败";
        }
    }



    /**
     * 退出登录
     * @param request
     * @return
     */
    @GetMapping("/logout")
    public String logout(HttpServletRequest request){
        request.getSession().removeAttribute("loginUserId");
        request.getSession().removeAttribute("loginUser");
        request.getSession().removeAttribute("errorMsg");
        return "admin/login";
    }




    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("blog:adminuser:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = adminUserService.queryPage(params);
        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{adminUserId}")
    //@RequiresPermissions("blog:adminuser:info")
    public R info(@PathVariable("adminUserId") Integer adminUserId){
		AdminUserEntity adminUser = adminUserService.getById(adminUserId);

        return R.ok().put("adminUser", adminUser);
    }



}
