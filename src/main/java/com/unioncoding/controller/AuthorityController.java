package com.unioncoding.controller;

import com.unioncoding.dao.AuthorityRepository;
import com.unioncoding.dao.FunctionRepository;
import com.unioncoding.model.Authority;
import com.unioncoding.model.Function;
import com.unioncoding.utils.CustomException;
import com.unioncoding.utils.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * 角色管理
 * Created by 吴晓冬 on 2017/9/10.
 */
@Controller
@RequestMapping("/authorities")
public class AuthorityController
{
    @Autowired
    private AuthorityRepository authorityRepository;

    @Autowired
    private FunctionRepository functionRepository;

    @Value("${pageSize}")
    private Integer pageSize;

    /**
     * 分页查询
     */
    @RequestMapping(method = {RequestMethod.GET, RequestMethod.POST})
    public String list(Model model, Authority authority, @RequestParam(value = "pageNumber", required = false, defaultValue = "0") Integer pageNumber)
    {
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);//设置string为模糊查询
        PageRequest pageRequest = new PageRequest(pageNumber, pageSize);

        Page<Authority> page = authorityRepository.findAll(Example.of(authority, matcher), pageRequest);
        model.addAttribute("page", page);
        model.addAttribute("authority", authority);
        model.addAttribute("title", "角色管理");

        return "authorities/list";
    }

    /**
     * 进入新增页面
     */
    @GetMapping("/save")
    public String save(Model model)
    {
        List<Function> functions = functionRepository.findAll();
        model.addAttribute("functions", functions);

        model.addAttribute("title", "新建角色");

        Authority authority = new Authority();
        authority.setFunctions(new ArrayList<>());
        model.addAttribute("authority", authority);

        return "authorities/save";
    }

    /**
     * 进入修改页面
     */
    @GetMapping("/save/{id}")
    public String save(Model model, @PathVariable("id") String id)
    {
        List<Function> functions = functionRepository.findAll();
        model.addAttribute("functions", functions);

        Authority authority = authorityRepository.findOne(id);
        model.addAttribute("title", "修改角色");
        model.addAttribute("authority", authority);

        return "authorities/save";
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    @ResponseBody
    public Response save(Authority authority, String title)
    {
        //保存前判断角色名是否已存在
        Authority oldAuthority = authorityRepository.findOne(authority.getAuthority());
        if ("新建角色".equals(title) && null != oldAuthority)
        {
            throw new CustomException("9999", "角色名已被使用");
        }

        authorityRepository.save(authority);

        return new Response("0000", "操作成功");
    }

    /**
     * 删除
     */
    @DeleteMapping("/{id}")
    @ResponseBody
    public Response delete(@PathVariable("id") String id)
    {
        authorityRepository.delete(id);

        return new Response("0000", "操作成功");
    }
}