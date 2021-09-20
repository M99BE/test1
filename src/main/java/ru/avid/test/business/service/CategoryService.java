package ru.avid.test.business.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.avid.test.business.entity.Category;
import ru.avid.test.business.repository.CategoryRepository;
import ru.avid.test.business.search.SearchBase;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class CategoryService {
    @Autowired
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    private CategoryRepository categoryRepository;

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
    public Category findById(Long id){
        return this.categoryRepository.findById(id).get();
    }
}
