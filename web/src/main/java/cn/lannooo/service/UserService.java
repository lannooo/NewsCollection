package cn.lannooo.service;

import cn.lannooo.entity.database.NewsType;
import cn.lannooo.entity.database.User;
import cn.lannooo.entity.database.UserFavor;
import cn.lannooo.entity.database.UserHistory;
import cn.lannooo.repository.UserFavorRepository;
import cn.lannooo.repository.UserHistoryRepository;
import cn.lannooo.repository.UserRepository;
import cn.lannooo.util.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by 51499 on 2017/5/13 0013.
 */
@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserHistoryRepository historyRepository;
    @Autowired
    private UserFavorRepository favorRepository;


    public User createUser(String username, String password, String email){
        User user = new User(username, MD5Util.md5(password), email);
        return userRepository.save(user);
    }

    public User findByUserName(String username){
        List<User> users = userRepository.findByUserName(username);
        if(null!=users && users.size()>0){
            return users.get(0);
        }else{
            return null;
        }
    }

    public User findByEmail(String email){
        List<User> users = userRepository.findByUserEmail(email);
        if(null!=users && users.size()>0){
            return users.get(0);
        }
        return null;
    }

    public boolean isEmailExist(String email){
        if(null != findByEmail(email)){
            return true;
        }
        return false;
    }

    public boolean isUserNameExist(String username){
        if(null!= findByUserName(username)){
            return true;
        }
        return false;
    }

    public UserFavor findFavorById(int fid){
        return favorRepository.findOne(fid);
    }
    public UserFavor findFavorByTypeId(int uid, int tid){
        List<UserFavor> favors = favorRepository.findByUserIdTypeId(uid, tid);
        if(favors!=null && favors.size()>0){
            return favors.get(0);
        }
        return null;
    }

    public UserFavor createUserFavor(UserFavor favor){
        return favorRepository.save(favor);
    }

    public UserFavor createOrUpdateFavor(UserFavor favor, User user, NewsType type){
        List<UserFavor> favors = favorRepository.findByUserIdTypeId(user.getUser_id(),type.getType_id());
        if(favors!=null && favors.size()>0){
            UserFavor oldFavor = favors.get(0);
            oldFavor.setValid(true);
            return favorRepository.save(oldFavor);
        }else{
            favor.setNewsType(type);
            favor.setUser(user);
            return createUserFavor(favor);
        }
    }

    public UserFavor deleteFavor(UserFavor favor){
        favor.setValid(false);
        return favorRepository.save(favor);
    }

    public UserHistory findHistoryByTypeAndUsername(NewsType newsType, String username) {
        List<UserHistory> histories = historyRepository.findByTypeIdAndUserName(newsType.getType_id(), username);
        if(histories==null || histories.size()==0){
            return null;
        }
        return histories.get(0);
    }

    public UserHistory createOrUpdateHistory(UserHistory history){
        return historyRepository.save(history);
    }

    public List<UserHistory> findHistoryTop5(int user_id){
        return historyRepository.findHistoryTop10(user_id);
    }

}
