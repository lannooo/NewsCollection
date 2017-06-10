package cn.lannooo.controller;

import cn.lannooo.config.WebSecurityConfig;
import cn.lannooo.constants.ResultMessage;
import cn.lannooo.entity.database.User;
import cn.lannooo.service.UserService;
import cn.lannooo.util.InputUtil;
import cn.lannooo.util.MD5Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by 51499 on 2017/5/21 0021.
 */
@Controller
@RequestMapping("/user")
public class UserController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserService userService;

    @PostMapping("/loginPost")
    @ResponseBody
    public Map<String, Object> loginPost(String username,
                                         String password,
                                         HttpSession session){
        Map<String, Object> map =  new HashMap<>();
        logger.info("User login:username={}, password={}", username, password);
        User user = userService.findByUserName(username);
        if(null!=user && user.getUser_password().equals(MD5Util.md5(password))){
            session.setAttribute(WebSecurityConfig.SESSION_KEY_USER, username);
            map.put("success", true);
            map.put("userInfo", username);
            map.put("message", "login success");
            return map;
        }else{
            map.put("success", false);
            map.put("message", "password incorrect");
            return map;
        }
    }

    @RequestMapping("/logout")
    @ResponseBody
    public Map<String, Object> logout(HttpSession session) {
        logger.info("User {} logout",session.getAttribute(WebSecurityConfig.SESSION_KEY_USER));
        // 移除session
        session.removeAttribute(WebSecurityConfig.SESSION_KEY_USER);
        Map<String,Object> result = new HashMap<>();
        result.put("success", true);
        return result;
    }

    @RequestMapping("/register")
    @ResponseBody
    public Map<String, Object> register(String username,
                                        String password,
                                        String email){
        Map<String, Object> result = new HashMap<>();
        logger.info("Register: username:{}, password:{}, email:{}", username, password, email);
        if(InputUtil.checkUserNameValid(username)&&
                InputUtil.checkEmailValid(email)&&
                InputUtil.checkPasswordValid(password)){
            if(!userService.isUserNameExist(username)&&!userService.isEmailExist(email)){
                User user = userService.createUser(username, MD5Util.md5(password), email);
                result.put("success", true);
                result.put("message", ResultMessage.REGISTER_SUCCESS);
                result.put("data", user.getUser_id());
            }else{
                result.put("success", false);
                result.put("message", ResultMessage.EMAIL_OR_USERNAME_EXIST);
            }
        }else{
            result.put("success", false);
            result.put("message", ResultMessage.REGISTER_FAILED);
        }
        return result;
    }

    @RequestMapping(value = "/check/username")
    @ResponseBody
    public Map<String, Object> checkUserName(String user){
        Map<String, Object> result = new HashMap<>();
        logger.info("Check username:{}", user);
        if(InputUtil.checkUserNameValid(user)){
            if(!userService.isUserNameExist(user)){
                result.put("valid", true);
                result.put("message", ResultMessage.USERNAME_VALID);
            }else{
                result.put("valid", false);
                result.put("message", ResultMessage.USERNAME_EXIST);
            }
        }else{
            result.put("valid", false);
            result.put("message", ResultMessage.USERNAME_INVALID);
        }
        return result;
    }

    @RequestMapping(value = "/check/email")
    @ResponseBody
    public Map<String, Object> checkEmail(String email){
        Map<String, Object> result = new HashMap<>();
        logger.info("Check email:{}", email);
        if(InputUtil.checkEmailValid(email)){
            if(!userService.isEmailExist(email)){
                result.put("valid", true);
                result.put("message", ResultMessage.EMAIL_VALID);
            }else{
                result.put("valid", false);
                result.put("message", ResultMessage.EMAIL_EXIST);
            }
        }else{
            result.put("valid",false);
            result.put("message", ResultMessage.EMAIL_INVALID);
        }
        return result;
    }

}
