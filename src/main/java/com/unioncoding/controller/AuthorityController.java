package com.unioncoding.controller;

import com.unioncoding.dao.AuthorityRepository;
import com.unioncoding.dao.UserRepository;
import com.unioncoding.model.Authority;
import com.unioncoding.model.User;
import com.unioncoding.utils.ErrorTypeEnum;
import com.unioncoding.utils.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 吴晓冬 on 2017/9/10.
 */
@Controller
@RequestMapping("/authorities")
public class AuthorityController
{
    @Autowired
    private AuthorityRepository authorityRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/save1")
    @ResponseBody
    public Authority save1()
    {
        Authority authority = new Authority();
        authority.setAuthority("test");
        authority.setName("");
//        authority.setFunctions(new ArrayList<>());

        try
        {
            authorityRepository.save(authority);
        }
        catch (ConstraintViolationException e)//获取参数校验异常
        {
            List<String> msgList = new ArrayList<>();
            for (ConstraintViolation<?> constraintViolation : e.getConstraintViolations())
            {
                msgList.add(constraintViolation.getMessage());
            }
            String messages = StringUtils.arrayToDelimitedString(msgList.toArray(new String[msgList.size()]), ";");
            System.out.println(messages);
        }

        return authority;
    }

    @GetMapping("/save2")
    @ResponseBody
    public User save2()
    {
        User user = new User();
        user.setUsername("test");
        user.setName("");
//        user.setAuthorities(new ArrayList<>());

        try
        {
            userRepository.save(user);
        }
        catch (ConstraintViolationException e)//获取参数校验异常
        {
            List<String> msgList = new ArrayList<>();
            for (ConstraintViolation<?> constraintViolation : e.getConstraintViolations())
            {
                msgList.add(constraintViolation.getMessage());
            }
            String messages = StringUtils.arrayToDelimitedString(msgList.toArray(new String[msgList.size()]), ";");
            System.out.println(messages);
        }

        return user;
    }
}
