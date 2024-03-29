package ru.avid.test.business.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.avid.test.business.entity.Task;
import ru.avid.test.business.object.StatsObject;
import ru.avid.test.business.repository.TaskRepository;
import ru.avid.test.business.search.SearchTask;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class TaskService {
    private TaskRepository taskRepository;
    @Autowired
    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public List<Task> findAll(Sort sort){
        return this.taskRepository.findAll(sort);
    }
    public Task findById(Long id){
        return this.taskRepository.findById(id).get();
    }
    public void delete(Long id){
        this.taskRepository.deleteById(id);
    }
    public Task addOrUpdate(Task task){
        return this.taskRepository.save(task);
    }

    public List<Task> find(SearchTask searchTask){
//        if (searchTask.getCompleted() == null && searchTask.getPriorityId() == null && searchTask.getCategoryTitle() == null){
//            return this.taskRepository.find(searchTask.getTitle());
//        }
//        if (searchTask.getCompleted() == null && searchTask.getPriorityId() != null && searchTask.getCategoryTitle() == null){
//            return this.taskRepository.find(searchTask.getTitle(), searchTask.getPriorityId());
//        }
//        if (searchTask.getCompleted() != null && searchTask.getPriorityId() == null && searchTask.getCategoryTitle() == null){
//            return this.taskRepository.find(searchTask.getTitle(), searchTask.getCompleted());
//        }
//        return this.taskRepository.find(searchTask);
        return this.taskRepository.find(searchTask.getUserId(), searchTask.getTitle(), searchTask.getCategoryTitle(), searchTask.getPriorityId(), searchTask.getCompleted());
    }

    public StatsObject getStat(SearchTask search){
        StatsObject stat = new StatsObject();
        int countCategoryTask;
        SearchTask tempSearch = new SearchTask();
        tempSearch.setUserId(search.getUserId());
        int totalTask = countCategoryTask = this.taskRepository.find(tempSearch).size();
        stat.setTotalTask(totalTask);

        if (search.getCategoryTitle().trim().length() == 0) {
            countCategoryTask = totalTask;
            stat.setCompletedTask(this.taskRepository.countTaskByCompletedAndUser_id(true, search.getUserId()));
            stat.setUncompletedTask(this.taskRepository.countTaskByCompletedAndUser_id(false, search.getUserId()));

        } else {
            countCategoryTask = this.taskRepository.countTaskByCategory_TitleAndUser_Id(search.getCategoryTitle(), search.getUserId());
            stat.setCompletedTask(this.taskRepository.countTaskByCategory_TitleAndCompletedAndUser_Id(search.getCategoryTitle(), true, search.getUserId()));
            stat.setUncompletedTask(this.taskRepository.countTaskByCategory_TitleAndCompletedAndUser_Id(search.getCategoryTitle(), false, search.getUserId()));
        }
        stat.setTotalCategoryTask(countCategoryTask);
        return stat;
    }
    public Page<Task> find(
            SearchTask searchTask,
            PageRequest paging){
        return this.taskRepository.find(searchTask.getUserId(), searchTask.getTitle(), searchTask.getCategoryTitle(), searchTask.getPriorityId(), searchTask.getCompleted(), paging);
    }
}
