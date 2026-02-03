<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>売上管理 - 管理画面</title>
<link rel="stylesheet" href="assets/css/admin-sales.css">
</head>
<body>
    <div class="container">
        <h1>売上管理ダッシュボード</h1>

        <div class="dashboard-grid">
            <div class="card">
                <h2>📅 日別売上レポート</h2>
                <table>
                    <thead>
                        <tr>
                            <th>日付</th>
                            <th class="text-right">売上合計</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:choose>
                            <c:when test="${empty dailySalesList}">
                                <tr><td colspan="2">データがありません</td></tr>
                            </c:when>
                            <c:otherwise>
                                <c:forEach var="day" items="${dailySalesList}">
                                    <tr>
                                        <td>${day.salesDate}</td>
                                        <td class="text-right total-highlight">
                                            ¥ <fmt:formatNumber value="${day.totalSales}" pattern="#,###" />
                                        </td>
                                    </tr>
                                </c:forEach>
                            </c:otherwise>
                        </c:choose>
                    </tbody>
                </table>
            </div>

            <div class="card">
                <h2>🏆 商品別売上ランキング</h2>
                <table>
                    <thead>
                        <tr>
                            <th>順位</th>
                            <th>商品名</th>
                            <th class="text-right">数量</th>
                            <th class="text-right">金額</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:choose>
                            <c:when test="${empty rankingList}">
                                <tr><td colspan="4">データがありません</td></tr>
                            </c:when>
                            <c:otherwise>
                                <c:forEach var="item" items="${rankingList}" varStatus="status">
                                    <tr>
                                        <td>${status.count}位</td>
                                        <td>${item.productName}</td>
                                        <td class="text-right">${item.totalQuantity}個</td>
                                        <td class="text-right">
                                            ¥ <fmt:formatNumber value="${item.totalSales}" pattern="#,###" />
                                        </td>
                                    </tr>
                                </c:forEach>
                            </c:otherwise>
                        </c:choose>
                    </tbody>
                </table>
            </div>
        </div>

        <a href="ManagerServlet" class="btn-back">管理者メニューに戻る</a>
    </div>
</body>
</html>