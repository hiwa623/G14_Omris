<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>注文履歴</title>
<style>
    body { font-family: sans-serif; background: #f5f5f5; margin: 0; padding: 20px; color: #333; }
    .container { max-width: 600px; margin: 0 auto; background: #fff; padding: 20px; border-radius: 8px; box-shadow: 0 2px 5px rgba(0,0,0,0.1); }
    h1 { text-align: center; color: #2c3e50; font-size: 1.5rem;}
    
    /* 注文ごとのカードデザイン */
    .order-card { border: 1px solid #ddd; border-radius: 8px; margin-bottom: 20px; padding: 15px; background: #fff; }
    .order-header { display: flex; justify-content: space-between; border-bottom: 2px solid #eee; padding-bottom: 10px; margin-bottom: 10px; font-weight: bold; color: #555; }
    
    /* 明細行のデザイン */
    .detail-item { padding: 8px 0; border-bottom: 1px dashed #eee; display: flex; justify-content: space-between; align-items: flex-start; }
    .item-info { flex: 1; }
    .item-name { font-weight: bold; font-size: 1.1rem; }
    .item-opts { font-size: 0.85rem; color: #7f8c8d; margin-left: 10px; display: block; }
    
    /* ステータスバッジのデザイン */
    .status-badge {
        display: inline-block; padding: 2px 8px; border-radius: 4px;
        font-size: 0.8rem; color: white; margin-left: 5px; vertical-align: middle;
        white-space: nowrap;
    }
    
    .order-total { text-align: right; margin-top: 10px; font-size: 1.2rem; font-weight: bold; color: #e67e22; }
    
    .btn-back { display: block; width: 100%; text-align: center; background: #95a5a6; color: white; padding: 15px; text-decoration: none; border-radius: 5px; margin-top: 20px; box-sizing: border-box;}
</style>
</head>
<body>

<div class="container">
    <h1>注文履歴</h1>
    
    <c:if test="${empty historyList}">
        <p style="text-align:center; padding: 20px;">まだ注文履歴がありません。</p>
    </c:if>

    <c:forEach var="order" items="${historyList}">
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