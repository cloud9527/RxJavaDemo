package com.example.cloud.rxjavademo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jakewharton.rxbinding.view.RxView;
import com.jakewharton.rxbinding.widget.RxTextView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class RxJavaBindingActivity extends AppCompatActivity {


    @BindView(R.id.edit1)
    EditText edit1;
    @BindView(R.id.button)
    Button button;
    @BindView(R.id.text1)
    TextView text1;
    @BindView(R.id.linearlayout)
    LinearLayout linearlayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout3);
        ButterKnife.bind(this);
        edit1.setHint("输入含有1的数字，下方才会出现提示");
        text1.setText("提示数据：\n");
        //用来监听edittext输入，同时匹配输入数数据来提示
        RxTextView.textChanges(edit1)
                //设置没有操作的刷新时间
                .debounce(500, TimeUnit.MILLISECONDS)
                //切换子线程
                .observeOn(Schedulers.newThread())
                //通过输入的数据，来匹配"数据库"中的数据从而提示。。
                .map(new Func1<CharSequence, List<String>>() {

                    @Override
                    public List<String> call(CharSequence charSequence) {
                        ArrayList<String> strings = new ArrayList<>();
                        if (charSequence.toString().contains("1")) {
                            for (int i = 0; i < 5; i++) {
                                strings.add("11" + i);
                            }
                        }
                        return strings;
                    }
                })
                .flatMap(new Func1<List<String>, Observable<String>>() {
                    @Override
                    public Observable<String> call(List<String> strings) {
                        return Observable.from(strings);
                    }
                })
                //切换主线程
                .observeOn(AndroidSchedulers.mainThread())
                //执行过滤方法
                .filter(new Func1<String, Boolean>() {
                    @Override
                    public Boolean call(String s) {
                        return !text1.getText().toString().contains(s);
                    }
                })
                //订阅
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        //这里展示提示数据
                        text1.append(s + "\n");
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Log.w("DDDDDDDD", throwable.getMessage().toString());
                    }
                });
        button.setText("连续点击防误触");
        RxView.clicks(button)
                .throttleFirst(1500, TimeUnit.MILLISECONDS)
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        text1.append("\n 防误触 测试  \n");
                    }
                });

    }

    @OnClick(R.id.button)
    public void onViewClicked() {
        text1.append("lalal\n");
    }
}
