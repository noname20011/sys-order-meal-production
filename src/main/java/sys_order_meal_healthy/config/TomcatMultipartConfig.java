package sys_order_meal_healthy.config;

import org.apache.coyote.http11.AbstractHttp11Protocol;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TomcatMultipartConfig {

    @Bean
    public WebServerFactoryCustomizer<TomcatServletWebServerFactory> customizer() {
        return factory -> factory.addConnectorCustomizers(connector -> {
            // Nâng giới hạn tổng số lượng file + text fields trong form-data lên tối đa 100
            connector.setMaxPartCount(100);

            // Ép Tomcat nâng giới hạn parameter xử lý nội bộ của đầu nối (connector)
            if (connector.getProtocolHandler() instanceof AbstractHttp11Protocol<?>) {
                connector.setMaxParameterCount(100);
            }
        });
    }
}
