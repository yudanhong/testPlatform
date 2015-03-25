
/**
 * 	
 */
jQuery.fn.extend({
	htmlLoad:function(url,jsurl){
		 var othis=$(this);
		 url=$.getFullUrl(url,false);
		 $.get(url,"html").done(function(data){
			 othis.html(data);
			 if(typeof jsurl!='undefined'){
				 $.loadJs(jsurl.split(","),0);
			 }
		 });
	},clearNoNum:function(type){
		var el=this;
		if(type=='1'){
			 el.val(el.val().replace(/[^\d.]/g,""));//先把非数字的都替换掉，除了数字和.
			 el.val(el.val().replace(/^\./g,"")); //必须保证第一个为数字而不是.
			 el.val(el.val().replace(/\.{2,}/g,".")); //保证只有出现一个.而没有多个.
			 el.val(el.val().replace(".","$#$").replace(/\./g,"").replace("$#$",".")); //保证.只出现一次，而不能出现两次以上
			 return;
		}
		el.val(el.val().replace(/[^\d]/g,""));//先把非数字的都替换掉，除了数字
		if(el.val().length>9){
			 el.val(el.val().substring(0,8));
		}
		return;
	},isnull:function(){
		var othis=$(this);
		for(var i=0;i<othis.length;i++){
			var val=$.trim($(othis[i]).val());
			if(val.length==0)
				return $(othis[i]).attr("isnull");
		}
		return null;
	},
    setOption:function(options){
    	var othis=$(this);
    	var url=$.getFullUrl(options.url,true);
    	if(typeof options.param=='undefined') options.param={};
    	if(typeof options.id=='undefined') options.id="id";
    	if(typeof options.name=='undefined') options.name="name";
    	$.getJSON(url,options.param,function(data){
    		if(data!=null){
    			$.each(data,function(i,n){
    				othis.append("<option value="+n[options.id]+">"+n[options.name]+"</option>");
    			});
    		}
    	});
    },
    validateEmail:function(){
    	var stringValue=$(this).val();
		var myreg = /^([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+\.[a-zA-Z]{2,3}$/;
		if (!myreg.test(stringValue)) {
			return false;
		}else{
			return true;
		}

    },
    validateMobile:function(){
    	var stringValue=$(this).val();
    	var mobile=/^1[3|4|5|8][0-9]\d{8}$/;
		if (!mobile.test(stringValue)) {
			return false;
		}else{
			return true;
		}
    },
    validateArea:function(){
    	var stringValue=$(this).val();
    	var phoneAreaNum = /^\d{3,4}$/;
    	if (!phoneAreaNum.test(stringValue)) {
			return false;
		}else{
			return true;
		}
    },
    validatePhone:function(){
    	var stringValue=$(this).val();
    	var phone =/^(\(\d{3,4}\)|\d{3,4}-)?\d{7,8}$/;
    	if (!phone.test(stringValue)) {
			return false;
		}else{
			return true;
		}
    },
    validateLianxi:function(){
    	var stringValue=$(this).val();
    	var phone =/^(\(\d{3,4}\)|\d{3,4}-)?\d{7,8}$|^\d{11}$/;
    	if (!phone.test(stringValue)) {
			return false;
		}else{
			return true;
		}
    },
    inputNumAndWord:function(){
		var el=this;
		el.val(el.val().replace(/[\u4e00-\u9fa5]{1,100}/,''));//先把非数字的都替换掉，除了数字和.
		return;
    },
    validatePostalCode:function(){
    	var stringValue=$(this).val();
    	var reg =/^[1-9][0-9]{5}$/;
    	if (!reg.test(stringValue)) {
			return false;
		}else{
			return true;
		}
    },
    validateFax:function(){
    	var stringValue=$(this).val();
    	var reg =/^[+]{0,1}(\d){1,3}[ ]?([-]?((\d)|[ ]){1,12})+$/;
    	if(!reg.test(stringValue)){
    		return false;
    	}else{
    		return true;
    	}
    },validateIpAddr:function(){
    	var stringValue=$(this).val();
    	var reg =/^(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])$/;
    	if(!reg.test(stringValue)){
    		return false;
    	}else{
    		return true;
    	}
    }
    
});

jQuery.extend({
	query:{
		get:function(name){
			var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
			var r = window.location.search.substr(1).match(reg);  //匹配目标参数
			if (r!=null) return unescape(r[2]); return null; //返回参数值
		}
	},
	basePath:function(){
		var curWwwPath = window.document.location.href;
        var pathName = window.document.location.pathname;
        var pos = curWwwPath.indexOf(pathName);
        var localhostPaht = curWwwPath.substring(0, pos);
        var projectName = pathName.substring(0, pathName.substr(1).indexOf('/') + 1);
        return(localhostPaht + projectName+"/");
	},
	getFullUrl:function(url,cache){
		if(url.indexOf("http://")==-1) url=$.basePath()+url;
		if(cache){
			if(url.indexOf("?")==-1) url+="?fresh=" + Math.random();
			else url+="&fresh=" + Math.random();
		}
		return url;
	},
	loadJs:function(jsurl,i){
		var url=jsurl[i];
		if(url.indexOf("http://")==-1) url=$.basePath()+url;
	    $.cachedScript(url).done(function(script, textStatus) {
			 if(textStatus=='success'){
				 if(i<jsurl.length-1) $.loadJs(jsurl,i+1);
			 }
		 });
	},
	cachedScript:function(url, options){
		 options = $.extend(options || {}, { dataType: "script", cache: true,  url: url });
		 return jQuery.ajax(options);
	},	//查看图片
	showImage:function (src){
		if(src.indexOf("http://")==-1) src=$("#basePath").val()+src;
		$.fancybox({
			//'orig'			: $(this),
			'padding'		: 0,
			'href'			: src,
			'title'   		: '',
			'transitionIn'	: 'elastic',
			'transitionOut'	: 'elastic'
		});
	},
	/**
	 * {url:地址,不带http
	 *  w:宽度
	 *  h:高度
	 *  title:标题
	 *  }
	 * @param options
	 */
	updateLayer:function(options){
		var w=options.w,h=options.h,title=options.title,url=options.url;
		url=$.getFullUrl(url,true);
		if(!$.isFunction(options.end)){
			options.end=function(){};
		}
	 	var left=($(window).width()-w)/2;
	 	var top=($(window).height()-h)/2;
		return $.layer({
			type: 2,
			title: [title,true],
			closeBtn :[0, true],
			shade: [0.1,'#000', true],
			area: [w+'px',h+'px'],
			border : [4, 1, '#1155c2', true],
			offset: [top+"px",left+"px"],
			iframe: {src: url},
			end:options.end
		});
	},
	htmlLayer:function(options){
		var w=options.w,h=options.h,title=options.title;
		if(!$.isFunction(options.success)){
			options.success=function(){};
		}
		var left=($(window).width()-w)/2;
	 	var top=($(window).height()-h)/2;
	 	return $.layer({
			type: 1,
			title: [title,true],
			closeBtn :[0, true],
			shade: [0.1,'#000', true],
			area: [w+'px',h+'px'],
			border : [4, 1, '#1155c2', true],
			offset: [top+"px",left+"px"],
			page: {dom: '#treeDisplay', html:''},
			success:options.success
		});
		
	},
    /*错误提示  */
	errAlert:function(alertMsg,width,height){
		if(!$.isNumeric(width)) width=300;
		if(!$.isNumeric(height)) height=150;
		 return $.alert(alertMsg,5,width,height);
	},
	/**
	 * 警告,提示
	 */
	warnAlert:function(alertMsg){
	   return $.alert(alertMsg,8,350,150);
	},
	/*成功提示*/
	successAlert:function(alertMsg,end){
		return $.alert(alertMsg,1,300,150,end);
	},
	/*confirm*/
	confirmAlert:function(conMsg,conYes,width,height,conNo){
		if(!$.isNumeric(width)) width=380;
		if(!$.isNumeric(height)) height=150;
		return $.layer({
			dialog : {msg : conMsg, type : 4, btns : 2, yes : conYes,no:conNo},
			title : "提示信息",
			area : [width+'px',height+'px'],
			border : [4 , 1 , '#125fae', true]
		}); 
	},
    alert:function(alertMsg,alertType,w,h,end){
    	var opt={dialog : {msg:alertMsg,type:alertType},border : [4 , 1 , '#125fae', true],title:'提示信息'};
    	if($.isNumeric(w)&&$.isNumeric(h)) opt.area=[w+'',h+''];
    	if($.isFunction(end)) opt.end=end;
    	return $.layer(opt);
    }
});
function isTheSame(arr){
	if(arr.length<=1) return true;
	 for(var i=0;i<arr.length-1;i++){
		for(var j=i+1;j<arr.length;j++){
			if(arr[i]==arr[j]){
				return false;
			}
		}
	} 
	
}
function isNotTheSame(arr){
	if(arr.length<=1) return true;
	 for(var i=0;i<arr.length-1;i++){
		for(var j=i+1;j<arr.length;j++){
			if(arr[i]!=arr[j]){
				return false;
			}
		}
	} 
	
}
function getTopTime(){
	 var today = new Date();
	 var vYear = today.getFullYear();
	 var vMon = today.getMonth() + 1;
	 var vDay = today.getDate();
	 var h = today.getHours(); 
	 var m = today.getMinutes(); 
	 var se = today.getSeconds();
	 var time=vYear+"-"+(vMon<10 ? "0" + vMon : vMon)+"-"+(vDay<10 ? "0"+ vDay : vDay);
	// var time=vYear+"-"+(vMon<10 ? "0" + vMon : vMon)+"-"+(vDay<10 ? "0"+ vDay : vDay)+" "+(h<10 ? "0"+ h : h)+":"+(m<10 ? "0" + m : m)+":"+(se<10 ? "0" +se : se);
	 $("#showTime").html(time);
}

function conterHeight(el){
	var conH=$(el).height();
	var wh=$(window).height()-116;
	if(wh>conH){
		$(".conter").height(wh);
		$(".t_box").height(wh);
	}
	else{
		$(".conter").height(conH+5);
		$(".t_box").height(conH-4);
	}	
}
//通过秒返回时分秒--字符串
function minToHms(time){
	var hms = "";
	if (time < 60) {
		hms = time + "秒";
	}else if(time >=60 && time < 60*60){
		var m = Math.floor(time/60);
		var s = time-m*60
		hms = m + "分" + s + "秒";
	}else {
		var h = Math.floor(time/3600);
		var m = Math.floor((time - h*3600)/60);
		var s = time - h*3600 - m*60;
		hms = h + "时" + m + "分" + s + "秒";
	}
	return hms;
}
function closeQueryShadow(){
	layer.close(n);
}
function openQueryShadow(){
	n = $.layer({
	    type : 3
	});
}
Array.prototype.del=function(n) {　//n表示第几项，从0开始算起。
	  //prototype为对象原型，注意这里为对象增加自定义方法的方法。
	  if(n<0){
	  　　return this;
	  } else {
	  　　return this.slice(0,n).concat(this.slice(n+1,this.length));
	  }
	 }
	  // 确定元素所在的索引
	  Array.prototype.indexOf = function(val) {
	      for (var i = 0; i < this.length; i++) {
	          if (this[i] == val) return i;
	      }
	      return -1;
	  };

