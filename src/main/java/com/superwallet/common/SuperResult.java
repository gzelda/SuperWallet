package com.superwallet.common;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.Serializable;
import java.util.List;

public class SuperResult implements Serializable {

    // 定义jackson对象
    private static final ObjectMapper MAPPER = new ObjectMapper();

    // 响应业务代码
    private Integer code;

    // 响应状态
    private Integer status;

    // 响应中的数据
    private Object data;

    //响应中的消息
    private String msg;

    public SuperResult(Integer code, Integer status, String msg, Object data) {
        this.code = code;
        this.status = status;
        this.msg = msg;
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public static SuperResult build(Integer code, Integer status, String msg, Object data) {
        return new SuperResult(code, status, msg, data);
    }

    public static SuperResult build(Integer code, Integer status, Object data) {
        return new SuperResult(code, status, data);
    }

    public static SuperResult ok(Object data) {
        return new SuperResult(data);
    }

    public static SuperResult ok() {
        return new SuperResult(null);
    }

    public static SuperResult ok(String msg) {
        SuperResult result = SuperResult.ok();
        result.setMsg(msg);
        return result;
    }

    public SuperResult() {

    }

    public static SuperResult build(Integer code, Integer status) {
        return new SuperResult(code, status, null);
    }

    public SuperResult(Integer code, Integer status, Object data) {
        this.code = code;
        this.status = status;
        this.data = data;
    }

    public SuperResult(Object data) {
        this.code = 1;
        this.status = 0;
        this.data = data;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    /**
     * 将json结果集转化为SuperResult对象
     *
     * @param jsonData json数据
     * @param clazz    SuperResult中的object类型
     * @return
     */
    public static SuperResult formatToPojo(String jsonData, Class<?> clazz) {
        try {
            if (clazz == null) {
                return MAPPER.readValue(jsonData, SuperResult.class);
            }
            JsonNode jsonNode = MAPPER.readTree(jsonData);
            JsonNode data = jsonNode.get("data");
            Object obj = null;
            if (clazz != null) {
                if (data.isObject()) {
                    obj = MAPPER.readValue(data.traverse(), clazz);
                } else if (data.isTextual()) {
                    obj = MAPPER.readValue(data.asText(), clazz);
                }
            }
            return build(jsonNode.get("code").intValue(), jsonNode.get("status").intValue(), obj);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 没有object对象的转化
     *
     * @param json
     * @return
     */
    public static SuperResult format(String json) {
        try {
            return MAPPER.readValue(json, SuperResult.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Object是集合转化
     *
     * @param jsonData json数据
     * @param clazz    集合中的类型
     * @return
     */
    public static SuperResult formatToList(String jsonData, Class<?> clazz) {
        try {
            JsonNode jsonNode = MAPPER.readTree(jsonData);
            JsonNode data = jsonNode.get("data");
            Object obj = null;
            if (data.isArray() && data.size() > 0) {
                obj = MAPPER.readValue(data.traverse(),
                        MAPPER.getTypeFactory().constructCollectionType(List.class, clazz));
            }
            return build(jsonNode.get("code").intValue(), jsonNode.get("status").intValue(), obj);
        } catch (Exception e) {
            return null;
        }
    }

}
