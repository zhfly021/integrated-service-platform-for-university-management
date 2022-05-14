package com.zhfly021.utils.response;


import com.zhfly021.utils.DateKit;

public class RestResponses<T> {
    private T payload;
    private boolean success;
    private String msg;
    private int code = 0;
    private long timestamp = (long) DateKit.nowUnix();

    public RestResponses() {
    }

    public RestResponses(boolean success) {
        this.success = success;
    }

    public RestResponses(boolean success, T payload) {
        this.success = success;
        this.payload = payload;
    }

    public T getPayload() {
        return this.payload;
    }

    public boolean isSuccess() {
        return this.success;
    }

    public String getMsg() {
        return this.msg;
    }

    public int getCode() {
        return this.code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public long getTimestamp() {
        return this.timestamp;
    }

    public RestResponses<T> peek(Runnable runnable) {
        runnable.run();
        return this;
    }

    public RestResponses<T> success(boolean success) {
        this.success = success;
        return this;
    }

    public RestResponses<T> payload(T payload) {
        this.payload = payload;
        return this;
    }

    public RestResponses<T> code(int code) {
        this.code = code;
        return this;
    }

    public RestResponses<T> message(String msg) {
        this.msg = msg;
        return this;
    }

    public static <T> RestResponses<T> ok() {
        return (new RestResponses()).success(true);
    }

    public static <T> RestResponses<T> ok(T payload) {
        return (new RestResponses()).success(true).payload(payload);
    }

    public static <T> RestResponses ok(T payload, int code) {
        return (new RestResponses()).success(true).payload(payload).code(code);
    }

    public static <T> RestResponses<T> fail() {
        return (new RestResponses()).success(false);
    }

    public static <T> RestResponses<T> fail(String message) {
        return (new RestResponses()).success(false).message(message);
    }

    public static <T> RestResponses fail(int code, String message) {
        return (new RestResponses()).success(false).message(message).code(code);
    }

    public void setPayload(T payload) {
        this.payload = payload;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof RestResponses)) {
            return false;
        } else {
            RestResponses<?> other = (RestResponses) o;
            if (!other.canEqual(this)) {
                return false;
            } else {
                label47:
                {
                    Object this$payload = this.getPayload();
                    Object other$payload = other.getPayload();
                    if (this$payload == null) {
                        if (other$payload == null) {
                            break label47;
                        }
                    } else if (this$payload.equals(other$payload)) {
                        break label47;
                    }

                    return false;
                }

                if (this.isSuccess() != other.isSuccess()) {
                    return false;
                } else {
                    Object this$msg = this.getMsg();
                    Object other$msg = other.getMsg();
                    if (this$msg == null) {
                        if (other$msg != null) {
                            return false;
                        }
                    } else if (!this$msg.equals(other$msg)) {
                        return false;
                    }

                    if (this.getCode() != other.getCode()) {
                        return false;
                    } else if (this.getTimestamp() != other.getTimestamp()) {
                        return false;
                    } else {
                        return true;
                    }
                }
            }
        }
    }

    protected boolean canEqual(Object other) {
        return other instanceof RestResponses;
    }

    @Override
    public String toString() {
        return "RestResponse(payload=" + this.getPayload() + ", success=" + this.isSuccess() + ", msg=" + this.getMsg() + ", code=" + this.getCode() + ", timestamp=" + this.getTimestamp() + ")";
    }

}