package ru.geekbrains.camel_geekbrains_demo.beans;

import lombok.extern.slf4j.Slf4j;
import org.apache.camel.Exchange;
import org.springframework.stereotype.Component;

/**
 * Project camel_geekbrains_demo
 *
 * @Author Alexander Grigorev
 * Created 20.03.2021
 * v1.0
 */
@Component
@Slf4j
public class SomeBean {
    public void doSomething(Exchange exchange) {

        log.info("Hello from bean! Got message {}", exchange.getMessage());
    }
}
