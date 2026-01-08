<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%-- 【修正】URIを最新版に変更 --%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>商品メニュー編集</title>
</head>
<body>

    <h1>商品メニュー編集</h1>
    
    <c:if test="${not empty message}">
        <p style="color: red; font-weight: bold;"><c:out value="${message}"/></p>
    </c:if>

    <form action="EditServlet" method="POST" enctype="multipart/form-data">
        <input type="hidden" name="productId" value="${product.productId}">

        <table>
            <tr>
                <th>商品名（必須）</th>
                <td><input type="text" name="productName" value="${product.productName}" required></td>
            </tr>
            <tr>
                <th>カテゴリ名（必須）</th>
                <td>
                    <%-- 【修正】数字入力から select 形式に変更 --%>
                    <select name="categoryId" required>
                        <c:forEach var="cat" items="${categoryList}">
                            <option value="${cat.categoryId}" ${cat.categoryId == product.categoryId ? 'selected' : ''}>
                                <c:out value="${cat.categoryName}" />
                            </option>
                        </c:forEach>
                    </select>
                </td>
            </tr>
            <tr>
                <th>商品単価（必須）</th>
                <td><input type="number" name="price" value="${product.price}" min="1" required></td>
            </tr>
            <tr>
                <th>現在の画像</th>
                <td>
                    <c:if test="${not empty product.productImageUrl}">
                        <%-- cite: 1 (ProductDTO.java) --%>
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
                    <%-- cite: 1 (ProductDTO.java) の favorite フィールドを使用 --%>
                    <input type="checkbox" name="isRecommended" value="true" ${product.favorite ? 'checked' : ''}> おすすめ商品として表示する
                </td>
            </tr>
        </table>
        
        <br>
        <button type="submit">この内容で更新する</button>
        
    </form>
    
    <hr>
    <%-- 管理メニューの名前が ManagerServlet か AdminLineupServlet か確認してください --%>
    <a href="ManagerServlet">管理メニューに戻る</a>
    
</body>
</html>