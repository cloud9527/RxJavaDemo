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
import rx.functions.Action1;
import rx.functions.Func1;

public class RxJavaTakeActivity extends AppCompatActivity {

    @BindView(R.id.edit1)
    TextView edit1;
    @BindView(R.id.button)
    Button button;
    @BindView(R.id.text1)
    TextView text1;
    @BindView(R.id.linearlayout)
    LinearLayout linearlayout;
    private Integer[] number = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_rx_java);
        ButterKnife.bind(this);
        edit1.setText("输出[1,2,3,4,5,6,7,8,9,10]中第三个和第四个奇数，\n\ntake(i) 取i个事件 \ntakeLast(i) 取后i个事件 \ndoOnNext(Action1) 每次观察者中的onNext调用之前调用");

    }

    @OnClick(R.id.button)
    public void onViewClicked() {
        if (text1.getText().toString() != null || text1.getText().toString().length() > 0) {
            text1.setText("");
        }
        init();
    }

    private void init() {
        Observable.from(number)
                .filter(new Func1<Integer, Boolean>() {
                    @Override
                    public Boolean call(Integer integer) {
                        return integer % 2 != 0;
                    }
                })
                //倒数取出事件范围   takeLast与take不分代码的前后顺序，takeLast总是优先于take
                .takeLast(3)
                //设置取出事件的范围
                .take(10)
                //此方法在onNext前调用
                .doOnNext(new Action1<Integer>() {
                    @Override
                    public void call(Integer integer) {
                        text1.append("before onNext（）\n");
                    }
                })
                .subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer integer) {
                        text1.append("onNext()--->" + integer + "\n");
                    }
                });
    }
}
