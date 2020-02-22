package com.apeiron.android_mvi_architecture_hello_world.Util;

import java.util.List;

import io.reactivex.Observable;

public class DataSource {
    List<String> imageList;

    public DataSource(List<String> imageList) {
        this.imageList = imageList;
    }

    public Observable<String> getImageLinkFromList(int index){
        return Observable.just(imageList.get(index));
    }
}
