package bg.softuni.quizzical.config;

import bg.softuni.quizzical.web.interceptor.FaviconInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class ApplicationWebConfiguration implements WebMvcConfigurer {
    private final FaviconInterceptor faviconInterceptor;


    @Autowired
    public ApplicationWebConfiguration(FaviconInterceptor faviconInterceptor) {
        this.faviconInterceptor = faviconInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(this.faviconInterceptor);
    }
}