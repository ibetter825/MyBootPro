package com.mpro.web.controller.admin.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import com.mypro.Application;
import com.mypro.bean.entity.QtTag;
import com.mypro.service.admin.TagService;

/**
 * Test Example
 * @author user
 *
 */
@RunWith(SpringJUnit4ClassRunner.class) // SpringJUnit支持，由此引入Spring-Test框架支持！ 
@SpringBootTest(classes = Application.class) // 指定SpringBoot工程的Application启动类
@WebAppConfiguration // 由于是Web项目，Junit需要模拟ServletContext，因此我们需要给我们的测试类加上@WebAppConfiguration。
public class TagControllerTest {
	@Autowired
	private TagService tagService;
	
	@Test
	public void test(){
		QtTag tag = tagService.selectTagById(10);
		System.err.println(tag.getTag_name());
	}
}
