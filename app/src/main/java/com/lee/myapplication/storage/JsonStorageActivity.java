package com.lee.myapplication.storage;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.lee.myapplication.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by alvinlee on 2017/3/24.
 */

public class JsonStorageActivity extends AppCompatActivity {

    Button mSet, mGet;
    TextView mResult;
    JSONObject jsonObject;
    JSONArray jsonObject2;
    JSONObject jsonObject3;

    //private static final String JSON = "{\"user\":{\"name\":\"lifa\",\"age\":18,\"teacher\":[{\"name\":\"renbin\",\"age\":20},{\"name\":\"binbin\",\"age\":25}]}}";
    //private static final String JSON = "[{\"name\":\"lifa1\"},{\"name\":\"lifa2\"},{\"name\":\"lifa3\"}]";
    private static final String JSON = "{\"user\":{\"name\":\"lifa\",\"age\":18,\"teacher\":[{\"name\":\"renbin\",\"age\":20},{\"name\":\"zhangtao\",\"age\":25}]}}";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_json_storage);
        initView();
    }

    private void initView() {
        mSet = (Button) findViewById(R.id.storage_set);
        mGet = (Button) findViewById(R.id.storage_get);
        mResult = (TextView) findViewById(R.id.result);
        mSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //SpStorage.getInstance(JsonStorageActivity.this).set("info", JSON);
                SpStorage.getInstance(JsonStorageActivity.this).set("storage1.teacher1[3].teacher3", "lifa");
                //toJson();
            }
        });
        mGet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mResult.setText(SpStorage.getInstance(JsonStorageActivity.this).get("storage1.teacher1[3].teacher3"));
                //getJson();
            }
        });
    }

    private void toJson() {
        try {
            jsonObject = new JSONObject("{\"user\":[1,{\"name\":\"lifa\",\"age\":10,\"teacher\":\"wuhao\",\"abc\":\"wuhao\",\"def\":\"wuhao\"},{\"name\":\"lifa\",\"age\":10,\"teacher\":\"wuhao\"}]}");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void getJson() {
        String value = "";
        try {
            jsonObject2 = jsonObject.getJSONArray("user");
            //JSONObject newJson = new JSONObject("{\"name\":\"binbin\",\"age\":25}");
            jsonObject3 = jsonObject2.getJSONObject(0);
            value = jsonObject3.getString("abc");

        } catch (JSONException e) {
            e.printStackTrace();
        }
        //value = jsonObject2.toString();
        mResult.setText(value);
    }
}
