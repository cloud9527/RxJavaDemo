package com.example.cloud.rxjavademo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;

public class RxJavaSortActivity extends AppCompatActivity {

    @BindView(R.id.edit1)
    TextView edit1;
    @BindView(R.id.button)
    Button button;
    @BindView(R.id.text1)
    TextView text1;
    @BindView(R.id.linearlayout)
    LinearLayout linearlayout;
    private Integer[] words = {1, 3, 5, 2, 34, 7, 5, 86, 23, 43};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_rx_java);
        ButterKnife.bind(this);
        button.setText("开始排序");
        text1.setText("为给定数据列表排序：1,3,5,2,34,7,5,86,23,43   \n\ntoSortedList() :为事件中的数据排序");
    }

    @OnClick({R.id.edit1, R.id.button})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.edit1:
                break;
            case R.id.button:
                init();
                break;
        }
    }

    private void init() {
        Observable.from(words)
                .toSortedList()
                .flatMap(new Func1<List<Integer>, Observable<Integer>>() {
                    @Override
                    public Observable<Integer> call(List<Integer> integers) {
                        return Observable.from(integers);
                    }
                })
                .subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer integer) {
                        text1.append(integer + "\n");
                    }
                });
    }
}
