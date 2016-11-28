$(document).ready(function(){

	/*****弹窗示例开始，使用请删除以下内容*******/


	 $('.exitDialog').Dialog({
		title:'我的内容是个iframe哦',
		autoOpen: false,
		width:700,
		height:400,
		modal: true,
		closeCallback: function(){

			// 关闭刷新当前页面
			top.window.frame.reloadCurrentFrame();
			alert('关闭弹出刷新当前页面');
		}
	});


	 $('.abult').click(function(){
	 	$('.exitDialog').Dialog('open');
	 });

	/*****弹窗示例结束，使用请删除以上内容*******/

	$('.nav-tabs').NavTab({});

	$(window).resize(function(){
		var tempHeight = $('.hideHead').hasClass('hide') ? 0 : $('#hd').height();
		$('.leftMenu iframe').height($(window).height() - tempHeight - 70);
		$('.content-wrap iframe').height($(window).height() - tempHeight - 90);

		$('.nav-tabs').NavTab('setContentHeight', $(window).height() - tempHeight - 90);
	}).resize();

	$('.hideHead').toggle(function(){
		$(this).addClass('hide').attr('title' ,'展开头部');
		$('#hd').hide();
		$(window).resize();
	}, function(){
		$(this).removeClass('hide').attr('title' ,'收起头部');
		$('#hd').show();
		$(window).resize();
	});


	$('.fixedLeft').toggle(function(){
		//$(this).addClass('fixed').closest('#bd').addClass('hideleft');
		$(this).closest('#bd').stop().animate({'paddingLeft': 0}, 300, function(){
			$(this).addClass('fixed hideleft');
		});

	},function(){
		$(this).closest('#bd').stop().animate({'paddingLeft': 187}, 300, function(){
			$(this).removeClass('fixed hideleft');
		});
	});

	$('.leftMenu').mouseleave(function(){
		if(!!$(this).closest('.hideleft').size()){
			$(this).closest('#bd').stop().animate({'paddingLeft': 0});
		}
	});

	

	$('.fixedLeft,.fiexdMouseover').mouseenter(function(){
		if(!!$(this).closest('.hideleft').size()){
			$(this).closest('#bd').stop().animate({'paddingLeft': 187});
		}
	});


	$('.user').click(function(){
		$('.profile').toggle();
	});


	$('.overlay').click(function(){
		$(this).parent().hide();
	});



	

});


// 主页面公共接口

var frame = {
	openTab: function(data){
		$('.nav-tabs').NavTab('openTab', data);
	},

	closeTab: function(name){
		if(typeof name === 'object'){
			name = name.name;
		}

		$('.nav-tabs').NavTab('closeTab', data);
	},

	reloadCurrentFrame: function(){
		$('.nav-tabs').NavTab('reloadCurrentFrame');
	},

	closeCurrent: function(){
		$('.nav-tabs').NavTab('closeCurrent');
	}
}