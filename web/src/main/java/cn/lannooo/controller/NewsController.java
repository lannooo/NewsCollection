package cn.lannooo.controller;

import cn.lannooo.config.WebSecurityConfig;
import cn.lannooo.constants.ResultMessage;
import cn.lannooo.entity.database.*;
import cn.lannooo.entity.model.NewsModel;
import cn.lannooo.entity.model.NewsTypeModel;
import cn.lannooo.service.NewsService;
import cn.lannooo.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * Created by 51499 on 2017/5/21 0021.
 */


@Controller
@RequestMapping("/news")
public class NewsController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private NewsService newsService;
    @Autowired
    private UserService userService;

    @RequestMapping(value = "/article/{news_id}", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> getArticle(@PathVariable String news_id,
                                          @SessionAttribute(value = WebSecurityConfig.SESSION_KEY_USER, required = false)String username){
        Map<String, Object> result = new HashMap<>();
        logger.info("username is "+username);
        logger.info("GET news id={}", news_id);
        int id = Integer.valueOf(news_id);
        News news = newsService.findById(id);
        NewsType newsType = news.getNewsType();
        if(news!=null){
            NewsModel data = new NewsModel(news);
            result.put("data", data);
            result.put("success", true);
            result.put("message", ResultMessage.NEWS_FOUND);
            if(username!=null){
                //update history
                UserHistory history = userService.findHistoryByTypeAndUsername(newsType, username);
                if(history!=null){
                    history.updateReview();
                    userService.createOrUpdateHistory(history);
                }else{
                    UserHistory history1 = new UserHistory(1);
                    User user = userService.findByUserName(username);
                    history1.setNewsType(newsType);
                    history1.setUser(user);
                    userService.createOrUpdateHistory(history1);
                }
            }
            //update typeclick and newsclick
            newsType.updateClick();
            news.updateClick();
            newsService.saveNews(news);
            newsService.saveNewsType(newsType);
        }else {
            result.put("success",false);
            result.put("message", ResultMessage.NEWS_NOTFOUND);
        }
        return result;
    }

    @RequestMapping("/hot/{page}/{num_perPage}")
    @ResponseBody
    public Map<String, Object> getHotNews(@PathVariable int page, @PathVariable int num_perPage){
        //返回热门新闻内容：总共10条，可进行调整
        Map<String, Object> result = new HashMap<>();
        int offset=0;
        int count=10;
        if(page>0 && num_perPage>0){
            offset = (page-1)*num_perPage;
            count = num_perPage;
        }
        List<News> hotNews = newsService.findHotNews(offset, count);
        //TODO: 不需要把新闻的详细信息传回，可以只返回简略信息，来增加效率
        List<NewsModel> data = new ArrayList<>();
        for(News news: hotNews){
            data.add(new NewsModel(news));
        }
        result.put("data", data);
        result.put("success",true);
        result.put("message", ResultMessage.NEWS_FOUND);
        return result;
    }

    @RequestMapping("/hot/type")
    @ResponseBody
    public Map<String, Object> getHotTypes(@SessionAttribute(value = WebSecurityConfig.SESSION_KEY_USER, required = false)String username){
        //返回热门的板块，在前端生成标签供用户选择
        //若为游客，默认生成的热门板块为当前新闻数最多的10个板块
        //若为登陆用户，先根据用户的浏览历史，返回最多的5个板块，然后根据用户的favor列表，添加所有favor的板块
        Map<String, Object> result = new HashMap<>();
        if(username!=null){
            //login user
            User user = userService.findByUserName(username);
            if(user!=null){
                List<NewsTypeModel> data = new ArrayList<>();
                Set<Integer> set = new HashSet<>();
                List<UserHistory> historyList = userService.findHistoryTop5(user.getUser_id());
                Set<UserFavor> favorSet = user.getFavors();
                for(UserHistory history:historyList){
                    NewsType type = history.getNewsType();
                    data.add(new NewsTypeModel(type));
                    set.add(type.getType_id());
                }
                for(UserFavor favor: favorSet){
                    if(!favor.isValid())
                        continue;
                    NewsType type = favor.getNewsType();
                    if(!set.contains(type.getType_id())){
                        data.add(new NewsTypeModel(type));
                    }
                }
                result.put("data",data);
                result.put("success",true);
                result.put("message", ResultMessage.TYPE_GET_ALL);
            }else{
                result.put("success", false);
                result.put("message", ResultMessage.TYPE_USER_NOTFOUND);
            }
        }else{
            //passer
            List<NewsTypeModel> types = newsService.getHotTypes();
            result.put("data", types);
            result.put("success", true);
            result.put("message", ResultMessage.TYPE_GET_ALL);
        }
        return result;
    }

    @RequestMapping("/type/{type_id}/{page}/{num_perPage}")
    @ResponseBody
    public Map<String, Object> getNewsByType(@PathVariable int type_id, @PathVariable int page, @PathVariable int num_perPage){
        //根据类别Id，返回该类别的新闻10条，可进行调整
        Map<String, Object> result = new HashMap<>();
        int offset=0;
        int count=10;
        if(page>0 && num_perPage>0){
            offset = (page-1)*num_perPage;
            count = num_perPage;
        }
        List<News> newsList = newsService.findNewsByType(type_id, offset, count);
        List<NewsModel> data = new ArrayList<>();
        for(News news: newsList){
            data.add(new NewsModel(news));
        }
        result.put("data", data);
        result.put("success",true);
        result.put("message", ResultMessage.NEWS_FOUND);
        logger.info(result.toString());
        return result;
    }

    @RequestMapping("/typeList")
    @ResponseBody
    public Map<String, Object> getTypeList(){
        Map<String, Object> result = new HashMap<>();
        List<NewsTypeModel> list = newsService.getALlTypes();

        result.put("success", true);
        result.put("data", list);
        logger.info("GET news type list:[{}]", list);

        return result;
    }
}
