package edu.cnm.deepdive.deepdivegallery12presentation.model;

import android.media.Image;
import com.google.gson.annotations.Expose;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class Gallery implements Serializable {

  private static final long serialVersionUID = 2723203273848490920L;

  @Expose
  private UUID id;

  @Expose
  private Date created;

  @Expose
  private String title;

  @Expose
  private String description;

  @Expose
  private String href;

  @Expose
  private User contributor;

  @Expose
  private List<Image> images;

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

  public List<Image> getImages() {
    return images;
  }

  public void setImages(List<Image> images) {
    this.images = images;
  }
}