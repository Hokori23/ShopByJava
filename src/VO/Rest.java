package VO;

import java.util.List;
import FILTER.XssJSONObject;
import com.alibaba.fastjson.JSONObject;

public class Rest {
    public static XssJSONObject toRest(int errcode, List<Object> obj, String message) {
        XssJSONObject data = new XssJSONObject(new JSONObject());
        data.put("errcode",errcode);
        data.put("data",obj);
        data.put("message",message);
        return data;
    }
}
