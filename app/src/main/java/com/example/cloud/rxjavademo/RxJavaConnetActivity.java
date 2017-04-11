package com.example.cloud.rxjavademo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;


import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;
import rx.functions.Action1;
import rx.observables.ConnectableObservable;

public class RxJavaConnetActivity extends AppCompatActivity {

    @BindView(R.id.edit1)
    TextView edit1;
    @BindView(R.id.button)
    Button button;
    @BindView(R.id.button_cancal)
    Button buttonCancal;
    @BindView(R.id.text1)
    TextView text1;
    @BindView(R.id.linearlayout)
    LinearLayout linearlayout;
    private Integer[] integer = {1, 2, 3, 4, 5, 6};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout2);
        ButterKnife.bind(this);
        button.setText("正常情况下");

        buttonCancal.setText("connect模式");

        edit1.setText("Observable发送事件1-6，两个观察者同时观察这个Observable \n要求：每发出一个事件，观察者A和观察者都会收到，而不是先把所有的时间发送A,然后再发送给B  \n\n");
    }

    @OnClick({R.id.button, R.id.button_cancal})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.button:
                text1.setText("");
                begin();
                break;
            case R.id.button_cancal:
                cancel();
                break;
        }
    }

    private void cancel() {
        //将一个Observable转换为一个可连接的Observable
        ConnectableObservable observable = ConnectableObservable.from(integer).publish();
        Action1 actionA = new Action1<Integer>() {
            @Override
            public void call(Integer integer) {
                text1.append("观察者A  收到:  " + integer + "\n");
            }
        };
        Action1 actionB = new Action1<Integer>() {
            @Override
            public void call(Integer integer) {
                text1.append("观察者B  收到:  " + integer + "\n");
            }
        };
        observable.subscribe(actionA);
        observable.subscribe(actionB);
        observable.connect();
    }

    private void begin() {
        Observable<Integer> from = Observable.from(integer);
        Action1 actionA = new Action1<Integer>() {
            @Override
            public void call(Integer integer) {
                text1.append("观察者A  收到:  " + integer + "\n");
            }
        };
        Action1 actionB = new Action1<Integer>() {
            @Override
            public void call(Integer integer) {
                text1.append("观察者B  收到:  " + integer + "\n");
            }
        };
        from.subscribe(actionA);
        from.subscribe(actionB);
    }


}
