package com.ctc.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Date;
import java.util.Map;

@Controller
public class loginCtrl {
    @RequestMapping(value = "/goindex")
    public ModelAndView goindex() {
        ModelAndView mav = new ModelAndView("index");
        mav.addObject("name", "笑傲江湖");
        mav.addObject("projectName", "Freemarker框架");
        return mav;
    }
    @RequestMapping(value = "/login")
    public void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getSession().setAttribute("username", "身份认证成功");
        request.getRequestDispatcher("/index.jsp").forward(request, response);
    }
    @RequestMapping("/uploadAttach")
    public void processUploadDir(ModelMap modelMap,
                                 MultipartHttpServletRequest request, PrintWriter writer) throws Exception {
        Map<String, MultipartFile> fileMap = request.getFileMap();
        String path = request.getSession().getServletContext().getRealPath("/");;
        System.out.println("path:"+path);
        Date currentTime = new Date();
        long prefix = currentTime.getTime();
        StringBuffer attachIds = new StringBuffer();
        for (Map.Entry<String, MultipartFile> f : fileMap.entrySet()) {
            MultipartFile file = f.getValue();
            if (!isLegalFile(file)) {
                String msg = "is a illegal file";
                throw new RuntimeException(msg);
            }
            String originalFileName = prefix + "_" + file.getOriginalFilename();
            File fileDir = new File(path + "/upload" + File.separator);
            if (!fileDir.exists()) {
                fileDir.mkdirs();
            }

            File files = new File(path + "/upload" + File.separator
                    + originalFileName);
            FileOutputStream fileOutputStream = null;
            try {
                fileOutputStream = new FileOutputStream(files);
                fileOutputStream.write(file.getBytes());
                fileOutputStream.flush();

                attachIds.append(originalFileName + ",");

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (fileOutputStream != null) {
                    try {
                        fileOutputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

        }

        writer.write(attachIds.toString().substring(0,attachIds.toString().length()-1));
    }
    private final String[] fileType = new String[]{".dat",".264",".h264",".mp4",".dav",".MP4",".AVI",".ts",".avi",".mpg",".rmvb",".flv",".rm",".mov",".wmv",
            ".JPG",".bmp",".png",".BMP",".jpg",".PNG",".gif",
            ".xlsx",".xls",".txt",".pdf",".doc",".docx",
            ".rar",".zip",".7z"};
    private boolean isLegalFile(MultipartFile file) {
        String originalFileName =  file.getOriginalFilename();
        for(String ft : fileType) {
            if (originalFileName.endsWith(ft)) {
                return true;
            }
        }
        return false;
    }
}
