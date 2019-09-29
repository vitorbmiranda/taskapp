package me.vitor.taskapp_springboot.controller;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import me.vitor.taskapp_springboot.model.Task;
import me.vitor.taskapp_springboot.service.TaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("task")
public class TaskController {

  private static final Logger log = LoggerFactory.getLogger(TaskController.class);
  private static final String PATH_ID = "/{id}";

  @Autowired
  private TaskService taskService;

  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<List<Task>> getTasks() {
    List<Task> taskList = taskService.findAll();
    return ResponseEntity.ok().body(taskList);
  }

  @GetMapping(path = PATH_ID, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Task> getTask(@PathVariable Long id) {
    Task task = taskService.findById(id);
    if (task == null) {
      return ResponseEntity.notFound().build();
    }
    return ResponseEntity.ok().body(task);
  }

  @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Object> postTask(@RequestBody Task task) throws URISyntaxException {
    Long id = taskService.insert(task);
    String location = ControllerUtils.generateURIFromRequestAndId(id);
    return ResponseEntity.created(new URI(location)).build();
  }

  @PutMapping(path = PATH_ID, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Task> putTask(@PathVariable Long id, @RequestBody Task task) {
    Task resultingTask = taskService.put(task, id);
    Long resultingId = resultingTask.getId();
    String location = ControllerUtils.generateURIFromRequestAndId(resultingId);
    return ResponseEntity.noContent().header("Location", location).build();
  }

  @PatchMapping(path = PATH_ID, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Task> patchTask(@PathVariable Long id, @RequestBody Task task) {
    if (!taskService.taskExists(id)) {
      return ResponseEntity.notFound().build();
    }
    taskService.patch(task, id);
    String location = ControllerUtils.generateURIFromRequestAndId(id);
    return ResponseEntity.noContent().header("Location", location).build();
  }

  @DeleteMapping(path = PATH_ID, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Object> deleteTask(@PathVariable Long id) {
    if (!taskService.taskExists(id)) {
      return ResponseEntity.notFound().build();
    }
    taskService.delete(id);
    return ResponseEntity.ok().build();
  }

}
