package com.lee.myapplication.storage;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by xiaohong.wang@dmall.com on 2017/1/5.
 * description:
 */

public class SpStorage {

    private static final String TAG = SpStorage.class.getSimpleName();
    private static final String storageName = "storage";
    private static final String contextKey = "storageContextKey";

    public static final String COME_FORWARD_UNDATE_KEY = "updatekey";
    public static final String COME_FORWARD_UNZIP_KEY = "forward_unzip";
    private static Context mContext;

    private static class StorageHolder {
        public static SpStorage instance = new SpStorage();
    }

    public static SpStorage getInstance(Context context) {
        mContext = context;
        return SpStorage.StorageHolder.instance;
    }

    public boolean set(String key, String data) {
        Log.d(TAG, "set key: " + key + "value: " + data);

        SharedPreferences sharedPreferences = mContext.getSharedPreferences(storageName, 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        JSONObject finalJsonObject = null;
        JSONArray finalJsonArray = null;
        String finalValue = "";

        //对入参进行分段
        String[] keyList = key.split("\\.");
        boolean isFirstKeyJsonArray = isJsonArray(keyList[0]);

        //如果有多段，或者第一段为jsonarray
        if (keyList.length > 1 || isFirstKeyJsonArray) {
            JSONObject tempJsonObject = null;
            JSONObject cacheJsonObject = null;
            JSONArray tempJsonArray = null;
            String value;

            //如果是jsonarray用array的name取值
            if (isFirstKeyJsonArray) {
                value = sharedPreferences.getString(getJsonArrayName(keyList[0]), null);
            } else {
                value = sharedPreferences.getString(keyList[0], null);
            }

            //判断第一段key和以第一段key存储的旧数据格式是否一致
            boolean isValidJsonObjectOK = !isFirstKeyJsonArray && isValidJsonObject(value);
            boolean isValidJsonArrayOK = isFirstKeyJsonArray && isValidJsonArray(value);

            if (isValidJsonObjectOK || isValidJsonArrayOK) {
                if (isValidJsonObjectOK) {
                    tempJsonObject = finalJsonObject = getJSONObjectFromString(value);
                } else if (isValidJsonArrayOK) {
                    tempJsonArray = finalJsonArray = getJSONArrayFromString(value);
                    tempJsonObject = getJSONObjectFromJSONArray(tempJsonArray, getJsonArrayIndex(keyList[0]));
                    if (tempJsonObject == null) {
                        if (getJsonArrayIndex(keyList[0]) == tempJsonArray.length()) {
                            //在存储的jsonarray内取不到该index的jsonobject，如果该index刚好等于length，新建一个存储
                            tempJsonObject = new JSONObject();
                            try {
                                tempJsonArray.put(getJsonArrayIndex(keyList[0]), tempJsonObject);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            if (creat(tempJsonObject, keyList, 1, data)) {
                                if (finalJsonArray != null) {
                                    finalValue = finalJsonArray.toString();
                                    editor.putString(isFirstKeyJsonArray ? getJsonArrayName(keyList[0]) : keyList[0], finalValue);
                                    editor.commit();
                                    return true;
                                }
                            } else {
                                return false;
                            }
                        }
                    }
                }

                for (int i = 1; i < keyList.length; i++) {
                    if (i == keyList.length - 1) {
                        if (isJsonArray(keyList[i])) {
                            tempJsonArray = getJSONArrayFromJSONObject(getJsonArrayName(keyList[i]), tempJsonObject);
                            try {
                                if (isValidJsonObject(data)) {
                                    tempJsonArray.put(getJsonArrayIndex(keyList[i]), getJSONObjectFromString(data));
                                } else if (isValidJsonArray(data)) {
                                    tempJsonArray.put(getJsonArrayIndex(keyList[i]), getJSONArrayFromString(data));
                                } else {
                                    tempJsonArray.put(getJsonArrayIndex(keyList[i]), data);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else {
                            try {
                                if (isValidJsonObject(data)) {
                                    tempJsonObject.put(keyList[i], getJSONObjectFromString(data));
                                } else if (isValidJsonArray(data)) {
                                    tempJsonObject.put(keyList[i], getJSONArrayFromString(data));
                                } else {
                                    tempJsonObject.put(keyList[i], data);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    } else {
                        if (isJsonArray(keyList[i])) {
                            tempJsonArray = getJSONArrayFromJSONObject(getJsonArrayName(keyList[i]), tempJsonObject);
                            if (tempJsonArray == null) {
                                tempJsonArray = new JSONArray();
                                try {
                                    tempJsonObject.put(getJsonArrayName(keyList[i]), tempJsonArray);
                                    tempJsonObject = new JSONObject();
                                    tempJsonArray.put(0, tempJsonObject);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                if (creat(tempJsonObject, keyList, i + 1, data)) {
                                    break;
                                } else {
                                    return false;
                                }
                            }

                            tempJsonObject = getJSONObjectFromJSONArray(tempJsonArray, getJsonArrayIndex(keyList[i]));

                            if (tempJsonObject == null) {
                                if (getJsonArrayIndex(keyList[i]) == tempJsonArray.length()) {
                                    //在存储的jsonarray内取不到该index的jsonobject，如果该index刚好等于length，新建一个存储
                                    tempJsonObject = new JSONObject();
                                    try {
                                        tempJsonArray.put(getJsonArrayIndex(keyList[i]), tempJsonObject);
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                    if (creat(tempJsonObject, keyList, i + 1, data)) {
                                        break;
                                    } else {
                                        return false;
                                    }
                                }
                            }
                        } else {
                            cacheJsonObject = getJSONObjectFromJSONObject(keyList[i], tempJsonObject);
                            if (cacheJsonObject == null) {
                                cacheJsonObject = new JSONObject();
                                try {
                                    tempJsonObject.put(keyList[i], cacheJsonObject);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                if (creat(tempJsonObject, keyList, i + 1, data)) {
                                    break;
                                } else {
                                    return false;
                                }
                            } else {
                                tempJsonObject = cacheJsonObject;
                            }
                        }
                    }
                }
            } else {
                //新旧数据最外层就不匹配，需要新建并覆盖存储
                if (isFirstKeyJsonArray) {
                    //第一段key为jsonarray,旧数据为非法的jsonarray，不匹配，需要新建并覆盖存储
                    if (getJsonArrayIndex(keyList[0]) == 0) {
                        finalJsonArray = new JSONArray();
                        tempJsonObject = new JSONObject();
                        try {
                            finalJsonArray.put(0, tempJsonObject);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    } else {
                        //第一段key为jsonarray,且index大于0,而旧数据为非法的jsonarray，异常状态，不能存储
                        return false;
                    }
                } else {
                    //第一段key为jsonobject,旧数据为非法的jsonobject，不匹配，需要新建并覆盖存储
                    finalJsonObject = new JSONObject();
                    tempJsonObject = finalJsonObject;
                }

                //新建不匹配的json层次
                if (!creat(tempJsonObject, keyList, 1, data)) {
                    return false;
                }
            }
            if (finalJsonObject != null) {
                finalValue = finalJsonObject.toString();
            } else if (finalJsonArray != null) {
                finalValue = finalJsonArray.toString();
            }
        } else {
            //只有一段，且这一段不是jsonarray
            if (isValidJsonObject(data)) {
                //data是合法的jsonobject，转成jsonString存储
                finalJsonObject = getJSONObjectFromString(data);
                finalValue = finalJsonObject.toString();
            } else {
                //data是非法的jsonobject，直接存储
                finalValue = data;
            }
        }

        editor.putString(isFirstKeyJsonArray ? getJsonArrayName(keyList[0]) : keyList[0], finalValue);
        editor.commit();
        return true;
    }

    public boolean set(String key, String data, boolean nonono) {
        Log.d(TAG, "存储 " + key + " = " + data);
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(storageName, 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Log.d(TAG, "set value: " + key + " = " + data);

        JSONObject finalJsonObject = null;
        JSONArray finalJsonArray = null;
        String finalValue = "";

        //对入参进行分段
        String[] keyList = key.split("\\.");
        boolean isFirstKeyJsonArray = isJsonArray(keyList[0]);

        //如果有多段，或者第一段为jsonarray
        if (keyList.length > 1 || isFirstKeyJsonArray) {
            JSONObject tempJsonObject = null;
            JSONArray tempJsonArray = null;
            JSONObject cacheJsonObject = null;
            String value;

            //如果是jsonarray用array的name取值
            if (isFirstKeyJsonArray) {
                value = sharedPreferences.getString(getJsonArrayName(keyList[0]), null);
            } else {
                value = sharedPreferences.getString(keyList[0], null);
            }

            //判断第一段key和以第一段key存储的旧数据格式是否一致
            boolean isValidJsonObjectOK = !isFirstKeyJsonArray && isValidJsonObject(value);
            boolean isValidJsonArrayOK = isFirstKeyJsonArray && isValidJsonArray(value);

            //判断将要存储的数据的格式
            boolean isDataValidJsonObject = isValidJsonObject(data);
            boolean isDataValidJsonArray = isValidJsonArray(data);

            if (isValidJsonObjectOK || isValidJsonArrayOK) {
                if (isValidJsonObjectOK) {
                    tempJsonObject = finalJsonObject = getJSONObjectFromString(value);
                } else if (isValidJsonArrayOK) {
                    tempJsonArray = finalJsonArray = getJSONArrayFromString(value);
                    tempJsonObject = getJSONObjectFromJSONArray(tempJsonArray, getJsonArrayIndex(keyList[0]));
                    if (tempJsonObject == null) {
                        if (getJsonArrayIndex(keyList[0]) == tempJsonArray.length()) {
                            //在存储的jsonarray内取不到该index的jsonobject，如果该index刚好等于length，新建一个存储
                            tempJsonObject = new JSONObject();
                            try {
                                tempJsonArray.put(getJsonArrayIndex(keyList[0]), tempJsonObject);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            if (creat(tempJsonObject, keyList, 1, data)) {
                                if (finalJsonArray != null) {
                                    finalValue = finalJsonArray.toString();
                                    editor.putString(isFirstKeyJsonArray ? getJsonArrayName(keyList[0]) : keyList[0], finalValue);
                                    editor.commit();
                                    return true;
                                }
                            } else {
                                return false;
                            }
                        }
                    }
                }

                for (int i = 1; i < keyList.length; i++) {
                    if (i == keyList.length - 1) {
                        if (isJsonArray(keyList[i])) {
                            tempJsonArray = getJSONArrayFromJSONObject(getJsonArrayName(keyList[i]), tempJsonObject);
                            try {
                                if (isDataValidJsonObject) {
                                    tempJsonArray.put(getJsonArrayIndex(keyList[i]), getJSONObjectFromString(data));
                                } else if (isDataValidJsonArray) {
                                    tempJsonArray.put(getJsonArrayIndex(keyList[i]), getJSONArrayFromString(data));
                                } else {
                                    tempJsonArray.put(getJsonArrayIndex(keyList[i]), data);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else {
                            try {
                                if (isDataValidJsonObject) {
                                    tempJsonObject.put(keyList[i], getJSONObjectFromString(data));
                                } else if (isDataValidJsonArray) {
                                    tempJsonObject.put(keyList[i], getJSONArrayFromString(data));
                                } else {
                                    tempJsonObject.put(keyList[i], data);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    } else {
                        if (isJsonArray(keyList[i])) {
                            tempJsonArray = getJSONArrayFromJSONObject(getJsonArrayName(keyList[i]), tempJsonObject);
                            tempJsonObject = getJSONObjectFromJSONArray(tempJsonArray, getJsonArrayIndex(keyList[i]));
                        } else {
                            tempJsonObject = getJSONObjectFromJSONObject(keyList[i], tempJsonObject);
                        }
                    }
                }
                if (finalJsonObject != null) {
                    finalValue = finalJsonObject.toString();
                } else if (finalJsonArray != null) {
                    finalValue = finalJsonArray.toString();
                }
            } else {
                //1.key为jsonobject,旧数据为非法的jsonobject，不匹配，不能存储
                //2.key为jsonarray,旧数据为非法的jsonarray，不匹配，不能存储
                return false;
            }
        } else {
            //只有一段，且这一段不是jsonarray
            if (isValidJsonObject(data)) {
                //data是合法的jsonobject，转成jsonString存储
                finalJsonObject = getJSONObjectFromString(data);
                finalValue = finalJsonObject.toString();
            } else {
                //data是非法的jsonobject，直接存储
                finalValue = data;
            }
        }

        editor.putString(isFirstKeyJsonArray ? getJsonArrayName(keyList[0]) : keyList[0], finalValue);
        editor.commit();
        return true;
    }

    //新建不匹配的json层次
    private boolean creat (JSONObject tempJsonObject, String [] keyList, int index, String data) {

        JSONArray tempJsonArray;

        for (int i = index; i < keyList.length; i ++) {
            if ( i == keyList.length - 1) {
                if (isJsonArray(keyList[i])) {
                    if (getJsonArrayIndex(keyList[i]) == 0) {
                        tempJsonArray = new JSONArray();
                        try {
                            if (isValidJsonObject(data)) {
                                tempJsonArray.put(getJsonArrayIndex(keyList[i]), getJSONObjectFromString(data));
                            } else if (isValidJsonArray(data)) {
                                tempJsonArray.put(getJsonArrayIndex(keyList[i]), getJSONArrayFromString(data));
                            } else {
                                tempJsonArray.put(getJsonArrayIndex(keyList[i]), data);
                            }
                            tempJsonObject.put(getJsonArrayName(keyList[i]), tempJsonArray);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    } else {
                        //jsonarray的index大于0，异常状态，不能存储
                        return false;
                    }
                } else {
                    try {
                        if (isValidJsonObject(data)) {
                            tempJsonObject.put(keyList[i], getJSONObjectFromString(data));
                        } else if (isValidJsonArray(data)) {
                            tempJsonObject.put(keyList[i], getJSONArrayFromString(data));
                        } else {
                            tempJsonObject.put(keyList[i], data);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            } else {
                if (isJsonArray(keyList[i])) {
                    if (getJsonArrayIndex(keyList[i]) == 0) {
                        tempJsonArray = new JSONArray();
                        try {
                            tempJsonObject.put(getJsonArrayName(keyList[i]), tempJsonArray);
                            tempJsonObject = new JSONObject();
                            tempJsonArray.put(0, tempJsonObject);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    } else {
                        //jsonarray的index大于0，异常状态，不能存储
                        return false;
                    }
                } else {
                    try {
                        tempJsonObject.put(keyList[i], new JSONObject());
                        tempJsonObject = tempJsonObject.getJSONObject(keyList[i]);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return true;
    }

    public String get(String key) {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(storageName, 0);
        String value;
        JSONObject tempJsonObject = null;
        JSONArray tempJsonArray = null;

        //对入参进行分段
        String[] keyList = key.split("\\.");
        boolean isJsonArray = isJsonArray(keyList[0]);

        //如果是jsonarray用array的name取值
        if (isJsonArray) {
            value = sharedPreferences.getString(getJsonArrayName(keyList[0]), null);
        } else {
            value = sharedPreferences.getString(keyList[0], null);
            //如果只有一段，且这一段不是array，直接返回
            if (keyList.length <= 1) {
                return value;
            }
        }

        if (!isJsonArray && isValidJsonObject(value)) {
            //判断取值是否为合法jsonobject格式
            tempJsonObject = getJSONObjectFromString(value);
        } else if (isJsonArray && isValidJsonArray(value)) {
            //判断取值是否为合法jsonarray格式
            tempJsonArray = getJSONArrayFromString(value);
            try {
                value = tempJsonArray.getString(getJsonArrayIndex(keyList[0]));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            tempJsonObject = getJSONObjectFromJSONArray(tempJsonArray, getJsonArrayIndex(keyList[0]));
        } else {
            //如果都解析失败，返回
            return value;
        }

        for (int i = 1; i < keyList.length; i++) {
            if (isJsonArray(keyList[i])) {
                tempJsonArray = getJSONArrayFromJSONObject(getJsonArrayName(keyList[i]), tempJsonObject);
                try {
                    value = tempJsonArray.getString(getJsonArrayIndex(keyList[i]));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                tempJsonObject = getJSONObjectFromJSONArray(tempJsonArray, getJsonArrayIndex(keyList[i]));
            } else {
                try {
                    value = tempJsonObject.getString(keyList[i]);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                tempJsonObject = getJSONObjectFromJSONObject(keyList[i], tempJsonObject);
            }

        }

        return value;
    }

    private boolean isValidJsonObject(String str) {
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        try {
            new JSONObject(str);
            return true;
        } catch (JSONException e) {
            e.printStackTrace();
            return false;
        }
    }

    private boolean isValidJsonArray(String str) {
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        try {
            new JSONArray(str);
            return true;
        } catch (JSONException e) {
            e.printStackTrace();
            return false;
        }
    }

    private String getJsonArrayName(String str) {
        String namr = str.substring(0, str.length() - 3);
        return namr;
    }

    private int getJsonArrayIndex(String str) {
        int index = Integer.valueOf(String.valueOf(str.charAt(str.length() - 2)));
        return index;
    }

    private boolean isJsonArray(String str) {
        return str.contains("[") && str.endsWith("]") ? true : false;
    }

    //从json字符串中解析jsonobject
    private JSONObject getJSONObjectFromString(String json) {
        JSONObject target = null;
        try {
            target = new JSONObject(json);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return target;
    }

    //从json字符串中解析jsonobject
    private JSONArray getJSONArrayFromString(String json) {
        JSONArray target = null;
        try {
            target = new JSONArray(json);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return target;
    }

    //从jsonobject中获取jsonobject
    private JSONObject getJSONObjectFromJSONObject(String key, JSONObject parent) {
        JSONObject target = null;
        try {
            target = parent.getJSONObject(key);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return target;
    }

    //从jsonarray中获取jsonobject
    private JSONObject getJSONObjectFromJSONArray(JSONArray parent, int index) {
        JSONObject target = null;
        try {
            target = parent.getJSONObject(index);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return target;
    }

    //从jsonobject中获取jsonarray
    private JSONArray getJSONArrayFromJSONObject(String key, JSONObject parent) {
        JSONArray target = null;
        try {
            target = parent.getJSONArray(key);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return target;
    }

    //从jsonarray中获取jsonarray
    private JSONArray getJSONArrayFromJSONArray(JSONArray parent, int index) {
        JSONArray target = null;
        try {
            target = parent.getJSONArray(index);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return target;
    }
}
