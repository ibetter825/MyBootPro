package com.mypro.configure.security.authentication;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import javax.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Service;
import com.mypro.bean.constant.WebConstant;
import com.mypro.dao.admin.SysOptDao;

@Service
public class MyInvocationSecurityMetadataSourceService implements FilterInvocationSecurityMetadataSource {
	private static final Logger logger = LoggerFactory.getLogger(MyInvocationSecurityMetadataSourceService.class);
	@Autowired
	private SysOptDao optDao;
	private static Map<String, Collection<ConfigAttribute>> resourceMap = null;//存放所有的操作的完整路由地址，如:/admin/menu/add
	
	private static Collection<ConfigAttribute> atts;
	static {
		ConfigAttribute ca = new SecurityConfig("ALL");//"ALL"按照ss默认的权限设计，这个atts存放的是 资源对应的所有 角色,在本项目没有这样设计，"ALL"没有实际的意义
		atts = new ArrayList<ConfigAttribute>();
		atts.add(ca);
	}
	private static final String regEx = WebConstant.ADMIN_REQUEST_ROOT_PATH + "/db/\\S*/\\S*";
	
	@PostConstruct//被@PostConstruct修饰的方法会在服务器加载Servle的时候运行，并且只会被服务器执行一次。PostConstruct在构造函数之后执行,init()方法之前执行。
    private void loadResourceDefine() {//一定要加上@PostConstruct注解
		logger.debug("SpringSecurity: 装载系统所有权限");
		//在Web服务器启动时，提取系统中的所有权限。
		List<Map<String, Object>> list = optDao.selectAllOpts();
        if(list != null && list.size() > 0) {
        	resourceMap = new HashMap<String, Collection<ConfigAttribute>>();
        	//ConfigAttribute ca = new SecurityConfig("ALL");//"ALL"按照ss默认的权限设计，这个atts存放的是 资源对应的所有 角色,在本项目没有这样设计，"ALL"没有实际的意义
        	//Collection<ConfigAttribute> atts = new ArrayList<ConfigAttribute>();
        	//atts.add(ca);
        	for (Map<String, Object> opt : list)
        		resourceMap.put(WebConstant.ADMIN_REQUEST_ROOT_PATH + "/" + opt.get("menu_route") + "/" + opt.get("opt_code"), atts);
        }
    }
	@Override
	public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
		FilterInvocation filterInvocation = (FilterInvocation) object;
		String uri = filterInvocation.getHttpRequest().getRequestURI();
		if(!uri.startsWith(WebConstant.ADMIN_REQUEST_ROOT_PATH)) return null;//不拦截/admin/**以外的路径
        if (resourceMap == null) loadResourceDefine();
        //以/admin/db/{tableName}/{optCode}开头的链接，需要特殊处理，如果不特殊处理的话，就会通过tableName直接操作数据库
        if(isDbOptUri(uri)) return atts;
        Iterator<String> ite = resourceMap.keySet().iterator();
        String resURL = null;
        RequestMatcher requestMatcher = null;
        while (ite.hasNext()) {//遍历resourceMap查看该请求是否需要权限验证，如果不存在则不需要验证
             resURL = ite.next();
             requestMatcher = new AntPathRequestMatcher(resURL);
             if(requestMatcher.matches(filterInvocation.getHttpRequest())) return resourceMap.get(resURL);
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

	/**
	 * 是否是/admin/db/{tableName}/{optCode}链接
	 * @param url
	 * @return
	 */
	public boolean isDbOptUri(String uri){
		return Pattern.compile(regEx).matcher(uri).matches();
	}
}
