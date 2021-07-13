function calc(x, time) {
	return new Promise((resolve, reject) =>{
		setTimeout(()=>{
			resolve(x)
		}, time)
	})
}

async function add() {
	const a = await calc(3, 1000)
	console.log("param 1:" + a)
	const b = await calc(4, 1000)
	console.log("param 2:" + b)
	const c = await calc(5, 1000)
	console.log("param 3:" + c)
	const d = a + b +c  
	console.log("add result:" + d)
}