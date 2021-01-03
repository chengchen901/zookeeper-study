package com.study;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 订单编号生成类
 *
 * @author Hash
 * @since 2021/1/2
 */
public class OrderCodeGenerator {
    // 自增长序列
    private int i = 1;

    // 按照“年-月-日-小时-分钟-秒-自增长序列”的规则生成订单编号
    public String getOrderCode() {
        Date now = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss-");
        return sdf.format(now) + i++;
    }



    public static void main(String[] args) {
        OrderCodeGenerator ong = new OrderCodeGenerator();
        for (int i = 0; i < 10; i++) {
            System.out.println(ong.getOrderCode());
        }
    }

}
