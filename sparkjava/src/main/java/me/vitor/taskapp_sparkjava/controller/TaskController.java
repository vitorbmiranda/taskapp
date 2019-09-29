package me.vitor.taskapp_sparkjava.controller;

import java.util.List;
import me.vitor.taskapp_sparkjava.model.Task;
import me.vitor.taskapp_sparkjava.model.TaskRepositoryHibernate;
import me.vitor.taskapp_sparkjava.service.TaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.Request;
import spark.Response;

public class TaskController {

  private static final String H_LOCATION = "Location";
  private static final Logger log = LoggerFactory.getLogger(TaskController.class);

  public static List<Task> getTasks(Request request, Response response) {
    TaskService taskService = new TaskService(new TaskRepositoryHibernate());
    List<Task> taskList = taskService.findAll();
    return taskList;
  }

  public static String postTask(Request request, Response response) {
    TaskService taskService = new TaskService(new TaskRepositoryHibernate());
    Task task = ControllerUtils.getTaskFromRequestBody(request);
    Long taskId = taskService.insert(task);
    response.header(H_LOCATION, ControllerUtils.generateUri(request, taskId));
    response.status(201);

    // can't return null here otherwise will get a 404
    // https://github.com/perwendel/spark/issues/620
    return "";
  }

  public static Task getTask(Request request, Response response) {
    Long id = Long.parseLong(request.params("id"));
    TaskService taskService = new TaskService(new TaskRepositoryHibernate());
    Task task = taskService.findById(id);

    if (task == null) {
      response.status(404);
      return null;
    }

    return task;
  }

  public static String putTask(Request request, Response response) {
    String paramId = request.params("id");
    Long id = Long.parseLong(paramId);

    Task task = ControllerUtils.getTaskFromRequestBody(request);
    TaskService taskService = new TaskService(new TaskRepositoryHibernate());
    Long putId = taskService.put(task, id).getId();

    response.header(H_LOCATION, ControllerUtils.generateUri(request, putId));
    response.status(204);
    return "";
  }

  public static String patchTask(Request request, Response response) {
    String paramId = request.params("id");
    Long id = Long.parseLong(paramId);
    TaskService taskService = new TaskService(new TaskRepositoryHibernate());

    boolean taskExists = taskService.taskExists(id);

    if (!taskExists) {
      response.status(404);
      return "Not found";
    }

    Task task = ControllerUtils.getTaskFromRequestBody(request);
    taskService.patch(task, id);
    response.status(204);
    return "";
  }

  public static String deleteTask(Request request, Response response) {
    String paramId = request.params("id");
    Long id = Long.parseLong(paramId);
    TaskService taskService = new TaskService(new TaskRepositoryHibernate());
    boolean taskExists = taskService.taskExists(id);

    if (!taskExists) {
      response.status(404);
      return "Not found";
    }

    taskService.delete(id);
    return "";
  }

}
