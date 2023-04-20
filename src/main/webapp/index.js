var socket=null;



//socket.heartbeatTimeout=20000;
//socket.heartbeatInterval=60000;





function connect(){
	/*let socket = new SockJS("/server")
	
	//stompClient = Stomp.over(socket)
	
	stompClient.connect({},function(frame){
		console.log("Connected : "+frame)
		
		$("#name-from").addClass('d-none')
		$("#chat-room").removeClass('d-none')
		
		//subscribe
		stompClient.subscribe("/topic/return-to",function(response){
			showMessage(JSON.parse(response.body))
		})
	})*/
	
	console.log("In connect method");
	socket=io({transportOptions:{
	Polling:{
	extraHeaders:
	{'X-Host':window.location.hostname,'X-Port' : window.location.port||8080},
	'Authorization': 'Bearer <token>'
	}
},
//'query': 'token=' + Auth.getToken(),
transports:['polling'],
path:'/socketio',
allowEIO3 : true,
withCredentials:true,
reconnectionDelay : 1000,
reconnectionAttempts:21000,
timeout:30000,
namespace:"/"
}
);
	
	
	
	socket.on("connect",()=>{
		console.log("connected to server : ");
		$("#name-from").addClass('d-none')
		$("#chat-room").removeClass('d-none')
		socket.emit('create','room')
		
		socket.on("RoomJoined",(args)=>{
			console.log(args);
		})
		
		socket.on("message_error",(err)=>{
			console.log(`Message sending failed due to ${err.message}`);
		})
	})
	
	socket.on("connected",(args)=>{
		console.log(args);
	})
	
	
	
	socket.on("connect_error", (err) => {
            console.log(`connect_error due to ${err.message}`);
        });
        
//    socket.io.engine.on("upgrade",()=>{
//	const upgradedTransport = socket.io.engine.transport.name;
//	console.log(upgradedTransport);
//})
	console.log("In after connect method");
	
	
	
}

function showMessage(message){
	
	$("#message-container-table").prepend(`<tr><td><b>${message.messageSender} : </b> ${message.messageContent}</td></tr>`)
	
}

function sendMessage(){
	let jsonOb={
		messageSender : localStorage.getItem("name"),
		messageContent : $("#message-value").val()
	}
	//console.log(jsonOb)
	//stompClient.send("/app/message",{},JSON.stringify(jsonOb));
	socket.emit("message",jsonOb);
	socket.on("messageDelivered",(args)=>{
		showMessage(args)
	})
}


$(document).ready(e=>{
	
	$("#login").click(()=>{
		
		let name = $("#name-value").val()
		localStorage.setItem("name",name)
		console.log(name);
		connect();
	
	})
	
	$("#send-btn").click(()=>{
		sendMessage();
	})
	
	$("#logout").click(()=>{
		localStorage.removeItem("name")
		/*if(stompClient!==null){
			stompClient.disconnect()
			$("#name-from").removeClass('d-none')
			$("#chat-room").addClass('d-none')
		}*/
		socket.on("disconnect",()=>{
			("#name-from").removeClass('d-none')
			$("#chat-room").addClass('d-none')
		})
	})

}
)
