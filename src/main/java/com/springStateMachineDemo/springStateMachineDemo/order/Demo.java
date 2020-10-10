package com.springStateMachineDemo.springStateMachineDemo.order;

import com.springStateMachineDemo.springStateMachineDemo.order.model.OrderModel;
import lombok.extern.java.Log;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineFactory;
import org.springframework.stereotype.Component;

import java.util.Map;

@Log
@Component
public class Demo implements ApplicationRunner {

    private final StateMachineFactory<OrderStates,OrderEvents> factory;

    private Long orderId=1l;

    Demo(StateMachineFactory<OrderStates,OrderEvents> fac){
        this.factory=fac;
    }
    @Override
    public void run(ApplicationArguments args) throws Exception {
        initiateOrder(getOrder1());
        initiateOrder(getOrder2());
        /*log.info("current state="+machine.getState().getId().name());
        machine.sendEvent(OrderEvents.FULFILL); // submit >/ Paid
        log.info("current state="+machine.getState().getId().name());
        machine.sendEvent(OrderEvents.PAY);
        log.info("current state="+machine.getState().getId().name());
        machine.sendEvent(OrderEvents.FULFILL);
        log.info("current state="+machine.getState().getId().name());*/
    }
    private void initiateOrder(OrderModel orderModel){
        StateMachine<OrderStates,OrderEvents> machine=this.factory.getStateMachine(Long.toString(orderModel.getOrderId()));
        Map<Object,Object> variables=machine.getExtendedState().getVariables();
        variables.putIfAbsent("order",orderModel);
        machine.start();
    }
    private OrderModel getOrder1(){
        return new OrderModel(12.0,1l);
    }
    private OrderModel getOrder2(){
        return new OrderModel(125.0,2l);
    }
}
