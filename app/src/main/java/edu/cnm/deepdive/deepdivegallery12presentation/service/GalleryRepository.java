package edu.cnm.deepdive.deepdivegallery12presentation.service;

import android.content.Context;
import edu.cnm.deepdive.deepdivegallery12presentation.model.Gallery;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import java.util.List;
import java.util.UUID;

public class GalleryRepository {

  private final Context context;
  private final GalleryServiceProxy serviceProxy;
  private final GoogleSignInService signInService;

  public GalleryRepository(Context context) {
    this.context = context;
    serviceProxy = GalleryServiceProxy.getInstance();
    signInService = GoogleSignInService.getInstance();
  }

  public Single<List<Gallery>> getAll() {
    return signInService.refreshBearerToken()
        .observeOn(Schedulers.io())
        .flatMap(serviceProxy::getAllGalleries);
  }

  public Single<Gallery> getGalleryForImages(UUID galleryId) {
    return signInService.refreshBearerToken()
        .observeOn(Schedulers.io())
        .flatMap((account) -> serviceProxy.getGallery(galleryId,account));
  }

}
