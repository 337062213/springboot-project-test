//弹出隐藏层
function ShowDiv(show_div,bg_div){
	var scrollHeight = document.body.scrollHeight; //文档高度
	var screenHeight = window.outerheight;
	var maskHeight = screenHeight;
	if(Number(scrollHeight)>Number(screenHeight)){
		maskHeight = scrollHeight;
	}
	document.getElementById(bg_div).style.height=maskHeight+'px';	
	document.getElementById(show_div).style.display='block';
	document.getElementById(bg_div).style.display='block';
};
//关闭弹出层
function CloseDiv(show_div,bg_div){
	document.getElementById("label").value = '';
	document.getElementById(show_div).style.display='none';
	document.getElementById(bg_div).style.display='none';
};