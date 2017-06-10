package cn.lannooo.repository;

import cn.lannooo.entity.database.NewsType;
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
public interface NewsTypeRepository extends CrudRepository<NewsType, Integer> {
    @Query(value = "from NewsType t where t.type_name=:name and t.type_website=:website")
    public List<NewsType> getByNameAndWebSite(@Param("name") String name, @Param("website") String website);

    @Query(value = "update NewsType t set t.type_total=:total where t.type_id=:id")
    public void updateTotal(@Param("id") int id, @Param("total") int total);

    @Query(value = "select * from news_type order by type_total desc limit 0, 10", nativeQuery = true)
    public List<NewsType> getHot10Types();
}
