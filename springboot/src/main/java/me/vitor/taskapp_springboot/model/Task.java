package me.vitor.taskapp_springboot.model;

import java.time.Instant;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Entity
public class Task {

  private static final Logger log = LoggerFactory.getLogger(Task.class);

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;
  private String description;
  private Instant createdAt;
  private Instant updatedAt;
  private Boolean resolved;

  public Task() {
    Instant instant = Instant.now();
    createdAt = instant;
    updatedAt = instant;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getDescription() {
    return description;
  }

  public Instant getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(Instant createdAt) {
    this.createdAt = createdAt;
  }

  public Instant getUpdatedAt() {
    return updatedAt;
  }

  public void setUpdatedAtToNow() {
    this.updatedAt = Instant.now();
  }

  public Boolean getResolved() {
    return resolved;
  }

  public void merge(Task otherTask) {
    this.description = otherTask.getDescription() != null ? otherTask.getDescription() : this.getDescription();
    this.resolved = otherTask.getResolved() != null ? otherTask.getResolved() : this.getResolved();
  }

  @Override
  public String toString() {
    return "Task{" +
        "id=" + id +
        ", description='" + description + '\'' +
        ", createdAt=" + createdAt +
        ", updatedAt=" + updatedAt +
        ", resolved=" + resolved +
        '}';
  }

}
