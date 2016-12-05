!(function () {
    "use strict";
    window.layer = {}; 
    /**
     * 存放调用组件的默认设置
     */
    layer.options = {
            /**
             * 基本层类型
             * 类型：Number，默认：0
             * layer提供了5种层类型。可传入的值有：0（信息框，默认）1（页面层）2（iframe层）3（加载层）4（tips层）。 若你采用layer.open({type: 1})方式调用，则type为必填项（信息框除外）
             */
            type: 0,//0（信息框，默认）1（页面层）2（iframe层）3（加载层）4（tips层）
            /**
             * 标题
             * 类型：String/Array/Boolean，默认：'信息'\
             * title支持三种类型的值，若你传入的是普通的字符串，如title :'我是标题'，那么只会改变标题文本；若你还需要自定义标题区域样式，那么你可以title: ['文本', 'font-size:18px;']，数组第二项可以写任意css样式；如果你不想显示标题栏，你可以title: false
             */
            title: '信息',
            /**
             * 内容
             * 类型：String/DOM/Array，默认：''
             * content可传入的值是灵活多变的，不仅可以传入普通的html内容，还可以指定DOM，更可以随着type的不同而不同。
             */
            content: '',
            /**
             * 样式类名
             * 类型：String，默认：''
             * skin不仅允许你传入layer内置的样式class名，还可以传入您自定义的class名。这是一个很好的切入点，意味着你可以借助skin轻松完成不同的风格定制。目前layer内置的skin有：layui-layer-lanlayui-layer-molv
             */
            skin: '',
            /**
             * 宽高
             * 类型：String/Array，默认：'auto'
             * 在默认状态下，layer是宽高都自适应的，但当你只想定义宽度时，你可以area: '500px'，高度仍然是自适应的。当你宽高都要定义时，你可以area: ['500px', '300px']
             */
            area: 'auto',
            /**
             * 坐标
             * 类型：String/Array，默认：垂直水平居中
             * offset 默认情况下不用设置。但如果你不想垂直水平居中
             * offset: '100px'	只定义top坐标，水平保持居中
             * offset: ['100px', '50px']	同时定义top、left坐标
             * offset: 't' 快捷设置顶部坐标
             * offset: 'r'	快捷设置右边缘坐标
             * offset: 'b'	快捷设置底部坐标
             * offset: 'l'	快捷设置左边缘坐标
             * offset: 'lt'	快捷设置左上角
             * offset: 'lb'	快捷设置左下角
             * offset: 'rt'	快捷设置右上角
             * offset: 'rb'
             */
            offset: '',
            /**
             * 图标。信息框和加载层的私有参数
             * 类型：Number，默认：-1（信息框）/0（加载层）
             * 信息框默认不显示图标。当你想显示图标时，默认皮肤可以传入0-6如果是加载层，可以传入0-2
             */
            icon: -1,
            /**
             * 按钮
             * 类型：String/Array，alert默认：'确认',其他默认不显示
             * 信息框模式时，btn默认是一个确认按钮，其它层类型则默认不显示，加载层和tips层则无效。当您只想自定义一个按钮时，你可以btn: '我知道了'，当你要定义两个按钮时，你可以btn: ['yes', 'no']。当然，你也可以定义更多按钮，比如：btn: ['按钮1', '按钮2', '按钮3', …]，按钮1和按钮2的回调分别是yes和cancel，而从按钮3开始，则回调为* btn3: function(){}，以此类推。
             */
            btn: '确认',
            /**
             * 按钮排列
             * 类型：String，默认：r
             * 你可以快捷定义按钮的排列位置，btnAlign的默认值为r，即右对齐。该参数可支持的赋值如:l,c,r
             */
            btnAlign: 'r',
            /**
             * 关闭按钮
             * 类型：String/Boolean，默认：1
             * layer提供了两种风格的关闭按钮，可通过配置1和2来展示，如果不显示，则closeBtn: 0
             */
            closeBtn: 1,
            /**
             * 遮罩
             * 类型：String/Array/Boolean，默认：0.3
             * 即弹层外区域。默认是0.3透明度的黑色背景（'#000'）。如果你想定义别的颜色，可以shade: [0.8, '#393D49']；如果你不想显示遮罩，可以shade: 0
             */
            shade: [0.3, '#000'],
            /**
             * 是否点击遮罩关闭
             * 类型：Boolean，默认：false
             * 如果你的shade是存在的，那么你可以设定shadeClose来控制点击弹层外区域关闭。
             */
            shadeClose: false,
            /**
             * 自动关闭所需毫秒
             * 类型：Number，默认：0
             * 默认不会自动关闭。当你想自动关闭时，可以time: 5000，即代表5秒后自动关闭，注意单位是毫秒（1秒=1000毫秒）
             */
            time: 0,
            /**
             * 用于控制弹层唯一标识
             * 类型：String，默认：空字符
             * 设置该值后，不管是什么类型的层，都只允许同时弹出一个。一般用于页面层和iframe层模式
             */
            id: '',
            /**
             * 动画
             * 类型：Number，默认：0
             * 我们的出场动画全部采用CSS3。这意味着除了ie6-9，其它所有浏览器都是支持的。目前anim可支持的动画类型有0-6
             */
            anim: 0,
            /**
             * 最大最小化
             * 类型：Boolean，默认：false
             * 该参数值对type:1和type:2有效。默认不显示最大小化按钮。需要显示配置maxmin: true即可
             */
            maxmin: false,
            /**
             * 固定
             * 类型：Boolean，默认：true
             * 即鼠标滚动时，层是否固定在可视区域。如果不想，设置fixed: false即可
             */
            fixed: true,
            /**
             * 是否允许拉伸
             * 类型：Boolean，默认：true
             * 默认情况下，你可以在弹层右下角拖动来拉伸尺寸。如果对指定的弹层屏蔽该功能，设置 false即可。该参数对loading、tips层无效
             */
            resize: true,
            /**
             * 是否允许浏览器出现滚动条
             * 类型：Boolean，默认：true
             * 默认允许浏览器滚动，如果设定scrollbar: false，则屏蔽
             */
            scrollbar: true,
            /**
             * 最大宽度
             * 类型：，默认：360
             * 请注意：只有当area: 'auto'时，maxWidth的设定才有效。
             */
            maxWidth: 360,
            /**
             * 层叠顺序
             * 类型：，默认：19891014（贤心生日 0.0）一般用于解决和其它组件的层叠冲突。
             */
            zIndex: 19891014,
            /**
             * 触发拖动的元素
             * 类型：String/DOM/Boolean，默认：'.layui-layer-title'
             * 默认是触发标题区域拖拽。如果你想单独定义，指向元素的选择器或者DOM即可。如move: '.mine-move'。你还配置设定move: false来禁止拖拽
             */
            move: '.layui-layer-title',
            /**
             * 是否允许拖拽到窗口外
             * 类型：Boolean，默认：false
             * 默认只能在窗口内拖拽，如果你想让拖到窗外，那么设定moveOut: true即可
             */
            moveOut: false,
            /**
             * 拖动完毕后的回调方法
             * 类型：Function，默认：null
             * 默认不会触发moveEnd，如果你需要，设定moveEnd: function(){}即可。
             */
            moveEnd: null,
            /**
             * tips方向和颜色
             * 类型：Number/Array，默认：2
             * tips层的私有参数。支持上右下左四个方向，通过1-4进行方向设定。如tips: 3则表示在元素的下面出现。有时你还可能会定义一些颜色，可以设定tips: [1, '#c00']
             */
            tips: 2,
            /**
             * 是否允许多个tips
             * 类型：Boolean，默认：false
             * 允许多个意味着不会销毁之前的tips层。通过tipsMore: true开启
             */
            tipsMore: false,
            /**
             * 层弹出后的成功回调方法
             * 类型：Function，默认：null
             * 当你需要在层创建完毕时即执行一些语句，可以通过该回调。success会携带两个参数，分别是当前层DOM当前层索引。如：
             */
            success: null,
            /**
             * 确定按钮回调方法
             * 类型：Function，默认：null
             * 该回调携带两个参数，分别为当前层索引、当前层DOM对象。
             */
            yes: null,
            /**
             * 右上角关闭按钮触发的回调
             * 类型：Function，默认：null
             * 该回调同样只携带当前层索引一个参数，无需进行手工关闭。如果不想关闭，return false即可
             */
            cancel: null,
            /**
             * 层销毁后触发的回调
             * 类型：Function，默认：null
             * 无论是确认还是取消，只要层被销毁了，end都会执行，不携带任何参数。
             */
            end: null,
            /**
             * 最大化后触发的回调
             * 类型：Function，默认：null
             * 携带一个参数，即当前层DOM
             */
            full: null,
            /**
             * 最小化后触发的回调
             * 类型：Function，默认：null
             * 携带一个参数，即当前层DOM
             */
            min: null,
            /**
             * 还原后触发的回调
             * 类型：Function，默认：null
             * 携带一个参数，即当前层DOM
             */
            restore: null
    };
    /**
     * 组件中公用的一些方法
     */
    layer.common = {
        setOffset: function(vm, flg){
            let dom = vm.$el;//弹出层的html节点
            let sh = document.documentElement.clientHeight || document.body.clientHeight
            let sw = document.documentElement.clientWidth || document.body.clientWidth
            let dw = dom.offsetWidth;
            let dh = dom.offsetHeight;
            let left = 0;
            let top = 0;
            if(flg){
                let lu = 'px', tu = 'px';
                let offset = vm.options.offset;
                //位置通过offset来取
                switch (offset) {
                    case ''://默认垂直居中
                        left = Math.ceil(sw/2-dw/2);
                        top = Math.ceil(sh/2-dh/2);
                        top = top < 0 ? 0 : top;
                        break;
                    case 't'://顶部居中
                        left = Math.ceil(sw/2-dw/2);
                        break;
                    case 'r'://右部居中
                        left = Math.ceil(sw-dw);
                        top = Math.ceil(sh/2-dh/2);
                        break;
                    case 'b'://底部居中
                        left = Math.ceil(sw/2-dw/2);
                        top = Math.ceil(sh-dh);
                        break;
                    case 'l'://左部居中
                        left = 0;
                        top = Math.ceil(sh/2-dh/2);
                        break;
                    case 'lt'://左上角
                        left = 0;
                        top = 0;
                        break;
                    case 'lb'://左下角
                        left = 0;
                        top = Math.ceil(sh-dh);
                        break;
                    case 'rt'://右上角
                        left = left = Math.ceil(sw-dw);
                        top = 0;
                        break;
                    case 'rb'://右下角
                        left = left = Math.ceil(sw-dw);
                        top = Math.ceil(sh-dh);
                        break;
                    default:
                        if(typeof offset === 'string'){//eg:100px; top坐标，水平居中
                            tu = offset.indexOf('%') > 0 ? '%' : 'px';
                            top = parseFloat(offset);
                            left = left = Math.ceil(sw/2-dw/2);
                        }else if(offset instanceof Array){//[top, left]
                            tu = offset[0].indexOf('%') > 0 ? '%' : 'px';
                            lu = offset[1].indexOf('%') > 0 ? '%' : 'px';
                            top = parseFloat(offset[0]);
                            left = parseFloat(offset[1]);
                        }else{
                            left = Math.ceil(sw/2-dw/2);
                            top = Math.ceil(sh/2-dh/2);
                            top = top < 0 ? 0 : top;
                        }
                        break;
                }
                vm.options.offset = [top + tu, left + lu];//[0, left]
            }else
                vm.options.offset = [-Math.abs(dh) + 'px', '0px'];//弹出层隐藏时放入位置放到屏幕外去
        }
    };

/************************************/
    /**
     * layer store
     */
    const s_layer = {
        state: {
            type: '', //弹出层的类型
            counter: -1, //计数器 默认0
            show: false, //显示状态 默认不显示
            view: '', //指定当前组件与模板
            options: layer.options//默认的参数
        },
        mutations: {
            l_counter(state, counter) {
                if(counter != undefined)
                    state.counter = counter;
                else
                    state.counter++;
            },
            l_toggle(state, show) {
                state.show = show != undefined ? show : !state.show;
            },
            l_view(state, view) {//获取当前指定的视图
                state.view = view;
            },
            l_type(state, type){
                state.type = type;
            },
            l_options(state, options) {
                //直接传options对象
                //通过数组传入需要传入的值 eg: [{'shade': 0, 'closeBtn', 1}]
                if(options instanceof Array){
                    let opt = options[0];
                    for(let k in opt)
                        state.options[k] = opt[k];
                }else
                    state.options = options;
            }
        }
    }
    /**
     * app store
     */
    const store = new Vuex.Store({
        modules: {
            layer: s_layer
        }
    });

    /**
     * 遮罩层组件
     */
    const CompLayerShade = {
        template: `<div class="layui-layer-shade" :id="'layui-layer-shade'+counter" :style="style"></div>`,
        computed: {
            options(){
                return this.$store.state.layer.options;
            },
            counter(){//取得弹出层全局计数器
                return this.$store.state.layer.counter;
            },
            style: function(){
                //shade属性 [0.8, '#393D49'],0不显示遮罩
                return {
                    zIndex: this.options.zIndex + this.counter,
                    backgroundColor: this.options.shade[1],
                    opacity: this.options.shade[0],
                    filter: 'alpha(opacity='+this.options.shade[0]*100+')'
                }
            }
        }
    };
    /**
     * 提示框组件
     */
    const CompLayerMain = {
        props: ['props'],
        template: `<div :class="'layui-layer' + props.classes + props.anim" :id="'layui-layer'+props.counter" :style="props.style">
                        <div v-if="props.title" class="layui-layer-title">{{options.title}}</div>
                        <div id="" :class="'layui-layer-content'+props.contclz">
                            <i v-if="options.icon != -1" :class="'layui-layer-ico layui-layer-ico'+options.icon"></i>
                            <div v-html="options.content"></div>
                        </div>
                        <span v-if="props.closeBtn != 0" class="layui-layer-setwin">
                            <a :class="'layui-layer-ico layui-layer-close layui-layer-close' + props.closeBtn"  @click="props.btnclick(1)"  href="javascript:;"></a>
                        </span>
                        <div v-if="props.btns != ''" class="layui-layer-btn layui-layer-btn-">
                            <a v-for="(item, index) in props.btns" :class="'layui-layer-btn'+index" @click="props.btnclick(index)">{{item}}</a>
                        </div>
                </div>`,
        data: function(){
            return {
                is_need_recount_offset: false
            };
        },
        computed: {
            options(){
                return this.$store.state.layer.options;
            }
        },
        mounted: function(){
            layer.common.setOffset(this, this.props.show);//子组件第一次加载后计算其dom的大小
        },
        watch: {
            'props.counter': function(){
                this.is_need_recount_offset = true;//需要重新计算弹出层的offset
            },
            'props.show': function(){
                this.is_need_recount_offset = true;//当隐藏时需要将弹出框放置在当前窗体之外
            }
        },
        updated: function(){
            if(this.is_need_recount_offset) layer.common.setOffset(this, this.props.show);
            this.is_need_recount_offset = false;
        }
    };
    /**
     * 静态layer vm实例
     * 所有弹出层都调用此实例
     */
    let vm_layer = null;
    
    layer.newVue = function(){
        store.commit('l_counter');//当前计数器加1
        if(vm_layer) return;
        tool.create(document.getElementsByTagName('body')[0], 'layer');
        vm_layer = new Vue({
            el: '.layer',
            // 把 store 对象提供给 “store” 选项，这可以把 store 的实例注入所有的子组件
            store,
            /*动态切换模板 START */
            data: {
                views: ['layer-main'],
                timer: false //是否定时隐藏
            },
            components: {
                'layer-shade': CompLayerShade,
                'layer-main': CompLayerMain
                
            }, //此处的组件需要动态切换, 模板也需要动态切换
            /*动态切换模板 END */
            template: `<div class="layer">
                            <layer-shade v-if="options.shade != 0 && show"></layer-shade>
                            <component :is="view" :props="props"></component>
                        </div>`,
            computed: {
                options(){//layer弹出属性
                    return store.state.layer.options;
                },
                type(){
                    //console.log('当前的弹出层类型:'+store.state.layer.type);
                    return store.state.layer.type;
                },
                view(){//获取当前视图
                    //console.log('当前组件:'+store.state.layer.view);
                    return store.state.layer.view;
                },
                show(){//获取当前隐藏状态
                    return store.state.layer.show;
                },
                counter(){//取得弹出层全局计数器
                    return store.state.layer.counter;
                },
                props(){//传递给子组件的属性值
                    let visibility = 'hidden';
                    let anim = '';//动画效果 //layer-anim-close
                    let typeclz = '';
                    let clzname = '';
                        
                    let contclz = '';//内容显示页面的样式，当存在图标时需要添加class
                    if(this.show){
                        visibility = 'visible';
                        anim = ' layer-anim';
                        if(this.options.anim) 
                            anim += ('-0' + this.options.anim);
                        if(this.options.icon != -1){
                            clzname = '';
                            contclz = ' layui-layer-padding';
                        }
                        typeclz = ' layui-layer-dialog';
                        if(this.type == 'msg'){
                            typeclz += ' layui-layer-border layui-layer-main'; //msg时的默认
                            clzname = ' layui-layer-hui';
                        }else if(this.type == 'load'){
                            let load = this.options.type;
                            typeclz = ' layui-layer-loading';
                            contclz = ' layui-layer-loading' + (load == undefined ? 0 : load);
                        }
                    }
                    
                    let style = {
                        zIndex: this.options.zIndex + this.counter + 1,
                        left: this.options.offset[1],
                        top: this.options.offset[0],
                        visibility: visibility
                    };//不使用visibility不能计算弹出层的大小
                    
                    //是否显示标题
                    let title = 'msg,load'.indexOf(this.type) > -1 ? false:true;

                    let closeBtn = this.options.closeBtn;
                    let btns = this.options.btn; //自定义按钮

                    //msg只有第二种按钮
                    if(this.type == 'msg' && closeBtn > 0)
                        closeBtn = 2;

                    if(typeof btns == 'string')
                        btns = [btns];
                    else if(btns instanceof Array)
                        btns = btns;
                    else
                        btns = '';
                   
                    return {
                        view: this.view, //当前的视图
                        show: this.show,
                        style: style,
                        title: title, 
                        counter: this.counter,
                        btnclick: this.btnclick,//传给子组件 调用btnclick
                        anim: anim,
                        classes: typeclz + clzname,
                        contclz: contclz,
                        closeBtn: closeBtn,
                        btns: btns
                    };
                }
            },
            watch: {
                'counter': function(){
                    //console.log('计数器发生改变关闭定时器,启用新的定时器');
                    store.commit('l_toggle', true);
                    if(this.timer) clearTimeout(this.timer);
                    if(this.show && this.options.time != 0)
                        this.timer = setTimeout(this.cancel, this.options.time);//定时关闭alert框
                }
            },
            methods: {
                /**
                 * 关闭方法
                 */
                cancel: function(){
                        let func = this.options.cancel;
                        if(func) func();
                        store.commit('l_toggle', false)
                        if(this.timer) clearTimeout(this.timer);
                },
                /**
                 * 按钮点击回调方法
                 */
                btnclick: function(i){
                    if(i == 0){//第一个按钮默认绑定yes回调函数
                        let yes = this.options.yes;
                        if(yes)
                            yes();
                        else
                            this.cancel();
                    }else if(i == 1){//第二个按钮默认绑定cancel回调函数
                        this.cancel();
                    }else {
                        i += 1;
                        let cb = this.options['btn'+i];
                        if(cb) cb();
                        this.cancel();
                    }
                }
            },
            mounted: function(){
                //console.log('Vue实例第一次渲染完成时修改状态为true');
                store.commit('l_toggle', true);//vue实例第一次创建后，显示弹出层
                store.commit('l_counter');
            }
        });
    }

    /**
     * 调用确认框
     */
    layer.confirm = function(content, props, func1, func2){
        layer.newVue();
        store.commit('l_view', 'layer-main');
        store.commit('l_type', 'comfirm');//当前弹出层的类型
        let alert_default_options = {
            btn: ['确认', '取消'],
            yes: func1,
            cancel: func2,
            content: content
        };
        if(typeof props == 'function'){
            alert_default_options.yes = props;
            alert_default_options.cancel = func1;
        }
        store.commit('l_options', tool.copyAndMerge(layer.options, tool.put(alert_default_options, props)));//修改layer弹出层vue实例的数据
    }
    /**
     * 调用alert方法
     */
    layer.alert = function(content, props, func){
        layer.newVue();
        store.commit('l_view', 'layer-main');
        store.commit('l_type', 'alert');//当前弹出层的类型
        let alert_default_options = {
            content: content
        };
        if(typeof props == 'function'){
            alert_default_options.cancel = props;
        }
        store.commit('l_options', tool.copyAndMerge(layer.options, tool.put(alert_default_options, props)));//修改layer弹出层vue实例的数据
    }
    /**
     * 调用msg提示框
     */
    layer.msg = function(content, props, func){ 
        layer.newVue();
        store.commit('l_view', 'layer-main');
        store.commit('l_type', 'msg');//当前弹出层的类型
        let msg_default_options = {
                btn: '', //msg默认没有按钮
                time: 3000,
                shade: 0,
                content: content,
                closeBtn: 0,
                cancel: func//提示弹出层 func为close后的回调
        };
        if(typeof props == 'function'){
            msg_default_options.anim = 6;
            msg_default_options.cancel = props;
        }
        store.commit('l_options', tool.copyAndMerge(layer.options, tool.put(msg_default_options, props)));//修改layer弹出层vue实例的数据
    }
    /**
     * 调用加载动画
     */
    layer.load = function(type, props){
        layer.newVue();
        store.commit('l_view', 'layer-main');
        store.commit('l_type', 'load');//当前弹出层的类型
        let msg_default_options = {
                btn: '', //msg默认没有按钮
                closeBtn: 0,
                type: type, //加载动画的风格
                shade: [0.1, '#fff'] //默认遮罩层颜色
        };
        store.commit('l_options', tool.copyAndMerge(layer.options, tool.put(msg_default_options, props)));//修改layer弹出层vue实例的数据
    }
    /**
     * 删除当前的弹出层
     */
    layer.close = function(){
        store.commit('l_toggle', false);
    }
})();
