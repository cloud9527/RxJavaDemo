package com.example.cloud.rxjavademo;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class RxJavaSchuderActivity extends AppCompatActivity {

    @BindView(R.id.edit1)
    TextView edit1;
    @BindView(R.id.button)
    Button button;
    @BindView(R.id.text1)
    TextView text1;
    @BindView(R.id.linearlayout)
    LinearLayout linearlayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_rx_java);
        ButterKnife.bind(this);

        button.setText("获取资源图片并展示");
    }

    @OnClick(R.id.button)
    public void onViewClicked() {
        if (text1.getText().toString().length() > 0) {
            text1.setText("");
        }
        init();
    }

    static StringBuffer sb = null;

    private void init() {
        sb = new StringBuffer();
        Observable.create(new Observable.OnSubscribe<Drawable>() {

            @Override
            public void call(Subscriber<? super Drawable> subscriber) {
                sb.append(" Observable.create(): 线程: " + Thread.currentThread().getName() + "\n\n");
                Drawable d = getResources().getDrawable(R.drawable.boy);
                subscriber.onNext(d);
                subscriber.onCompleted();
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(Schedulers.newThread())
                .map(new Func1<Drawable, ImageView>() {
                    @Override
                    public ImageView call(Drawable drawable) {
                        sb.append("map():  drawable -->imageview 的线程: " + Thread.currentThread().getName() + "\n\n");
                        ImageView imageView = new ImageView(RxJavaSchuderActivity.this);
                        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                        imageView.setLayoutParams(layoutParams);
                        imageView.setImageDrawable(drawable);
                        return imageView;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<ImageView>() {
                    @Override
                    public void call(ImageView imageView) {
                        sb.append("call(): 线程: " + Thread.currentThread().getName() + "\n");
                        text1.setText(sb);
                        linearlayout.addView(imageView);
                    }
                });
    }
}
