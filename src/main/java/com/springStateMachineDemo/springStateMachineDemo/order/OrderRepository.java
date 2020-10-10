package com.springStateMachineDemo.springStateMachineDemo.order;

import com.springStateMachineDemo.springStateMachineDemo.order.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity,Integer> {
}
