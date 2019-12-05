package main.com.em.controller;

import main.com.em.domain.Result;
import main.com.em.domain.User;
import main.com.em.tool.ResultUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by Carson on 2017/11/30.
 */
@Controller
public class LoginController {

    //登录跳转
    @RequestMapping(value = "/login", method = {RequestMethod.GET})
    public String loginUI() throws Exception {
        return "../../login";
    }

    //登录表单处理
    @RequestMapping(value = "/login", method = {RequestMethod.POST})

    public String login(User user) throws Exception {

        //Shiro实现登录
        UsernamePasswordToken token = new UsernamePasswordToken(user.getId(), user.getPassword());
        Subject subject = SecurityUtils.getSubject();

        //如果获取不到用户名就是登录失败，但登录失败的话，会直接抛出异常
        subject.login(token);
        /*try {
            subject.login(token);
            subject.getSession().setAttribute("user", user);
        } catch (UnknownAccountException ua) {
            System.out.println("未知账号！（提示：若已成功注册，请联系管理员查看用户是否已激活。）：" + ua.getMessage());
            return ResultUtil.error(500, "未知账号！（提示：若已成功注册，请联系管理员查看是否已激活。）");
        } catch (IncorrectCredentialsException ice) {
            System.out.println("密码输入错误！请您重新输入！：" + ice.getMessage());
            return ResultUtil.error(500, "密码输入错误！请您重新输入!");

        }*/

        /*if (subject.hasRole("admin")) {
            return ResultUtil.success("redirect:/admin/showRoom");
        } else if (subject.hasRole("ordinary")) {
            return ResultUtil.success("redirect:/ordinary/showRoom");
        }

        return ResultUtil.success("/login");*/
        if (subject.hasRole("admin")) {
            return "redirect:/admin/showRoom";
        } else if (subject.hasRole("ordinary")) {
            return "redirect:/ordinary/showRoom";
        }

        return "/login";
    }
}