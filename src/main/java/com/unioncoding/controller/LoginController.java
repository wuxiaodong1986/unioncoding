package com.unioncoding.controller;

import com.unioncoding.model.Authority;
import com.unioncoding.model.Function;
import com.unioncoding.model.User;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

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
    public String login()
    {
        return "login";
    }

    @GetMapping(value = "/main")
    public ModelAndView main(HttpSession session, Model model)
    {
        //获取已登录用户信息
        SecurityContextImpl securityContextImpl = (SecurityContextImpl) session.getAttribute("SPRING_SECURITY_CONTEXT");
        User user = (User) securityContextImpl.getAuthentication().getPrincipal();

        //获取该用户可访问所有菜单并去重
        List<Function> functionList = new ArrayList<>();
        for (Authority authority : user.getAuthorities())
        {
            for (Function function : authority.getFunctions())
            {
                if (!functionList.contains(function))
                {
                    functionList.add(function);
                }
            }
        }

        //生成菜单树
        Function root = new Function("0", "");
        functionList.add(0, root);
        for (int i = 0; i < functionList.size(); i++)
        {
            for (int j = 0; j < functionList.size(); j++)
            {
                if (functionList.get(i).getId().equals(functionList.get(j).getpId()))
                {
                    if (!functionList.get(i).getFunctions().contains(functionList.get(j)))
                    {
                        functionList.get(i).getFunctions().add(functionList.get(j));
                    }
                }
            }
        }

        model.addAttribute("functions", root.getFunctions());

        return new ModelAndView("frame/main", "model", model);
    }
}