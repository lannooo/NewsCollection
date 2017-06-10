package cn.lannooo.repository;

import cn.lannooo.entity.database.RawNews;
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
public interface RawNewsRepository extends CrudRepository<RawNews, Integer> {

    @Query(value = "select distinct(types) from raw_news where website=:website",nativeQuery = true)
    public List<String> getAllTypes(@Param("website")String website);

    @Query(value = "select * from raw_news where types=:types and website=:website", nativeQuery = true)
    public List<RawNews> getByTypesAndWebsite(@Param("types")String types, @Param("website")String website);

    @Query(value = "select distinct(website) from raw_news", nativeQuery = true)
    public List<String> getWebsites();
}
