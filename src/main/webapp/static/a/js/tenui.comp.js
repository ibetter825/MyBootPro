/**
 * 后台封装组件
 * version: 1.0.0
 */
!(function(){
	"use strict";
	 window.tenui = {};
	 tenui.config = {};
	 /**
	  * 默认表格等配置
	  */
	 tenui.config.datagrid = {
		 url: null, //后台请求连接
		 method: 'post', //请求远程数据的方法
		 queryParams: {}, //请求后台的参数
		 total: 0,
		 order: {}, //{'or["menu_id"]': 'desc', 'or["menu_seq"]': 'desc'}
		 rows: [],
		 footer: [],
		 pageSize: 20,
		 pageNumber: 1,
		 totalPages: 1,
		 tools: [], //弹出层的类型
   		 columns: [], //字段
   		 singleSelect: false, //是否只允许选择一行
   		 loading: false, //是否显示加载中gif
   		 reload: false, //重新加载当前页面
         pagination: true,//是否显示分页工具栏，默认显示
         pageList: [10, 20, 50, 100, 500], //设置分页属性时初始化页面大小选择列表
         onClickRow: null, //datagrid点击行时的事件, 默认null  参数 index,row,event
         onClickCell: null //单击列
	 }
	 /**
     * tenui datagrid store
     */
    const s_datagrid = {
        state: {
            options: tenui.config.datagrid,
            depot: {
            	checkedAll: false,
            	checkeds: [],
            	selecteds: []
            } //存储数据
        },
        mutations: {
        	a_options(state, options) {
                //直接传options对象
                if(options instanceof Array){
                    let opt = options[0];
                    for(let k in opt)
                        state.options[k] = opt[k];
                }else
                    state.options = options;
            },
            a_depot_reset(state) {
                state.depot = {
                	checkedAll: false,
                	checkeds: [],
                	selecteds: []
                }
            }
        }
    };
	 /**
	  * tenui search store
	  */
	 const s_search = {
		        state: {
		            nodes: null, //格式 {name: {selected: {}, show: true}}
		            params: null, //格式 {field:''}
		            config: null,
		            init: {}, //存储初始化数据
		        },
		        mutations: {
		        	a_s_config(state, config) {
		                state.config = config;
		            },
		            a_s_nodes(state, nodes) {
		                state.nodes = nodes;
		                state.init.nodes = tool.copy(nodes);
		            },
		            a_s_params(state, params) {
		                state.params = params;
		                state.init.params = tool.copy(params);
		            },
		            a_s_reset(state) {//重置表单数据
		                state.params = tool.copy(state.init.params);
		                state.nodes = tool.copy(state.init.nodes);
		            }
		        }
		    };
    /**
     * tenui store
     */
	 tenui.store = new Vuex.Store({
    	state: {
        	client: tool.dimension()//获取当前窗口的宽高 
    	},
        modules: {
            datagrid: s_datagrid,
            search: s_search
        }
    });
    //解决vue-resource异步请求时后台取不到参数的方法
    Vue.http.options.emulateJSON = true;
	 /**
	  * 工具条
	  */
    tenui.CompTenuiTools = {
        props: ['props'],
        template: `<div v-if="options.tools.length > 0" class="tools">
			            <ul class="toolbar">
			                <li v-for="tool in options.tools" @click="click(tool)"><span><img :src="tool.icon" /></span>{{ tool.title }}</li>
			            </ul>
			        </div>`,
        data: function(){
            return {};
        },
        computed: {
        	options(){
        		return this.$store.state.datagrid.options;
        	}
        },
        methods: {
        	click: function(tool){
        		let handler = tool['handler'];
        		if(handler)
        			handler();
        	}
        },
        watch: {

        }
	};
	
	 /**
	  * 列表
	  */
    tenui.CompTenuiTable = {
        props: ['props'],
        template: `
        			<div class="tablecont">
	        			<table class="tablelist" :style="props.table.bg">
				            <thead>
				                <tr>
				                    <th v-for="col in options.columns" :style="style(col, 'th')">
				                    	<input v-if="col.checkbox && !options.singleSelect" name="checkedAll" @click.stop="checkAll()" type="checkbox" value="" v-model="depot.checkedAll"/>
				                    	{{ col.title }}	<i v-if="col.sortable" class="sort" @click="order(col.field)"><img src="/static/a/images/px.gif" /></i>
				                    </th>
				                </tr>
				            </thead>
				            <tbody>
				                <tr v-for="(row, ri) in options.rows" @click.stop="clickRow(ri, row, $event)" :class="[ri % 2 == 0 ? 'odd' : '', depot.selecteds[ri]]">
				                	<td v-for="(col, ci) in options.columns" :style="style(col, 'td')">
				                		<span v-if="col.checkbox"><input :name="col.field" type="checkbox" :value="row[col.field]" v-model="depot.checkeds" /></span>
				                		<span v-else @click.stop="clickCell(ri, col.field, row[col.field], $event, row)" v-html="formatter(row[col.field], row, ri, ci)"></span>
				                	</td>
				                </tr>
				            </tbody>
				        </table>
				        <div v-show="options.loading" class="loading"></div>
			        </div>`,
        data: function(){
            return {
            	
            };
        },
        computed: {
        	options(){
        		return this.$store.state.datagrid.options;
        	},
        	depot(){
        		return this.$store.state.datagrid.depot;
        	}
        },
        methods: {
        	/**
        	 * 格式化数据, 默认直接返回值
        	 */
        	formatter: function(value, row, ri, ci){
        		let fmt = this.options.columns[ci]['formatter'];
        		if(fmt)
        			return fmt(value, row, ri);
        		return value;
        	},
        	/**
        	 * 样式
        	 */
        	style: function(column, tp){
        		let style = {};
        		if(tp === 'th') {
        			if(column.halign)
            			style.textAlign = column.halign;
        			else if(column.align)
            			style.textAlign = column.align;
        		} else {
        			if(column.align)
            			style.textAlign = column.align;
        		}
        		
        		let width = column.width;
        		if(width != undefined){
        			if(typeof width == 'number')
        				style.width = width + 'px';
        			else
        				style.width = width;
        		}else
        			style.width = '100px';
        		return style;
        	},
        	/**
        	 * th与td的宽度
        	 */
        	width: function(column){
        		return column.width == undefined ? '' : column.width;
        	},
        	/**
        	 * 排序
        	 */
        	order: function(field){
        		let order = this.options.order;
        		let key = 'or[\'' + field + '\']';
        		let val = order[key];
        		if(val === 'asc')
        			delete order[key];
        		else{
        			if(val == undefined)
            			val = 'desc';
            		else if(val === 'desc')
            			val = 'asc';
        			order[key] = val;
        		}
        		this.options.reload = true;//重新加载
        	},
        	/**
        	 * 全选
        	 */
        	checkAll:　function(){
        		let checkedAll = this.depot.checkedAll;
        		if(checkedAll){
        			this.depot.checkedAll = false;
        			this.depot.checkeds = []; 
        			this.depot.selecteds = [];
        		}else{
        			this.depot.checkedAll = true;
        			let rows = this.options.rows;
        			this.depot.checkeds = [];
        			let col = this.options.columns[0];
        			for(let i in rows){
        				this.depot.checkeds.push(rows[i][col.field]);
        				this.depot.selecteds[i] = 'selected';
        			}
        		}
        	},
        	/**
        	 * 单击行
        	 */
        	clickRow: function(index, row, event){
        		let clickRow = this.options.onClickRow;
        		if(clickRow) clickRow(index, row, event);
        		
        		//默认点击行事件需要选中复选框
        		let col = this.options.columns[0];
        		let cb = col.checkbox;
        		if(cb){
        			let size = this.options.rows.length;
        			let pk = row[col.field]; //复选框的字段
        			if(this.options.singleSelect){
        				this.depot.checkeds = [];
        				this.depot.selecteds = [];
            			this.depot.checkedAll = false;
        			}
        			let checkeds = this.depot.checkeds;
            		let i = checkeds.indexOf(pk);
            		if(i == -1){
            			checkeds.push(pk);
            			this.depot.selecteds[index] = 'selected';
            			if(size <= checkeds.length)
            				this.checkedAll = true;
            		} else {
            			checkeds.splice(i, 1);
            			this.depot.selecteds[index] = '';
            			this.depot.checkedAll = false;
            		}
        		}
        	},
        	/**
        	 * 单击列
        	 */
        	clickCell: function(index, field, value, event, row){
        		let clickCell = this.options.onClickCell;
        		if(clickCell) clickCell(index, field, value, event);
        		//不论有没有列点击事件都需要调用行点击事件
        		this.clickRow(index, row, event);
        	}
        },
        watch: {

        },
        beforeCreate: function(){
        	console.log('实例创建之前');
        },
        created: function(){
        	
        }
	};
	 
	 /**
	  * 分页
	  */
    tenui.CompTenuiPager = {
        props: ['props'],
        template: `<div class="pagin" v-if="options.pagination">
				        <div class="message">共<i class="blue">{{ options.total }}</i>条记录，当前显示第&nbsp;<i class="blue">{{ options.pageNumber }}&nbsp;</i>页，每页显示
				        &nbsp;<select v-model="options.pageSize">
				        		<option v-for="l in options.pageList" :value="l" :selected="[l == options.pageSize ? 'selected' : '']">{{ l }}</option>
				        	</select>&nbsp;条记录
				        </div>
				        <ul class="paginList">
				        	<li class="paginItem"><a href="javascript:;" @click="refresh()"><span class="pagerfrh"></span></a></li>
				            <li v-if="options.pageNumber > 1" class="paginItem"><a href="javascript:;" @click="change(0, -1)"><span class="pagepre"></span></a></li>
				            <li v-if="options.pageNumber > 3" class="paginItem"><a href="javascript:;" @click="change(1)">首页</a></li>
				            <li v-if="options.pageNumber > 3" class="paginItem more"><a href="javascript:;">...</a></li>
				            <li class="paginItem" v-for="n in count.front"><a href="javascript:;" @click="change(n)">{{ n }}</a></li>
				            <li class="paginItem current"><a href="javascript:;" @click="change(count.middle)">{{ count.middle }}</a></li>
				            <li class="paginItem" v-for="n in count.back"><a href="javascript:;" @click="change(n)">{{ n }}</a></li>
				            <li v-if="options.pageNumber < options.totalPages - 2" class="paginItem more"><a href="javascript:;">...</a></li>
				            <li v-if="options.pageNumber < options.totalPages - 2" class="paginItem"><a href="javascript:;" @click="change(options.totalPages)">末页</a></li>
				            <li v-if="options.pageNumber < options.totalPages" class="paginItem"><a href="javascript:;" @click="change(0, 1)"><span class="pagenxt"></span></a></li>
				        </ul>
				    </div>`,
        data: function(){
            return {};
        },
        computed: {
        	options(){
        		return this.$store.state.datagrid.options;
        	},
        	count(){
        		let pn = this.options.pageNumber;
        		let tp = this.options.totalPages;
        		let start = pn - 2 < 1 ? 1 : pn - 2, end = pn + 2 > tp ? tp : pn + 2;
        		if(tp >= 5){
        			let dv = end - start;
        			let c = 0;
            		switch(dv){
            			case 2:
            				c = 2;
            				break;
            			case 3:
            				c = 1;
            				break;
            		}
            		if(start == 1) end = end + c > tp ? tp : end + c;
    				else if(end == tp) start = start - c < 1 ? 1 : start - c;
        		}
        		
        		let front = [], back = [];
        		for(let i = start; i < pn; i++)
        			front.push(i);
        		for(let i = pn + 1; i <= end; i++)
        			back.push(i);
        		return {
        			front: front,
        			back: back,
        			middle: pn
        		};
        	}
        },
        methods: {
        	change: function(number, d){
        		if(!this.props.pager.disable){
        			if(number != 0) this.options.pageNumber = number;
        			else this.options.pageNumber += d;
        			this.options.reload = true;//重新加载
        		}
        	},
        	refresh(){
        		this.options.reload = true;//重新加载
        	}
        }
	};
    
	 /**
	  * select
	  */
    tenui.CompTenuiSelect = {
        props: ['select', 'column'],
        template: `<div class="uew-select" @click="sclick(column)" @mouseleave="sleave(column)">
				    	<div class="uew-select-value ue-state-default" style="width: 125px;">
						<input :name="column.name+'_text'" :value="select.nodes[column.name].selected.text" class="scinput2" readonly="true">
						<input :name="column.name" type="hidden" v-model="select.params[column.name]">
						<em class="uew-icon uew-icon-triangle-1-s"></em>
					</div>
					<ul v-show=" select.nodes[column.name].show" class="pretty-select">
				        <li v-for="o in column.options" :class="[o.value === select.nodes[column.name].selected.value ? 'selected' : '']" @click.stop="schange(o, column)">{{ o.text }}</li>
				    </ul>
				</div>`,
        methods: {
        	sclick: function(column){
        		this.select.nodes[column.name].show = true;
        	},
        	sleave: function(column){
        		this.select.nodes[column.name].show = false;
        	},
        	schange: function(option, column){
        		this.select.nodes[column.name].selected = option;
        		this.select.params[column.name] = option.value;
        		this.select.nodes[column.name].show = false;
        	}
        }
	};
	 /**
	  * tree
	  */
    tenui.CompTenuiTree = {
        props: ['props'],
        template: `<div class="uew-select">
				    	<div class="uew-select-value ue-state-default" style="width: 125px;">
						<input class="scinput2" readonly="true">
						<input type="hidden">
						<em class="uew-icon uew-icon-triangle-1-s"></em>
					</div>
					<ul class="pretty-tree">
				        <li class="tree-root">
				        	<p><span class="tree-icon tree-expanded">
				        		</span><span class="tree-icon tree-folder">
				        		</span><span class="tree-icon tree-checkbox0">
				        		</span><span class="tree-title">Parent
				        		</span>
				        	</p>
				        	<ul>
				        		<li>
				        			<p><input type="checkbox"/><span>Child01</span></p>
				        			<ul>
						        		<li>
						        			<p><input type="checkbox"/><span>Child01_01</span></p>
						        		</li>
						        		<li>
						        			<p><input type="checkbox"/><span>Child01_02</span></p>
						        		</li>
						        	</ul>
				        		</li>
				        		<li>
				        			<p><input type="checkbox"/><span>Child02</span></p>
				        		</li>
				        	</ul>
				        </li>
				        <li class="tree-root">
				        	<p><input type="checkbox"/><span>Parent</span></p>
				        </li>
				    </ul>
				</div>`,
        methods: {
        	
        }
	};
})();