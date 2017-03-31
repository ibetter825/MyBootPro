/*超级管理员拥有所有权限
1.用户 - 菜单 - 操作
2.用户 - 角色 - 菜单 - 操作
3.用户 - 分组 - 菜单 - 操作
				 上级可以给下级分配其拥有的菜单和操作
4.用户 - 分组 - 角色 - 菜单 - 操作
				 上级可以创建角色并给角色分配其拥有的菜单和操作 再将角色赋予下级
*/
/*
数据
1.新建用户:admin,超级管理员
2.新建角色:超级管理员，系统管理员，分组管理员(管理分组)
3.新建分组:分组以及两个子分组
4.给id为14的菜单添加:新增与编辑两个按钮
*/
/*
查询
1.用户登录时查询其所有权限的并集，然后以menu_id:opt_id的格式存到ss中
*/
#查询用户对应的所有操作
SELECT
	opt.*, CONCAT(opt.menu_id, ':', opt.opt_id) AS rit
FROM
	sys_opt opt
WHERE
	opt.opt_id IN (
		SELECT
			r.opt_id
		FROM
			sys_right r
		WHERE
			r.role_id IN (
				SELECT
					ur.role_id
				FROM
					sys_user_role ur
				WHERE
					ur.user_id = 10001
			)
		OR r.group_id IN (
			SELECT
				ug.group_id
			FROM
				sys_user_group ug
			WHERE
				ug.user_id = 10001
		)
	)