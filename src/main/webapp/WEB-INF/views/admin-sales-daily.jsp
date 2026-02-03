<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>日別売上レポート</title>
<link rel="stylesheet" href="assets/css/admin-sales-daily.css">
</head>
<body>
    <div class="container">
        <div class="nav-links">
            <a href="SalesDailyServlet" class="active">📅 日別売上推移</a>
            <a href="SalesProductServlet">🏆 商品別ランキング!</a>
        </div>

        <h1>日別売上レポート</h1>

        <div class="search-box">
            <form action="SalesDailyServlet" method="get">
                <label>期間：</label>
                <input type="date" name="startDate" value="${startDate}" required>
                ～
                <input type="date" name="endDate" value="${endDate}" required>
                <button type="submit" class="btn">表示</button>
            </form>
        </div>

        <table>
            <thead>
                <tr>
                    <th>日付</th>
                    <th style="text-align: right;">売上合計</th>
                </tr>
            </thead>
            <tbody>
                <c:choose>
                    <c:when test="${empty dailySalesList}">
                        <tr><td colspan="2">指定期間のデータはありません</td></tr>
                    </c:when>
                    <c:otherwise>
                        <c:forEach var="day" items="${dailySalesList}">
                            <tr>
                                <td>${day.salesDate}</td>
                                <td style="text-align: right; font-weight: bold;">
                                    ¥ <fmt:formatNumber value="${day.totalSales}" pattern="#,###" />
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