package edu.cnm.deepdive.deepdivegallery12presentation.controller;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import edu.cnm.deepdive.deepdivegallery12presentation.adapter.ImageAdapter;
import edu.cnm.deepdive.deepdivegallery12presentation.adapter.ImageAdapter.OnImageClickHelper;
import edu.cnm.deepdive.deepdivegallery12presentation.databinding.FragmentImageBinding;
import edu.cnm.deepdive.deepdivegallery12presentation.model.Image;
import edu.cnm.deepdive.deepdivegallery12presentation.viewmodel.GalleryViewModel;
import edu.cnm.deepdive.deepdivegallery12presentation.viewmodel.ImageViewModel;
import java.util.UUID;
import org.jetbrains.annotations.NotNull;

public class ImageFragment extends Fragment implements OnImageClickHelper{

  private FragmentImageBinding binding;
  private GalleryViewModel viewModel;
  public UUID galleryId;

  @Override
  public void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setHasOptionsMenu(true);
  }

  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    binding = FragmentImageBinding.inflate(inflater);
    return binding.getRoot();
  }

  @Override
  public void onViewCreated(@NonNull @NotNull View view,
      @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    //noinspection ConstantConditions
    viewModel = new ViewModelProvider(getActivity()).get(GalleryViewModel.class);
    if (getArguments() != null) {
      ImageFragmentArgs args = ImageFragmentArgs.fromBundle(getArguments());
      galleryId = UUID.fromString(args.getGalleryImages());
    }
    viewModel.getGalleryById(galleryId);
    viewModel.getGallery().observe(getViewLifecycleOwner(), (gallery) -> {
      if (gallery != null) {
        binding.imageView.setAdapter(new ImageAdapter(getContext(), gallery.getImages(), this));
      }
    });
    viewModel.getThrowable().observe(getViewLifecycleOwner(), (throwable) -> {
      if (throwable != null) {
        Snackbar.make(binding.getRoot(), throwable.getMessage(),
            BaseTransientBottomBar.LENGTH_INDEFINITE).show();
      }
    });
  }

  @Override
  public void onImageClick(Image image, int position) {
   /* ImageDetailDialogFragment fragment = ImageDetailDialogFragment.newInstance(image);
    fragment.show(getChildFragmentManager(), fragment.getClass().getName());*/
  }

  public interface OnFragmentInteractionListener {

    void onFragmentInteraction(Uri uri);
  }

}