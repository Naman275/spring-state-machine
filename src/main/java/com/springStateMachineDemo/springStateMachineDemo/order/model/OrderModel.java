package com.springStateMachineDemo.springStateMachineDemo.order.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class OrderModel {
    private Double amount;
    private Long orderId;
}
