package com.qiansheng.reggie.controller;

import com.qiansheng.reggie.controller.util.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.file.Files;
import java.util.UUID;

@RestController
@RequestMapping("/common")
@Slf4j
public class CommonController {

    @Value("${mydate.imgPath}")
    private String imgPath;

    /**
     * 上传文件
     * @param request
     * @param file
     * @return
     * @throws IOException
     */
    @RequestMapping("/upload")
    public R<String> upload(HttpServletRequest request, MultipartFile file) throws IOException {
        //生成UUID
        String s = UUID.randomUUID().toString().replaceAll("\\-", "");
        //获取文件名
        String originalFilename = file.getOriginalFilename();
        //获取后缀
        String substring = originalFilename.substring(originalFilename.lastIndexOf("."));
        //生成文件名
        String name=s+substring;
        //File file1 = new File(request.getServletContext().getRealPath("/")+"/templates/img");
        File file1 = new File(imgPath+name);
        FileCopyUtils.copy(file.getInputStream(), Files.newOutputStream(file1.toPath()));
        return R.success(name);
    }

    /**
     * 下载文件
     * @param name
     * @param response
     */
    @RequestMapping("/download")
    public void download(String name,HttpServletResponse response)  {
        FileInputStream fileInputStream = null;
        ServletOutputStream outputStream =null;
        response.setContentType("imag/jpeg");
        try {
            fileInputStream = new FileInputStream(new File(imgPath+ name) );
            outputStream = response.getOutputStream();
            int nlen=0;
            byte[] i=new byte[1024];
            while ((nlen=fileInputStream.read(i))!=-1){
                outputStream.write(i,0,nlen);
                outputStream.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                fileInputStream.close();
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
