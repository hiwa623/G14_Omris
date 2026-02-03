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
<link rel="stylesheet" href="assets/css/CashRegisterComplete.css">
</head>
<body>

    <div class="container">
        <h1><%= vm.getTitle() %></h1>
        <p><%= vm.getMessage() %></p>
        
        <a href="CashRegisterInputServlet" class="btn-home">トップ画面へ戻る</a>
    </div>

</body>
</html>