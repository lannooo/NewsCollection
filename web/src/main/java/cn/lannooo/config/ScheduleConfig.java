package cn.lannooo.config;

import cn.lannooo.entity.database.News;
import cn.lannooo.entity.database.NewsType;
import cn.lannooo.entity.database.RawNews;
import cn.lannooo.service.NewsService;
import cn.lannooo.websocket.ServerMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.List;

/**
 * Created by 51499 on 2017/5/29 0029.
 */
@Configuration
@EnableScheduling
public class ScheduleConfig {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private SimpMessagingTemplate messageSender;

    @Autowired
    private NewsService newsService;

    //每十分钟，发送一次websocket广播，通知最热的新闻
    @Scheduled(cron = "0 */10 * * * ?")
    public void send(){
        List<News> hots = newsService.findHotNews(0, 3);
        News one = null;
        if(hots!=null && hots.size()>0)
            one = hots.get(0);
        else
            return;
        messageSender.convertAndSend("/topic/getResponse",
                new ServerMessage(one.getNews_source(), one.getNews_title()));
    }
    /**
     * 爬虫爬到的新闻写入的是raw_news表
     * 该测试可以把raw_news表中的新闻，分类存储到news和news_type表
     * 为防止重复新闻记录的错误，可以先清空news表和news_type表
     * */
    //每日1点将爬取的数据构建进数据库
    @Scheduled(cron = "0 0 1 * * ?")
    public void build(){
        for(String website: newsService.getRawWebSites()){
            //获取该website爬出来的新闻的所有类别
            List<String> types = newsService.getRawTypes(website);
           logger.info(website + " has types: " + types.toString());
            for(String type: types){
                //判断类别是否存在
                NewsType tmpType = newsService.getNewsTypeByNameAndWebsite(type, website);
                List<RawNews> rawNews = newsService.getRawByTypeAndWebsite(type, website);
                logger.info("type " + type + " has total news:" + rawNews.size());
                if(tmpType!=null){
                    //update newsType column
                    newsService.updateNewsTypeTotalNews(tmpType.getType_id(), rawNews.size());

                }else {
                    //create a new type
                    tmpType = new NewsType(type, rawNews.size(), website, 0);
                    newsService.saveNewsType(tmpType);
                }
                for (RawNews rawNews1 : rawNews) {
                    if(newsService.isNewsURLExist(rawNews1.getUrl())){
                        continue;
                    }
                    News aNews = new News(
                            rawNews1.getTitle(),
                            rawNews1.getUrl(),
                            rawNews1.getSource(),
                            rawNews1.getTime(),
                            rawNews1.getContent(),
                            0,
                            tmpType);
                    newsService.saveNews(aNews);
                    newsService.deleteFromRaw(rawNews1);
                }
            }
        }
    }

}
