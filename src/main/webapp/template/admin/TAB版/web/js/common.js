var open_win_dialog = {
	open: function (options) {
		// 生成相应的节点
		var id = options.id;
		var $dialog = $('<div id="_dialog'+ id +'"></div>').appendTo('body').hide();
		if(options.url){
			$dialog.append('<div class="dialog-content"><iframe src="'+ options.url +'" frameborder="0" width="100%" height="380px"></iframe></div>');
		}

		$('#_dialog'+ id).Dialog({
			title: options.title,
			autoOpen: false,
			width:700,
			height:400,
			modal: true,
			closeCallback: function(){
				options.closeCallback && options.closeCallback();
			}
		});

		$('#_dialog'+ id).Dialog('open');

	},

	close: function(id){
		$('#_dialog'+ id).Dialog('close');
	}
}