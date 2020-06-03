package FILTER;

import com.alibaba.fastjson.JSONObject;


public class XssJSONObject extends JSONObject {
    private JSONObject obj;

    public XssJSONObject(JSONObject obj) {
        this.obj = obj;
    }

    @Override
    public Object get(Object key) {
        Object obj = this.obj.get(key);
        if (obj == null) {
            return null;
        }
        String value = XssFunction.xssEncode(obj.toString());
        if (value.isEmpty() || value == null) {
            return null;
        }
        return (Object) value;
    }

    ;
}
