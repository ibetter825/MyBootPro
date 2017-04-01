package com.mypro.configure.security.Authentication;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Service;
import com.mypro.bean.entity.admin.SysOpt;
import com.mypro.dao.admin.SysOptDao;

@Service
public class MyInvocationSecurityMetadataSourceService implements FilterInvocationSecurityMetadataSource {
	@Autowired
	private SysOptDao optDao;
	private static Map<String, Collection<ConfigAttribute>> resourceMap = null;
	
	@PostConstruct//被@PostConstruct修饰的方法会在服务器加载Servle的时候运行，并且只会被服务器执行一次。PostConstruct在构造函数之后执行,init()方法之前执行。
    private void loadResourceDefine() {//一定要加上@PostConstruct注解
		//在Web服务器启动时，提取系统中的所有权限。
		List<SysOpt> list = optDao.selectAll();
        if(list != null && list.size() > 0) {
        	resourceMap = new HashMap<String, Collection<ConfigAttribute>>();
        	ConfigAttribute ca = new SecurityConfig("all");
        	Collection<ConfigAttribute> atts = new ArrayList<ConfigAttribute>();
        	atts.add(ca);
        	for (SysOpt opt : list)
        		resourceMap.put("/admin", atts);
        }
    }
	@Override
	public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
		System.out.println("nwuidhwuiehdfu"); 
		FilterInvocation filterInvocation = (FilterInvocation) object;  
        if (resourceMap == null) {  
            loadResourceDefine();  
        }  
        Iterator<String> ite = resourceMap.keySet().iterator();  
        while (ite.hasNext()) {  
            String resURL = ite.next();  
             RequestMatcher requestMatcher = new AntPathRequestMatcher(resURL);  
                if(requestMatcher.matches(filterInvocation.getHttpRequest())) {  
                return resourceMap.get(resURL);  
            }  
        }  
        return null;
	}

	@Override
	public Collection<ConfigAttribute> getAllConfigAttributes() {
        return new ArrayList<ConfigAttribute>();
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return true;
	}

}
