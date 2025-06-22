var stompClient = null;

function setConnected(connected) {
	$("#connect").prop("disabled", connected);
	$("#disconnect").prop("disabled", !connected);
	if (connected) {
		$("#conversation").show();
	}
	else {
		$("#conversation").hide();
	}
	$("#userinfo").html("");
}

function connect() {
	console.log('Conectando ao websocket');
	var socket = new SockJS('/websocket');
	stompClient = Stomp.over(socket);
	stompClient.connect({}, function(frame) {
		setConnected(true);
		console.log('Connected: ' + frame);
		
		//Stomp de notificações do estoque		
		stompClient.subscribe('/estoque/notificar', function(greeting) {
			showGreeting(JSON.parse(greeting.body).content);
		});


	});
	
}

function disconnect() {
	if (stompClient !== null) {
		stompClient.disconnect();
	}
	setConnected(false);
	console.log("Disconnected");
}

function sendName() {
	stompClient.send("/app/notificar", {}, JSON.stringify({ 'name': $("#name").val() }));
}

function showGreeting(message) {
	console.log('Mensagem recebida 1: ' + message);
	$("#userinfo").append("<tr><td>" + message + "</td></tr>");		
	rc();
}

$(function() {
	$("form").on('submit', function(e) {
		e.preventDefault();
	});
	$("#connect").click(function() { connect(); });
	$("#disconnect").click(function() { disconnect(); });
	$("#send").click(function() { sendName(); });
});

