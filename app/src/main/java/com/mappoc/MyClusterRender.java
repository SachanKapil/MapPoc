package com.mappoc;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.clustering.Cluster;
import com.google.maps.android.clustering.ClusterManager;
import com.google.maps.android.clustering.view.DefaultClusterRenderer;

import java.util.ArrayList;
import java.util.Collection;

public class MyClusterRender extends DefaultClusterRenderer<Person> {

    private BitmapDescriptor placeholderBitmapForMarker;
    private Context context;
    private BitmapDescriptor placeholderBitmapForCluster;
    private int tempLoaded = 0;

    public MyClusterRender(final Context context, GoogleMap map, ClusterManager<Person> clusterManager) {
        super(context, map, clusterManager);
        this.context = context;

        View markerView = LayoutInflater.from(context).inflate(R.layout.layout_cluster_marker, null);
        placeholderBitmapForMarker = generateBitmap(markerView);
        View clusterView = LayoutInflater.from(context).inflate(R.layout.layout_cluster, null);
        placeholderBitmapForCluster = generateBitmap(clusterView);
    }


    @Override
    protected void onBeforeClusterRendered(Cluster<Person> cluster, MarkerOptions markerOptions) {
    }

    @Override
    protected void onClusterRendered(Cluster<Person> cluster, Marker marker) {
        if (placeholderBitmapForCluster != null) {
            marker.setTag("Marker Tag");
            marker.setIcon(placeholderBitmapForCluster);
            createClusterMarkerLayout(marker, cluster);
        }
    }

    @Override
    protected void onClusterItemRendered(Person item, Marker marker) {
        if (placeholderBitmapForMarker != null) {
            marker.setTag("Marker Tag");
            marker.setIcon(placeholderBitmapForMarker);
            createCustomItemMarkerLayout(item, marker);
        }
    }

    @Override
    protected void onBeforeClusterItemRendered(Person item, MarkerOptions markerOptions) {
    }

    private BitmapDescriptor generateBitmap(View view) {
        view.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
        view.buildDrawingCache();
        Bitmap returnedBitmap = Bitmap.createBitmap(view.getMeasuredWidth(), view.getMeasuredHeight(),
                Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(returnedBitmap);
        canvas.drawColor(Color.WHITE, PorterDuff.Mode.SRC_IN);
        Drawable drawable = view.getBackground();
        if (drawable != null)
            drawable.draw(canvas);
        view.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(returnedBitmap);
    }

    @Override
    public void setOnClusterItemClickListener(ClusterManager.OnClusterItemClickListener<Person> listener) {
        super.setOnClusterItemClickListener(listener);
    }

    @Override
    public Person getClusterItem(Marker marker) {
        return super.getClusterItem(marker);
    }

    private void createCustomItemMarkerLayout(Person person, final Marker marker) {
        final View customClusterItemMarkerView = LayoutInflater.from(context).inflate(R.layout.layout_cluster_marker, null);
        final AppCompatImageView ivProfile = customClusterItemMarkerView.findViewById(R.id.iv_profile);
        Glide.with(customClusterItemMarkerView.getContext())
                .load(person.getProfileUrl())
                .apply(RequestOptions.circleCropTransform())
                .into(new CustomTarget<Drawable>() {
                    @Override
                    public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                        ivProfile.setImageDrawable(resource);
                        setMarker(marker, customClusterItemMarkerView);
                    }

                    @Override
                    public void onLoadCleared(@Nullable Drawable placeholder) {
                        Log.e("a", "onLoadCleared: ");
                    }
                });
    }


    private void createClusterMarkerLayout(Marker marker, Cluster<Person> cluster) {
        View customClusterMarkerView = LayoutInflater.from(context).inflate(R.layout.layout_cluster, null);
        AppCompatImageView ivUserImageOne = customClusterMarkerView.findViewById(R.id.iv_one);
        AppCompatImageView ivUserImageTwo = customClusterMarkerView.findViewById(R.id.iv_two);
        AppCompatTextView tvCount = customClusterMarkerView.findViewById(R.id.tv_count);
        tvCount.setText("" + cluster.getSize());
        ArrayList<String> firstTwoImages = getFirstTwoImages(cluster.getItems());
        setClusterMarker(marker, customClusterMarkerView, ivUserImageOne, ivUserImageTwo, firstTwoImages);
    }

    private ArrayList<String> getFirstTwoImages(Collection<Person> items) {
        ArrayList<String> images = new ArrayList<>();
        for (Person person : items) {
            if (!TextUtils.isEmpty(person.getProfileUrl())) {
                images.add(person.getProfileUrl());
                if (images.size() == 2) {
                    break;
                }
            }
        }
        return images;
    }

    private void setClusterMarker(final Marker marker, final View view, final ImageView ivUserImageOne, final ImageView ivUserImageTwo, ArrayList<String> firstTwoImages) {

        Glide.with(ivUserImageOne.getContext())
                .load(firstTwoImages.get(0))
                .apply(RequestOptions.circleCropTransform())
                .into(new CustomTarget<Drawable>() {
                    @Override
                    public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                        ivUserImageOne.setImageDrawable(resource);
                        tempLoaded++;
                        if (tempLoaded == 2) {
                            setMarker(marker, view);
                        }
                    }

                    @Override
                    public void onLoadCleared(@Nullable Drawable placeholder) {
                        Log.e("a", "onLoadCleared: ");
                    }
                });
        Glide.with(ivUserImageOne.getContext())
                .load(firstTwoImages.get(1))
                .apply(RequestOptions.circleCropTransform())
                .into(new CustomTarget<Drawable>() {
                    @Override
                    public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                        ivUserImageTwo.setImageDrawable(resource);
                        tempLoaded++;
                        if (tempLoaded == 2) {
                            setMarker(marker, view);
                        }
                    }

                    @Override
                    public void onLoadCleared(@Nullable Drawable placeholder) {
                        Log.e("a", "onLoadCleared: ");
                    }
                });
    }

    private void setMarker(Marker marker, View view) {
        tempLoaded = 0;
        marker.setIcon(generateBitmap(view));
    }

}

