package com.intuit.test.controller;


import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.PathItem;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import io.swagger.v3.oas.models.servers.ServerVariable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

/**
 * Configures Swagger Open API
 */
@Configuration
public class OpenApiConfig {
    /*
      properties bean
     */
    static class ApiProperties{

        @Value("${swagger.title}")
        private String title;
        @Value("${swagger.description}")
        private String desription;
        @Value("${swagger.version}")
        private String version;


        @Value("${swagger.licence.title}")
        private String licenceTitle;
        @Value("${swagger.licence.title}")
        private String licenceUrl;

        @Value("${swagger.external.doc.description}")
        private String externalDocDescription;

        @Value("${swagger.external.doc.url}")
        private String externalDocUrl;
    }
    @Bean
    static ApiProperties propsApi(){
        return new ApiProperties();
    }
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build();
    }
   @Bean
    public OpenAPI testApi(@Autowired ApiProperties apiProps) {
       Server server= new Server();
       server.setDescription("players access");
       server.setUrl("http://localhost:10101/api");

       PathItem piPlayers= new PathItem();
       piPlayers.description("to get players");
       List<Server> servers= new ArrayList(){{add(server);}};
       piPlayers.setServers(servers);
       Operation op= new Operation();
       op.addServersItem(server);
       op.setDescription("get all players");
       op.setOperationId("/players");
       piPlayers.operation(PathItem.HttpMethod.GET,op);


        return new OpenAPI()
                .info(new Info().title(apiProps.title)
                        .description(apiProps.desription)
                        .version(apiProps.version)
                        .license(new License().name(apiProps.licenceTitle).url(apiProps.licenceUrl)))
                .externalDocs(new ExternalDocumentation()
                        .description(apiProps.externalDocDescription)
                        .url(apiProps.externalDocUrl)).addServersItem(server).path("/api",piPlayers);

    }
}
