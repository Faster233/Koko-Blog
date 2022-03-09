package com.koko.blog.controller.common;/*
@date   2022/1/28 - 16:33
@SH     Let's go! Fuck Everything!
*/


import com.google.code.kaptcha.impl.DefaultKaptcha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Controller
public class CommonController {

    //注入的对象是在KaptchaConfig配置的bean
    @Autowired
    private DefaultKaptcha captchaProducer;


    /**
     *  获取验证码,并由response响应给页面请求
     * @param request
     * @param response
     * @throws IOException
     */
    @GetMapping("/common/kaptcha")
    public void defaultKaptcha(HttpServletRequest request, HttpServletResponse response) throws IOException {
        byte[] KaptchaOutPutStream=null;
        ByteArrayOutputStream imgOutputStream = new ByteArrayOutputStream();
        String kaptchaText = captchaProducer.createText();
        request.getSession().setAttribute("verifyCode",kaptchaText);
        BufferedImage image = captchaProducer.createImage(kaptchaText);
        ImageIO.write(image,"jpg",imgOutputStream);

        KaptchaOutPutStream = imgOutputStream.toByteArray();
        response.setHeader("Cache-Control", "no-store");
        response.setHeader("Pragma", "no-cache");
        response.setDateHeader("Expires", 0);
        response.setContentType("image/jpeg");
        ServletOutputStream stream = response.getOutputStream();
        stream.write(KaptchaOutPutStream);
        stream.flush();
        stream.close();
    }
}
