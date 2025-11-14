package cn.eova.core.api;

/**
 * 接口结果返回
 *
 * @author Jieven
 */
public class ApiResponse {

    public static ApiResponse me = new ApiResponse();

    private int code;       // 状态码(0=成功, 其他=错误)
    private String msg;     // 请求失败返回的错误信息
    private Object data;    // 请求成功返回数据

    public static ApiResponse NO(int code, String msg) {
        ApiResponse api = new ApiResponse();
        api.setCode(code);
        api.setMsg(msg);
        return api;
    }

    public static ApiResponse NO(String msg) {
        return NO(500, msg);
    }

    public static ApiResponse OK(Object data) {
        ApiResponse api = new ApiResponse();
        // 微信API简约风格
        api.setCode(0);
        api.setMsg("ok");
        api.setData(data);
        return api;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
