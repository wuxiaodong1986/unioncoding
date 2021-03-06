package com.unioncoding.controller;

import com.unioncoding.dao.SysFunctionRepository;
import com.unioncoding.model.SysFunction;
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


/**
 * 菜单管理
 * Created by 吴晓冬 on 2017/9/11.
 */
@Controller
@RequestMapping("/functions")
public class SysFunctionController
{
    @Autowired
    private SysFunctionRepository functionRepository;

    @Value("${pageSize}")
    private Integer pageSize;

    /**
     * 分页查询
     */
    @RequestMapping(method = {RequestMethod.GET, RequestMethod.POST})
    public String list(Model model, SysFunction function, @RequestParam(value = "pageNumber", required = false, defaultValue = "0") Integer pageNumber)
    {
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);//设置string为模糊查询
        PageRequest pageRequest = new PageRequest(pageNumber, pageSize);

        Page<SysFunction> page = functionRepository.findAll(Example.of(function, matcher), pageRequest);
        model.addAttribute("page", page);
        model.addAttribute("function", function);
        model.addAttribute("title", "菜单管理");

        return "functions/list";
    }

    /**
     * 进入新增页面
     */
    @GetMapping("/save")
    public String save(Model model)
    {
        model.addAttribute("title", "新建菜单");

        SysFunction function = new SysFunction();
        model.addAttribute("function", function);

        return "functions/save";
    }

    /**
     * 进入修改页面
     */
    @GetMapping("/save/{id}")
    public String save(Model model, @PathVariable("id") String id)
    {
        SysFunction function = functionRepository.findOne(id);
        model.addAttribute("title", "修改菜单");
        model.addAttribute("function", function);

        return "functions/save";
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    @ResponseBody
    public Response save(SysFunction function, String title)
    {
        //保存前判断菜单编号是否已存在
        SysFunction oldFunction = functionRepository.findOne(function.getId());

        if ("新建菜单".equals(title) && null != oldFunction)
        {
            throw new CustomException("9999", "菜单编号已被使用");
        }

        functionRepository.save(function);

        return new Response("0000", "操作成功");
    }

    /**
     * 删除
     */
    @DeleteMapping("/{id}")
    @ResponseBody
    public Response delete(@PathVariable("id") String id)
    {
        functionRepository.delete(id);

        return new Response("0000", "操作成功");
    }
}
