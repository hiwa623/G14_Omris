<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>注文履歴</title>
<link rel="stylesheet" href="assets/css/order_history.css">
</head>
<body>

<div class="container">
    <h1>注文履歴</h1>
    
    <c:if test="${empty vm.historyList}">
        <p style="text-align:center; padding: 20px;">まだ注文履歴がありません。</p>
    </c:if>

    <c:forEach var="order" items="${vm.historyList}">
        <div class="order-card">
            <div class="order-header">
                <span><fmt:formatDate value="${order.orderDate}" pattern="HH:mm" /> 注文</span>
                <span>No.${order.orderId}</span>
            </div>
            
            <%-- 商品ごとのループ --%>
            <c:forEach var="detail" items="${order.details}">
                <div class="detail-item">
                    <div class="item-info">
                        <span class="item-name">${detail.productName}</span> 
                        <span style="font-size:0.9rem;">x ${detail.quantity}</span>

                        <%-- ステータス表示（DBのIDで色分け） --%>
                        <span class="status-badge" style="
                            <c:choose>
                                <c:when test="${detail.statusId == 'SERVED'}">background:#95a5a6;</c:when>  <%-- 提供済(グレー) --%>
                                <c:when test="${detail.statusId == 'COOKING'}">background:#e74c3c;</c:when> <%-- 調理中(赤) --%>
                                <c:otherwise>background:#2ecc71;</c:otherwise>  <%-- 未調理(緑) --%>
                            </c:choose>
                        ">
                            ${detail.statusName}
                        </span>

                        <%-- オプションがあれば表示 --%>
                        <c:forEach var="opt" items="${detail.optionNames}">
                            <span class="item-opts">+ ${opt}</span>
                        </c:forEach>
                    </div>
                    <div>
                        ¥<fmt:formatNumber value="${detail.price}" />
                    </div>
                </div>
            </c:forEach>
            
            <div class="order-total">
                小計: ¥<fmt:formatNumber value="${order.totalPrice}" />
            </div>
        </div>
    </c:forEach>

    <a href="MenuListServlet" class="btn-back">メニューに戻る</a>
</div>

</body>
</html>