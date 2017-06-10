package cn.lannooo.repository;

import cn.lannooo.entity.database.UserHistory;
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
public interface UserHistoryRepository extends CrudRepository<UserHistory, Integer> {
    @Query("from UserHistory h where h.newsType.type_id=:tid and h.user.user_name=:username")
    public List<UserHistory> findByTypeIdAndUserName(@Param("tid") int tid, @Param("username") String username);

    @Query(value = "select * from user_history where user_id=?1 order by review_count desc limit 0, 10", nativeQuery = true)
    public List<UserHistory> findHistoryTop10(int user_id);
}
