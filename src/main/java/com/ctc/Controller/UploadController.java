package com.ctc.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping(value = "/test")
public class UploadController {

    @RequestMapping(value = "/testUpload")
    public String testUpload(){
        return "upload";
        //return new ModelAndView("upload");
    }

    @ResponseBody
    @RequestMapping(value = "/upload")
    public String upload(HttpServletRequest request, @RequestParam(value = "file") MultipartFile file,
                         ModelMap model) {
            System.out.println("开始");
            String path = request.getSession().getServletContext().getRealPath("upload");
            String fileName = file.getOriginalFilename();
            // String fileName = new Date().getTime()+".jpg";
            System.out.println(path);
            File targetFile = new File(path, fileName);
            if (!targetFile.exists()) {
                targetFile.mkdirs();
            }

            // 保存
            try {
                file.transferTo(targetFile);
            } catch (Exception e) {
                e.printStackTrace();
            }
            model.addAttribute("fileUrl", request.getContextPath() + "/upload/" + fileName);

        return "result";
    }

}
