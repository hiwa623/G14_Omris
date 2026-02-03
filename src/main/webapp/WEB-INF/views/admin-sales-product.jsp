<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>商品別売上ランキング</title>
<link rel="stylesheet" href="assets/css/admin-sales-product.css">
</head>
<body>
    <div class="container">
        <div class="nav-links">
            <a href="SalesDailyServlet">📅 日別売上推移</a>
            <a href="SalesProductServlet" class="active">🏆 商品別ランキング</a>
        </div>

        <h1>商品別売上ランキング</h1>

        <div class="search-box">
            <form action="SalesProductServlet" method="get">
                <label>期間：</label>
                <input type="date" name="startDate" value="${startDate}" required>
                ～
                <input type="date" name="endDate" value="${endDate}" required>
                <button type="submit" class="btn">ランキング更新</button>
            </form>
        </div>

        <table>
            <thead>
                <tr>
                    <th>順位</th>
                    <th>商品名</th>
                    <th style="text-align: right;">販売数</th>
                    <th style="text-align: right;">売上金額</th>
                </tr>
            </thead>
            <tbody>
                <c:choose>
                    <c:when test="${empty rankingList}">
                        <tr><td colspan="4">指定期間のデータはありません</td></tr>
                    </c:when>
                    <c:otherwise>
                        <c:forEach var="item" items="${rankingList}" varStatus="status">
                            <tr>
                                <td>${status.count}位</td>
                                <td>${item.productName}</td>
                                <td style="text-align: right;">${item.totalQuantity}</td>
                                <td style="text-align: right;">
                                    ¥ <fmt:formatNumber value="${item.totalSales}" pattern="#,###" />
                                </td>
                            </tr>
                        </c:forEach>
                    </c:otherwise>
                </c:choose>
            </tbody>
        </table>
        
        <div style="margin-top:20px;">
            <a href="ManagerServlet">管理者メニューへ戻る</a>
        </div>
    </div>
</body>
</html>