<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="viewmodel.CashRegisterCompleteViewModel" %>
<%
    CashRegisterCompleteViewModel vm = (CashRegisterCompleteViewModel) request.getAttribute("viewModel");
    if (vm == null) { vm = new CashRegisterCompleteViewModel(); }
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>会計完了</title>
<style>
    body { font-family: "Meiryo", sans-serif; text-align: center; padding: 50px; background-color: #f9f9f9; }
    .container { 
        background-color: white; padding: 40px; border-radius: 10px; 
        box-shadow: 0 4px 15px rgba(0,0,0,0.1); display: inline-block; 
    }
    h1 { color: #2e7d32; margin-bottom: 20px; }
    p { font-size: 1.2em; color: #555; }
    
    .btn-home {
        display: inline-block; margin-top: 30px; padding: 15px 30px;
        background-color: #007bff; color: white; text-decoration: none;
        border-radius: 5px; font-weight: bold; font-size: 1.1em;
        transition: background 0.3s;
    }
    .btn-home:hover { background-color: #0056b3; }
</style>
</head>
<body>

    <div class="container">
        <h1><%= vm.getTitle() %></h1>
        <p><%= vm.getMessage() %></p>
        
        <a href="CashRegisterInputServlet" class="btn-home">トップ画面へ戻る</a>
    </div>

</body>
</html>