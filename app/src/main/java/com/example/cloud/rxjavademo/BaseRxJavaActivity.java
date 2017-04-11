package com.example.cloud.rxjavademo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;
import rx.Subscriber;

/**
 * RXJAVA简单流程
 */
public class BaseRxJavaActivity extends AppCompatActivity {

    @BindView(R.id.edit1)
    TextView edit1;
    @BindView(R.id.button)
    Button button;
    @BindView(R.id.text1)
    TextView text1;
    static String str = "岭外音书绝\n经冬复立春\n近乡情更怯\n不敢问来人\n";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_rx_java);
        ButterKnife.bind(this);
        edit1.setText(str);
    }

    @OnClick({R.id.edit1, R.id.button, R.id.text1})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.edit1:
                break;
            case R.id.button:
                if (text1.getText().toString() != null || text1.getText().toString().length() > 0) {
                    text1.setText("");
                }
                init();
                break;
            case R.id.text1:
                break;
        }
    }

    void init() {
        Observable observable = createObservable();
        Subscriber subscriber = createSub();
        text1.append("准备订阅\n");
        observable.subscribe(subscriber);
    }

    private Observable createObservable() {
        //被观察者 方式一
//        Observable observable = Observable.create(new Observable.OnSubscribe<String>() {
//            @Override
//            public void call(Subscriber<? super String> subscriber) {
//                subscriber.onNext("岭外音书绝");
//                subscriber.onNext("经冬复立春");
//                subscriber.onNext("近乡情更怯");
//                subscriber.onNext("不敢问来人");
//                subscriber.onCompleted();
//            }
//
//        });
        //方式二：
//        String[] kk = {"一二三四五", "上山打老虎", "老虎一发威", "武松就发怵"};
//        Observable observable = Observable.from(kk);
        //方式三
        Observable observable = Observable.just("一二三四五", "上山打老虎", "老虎一发威", "武松就发怵");

        return observable;
    }

    private Subscriber createSub() {
        Subscriber subscriber = new Subscriber<String>() {
            @Override
            public void onCompleted() {
                text1.append("执行观察者中的onCompleted()...\n");
                text1.append("订阅完毕，结束观察...\n");
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(String s) {
                text1.append("执行观察者中的onNext()...\n");
                text1.append(s + "...\n");
            }
        };
        return subscriber;
    }
}
