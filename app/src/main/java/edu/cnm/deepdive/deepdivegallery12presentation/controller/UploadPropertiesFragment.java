package edu.cnm.deepdive.deepdivegallery12presentation.controller;

import android.app.Dialog;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AlertDialog.Builder;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;
import com.squareup.picasso.Picasso;
import edu.cnm.deepdive.deepdivegallery12presentation.R;
import edu.cnm.deepdive.deepdivegallery12presentation.databinding.FragmentUploadPropertiesBinding;
import edu.cnm.deepdive.deepdivegallery12presentation.model.Gallery;
import edu.cnm.deepdive.deepdivegallery12presentation.viewmodel.GalleryViewModel;
import edu.cnm.deepdive.deepdivegallery12presentation.viewmodel.ImageViewModel;
import java.util.List;
import java.util.UUID;

public class UploadPropertiesFragment extends DialogFragment implements TextWatcher {


  private FragmentUploadPropertiesBinding binding;
  private Uri uri;
  private AlertDialog dialog;
  private GalleryViewModel galleryViewModel;
  private ImageViewModel imageViewModel;
  private Gallery gallery;
  private List<Gallery> galleries;
  String galleryTitle;

  @NonNull
  @Override
  public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
    //noinspection ConstantConditions
    uri = UploadPropertiesFragmentArgs.fromBundle(getArguments()).getContentUri();
    binding =
        FragmentUploadPropertiesBinding.inflate(LayoutInflater.from(getContext()), null, false);
    dialog = new Builder(getContext())
        .setIcon(R.drawable.ic_upload)
        .setTitle(R.string.upload_properties_title)
        .setView(binding.getRoot())
        .setNeutralButton(android.R.string.cancel, (dlg, which) -> {/* No need to do anything. */})
        .setPositiveButton(android.R.string.ok, (dlg, which) -> upload())
        .create();
    dialog.setOnShowListener((dlg) -> checkSubmitConditions());
    return dialog;
  }

  @Override
  public View onCreateView(
      @NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    return binding.getRoot();
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    Picasso
        .get()
        .load(uri)
        .into(binding.image);
    binding.title.addTextChangedListener(this);
    binding.description.addTextChangedListener(this);
    binding.galleryTitle.addTextChangedListener(this);
    //noinspection ConstantConditions
    imageViewModel = new ViewModelProvider(getActivity()).get(ImageViewModel.class);
    galleryViewModel = new ViewModelProvider(getActivity()).get(GalleryViewModel.class);
    galleryViewModel.getGalleries().observe(getViewLifecycleOwner(),
        (galleries) -> UploadPropertiesFragment.this.galleries = galleries);
  }

  @Override
  public void beforeTextChanged(CharSequence s, int start, int count, int after) {
  }

  @Override
  public void onTextChanged(CharSequence s, int start, int before, int count) {
  }

  @Override
  public void afterTextChanged(Editable s) {
    checkSubmitConditions();
  }

  private void checkSubmitConditions() {
    Button positive = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
    //noinspection ConstantConditions
    positive.setEnabled(!binding.title.getText().toString().trim().isEmpty());
  }

  @SuppressWarnings("ConstantConditions")
  private void upload() {
    String title = binding.title.getText().toString().trim();
    String description = binding.description.getText().toString().trim();
    galleryTitle = binding.galleryTitle.getText().toString().trim();
    String titleId = "";
    for (Gallery g : galleries) {
      if (g != null && galleryTitle.equalsIgnoreCase(g.getTitle())) {
        titleId = g.getId().toString();
      }
    }
    UUID uuid = UUID.fromString(titleId);
    imageViewModel.store(uuid, uri, title, (description.isEmpty() ? null : description));
  }

}