package com.study.zkclient;

import java.util.Properties;

/**
 * 配置文件读取器
 * ConfigureReader
 *
 * @author Hash
 * @since 2021/1/3
 */
public interface ConfigureReader {
    /**
     * 读取配置文件
     * @param fileName 配置文件名称
     * @return 如果存在文件配置，则返回Properties对象，不存在返回null
     */
    Properties loadCnfFile(String fileName);
    /**
     * 监听配置文件变化，此操作只需要调用一次。
     * @param fileName
     * @param changeHandler
     */
    void watchCnfFile(String fileName, ChangeHandler changeHandler);

    /**
     * 配置文件变化处理器
     * ChangeHandler
     */
    interface ChangeHandler {
        /**
         * 配置文件发生变化后给一个完整的属性对象
         * @param newProp
         */
        void itemChange(Properties newProp);
    }
}
