package com.example.cloud.rxjavademo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class RxJavaMergeActivity extends AppCompatActivity {

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
        edit1.setText("两个任务并发进行，全部处理完毕之后在更新数据");
    }

    @OnClick(R.id.button)
    public void onViewClicked() {
        if (text1.getText().toString().length() > 0) {
            text1.setText("");
        }
        init();
    }

    private void init() {
        Observable observable = Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                try {
                    Thread.sleep(3000);
                    subscriber.onNext("aaa");
                    subscriber.onCompleted();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }).subscribeOn(Schedulers.newThread());
        Observable observable1 = Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                try {
                    Thread.sleep(5000);
                    subscriber.onNext("bbb");
                    subscriber.onCompleted();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }).subscribeOn(Schedulers.newThread());
        Observable.merge(observable, observable1)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<String>() {
                    StringBuffer sb = new StringBuffer();

                    @Override
                    public void onCompleted() {
                        text1.append("两个任务都处理完毕！！\n");
                        text1.append("更新数据：" + sb + "\n");
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(String s) {
                        sb.append(s + ",");
                        text1.append("得到一个数据：" + s + "\n");
                    }

                });
    }
}
