package com.example.cloud.rxjavademo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;


public class RxJavaMapActivity extends AppCompatActivity {

    @BindView(R.id.edit1)
    TextView edit1;
    @BindView(R.id.button)
    Button button;
    @BindView(R.id.text1)
    TextView text1;
    private Integer[] number = {1, 2, 3, 4, 5, 6};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_rx_java);
        ButterKnife.bind(this);

        button.setText("判断数组中的小于3的数");
        edit1.setText("输入Integer(int)：1,2,3,4,5,6 \n" + "\n" + "输出：type:true/false \n");
    }

    @OnClick(R.id.button)
    public void onViewClicked() {
        if (text1.getText().toString() != null || text1.getText().toString().length() > 0) {
            text1.setText("");
            init();
        }
    }

    void init() {
        text1.append("\n 输入参数： 1,2,3,4,5,6 \n");
        Observable.from(number)
                .map(new Func1<Integer, Boolean>() {
                    @Override
                    public Boolean call(Integer integer) {
                        text1.append("\n\n map()  Integer--->Boolean");
                        return integer < 6;
                    }
                })
                .subscribe(new Action1<Boolean>() {
                    @Override
                    public void call(Boolean aBoolean) {
                        text1.append("\n观察到输出结果：\n");
                        text1.append(aBoolean.toString());
                    }
                });
    }
}
