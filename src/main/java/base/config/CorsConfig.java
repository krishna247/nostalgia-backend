package base.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

     @Override
        public void addCorsMappings(CorsRegistry registry) {
            registry.addMapping("/**")
                    .allowedOrigins("http://3.65.240.94:3000")
                    .allowedOrigins("http://3.65.240.94:8080")
                    .allowedOrigins("http://3.65.240.94")
                    .allowedOrigins("http://krishnaj.me")
                    .allowedOrigins("http://krishnaj.me:8080")
                    .allowedOrigins("http://localhost:3000")
                 .allowedHeaders("*").allowCredentials(true);
        }
    }
