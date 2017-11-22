package com.unioncoding.controller;

import com.unioncoding.service.file.FileService;
import com.unioncoding.utils.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.StandardMultipartHttpServletRequest;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by 吴晓冬 on 2017/10/26.
 */
@Controller
public class UploadController
{
    @Autowired
    private FileService fileService;

    @PostMapping("/pasteImage")
    @ResponseBody
    public Response pasteImage(StandardMultipartHttpServletRequest request)
    {
        MultiValueMap<String, MultipartFile> map = request.getMultiFileMap();
        Iterator<String> iterator = map.keySet().iterator();
        while (iterator.hasNext())
        {
            String key = iterator.next();
            List<MultipartFile> files = map.get(key);
            for (MultipartFile file : files)
            {
                Map<String, String> body = new HashMap<>();
                body.put("url", fileService.save(file));
                body.put("filename", file.getOriginalFilename());

                Response response = new Response("0000", "操作成功");
                response.setBody(body);

                return response;
            }
        }

        return new Response("0001", "上传失败");
    }

    @ResponseBody
    @RequestMapping(value = "/uploadFileForKindeditor.do", method = RequestMethod.POST)
    public Map<String, Object> uploadFileForKindeditor(StandardMultipartHttpServletRequest request)
    {
        Map<String, Object> resultMap = new HashMap<>();

        MultiValueMap<String, MultipartFile> map = request.getMultiFileMap();
        Iterator<String> iterator = map.keySet().iterator();
        while (iterator.hasNext())
        {
            String key = iterator.next();
            List<MultipartFile> files = map.get(key);
            for (MultipartFile file : files)
            {
                resultMap.put("error", 0);
                resultMap.put("url", fileService.save(file));

                return resultMap;
            }
        }

        resultMap.put("error", 1);
        resultMap.put("message", "上传错误");
        return resultMap;
    }
}
