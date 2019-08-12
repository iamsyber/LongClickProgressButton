# LongClickProgressButton
安卓仿咕咚长按结束运动按钮，带进度条
**使用方式**
导入ProgressButton.java，新建attr文件，复制attr文件的属性，调用xml，使用的时候为按钮添加监听执行结束的回调。
```java
        myProgressButton.setListener(new ProgressButton.ProgressButtonFinishCallback() {
            @Override
            public void onFinish() {
                //todo  your  business
            }
        });
```

#### xml使用文件如下
        <com.cpic.loverun.widget.ProgressButton
            android:id="@+id/myProgressButton"
            android:layout_centerInParent="true"
            android:layout_width="150dp"
            my:strokeWidth="10dp"
            my:bigRingColor="@color/color_default_shadow"
            my:circleTextColor="@color/white"
            my:circleColor="@color/text_color_green"
            my:ringColor="@color/color_red"
            android:layout_height="150dp"
            android:clickable="true">
        </com.cpic.loverun.widget.ProgressButton>

