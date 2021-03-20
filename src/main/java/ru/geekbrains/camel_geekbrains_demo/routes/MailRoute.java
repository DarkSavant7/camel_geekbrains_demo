package ru.geekbrains.camel_geekbrains_demo.routes;

import lombok.AllArgsConstructor;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;
import ru.geekbrains.camel_geekbrains_demo.processors.AttachmentProcessor;

/**
 * Project camel_geekbrains_demo
 *
 * @Author Alexander Grigorev
 * Created 20.03.2021
 * v1.0
 */
@Component
@AllArgsConstructor
public class MailRoute extends RouteBuilder {

    private final AttachmentProcessor processor;

    @Override
    public void configure() throws Exception {

        from("imaps://{{email.address}}?username={{email_credentials.username}}&password=" +
                "{{email_credentials.password}}&delete={{email.autodelete}}&unseen={{email.unseen}}" +
                "&delay={{email.delay}}")
                .routeId("mail_route")
                .autoStartup(true)
                .log(LoggingLevel.INFO, "Got a new email from ${header.from} with body: ${body}")

                .process(processor)
                .choice()

                .when(header("subject").startsWith("task"))
                .log(LoggingLevel.INFO, "Got a new task processing....")
                .to("jms:queue:{{spring.jms.template.task-queue}}")
                .endChoice()

                .otherwise()
                .log(LoggingLevel.INFO, "Got some else message")
                .to("jms:queue:{{spring.jms.template.otherwise-queue}}")
                .end();
//                .to("file://")
    }
}
