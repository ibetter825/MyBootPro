package com.mypro.configure;

import java.util.List;

import javax.servlet.Servlet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.web.ErrorAttributes;
import org.springframework.boot.autoconfigure.web.ErrorViewResolver;
import org.springframework.boot.autoconfigure.web.ResourceProperties;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.autoconfigure.web.WebMvcAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.DispatcherServlet;

import com.mypro.web.controller.MyBasicErrorController;

/**
 * 定义自定义好的Controller,覆盖原生的ErrorMvcAutoConfiguration中的BasicErrorController
 * 与MyBasicErrorController一起，处理错误页面跳转问题
 * 参考页面: http://blog.csdn.net/king_is_everyone/article/details/53080851
 * @author user
 *
 */
@Configuration
@ConditionalOnWebApplication
@ConditionalOnClass({Servlet.class, DispatcherServlet.class})
@AutoConfigureBefore(WebMvcAutoConfiguration.class)
@EnableConfigurationProperties(ResourceProperties.class)
public class ErrorControllerConfig {
	@Autowired(required = false)
    private List<ErrorViewResolver> errorViewResolvers;
    private final ServerProperties serverProperties;

    public ErrorControllerConfig(
            ServerProperties serverProperties) {
        this.serverProperties = serverProperties;
    }

    @Bean
    public MyBasicErrorController basicErrorController(ErrorAttributes errorAttributes) {
        return new MyBasicErrorController(errorAttributes, this.serverProperties.getError(),
                this.errorViewResolvers);
    }

}
