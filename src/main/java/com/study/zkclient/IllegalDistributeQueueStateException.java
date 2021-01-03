package com.study.zkclient;

/**
 * @author Hash
 * @since 2021/1/3
 */
public class IllegalDistributeQueueStateException extends RuntimeException {

    private static final long serialVersionUID = 1L;
    private State state = State.other;

    public IllegalDistributeQueueStateException() {
        super();
    }
    public IllegalDistributeQueueStateException(String msg) {
        super(msg);
    }
    public IllegalDistributeQueueStateException(State illegalState) {
        super();
        state = illegalState;
    }
    public IllegalDistributeQueueStateException(String msg, Throwable cause) {
        super(msg, cause);
    }
    public State getState() {return state;};

    public enum State{
        other,
        empty,
        full;
    }
}
