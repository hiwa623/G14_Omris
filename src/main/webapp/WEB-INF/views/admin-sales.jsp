<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>売上管理 - 管理画面</title>
<style>
    body { font-family: sans-serif; background: #f4f6f9; padding: 20px; }
    .container { max-width: 1000px; margin: 0 auto; }
    h1 { text-align: center; color: #333; }
    
    .dashboard-grid {
        display: grid;
        grid-template-columns: 1fr 1fr;
        gap: 20px;
        margin-top: 20px;
    }
    
    .card {
        background: white;
        padding: 20px;
        border-radius: 8px;
        box-shadow: 0 2px 5px rgba(0,0,0,0.1);
    }
    
    .card h2 { border-bottom: 2px solid #ddd; padding-bottom: 10px; margin-top: 0; color: #555; }
    
    table { width: 100%; border-collapse: collapse; margin-top: 10px; }
    th, td { padding: 10px; border-bottom: 1px solid #eee; text-align: left; }
    th { background-color: #f8f9fa; }
    .text-right { text-align: right; }
    .total-highlight { font-weight: bold; color: #2c3e50; }

    .btn-back {
        display: inline-block;
        margin-top: 20px;
        padding: 10px 20px;
        background: #95a5a6;
        color: white;
        text-decoration: none;
        border-radius: 4px;
    }
</style>
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