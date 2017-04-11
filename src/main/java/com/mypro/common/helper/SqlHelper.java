package com.mypro.common.helper;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;

import com.mypro.bean.constant.SqlConstant;
import com.mypro.bean.rq.BeanRq;
import com.mypro.bean.rq.QueryRq;

public class SqlHelper {
	private static final String COLUMN_NAME = "COLUMN_NAME";
	@SuppressWarnings("unused")
	private static final String IS_NULLABLE = "IS_NULLABLE";
	@SuppressWarnings("unused")
	private static final String COLUMN_KEY = "COLUMN_KEY";
	
	private static final String COLUMN_KEY_PRIMARY_VAL = "PRI";//mysql 主键
	
	/**
	 * 查询语句拼接
	 * @return
	 */
	public static String select(List<Map<String, Object>> columnList, String tableName, QueryRq rq){
		StringBuffer selectBuffer = new StringBuffer("SELECT ");
		StringBuffer whereBuffer = null;
		String columnName = null;
		for(int i = 0, l = columnList.size(); i < l; i++){
			columnName = MapUtils.getString(columnList.get(i), COLUMN_NAME);
			if(i == l - 1)
				selectBuffer.append(columnName);
			else
				selectBuffer.append(columnName + ",");
		}
		selectBuffer.append(" FROM " + tableName);
		if(rq.getQrq() != null){
			if(whereBuffer == null) whereBuffer = new StringBuffer();
			Map<String, Object> params = rq.getQrq(); 
			Iterator<String> it = params.keySet().iterator();
			String key = null, val = null;
			while (it.hasNext()) {
				key = it.next();//eg: role_name#eq# or role_name
				val = MapUtils.getString(params, key);
				if(StringUtils.isEmpty(val)) continue;
				if(whereBuffer.length() == 0)
					whereBuffer.append(getCondition(key, val));
				else
					whereBuffer.append(" AND " + getCondition(key, val));
			}
			if(whereBuffer.length() > 0) whereBuffer.insert(0, " WHERE ");
		}
		return selectBuffer.append(whereBuffer == null ? "" : whereBuffer).toString();
	}
	
	/**
	 * 插入语句拼接
	 * @param tableName
	 * @param rq
	 * @return
	 */
	public static String insert(String tableName, BeanRq rq){
		StringBuffer insertBuffer = new StringBuffer("INSERT INTO " + tableName);
		StringBuffer valueBuffer = new StringBuffer(" VALUES");
		Map<String, Object> bean = rq.getBrq();
		Iterator<String> it = bean.keySet().iterator();
		String col = null, val = null;
		while (it.hasNext()) {
			col = it.next();
			val = MapUtils.getString(bean, col, "");
			if (insertBuffer.indexOf("(") == -1){
				insertBuffer.append("(" + col);
				valueBuffer.append("('" + val + "'");
			}else{
				insertBuffer.append("," + col);
				valueBuffer.append(",'" + val + "'");
			}
		}
		insertBuffer.append(")");
		valueBuffer.append(")");
		return insertBuffer.append(valueBuffer).toString();
	}
	/**
	 * 修改语句拼接
	 * @param tableName
	 * @param bean
	 * @return
	 */
	public static String update(String tableName, BeanRq bean, QueryRq query){
		StringBuffer updateBuffer = new StringBuffer("UPDATE " + tableName);
		Map<String, Object> br = bean.getBrq();//修改的值
		Map<String, Object> qr = query.getQrq();//条件
		Iterator<String> it = br.keySet().iterator();
		String col = null, val = null;
		while (it.hasNext()) {
			col = it.next();//role_name#eq#
			val = MapUtils.getString(br, col, "");
			if (updateBuffer.indexOf("SET") == -1)
				updateBuffer.append(" SET " + col + "='" + val + "'");
			else
				updateBuffer.append("," + col + "='" + val + "'");
		}
		it = qr.keySet().iterator();
		while (it.hasNext()) {
			col = it.next();
			val = MapUtils.getString(qr, col, "");
			if(StringUtils.isEmpty(val)) continue;
			if (updateBuffer.indexOf("WHERE") == -1)
				updateBuffer.append(" WHERE " + col + "='" + val + "'");
			else
				updateBuffer.append(" AND " + col + "='" + val + "'");
		}
		return updateBuffer.toString();
	}
	
	/**
	 * 是否是主键
	 * @param key
	 * @return
	 */
	@SuppressWarnings("unused")
	private static boolean isPrimaryKey(String key){
		return key.equalsIgnoreCase(COLUMN_KEY_PRIMARY_VAL);
	}
	
	/**
	 * 获取字段条件判断符号
	 * @param key eg: role_name#eq# or role_name
	 * @param val
	 * @return
	 */
	private static String getCondition(String key, String val){
		String value = "'" + val + "'";
		String sign = "=", symbol = null;
		String[] arr = null;
		if(key.endsWith("#")){
			arr = key.split("#");
			key = arr[0];
			symbol = arr[1];
			switch (symbol) {
				case SqlConstant.EQUAL:
					sign = " = ";
					break;
				case SqlConstant.NOT_EQUAL:
					sign = " <> ";
					break;
				case SqlConstant.GREATER_THAN:
					sign = " > ";
					break;
				case SqlConstant.GREATER_THAN_OR_EQUAL:
					sign = " >= ";
					break;
				case SqlConstant.LESS_THAN:
					sign = " < ";
					break;
				case SqlConstant.LESS_THAN_OR_EQUAL:
					sign = " <= ";
					break;
				case SqlConstant.NULL://空值
					sign = " IS NULL";
					value = "";
					break;
				case SqlConstant.NOT_NULL:
					sign = " IS NOT NULL";
					value = "";
					break;
				case SqlConstant.CONTAIN:
					sign = " LIKE ";
					value = "'%" + val + "%'";
					break;
				case SqlConstant.NOT_CONTAIN:
					sign = " NOT LIKE ";
					value = "'%" + val + "%'";
					break;
				default:
					sign = "=";
					break;
			}
		}
		return key + sign + value;
	}
}
