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
	 
	 /**
	  * 默认表格等配置
	  */
	 admin.datagrid = {
		 url: null, //后台请求连接
		 data: {
			 total: 0,
			 rows: [],
			 footer: null
		 }, // 数据
		 tools: [], //弹出层的类型
   		 columns: [], //计数器 默认0
         pager: {}, //分页栏
         onClickRow: null, //datagrid点击行时的事件, 默认null  参数 index,row,event
         onClickCell: null //单击列
	 }
	 /**
     * admin datagrid store
     */
    const s_datagrid = {
        state: {
            options: admin.datagrid
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
        template: `<div v-if="options.tools.length > 0" class="tools">
			            <ul class="toolbar">
			                <li v-for="tool in options.tools"><span><img :src="tool.icon" /></span>{{ tool.title }}</li>
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
		                    <th v-for="col in options.columns" :style="style(col, 'th')">
		                    	<input v-if="col.checkbox" name="checkedAll" @click.stop="checkAll()" type="checkbox" value="" v-model="checkedAll"/>
		                    	{{ col.title }}	<i v-if="col.sortable" class="sort"><img src="/static/a/images/px.gif" /></i>
		                    </th>
		                </tr>
		            </thead>
		            <tbody>
		                <tr v-for="(row, ri) in options.data.rows" @click.stop="clickRow(ri, row, $event)" :class="[ri % 2 == 0 ? 'odd' : '', selecteds[ri]]">
		                	<td v-for="(col, ci) in options.columns" :style="style(col, 'td')">
		                		<span v-if="col.checkbox"><input :name="col.field" type="checkbox" :value="row[col.field]" v-model="checkeds" /></span>
		                		<span v-else @click.stop="clickCell(ri, col.field, row[col.field], $event, row)" v-html="formatter(row[col.field], row, ri, ci)"></span>
		                	</td>
		                </tr>
		            </tbody>
		        </table>`,
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
        	},
        	client(){
        		return tool.dimension();//获取当前窗口的宽高
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
        			let rows = this.options.data.rows;
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
        			let size = this.options.data.rows.length;
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
	
	let vm_search = null;
	/**
	 * 搜索表单
	 */
	admin.searchVue = function(searchs){
		if(vm_search) return;
		vm_search = new Vue({
	        el: '.search',
	        template: `<div class="search">
							<form action="" method="post">
								<ul class="seachform1">
							        <li v-for="(s, index) in searchs">
							        	<label>{{ s.label }}</label>
							        	<input v-if="s.type == 'text'" :name="s.name" type="text" class="scinput1">
							            <div v-else class="vocation">
								            <div class="uew-select">
								            	<div class="uew-select-value ue-state-default" style="width: 125px;"><em class="uew-select-text">{{ selected.text }}</em><em class="uew-icon uew-icon-triangle-1-s"></em></div>
									            	<select class="select3" style="width: 152px;" @change="change(s.options, $event)">
											            <option v-for="o in s.options" :value="o.value" :selected="iSelected(o)">{{ o.text }}</option>
										            </select>
									            </div>
							            </div>
							        </li>
							        <div class="cl"></div>
						        </ul>
						        <ul class="seachform1 cl">
						        	<li class="sarchbtn"><label>&nbsp;</label><input name="" type="submit" class="scbtn1" value="查询">   <input name="" type="reset" class="scbtn" value="重置"></li>  
						        </ul>
						    </form>
				        </div>`,
	        data: {
	        	searchs: searchs,
	        	selected: {value: '', text: '请选择'}
	        },
	        methods: {
	        	iSelected: function(o){
	        		 if(o['selected']){
	        			 this.selected = o;
	        			 return 'selected';
	        		 }
	        		 return '';
	        	},
	        	change: function(options, e){
	        		let val = e.target.value;
	        		for(let i in options){
	        			if(options[i].value == val){
	        				this.selected = options[i];
	        			}
	        		}
	        	}
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
	        data: {

	        },
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
	//datagrid
	admin.datagrid = function(tools, columns, data, pager){
		admin.datagridVue();
		store.commit('a_options', tool.copyAndMerge(admin.datagrid, {tools: tools, columns: columns, data: data, onClickCell: function(index, field, value){
			layer.msg('当前的值是:'+value);
		}}));
	}
	//搜索表单
	admin.search = function(searchs){
		admin.searchVue(searchs);
	}
})();