package ru.avid.test.business.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.avid.test.business.entity.Category;
import ru.avid.test.business.object.CategoryStat;
import ru.avid.test.business.repository.CategoryRepository;
import ru.avid.test.business.repository.TaskRepository;
import ru.avid.test.business.search.SearchBase;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class CategoryService {
    private CategoryRepository categoryRepository;
    private TaskRepository taskRepository;
    @Autowired
    public CategoryService(
            CategoryRepository categoryRepository,
            TaskRepository taskRepository) {
        this.categoryRepository = categoryRepository;
        this.taskRepository = taskRepository;
    }

    public List<Category> findAll(Sort sort){
        return this.categoryRepository.findAll(sort);
    }

    public Category addOrUpdate(Category category){
        return this.categoryRepository.save(category);
    }

    public void delete(Long id){
        this.categoryRepository.deleteById(id);
    }
    public List<Category> search(SearchBase search){
        return this.categoryRepository.find(search);
    }
    public List<CategoryStat> searchStat(SearchBase search){
        List<CategoryStat> categoryStatList = new ArrayList<>();
        List<Category> categoryList = this.categoryRepository.find(search);
        for (Category iter: categoryList) {
            categoryStatList.add(new CategoryStat(iter, this.taskRepository.countTaskByCategory_Title(iter.getTitle())));
        }
        return categoryStatList;
    }
    public Category findById(Long id){
        return this.categoryRepository.findById(id).get();
    }
}
