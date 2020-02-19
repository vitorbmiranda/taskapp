package me.vitor.taskapp_sparkjava;

import static spark.Spark.get;
import static spark.Spark.patch;
import static spark.Spark.post;
import static spark.Spark.put;
import static spark.Spark.delete;

import me.vitor.taskapp_sparkjava.controller.TaskController;
import me.vitor.taskapp_sparkjava.model.TaskRepositoryHibernate;
import me.vitor.taskapp_sparkjava.service.TaskService;
import me.vitor.taskapp_sparkjava.transformer.JsonTransformer;

public class App {

    private static final String PATH_TASK = "/task";
    private static final String PATH_ID = ":id";
    private static final String PATH_TASK_WITH_ID = PATH_TASK + "/" + PATH_ID;

    public static void main(String[] args) {
        App app = new App();
        app.setup();
    }

    private void setup() {

        TaskController controller = new TaskController(new TaskService(new TaskRepositoryHibernate()));

        get(PATH_TASK, controller::getTasks, new JsonTransformer());
        get(PATH_TASK_WITH_ID, controller::getTask, new JsonTransformer());
        post(PATH_TASK, controller::postTask);
        put(PATH_TASK_WITH_ID, controller::putTask);
        patch(PATH_TASK_WITH_ID, controller::patchTask);
        delete(PATH_TASK_WITH_ID, controller::deleteTask);
    }

}
