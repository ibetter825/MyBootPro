require.config({
    paths : {
      "jquery" : ["jquery-2.0.3.min"],
      "bootstrap": ["bootstrap.min"],
      "layer": ["layer/layer"],
      "ace": ["ace.min"]
    },
    shim: {  
        'bootstrap': ['jquery'],
        'layer': ['jquery'],
        'ace': ['jquery', 'bootstrap']
    }
  });

require(["bootstrap"], function(){
	alert("bs load finished");
});

require(["layer"], function(){
	alert("ly load finished");
});

require(["ace"], function(){
	alert("ace load finished");
});