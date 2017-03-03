/**
* @preserve admin v1.0.0
*/
var loadJS = function(id, callback, url){
	var script = document.getElementById(id);
	if(script) {//已经加载过改js
		callback();
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
	head.appendChild( script);
	script.onload = script.onreadystatechange = function(){
		//script 标签，IE 下有 onreadystatechange 事件, w3c 标准有 onload 事件     
		//这些 readyState 是针对IE8及以下的，W3C 标准的 script 标签没有 onreadystatechange 和 this.readyState , 
		//文件加载不成功 onload 不会执行，
		//(!this.readyState) 是针对 W3C标准的, IE 9 也支持 W3C标准的 onload 
		if ((!this.readyState) || this.readyState == "complete" || this.readyState == "loaded" )
			callback();     
	 }//end onreadystatechange 
	 script.src = url;
}
var admin = {};
(function(admin) {
	/**
	 * 初始化jqgrid数据
	 */
	admin.initGrid = function(config) {
		$grid.jqGrid({
			url : '/admin/menu/list',//config
			mtype: 'POST',
	        datatype : "json",
	        jsonReader: {
	            page: "pageNumber",   // json中代表当前页码的数据  
	            total: "totalPages", // json中代表页码总数的数据  
	            records: "total" // json中代表数据行总数的数据  
	        },
	        prmNames:  {page:'page', rows:'size', sort: 'sort', order: 'order', search: null, nd: null, npage:null},
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
			rownumbers: false,//显示行编号
			viewrecords : true,
			rowNum: 10,
			rowList: [10,30,50,100],
			pager : pagerSelector,
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
	
			loadComplete : function() {
				var _this = this;
				setTimeout(function(){
					//styleCheckbox(_this);
					//updateActionIcons(_this);
					updatePagerIcons(_this);
				}, 0);
			},
			beforeRequest: function(){
				//jQuery("#grid-table").jqGrid('setGridParam',{loadui: 'block'});//显示正在加载提示
			},
			editurl:'',//nothing is saved
			autowidth: true
		});
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
	}
	/**
	 * 绑定验证
	 */
	admin.attachVali = function($obj){
		$obj.validationEngine('attach', {
			maxErrorsPerField: 1,
			showOneMessage: true,
			promptPosition: 'bottomLeft',
			addPromptClass: 'formError-small'
		});
	}
	/**
	 * 绑定时间控件
	 */
	admin.attachTimepicker = function(){
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
	}
	/**
	 * 搜索表单查询
	 */
	admin.search = function(){
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
	admin.addDto = function(){
		$dtoForm.validationEngine('hideAll');
		$dtoModel.removeClass('dto-model-hide').find('h6').html('<i class="icon-plus"></i> 新增');
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
	admin.editDto = function(){
		var gr = $grid.jqGrid('getGridParam', 'selrow');
	    if (gr != null){
	    	$dtoForm.validationEngine('hideAll');
	    	$dtoModel.removeClass('dto-model-hide').find('h6').html('<i class="icon-edit"></i> 编辑');
	    	var row = $grid.jqGrid('getRowData', gr);
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
	 * 提交dto编辑框表单
	 */
	admin.submitDtoModelForm = function(){
		var vali = $dtoForm.validationEngine('validate');
		if(!vali) return false;
		return false;
	}
	/**
	 * 关闭dto编辑框
	 */
	admin.closeDtoModel = function(){
		layer.closeAll('page');
	}
}(admin));
