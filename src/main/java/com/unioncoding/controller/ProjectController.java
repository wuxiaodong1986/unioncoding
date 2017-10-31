package com.unioncoding.controller;

import com.unioncoding.dao.ProjectRepository;
import com.unioncoding.dao.SysUserRepository;
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
 * 项目管理
 * Created by 吴晓冬 on 2017/10/25.
 */
@Controller
@RequestMapping("/projects")
public class ProjectController
{
    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private SysUserRepository userRepository;

    @Value("${pageSize}")
    private Integer pageSize;

    /**
     * 分页查询信息
     */
    @RequestMapping(method = {RequestMethod.GET, RequestMethod.POST})
    public String list(Model model, Project project, @RequestParam(value = "pageNumber", required = false, defaultValue = "0") Integer pageNumber)
    {
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);//设置string为模糊查询
        PageRequest pageRequest = new PageRequest(pageNumber, pageSize);

        Page<Project> page = projectRepository.findAll(Example.of(project, matcher), pageRequest);

        List<SysUser> users = userRepository.findAll();

        model.addAttribute("users", users);
        model.addAttribute("page", page);
        model.addAttribute("project", project);
        model.addAttribute("title", "项目管理");

        return "projects/list";
    }

    /**
     * 进入新增页面
     */
    @GetMapping("/save")
    public String save(Model model)
    {
        List<SysUser> users = userRepository.findAll();
        model.addAttribute("users", users);

        model.addAttribute("title", "新建项目");

        Project project = new Project();
        model.addAttribute("project", project);

        return "projects/save";
    }

    /**
     * 进入修改页面
     */
    @GetMapping("/save/{id}")
    public String save(Model model, @PathVariable("id") Long id)
    {
        List<SysUser> users = userRepository.findAll();
        model.addAttribute("users", users);

        Project project = projectRepository.findOne(id);
        model.addAttribute("title", "修改项目");
        model.addAttribute("project", project);

        return "projects/save";
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    @ResponseBody
    public Response save(Project project)
    {
        projectRepository.save(project);

        return new Response("0000", "操作成功");
    }

    /**
     * 删除
     */
    @DeleteMapping("/{id}")
    @ResponseBody
    public Response delete(@PathVariable("id") Long id)
    {
        projectRepository.delete(id);

        return new Response("0000", "操作成功");
    }

    /**
     * 进入查看页面
     */
    @GetMapping("/view/{id}")
    public String view(Model model, @PathVariable("id") Long id)
    {
        Project project = projectRepository.findOne(id);
        model.addAttribute("title", "查看项目");
        model.addAttribute("project", project);

        return "projects/view";
    }
}
