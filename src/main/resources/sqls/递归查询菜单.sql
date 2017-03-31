#向下递归查询菜单的子菜单,传入单个菜单id
DROP FUNCTION IF EXISTS func_query_children_menu;
CREATE FUNCTION func_query_children_menu(in_menu_id INT)
RETURNS VARCHAR(4000)
BEGIN
	DECLARE sTemp VARCHAR(4000);
	DECLARE sTempChd VARCHAR(4000);

	SET sTemp='';
	SET sTempChd = CAST(in_menu_id AS CHAR);

	WHILE sTempChd IS NOT NULL DO
		SET sTemp = CONCAT(sTemp,',',sTempChd);
		SELECT GROUP_CONCAT(menu_id) INTO sTempChd FROM sys_menu WHERE FIND_IN_SET(menu_pid, sTempChd) > 0;
	END WHILE;
	RETURN substring(sTemp, 2);
END;

#调用
#SELECT * FROM sys_menu where FIND_IN_SET(menu_id, func_query_children_menu(2));

#向上递归查询菜单的父菜单,出入单个菜单id
DROP FUNCTION IF EXISTS func_query_parents_menu;
CREATE FUNCTION func_query_parents_menu(in_menu_id INT)
RETURNS VARCHAR(4000)
BEGIN
	DECLARE sTemp VARCHAR(4000);
	DECLARE sTempChd VARCHAR(4000);

	SET sTemp='';
	SET sTempChd = CAST(in_menu_id AS CHAR);
	SET sTemp = CONCAT(sTemp,',',sTempChd);

	SELECT menu_pid INTO sTempChd FROM sys_menu WHERE menu_id = sTempChd;
	WHILE sTempChd <> 0 DO
		SET sTemp = CONCAT(sTemp,',',sTempChd);
		SELECT menu_pid INTO sTempChd FROM sys_menu WHERE menu_id = sTempChd;
	END WHILE;
RETURN substring(sTemp, 2);
END;

#调用
#SELECT * FROM sys_menu where FIND_IN_SET(menu_id, func_query_parents_menu(14));


#向下递归查询菜单的子菜单,传入多个菜单id
DROP FUNCTION IF EXISTS func_query_children_menus;
CREATE FUNCTION func_query_children_menus(in_menu_ids VARCHAR(1000))
RETURNS VARCHAR(4000)
BEGIN
	DECLARE sTemp VARCHAR(4000);
	DECLARE sTempChd VARCHAR(4000);

	SET sTemp='';
	SET sTempChd = in_menu_ids;

	WHILE sTempChd IS NOT NULL DO
		SET sTemp = CONCAT(sTemp,',',sTempChd);
		SELECT GROUP_CONCAT(menu_id) INTO sTempChd FROM sys_menu WHERE FIND_IN_SET(menu_pid, sTempChd) > 0;
	END WHILE;
	RETURN substring(sTemp, 2);
END;

#调用
#SELECT * FROM sys_menu where FIND_IN_SET(menu_id, func_query_children_menus('2,6'));

#向上递归查询菜单的父菜单,传入多个菜单id
DROP FUNCTION IF EXISTS func_query_parents_menus;
CREATE FUNCTION func_query_parents_menus(in_menu_ids VARCHAR(1000))
RETURNS VARCHAR(4000)
BEGIN
	DECLARE sTemp VARCHAR(4000);
	DECLARE sTempChd VARCHAR(4000);
	DECLARE sLth INT;
	DECLARE i INT;
	DECLARE id VARCHAR(10);

	SET sLth = length(in_menu_ids) - length(replace(in_menu_ids, ',', '')) + 1;#ids以','切割的
	SET sTemp='';
	SET sTempChd = '';
	SET i = 1;

	WHILE i <= sLth DO
		SET id = reverse(substring_index(reverse(substring_index(in_menu_ids, ',', i)), ',', 1));
		SELECT func_query_parents_menu(id+0) INTO sTempChd;
		SET sTemp = CONCAT(sTemp,',',sTempChd);
		SET i = i + 1;
	END WHILE;
RETURN substring(sTemp, 2);
END;

#调用
#SELECT * FROM sys_menu where FIND_IN_SET(menu_id, func_query_parents_menus('14'));
#SELECT func_query_parents_menus('13,14,15');
#SELECT func_query_parents_menu(15);
#SELECT length('14') - length(replace('14', ',', '')) + 1;
#SELECT reverse(substring_index(reverse(substring_index('14', ',', 1)), ',', 1));
