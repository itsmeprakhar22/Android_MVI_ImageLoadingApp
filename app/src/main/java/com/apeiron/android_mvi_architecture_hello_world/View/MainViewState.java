package com.apeiron.android_mvi_architecture_hello_world.View;

public class MainViewState {
    boolean isImageLoading;
    boolean isImageViewShow;
    String imageLink;
    Throwable error;

    public MainViewState(boolean isImageLoading, boolean isImageViewShow, String imageLink, Throwable error) {
        this.isImageLoading = isImageLoading;
        this.isImageViewShow = isImageViewShow;
        this.imageLink = imageLink;
        this.error = error;
    }
}
