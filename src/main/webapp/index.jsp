<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="viewmodel.LoginViewModel" %>
<%
    LoginViewModel vm = (LoginViewModel) request.getAttribute("viewModel");
    if (vm == null) {
        vm = new LoginViewModel();
    }
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>ログイン | 飲食店管理システム</title>
    <link rel="stylesheet" href="assets/css/index.css">
</head>
<body>

    <%-- 
       ▼ ここが右上のリンク集です
    --%>
    <div class="nav-container">
        <a href="StartOrderServlet" class="nav-link">商品一覧（注文）</a>
        <a href="KitchenServlet" class="nav-link">キッチン画面</a>
        <a href="HallBeforeServlet" class="nav-link">ホール画面</a>
        <a href="CashRegisterInputServlet" class="nav-link">会計画面</a>
    </div>


    <div class="login-container">
        <h2>システムログイン</h2>

        <% if (vm.getErrorMessage() != null && !vm.getErrorMessage().isEmpty()) { %>
            <div class="error-msg">
                <%= vm.getErrorMessage() %>
            </div>
        <% } %>

        <form action="login" method="post">
            <div class="form-group">
                <input type="text" name="loginId" placeholder="ログインID" 
                       value="<%= (vm.getLoginId() != null) ? vm.getLoginId() : "" %>" required>
            </div>
            <div class="form-group">
                <input type="password" name="password" placeholder="パスワード" required>
            </div>
            <div class="form-group">
                <button type="submit">ログイン</button>
            </div>
        </form>
    </div>

</body>
</html>