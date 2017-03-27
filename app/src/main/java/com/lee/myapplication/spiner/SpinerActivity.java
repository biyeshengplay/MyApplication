package com.lee.myapplication.spiner;

import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import com.lee.myapplication.R;

/**
 * Created by alvinlee on 16/7/15.
 */
public class SpinerActivity extends AppCompatActivity {
    // 下拉框依附组件
    private RelativeLayout parent;
    // 下拉框依附组件宽度，也将作为下拉框的宽度
    private int pwidth;
    // 文本框
    private EditText et, et1;
    // 下拉箭头图片组件
    private ImageView image, image1;
    // 展示所有下拉选项的ListView
    private ListView listView = null;
    private String str1[] = {"北京", "上海", "广州"};
    private String str2[] = {"多媒体播放", "HDMI 1950*780", "北京高视"};
    // PopupWindow对象
    private PopupWindow selectPopupWindow = null;
    private OptionsAdapter optionsAdapter = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spiner);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        initWedget();
    }

    /**
     * 初始化界面控件
     */
    private void initWedget() {
        // 初始化界面组件
        parent = (RelativeLayout) findViewById(R.id.parent);
        et = (EditText) findViewById(R.id.edittext);
        et1 = (EditText) findViewById(R.id.edittext1);
        et.setFocusable(false);// 禁止编辑框被编辑
        et1.setFocusable(false);// 禁止编辑框被编辑
        image = (ImageView) findViewById(R.id.btn_select);
        image1 = (ImageView) findViewById(R.id.btn_select1);
        // 获取下拉框依附的组件宽度
        pwidth = parent.getWidth();
        // 设置点击下拉箭头图片事件，点击弹出PopupWindow浮动下拉框
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initPopuWindow(et, str1);
                popupWindwShowing(et);
            }
        });
        image1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 初始化PopupWindow
                initPopuWindow(et1, str2);
                // 显示PopupWindow窗口
                popupWindwShowing(et1);
            }
        });
    }

    /**
     * 初始化PopupWindow
     */
    private void initPopuWindow(final EditText et, final String str[]) {
        View loginwindow = (View) this.getLayoutInflater().inflate(
                R.layout.options, null);
        listView = (ListView) loginwindow.findViewById(R.id.list);
        // 设置自定义Adapter
        optionsAdapter = new OptionsAdapter(this, str);
        listView.setAdapter(optionsAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View v, int position,
                                    long arg3) {
                et.setText(str[position]);
                selectPopupWindow.dismiss();
            }
        });
        selectPopupWindow = new PopupWindow(loginwindow, pwidth,
                LinearLayout.LayoutParams.WRAP_CONTENT, true);
        selectPopupWindow.setOutsideTouchable(true);
        //当点击屏幕其他部分及Back键时PopupWindow消失
        selectPopupWindow.setBackgroundDrawable(new BitmapDrawable());
    }

    public void popupWindwShowing(View view) {
        selectPopupWindow.showAsDropDown(view, 0, -1);
    }
}

