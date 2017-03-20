package com.mypro.web.controller.admin;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.mypro.bean.entity.admin.SysOpt;
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
	 * 查询页面的按钮
	 * @param opt
	 * @return
	 */
	@RequestMapping(value="/opt/btns")
	public List<SysOpt> opt(SysOpt opt){
		return sysOptService.queryOpts(opt);
	}
}
