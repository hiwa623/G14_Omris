<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>人数入力</title>
<style>
    /* start_order.jsp と同じスタイル */
    body {
        font-family: "Helvetica Neue", Arial, sans-serif;
        display: flex;
        justify-content: center;
        align-items: center;
        height: 100vh;
        background-color: #333;
        margin: 0;
        color: white;
    }
    .container {
        background-color: white;
        color: #333;
        padding: 40px;
        border-radius: 10px;
        text-align: center;
        width: 90%;
        max-width: 400px;
    }
    input, button {
        width: 100%;
        padding: 15px;
        font-size: 1.2rem;
        margin-top: 10px;
        border-radius: 5px;
        box-sizing: border-box;
    }
    button {
        background-color: #27ae60; /* 色を変えてみました */
        color: white;
        border: none;
        cursor: pointer;
    }
</style>
</head>
<body>
    <div class="container">
        <h1>人数入力</h1>
        <p>Table No. <%= session.getAttribute("tableId") %></p>
        <p>ご利用人数を入力してください</p>
        
        <form action="ConfirmCountServlet" method="post">
            <input type="number" name="customerCount" min="1" max="99" value="1" required>
            <button type="submit">メニューを見る</button>
        </form>
    </div>
</body>
</html>