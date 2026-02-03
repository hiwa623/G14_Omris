<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="viewmodel.CashRegisterPaymentViewModel" %>
<%@ page import="model.dto.BillItemDTO" %>
<%@ page import="java.util.List" %>
<%
    CashRegisterPaymentViewModel vm = (CashRegisterPaymentViewModel) request.getAttribute("viewModel");
    if (vm == null) { vm = new CashRegisterPaymentViewModel(); }
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>お会計確認・支払い</title>
<link rel="stylesheet" href="assets/css/CashRegisterPayment.css">
<script src="assets/js/CashRegisterPayment.js"></script>
</head>
<body>

<div class="main-container">
    
    <div class="left-panel">
        <h3>テーブル: <%= vm.getTableNo() %> 注文詳細</h3>
        <table class="order-table">
            <thead>
                <tr>
                    <th>商品名</th>
                    <th class="text-right">単価</th>
                    <th class="text-right">数量</th>
                    <th class="text-right">小計</th>
                </tr>
            </thead>
            <tbody>
                <% for(BillItemDTO item : vm.getBillItems()) { %>
                <tr>
                    <td><%= item.getProductName() %></td>
                    <td class="text-right">¥<%= String.format("%,d", item.getPrice()) %></td>
                    <td class="text-right"><%= item.getQuantity() %></td>
                    <td class="text-right">¥<%= String.format("%,d", item.getSubTotal()) %></td>
                </tr>
                <% } %>
            </tbody>
        </table>
    </div>

    <div class="right-panel">
        <form action="CashRegisterPaymentServlet" method="post">
            <input type="hidden" name="table_no" value="<%= vm.getTableNo() %>">
			<input type="hidden" id="js-total-price" value="<%= vm.getTotalPrice() %>">
            <div class="total-area">
                <div class="total-label">ご請求金額</div>
                <div class="total-price">¥ <%= String.format("%,d", vm.getTotalPrice()) %></div>
            </div>

            <div class="change-info" id="changeDisplay"></div>

            <input type="number" id="depositInput" name="deposit_amount" class="input-display" placeholder="お預かり" readonly required>

            <div class="keypad">
                <button type="button" class="btn-key" onclick="addNumber('7')">7</button>
                <button type="button" class="btn-key" onclick="addNumber('8')">8</button>
                <button type="button" class="btn-key" onclick="addNumber('9')">9</button>
                
                <button type="button" class="btn-key" onclick="addNumber('4')">4</button>
                <button type="button" class="btn-key" onclick="addNumber('5')">5</button>
                <button type="button" class="btn-key" onclick="addNumber('6')">6</button>
                
                <button type="button" class="btn-key" onclick="addNumber('1')">1</button>
                <button type="button" class="btn-key" onclick="addNumber('2')">2</button>
                <button type="button" class="btn-key" onclick="addNumber('3')">3</button>
                
                <button type="button" class="btn-key" onclick="addNumber('0')">0</button>
                <button type="button" class="btn-key" onclick="addNumber('00')">00</button>
                <button type="button" class="btn-key btn-clear" onclick="clearInput()">C</button>
                
                <button type="submit" class="btn-submit">会計確定</button>
            </div>
        </form>
    </div>
</div>

</body>
</html>