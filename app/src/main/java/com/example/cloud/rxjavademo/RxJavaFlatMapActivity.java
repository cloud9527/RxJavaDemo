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

public class RxJavaFlatMapActivity extends AppCompatActivity {

    @BindView(R.id.edit1)
    TextView edit1;
    @BindView(R.id.button)
    Button button;
    @BindView(R.id.text1)
    TextView text1;
    @BindView(R.id.linearlayout)
    LinearLayout linearlayout;
    private SchoolClass[] mSchoolClasses = new SchoolClass[3];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_rx_java);
        ButterKnife.bind(this);
        edit1.setText("打印一个学校全部班级的学生");

        initData();
    }

    private void initData() {
        Student[] student = new Student[5];
        for (int i = 0; i < 5; i++) {
            Student s = new Student("王大" + i, "17");
            student[i] = s;
        }
        mSchoolClasses[0] = new SchoolClass(student);

        Student[] student2 = new Student[5];
        for (int i = 0; i < 5; i++) {
            Student s = new Student("王二" + i, "37");
            student2[i] = s;
        }
        mSchoolClasses[1] = new SchoolClass(student2);
        Student[] student3 = new Student[5];
        for (int i = 0; i < 5; i++) {
            Student s = new Student("王三" + i, "77");
            student3[i] = s;
        }
        mSchoolClasses[2] = new SchoolClass(student3);
    }

    @OnClick(R.id.button)
    public void onViewClicked() {
        if (text1.getText().toString().length() > 0) {
            text1.setText("");
        }
        init();
    }
    public SchoolClass[] getSchoolClass(){
        return  mSchoolClasses;
    }

    private void init() {
        Observable.from(getSchoolClass())
                .flatMap(new Func1<SchoolClass, Observable<Student>>() {
                    @Override
                    public Observable<Student> call(SchoolClass schoolClass) {
                        return Observable.from(schoolClass.getStudents());
                    }
                })
                .subscribe(new Action1<Student>() {
                    @Override
                    public void call(Student student) {
                        text1.append("打印单个学生信息：\n");
                        text1.append("name:" + student.name + "   age:" + student.age + "\n");
                    }
                });

    }
}


class Student {
    String name;
    String age;

    public Student(String name, String age) {
        this.name = name;
        this.age = age;
    }
}

class SchoolClass {
    Student[] stud;

    public SchoolClass(Student[] s) {
        this.stud = s;
    }

    public Student[] getStudents() {
        return stud;
    }
}
