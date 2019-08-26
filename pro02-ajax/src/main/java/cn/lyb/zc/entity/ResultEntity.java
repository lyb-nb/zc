package cn.lyb.zc.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author lyb
 * @since 2019/8/9 9:52
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResultEntity<T> {

    public static final String SUCCESS = "SUCCESS";
    public static final String FAILED = "FAILID";
    public static final String NO_MESSAGE = "NO_MESSAGE";
    public static final String NO_DATA = "NO_DATA";

    private String result;
    private String message;
    private T data;

    public static ResultEntity<String> success() {
        return new ResultEntity<>(SUCCESS, NO_MESSAGE, NO_DATA);
    }

    public static <E> ResultEntity<E> success(E data) {
        return new ResultEntity<>(SUCCESS, NO_MESSAGE, data);
    }

    public static <E> ResultEntity<E> fail(String message, E data) {
        return new ResultEntity<>(FAILED, message, data);
    }
}
