package tw.ym.fshra.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import tw.ym.fshra.util.Log;

/**
 * @author H-yin on 2020.
 *         介接通道許可設定
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Value("${cors.allowed.origins}")
    private String allowOrigins;

// 在Spring Boot 2.0之前版本都是重寫WebMvcConfigurerAdapter來自定義攔截器轉換器等。
// SpringBoot 2.0 後該類被標為@Deprecated。只能靠繼承 WebMvcConfigurer 並覆寫方法來自定義

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        Log.info("Initialized WebMvcConfigurer....");
        registry.addMapping("/**")
                //許可的請求通道
                .allowedOrigins(allowOrigins.split(","))
                //允許使用那些請求方式
                .allowedMethods("GET", "POST", "HEAD", "OPTIONS")
                //允許哪些Header
                .allowedHeaders("*")
                //允許跨域攜帶cookie資訊，預設跨網域請求是不攜帶cookie資訊的。
                .allowCredentials(true)
                .maxAge(1800);
        Log.info("Initialized WebMvcConfigurer allowedOrigins=" + allowOrigins);
        Log.info("Initialized WebMvcConfigurer complete.");
    }
}
