package ru.avid.test.business.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.avid.test.business.entity.Category;
import ru.avid.test.business.entity.Priority;
import ru.avid.test.business.repository.PriorityRepository;
import ru.avid.test.business.search.SearchBase;

import java.util.List;

@Service
public class PriorityService {
    private PriorityRepository priorityRepository;
    @Autowired
    public PriorityService(PriorityRepository priorityRepository) {
        this.priorityRepository = priorityRepository;
    }
    public List<Priority> findAll(Sort sort){
        return this.priorityRepository.findAll(sort);
    }

    public Priority addOrUpdate(Priority priority){
        return this.priorityRepository.save(priority);
    }

    public void delete(Long id){
        this.priorityRepository.deleteById(id);
    }
    public List<Priority> search(SearchBase search){
        return this.priorityRepository.find(search);
    }
    public Priority findById(Long id){
        return this.priorityRepository.findById(id).get();
    }

}
