package cn.lannooo.controller;

import cn.lannooo.config.WebSecurityConfig;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by 51499 on 2017/4/8 0008.
 */
@Controller
public class Maincontroller {

    @GetMapping("/index")
    public String index(){
        return "index";
    }
}
