package com.unioncoding.controller;

import com.unioncoding.dao.AuthorityRepository;
import com.unioncoding.dao.UserRepository;
import com.unioncoding.model.Authority;
import com.unioncoding.model.User;
import com.unioncoding.utils.CustomException;
import com.unioncoding.utils.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 吴晓冬 on 2017/9/4.
 */
@Controller
@RequestMapping("/users")
public class UserController
{
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthorityRepository authorityRepository;

    @Value("${pageSize}")
    private Integer pageSize;

    /**
     * 分页查询用户信息
     */
    @RequestMapping(method = {RequestMethod.GET, RequestMethod.POST})
    public String list(Model model, User user, @RequestParam(value = "pageNumber", required = false, defaultValue = "0") Integer pageNumber)
    {
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);//设置string为模糊查询
        PageRequest pageRequest = new PageRequest(pageNumber, pageSize);

        Page<User> userPage = userRepository.findAll(Example.of(user, matcher), pageRequest);
        model.addAttribute("page", userPage);
        model.addAttribute("user", user);
        model.addAttribute("title", "用户管理");

        return "users/list";
    }

    /**
     * 进入新增页面
     */
    @GetMapping("/save")
    public String save(Model model)
    {
        List<Authority> authorities = authorityRepository.findAll();
        model.addAttribute("authorities", authorities);

        model.addAttribute("title", "新建用户");

        User user = new User();
        user.setAuthorities(new ArrayList<>());
        model.addAttribute("user", user);

        return "users/save";
    }

    /**
     * 进入修改用户页面
     */
    @GetMapping("/save/{id}")
    public String save(Model model, @PathVariable("id") Long id)
    {
        List<Authority> authorities = authorityRepository.findAll();
        model.addAttribute("authorities", authorities);

        User user = userRepository.findOne(id);
        model.addAttribute("title", "修改用户");
        model.addAttribute("user", user);

        return "users/save";
    }

    /**
     * 保存用户信息
     */
    @PostMapping("/save")
    @ResponseBody
    public Response save(User user)
    {
        //保存前判断用户名是否已存在
        User oldUser = userRepository.findByUsername(user.getUsername());
        if (null != oldUser && oldUser.getId() != user.getId())
        {
            throw new CustomException("9999", "用户名已被使用");
        }

        //只保存用户信息，不修改密码，因为jpa默认全更新，所以从数据库中查询出原密码;如是新用户，则密码为初始密码111111
        String password = new BCryptPasswordEncoder().encode("111111");
        if (null != user.getId())
        {
            oldUser = userRepository.findOne(user.getId());
            if (null != oldUser)
            {
                password = oldUser.getPassword();
            }
        }
        user.setPassword(password);

        userRepository.save(user);

        return new Response("0000", "操作成功");
    }

    /**
     * 删除用户信息
     */
    @DeleteMapping("/{id}")
    @ResponseBody
    public Response delete(@PathVariable("id") Long id)
    {
        userRepository.delete(id);

        return new Response("0000", "操作成功");
    }
}