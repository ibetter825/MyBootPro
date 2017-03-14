package com.mypro.web.controller.admin;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
	@RequestMapping("/db/tables")
	public PageModel tables(PagerRq page){
		Page<Map> pager = PageHelper.startPage(page.getPage(), page.getSize());
		PageHelper.orderBy(page.getOrder());
		String sql = "select TABLE_NAME, TABLE_COMMENT from information_schema.tables where table_schema='boot'";
		smcService.queryTable(sql);
		return new PageModel(pager);
	}
	
	@SuppressWarnings("rawtypes")
	@RequestMapping("/db/columns")
	public PageModel columns(PagerRq page,	@RequestParam(defaultValue = "") String tableName){
		Page<Map> pager = PageHelper.startPage(page.getPage(), page.getSize());
		PageHelper.orderBy(page.getOrder());
		String sql = "select COLUMN_NAME, COLUMN_TYPE, ORDINAL_POSITION, COLUMN_DEFAULT, IS_NULLABLE, COLUMN_COMMENT from information_schema.columns where table_schema='boot' and table_name = '"+ tableName +"'";
		smcService.queryTable(sql);
		return new PageModel(pager);
	}
}
