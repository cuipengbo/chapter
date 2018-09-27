package com.hytx.jcxfd.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.util.Base64Utils;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/uploads")
public class FileUploadController {

    private static final Logger log = LoggerFactory.getLogger(FileUploadController.class);

    @GetMapping
    public String index() {
    	log.info("静茹index");
        return "index";
    }


    @PostMapping("/upload1")
    @ResponseBody
    public Map<String, String> upload1(@RequestParam("file") MultipartFile file, HttpServletRequest request) throws IOException {
        log.info("[文件类型] - [{}]", file.getContentType());
        log.info("[文件名称] - [{}]", file.getOriginalFilename());
        log.info("[文件大小] - [{}]", file.getSize());
        Map<String, String> result = new HashMap<>(16);
        if (file.isEmpty()) {
        	result.put("result", "文件为空");
            return result;
        }
        // TODO 将文件写入到指定目录（具体开发中有可能是将文件写入到云存储/或者指定目录通过 Nginx 进行 gzip 压缩和反向代理，此处只是为了演示故将地址写成本地电脑指定目录）
        //TODO 不好使....	 String realPath = request.getServletContext().getRealPath("/static/js/");  获取项目动态绝对目录
//        String path = ResourceUtils.getURL("classpath:").getPath();
        //getPath() / getAbsolutePath() 括号中相对路径查询出来的路径不同，getpath是相对路径， getAbsolutePath绝对路径，若括号中是绝对路径，查询出来相同
        //System.out.println(path + "------------" + new File(path).getAbsolutePath() + "------------------------------");
        //  获取文件名   String filename = file.getOriginalFilename();
        // 获取文件后缀名   String last = filename.substring(filename.lastIndexOf("."));
        //该路径是上传到什么位置
        File file2 = new File("D:" + File.pathSeparator + "img" + File.pathSeparator + file.getOriginalFilename());
        // 同上  File file2 = new File("D:\\img\\"+ file.getOriginalFilename());
        // 检测是否存在目录
        if (!file2.getParentFile().exists()) {
        	file2.getParentFile().mkdirs();
        }
        try {
        	file.transferTo(file2);
        	result.put("contentType", file.getContentType());
        	result.put("fileName", file.getOriginalFilename());
        	result.put("fileSize", file.getSize() + "");
        	result.put("result", "上传成功");
        	return result;
		} catch (IllegalStateException e) {
			e.printStackTrace();
		}catch (IOException e) {
            e.printStackTrace();
        }
        result.put("result", "上传失败");
        return result;
    }

    @PostMapping("/upload2")
    @ResponseBody
    public List<Map<String, String>> upload2(@RequestParam("file") MultipartFile[] files) throws IOException {
        if (files == null || files.length == 0) {
            return null;
        }
        List<Map<String, String>> results = new ArrayList<>();
        for (MultipartFile file : files) {
            // TODO Spring Mvc 提供的写入方式
            file.transferTo(new File("D:\\img\\" + file.getOriginalFilename()));
            Map<String, String> map = new HashMap<>(16);
            map.put("contentType", file.getContentType());
            map.put("fileName", file.getOriginalFilename());
            map.put("fileSize", file.getSize() + "");
            results.add(map);
        }
        return results;
    }

    @PostMapping("/upload3")
    @ResponseBody
    public void upload2(String base64) throws IOException {
        // TODO BASE64 方式的 格式和名字需要自己控制（如 png 图片编码后前缀就会是 data:image/png;base64,）
        final File tempFile = new File("D:\\img\\test.jpg");
        // TODO 防止有的传了 data:image/png;base64, 有的没传的情况
        String[] d = base64.split("base64,");
        final byte[] bytes = Base64Utils.decodeFromString(d.length > 1 ? d[1] : d[0]);
        FileCopyUtils.copy(bytes, tempFile);

    }
}