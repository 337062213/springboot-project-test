
function downLoad() {
	let baseURL = "http://localhost:8081/";
	window.location.href = baseURL + "api/servlet/download2?filename=" + "2.txt";
}

function downloadFile() {
  let baseURL = "http://localhost:8081/";
  var request = new XMLHttpRequest();
  request.responseType = "blob";//定义响应类型
  request.open("GET", baseURL + "api/servlet/download2?filename=" + "2.txt");
  request.onload = function () {
	var url = window.URL.createObjectURL(this.response);
	var a = document.createElement("a");
	document.body.appendChild(a);
	a.href = url;
	a.download = "2.txt";
	a.click();
  }
  request.send();
}

function openNewWindow(url){
	window.open (url, 'newwindow', 'height=800, width=1500, top=100, left=350,toolbar=no, menubar=no, scrollbars=no, resizable=no,location=n o, status=no')
}

function confirmWindow(){
	var random = Math.random();
	var number = Math.round(random*500 + 500);
	promptValue = "";
	//有取消按钮
	var confirmValue = confirm("Please confirm whether to continue or quit");
	if(confirmValue){
		alert(number);
		if(number>=750){
			alert("function");
			//有取消按钮
			confirm("Confirm");
			//弹出输入框
			promptValue = prompt("prompt");
			while("admin"!==promptValue && promptValue!==null){
				promptValue = prompt("prompt");
			}
			myVar = setInterval(
				function(){ myTimer() }, 1000
			);
		}else{
			alert("arrow");
			//有取消按钮
			confirm("Confirm");
			//弹出输入框
			promptValue = prompt("prompt");
			while("admin"!==promptValue && promptValue!==null){
				promptValue = prompt("prompt");
			}
			myVar = setInterval(
				() => { myTimer() }, 1000
			);
		}
	}
}

function refreshPage(){
   window.location.reload();
}