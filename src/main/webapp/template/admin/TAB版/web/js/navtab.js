+function($){
	'use strict';
	function NavTab(element,options){
		this.$element = $(element);
		var _this = this;
		if(!options){ 
			this.options = this.getDefaults();
		}else{
			this.options = $.extend(true,this.getDefaults(),options);
		}
		this.init();
		
	};
	NavTab.DEFAULTS = {
		id: 'navtab20150125',
		showMore: 2,
		initOpenTabs: [{
			label: '首页',
			name: 'home',
			url: 'index.html'
		}]
	};

	NavTab.prototype = {
		getDefaults: function () {
	        return NavTab.DEFAULTS
	    },
		toValidId: function(_id){
			_id = _id + '';
			var result = '';
			if(_id && _id.length){
				for(var i = 0, len = _id.length; i < len; i++){
					result += _id.charAt(i).charCodeAt().toString(36)+'_';
				}

				_id = result;

				return result;
			}
		},

		init: function(){
			var options = this.options;
			this.opendTab = [];
			this._createDom(options.initOpenTabs[0]);
			this.openTab(options.initOpenTabs[0]);

			this._event();
		},

		_createDom: function(){
			var options = this.options;

			for(var i = 0, len = options.initOpenTabs.length; i < len; i++){
				this._createOneTab(options.initOpenTabs[0]);
				this._createIframeContent(options.initOpenTabs[0]);
			}
		},

		_event: function(){
			var self = this;
			this.$element.on('click',".nav-tabs-item",function(){
			   self.openTab({
			   		name: $(this).attr('name'),
			   		label: $(this).attr('label'),
			   		url: $(this).attr('data-url')
			   });
			});


			this.$element.on('click',".close-icon",function(){
			   self.closeTab($(this).closest('.nav-tabs-item').attr('name'));
			   return false;
			});

			$('.more-btn').click(function(){
				$('.tabs-more-panel').css({
					'top': '125px',
					'left': $(this).offset().left - 50
				}).show();
			});

			$('.tabs-more-panel').on('click', 'li', function(){
				if($(this).hasClass('closeAllTab')){
					// 关闭所有
					self.closeAll();
				}else if($(this).hasClass('closeCurrentTab')){
					// 关闭当前
					self.closeCurrent();

				}else if($(this).hasClass('closeOther')){
					// 关闭除当前的其他的
					self.closeOther();
				}else{

					self.openTab({
						name: $(this).attr('name'),
				   		label: $(this).attr('label'),
				   		url: $(this).attr('data-url')
					});
				}


			});


			$(window).resize(function(){
				self._adjustWidth();
			});
		},

		_createOneTabHtml: function(data){
			if(!data) return;
			var id = this.toValidId(data.name),
				html;
			if(data.name === 'home'){
				html = '<li class="nav-tabs-item home" name="'+ data.name +'" label="'+ data.label +'" id="_tab_'+ id +'" data-url="'+ data.url +'"><i></i><span>'+ data.label +'</span></li>';
			}else{
				html = '<li class="nav-tabs-item" name="'+ data.name +'" label="'+ data.label +'" id="_tab_'+ id +'" data-url="'+ data.url +'"><span>'+ data.label +'</span><a href="javascript:;" title="关闭" class="close-icon"></a></li>';
			}

			return html;
		},

		// 适应宽度
		_adjustWidth: function(){

			var width = $('.nav-tabs-wrap').width(),
				tabsWitdh = $('.nav-tabs').width();

			var totalWidth = 0;
			$('.nav-tabs-item').show();
			$.each($('.nav-tabs-item'), function(index, element){
				totalWidth += ($(this).outerWidth() + 3);
				if(($(this).position().left + $(this).outerWidth() + 3) > width - 70 || (($(this).position().left <= 10) && (index !==0))){
					$(this).hide();
				}else{
					$(this).show();
				}
			});

			//$('.nav-tabs').width(totalWidth);

			// var aWidth = 0;
			// $.each($('.nav-tabs-item:visible'), function(index, element){
			// 	aWidth += ($(this)[0].clientWidth + 30);
			// });

			//$('.nav-tabs-container').width(aWidth);


			if(!!$('.nav-tabs-item:hidden').size()){
				$('.more-btn').show();
			}else{
				$('.more-btn').hide();
			}

			$('.tabs-more-panel').hide();
		},

		_createOneTab: function(data){
			var id = this.toValidId(data.name),
				$tab = $('#_tab_' + id);
			if(!!!$tab.size()){
				var html = this._createOneTabHtml(data);
				this.$element.append(html);

				// 存储数据
				this.opendTab.push(data);

				this._celTotalWidth();

				this._adjustWidth();
			}
			
		},

		// 创建更多操作板
		_createMorePanel: function(data){
			var id = this.toValidId(data.name),
				$panelItem = $('#_morePanel_' + id);

			if(!!!$panelItem.size()){
				var html = '<li class="more-opend-item" name="'+ data.name +'" id="_morePanel_'+ id +'" label="'+ data.label +'" data-url="'+ data.url +'"><a href="javascript:;">'+ data.label +'</a></li>';
				$('.opend-panel ul').append(html);
			}
			
		},

		_celTotalWidth: function(){
			// var totalWidth = 0;
			// $.each($('.nav-tabs-item'), function(index, element){
			// 	totalWidth += ($(this)[0].clientWidth + 30);
			// });

			// $('.nav-tabs').width(totalWidth);

			// this.totalWidth = totalWidth;
		},


		_createIframeContent: function(data){
			var id = this.toValidId(data.name),
				$page = $('#_page_'+ id);
			if(!!!$page.size()){ // 此页面已经创建了，此时只要隐藏其他，显示当前
				// 此页面不存在，创建此页面
				$page = $('<div id="_page_'+ id +'" data-url="'+ data.url +'" data-label="'+ data.label +'" class="content-page">'+
							 '<iframe src="'+ data.url +'" width="100%" height="100%" frameborder="0"></iframe>'+
						   '</div>');

				$page.appendTo($('.content-pages'));

				var $iframe = $page.find('iframe');

				$iframe[0].onload = function(){
					// 可扩展一个tab内容加载完成执行的内容
				}
			}

			$('.content-page').hide();
			$page.show();

			this.currentId = id;

			// tab设置为current
			$('.nav-tabs-item').removeClass('current');
			$('#_tab_' + id).addClass('current');

			// 设置更多为current
			$('.more-opend-item').removeClass('current');
			$('#_morePanel_' + id).addClass('current');

			$page.height(this.iframeContentHeight);
			$page.find('iframe').height(this.iframeContentHeight);

			// 寻找当前左侧菜单节点，加上current
			var contens = $('.leftMenu').find('iframe').contents(),
				$leftMenu = contens.find('#'+id);
			contens.find('.active').removeClass('active');
			//contens.find('.open').find('.sub-menus').hide();
			contens.find('.open').removeClass('open');

			$leftMenu.addClass('active');

			// 二级菜单
			if(!!$leftMenu.find('.sub-menus').size()){
				$leftMenu.addClass('open');
				$leftMenu.find('.sub-menus').show();
			}
			// 三级菜单
			if(!!$leftMenu.closest('.sub-menus').size()){

				$leftMenu.closest('.sub-menus').closest('li').addClass('open active');
				//$leftMenu.closest('.sub-menus').show();

				
			}
		},

		openTab: function(data){
			this._createOneTab(data);
			this._createIframeContent(data);
			this._createMorePanel(data);
		},

		closeTab: function(name){
			var id = this.toValidId(name),
				$tab = $('#_tab_' + id),
				$page = $('#_page_'+ id),
				$moreItem = $('#_morePanel_' + id);

			$tab.remove();
			$page.remove();
			$moreItem.remove();

			// 删除对应的已打开数据
			for(var i = 0, len = this.opendTab.length; i < len; i++){
				if(name === this.opendTab[i].name){
					this.opendTab.splice(i,1);
					break;
				}
			}

			// 如果关闭的就是当前打开的，则当前变为他前面的一个
			if(id === this.currentId){
				this.openTab(this.opendTab[i-1]);
			}

			this._celTotalWidth();

			this._adjustWidth();
		},

		closeOther: function(){
			var id = this.currentId,
				name = $('#_tab_' + id).attr('name');

			var opendTab = [];
			$.extend(true, opendTab, this.opendTab);

			for(var i = 0,len = opendTab.length; i < len; i++){
				var data = opendTab[i];
				if(data.name !== 'home' && data.name !== name){
					this.closeTab(data.name);
				}
			}

			$('.tabs-more-panel').hide();
		},


		closeAll: function(){
			// 关闭所有
			var opendTab = [];
			$.extend(true, opendTab, this.opendTab);
			for(var i = 0,len = opendTab.length; i < len; i++){
				var data = opendTab[i];
				if(data.name !== 'home'){
					this.closeTab(data.name);
				}
			}

			$('.tabs-more-panel').hide();
		},


		closeCurrent: function(){
			var id = this.currentId,
				name = $('#_tab_' + id).attr('name');

			if(name === 'home') {
				alert('首页不能关闭');
				return;
			}
			this.closeTab(name);
		},



		reloadCurrentFrame: function(){
			// 刷新当前页面
			var currentId = this.currentId;
			$('#_page_' + currentId).find('iframe')[0].contentWindow.location.reload();
		},


		setContentHeight: function(height){
			this.iframeContentHeight = height;
			$('_page_'+ this.currentId).height(height).find('iframe').height(height);
		}
	};

	//组件封装处理
	function Plugin(option,object){
		this.each(function(){
			var $this = $(this),
				data = $this.data('hc.waterfall'),
				options = typeof option == 'object' && option;
			if(!data){
				$this.data('hc.waterfall',(data = new NavTab($this,options)));
			}
			if(typeof option =='string'){
				data[option](object);
			}
			return $this;
		});
	};
	var old = $.fn.NavTab;
	$.fn.NavTab = Plugin;
	$.fn.NavTab.Constructor = NavTab;
	$.fn.NavTab.noConflict = function () {
		$.fn.NavTab = old;
		return this;
	};	
}(jQuery);