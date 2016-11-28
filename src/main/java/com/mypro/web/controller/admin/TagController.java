package com.mypro.web.controller.admin;

import java.util.List;
import java.util.Map;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.mypro.bean.entity.QtTag;
import com.mypro.bean.rq.QueryRq;
import com.mypro.service.admin.TagService;

@RestController
//@Scope("prototype") //每次请求生成一个controller，改变默认的单例模式
@RequestMapping("/tag")
@Validated
public class TagController extends BaseAdminController {
	private Logger logger = LoggerFactory.getLogger(TagController.class);
	@Autowired
	private TagService tagService;
	
	@RequestMapping("{id}")
	public QtTag ds(@PathVariable("id") Integer id){
		return tagService.selectTagById(id);
	}
	/**
	 * 多数据源案例的使用
	 * @param id
	 * @return
	 */
	@RequestMapping("/ds1/{id}")
	public QtTag ds1(@PathVariable("id") Integer id){
		logger.info("--访问TagController");
		return tagService.selectTagByIdFromDs1(id);
	}
	/**
	 * 分页插件使用的案例
	 * @param p pageNumber
	 * @param s pageSize
	 * @return
	 */
	@RequestMapping("/query/{p}/{s}")
	public List<QtTag> query(@PathVariable("p") Integer p, @PathVariable("s") Integer s){
		PageHelper.startPage(p, s);//分页插件
		if(true)
			throw new RuntimeException("出错");
		return tagService.select();
	}
	/**
	 * ehcache缓存案例
	 * @return
	 */
	@RequestMapping("/all")
	public List<QtTag> all(){
		return tagService.selectAllWithCache();
	}
	@RequestMapping("/add/{name}")
	public String add(@PathVariable("name") String name){
		QtTag tag = new QtTag();
		tag.setTag_name(name);
		tagService.insertTag(tag);
		return "success";
	}
	/**
	 * 使用xml中的sql配置案例
	 * @param name
	 * @return
	 */
	@RequestMapping("/save/{name}")
	public String save(@PathVariable("name") String name){
		QtTag tag = new QtTag();
		tag.setTag_name(name);
		tagService.saveTag(tag);
		return "success";
	}
	
	@RequestMapping("/tag")
	public ModelAndView tag(){
		return new ModelAndView("admin/tag");
	}
	
	/**
	 * bean validator验证demo
	 * 	@valid 注解需要验证的实体 配合 QtTag中的 @NotEmpty等注解使用
	 * 	出现错误会在拦截器中拦截到该错误
	 * @param tag
	 * @param errors
	 * @return
	 */
	@RequestMapping("/vali")
	public String vali(@Valid QtTag tag, BindingResult result){
		Map<String, String> errors = resultErrors(result);
		if(errors != null)
			return JSON.toJSONString(errors);
		return "succes";
	}
	
	/**
	 * 用于接收前台查询参数，map
	 * @param rq
	 * @return
	 */
	@RequestMapping("/rq")
	public Object rq(QueryRq rq){
		PageHelper.startPage(1, 5);
		return tagService.selectWithParams(rq);
	}
	
	@RequestMapping("/map/{id}")
	public Object map(@PathVariable("id") Integer id){
		QtTag tag = new QtTag();
		tag.setTag_id(id);
		return tagService.selectMap(tag);
	}
}
