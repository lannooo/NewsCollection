package cn.lannooo.controller;

import cn.lannooo.config.WebSecurityConfig;
import cn.lannooo.constants.ResultMessage;
import cn.lannooo.entity.database.User;
import cn.lannooo.entity.database.UserHistory;
import cn.lannooo.entity.model.UserHistoryModel;
import cn.lannooo.service.NewsService;
import cn.lannooo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by 51499 on 2017/5/28 0028.
 */
@Controller
@RequestMapping("/history")
public class HistoryController {
    @Autowired
    private UserService userService;
    @Autowired
    private NewsService newsService;

    @RequestMapping("/get")
    @ResponseBody
    public Map<String, Object> getHistory(@SessionAttribute(WebSecurityConfig.SESSION_KEY_USER) String username){
        Map<String, Object> result = new HashMap<>();
        Set<UserHistoryModel> data = new HashSet<>();
        User user = userService.findByUserName(username);
        if(null!=user){
            Set<UserHistory> histories = user.getHistories();
            if(histories!=null){
                for(UserHistory history: histories){
                    UserHistoryModel model = new UserHistoryModel(history);
                    data.add(model);
                }
            }
            result.put("data", data);
            result.put("success", true);
            result.put("message", ResultMessage.HISTORY_GET_ALL);
        }else{
            result.put("success", false);
            result.put("message", ResultMessage.HISTORY_USER_NOTFOUND);
        }
        return result;
    }
}
