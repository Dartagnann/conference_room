package main.com.em.tool;



import main.com.em.domain.Result;




/**
 * 对于结果集（Result）的工具类
 * Created by liuxiaoming on 2017/11/7.
 */
public class ResultUtil {
    public static Result success(Object object){
        Result result = new Result();
        result.setCode(0);
        result.setMsg("成功");
        result.setData(object);
        return result;
    }

    public static Result success(){
        return success(null);
    }

    public static Result error(Integer code,String msg){
        Result result = new Result();
        result.setCode(code);
        result.setMsg(msg);
        return result;
    }
    public static Result error() {
        Result result = new Result();
        result.setCode(404);
        result.setMsg("失败");
        return result;

    }

}