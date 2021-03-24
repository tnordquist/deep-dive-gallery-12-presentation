package edu.cnm.deepdive.deepdivegallery12presentation.model;

import com.google.gson.annotations.Expose;
import java.util.Date;
import java.util.UUID;

public class Image {

  @Expose
  private UUID id;

  @Expose
  private Date created;

  @Expose
  private String name;

  @Expose
  private String title;

  @Expose
  private String description;

  @Expose
  private String contentType;

  @Expose
  private String href;

  @Expose
  private User contributor;

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public Date getCreated() {
    return created;
  }

  public void setCreated(Date created) {
    this.created = created;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getContentType() {
    return contentType;
  }

  public void setContentType(String contentType) {
    this.contentType = contentType;
  }

  public String getHref() {
    return href;
  }

  public void setHref(String href) {
    this.href = href;
  }

  public User getContributor() {
    return contributor;
  }

  public void setContributor(User contributor) {
    this.contributor = contributor;
  }
}