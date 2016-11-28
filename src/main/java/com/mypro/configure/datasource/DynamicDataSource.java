package com.mypro.configure.datasource;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import com.mypro.context.DynamicDataSourceContextHolder;

/**
 * 动态数据源
 * @author user
 *
 */
public class DynamicDataSource extends AbstractRoutingDataSource {

	@Override
	protected Object determineCurrentLookupKey() {
		return DynamicDataSourceContextHolder.getDataSourceType();
	}
}
