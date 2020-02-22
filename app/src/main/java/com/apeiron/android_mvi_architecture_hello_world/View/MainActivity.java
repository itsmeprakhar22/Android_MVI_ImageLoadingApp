package com.apeiron.android_mvi_architecture_hello_world.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.apeiron.android_mvi_architecture_hello_world.Model.MainView;
import com.apeiron.android_mvi_architecture_hello_world.R;
import com.apeiron.android_mvi_architecture_hello_world.Util.DataSource;
import com.hannesdorfmann.mosby3.mvi.MviActivity;
import com.jakewharton.rxbinding2.view.RxView;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import io.reactivex.Observable;

public class MainActivity extends MviActivity<MainView, MainPresenter> implements MainView {

    ImageView imageView;
    Button button;
    ProgressBar progressBar;

    List<String> imageList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView = findViewById(R.id.image_view);
        button = findViewById(R.id.btn_get);
        progressBar = findViewById(R.id.progressbar);
        
        imageList = createImageList();
    }

    private List<String> createImageList() {
        return Arrays.asList("https://via.placeholder.com/300.png/09f/fff/O https://placeholder.com/",
                "https://via.placeholder.com/150/0000FF/808080%20?Text=Digital.comC/O%20https://placeholder.com/",
                "https://via.placeholder.com/150/FF0000/FFFFFF?Text=Down.comC/O%20https://placeholder.com/ ",
                "https://via.placeholder.com/150/0000FF/808080%20?Text=Dig.comC/O%20https://placeholder.com/",
                "https://via.placeholder.com/300.png/09f/fff/O https://placeholder.com/",
                "https://via.placeholder.com/150/0000FF/808080%20?Text=Digital.comC/O%20https://placeholder.com/",
                "https://via.placeholder.com/150/0000FF/808080%20?Text=Dig.comC/O%20https://placeholder.com/");
    }

    @NonNull
    @Override
    public MainPresenter createPresenter() {
        return new MainPresenter(new DataSource(imageList));
    }

    @Override
    public Observable<Integer> getImageIntent() {
        return RxView.clicks(button).map(click->getRandomInRange(0, imageList.size()-1));
    }

    private Integer getRandomInRange(int min, int max) {
        if(min>=max)
            throw new IllegalArgumentException("Max must be greater than Min");

        Random r  = new Random();
        return r.nextInt((max-min)+1)+min;
    }

    @Override
    public void render(MainViewState mainViewState) {
        //Here we will process change state to display view
        if(mainViewState.isImageLoading){
            progressBar.setVisibility(View.VISIBLE);
            imageView.setVisibility(View.GONE);
            button.setEnabled(false);
        }
        else if(mainViewState.error!=null){
            progressBar.setVisibility(View.GONE);
            imageView.setVisibility(View.GONE);
            button.setEnabled(true);
            Toast.makeText(this, mainViewState.error.getMessage(), Toast.LENGTH_SHORT).show();
        }
        else if(mainViewState.isImageViewShow){
            button.setEnabled(true);
            Picasso.get().load(mainViewState.imageLink)
                    .fetch(new Callback() {
                        @Override
                        public void onSuccess() {
                            imageView.setAlpha(0f);
                            Picasso.get().load(mainViewState.imageLink)
                                    .into(imageView);
                            imageView.animate().setDuration(300)
                                    .alpha(1f).start();
                            progressBar.setVisibility(View.GONE);
                            imageView.setVisibility(View.VISIBLE);
                        }

                        @Override
                        public void onError(Exception e) {

                        }
                    });
        }

    }
}
