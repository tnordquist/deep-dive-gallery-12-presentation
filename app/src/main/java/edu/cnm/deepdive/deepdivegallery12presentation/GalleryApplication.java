package edu.cnm.deepdive.deepdivegallery12presentation;

import android.app.Application;
import edu.cnm.deepdive.deepdivegallery12presentation.service.GoogleSignInService;

public class GalleryApplication extends Application {

  @Override
  public void onCreate() {
    super.onCreate();
    GoogleSignInService.setContext(this);
  }
}