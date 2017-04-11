package com.example.cloud.rxjavademo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.button1)
    Button button1;
    @BindView(R.id.button2)
    Button button2;
    @BindView(R.id.button3)
    Button button3;
    @BindView(R.id.button4)
    Button button4;
    @BindView(R.id.button5)
    Button button5;
    @BindView(R.id.button6)
    Button button6;
    @BindView(R.id.button7)
    Button button7;
    @BindView(R.id.button8)
    Button button8;
    @BindView(R.id.button9)
    Button button9;
    @BindView(R.id.button10)
    Button button10;
    @BindView(R.id.button11)
    Button button11;
    @BindView(R.id.button12)
    Button button12;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

    }

    @OnClick({R.id.button1, R.id.button2, R.id.button3, R.id.button4, R.id.button5, R.id.button6, R.id.button7
            , R.id.button8, R.id.button9, R.id.button10, R.id.button11, R.id.button12,})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.button1:
                startActivity(new Intent(this, BaseRxJavaActivity.class));
                break;
            case R.id.button2:
                startActivity(new Intent(this, RxJavaMapActivity.class));
                break;
            case R.id.button3:
                startActivity(new Intent(this, RxJavaSchuderActivity.class));
                break;
            case R.id.button4:
                startActivity(new Intent(this, RxJavaFlatMapActivity.class));
                break;
            case R.id.button5:
                startActivity(new Intent(this, RxJavaMergeActivity.class));
                break;
            case R.id.button6:
                startActivity(new Intent(this, RxJavaBindingActivity.class));
                break;
            case R.id.button7:
                startActivity(new Intent(this, RxJavaFilterActivity.class));
                break;
            case R.id.button8:
                startActivity(new Intent(this, RxJavaTakeActivity.class));
                break;
            case R.id.button9:
                startActivity(new Intent(this, RxJavaTimerActivity.class));
                break;
            case R.id.button10:
                startActivity(new Intent(this, RxJavaSortActivity.class));
                break;
            case R.id.button11:
                startActivity(new Intent(this, RxJavaConnetActivity.class));
                break;
            case R.id.button12:
                startActivity(new Intent(this, RxTimestampActivity.class));
                break;
        }
    }
}
