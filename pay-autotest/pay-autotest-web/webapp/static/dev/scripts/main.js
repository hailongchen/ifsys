/** * * Created by marszed on 16/3/28. */if(seajs) {	var environment = "dev";	// seajs 的简单配置	seajs.config({		//规定加载js文件的基础路径		base: "./static/" + environment + "/",		//别名配置 继续base基础路径		alias: {			"global": "scripts/compent/global.js",			"jquery": "scripts/thrid_party/jquery-cmd-1.11.1.js",			"template": "scripts/thrid_party/template.js",			"common": "scripts/jq_compent/jquery.common.js",			"intInfo": "scripts/template/intInfo.js",			"proInfo": "scripts/template/proInfo.js",			"sysInfo": "scripts/template/sysInfo.js",			"usecase": "scripts/template/usecase.js",			"fixlayer": "scripts/template/fixlayer.js",			"zclip": "scripts/thrid_party/jquery.zclip.js",			"bootstrap": "scripts/thrid_party/bootstrap.min.js",			"layer": "scripts/thrid_party/layer.js"		},		//路径配置 方便跨目录调用		paths: {			//'arale': 'https://a.alipayobjects.com/arale'		},		//预先加载		preload: ['jquery'],		//map,批量更新时间戳		map: [[/^(.*\.(?:css|js))(.*)$/i, '$1?v=20160325']],		// 文件编码		charset: 'utf-8'	});	// 加载入口模块	seajs.use(["global", 'jquery', 'template', 'common', 'intInfo', 'sysInfo', 'proInfo', 'usecase', 'fixlayer', 'bootstrap', 'layer'], function () {		console.log("js模块已全部加载完毕!");	});}else{	console.log('需先加载seaJs!!!');}