package ru.geekbrains.camel_geekbrains_demo.routes;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

/**
 * Project camel_geekbrains_demo
 *
 * @Author Alexander Grigorev
 * Created 20.03.2021
 * v1.0
 */
@Component
public class ActiveMQRoute extends RouteBuilder {
    @Override
    public void configure() throws Exception {
        from("jms:queue:{{spring.jms.template.task-queue}}")
                .routeId("amq_to_telegram")
                .autoStartup(true)
                .convertBodyTo(String.class)
                .to("telegram:bots?authorizationToken={{telegram.key}}:{{telegram.token}}&chatId={{telegram.daddy-token}}");
    }
}
