package com.springStateMachineDemo.springStateMachineDemo.order.handler;

import com.springStateMachineDemo.springStateMachineDemo.order.OrderEvents;
import com.springStateMachineDemo.springStateMachineDemo.order.OrderRepository;
import com.springStateMachineDemo.springStateMachineDemo.order.OrderStates;
import com.springStateMachineDemo.springStateMachineDemo.order.entity.OrderEntity;
import com.springStateMachineDemo.springStateMachineDemo.order.model.OrderModel;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.action.Action;
import org.springframework.stereotype.Component;

@Log
@Component
public class SubmitHandler implements Action<OrderStates, OrderEvents> {
    @Autowired
    private OrderRepository orderRepository;
    @Override
    public void execute(StateContext stateContext) {
        OrderModel order=OrderModel.class.cast(stateContext.getExtendedState().getVariables().getOrDefault("order",null));
        StateMachine<OrderStates,OrderEvents> machine = stateContext.getStateMachine();
        OrderStates state=machine.getState().getId();
        switch (state){
            case SUBMITTED:{
                if(updateOrder(order,OrderStates.SUBMITTED))machine.sendEvent(OrderEvents.PAY);break;
            }
            case PAID:{
                updateOrder(order,OrderStates.PAID);
                if(order.getAmount()>=100)
                    machine.sendEvent(OrderEvents.COMPLETE);
                else
                    machine.sendEvent(OrderEvents.CANCEL);
                break;
            }
            case COMPLETED:{if(updateOrder(order,OrderStates.COMPLETED))break;}
            case CANCELLED:{if(updateOrder(order,OrderStates.CANCELLED))break;}
        }
    }
    public boolean updateOrder(OrderModel orderModel,OrderStates orderState){
        log.info("Order Id : "+orderModel.getOrderId()+"  is  "+orderState.name());
        OrderEntity orderEntity=new OrderEntity();
        orderEntity.setOrderId(orderModel.getOrderId());
        orderEntity.setAmount(orderModel.getAmount());
        orderEntity.setState(orderState.name());
        orderRepository.save(orderEntity);
        return true;
    }
}
