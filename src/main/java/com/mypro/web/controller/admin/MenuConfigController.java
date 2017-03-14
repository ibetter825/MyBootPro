package com.mypro.web.controller.admin;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.mypro.bean.model.PageModel;
import com.mypro.bean.rq.PagerRq;
import com.mypro.service.admin.SysMenuConfigService;

/**
 * 查询数据库相关信息
 * @author user
 *
 */
@RestController
public class MenuConfigController extends BaseAdminController {
	@Autowired
	private SysMenuConfigService smcService;
	
	@SuppressWarnings("rawtypes")
	@RequestMapping("/tables")
	public PageModel tables(PagerRq page){
		Page<Map> pager = PageHelper.startPage(page.getPage(), page.getSize());
		PageHelper.orderBy(page.getOrder());
		String sql = "select TABLE_NAME, COLLATION_NAME, ORDINAL_POSITION, COLUMN_DEFAULT, IS_NULLABLE, DATA_TYPE, COLUMN_COMMENT from information_schema.columns where table_schema='boot'";
		smcService.queryTable(sql);
		return new PageModel(pager);
	}
}
