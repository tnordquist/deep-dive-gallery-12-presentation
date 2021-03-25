package edu.cnm.deepdive.deepdivegallery12presentation.viewmodel;

import android.app.Application;
import android.net.Uri;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.Lifecycle.Event;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.OnLifecycleEvent;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import edu.cnm.deepdive.deepdivegallery12presentation.model.Gallery;
import edu.cnm.deepdive.deepdivegallery12presentation.model.Image;
import edu.cnm.deepdive.deepdivegallery12presentation.model.User;
import edu.cnm.deepdive.deepdivegallery12presentation.service.GalleryRepository;
import edu.cnm.deepdive.deepdivegallery12presentation.service.ImageRepository;
import edu.cnm.deepdive.deepdivegallery12presentation.service.UserRepository;
import io.reactivex.disposables.CompositeDisposable;
import java.util.List;

public class GalleryViewModel extends AndroidViewModel implements LifecycleObserver {

  private final GalleryRepository galleryRepository;
  private final MutableLiveData<List<Gallery>> galleries;
  private final MutableLiveData<Gallery> gallery;
  private final MutableLiveData<Throwable> throwable;
  private final CompositeDisposable pending;

  public GalleryViewModel(@NonNull Application application) {
    super(application);
    galleryRepository = new GalleryRepository(application);
    gallery = new MutableLiveData<>();
    galleries = new MutableLiveData<>();
    throwable = new MutableLiveData<>();
    pending = new CompositeDisposable();
    loadGalleries();
  }


  public LiveData<List<Gallery>> getGalleries() {
    return galleries;
  }

  public LiveData<Gallery> getGallery() {
    return gallery;
  }

  public LiveData<Throwable> getThrowable() {
    return throwable;
  }

  public void loadGalleries() {
    throwable.setValue(null);
    pending.add(
        galleryRepository.getAll()
            .subscribe(
                galleries::postValue,
                throwable::postValue
            )
    );
  }

  @OnLifecycleEvent(Event.ON_STOP)
  private void clearPending() {
    pending.clear();
  }

  private void postThrowable(Throwable throwable) {
    Log.e(getClass().getName(), throwable.getMessage(), throwable);
    this.throwable.postValue(throwable);
  }

}