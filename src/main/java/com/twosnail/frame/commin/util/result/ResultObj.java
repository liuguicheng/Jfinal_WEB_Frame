package com.twosnail.frame.commin.util.result;

import com.alibaba.fastjson.JSONObject;

/**   
 * @Title: ResultObj.java
 * @Description: 返回数据
 */
public class ResultObj {

    //成功
    public static int SUCCESS = 1;
    //失败
    public static int FAIL = -1;
    //版本不可用
    public static int UN_USE = -2;
    //状态
    private int status;
    //反馈信息
    private String msg;
    //反馈数据
    private Object data;

    public ResultObj() {}

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public ResultObj(int status, String msg, Object data) {
        this.status = status;
        this.msg = msg;
        this.data = data;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
    
    @Override
    public String toString() {
        return JSONObject.toJSONString(this) ;
    }
    public Object toJason() {
    	return JSONObject.toJSON(this) ;
    }
}
