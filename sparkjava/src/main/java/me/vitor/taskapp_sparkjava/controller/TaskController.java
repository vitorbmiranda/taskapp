package me.vitor.taskapp_sparkjava.controller;

import java.util.List;
import me.vitor.taskapp_sparkjava.model.Task;
import me.vitor.taskapp_sparkjava.service.TaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.Request;
import spark.Response;

public class TaskController {

    private static final String H_LOCATION = "Location";
    private final TaskService taskService;
    private static final Logger log = LoggerFactory.getLogger(TaskController.class);

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    public List<Task> getTasks(Request request, Response response) {
        List<Task> taskList = taskService.findAll();
        return taskList;
    }

    public String postTask(Request request, Response response) {
        Task task = ControllerUtils.getTaskFromRequestBody(request);
        Long taskId = taskService.insert(task);
        response.header(H_LOCATION, ControllerUtils.generateUri(request, taskId));
        response.status(201);

        // can't return null here otherwise will get a 404
        // https://github.com/perwendel/spark/issues/620
        return "";
    }

    public Task getTask(Request request, Response response) {
        Long id = Long.parseLong(request.params("id"));
        Task task = taskService.findById(id);

        if (task == null) {
            response.status(404);
            return null;
        }

        return task;
    }

    public String putTask(Request request, Response response) {
        String paramId = request.params("id");
        Long id = Long.parseLong(paramId);

        Task task = ControllerUtils.getTaskFromRequestBody(request);
        Long putId = taskService.put(task, id).getId();

        response.header(H_LOCATION, ControllerUtils.generateUri(request, putId));
        response.status(204);
        return "";
    }

    public String patchTask(Request request, Response response) {
        String paramId = request.params("id");
        Long id = Long.parseLong(paramId);

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

    public String deleteTask(Request request, Response response) {
        String paramId = request.params("id");
        Long id = Long.parseLong(paramId);
        boolean taskExists = taskService.taskExists(id);

        if (!taskExists) {
            response.status(404);
            return "Not found";
        }

        taskService.delete(id);
        return "";
    }

}
