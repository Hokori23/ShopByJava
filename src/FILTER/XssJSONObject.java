package FILTER;

import com.alibaba.fastjson.JSONObject;


public class XssJSONObject extends JSONObject{

    JSONObject orgObject = null;

    public XssJSONObject(JSONObject obj) {
        super(obj);
        this.orgObject = obj;
    }

    @Override
    public Object get(Object key){
        Object obj = super.get(key);
        return (Object)XssFunction.xssEncode(obj.toString());
    };
}
