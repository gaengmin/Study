package com.example.shopping.repository;

import com.example.shopping.entity.Order;
import org.springframework.stereotype.Repository;

@Repository //레포지터리 어노테이션 처리
public class JdbcOrderRepository implements OrderRepository {
    @Override
    public void insert(Order order) {
        if (order == null) {
            throw new IllegalArgumentException("인수가 바르지 않습니다.");
        }
        System.out.println("주문을 등록했습니다. id=" + order.getId());
    }
}
