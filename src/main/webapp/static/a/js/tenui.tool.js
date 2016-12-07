/**
 * 工具
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
         * 合并对象
         */
        merge: function(f, c){
            for(let p in c) f[p] = c[p];
            return f;
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
            for(let k in o){
            	if(typeof o[k] == "object")
            		n[k] = this.copy(o[k]);
            	else
            		n[k] = o[k];
            }
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
})();