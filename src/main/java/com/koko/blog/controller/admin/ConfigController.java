package com.koko.blog.controller.admin;

import java.util.Arrays;
import java.util.Map;



import com.koko.blog.utils.Result;
import com.koko.blog.utils.ResultGenerator;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.*;


import com.koko.blog.service.ConfigService;

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
public class ConfigController {
    @Autowired
    private ConfigService configService;

    /**
     * 系统设置页面跳转
     * @param request
     * @return
     */
    @GetMapping("/configurations")
    public String configurationsJump(HttpServletRequest request){
        request.setAttribute("path","configurations");
        request.setAttribute("configurations",configService.getAllConfigs());
        return "admin/configuration";
    }


    /**
     *  修改站点信息
     * @return
     */
    @PostMapping("/configurations/website")
    @ResponseBody
    public Result updateWebsite(@RequestParam(value="websiteName",required = false) String websiteName,
                                @RequestParam(value="websiteDescription",required = false) String websiteDescription,
                                @RequestParam(value="websiteLogo",required = false) String websiteLogo,
                                @RequestParam(value="websiteIcon",required = false) String websiteIcon){
        //判断是否有值传入,一个一个判断更新
        //返回result对象
        int updateResult=0;
        if (!StringUtils.isEmpty(websiteName)){
            updateResult+=configService.updateConfig("websiteName",websiteName);
        }
        if (!StringUtils.isEmpty(websiteDescription)){
            updateResult+=configService.updateConfig("websiteDescription",websiteDescription);
        }
        if (!StringUtils.isEmpty(websiteLogo)){
            updateResult+=configService.updateConfig("websiteLogo",websiteLogo);
        }
        if (!StringUtils.isEmpty(websiteIcon)){
            updateResult+=configService.updateConfig("websiteIcon",websiteIcon);
        }
        return ResultGenerator.genSuccessResult(updateResult>0);
    }

    /**
     * 修改个人信息
     * @param yourAvatar
     * @param yourName
     * @param email
     * @return
     */
    @PostMapping("/configurations/userInfo")
    @ResponseBody
    public Result updateUserInfo(@RequestParam(value = "yourAvatar",required = false) String yourAvatar,
                                 @RequestParam(value = "yourName",required = false) String yourName,
                                 @RequestParam(value = "email",required = false) String email){
        int updateResult=0;
        if (!StringUtils.isEmpty(yourAvatar)){
            updateResult+=configService.updateConfig("yourAvatar",yourAvatar);
        }
        if (!StringUtils.isEmpty(yourName)){
            updateResult+=configService.updateConfig("yourName",yourName);
        }
        if (!StringUtils.isEmpty(email)){
            updateResult+=configService.updateConfig("email",email);
        }
        return ResultGenerator.genSuccessResult(updateResult>0);
    }

    @PostMapping("/configurations/footer")
    @ResponseBody
    public Result updateFooter(@RequestParam(value = "footerAbout",required = false) String footerAbout,
                               @RequestParam(value = "footerICP",required = false) String footerICP,
                               @RequestParam(value = "footerCopyRight",required = false) String footerCopyRight,
                               @RequestParam(value = "footerPoweredBy",required = false) String footerPoweredBy,
                               @RequestParam(value = "footerPoweredByURL",required = false) String footerPoweredByURL){
        int updateResult=0;
        if (!StringUtils.isEmpty(footerAbout)){
            updateResult+=configService.updateConfig("footerAbout",footerAbout);
        }
        if (!StringUtils.isEmpty(footerICP)){
            updateResult+=configService.updateConfig("footerICP",footerICP);
        }
        if (!StringUtils.isEmpty(footerCopyRight)){
            updateResult+=configService.updateConfig("footerCopyRight",footerCopyRight);
        }
        if (!StringUtils.isEmpty(footerPoweredBy)){
            updateResult+=configService.updateConfig("footerPoweredBy",footerPoweredBy);
        }
        if (!StringUtils.isEmpty(footerPoweredByURL)){
            updateResult+=configService.updateConfig("footerPoweredByURL",footerPoweredByURL);
        }

        return ResultGenerator.genSuccessResult(updateResult>0);
    }



    /**
     * 列表
     */
    /*@RequestMapping("/list")
    //@RequiresPermissions("blog:config:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = configService.queryPage(params);

        return R.ok().put("page", page);
    }*/


    /**
     * 信息
     */
    // @RequestMapping("/info/{configName}")
    //@RequiresPermissions("blog:config:info")
    // public R info(@PathVariable("configName") String configName){
	// 	ConfigEntity config = configService.getById(configName);
    //
    //     return R.ok().put("config", config);
    // }

    /**
     * 保存
     */
    // @RequestMapping("/save")
    //@RequiresPermissions("blog:config:save")
    // public R save(@RequestBody ConfigEntity config){
	// 	configService.save(config);
    //
    //     return R.ok();
    // }

    /**
     * 修改
     */
    // @RequestMapping("/update")
    //@RequiresPermissions("blog:config:update")
    // public R update(@RequestBody ConfigEntity config){
	// 	configService.updateById(config);
    //
    //     return R.ok();
    // }

    /**
     * 删除
     */
    // @RequestMapping("/delete")
    //@RequiresPermissions("blog:config:delete")
    // public R delete(@RequestBody String[] configNames){
	// 	configService.removeByIds(Arrays.asList(configNames));
    //
    //     return R.ok();
    // }

}
