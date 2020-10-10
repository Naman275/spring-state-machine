package com.springStateMachineDemo.springStateMachineDemo.config;


import com.springStateMachineDemo.springStateMachineDemo.order.OrderEvents;
import com.springStateMachineDemo.springStateMachineDemo.order.OrderStates;
import com.springStateMachineDemo.springStateMachineDemo.order.handler.SubmitHandler;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.config.EnableStateMachineFactory;
import org.springframework.statemachine.config.StateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineConfigurationConfigurer;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;
import org.springframework.statemachine.listener.StateMachineListenerAdapter;
import org.springframework.statemachine.state.State;

@Log
@Configuration
@EnableStateMachineFactory
public class OrderMachineConfiguration extends StateMachineConfigurerAdapter<OrderStates, OrderEvents> {

    @Autowired
    private SubmitHandler submitHandler;

    StateMachineListenerAdapter<OrderStates,OrderEvents> listenerAdapter=new StateMachineListenerAdapter<OrderStates,OrderEvents>(){
        @Override
        public void stateChanged(State<OrderStates, OrderEvents> from, State<OrderStates, OrderEvents> to) {
            log.info("state changed from="+from+"   to="+to);
            super.stateChanged(from, to);
        }
    };

    @Override
    public void configure(StateMachineConfigurationConfigurer<OrderStates, OrderEvents> config) throws Exception {
        config.withConfiguration()
                .autoStartup(false)
                .listener(listenerAdapter);
    }

    @Override
    public void configure(StateMachineStateConfigurer<OrderStates, OrderEvents> states) throws Exception {
        states.withStates()
                .initial(OrderStates.SUBMITTED).stateEntry(OrderStates.SUBMITTED,submitHandler)
                .state(OrderStates.PAID).stateEntry(OrderStates.PAID,submitHandler)
                .end(OrderStates.CANCELLED).stateEntry(OrderStates.CANCELLED,submitHandler)
                .end(OrderStates.COMPLETED).stateEntry(OrderStates.COMPLETED,submitHandler);
    }

    @Override
    public void configure(StateMachineTransitionConfigurer<OrderStates, OrderEvents> transitions) throws Exception {
        transitions.withExternal()
                .source(OrderStates.SUBMITTED).target(OrderStates.PAID).event(OrderEvents.PAY)
                .and().withExternal().
                source(OrderStates.PAID).target(OrderStates.COMPLETED).event(OrderEvents.COMPLETE)
                .and().withExternal()
                .source(OrderStates.PAID).target(OrderStates.CANCELLED).event(OrderEvents.CANCEL);
    }
}

