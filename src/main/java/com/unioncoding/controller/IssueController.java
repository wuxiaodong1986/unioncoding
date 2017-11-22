package com.unioncoding.controller;

import com.unioncoding.dao.IssueRepository;
import com.unioncoding.dao.ProjectRepository;
import com.unioncoding.dao.SysUserRepository;
import com.unioncoding.model.Issue;
import com.unioncoding.model.Project;
import com.unioncoding.model.SysUser;
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

import java.util.List;

/**
 * 问题管理
 * Created by 吴晓冬 on 2017/10/30.
 */
@Controller
@RequestMapping("/issues")
public class IssueController
{
    @Autowired
    private IssueRepository repository;

    @Autowired
    private SysUserRepository userRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Value("${pageSize}")
    private Integer pageSize;


    /**
     * 分页查询信息
     */
    @RequestMapping(method = {RequestMethod.GET, RequestMethod.POST})
    public String list(Model model, Issue object, @RequestParam(value = "pageNumber", required = false, defaultValue = "0") Integer pageNumber)
    {
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);//设置string为模糊查询
        PageRequest pageRequest = new PageRequest(pageNumber, pageSize);

        Page<Issue> page = repository.findAll(Example.of(object, matcher), pageRequest);

        List<SysUser> users = userRepository.findAll();

        model.addAttribute("users", users);
        model.addAttribute("page", page);
        model.addAttribute("object", object);
        model.addAttribute("title", "问题管理");

        return "issues/list";
    }

    /**
     * 进入新增页面
     */
    @GetMapping("/save")
    public String save(Model model)
    {
        List<SysUser> users = userRepository.findAll();
        model.addAttribute("users", users);

        List<Project> projects = projectRepository.findAll();
        model.addAttribute("projects", projects);

        model.addAttribute("title", "新建问题");

        model.addAttribute("object", new Issue());

        return "issues/save";
    }

    /**
     * 进入修改页面
     */
    @GetMapping("/save/{id}")
    public String save(Model model, @PathVariable("id") Long id)
    {
        List<SysUser> users = userRepository.findAll();
        model.addAttribute("users", users);

        List<Project> projects = projectRepository.findAll();
        model.addAttribute("projects", projects);

        Issue object = repository.findOne(id);
        model.addAttribute("object", object);

        model.addAttribute("title", "修改问题");

        return "issues/save";
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    @ResponseBody
    public Response save(Issue object)
    {
        repository.save(object);

        return new Response("0000", "操作成功");
    }

    /**
     * 删除
     */
    @DeleteMapping("/{id}")
    @ResponseBody
    public Response delete(@PathVariable("id") Long id)
    {
        repository.delete(id);

        return new Response("0000", "操作成功");
    }
}