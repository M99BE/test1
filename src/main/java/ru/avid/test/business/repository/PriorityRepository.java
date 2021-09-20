package ru.avid.test.business.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.avid.test.business.entity.Priority;
import ru.avid.test.business.search.SearchBase;

import java.util.List;

@Repository
public interface PriorityRepository extends JpaRepository<Priority, Long> {
    @Query( "SELECT p from Priority p where " +
            "(:#{#search.title} is null or :#{#search.title} = '' or lower(p.title) like lower(concat('%', :#{#search.title}, '%'))) " +
            " order by p.title asc"
    )
    List<Priority> find(@Param("search") SearchBase search);
}
