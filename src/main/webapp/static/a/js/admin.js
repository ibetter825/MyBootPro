/**
 * 后台封装组件
 * version: 1.0.0
 */
!(function(){
	"use strict";
	 window.admin = {};
	 
	 
	 /**
     * admin datagrid store
     */
    const s_datagrid = {
        state: {
            tools: [], //弹出层的类型
            columns: [], //计数器 默认0
            pager: {}
        },
        mutations: {
            a_tools(state, tools) {
                state.tools = tools;
            },
            a_columns(state, columns) {
                state.columns = columns;
            },
            a_pager(state, pager) {//获取当前指定的视图
                state.pager = pager;
            }
        }
    };
    
    /**
     * admin store
     */
    const store = new Vuex.Store({
        modules: {
            datagrid: s_datagrid
        }
    });
	 
	 /**
	  * 工具条
	  */
	 const CompAdminTools = {
        props: ['props'],
        template: `<div v-if="tools.length > 0" class="tools">
			            <ul class="toolbar">
			                <li v-for="tool in tools"><span><img :src="tool.icon" /></span>{{ tool.title }}</li>
			            </ul>
			        </div>`,
        data: function(){
            return {};
        },
        computed: {
        	tools(){
        		return this.$store.state.datagrid.tools;
        	}
        },
        watch: {

        }
	};
	
	 /**
	  * 列表
	  */
	 const CompAdminTable = {
        props: ['props'],
        template: `<table class="tablelist">
			            <thead>
		                <tr>
		                    <th v-for="col in columns">
		                    	<input v-if="col.checkbox" name="" type="checkbox" value=""/>
		                    	{{ col.title }}	<i v-if="col.sortable" class="sort"><img src="/static/a/images/px.gif" /></i>
		                    </th>
		                </tr>
		            </thead>
		            <tbody>
		                <tr>
		                    <td><input name="" type="checkbox" value="" /></td>
		                    <td>20130908</td>
		                    <td>王金平幕僚：马英九声明字字见血 人活着没意思</td>
		                    <td>admin</td>
		                    <td>江苏南京</td>
		                    <td>2013-09-09 15:05</td>
		                    <td>已审核</td>
		                    <td><a href="#" class="tablelink">查看</a> <a href="#" class="tablelink"> 删除</a></td>
		                </tr>
		            </tbody>
		        </table>`,
        data: function(){
            return {};
        },
        computed: {
        	columns(){
        		return this.$store.state.datagrid.columns;
        	}
        },
        watch: {

        }
	};
	 
	 /**
	  * 分页
	  */
	 const CompAdminPager = {
        props: ['props'],
        template: `<div class="pagin">
				        <div class="message">共<i class="blue">1256</i>条记录，当前显示第&nbsp;<i class="blue">2&nbsp;</i>页</div>
				        <ul class="paginList">
				            <li class="paginItem"><a href="javascript:;"><span class="pagepre"></span></a></li>
				            <li class="paginItem"><a href="javascript:;">1</a></li>
				            <li class="paginItem current"><a href="javascript:;">2</a></li>
				            <li class="paginItem"><a href="javascript:;">3</a></li>
				            <li class="paginItem"><a href="javascript:;">4</a></li>
				            <li class="paginItem"><a href="javascript:;">5</a></li>
				            <li class="paginItem more"><a href="javascript:;">...</a></li>
				            <li class="paginItem"><a href="javascript:;">10</a></li>
				            <li class="paginItem"><a href="javascript:;"><span class="pagenxt"></span></a></li>
				        </ul>
				    </div>`,
        data: function(){
            return {};
        },
        computed: {

        },
        watch: {

        }
	};
	
	 
	 /**
     * 静态datagrid vm实例
     * 所有弹出层都调用此实例
     */
    let vm_datagrid = null;
	admin.datagridVue = function(){
		if(vm_datagrid) return;
		vm_datagrid = new Vue({
	        el: '#datagrid',
	        // 把 store 对象提供给 “store” 选项，这可以把 store 的实例注入所有的子组件
            store,
	        data: {

	        },
	        components: {
	        	'data-tools': CompAdminTools,
	            'data-table': CompAdminTable,
	            'data-pager': CompAdminPager
	        },
	        template: `<div class="rightinfo" id="datagrid">
	        				<data-tools :props="props"></data-tools>
	                        <data-table :props="props"></data-table>
	                        <data-pager :props="props"></data-pager>
	                    </div>`,
	        computed: {
	            props(){//传递给子组件的属性值
	            	return {
	            		
	    			}
	            }
	        },
	        watch: {

	        },
	        methods: {

	        },
	        mounted: function(){

	        }
	    });
	}
	
	admin.datagrid = function(tools, columns, pager){
		admin.datagridVue();
		store.commit('a_tools', tools);
		store.commit('a_columns', columns);
	}
})();