<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>カートの確認</title>
<style>
    body { font-family: "Helvetica Neue", Arial, sans-serif; background: #f5f5f5; padding: 20px; color: #333; }
    .cart-container { max-width: 900px; margin: 0 auto; background: white; padding: 20px; border-radius: 10px; box-shadow: 0 2px 10px rgba(0,0,0,0.1); }
    h1 { text-align: center; }
    
    table { width: 100%; border-collapse: collapse; margin-bottom: 20px; }
    th, td { padding: 15px; text-align: left; border-bottom: 1px solid #ddd; vertical-align: middle; }
    th { background-color: #f9f9f9; }
    .price-col { text-align: right; white-space: nowrap;}
    .action-col { text-align: center; width: 150px; }
    
    .option-text { font-size: 0.9rem; color: #666; margin-left: 10px; display: block; }
    .total-area { text-align: right; font-size: 1.5rem; font-weight: bold; margin-bottom: 30px; }
    .total-price { color: #d35400; }
    
    /* ボタン類のデザイン */
    .btn-group { display: flex; gap: 10px; }
    .btn { flex: 1; padding: 15px; text-align: center; border-radius: 5px; text-decoration: none; font-size: 1.1rem; border: none; cursor: pointer; }
    .btn-back { background: #95a5a6; color: white; }
    .btn-order { background: #d35400; color: white; font-weight: bold; }
    
    /* 変更・削除ボタン用のスタイル */
    .qty-input { width: 50px; padding: 5px; font-size: 1rem; text-align: center; }
    .btn-update { background-color: #3498db; color: white; border: none; padding: 5px 10px; border-radius: 3px; cursor: pointer; font-size: 0.9rem; margin-left: 5px;}
    .btn-delete { background-color: #e74c3c; color: white; border: none; padding: 8px 15px; border-radius: 3px; cursor: pointer; font-size: 0.9rem;}
    
    /* フォームをインライン表示にするための調整 */
    .form-inline { display: inline-flex; align-items: center; }
</style>
</head>
<body>

<div class="cart-container">
    <h1>ご注文内容の確認</h1>
    <p>テーブル番号: <strong>${sessionScope.tableId}</strong> / 人数: <strong>${sessionScope.customerCount}</strong>名</p>

    <c:choose>
        <c:when test="${empty vm.cartItems}">
            <p style="text-align:center; padding: 30px;">カートに商品が入っていません。</p>
            <div style="text-align:center;">
                 <a href="MenuListServlet" class="btn btn-back" style="display:inline-block; width:200px;">メニューに戻る</a>
            </div>
        </c:when>

        <c:otherwise>
            <table>
                <thead>
                    <tr>
                        <th>商品名 / オプション</th>
                        <th>単価</th>
                        <th>数量</th>
                        <th class="price-col">小計</th>
                        <th>削除</th>
                    </tr>
                </thead>
                <tbody>
                    <%-- varStatus="status" でループのインデックス(0,1,2...)を取得 --%>
                    <c:forEach var="item" items="${vm.cartItems}" varStatus="status">
                        <tr>
                            <td>
                                <strong>${item.product.productName}</strong>
                                <c:forEach var="opt" items="${item.optionList}">
                                    <span class="option-text">+ ${opt.optionName} (¥${opt.optionPrice})</span>
                                </c:forEach>
                            </td>
                            <td>¥ ${item.product.price}</td>
                            
                            <td>
                                <form action="UpdateCartServlet" method="post" class="form-inline">
                                    <input type="hidden" name="action" value="update">
                                    <input type="hidden" name="index" value="${status.index}">
                                    
                                    <input type="number" name="quantity" value="${item.quantity}" min="1" max="99" class="qty-input">
                                    <butto	n type="submit" class="btn-update">変更</button>
                                </form>
                            </td>
                            
                            <td class="price-col">
                                <c:set var="itemTotal" value="${item.product.price * item.quantity}" />
                                <c:forEach var="opt" items="${item.optionList}">
                                    <c:set var="itemTotal" value="${itemTotal + (opt.optionPrice * item.quantity)}" />
                                </c:forEach>
                                ¥ <fmt:formatNumber value="${itemTotal}" />
                            </td>
                            
                            <td class="action-col">
                                <form action="UpdateCartServlet" method="post">
                                    <input type="hidden" name="action" value="delete">
                                    <input type="hidden" name="index" value="${status.index}">
                                    <button type="submit" class="btn-delete" onclick="return confirm('削除してもよろしいですか？');">削除</button>
                                </form>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>

            <div class="total-area">
                合計金額: 合計金額: <span class="total-price">¥ <fmt:formatNumber value="${vm.totalPrice}" /></span>
            </div>
            
            <form action="PlaceOrderServlet" method="post">
                <div class="btn-group">
                    <a href="MenuListServlet" class="btn btn-back">買い物を続ける</a>
                    <button type="submit" class="btn btn-order">注文を確定する</button>
                </div>
            </form>
        </c:otherwise>
    </c:choose>
</div>

</body>
</html>