package ru.avid.test.business.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.avid.test.business.entity.Category;
import ru.avid.test.business.object.CategoryStat;
import ru.avid.test.business.object.JsonException;
import ru.avid.test.business.search.SearchBase;
import ru.avid.test.business.service.CategoryService;

import java.util.Calendar;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping(value = "/category")
public class CategoryController {
    private CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping("/all")
    public ResponseEntity<List<Category>> findAll() {
        return ResponseEntity.ok(this.categoryService.findAll(Sort.by(Sort.Direction.ASC, "title")));
    }

    @PutMapping("/add")
    public ResponseEntity<Category> add(@RequestBody Category category) {
        if (category == null) {
            return new ResponseEntity("category is empty", HttpStatus.NOT_ACCEPTABLE);
        } else {
            if (category.getId() != null && category.getId() != 0) {
                return new ResponseEntity("reduntal param: id must be null", HttpStatus.NOT_ACCEPTABLE);
            }
            if (category.getTitle() == null || category.getTitle().trim().length() == 0) {
                return new ResponseEntity("missing param: title", HttpStatus.NOT_ACCEPTABLE);
            }
//            две строки ниже идентичны
//            return new ResponseEntity(this.categoryService.add(category), HttpStatus.OK);
            return ResponseEntity.ok(this.categoryService.addOrUpdate(category));
        }
    }

    @PatchMapping("/update")
    public ResponseEntity update(@RequestBody Category category) {
        if (category == null) {
            return new ResponseEntity("category is empty", HttpStatus.NOT_ACCEPTABLE);
        } else {
            if (category.getId() == null || category.getId() == 0) {
                return new ResponseEntity("missing param: id", HttpStatus.NOT_ACCEPTABLE);
            }
            if (category.getTitle() == null || category.getTitle().trim().length() == 0) {
                return new ResponseEntity("missing param: title", HttpStatus.NOT_ACCEPTABLE);
            }
            this.categoryService.addOrUpdate(category);
            //объект не возвращаем так как он уже создан и только обновляется
            // и нет смысла его обраьно передавать
            return ResponseEntity.ok(HttpStatus.OK);
        }
    }

    @PostMapping("/delete")
    public ResponseEntity delete(@RequestBody Long id) {
        if(id == null || id == 0){
            return new ResponseEntity("missing param: id", HttpStatus.NOT_ACCEPTABLE);
        }
        try {
            this.categoryService.delete(id);
        } catch (EmptyResultDataAccessException exception) {
            exception.printStackTrace();
            return new ResponseEntity("id =" + id + "not found", HttpStatus.NOT_ACCEPTABLE);
        }
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PostMapping("/id")
    public ResponseEntity<Category> findById(@RequestBody Long id){
        Category category = null;
        try {
            category = this.categoryService.findById(id);
        } catch (NoSuchElementException ex){
            ex.printStackTrace();;
            return new ResponseEntity("id=" + id + " not found", HttpStatus.NOT_ACCEPTABLE);
        }
        return ResponseEntity.ok(category);
    }
    @PostMapping("/search")
    public ResponseEntity<List<Category>> search(@RequestBody SearchBase search){
        return ResponseEntity.ok(this.categoryService.search(search));
    }

    @PostMapping("/search-stat")
    public ResponseEntity<List<CategoryStat>> searchStat(@RequestBody SearchBase search){
        return ResponseEntity.ok(this.categoryService.searchStat(search));
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
