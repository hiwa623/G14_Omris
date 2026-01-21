<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>カートに追加しました</title>
<style>
    body { font-family: sans-serif; background: #333; display:flex; justify-content:center; align-items:center; height:100vh; margin:0; }
    .msg-box { background: white; padding: 40px; border-radius: 10px; text-align: center; width: 90%; max-width: 400px; }
    h2 { color: #27ae60; }
    .btn { display: block; width: 100%; padding: 15px; margin: 10px 0; text-decoration: none; border-radius: 5px; box-sizing: border-box; font-size: 1.1rem; }
    .btn-menu { background: #e67e22; color: white; }
    .btn-cart { background: #2980b9; color: white; }
</style>
</head>
<body>

<div class="msg-box">
    <h2>カートに追加しました！</h2>
    <p>ご注文ありがとうございます。</p>
    
    <a href="MenuListServlet" class="btn btn-menu">メニューに戻って買い物を続ける</a>
    
    <a href="CartServlet" class="btn btn-cart">カートを確認して注文へ</a>
</div>

</body>
</html>