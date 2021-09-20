package ru.avid.test.business.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.avid.test.business.entity.Task;
import ru.avid.test.business.search.SearchTask;

import java.util.Date;
import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    //    @Query("select t from Task t where " +
//            "(:title is null or :title = '' or lower(t.title) like lower(concat('%', :title, '%'))) and " +
//            "(:completed is null or t.completed=:completed) and " +
//            "(:categoryId is null or t.category.id=:categoryId) and " +
//            "(" +
//            "(cast(:dateFrom as timestamp) is null or t.date>=:dateFrom) and " +
//            "(cast(:dateTo as timestamp) is null or t.date<=:dateTo)" +
//            ")"
//    )
//    Page<Task> find(
//            @Param("title") String title,
//            @Param("completed") Integer completed,
//            @Param("categoryId") Long categoryId,
//            @Param("dateFrom") Date dateFrom,
//            @Param("dateTo") Date dateTo,
//            Pageable pageable
//    );
    @Query("select t from Task t where " +
            "(:#{#search.title} is null or :#{#search.title} = '' or lower(t.title) like lower(concat('%', :#{#search.title}, '%'))) and " +
            "(:#{#search.priority} is null or :#{#search.priority} = '' or lower(t.priority.title) like lower(concat('%', :#{#search.priority}, '%'))) and " +
            "(:#{#search.completed} is null or t.completed=:#{#search.completed}) " +
            " order by t.title asc"
    )
    List<Task> find(@Param("search") SearchTask search);

    @Query("select t from Task t where " +
            "(:title is null or :title = '' or lower(t.title) like lower(concat('%', :title, '%'))) and " +
            "(:priority is null or :priority = '' or lower(t.priority.title) like lower(concat('%', :priority, '%'))) and " +
            "(:completed is null or t.completed=:completed) " +
            " order by t.title asc"
    )
    List<Task> find(
            @Param("title") String title,
            @Param("priority") String priority,
            @Param("completed") boolean completed
    );

    @Query("select t from Task t where " +
            "(:title is null or :title = '' or lower(t.title) like lower(concat('%', :title, '%'))) and " +
            "(:priority is null or :priority = '' or lower(t.priority.title) like lower(concat('%', :priority, '%'))) " +
            " order by t.title asc"
    )
    List<Task> find(
            @Param("title") String title,
            @Param("priority") String priority
    );
}
