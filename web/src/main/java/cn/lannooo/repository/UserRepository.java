package cn.lannooo.repository;

import cn.lannooo.entity.database.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by 51499 on 2017/5/13 0013.
 */
@Repository
@Transactional
public interface UserRepository extends CrudRepository<User, Integer> {
    @Query("from User u where u.user_name=:username")
    public List<User> findByUserName(@Param("username") String username);

    @Query("from User u where u.user_email=:email")
    public List<User> findByUserEmail(@Param("email")String email);

    @Query("from User u where u.user_name=:username and u.user_email=:email")
    public List<User> findByUserNameEmail(@Param("username")String username,@Param("email")String email);



}
