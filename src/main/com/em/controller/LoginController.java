package main.com.em.controller;

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

import javax.servlet.http.HttpServletRequest;
import javax.swing.*;

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

    public String login(User user, HttpServletRequest req) throws Exception {

        //Shiro实现登录
        UsernamePasswordToken token = new UsernamePasswordToken(user.getId(), user.getPassword());
        Subject subject = SecurityUtils.getSubject();

        /*String errorClassName = (String) req.getAttribute(FormAuthenticationFilter.DEFAULT_ERROR_KEY_ATTRIBUTE_NAME);
        if (UnknownAccountException.class.getName().equals(errorClassName)) {
            req.setAttribute("error", "用户名/密码错误");
        } else if (IncorrectCredentialsException.class.getName().equals(errorClassName)) {
            req.setAttribute("error", "用户名/密码错误");
        } else if (errorClassName != null) {
            req.setAttribute("error", "未知错误：" + errorClassName);
        }*/
        //return "login.jsp";


        //如果获取不到用户名就是登录失败，但登录失败的话，会直接抛出异常
        //subject.login(token);




        try {
            subject.login(token);
            subject.getSession().setAttribute("user",user);
        } catch (UnknownAccountException ua) {
            //return "redirect:/admin/showRoom";
            //return "/login";
           System.out.println("未知账号！（提示：若已成功注册，请联系管理员查看用户是否已激活。）：" + ua.getMessage());
           JOptionPane.showMessageDialog(null, "用户不存在，请联系管理员注册！", "登录错误", JOptionPane.ERROR_MESSAGE);
            //return ResultUtil.error(500, "未知账号！（提示：若已成功注册，请联系管理员查看用户是否已激活。）");
            return "redirect:/admin/unKnown";
        } catch (IncorrectCredentialsException ice) {

            JOptionPane.showMessageDialog(null, "密码错误！", "登录错误", JOptionPane.ERROR_MESSAGE);
            return "redirect:/admin/error";
            //System.out.println("密码输入错误！请您重新输入！：" + ice.getMessage());
            //return ResultUtil.error(500, "密码输入错误！请您重新输入!");
            //return "redirect:/admin/showRoom";
            /*int res = JOptionPane.showConfirmDialog(null, "是否继续操作", "是否继续", JOptionPane.YES_NO_OPTION);
            if (res == JOptionPane.YES_OPTION) {
                System.out.println("选择是后执行的代码"); // 点击“是”后执行这个代码块
            } else {
                //System.out.println("选择否后执行的代码"); // 点击“否”后执行这个代码块
                return "/login";
            }*/
        }



        if (subject.hasRole("admin")) {
            return "redirect:/admin/showRoom";
        } else if (subject.hasRole("ordinary")) {
            return "redirect:/ordinary/showRoom";
        }

        return "/login";
    }
}