package ru.avid.test.business.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.avid.test.business.entity.Category;
import ru.avid.test.business.entity.Priority;
import ru.avid.test.business.object.JsonException;
import ru.avid.test.business.search.SearchBase;
import ru.avid.test.business.service.PriorityService;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/priority")
public class PriorityController {
    private PriorityService priorityService;
    @Autowired
    public PriorityController(PriorityService priorityService) {
        this.priorityService = priorityService;
    }

//    @PreAuthorize("hasAuthority('USER')")
    @PostMapping("/all")
    public ResponseEntity<List<Priority>> findAll() {
        return ResponseEntity.ok(this.priorityService.findAll(Sort.by(Sort.Direction.ASC, "title")));
    }
//    @PreAuthorize("hasAuthority('USER')")
    @PutMapping("/add")
    public ResponseEntity<Priority> add(@RequestBody Priority priority) {
        if (priority == null) {
            return new ResponseEntity("priority is empty", HttpStatus.NOT_ACCEPTABLE);
        } else {
            if (priority.getId() != null && priority.getId() != 0) {
                return new ResponseEntity("reduntal param: id must be null", HttpStatus.NOT_ACCEPTABLE);
            }
            if (priority.getTitle() == null || priority.getTitle().trim().length() == 0) {
                return new ResponseEntity("missing param: title", HttpStatus.NOT_ACCEPTABLE);
            }
//            две строки ниже идентичны
//            return new ResponseEntity(this.categoryService.add(category), HttpStatus.OK);
            return ResponseEntity.ok(this.priorityService.addOrUpdate(priority));
        }
    }
//    @PreAuthorize("hasAuthority('USER')")
    @PatchMapping("/update")
    public ResponseEntity update(@RequestBody Priority priority) {
        if (priority == null) {
            return new ResponseEntity("priority is empty", HttpStatus.NOT_ACCEPTABLE);
        } else {
            if (priority.getId() == null || priority.getId() == 0) {
                return new ResponseEntity("missing param: id", HttpStatus.NOT_ACCEPTABLE);
            }
            if (priority.getTitle() == null || priority.getTitle().trim().length() == 0) {
                return new ResponseEntity("missing param: title", HttpStatus.NOT_ACCEPTABLE);
            }
            this.priorityService.addOrUpdate(priority);
            //объект не возвращаем так как он уже создан и только обновляется
            // и нет смысла его обраьно передавать
            return ResponseEntity.ok(HttpStatus.OK);
        }
    }
//    @PreAuthorize("hasAuthority('USER')")
    @PostMapping("/delete")
    public ResponseEntity delete(@RequestBody Long id) {
        if(id == null || id == 0){
            return new ResponseEntity("missing param: id", HttpStatus.NOT_ACCEPTABLE);
        }
        try {
            this.priorityService.delete(id);
        } catch (EmptyResultDataAccessException exception) {
            exception.printStackTrace();
            return new ResponseEntity("id =" + id + "not found", HttpStatus.NOT_ACCEPTABLE);
        }
        return ResponseEntity.ok(HttpStatus.OK);
    }
    //    @PostMapping("/search")
//    public ResponseEntity<List<Category>> search(@RequestBody CategorySearchValues categorySearchValues){
//        return ResponseEntity.ok(this.categoryService.search(categorySearchValues.getTitle(), categorySearchValues.getEmail()));
//    }
//    @PreAuthorize("hasAuthority('USER')")
    @PostMapping("/id")
    public ResponseEntity<Priority> findById(@RequestBody Long id){
        Priority priority = null;
        try {
            priority = this.priorityService.findById(id);
        } catch (NoSuchElementException ex){
            ex.printStackTrace();;
            return new ResponseEntity("id=" + id + " not found", HttpStatus.NOT_ACCEPTABLE);
        }
        return ResponseEntity.ok(priority);
    }
    @PreAuthorize("hasAuthority('USER')")
    @PostMapping("/search")
    public ResponseEntity<List<Priority>> search(@RequestBody SearchBase search){
        return ResponseEntity.ok(this.priorityService.search(search));
    }
    /*
Метод перехватывает все ошибки в контроллере
Даже без этого метода все ошибки будут отправляться клиенту, просто здесь это можно кастомизировать, например отправить JSON в нужном формате
Можно настроить, какие типа ошибок отправлять в явном виде, а какие нет (чтобы не давать лишнюю информацию злоумышленникам)
*/
    @ExceptionHandler(Exception.class)
    public ResponseEntity<JsonException> handleExceptions(Exception ex){
    /*
    Эти типы ошибок можно будет считывать на клиенте и обрабатывать как нужно (например, показать текст ошибки)
    */
        // отправляем название класса ошибки (чтобы правильно обработать ошибку на клиенте)
        return new ResponseEntity(new JsonException(ex.getClass().getSimpleName()), HttpStatus.BAD_REQUEST);
    }
}
