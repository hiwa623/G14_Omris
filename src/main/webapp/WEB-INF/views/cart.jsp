<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>ショッピングカート</title>
</head>
<body>
    <h1>ショッピングカート</h1>

    <%-- エラーメッセージがある場合に表示 --%>
    <c:if test="${not empty vm.errorMessage}">
        <p style="color:red;">${vm.errorMessage}</p>
    </c:if>

    <c:choose>
        <c:when test="${empty vm.cartItems}">
            <p>カートに商品はありません。</p>
        </c:when>
        <c:otherwise>
            <table border="1">
                <tr>
                    <th>商品名</th>
                    <th>価格</th>
                    <th>数量</th>
                    <th>小計</th>
                    <th>操作</th>
                </tr>
                <c:forEach var="item" items="${vm.cartItems}">
                    <tr>
                        <td>${item.product.productName}</td>
                        <td>${item.product.price}円</td>
                        <td>${item.quantity}</td>
                        <td>${item.product.price * item.quantity}円</td>
                        <td>
                            <%-- 削除ボタン：CartServletに対して「削除(delete)」アクションを送る --%>
                            <form action="CartServlet" method="post" style="display:inline;">
                                <input type="hidden" name="productId" value="${item.product.productId}">
                                <input type="hidden" name="action" value="delete">
                                <button type="submit" onclick="return confirm('この商品をカートから削除しますか？');">削除</button>
                            </form>
                        </td>
                    </tr>
                </c:forEach>
            </table>
            
            <h3>合計金額: ${vm.totalPrice}円</h3>

            <%-- 注文確定ボタン --%>
            <form action="OrderServlet" method="get">
                <input type="submit" value="注文を確定する">
            </form>
        </c:otherwise>
    </c:choose>

    <br>
    <a href="LineupServlet">買い物を続ける</a>
</body>
</html>