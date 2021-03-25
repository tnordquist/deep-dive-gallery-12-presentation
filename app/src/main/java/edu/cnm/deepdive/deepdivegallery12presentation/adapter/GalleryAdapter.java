package edu.cnm.deepdive.deepdivegallery12presentation.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import edu.cnm.deepdive.deepdivegallery12presentation.adapter.GalleryAdapter.Holder;
import edu.cnm.deepdive.deepdivegallery12presentation.databinding.ItemGalleryBinding;
import edu.cnm.deepdive.deepdivegallery12presentation.model.Gallery;
import java.util.List;

public class GalleryAdapter extends RecyclerView.Adapter<Holder> {

  private final Context context;
  private final List<Gallery> galleries;
  private final LayoutInflater inflater;
  private final OnGalleryClickHelper onGalleryClickHelper;

  public GalleryAdapter(Context context, List<Gallery> galleries,
      OnGalleryClickHelper onGalleryClickHelper) {
    this.context = context;
    inflater = LayoutInflater.from(context);
    this.onGalleryClickHelper = onGalleryClickHelper;
    this.galleries = galleries;
  }

  @NonNull
  @Override
  public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    ItemGalleryBinding binding = ItemGalleryBinding.inflate(inflater, parent, false);
    return new Holder(binding, onGalleryClickHelper);
  }

  @Override
  public void onBindViewHolder(@NonNull Holder holder, int position) {
    holder.bind(position);
  }

  @Override
  public int getItemCount() {
    return galleries.size();
  }

  class Holder extends RecyclerView.ViewHolder implements OnClickListener {

    private final ItemGalleryBinding binding;
    private Gallery gallery;
    OnGalleryClickHelper onGalleryClickHelper;

    private Holder(ItemGalleryBinding binding, OnGalleryClickHelper onGalleryClickHelper) {
      super(binding.getRoot());
      this.binding = binding;
      this.onGalleryClickHelper = onGalleryClickHelper;
      binding.getRoot().setOnClickListener(this);
    }

    private void bind(int position) {
      gallery = galleries.get(position);
      binding.galleryTitle.setText(gallery.getTitle());
      binding.galleryDescription.setText(gallery.getDescription());
      binding.getRoot().setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
      onGalleryClickHelper
          .onGalleryClick(galleries.get(getAdapterPosition()).getId().toString(), view,
              getAdapterPosition());
    }
  }

  public interface OnGalleryClickHelper {

    void onGalleryClick(String galleryId, View view, int position);
  }
}