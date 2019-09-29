package me.vitor.taskapp_sparkjava.model;

import java.util.List;

public interface TaskRepository {
  Task findBy(Long id);
  List<Task> findAll();
  Task save(Task task);
  void deleteById(Long id);
}
