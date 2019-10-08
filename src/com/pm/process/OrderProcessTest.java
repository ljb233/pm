package com.pm.process;






import javax.transaction.SystemException;

import static org.junit.jupiter.api.Assertions.*;

class OrderProcessTest {

    @org.junit.jupiter.api.Test
    void cancelOrder(){
        OrderProcess op = new OrderProcess();
        op.cancelOrder(2);
    }

    @org.junit.jupiter.api.Test
    void deleteOrder() {
    }
}