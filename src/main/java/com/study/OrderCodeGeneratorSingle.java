package com.study;

/**
 * @author Hash
 * @since 2021/1/2
 */
public class OrderCodeGeneratorSingle {

    static class InstanceHolder {
        private static OrderCodeGenerator instance = new OrderCodeGenerator();
    }

    public static OrderCodeGenerator getInstance() {
        return InstanceHolder.instance;
    }
}
