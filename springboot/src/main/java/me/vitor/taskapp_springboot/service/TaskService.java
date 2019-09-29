package me.vitor.taskapp_springboot.service;

import java.util.List;
import java.util.Optional;
import me.vitor.taskapp_springboot.model.Task;
import me.vitor.taskapp_springboot.model.TaskRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TaskService {

  private static final Logger log = LoggerFactory.getLogger(TaskService.class);

  @Autowired
  private TaskRepository taskRepository;

  public Task findById(Long id) {
    Optional<Task> task = taskRepository.findById(id);
    return task.orElse(null);
  }

  public Boolean taskExists(Long id) {
    Task task = findById(id);
    return task != null;
  }

  public List<Task> findAll() {
    return taskRepository.findAll();
  }

  public Long insert(Task task) {
    Task createdTask = taskRepository.save(task);
    return createdTask.getId();
  }

  public Task put(Task task, Long id) {
    Task foundTask = findById(id);
    if (foundTask != null) {
      task.setId(id);
    }
    return taskRepository.save(task);
  }

  public void patch(Task task, Long id) {
    Task existingTask = findById(id);
    existingTask.merge(task);
    existingTask.setUpdatedAtToNow();
    taskRepository.save(existingTask);
  }

  public void delete(Long id) {
    taskRepository.deleteById(id);
  }

}
