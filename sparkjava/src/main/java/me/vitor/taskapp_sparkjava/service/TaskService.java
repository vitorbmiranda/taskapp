package me.vitor.taskapp_sparkjava.service;

import java.util.List;
import me.vitor.taskapp_sparkjava.model.Task;
import me.vitor.taskapp_sparkjava.model.TaskRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TaskService {

  private static final Logger log = LoggerFactory.getLogger(TaskService.class);
  private TaskRepository taskRepository;

  public TaskService(TaskRepository taskRepository) {
    this.taskRepository = taskRepository;
  }

  public Task findById(Long id) {
    Task task = taskRepository.findBy(id);
    return task != null ? task : null;
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
    Task foundTask = taskRepository.findBy(id);
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
