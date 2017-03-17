package com.mypro.web.controller.admin;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.mypro.bean.entity.admin.SysMenuConfig;
import com.mypro.bean.enums.ResultMessageEnum;
import com.mypro.bean.model.PageModel;
import com.mypro.bean.model.ResultModel;
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
	
	/**
	 * 查询boot下的所有数据表
	 * boot需要改成配置的内容，动态去获取
	 * @param page
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping("/db/tables")
	public PageModel tables(PagerRq page){
		Page<Map> pager = PageHelper.startPage(page.getPage(), page.getSize());
		PageHelper.orderBy(page.getOrder());
		String sql = "select TABLE_NAME, TABLE_COMMENT from information_schema.tables where table_schema='boot'";
		smcService.queryTable(sql);
		return new PageModel(pager);
	}
	
	/**
	 * 查询数据表中的字段
	 * @param page
	 * @param tableName
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping("/db/columns")
	public PageModel columns(PagerRq page,	@RequestParam(defaultValue = "") String tableName){
		Page<Map> pager = PageHelper.startPage(page.getPage(), page.getSize());
		PageHelper.orderBy(page.getOrder());
		String sql = "select COLUMN_NAME, COLUMN_TYPE, DATA_TYPE, ORDINAL_POSITION, COLUMN_DEFAULT, IS_NULLABLE, COLUMN_COMMENT from information_schema.columns where table_schema='boot' and table_name = '"+ tableName +"'";
		smcService.queryTable(sql);
		return new PageModel(pager);
	}
	
	/**
	 * 添加或修改菜单配置
	 * @param config
	 * @return
	 */
	@RequestMapping("/cfg/aoe")
	public ResultModel addOrEdit(SysMenuConfig config){
		ResultModel model = null;
		boolean res = false; 
		if(config != null)
			res = smcService.addOrEditConfig(config);
		
		if(res)
			model = new ResultModel();
		else
			model = new ResultModel(ResultMessageEnum.OPTION_EXCEPTION.getMsg());
		return model;
	}
}
