function uuid(){
	let uuid = "";
	for(let i=0;i<32;i++){
		let item = (Math.random()*0x10).toString(16).substring(0,1);
		uuid += item;
		if(i==7||i==11||i==15||i==19){
			uuid += "-";
		}
	}
	return uuid;
}