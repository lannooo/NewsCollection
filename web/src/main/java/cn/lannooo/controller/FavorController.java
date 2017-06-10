package cn.lannooo.controller;

import cn.lannooo.config.WebSecurityConfig;
import cn.lannooo.constants.ResultMessage;
import cn.lannooo.entity.database.NewsType;
import cn.lannooo.entity.database.User;
import cn.lannooo.entity.database.UserFavor;
import cn.lannooo.entity.model.NewsTypeModel;
import cn.lannooo.entity.model.UserFavorModel;
import cn.lannooo.service.NewsService;
import cn.lannooo.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by 51499 on 2017/5/22 0022.
 */
@Controller
@RequestMapping("/favor")
public class FavorController {

    Logger logger = LoggerFactory.getLogger(FavorController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private NewsService newsService;

    @RequestMapping("/add/{type_id}")
    @ResponseBody
    public Map<String, Object> createFavor(@PathVariable String type_id,
                                           @SessionAttribute(WebSecurityConfig.SESSION_KEY_USER) String username){
        Map<String, Object> result = new HashMap<>();
        int tid = Integer.valueOf(type_id);
        NewsType type = newsService.findTypeById(tid);
        User user = userService.findByUserName(username);
        logger.info("add favor:"+type.getType_name()+" for "+user.getUser_name());
        if(type!=null && user!=null){
            UserFavor favor = new UserFavor();
            if(userService.createOrUpdateFavor(favor, user, type)!=null) {
                result.put("success", true);
                result.put("message", ResultMessage.FAVOR_CREATE_SUCCESS);
            } else {
                result.put("success", false);
                result.put("message", ResultMessage.FAVOR_CREATE_FAIL);
            }
            return result;
        }else if(type==null){
            result.put("message", ResultMessage.FAVOR_TYPE_NOTFOUND);
        }else{
            result.put("message", ResultMessage.FAVOR_USER_NOTFOUND);
        }
        result.put("success", false);
        return result;
    }

    @RequestMapping("/get")
    @ResponseBody
    public Map<String, Object> getFavors(@SessionAttribute(WebSecurityConfig.SESSION_KEY_USER) String username){
        Map<String, Object> result = new HashMap<>();
        Set<UserFavorModel> data = new HashSet<>();
        User user = userService.findByUserName(username);
        if(user!=null){
            Set<UserFavor> favors = user.getFavors();
            for(UserFavor favor: favors){
                if(!favor.isValid())
                    continue;
                UserFavorModel model = new UserFavorModel(favor.getFavor_id(), new NewsTypeModel(favor.getNewsType()));
                data.add(model);
            }
            result.put("data", data);
            result.put("success", true);
            result.put("message", ResultMessage.FAVOR_GET_ALL);
        }else{
            result.put("success", false);
            result.put("message", ResultMessage.FAVOR_USER_NOTFOUND);
        }

        return result;
    }

    @RequestMapping("/delete/{tid}")
    @ResponseBody
    public Map<String, Object> deleteFavor(@PathVariable String tid,
                                           @SessionAttribute(WebSecurityConfig.SESSION_KEY_USER) String username){
        Map<String, Object> result = new HashMap<>();

        int typeId = Integer.valueOf(tid);
        User user = userService.findByUserName(username);
        UserFavor favor = userService.findFavorByTypeId(user.getUser_id(), typeId);
        logger.info("delete favor:"+favor.getNewsType().getType_name()+" for "+user.getUser_name());
        if(favor!=null && user != null){
            if(favor.getUser().getUser_id() == user.getUser_id()) {
                // update the favor to invalid
                userService.deleteFavor(favor);
            }
            result.put("success", true);
            result.put("message", ResultMessage.FAVOR_DELETE_SUCCESS);
        }else{
            result.put("success", false);
            result.put("message", ResultMessage.FAVOR_USER_NOTMATCH);
        }

        return result;
    }


}
