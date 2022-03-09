package com.koko.blog.controller.admin;/*
@date   2022/2/6 - 14:31
@SH     Let's go! Fuck Everything!
*/

import com.koko.blog.utils.MyBlogUtils;
import com.koko.blog.utils.Result;
import com.koko.blog.utils.ResultGenerator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

@Controller
@RequestMapping("/admin")
public class UploadController {
    @Value("${file.filePathWindow}")
    private String filePathWindow;

    @Value("${file.filePathLinux}")
    private String filePathLinux;

    @PostMapping("/upload/file")
    @ResponseBody
    public Result upload(HttpServletRequest request, @RequestPart("file")MultipartFile file) throws URISyntaxException, IOException {

        if (file.isEmpty()){
            ResultGenerator.genFailResult("文件内容找不到");
        }
        //获取文件
        String filename = file.getOriginalFilename();
        String suffixName = filename.substring(filename.lastIndexOf("."));

        DateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
        Random random = new Random();
        StringBuilder filetemp = new StringBuilder();
        filetemp.append(sdf.format(new Date())).append(random.nextInt(100)).append(suffixName);
        String newFileName =filetemp.toString();

        String path=null;
        String os = System.getProperty("os.name");
        if (os.toLowerCase().startsWith("win")){
            path=filePathWindow;
        }else{
            path=filePathLinux;
        }

        File destFile = new File(path+newFileName);
        File fileDir = new File(path);
        try {
            if (!fileDir.exists()){
                if (!fileDir.mkdir()){
                    throw new IOException("文件路径创建失败"+fileDir);
                }
            }
            file.transferTo(destFile);
            URI uri = new URI(request.getRequestURI() + "");
            String fileUrl = MyBlogUtils.getHost(uri) + "/upload/" + newFileName;
            Result result = ResultGenerator.genSuccessResult();
            result.setData(fileUrl);
            return result;
        } catch (IOException e) {
            e.printStackTrace();
            return ResultGenerator.genFailResult("文件上传失败");
        }
    }

}
