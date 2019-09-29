package me.vitor.taskapp_sparkjava.controller;

import com.google.gson.Gson;
import me.vitor.taskapp_sparkjava.model.Task;
import me.vitor.taskapp_sparkjava.service.TaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.Request;

public class ControllerUtils {

  private static final Logger log = LoggerFactory.getLogger(ControllerUtils.class);
  private static final Gson gson = new Gson();

  public static String generateUri(Request request, Long id) {
    String uri = request.url();
    if (uri.matches(".*task/\\d.*")) {
      uri = uri.substring(0, uri.lastIndexOf("/"));
    }
    return uri + "/" + id;
  }

  public static Task getTaskFromRequestBody(Request request) {
    if (request.body() == null) return null;
    log.info(request.body());
    return gson.fromJson(request.body(), Task.class);
  }

}
