package tw.ym.fshra.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author H-yin on 2020.
 *         設置 swagger2 自動產生 api 說明
 *         http://localhost:8080/FSRIS/api/swagger-ui.html
 */
@Configuration
@EnableSwagger2
public class Swagger2Config {

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                //返回ApiSelectorBuilder實體:控制Swagger要展現哪些介接，此處採用指定路徑
                .select()
                //掃描該路徑下所有的Controller定義的API (除了被 @ApiIgnore 者)
                .apis(RequestHandlerSelectors.basePackage("tw.ym.fshra.controller"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("FSRIS - RESTful APIs 說明")
                .description("Spring Boot 2 & Swagger 2")
                .version("1.0")
                .build();
    }

}
