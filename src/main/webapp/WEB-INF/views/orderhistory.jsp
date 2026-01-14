<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>注文履歴 | お弁当予約システム</title>
<style>
    body { font-family: sans-serif; background-color: #f4f4f4; margin: 0; padding: 20px; }
    .container { max-width: 800px; margin: auto; background: white; padding: 20px; border-radius: 8px; box-shadow: 0 2px 5px rgba(0,0,0,0.1); }
    h1 { color: #333; border-bottom: 2px solid #ffcc00; padding-bottom: 10px; }
    
    /* 注文ごとのカードスタイル */
    .order-card { border: 1px solid #ddd; margin-bottom: 20px; border-radius: 5px; overflow: hidden; }
    .order-header { background-color: #f9f9f9; padding: 10px 15px; border-bottom: 1px solid #ddd; display: flex; justify-content: space-between; align-items: center; }
    .order-id { font-weight: bold; color: #666; }
    .order-date { color: #333; }
    
    /* 明細部分のスタイル */
    .order-details { padding: 15px; }
    .detail-item { display: flex; justify-content: space-between; padding: 5px 0; border-bottom: 1px dashed #eee; }
    .detail-item:last-child { border-bottom: none; }
    
    .total-section { text-align: right; padding: 10px 15px; background-color: #fff9e6; font-weight: bold; font-size: 1.1em; }
    .no-history { text-align: center; padding: 50px; color: #888; }
    .back-link { display: inline-block; margin-top: 20px; color: #007bff; text-decoration: none; }
    .back-link:hover { text-decoration: underline; }
</style>
</head>
<body>

<div class="container">
    <h1>注文履歴</h1>

    <%-- ViewModelからメッセージがある場合（履歴なし等）に表示 --%>
    <c:if test="${not empty vm.message}">
        <div class="no-history">
            <p>${vm.message}</p>
        </div>
    </c:if>

    <%-- 注文履歴リストのループ処理 --%>
    <c:forEach var="order" items="${vm.historyList}">
        <div class="order-card">
            <div class="order-header">
                <span class="order-id">注文番号: #${order.orderId}</span>
                <span class="order-date">
                    注文日時: <fmt:formatDate value="${order.orderDate}" pattern="yyyy年MM月dd日 HH:mm"/>
                </span>
            </div>
            
            <div class="order-details">
                <%-- 注文に紐づく明細（OrderDetailDTO）のループ処理 --%>
                <c:forEach var="detail" items="${order.details}">
                    <div class="detail-item">
                        <span class="product-name">${detail.productName}</span>
                        <span class="quantity-price">
                            ${detail.quantity}個 × <fmt:formatNumber value="${detail.price}" pattern="¥#,###"/>
                        </span>
                    </div>
                </c:forEach>
            </div>
            
            <div class="total-section">
                合計金額: <fmt:formatNumber value="${order.totalPrice}" pattern="¥#,###"/>
            </div>
        </div>
    </c:forEach>

    <a href="LineupServlet" class="back-link">← 商品一覧に戻る</a>
</div>

</body>
</html>