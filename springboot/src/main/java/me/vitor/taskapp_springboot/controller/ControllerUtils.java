package me.vitor.taskapp_springboot.controller;

import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

public class ControllerUtils {

  /**
   * Uses the current request context to generate the resource URI.
   *
   * @param id the task ID
   * @return a String with the URI
   */
  public static String generateURIFromRequestAndId(Long id) {
    String uri = ServletUriComponentsBuilder.fromCurrentRequest()
        .toUriString();

    if (uri.matches(".*task/\\d.*")) {
      uri = uri.substring(0, uri.lastIndexOf("/"));
    }
    return uri + "/" + id;

  }

}
