package com.mypro.configure.druid.filter;

import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import com.alibaba.druid.support.http.WebStatFilter;
/**
 * Druid Filter的实现类(通过注解建立过滤器，与在web.xml中配置一样)
 * @author user
 *
 */
@WebFilter(filterName="druidWebStatFilter",urlPatterns="/*",  
    initParams={@WebInitParam(name="exclusions",value="*.js,*.gif,*.jpg,*.bmp,*.png,*.css,*.ico,/druid/*")/*忽略资源*/})
public class DruidStatFilter extends WebStatFilter {

}
