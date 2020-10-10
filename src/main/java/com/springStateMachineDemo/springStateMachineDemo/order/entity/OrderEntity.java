package com.springStateMachineDemo.springStateMachineDemo.order.entity;

import com.springStateMachineDemo.springStateMachineDemo.order.OrderStates;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class OrderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int serial;

    private Double amount;

    private Long orderId;

    private String state;

    private Date createdAt=new Date();

    private Date updatedAt=new Date();

    public OrderEntity(Date date, OrderStates orderStates){
        this.updatedAt=date;
        this.state=orderStates.name();
    }

}
