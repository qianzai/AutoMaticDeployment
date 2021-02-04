package club.gongsir.linux.common.utils;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * 返回信息封装
 * @author gongsir
 * @date 2020/3/30 22:18
 * 编码不要畏惧变化，要拥抱变化
 */
public class Result implements Serializable {
    private Integer code;
    private String msg;
    private Map<String,Object> data = new HashMap<>();
    private static final long serialVersionUID = 1L;

    public Result() {
    }

    public Result(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setData(Map<String, Object> data) {
        this.data = data;
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public Map<String, Object> getData() {
        return data;
    }

    public void putData(String key,Object value) {
        this.data.put(key,value);
    }

    public void removeData(String key) {
        this.data.remove(key);
    }

    @Override
    public String toString() {
        return "Result{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}
