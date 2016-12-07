/**
 * vue实例js
 * version: 1.0.0
 */
!(function(){
	"use strict";
	let store = tenui.store;
	let vm_search = null;
	/**
	 * 搜索表单
	 */
	tenui.searchVue = function(){
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
	tenui.datagridVue = function(){
		if(vm_datagrid) return;
		vm_datagrid = new Vue({
	        el: '#datagrid',
	        // 把 store 对象提供给 “store” 选项，这可以把 store 的实例注入所有的子组件
            store,
	        components: {
	        	'data-tools': tenui.CompTenuiTools,
	            'data-table': tenui.CompTenuiTable,
	            'data-pager': tenui.CompTenuiPager
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
	tenui.datagrid = function(options){
		store.commit('a_options', tool.copyAndMerge(config.datagrid, options));
		tenui.datagridVue();
	}
	//搜索表单
	tenui.search = function(search){
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
		tenui.searchVue();
	}
})();