package com.zhfly021.utils;


public class RestResponse<T> {
    private T payload;
    private boolean success;
    private String msg;
    private int code = 0;
    private long timestamp = (long) DateKit.nowUnix();

    public RestResponse() {
    }

    public RestResponse(boolean success) {
        this.success = success;
    }

    public RestResponse(boolean success, T payload) {
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

    public RestResponse<T> peek(Runnable runnable) {
        runnable.run();
        return this;
    }

    public RestResponse<T> success(boolean success) {
        this.success = success;
        return this;
    }

    public RestResponse<T> payload(T payload) {
        this.payload = payload;
        return this;
    }

    public RestResponse<T> code(int code) {
        this.code = code;
        return this;
    }

    public RestResponse<T> message(String msg) {
        this.msg = msg;
        return this;
    }

    public static <T> RestResponse<T> ok() {
        return (new RestResponse()).success(true);
    }

    public static <T> RestResponse<T> ok(T payload) {
        return (new RestResponse()).success(true).payload(payload);
    }

    public static <T> RestResponse ok(T payload, int code) {
        return (new RestResponse()).success(true).payload(payload).code(code);
    }

    public static <T> RestResponse<T> fail() {
        return (new RestResponse()).success(false);
    }

    public static <T> RestResponse<T> fail(String message) {
        return (new RestResponse()).success(false).message(message);
    }

    public static <T> RestResponse fail(int code, String message) {
        return (new RestResponse()).success(false).message(message).code(code);
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
        } else if (!(o instanceof RestResponse)) {
            return false;
        } else {
            RestResponse<?> other = (RestResponse) o;
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
        return other instanceof RestResponse;
    }

    @Override
    public String toString() {
        return "RestResponse(payload=" + this.getPayload() + ", success=" + this.isSuccess() + ", msg=" + this.getMsg() + ", code=" + this.getCode() + ", timestamp=" + this.getTimestamp() + ")";
    }

}