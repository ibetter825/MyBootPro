package com.mypro.configure;

import org.springframework.context.annotation.Bean;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;

//@Configuration //启用验证，@rang,@min,@max等注解才能生效，开启以后bindingResult就不起作用，会被一起拦截，暂时不需要这个
public class ValidationConfig {
	@Bean
	public MethodValidationPostProcessor methodValidationPostProcessor() {  
	  return new MethodValidationPostProcessor();
	}
}
