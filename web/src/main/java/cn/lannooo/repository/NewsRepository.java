package cn.lannooo.repository;

import cn.lannooo.entity.database.News;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by 51499 on 2017/4/21 0021.
 */
@Repository
@Transactional
public interface NewsRepository extends CrudRepository<News, Integer> {
    @Query(value = "from News n where n.news_url=:url")
    public News findByUrl(@Param("url") String url);

    @Query(value = "select * from news order by news_click desc limit ?1, ?2", nativeQuery = true)
    public List<News> findHotNews(int offset, int count);

    @Query(value = "select * from news where type_id=?1 limit ?2,?3", nativeQuery = true)
    public List<News> findByType(int tid, int offset, int count);

}
