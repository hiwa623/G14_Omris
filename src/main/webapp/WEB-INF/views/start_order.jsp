<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>テーブル番号入力</title>
<style>
    /* CSSは共通で使えます */
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
    select, button {
        width: 100%;
        padding: 15px;
        font-size: 1.2rem;
        margin-top: 10px;
        border-radius: 5px;
    }
    button {
        background-color: #d35400;
        color: white;
        border: none;
        cursor: pointer;
    }
</style>
</head>
<body>
    <div class="container">
        <h1>いらっしゃいませ</h1>
        <p>テーブル番号を選択してください</p>
        
        <form action="StartOrderServlet" method="post">
            <select name="tableId" required>
                <option value="" disabled selected>選択してください</option>
                <option value="1">C-1</option>
                <option value="2">C-2</option>
                <option value="3">C-3</option>
                <option value="4">B-101</option>
                <option value="5">B-102</option>
            </select>
            
            <button type="submit">次へ進む</button>
        </form>
    </div>
</body>
</html>