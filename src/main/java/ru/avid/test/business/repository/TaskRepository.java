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
        @Query("select t from Task t where " +
                "(:title is null or :title = '' or lower(t.title) like lower(concat('%', :title, '%'))) and " +
                "(:categoryTitle is null or :categoryTitle = '' or lower(t.category.title) like lower(:categoryTitle)) and " +
                "(:priorityId is null or :priorityId = 0L or t.priority.id = :priorityId) and " +
                "(:completed is null or t.completed=:completed) "
    )
    Page<Task> find(
                @Param("title") String title,
                @Param("categoryTitle") String categoryTitle,
                @Param("priorityId") Long priorityId,
                @Param("completed") Boolean completed,
            Pageable pageable
    );

    @Query("select t from Task t where " +
            "(:#{#search.title} is null or :#{#search.title} = '' or lower(t.title) like lower(concat('%', :#{#search.title}, '%'))) and " +
            "(:#{#search.categoryTitle} is null or :#{#search.categoryTitle} = '' or lower(t.category.title) like lower(concat('%', :#{#search.categoryTitle}, '%'))) and " +
            "(:#{#search.priorityId} is null or :#{#search.priorityId} = 0L or t.priority.id=:#{#search.priorityId}) and " +
            "(:#{#search.completed} is null or t.completed=:#{#search.completed}) " +
            " order by t.title asc"
    )
    List<Task> find(@Param("search") SearchTask search);

    @Query("select t from Task t where " +
            "(:title is null or :title = '' or lower(t.title) like lower(concat('%', :title, '%'))) and " +
            "(:categoryTitle is null or :categoryTitle = '' or lower(t.category.title) like lower(:categoryTitle)) and " +
            "(:priorityId is null or :priorityId = 0L or t.priority.id = :priorityId) and " +
            "(:completed is null or t.completed=:completed) " +
            " order by t.title asc"
    )
    List<Task> find(
            @Param("title") String title,
            @Param("categoryTitle") String categoryTitle,
            @Param("priorityId") Long priorityId,
            @Param("completed") Boolean completed
    );

    @Query("select t from Task t where " +
            "(:title is null or :title = '' or lower(t.title) like lower(concat('%', :title, '%'))) and " +
            "(:priorityId is null or :priorityId = 0L or t.priority.id = :priorityId) " +
            " order by t.title asc"
    )
    List<Task> find(
            @Param("title") String title,
            @Param("priorityId") Long priorityId
    );

    @Query("select t from Task t where " +
            "(:title is null or :title = '' or lower(t.title) like lower(concat('%', :title, '%'))) and " +
            "(:completed is null or t.completed=:completed) " +
            " order by t.title asc"
    )
    List<Task> find(
            @Param("title") String title,
            @Param("completed") Boolean completed
    );

    @Query("select t from Task t where " +
            "(:title is null or :title = '' or lower(t.title) like lower(concat('%', :title, '%'))) " +
            " order by t.title asc"
    )
    List<Task> find(
            @Param("title") String title
    );
    Integer countTaskByCategory_Title(String title);
    Integer countTaskByCategory_TitleAndCompleted(String title, Boolean completed);
    Integer countTaskByCompleted(Boolean completed);
}
