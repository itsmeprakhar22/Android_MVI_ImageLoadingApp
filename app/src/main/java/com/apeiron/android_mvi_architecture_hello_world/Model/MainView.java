package com.apeiron.android_mvi_architecture_hello_world.Model;

import com.apeiron.android_mvi_architecture_hello_world.View.MainViewState;
import com.hannesdorfmann.mosby3.mvp.MvpView;

import io.reactivex.Observable;

public interface MainView extends MvpView {

    Observable<Integer> getImageIntent();

    void render(MainViewState mainViewState);
    
}
