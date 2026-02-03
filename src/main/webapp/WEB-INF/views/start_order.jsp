<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link rel="stylesheet" href="assets/css/start_order.css">
<title>テーブル番号入力</title>

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
                <option value="3">C-4</option>
                <option value="3">C-5</option>
                
                <option value="4">B-1</option>
                <option value="5">B-2</option>
                <option value="5">B-3</option>
                <option value="5">B-4</option>
                <option value="5">B-5</option>
                <option value="5">B-6</option>
            </select>
            
            <button type="submit">次へ進む</button>
        </form>
    </div>
</body>
</html>