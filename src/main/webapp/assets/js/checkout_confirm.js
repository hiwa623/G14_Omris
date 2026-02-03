setTimeout(function() {
	// StartOrderServletへ移動
	// (そこでCookie判定が行われ、保存済みなら customer_count.jsp へ自動転送されます)
	window.location.href = "StartOrderServlet";
}, 150000); // 150000ミリ秒＝2分半