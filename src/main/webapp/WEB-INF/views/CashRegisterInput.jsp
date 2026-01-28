<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>レジ - テーブル選択</title>
<style>
    body { font-family: "Meiryo", sans-serif; display: flex; justify-content: center; align-items: center; height: 100vh; margin: 0; background-color: #f0f2f5; }
    .card { background: white; padding: 40px; border-radius: 8px; box-shadow: 0 4px 12px rgba(0,0,0,0.15); width: 350px; text-align: center; }
    h2 { color: #333; margin-bottom: 25px; }
    
    input[type="text"] {
        width: 100%; padding: 15px; font-size: 1.5em; text-align: center;
        border: 2px solid #ddd; border-radius: 5px; margin-bottom: 20px; box-sizing: border-box;
    }
    input[type="text"]:focus { border-color: #007bff; outline: none; }
    
    button {
        width: 100%; padding: 15px; font-size: 1.2em; background-color: #007bff;
        color: white; border: none; border-radius: 5px; cursor: pointer; transition: 0.3s;
    }
    button:hover { background-color: #0056b3; }
</style>
</head>
<body>

<div class="card">
    <h2>会計テーブル入力</h2>
    
    <form action="CashRegisterInputServlet" method="post">
        <input type="text" name="table_no" placeholder="C-1" required autofocus>
        
        <button type="submit">次へ進む</button>
    </form>
</div>

</body>
</html>