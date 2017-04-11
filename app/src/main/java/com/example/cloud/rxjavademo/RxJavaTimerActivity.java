package com.example.cloud.rxjavademo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

public class RxJavaTimerActivity extends AppCompatActivity {

    @BindView(R.id.edit1)
    TextView edit1;
    @BindView(R.id.button)
    Button button;
    @BindView(R.id.text1)
    TextView text1;
    @BindView(R.id.linearlayout)
    LinearLayout linearlayout;
    private Subscription subscription = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_rx_java);
        ButterKnife.bind(this);
        edit1.setText("定时器，每一秒发送打印一个数字   \n\ninterval(1, TimeUnit.SECONDS)  创建一个每隔一秒发送一次事件的对象");

    }

    @OnClick({R.id.edit1, R.id.button})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.edit1:
                if (subscription!=null && !subscription.isUnsubscribed()){
                    subscription.unsubscribe();
                }
                break;
            case R.id.button:
                init();
                break;
        }
    }

    private void init() {
        //interval（）是运行在computation Scheduler线程中的，因此需要转到主线程
        subscription = Observable.interval(1, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Long>() {
                    @Override
                    public void call(Long aLong) {
                        edit1.setText(aLong+"");
                    }
                });
    }
}
