package com.lee.myapplication.groupcount;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lee.myapplication.R;

/**
 * Created by alvinlee on 2017/2/20.
 */

public class GroupCountActivity extends AppCompatActivity {

    TextView mTextView1;
    TextView mTextView2;
    LinearLayout mRootView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_count);
        initView();
    }

    private void initView() {
        mRootView = (LinearLayout) findViewById(R.id.root_view);
        mTextView1 = (TextView) findViewById(R.id.count_tv1);
        mTextView1.setText("深度为" + getViewHeight(mRootView) + " view总数为：" + getViewCount(mRootView));
        mTextView2 = (TextView) findViewById(R.id.count_tv2);
        mTextView2.setText("深度为" + getGroupDepth(mRootView) + " view总数为：" + getGroupCount(mRootView));
    }

    int height = 0;
    public int getViewHeight(View group) {
        boolean hasChild = false;
        if (group instanceof ViewGroup && ((ViewGroup)group).getChildCount() > 0) {
            int size = ((ViewGroup)group).getChildCount();
            hasChild = true;
            for (int i = 0; i < size; i ++) {
                getViewHeight(((ViewGroup)group).getChildAt(i));
            }
        }
        if (hasChild) {
            height ++;
        }
        return height;
    }

    public int getViewCount(View group) {
        int count = 0;
        if (group instanceof ViewGroup) {
            int size = ((ViewGroup)group).getChildCount();
            for(int i = 0; i < size; i ++) {
                count += getViewCount(((ViewGroup)group).getChildAt(i));
            }
        } else {
            count = 1;
        }
        return count;
    }

    //获取深度
    public int getGroupDepth(View group) {
        if (group instanceof ViewGroup) {
            int curMaxDepth = 0;
            int tempDepth;
            for (int i = 0; i < ((ViewGroup) group).getChildCount(); i++) {
                tempDepth = getGroupDepth(((ViewGroup) group).getChildAt(i));
                if (tempDepth > curMaxDepth) {
                    curMaxDepth = tempDepth;
                }
            }
            return curMaxDepth + 1;
        } else {
            return 1;
        }
    }

    //获取子view总数
    public int getGroupCount(View group) {
        int count = 0;
        if (group instanceof ViewGroup) {
            count++;
            for (int i = 0; i < ((ViewGroup) group).getChildCount(); i++) {
                count += getGroupCount(((ViewGroup) group).getChildAt(i));
            }
        } else {
            count++;
        }
        return count;
    }
}
