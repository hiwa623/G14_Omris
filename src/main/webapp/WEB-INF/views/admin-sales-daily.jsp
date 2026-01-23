<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>日別売上レポート</title>
<style>
    /* 簡易CSS */
    body { font-family: sans-serif; padding: 20px; background: #f4f6f9; }
    .container { max-width: 800px; margin: 0 auto; background: white; padding: 20px; border-radius: 8px; }
    .search-box { background: #eee; padding: 15px; margin-bottom: 20px; border-radius: 5px; }
    table { width: 100%; border-collapse: collapse; }
    th, td { padding: 10px; border-bottom: 1px solid #ddd; }
    .btn { padding: 5px 15px; background: #3498db; color: white; border: none; cursor: pointer; }
    .nav-links { margin-bottom: 20px; }
    .nav-links a { margin-right: 15px; text-decoration: none; color: #333; font-weight: bold; border-bottom: 2px solid transparent; }
    .nav-links a.active { border-bottom: 2px solid #3498db; color: #3498db; }
</style>
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