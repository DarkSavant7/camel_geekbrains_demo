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
public class FileRouter extends RouteBuilder {
    @Override
    public void configure() throws Exception {
        log.info("Start listening for files");
        from("file://in?delete=true&idempotent=true").log("Transferring ${file:name} and size ${file:size}").to("file://out");

        from("file://inbox?delete=true")
                .autoStartup(false)
                .choice()

                .when(header("CamelFileName").endsWith(".jpg"))
                .log("Transferring file ${file:name} to images")
                .to("file://outbox/images")
                .endChoice()

                .when(header("CamelFileName").endsWith(".txt"))
                .log("Transferring file ${file:name} to documents")
                .to("file://outbox/documents")
                .endChoice()

                .otherwise()
                .log("Wrong file ${file:name} moving to trash")
                .to("file:outbox/trash")
                .endChoice();
    }
}
