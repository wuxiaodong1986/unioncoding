package com.unioncoding.controller;

import com.unioncoding.dao.SysAuthorityRepository;
import com.unioncoding.dao.SysUserRepository;
import com.unioncoding.model.SysAuthority;
import com.unioncoding.model.SysUser;
import com.unioncoding.utils.CustomException;
import com.unioncoding.utils.Response;
import com.unioncoding.utils.XlsView;
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
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 用户管理
 * Created by 吴晓冬 on 2017/9/4.
 */
@Controller
@RequestMapping("/users")
public class SysUserController
{
    @Autowired
    private SysUserRepository userRepository;

    @Autowired
    private SysAuthorityRepository authorityRepository;

    @Value("${pageSize}")
    private Integer pageSize;

    /**
     * 分页查询信息
     */
    @RequestMapping(method = {RequestMethod.GET, RequestMethod.POST})
    public String list(Model model, SysUser user, @RequestParam(value = "pageNumber", required = false, defaultValue = "0") Integer pageNumber)
    {
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);//设置string为模糊查询
        PageRequest pageRequest = new PageRequest(pageNumber, pageSize);

        Page<SysUser> page = userRepository.findAll(Example.of(user, matcher), pageRequest);
        model.addAttribute("page", page);
        model.addAttribute("user", user);
        model.addAttribute("title", "用户管理");

        return "users/list";
    }

    /**
     * 根据查询条件生成下载xls
     */
    @GetMapping("/xls")
    public XlsView xls(SysUser user)
    {
        ExampleMatcher matcher = ExampleMatcher.matching().withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);//设置string为模糊查询
        List<SysUser> users = userRepository.findAll(Example.of(user, matcher));

        //查询展示内容
        Map<String, String> titles = new LinkedHashMap<>();
        titles.put("id","id");
        titles.put("username","用户名");
        titles.put("enabled","是否可用");
        titles.put("name","姓名");
        titles.put("phone","手机号");
        titles.put("email","电子邮箱");

        XlsView view = new XlsView();
        view.addStaticAttribute("fileName", "用户信息.xls");
        view.addStaticAttribute("titles", titles);
        view.addStaticAttribute("objects", users);

        return view;
    }

    /**
     * 进入新增页面
     */
    @GetMapping("/save")
    public String save(Model model)
    {
        List<SysAuthority> authorities = authorityRepository.findAll();
        model.addAttribute("authorities", authorities);

        model.addAttribute("title", "新建用户");

        SysUser user = new SysUser();
        user.setAuthorities(new ArrayList<>());
        model.addAttribute("user", user);

        return "users/save";
    }

    /**
     * 进入修改页面
     */
    @GetMapping("/save/{id}")
    public String save(Model model, @PathVariable("id") Long id)
    {
        List<SysAuthority> authorities = authorityRepository.findAll();
        model.addAttribute("authorities", authorities);

        SysUser user = userRepository.findOne(id);
        model.addAttribute("title", "修改用户");
        model.addAttribute("user", user);

        return "users/save";
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    @ResponseBody
    public Response save(SysUser user)
    {
        //保存前判断用户名是否已存在
        SysUser oldUser = userRepository.findByUsername(user.getUsername());
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
     * 删除
     */
    @DeleteMapping("/{id}")
    @ResponseBody
    public Response delete(@PathVariable("id") Long id)
    {
        userRepository.delete(id);

        return new Response("0000", "操作成功");
    }
}