/**
* @preserve admin v1.0.0
*/
"use strict";
var CONSTANT = {
	//先加载的js文件放在前面
	'jqgrid': ['/static/a/js/jqGrid/jquery.jqGrid.min.js?v=4.5.2', '/static/a/js/jqGrid/i18n/grid.locale-cn.js?v=4.5.2'],
	'validate': ['/static/a/js/validation/jquery.validationEngine-zh_CN.js?v=2.6.2', '/static/a/js/validation/jquery.validationEngine.js?v=2.6.2'],
	'datepicker': ['/static/a/js/datepicker/moment.min.js?v=2.0.0', '/static/a/js/datepicker/daterangepicker.js'],
	'editor': ['/static/a/js/editor/wangEditor.min.js?v=2.0.0']
}
//创建事件
var EVT = document.createEvent('HTMLEvents');
// 初始化事件
EVT.initEvent('listen', false, false);

//listen事件执行的方法
function listen (e) {
  var el = e.target
  if(el.callback){
	for(var i = 0, l = el.callback.length; i < l; i++)
		el.callback[i]();
	delete el.callback
  }
  // 移除事件监听
  if (window.removeEventListener)
      el.removeEventListener('listen', listen, false)
    else if (window.detachEvent)
      el.detachEvent('onlisten', fn)
}
//lazies[i].dispatchEvent(evt); 触发事件
var loadJS = function(id, callback, url){
	var path = CONSTANT[id];
	if(path)
		it(id, path, 0, callback);
	else
		load(id, callback, url);
	
	function it(id, path, i, callback){
		load(id+'_'+i, function(){
			if(i === path.length - 1)
				callback();
			else
				it(id, path, i + 1, callback);
		}, path[i]);
	}
	function load(id, callback, url){
		var script = document.getElementById(id);
		if(script) {//已经加载过该js,但是需要判断是否已经加载完成
			if(script.ready)
				callback();
			else {
				if(script.callback === undefined)//如果还没有加载完成，将当前的回调函数放入节点callback中变量中，在加载完成后触发自定义函数循环调用回调函数
					script.callback = [];
				script.callback.push(callback);
			}
			return;
		}
		var head = document.getElementsByTagName('head');  
		if(head && head.length)
			head = head[0];
		else
			head = document.body;
		script = document.createElement('script');   
		script.type = "text/javascript";
		script.id = id;
		script.ready = false;//还没加载完成
		head.appendChild(script);
		//添加监听事件
	    if (window.addEventListener)
	    	script.addEventListener('listen', listen, false)
	    else if (window.attachEvent)
	    	script.attachEvent('onlisten', listen)
	    	
		script.onload = script.onreadystatechange = function(){
			//script 标签，IE 下有 onreadystatechange 事件, w3c 标准有 onload 事件     
			//这些 readyState 是针对IE8及以下的，W3C 标准的 script 标签没有 onreadystatechange 和 this.readyState , 
			//文件加载不成功 onload 不会执行，
			//(!this.readyState) 是针对 W3C标准的, IE 9 也支持 W3C标准的 onload 
			if ((!this.readyState) || this.readyState == "complete" || this.readyState == "loaded" ){
				callback();
				script.ready = true;
				script.dispatchEvent(EVT);//触发事件
			}
		 }//end onreadystatechange 
		 script.src = url;
	}
}
var admin = {};
!(function(app) {
	//eval全局执行
	app.eval = function(code){
		var a = document .createElement ("script" );
        a.type= "text/javascript" ;
        a.text= code ;
        document .getElementsByTagName ("head" )[0 ].appendChild (a) ;
	}
	app.getGridOption = function(option){
		var deft = {
			url : '',//config
			mtype: 'POST',
	        datatype : "json",
	        jsonReader: {
	            page: "pageNumber",   // json中代表当前页码的数据  
	            total: "totalPages", // json中代表页码总数的数据  
	            records: "total" // json中代表数据行总数的数据  
	        },
	        prmNames:  {page:'page', rows:'size', sort: 'sort', order: 'order', search: null, nd: null, npage:null},
			colNames:[],//config
			colModel:[], 
			rownumbers: false,//显示行编号
			viewrecords : true,
			rowNum: 10,
			rowList: [10,30,50,100],
			pager : '',
			recordpos: 'center',
			pagerpos: 'right',
			recordtext: '显示 {0} - {1} 共 {2} 条',
			pgtext : '第 {0} 页， 共 {1} 页',
			loadtext: '正在请求数据...',
			altRows: true,
			//multiSort: true,
			multiselect: true,
			//multikey: "ctrlKey",
	        multiboxonly: false,
			editurl:'',
			autowidth: true,
			loadComplete : function() {
				var _this = this;
				setTimeout(function(){
					//styleCheckbox(_this);
					//updateActionIcons(_this);
					updatePagerIcons(_this);
				}, 0);
			},
			loadError : function(xhr, st, err) {
				console.log(err);
		    }
		}
		//替换分页插件的按钮图标
		function updatePagerIcons(table) {
			var replacement = {
				'ui-icon-seek-first' : 'icon-double-angle-left bigger-140',
				'ui-icon-seek-prev' : 'icon-angle-left bigger-140',
				'ui-icon-seek-next' : 'icon-angle-right bigger-140',
				'ui-icon-seek-end' : 'icon-double-angle-right bigger-140'
			};
			$('.ui-pg-table:not(.navtable) > tbody > tr > .ui-pg-button > .ui-icon').each(function(){
				var icon = $(this);
				var $class = $.trim(icon.attr('class').replace('ui-icon', ''));
				if($class in replacement) icon.attr('class', 'ui-icon '+replacement[$class]);
			});
		}
		return $.extend({}, deft, option);
	}
	/**
	 * 初始化jqgrid数据
	 */
	app.initGrid = function() {
		loadJS('jqgrid', function(){
			$grid.jqGrid(app.getGridOption({
				url : config.grid.url.list,//config
				height: $height,
				colNames:['菜单编号', '菜单名称','菜单地址', '操作'],//config
				colModel:[//config
					{name:'menu_id',index:'menu_id', width:60, sorttype:"int", editable: false},
					{name:'menu_name',index:'menu_name',width:90, editable:true},
					{name:'menu_url',index:'menu_url', width:150, editable: true, editoptions:{maxlength:"30"}},
					{name:'myac',index:'', width:80, fixed:true, sortable:false, resize:false,
						formatter:'actions', 
						formatoptions:{
							keys:true
						}
					}
				], 
				pager : pagerSelector
			}));
			//按钮自定义样式
			$grid.jqGrid('navGrid', pagerSelector,
			        {   //navbar options
			            edit: false,
			            editicon : 'icon-pencil blue',
			            add: false,
			            addicon : 'icon-plus-sign purple',
			            del: false,
			            delicon : 'icon-trash red',
			            search: false,
			            searchicon : 'icon-search orange',
			            refresh: true,
			            refreshicon : 'icon-refresh green',
			            view: false,
			            viewicon : 'icon-eye-open grey'
			        }
			    );
		});
	}
	/**
	 * 绑定验证
	 */
	app.attachVali = function($obj){
		loadJS('validate', function(){
			$obj.validationEngine('detach');//先移除再绑定
			$obj.validationEngine('attach', {
				maxErrorsPerField: 1,
				showOneMessage: true,
				promptPosition: 'bottomLeft',
				addPromptClass: 'formError-small'
			});
		});
	}
	/**
	 * 绑定时间控件
	 */
	app.attachTimepicker = function(){
		loadJS('datepicker', function(){
			$('.auto-bind-timepicker').each(function(i){
				var id = this.id;
				var constraint = $(this).attr('data-constraint');
				$(this).after('<input id="'+id+'_hidden" name="'+constraint+'" type="text" style="display:none;"/>');
				bindDateTimePicker('#'+id);
			});
			function bindDateTimePicker(selecter){
				$(selecter).daterangepicker({
			    	"singleDatePicker": true,
			    	"autoUpdateInput": false,
			    	"showDropdowns": true,
			    	 locale : {  
	                     applyLabel : '确定',
	                     cancelLabel : '取消',  
	                     fromLabel : '起始时间',  
	                     toLabel : '结束时间',  
	                     customRangeLabel : '自定义',  
	                     daysOfWeek : [ '日', '一', '二', '三', '四', '五', '六' ],  
	                     monthNames : [ '一月', '二月', '三月', '四月', '五月', '六月', '七月', '八月', '九月', '十月', '十一月', '十二月' ],  
	                     firstDay : 1  
	                 }
			    },function(start, end, label) {
			    	var time = start.format('YYYY-MM-DD');
				  	$(selecter).val(time);
				  	$(selecter+'_hidden').val(strToTime(time));
				});
			};
			function strToTime(str){
				return new Date(str.replace(/-/g,'/')).getTime();
			};
		});
	}
	/**
	 * 搜索表单查询
	 */
	app.search = function(){
		var vali = $search.validationEngine('validate');
		if(!vali) return false;
		var pd = $grid.jqGrid('getGridParam','postData');
		//直接传入下面的参数不行，需要先将pd这个对象找出来
		var params = $('#search-form').serializeArray();
		$(params).each(function(){
			pd[this.name] = this.value
		});
		//传入查询条件参数  
        $grid.jqGrid("setGridParam",{postData: pd}).trigger("reloadGrid");
		return false;
	}
	/**
	 * dto天假
	 */
	app.addDto = function(){
		$dtoForm.validationEngine('hideAll');
		$dtoModel.removeClass('dto-model-hide').find('h6').html('<i class="icon-plus"></i> 新增');
		$dtoModel.attr('type', 'add');
		//需要填充到表单中
		var list = config.object.cols.add;
		app.setObjectCont(list);
		layer.open({
  		  type: 1,
  		  title: false,
  		  move: '.widget-header',
  		  closeBtn: 0,
  		  skin: 'dto-form',
  		  shadeClose: true,
  		  area: ['460px'],
  		  content: $dtoModel,
  		  end: function(){
  			$dtoModel.addClass('dto-model-hide');
  		  }
		});
	}
	/**
	 * 编辑dto
	 */
	app.editDto = function(){
		var gr = $grid.jqGrid('getGridParam', 'selrow');
	    if (gr != null){
	    	$dtoForm.validationEngine('hideAll');
	    	$dtoModel.removeClass('dto-model-hide').find('h6').html('<i class="icon-edit"></i> 编辑');
	    	$dtoModel.attr('type', 'edit');
	    	var row = $grid.jqGrid('getRowData', gr);
	    	var list = config.object.cols['edit'] || config.object.cols['add'];
			app.setObjectCont(list, row);
	    	layer.open({
	    		  type: 1,
	    		  title: false,
	    		  move: '.widget-header',
	    		  closeBtn: 0,
	    		  skin: 'dto-form',
	    		  shadeClose: true,
	    		  area: ['460px'],
	    		  content: $dtoModel,
    	  		  end: function(){
    	  			$dtoModel.addClass('dto-model-hide');
    	  		  }
    		});
	    }else
	      layer.msg('请选择一条记录');
	}
	/**
	 * 删除数据
	 */
	app.delDto = function(){
		var list = $grid.jqGrid('getGridParam', 'selarrrow');//获取选择行id的array
    	if(list.length > 0){
	    	//请求后台删除数据
    		layer.msg('确定要删除吗？', {
    			  time: 0,
    			  btn: ['确定','取消'], //按钮
    			  yes: function(){
      					//请求后台删除数据
	      				$.ajax({
	      					type: 'DELETE',
	      					url: '/admin/menu/del?ids='+list.join(','),
	      					dataType: 'json',
	      					success: function(res){
	      						if(res.success){
	      							if(res.data.errs.length != 0){
	      								layer.msg('部分记录未删除成功:['+res.data.errs+']');
	      								$grid.trigger("reloadGrid");
	      							}else
	      								layer.msg('部分记录未删除成功');
	      							$grid.trigger("reloadGrid");
	      							return;
	      						}
	      						layer.msg(res.msg);
	      					}
	      				});
	      			}
    			});
    	}else
    		layer.msg('请选择需要删除的记录');
	}
	/**
	 * 提交dto编辑框表单
	 */
	app.submitDtoModelForm = function(){
		var vali = $dtoForm.validationEngine('validate');
		if(!vali) return false;
		//验证通过后需要提交表单
		var type = $dtoModel.attr('type');
		var url = config.grid.url[type] || config.grid.url['add'];
		if(url){
			$.post(url, $dtoForm.serialize(), function(res){
				layer.msg(res.msg);
				if(res.success){
					layer.closeAll('page');
					//也可以不用重新刷新页面，在本地修改grid中的数据也一样
					$grid.trigger("reloadGrid");
				}
			}, 'json');
		}
		return false;
	}
	/**
	 * 关闭dto编辑框
	 */
	app.closeDtoModel = function(){
		layer.closeAll('page');
	}
	/**
	 * 创建搜索表单
	 */
	app.setSearchCont = function(){
		var html = new Array();
		var cols = config.search.cols;
		var col = null;
		for(var i = 0, l = cols.length; i < l; i++){
			col = cols[i];
			html.push('<div class="col-sm-3 no-padding-left">');
			html.push('<label class="col-sm-4 control-label no-padding-right" for="s-'+col.name+'"> '+col.label+': </label>');
			html.push('<div class="col-sm-8">');
			if(col.widget === 'text')
				html.push('<input id="s-'+col.name+'" type="text" placeholder="'+col.label+'" class="col-sm-12 '+col.vali+']" name="rq[\''+col.name+'\']" autocomplete="off">');
			else if(col.widget === 'select'){
				html.push('<select id="s-'+col.name+'" class="col-sm-12" name="rq[\''+col.name+'\']">');
				html.push('<option value=""></option>');
				var list = col.source.cont;
				for(var j = 0, ln = list.length; j < ln; j++)
					html.push('<option value="'+list[j].value+'">'+list[j].text+'</option>');
				html.push('</select>');
			} else if(col.widget === 'date')
				html.push('<input id="s-'+col.name+'" type="text" placeholder="'+col.label+'" class="auto-bind-timepicker col-sm-12 '+col.vali+']" name="rq[\''+col.name+'\']" autocomplete="off">');
			
			html.push('</div>');
			html.push('</div>');
		}
		html.push('</div>');
		$('#search-cnter').empty().append(html.join(''));
		app.attachVali($search);//给搜索表单绑定验证
	}
	/**
	 * 操作按钮
	 */
	app.setOptionCont = function(){
		var html = new Array();
		var btns = config.grid.btns;
		var btn = null;
		for(var i = 0, l = btns.length; i < l; i++){
			btn = btns[i];
			html.push('<button class="btn btn-sm '+ btn.clss +'" onclick="'+ btn.handler +'"><i class="'+ btn.icon +'"></i> '+ btn.label +'</button>');
		}
		$('#opt-cnter').empty().append(html.join(''));
	}
	/**
	 * 表单
	 */
	app.setObjectCont = function(list, dto){
		var html = new Array();
		var col = null;
		for(var i = 0, l = list.length; i < l; i++){
			col = list[i];
			html.push('<div class="form-group">');
			html.push('<label class="col-sm-2 col-sm-offset-2 control-label no-padding-right" for="d-'+ col.name +'"> '+ col.label  +': </label>');
			html.push('<div class="col-sm-6"><input id="d-'+ col.name +'" type="text" placeholder="'+ col.label +'" class="col-sm-12 '+ col.vali +'" name="'+ convert(col.name) +'"></div>');
			html.push('</div>');
		}
		$dtoForm.find('.widget-main').empty().append(html.join(''));
		app.attachVali($dtoForm);//给dto表单绑定验证
		
		function convert(str){
			//下划线转驼峰命名
			var a = str.split("_");
			var o = a[0];
			for(var i = 1, l = a.length; i < l; i++)
			    o = o + a[i].slice(0,1).toUpperCase() + a[i].slice(1);
			return o;
		}
	}
	/**
	 * 打开配置菜单页面
	 */
	app.config = function(){
		var gr = $grid.jqGrid('getGridParam', 'selrow');
	    if (gr != null){
	    	var row = $grid.jqGrid('getRowData', gr);
	    	curMenuId = row.menu_id;
	    	layer.open({
			  type: 2,
			  title: '配置页面 - '+ row.menu_name,
			  content: '/admin/forward/config.html',
			  area: ['100%', '100%'],
			  maxmin: true
			});
	    }else
	      layer.msg('请选择一条记录');
	}
}(admin));
