package com.unioncoding.controller;

import com.sun.org.apache.xpath.internal.operations.Mod;
import com.unioncoding.dao.UserRepository;
import com.unioncoding.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * Created by 吴晓冬 on 2017/9/4.
 */
@RestController
@RequestMapping("/users")
public class UserController
{
    @Autowired
    private UserRepository userRepository;

    @Value("${pageSize}")
    private Integer pageSize;

    /**
     * 分页查询用户信息
     */
    @GetMapping
    public ModelAndView list(Model model, User user, @RequestParam(value = "pageNumber", required = false, defaultValue = "0") Integer pageNumber)
    {
        ExampleMatcher matcher = ExampleMatcher.matching();
        PageRequest pageRequest = new PageRequest(pageNumber, pageSize);

        Page<User> userPage = userRepository.findAll(Example.of(user, matcher), pageRequest);

        model.addAttribute("page", userPage);
        model.addAttribute("title", "用户管理");

        return new ModelAndView("users/list", "userModel", model);
    }

    /**
     * 进入新增/修改用户页面
     */
    @GetMapping("/update")
    public ModelAndView form(Model model, Long id)
    {
        if (null == id || 0 == id)
        {
            model.addAttribute("title", "新建用户");
        }
        else
        {
            User user = userRepository.findOne(id);
            model.addAttribute("title", "修改用户");
            model.addAttribute("user", user);
        }
        return new ModelAndView("users/form", "userModel", model);
    }
}