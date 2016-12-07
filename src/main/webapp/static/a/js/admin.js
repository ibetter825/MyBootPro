/**
 * 后台封装组件
 * version: 1.0.0
 */
!(function(){
	"use strict";
	/**
     * 工具
     */
    window.tool = {
    	/**
    	 * 获取尺寸
    	 * @returns
    	 */
    	dimension: function(tar){
    		let h = 0, w = 0;
	        if(tar){
	        	if(tar.indexOf('.') == 0){//类
	        		
	        	}else if(tar.indexOf('#') == 0){//id
	        		
	        	}
	        }else{
	        	h = document.documentElement.clientHeight || document.body.clientHeight;
		        w = document.documentElement.clientWidth || document.body.clientWidth;
	        }
	        return {width: w, height: h};
    	},
        /**
         * 合并对象，将子对象合并到父对象中
         * f: 默认父对象
         * c: 子对象
         */
        merge: function(f, c){
            for(let p in c){
                if(f[p] != undefined)
                    f[p] = c[p];
            }
        },
        /**
         * 复制并合并一个对象
         * o: 需要复制的对象
         * c: 合并的子对象
         */
        copyAndMerge: function(o, c){
            if(c == undefined) c = {};
            let n = {};
            for(let k in o){
                if(c[k] != undefined){
                    n[k] = c[k];
                    delete c[k];
                }else
                    n[k] = o[k];
            }
            for(let k in c){
                if(n[k] == undefined)
                    n[k] = c[k];
            }
            return n;
        },
        /**
         * 复制一个对象
         */
        copy: function(o){
            let n = {};
            for(let k in o)
                n[k] = o[k];
            return n;
        },
        /**
         * 向对象中添加元素
         * o: 需要添加元素的对象
         * c: 被添加的元素对象
         */
        put: function(o, c){
            if(o == undefined) o ={};
            if(c == undefined) c = {};
            for(let k in c)
                o[k] = c[k];
            return o;
        },
        /**
         * 添加节点
         * f: 父节点
         * c: classname
         */
        create: function(f, c){
                let d = document.createElement('div');
                    d.className = c;
                    f.appendChild(d);
        }
    };
    
	 window.admin = {};
	 window.config = {};
	 /**
	  * 默认表格等配置
	  */
	 config.datagrid = {
		 url: null, //后台请求连接
		 total: 0,
		 rows: [],
		 footer: [],
		 pageSize: 20,
		 pageNumber: 1,
		 totalPages: 1,
		 tools: [], //弹出层的类型
   		 columns: [], //计数器 默认0
   		 loading: false, //是否显示加载中gif
   		 reload: false, //重新加载当前页面
         pagination: true,//是否显示分页工具栏，默认显示
         pageList: [10, 20, 50, 100, 500], //设置分页属性时初始化页面大小选择列表
         onClickRow: null, //datagrid点击行时的事件, 默认null  参数 index,row,event
         onClickCell: null //单击列
	 }
	 /**
     * admin datagrid store
     */
    const s_datagrid = {
        state: {
            options: config.datagrid
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
            }
        }
    };
	 /**
	  * admin search store
	  */
	 const s_search = {
		        state: {
		            nodes: null, //格式 {name: {selected: {}, show: true}}
		            config: null
		        },
		        mutations: {
		        	a_s_config(state, config) {
		                state.config = config;
		            },
		            a_s_nodes(state, nodes) {
		                state.nodes = nodes;
		            }
		        }
		    };
    /**
     * admin store
     */
    const store = new Vuex.Store({
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
	 const CompAdminTools = {
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
	 const CompAdminTable = {
        props: ['props'],
        template: `
        			<div class="tablecont">
	        			<table class="tablelist" :style="props.table.bg">
				            <thead>
				                <tr>
				                    <th v-for="col in options.columns" :style="style(col, 'th')">
				                    	<input v-if="col.checkbox" name="checkedAll" @click.stop="checkAll()" type="checkbox" value="" v-model="checkedAll"/>
				                    	{{ col.title }}	<i v-if="col.sortable" class="sort"><img src="/static/a/images/px.gif" /></i>
				                    </th>
				                </tr>
				            </thead>
				            <tbody>
				                <tr v-for="(row, ri) in options.rows" @click.stop="clickRow(ri, row, $event)" :class="[ri % 2 == 0 ? 'odd' : '', selecteds[ri]]">
				                	<td v-for="(col, ci) in options.columns" :style="style(col, 'td')">
				                		<span v-if="col.checkbox"><input :name="col.field" type="checkbox" :value="row[col.field]" v-model="checkeds" /></span>
				                		<span v-else @click.stop="clickCell(ri, col.field, row[col.field], $event, row)" v-html="formatter(row[col.field], row, ri, ci)"></span>
				                	</td>
				                </tr>
				            </tbody>
				        </table>
				        <div v-show="options.loading" class="loading"></div>
			        </div>`,
        data: function(){
            return {
            	checkedAll: false,
            	checkeds:　[],
            	selecteds: []
            };
        },
        computed: {
        	options(){
        		return this.$store.state.datagrid.options;
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
        	 * 全选
        	 */
        	checkAll:　function(){
        		let checkedAll = this.checkedAll;
        		if(checkedAll){
        			this.checkedAll = false;
        			this.checkeds = []; 
        			this.selecteds = [];
        		}else{
        			this.checkedAll = true;
        			let rows = this.options.rows;
        			this.checkeds = [];
        			let col = this.options.columns[0];
        			for(let i in rows){
        				this.checkeds.push(rows[i][col.field]);
        				this.selecteds[i] = 'selected';
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
        			let checkeds = this.checkeds;
            		let i = checkeds.indexOf(pk);
            		if(i == -1){
            			checkeds.push(pk);
            			this.selecteds[index] = 'selected';
            			if(size <= checkeds.length)
            				this.checkedAll = true;
            		} else {
            			checkeds.splice(i, 1);
            			this.selecteds[index] = '';
            			this.checkedAll = false;
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
	 const CompAdminPager = {
        props: ['props'],
        template: `<div class="pagin" v-if="options.pagination">
				        <div class="message">共<i class="blue">{{ options.total }}</i>条记录，当前显示第&nbsp;<i class="blue">{{ options.pageNumber }}&nbsp;</i>页，每页显示
				        &nbsp;<select v-model="options.pageSize">
				        		<option v-for="l in options.pageList" :value="l" :selected="[l == options.pageSize ? 'selected' : '']">{{ l }}</option>
				        	</select>&nbsp;条记录
				        </div>
				        <ul class="paginList">
				        	<li class="paginItem"><a href="javascript:;" @click="refresh()"><span class="pagerefresh"></span></a></li>
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
        		}
        	},
        	refresh(){
        		this.options.reload = true;//重新加载
        	}
        }
	};
	
	let vm_search = null;
	/**
	 * 搜索表单
	 */
	admin.searchVue = function(){
		if(vm_search) return;
		vm_search = new Vue({
	        el: '.search',
	        store,
	        template: `<div class="search">
							<form :action="config.action" method="post">
								<ul class="seachform1">
							        <li v-for="(c, index) in columns">
							        	<label>{{ c.label }}</label>
							        	<input v-if="c.type == 'text'" :name="c.name" type="text" class="scinput1">
							            <div v-else class="vocation">
								            <div class="uew-select" @click="sclick(c)" @mouseleave="sleave(c)">
								            	<div class="uew-select-value ue-state-default" style="width: 125px;">
							        				<input :name="c.name+'_text'" :value="nodes[c.name].selected.text" class="scinput2" readonly="true">
								            		<input :name="c.name" type="hidden" :value="nodes[c.name].selected.value">
							        				<em class="uew-icon uew-icon-triangle-1-s"></em></div>
							        				<ul v-show=" nodes[c.name].show" class="pretty-select">
											            <li v-for="o in c.options" :class="[o.value === nodes[c.name].selected.value ? 'selected' : '']" @click.stop="schange(o, c)">{{ o.text }}</li>
										            </ul>
									            </div>
							            </div>
							        </li>
							        <div class="cl"></div>
						        </ul>
						        <ul class="seachform1 cl">
						        	<li class="sarchbtn"><label>&nbsp;</label><input name="" @click="submit()" type="button" class="scbtn1" value="查询">   <input name="" type="reset" class="scbtn" value="重置"></li><div class="cl"></div>  
						        </ul>
						    </form>
				        </div>`,
	        computed: {
	        	search(){
	        		return this.$store.state.search;
	        	},
	        	nodes(){
	        		return this.search.nodes;
	        	},
	        	config(){
	        		return this.search.config;
	        	},
	        	columns(){
	        		return this.config.columns;
	        	}
	        },
	        methods: {
	        	sclick: function(column){
	        		this.nodes[column.name].show = true;
	        	},
	        	sleave: function(column){
	        		this.nodes[column.name].show = false;
	        	},
	        	schange: function(option, column){
	        		this.nodes[column.name].selected = option;
	        		this.nodes[column.name].show = false;
	        	},
	        	submit: function(){
	        		let func = this.config.submit;
	        		if(func) func();
	        	}
	        },
	        mounted: function(){
	        	//创建好以后修改grid的高度
	        	let search = document.querySelector('.search');
	        	let ch = this.$store.state.client.height;//屏幕高度
	        	let sh = search.clientHeight; //搜索框高度
	        	let tbody = document.querySelector('.tablelist > tbody');
	        	//-place -search -formtitle -tools -thead -pagin -formbody 的 padding -与底部的距离
	        	tbody.style.height = (ch - 40 - 40 - sh - 36 - 35 - 35 - 40) + 'px';
	        }
	    });
	}
		
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
	        components: {
	        	'data-tools': CompAdminTools,
	            'data-table': CompAdminTable,
	            'data-pager': CompAdminPager
	        },
	        template: `<div id="datagrid">
	        				<data-tools :props="props"></data-tools>
	                        <data-table :props="props"></data-table>
	                        <data-pager :props="props"></data-pager>
	                    </div>`,
	        computed: {
	            props(){//传递给子组件的属性值
	            	return {
	            		table: {
	            			loading: false
	            		},
	            		pager: {
	            			disable: false
	            		}
	    			}
	            },
	            options(){
	            	return this.$store.state.datagrid.options;
	            }
	        },
	        watch: {
	        	'options.pageNumber': function(n, o){
	        		this.loadData();
	        	},
	        	'options.pageSize': function(){
	        		this.loadData();
	        	},
	        	'options.reload': function(n){
	        		if(n) {
	        			this.loadData();
	        			this.options.reload = false;
	        		}
	        	},
	        },
	        methods: {
	        	loadData: function(n){
	        		let url = this.options.url;
	        		this.options.loading = true;
	        		this.props.pager.disable = true;
	        		// GET /someUrl
				    this.$http.post(url, {'page': this.options.pageNumber, 'size': this.options.pageSize}).then(function(resp){
				    	var page = resp.body;
				    	//success
				    	tool.merge(this.options, page);
				    	this.props.table.bg = '';
				    	this.props.pager.disable = false;
				    	this.options.loading = false;
				    }, function(resp){
				    	//error
				    	this.props.pager.disable = false;
				    });
	        	}
	        },
	        created:　function(){
	        	let url = this.options.url;
	        	if(url){
	        		this.loadData(this.options.pageNumber);
	        	}
	        },
	        mounted: function(){

	        }
	    });
	}
	//datagrid
	admin.datagrid = function(options){
		store.commit('a_options', tool.copyAndMerge(config.datagrid, options));
		admin.datagridVue();
	}
	//搜索表单
	admin.search = function(search){
		let opts = null, nodes = {}, columns = search.columns;
		for(let i in columns){
			if(columns[i].type === 'select'){
				nodes[columns[i].name] = {};
				nodes[columns[i].name].selected = {value:'', text:''};
				nodes[columns[i].name].show = false;
				
				opts = columns[i].options;
				for(let j in opts){
					if(opts[j]['selected']){
						nodes[columns[i].name].selected = opts[j];
						break;
					}
				}
			}
		}
		store.commit('a_s_nodes', nodes);
		store.commit('a_s_config', search);
		admin.searchVue();
	}
})();