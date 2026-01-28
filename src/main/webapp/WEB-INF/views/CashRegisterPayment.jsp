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
<style>
    body { font-family: "Meiryo", sans-serif; background-color: #eee; padding: 20px; display: flex; justify-content: center; height: 90vh; }
    
    /* レイアウト枠: 左右分割 */
    .main-container { display: flex; width: 900px; gap: 20px; }
    
    /* 左カラム: 注文詳細 */
    .left-panel { flex: 1; background: white; padding: 20px; border-radius: 8px; box-shadow: 0 4px 10px rgba(0,0,0,0.1); overflow-y: auto; }
    
    /* 右カラム: 会計操作 */
    .right-panel { flex: 0 0 380px; background: white; padding: 20px; border-radius: 8px; box-shadow: 0 4px 10px rgba(0,0,0,0.1); }

    /* テーブルデザイン */
    .order-table { width: 100%; border-collapse: collapse; margin-top: 10px; }
    .order-table th { background: #f0f0f0; padding: 10px; border-bottom: 2px solid #ddd; text-align: left; }
    .order-table td { padding: 10px; border-bottom: 1px solid #eee; }
    .text-right { text-align: right; }
    
    /* 金額表示 */
    .total-area { text-align: center; margin-bottom: 20px; background: #333; color: #fff; padding: 15px; border-radius: 5px; }
    .total-label { font-size: 0.9em; opacity: 0.8; }
    .total-price { font-size: 2.2em; font-weight: bold; }

    /* 入力エリア */
    .input-display { 
        width: 90%; height: 50px; font-size: 24px; text-align: right; padding: 5px 10px; 
        border: 2px solid #2e7d32; border-radius: 5px; background-color: #fff; margin-bottom: 15px;
    }
    
    /* キーパッド */
    .keypad { display: grid; grid-template-columns: repeat(3, 1fr); gap: 8px; }
    .btn-key { padding: 15px; font-size: 1.2em; border: 1px solid #ccc; background-color: #f9f9f9; border-radius: 4px; cursor: pointer; }
    .btn-key:active { background-color: #ddd; }
    
    .btn-clear { background-color: #ef9a9a; color: #b71c1c; }
    .btn-submit { 
        grid-column: span 3; padding: 20px; font-size: 1.4em; background-color: #2e7d32; 
        color: white; border: none; border-radius: 5px; cursor: pointer; font-weight: bold; margin-top: 10px;
    }
    
    .change-info { text-align: center; height: 30px; font-size: 1.2em; font-weight: bold; margin-bottom: 10px;}
</style>
<script>
    const totalPrice = <%= vm.getTotalPrice() %>;

    function addNumber(num) {
        const input = document.getElementById('depositInput');
        if (input.value === '0' && num === '0') return;
        input.value += num;
        calcChange();
    }
    function clearInput() {
        document.getElementById('depositInput').value = '';
        calcChange();
    }
    function calcChange() {
        const input = document.getElementById('depositInput');
        const changeDisplay = document.getElementById('changeDisplay');
        const deposit = parseInt(input.value) || 0;
        const change = deposit - totalPrice;
        
        if (deposit > 0) {
            if (change >= 0) {
                changeDisplay.innerText = "お釣り: ¥ " + change.toLocaleString();
                changeDisplay.style.color = "blue";
            } else {
                changeDisplay.innerText = "不足: ¥ " + Math.abs(change).toLocaleString();
                changeDisplay.style.color = "red";
            }
        } else {
            changeDisplay.innerText = "";
        }
    }
</script>
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