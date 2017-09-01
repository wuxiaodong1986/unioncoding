package com.unioncoding.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Created by 吴晓冬 on 2017/9/1.
 */
@Controller
public class LoginController
{
    @GetMapping(value = "/login")
    public String login()
    {
        return "login";
    }
}