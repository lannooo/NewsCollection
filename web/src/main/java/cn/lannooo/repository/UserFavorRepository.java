package cn.lannooo.repository;

import cn.lannooo.entity.database.UserFavor;
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
public interface UserFavorRepository extends CrudRepository<UserFavor, Integer> {

    @Query("from UserFavor f where f.newsType.type_id=:tid and f.user.user_id=:uid")
    public List<UserFavor> findByUserIdTypeId(@Param("uid") int uid, @Param("tid") int tid);

}
