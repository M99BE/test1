package ru.avid.test.business.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.avid.test.business.entity.Category;
import ru.avid.test.business.search.SearchBase;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    @Query("SELECT c from Category c where " +
            "(:#{#search.userId} = 0L or c.user.id = :#{#search.userId}) and " +
            "(:#{#search.title} is null or :#{#search.title} = '' or lower(c.title) like lower(concat('%', :#{#search.title}, '%'))) " +
            "order by c.title asc")
    List<Category> find(@Param("search") SearchBase search);
}
