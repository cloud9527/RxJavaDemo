package com.example.cloud.rxjavademo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;
import rx.functions.Action1;
import rx.schedulers.Timestamped;

public class RxTimestampActivity extends AppCompatActivity {

    @BindView(R.id.edit1)
    TextView edit1;
    @BindView(R.id.button)
    Button button;
    @BindView(R.id.text1)
    TextView text1;
    @BindView(R.id.linearlayout)
    LinearLayout linearlayout;
    private Integer [] words={1,3,5,2,34,7,5,86,23,43};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_rx_java);
        ButterKnife.bind(this);
        edit1.setText("为给定数据列表：1,3,5,2,34,7,5,86,23,43中每一个数据加上一个时间戳   \n\ntimestamp() :为每个事件加上一个时间戳" );
    }


    @OnClick(R.id.button)
    public void onViewClicked() {
        text1.setText("");
        init();

    }

    private void init() {
        Observable.from(words)
                .timestamp()
                .subscribe(new Action1<Timestamped<Integer>>() {
                    @Override
                    public void call(Timestamped<Integer> integerTimestamped) {
                        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd-HH:mm:ss");
                        text1.append("value: "+integerTimestamped.getValue()+"       time:   ");
                        text1.append(sdf.format(new Date(integerTimestamped.getTimestampMillis()))+"\n");
                    }
                });
    }
}
