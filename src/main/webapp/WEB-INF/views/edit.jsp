<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>商品メニュー編集</title>
</head>
<body>

    <h1>商品メニュー編集</h1>
    
    <%-- メッセージ表示 (登録時と同じ仕組みを利用) --%>
    <c:if test="${not empty message}">
        <p style="color: red; font-weight: bold;"><c:out value="${message}"/></p>
    </c:if>

    <form action="EditServlet" method="POST" enctype="multipart/form-data">
        <%-- ★重要：どの商品を編集しているかServletに伝えるための隠しフィールド --%>
        <input type="hidden" name="productId" value="${product.productId}">

        <table>
            <tr>
                <th>商品名（必須）</th>
                <td><input type="text" name="productName" value="${product.productName}" required></td>
            </tr>
            <tr>
                <th>カテゴリID（必須）</th>
                <td><input type="number" name="categoryId" value="${product.categoryId}" min="1" required></td>
            </tr>
            <tr>
                <th>商品単価（必須）</th>
                <td><input type="number" name="price" value="${product.price}" min="1" required></td>
            </tr>
            <tr>
                <th>現在の画像</th>
                <td>
                    <c:if test="${not empty product.productImageUrl}">
                        <img src="${pageContext.request.contextPath}/${product.productImageUrl}" width="100"><br>
                    </c:if>
                    <input type="file" name="image" accept="image/*">
                    <p style="font-size: 0.8em;">※変更する場合のみ選択してください</p>
                </td>
            </tr>
            <tr>
                <th>商品説明（必須）</th>
                <td>
                    <textarea name="productDescription" rows="8" cols="50" required>${product.productDescription}</textarea>
                </td>
            </tr>
            <tr>
                <th>おすすめ選択</th>
                <td>
                    <%-- チェックボックスの初期選択状態を制御 --%>
                    <input type="checkbox" name="isRecommended" value="true" ${product.favorite ? 'checked' : ''}> おすすめ商品として表示する
                </td>
            </tr>
        </table>
        
        <br>
        <button type="submit">この内容で更新する</button>
        
    </form>
    
    <hr>
    <a href="AdminLineupServlet">商品一覧に戻る</a>
    
    // admin-lineup.jsp の末尾のスクリプトを拡張
<script>
    const urlParams = new URLSearchParams(window.location.search);
    const status = urlParams.get('status');
    
    if (status === 'deleted') {
        alert('商品を削除しました。');
    } else if (status === 'updated') {
        alert('商品情報を更新しました。');
    }
    
    if (status) {
        window.history.replaceState(null, '', window.location.pathname);
    }
</script>
</body>
</html>