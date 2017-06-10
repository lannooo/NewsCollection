package cn.lannooo.service;

import cn.lannooo.entity.database.News;
import cn.lannooo.entity.database.NewsType;
import cn.lannooo.entity.database.RawNews;
import cn.lannooo.entity.model.NewsTypeModel;
import cn.lannooo.repository.NewsRepository;
import cn.lannooo.repository.NewsTypeRepository;
import cn.lannooo.repository.RawNewsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 51499 on 2017/5/13 0013.
 */
@Service
public class NewsService {

    @Autowired
    private NewsRepository newsRepository;

    @Autowired
    private NewsTypeRepository typeRepository;

    @Autowired
    private RawNewsRepository rawNewsRepository;


    /**
     * raw news method
     * */
    public List<String> getRawTypes(String website){
        return rawNewsRepository.getAllTypes(website);
    }

    public List<String> getRawWebSites(){
        return rawNewsRepository.getWebsites();
    }

    public List<RawNews> getRawByTypeAndWebsite(String type, String website){
        return rawNewsRepository.getByTypesAndWebsite(type,website);
    }

    /**
     * newsType entity method
     * */

    public NewsType findTypeById(int tid){
        return  typeRepository.findOne(tid);
    }

    public void saveNewsType(NewsType type){
        typeRepository.save(type);
    }

    public void updateNewsTypeTotalNews(int id, int count){
        typeRepository.updateTotal(id, count);
    }

    public NewsType getNewsTypeByNameAndWebsite(String name, String website) {
        List<NewsType> types = typeRepository.getByNameAndWebSite(name, website);
        if(types == null || types.size() == 0){
            return null;
        }else {
            return types.get(0);
        }
    }

    public List<NewsTypeModel> getALlTypes(){
        ArrayList<NewsTypeModel> types = new ArrayList<>();
        for(NewsType type: typeRepository.findAll()){
            NewsTypeModel model = new NewsTypeModel(type.getType_id(),
                                                    type.getType_name(),
                                                    type.getType_total(),
                                                    type.getType_website(),
                                                    type.getType_click());
            types.add(model);
        }
        return types;
    }

    public List<NewsTypeModel> getHotTypes(){
        ArrayList<NewsTypeModel> types = new ArrayList<>();
        for(NewsType type: typeRepository.getHot10Types()){
            NewsTypeModel model = new NewsTypeModel(type.getType_id(),
                    type.getType_name(),
                    type.getType_total(),
                    type.getType_website(),
                    type.getType_click());
            types.add(model);
        }
        return types;
    }

    /**
     * news entity method
     * */
    public News findById(int id){
        return newsRepository.findOne(id);
    }

    public List<News> findHotNews(int offset, int count){
        return newsRepository.findHotNews(offset, count);
    }

    public List<News> findNewsByType(int type_id, int offset, int count){
        return newsRepository.findByType(type_id, offset,count);
    }

    public void saveNews(News news){
        newsRepository.save(news);
    }

    public void deleteFromRaw(RawNews news){
        rawNewsRepository.delete(news);
    }

    public void deleteRawAll(){
        rawNewsRepository.deleteAll();
    }

    public boolean isNewsURLExist(String url){
        if(null!=newsRepository.findByUrl(url))
            return true;
        else
            return false;
    }


}
