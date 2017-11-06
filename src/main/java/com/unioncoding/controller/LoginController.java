package com.unioncoding.controller;

import com.unioncoding.dao.SysUserRepository;
import com.unioncoding.model.SysAuthority;
import com.unioncoding.model.SysFunction;
import com.unioncoding.model.SysUser;
import com.unioncoding.service.UserService;
import com.unioncoding.utils.CustomException;
import com.unioncoding.utils.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
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
    @Autowired
    private SysUserRepository userRepository;

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
        UserService.SysUserDetails userDetails = (UserService.SysUserDetails)  securityContextImpl.getAuthentication().getPrincipal();

        SysUser user = userDetails.getUser();
        List<SysAuthority> authorities = (List<SysAuthority>) userDetails.getAuthorities();
        //获取该用户可访问所有菜单并去重
        List<SysFunction> functionList = new ArrayList<>();
        for (SysAuthority authority : authorities)
        {
            for (SysFunction function : authority.getFunctions())
            {
                if (!functionList.contains(function))
                {
                    functionList.add(function);
                }
            }
        }

        //生成菜单树
        SysFunction root = new SysFunction("0", "");
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

        model.addAttribute("user", user);
        model.addAttribute("functions", root.getFunctions());

        return new ModelAndView("frame/main", "model", model);
    }

    /**
     * 个人信息页
     */
    @GetMapping(value = "/personal")
    public ModelAndView personal(HttpSession session, Model model)
    {
        //获取已登录用户信息
        SecurityContextImpl securityContextImpl = (SecurityContextImpl) session.getAttribute("SPRING_SECURITY_CONTEXT");
        UserService.SysUserDetails userDetails = (UserService.SysUserDetails)  securityContextImpl.getAuthentication().getPrincipal();

        SysUser user = userRepository.findOne(userDetails.getUser().getId());

        model.addAttribute("title", "个人信息");
        model.addAttribute("user", user);
        return new ModelAndView("frame/personal", "model", model);
    }

    /**
     * 保存个人信息
     */
    @PostMapping("/personal")
    @ResponseBody
    public Response savePersonal(HttpSession session, SysUser newUser)
    {
        //获取已登录用户信息
        SecurityContextImpl securityContextImpl = (SecurityContextImpl) session.getAttribute("SPRING_SECURITY_CONTEXT");
        UserService.SysUserDetails userDetails = (UserService.SysUserDetails)  securityContextImpl.getAuthentication().getPrincipal();
        SysUser user = userDetails.getUser();

        userRepository.updateForPersonal(newUser.getName(), newUser.getPhone(), newUser.getEmail(), user.getId());

        return new Response("0000", "操作成功");
    }

    /**
     * 修改个人密码页
     */
    @GetMapping(value = "/updatePwd")
    public ModelAndView updatePwdPage(Model model)
    {
        model.addAttribute("title", "修改密码");
        return new ModelAndView("frame/updatePwd", "model", model);
    }

    /**
     * 修改个人密码
     */
    @PostMapping("/updatePwd")
    @ResponseBody
    public Response updatePwd(HttpSession session, String oldPassword, String newPassword)
    {
        //获取已登录用户信息
        SecurityContextImpl securityContextImpl = (SecurityContextImpl) session.getAttribute("SPRING_SECURITY_CONTEXT");
        UserService.SysUserDetails userDetails = (UserService.SysUserDetails)  securityContextImpl.getAuthentication().getPrincipal();
        SysUser user = userDetails.getUser();

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        if (!passwordEncoder.matches(oldPassword, user.getPassword()))
        {
            throw new CustomException("9999", "原密码错误");
        }

        userRepository.updatePassword(passwordEncoder.encode(newPassword), user.getId());

        return new Response("0000", "操作成功");
    }
}