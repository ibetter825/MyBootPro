package com.mypro.web.controller.admin;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.collect.Maps;
import com.mypro.bean.entity.admin.SysOpt;
import com.mypro.configure.security.customer.MyUserDetails;
import com.mypro.service.admin.SysOptService;
/**
 * 页面操作按钮
 * @author user
 *
 */
@RestController
public class OptController extends BaseAdminController {
	@SuppressWarnings("unused")
	private Logger logger = LoggerFactory.getLogger(IndexController.class);
	@Autowired
	private SysOptService sysOptService;
	
	/**
	 * 查询用户在mid菜单页面的按钮
	 * @param opt
	 * @return
	 */
	@RequestMapping(value="/opt/btns/{mid}")
	public List<SysOpt> opt(@PathVariable("mid") Integer mid){
		MyUserDetails details =	getCurrentUser();
		if(details.isSuper()){
			SysOpt opt = new SysOpt();
			opt.setMenuId(mid);
			return sysOptService.queryOpts(opt);
		}else{
			Map<String, Integer> rq = Maps.newHashMap();
			rq.put("userId", details.getUserId());
			rq.put("menuId", mid);
			return sysOptService.queryOptsByUseAndMenu(rq);
		}
			
	}
}
