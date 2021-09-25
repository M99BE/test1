package ru.avid.test.business.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.avid.test.business.entity.Task;
import ru.avid.test.business.object.JsonException;
import ru.avid.test.business.object.StatsObject;
import ru.avid.test.business.search.SearchTask;
import ru.avid.test.business.service.TaskService;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/task")
public class TaskController {
    private static final String ID_COLUMN = "id";

    private TaskService taskService;
    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping("/all")
    public ResponseEntity<List<Task>> findAll(){
        return ResponseEntity.ok(this.taskService.findAll(Sort.by(Sort.Direction.ASC, "title")));
    }
    @PutMapping("/add")
    public ResponseEntity<Task> add(@RequestBody Task task){
        if (task == null){
            return new ResponseEntity("task is empty", HttpStatus.NOT_ACCEPTABLE);
        } else {
            if (task.getId() != null && task.getId() != 0){
                return new ResponseEntity("reduntal param: id must be null", HttpStatus.NOT_ACCEPTABLE);
            }
            if (task.getTitle() == null || task.getTitle().trim().length() == 0){
                return new ResponseEntity("missing param: title", HttpStatus.NOT_ACCEPTABLE);
            }
        }
        return ResponseEntity.ok(this.taskService.addOrUpdate(task));
    }
    @PatchMapping("/update")
    public ResponseEntity update(@RequestBody Task task){
        if (task == null){
            return new ResponseEntity("task is empty", HttpStatus.NOT_ACCEPTABLE);
        } else {
            if (task.getId() == null || task.getId() == 0){
                return new ResponseEntity("reduntal param: id must be not null", HttpStatus.NOT_ACCEPTABLE);
            }
            if (task.getTitle() == null || task.getTitle().trim().length() == 0){
                return new ResponseEntity("missing param: title", HttpStatus.NOT_ACCEPTABLE);
            }
            this.taskService.addOrUpdate(task);
            return ResponseEntity.ok(HttpStatus.OK);
        }
    }
    @PostMapping("/delete")
    public ResponseEntity delete(@RequestBody Long id){
        if (id == null || id == 0){
            return new ResponseEntity("missing param: id", HttpStatus.NOT_ACCEPTABLE);
        }
        try {
            this.taskService.delete(id);
        } catch (EmptyResultDataAccessException e){
            e.printStackTrace();
            return new ResponseEntity("id=" + id + " not found", HttpStatus.NOT_ACCEPTABLE);
        }
        return ResponseEntity.ok(HttpStatus.OK);
    }
    @PostMapping("/id")
    public ResponseEntity<Task> findById(@RequestBody Long id){
        Task task = null;
        try {
            task  = this.taskService.findById(id);
        } catch (NoSuchElementException e){
            e.printStackTrace();
            return new ResponseEntity("id=" + id + " not found", HttpStatus.NOT_ACCEPTABLE);
        }
        return ResponseEntity.ok(task);
    }

    @PostMapping("/search")
    public ResponseEntity<List<Task>> search(@RequestBody SearchTask search){
        return ResponseEntity.ok(this.taskService.find(search));
    }

    @PostMapping("/search-page")
    public ResponseEntity<Page<Task>> searchPage(@RequestBody SearchTask search){

//        String title = search.getTitle() != null ? search.getTitle() : "";
//        Boolean completed = search.getCompleted() != null ? search.getCompleted() : null;
//        String categoryTitle = search.getCategoryTitle() != null ? search.getCategoryTitle() : "";
        String sortColumn = search.getSortColumn() != null ? search.getSortColumn() : null;
        String sortDirection = search.getSortDirection() != null ? search.getSortDirection() : null;
        Integer pageNumber = search.getPageNumber() != null ? search.getPageNumber() : 0;
        Integer pageSize = search.getPageSize() != null ? search.getPageSize() : 5;

        Sort.Direction direction = sortDirection == null || sortDirection.trim().length() == 0 || sortDirection.trim().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;

        if (sortColumn == null){
            sortColumn = ID_COLUMN;
        }

        Sort sort = Sort.by(direction, sortColumn, ID_COLUMN);
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize, sort);
        Page<Task> result = this.taskService.find(search, pageRequest);
        return ResponseEntity.ok(result);
    }
/*    Метод перехватывает все ошибки в контроллере
    Даже без этого метода все ошибки будут отправляться клиенту, просто здесь это можно кастомизировать, например отправить JSON в нужном формате
    Можно настроить, какие типы ошибок отправлять в явном виде, а какие нет (чтобы не давать лишнюю информацию злоумышленникам)
*/
    @ExceptionHandler(Exception.class)
    public ResponseEntity<JsonException> handleException(Exception exception) {
        // отправляем название класса ошибки (чтобы правильно обработать ошибку на клиенте)
        return new ResponseEntity(new JsonException(exception.getClass().getSimpleName()), HttpStatus.BAD_REQUEST);
    }
    @PostMapping("/stats")
    public ResponseEntity<StatsObject> getStat(@RequestBody SearchTask search){
        return ResponseEntity.ok(this.taskService.getStat(search));
    }
}
