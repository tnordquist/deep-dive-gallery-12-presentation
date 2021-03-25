package edu.cnm.deepdive.deepdivegallery12presentation.controller;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import edu.cnm.deepdive.deepdivegallery12presentation.NavGraphDirections;
import edu.cnm.deepdive.deepdivegallery12presentation.NavGraphDirections.OpenUploadProperties;
import edu.cnm.deepdive.deepdivegallery12presentation.R;
import edu.cnm.deepdive.deepdivegallery12presentation.adapter.GalleryAdapter;
import edu.cnm.deepdive.deepdivegallery12presentation.adapter.GalleryAdapter.OnGalleryClickHelper;
import edu.cnm.deepdive.deepdivegallery12presentation.databinding.FragmentGalleryBinding;
import edu.cnm.deepdive.deepdivegallery12presentation.model.Image;
import edu.cnm.deepdive.deepdivegallery12presentation.viewmodel.GalleryViewModel;
import edu.cnm.deepdive.deepdivegallery12presentation.viewmodel.ImageViewModel;
import java.util.List;
import org.jetbrains.annotations.NotNull;

public class GalleryFragment extends Fragment implements OnGalleryClickHelper {

  private static final int PICK_IMAGE_REQUEST = 1023;
  private FragmentGalleryBinding binding;
  private GalleryViewModel galleryViewModel;
  private ImageViewModel imageViewModel;
  private GalleryAdapter adapter;

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setHasOptionsMenu(true);
  }

  public View onCreateView(@NonNull
      LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    binding = FragmentGalleryBinding.inflate(inflater);
    binding.addImage.setOnClickListener((v) -> {
      pickImage();
    });
    return binding.getRoot();
  }

  @Override
  public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    //noinspection ConstantConditions
    galleryViewModel = new ViewModelProvider(getActivity()).get(GalleryViewModel.class);
    imageViewModel = new ViewModelProvider(getActivity()).get(ImageViewModel.class);
    galleryViewModel.getGalleries().observe(getViewLifecycleOwner(), (galleries) -> {
      if (galleries != null) {
        binding.galleryView.setAdapter(new GalleryAdapter(getContext(), galleries, this));
      }
    });
  }

  @Override
  public void onCreateOptionsMenu(@NonNull @NotNull Menu menu,
      @NonNull MenuInflater inflater) {
    super.onCreateOptionsMenu(menu, inflater);
    inflater.inflate(R.menu.menu_gallery, menu);
  }


  @Override
  public boolean onOptionsItemSelected(@NonNull MenuItem item) {
    boolean handled = true;
    //noinspection SwitchStatementWithTooFewBranches
    switch (item.getItemId()) {
      case R.id.action_refresh:
        imageViewModel.loadImages();
        break;
      default:
        handled = super.onOptionsItemSelected(item);
    }
    return handled;
  }


  @Override
  public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null) {
      OpenUploadProperties action = NavGraphDirections.openUploadProperties(data.getData());
      Navigation.findNavController(binding.getRoot()).navigate(action);
    }
  }

  private void pickImage() {
    Intent intent = new Intent();
    intent.setType("image/*");
    intent.setAction(Intent.ACTION_GET_CONTENT);
    startActivityForResult(Intent.createChooser(intent, "Choose image to upload"),
        PICK_IMAGE_REQUEST);
  }

  // TODO Add functionality to update list of galleries.
/*
  private void updateGallery(List<Image> images) {
    .getImages().clear();
    adapter.getImages().addAll(images);
    adapter.notifyDataSetChanged();
  }
*/

  @Override
  public void onGalleryClick(String galleryId, View view, int position) {
    GalleryFragmentDirections.GalleryFragmentToImageFragment toImageFragment = GalleryFragmentDirections
        .galleryFragmentToImageFragment(galleryId);
    toImageFragment.setGalleryImages(galleryId);
    Navigation.findNavController(view).navigate(toImageFragment);
  }
}