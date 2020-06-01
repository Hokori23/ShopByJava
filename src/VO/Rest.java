package VO;

import java.util.List;

import com.alibaba.fastjson.JSONObject;

public class Rest extends JSONObject {

    public Rest() {}

    public void setErrcode(int errcode) {
        this.put("errocode", errcode);
    }

    public void setDataObject(Object obj) {
        this.put("data", obj);
    }

    public void setDataArray(List obj) {
        this.put("data", obj);
    }

    public void setMessage(String message){
        this.put("message", message);
    }

    public void toRestArray(int errcode, List obj, String message) {
        this.setErrcode(errcode);
        this.setDataArray(obj);
        this.setMessage(message);
    }

    public void toRestObject(int errcode, User obj, String message) {
        this.setErrcode(errcode);
        this.setDataObject(obj);
        this.setMessage(message);
    }

    public void toRestObject(int errcode, Product obj, String message) {
        this.setErrcode(errcode);
        this.setDataObject(obj);
        this.setMessage(message);
    }

    public void toRestObject(int errcode, ProductLog obj, String message) {
        this.setErrcode(errcode);
        this.setDataObject(obj);
        this.setMessage(message);
    }

    public void toRestMessage(int errcode,String message){
        this.setErrcode(errcode);
        this.setMessage(message);
    }
}
