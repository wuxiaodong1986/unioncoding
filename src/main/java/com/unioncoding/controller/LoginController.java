package com.unioncoding.controller;

import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

/**
 * Created by 吴晓冬 on 2017/9/1.
 */
@Controller
public class LoginController
{
    @GetMapping("/")
    public String index()
    {
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String login(HttpServletRequest request)
    {
//        Enumeration<String> enumeration = request.getAttributeNames();
//        while (enumeration.hasMoreElements())
//        {
//            String key = enumeration.nextElement();
//            String value = request.getAttribute(key).toString();
//            System.out.println(key + " : " + value);
//        }
        return "login";
    }

    @GetMapping(value = "/main")
    public String welcome()
    {
        return "frame/main";
    }
}