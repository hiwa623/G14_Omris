<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>商品メニュー新規登録</title>
</head>
<body>

    <h1>商品メニュー新規登録</h1>
    
    <%-- ViewModelからのメッセージ表示 --%>
    <c:if test="${not empty viewModel}">
        <p style="color: ${viewModel.success ? 'green' : 'red'}; font-weight: bold;">
            <c:out value="${viewModel.message}"/>
            <c:if test="${not empty requestScope.imageUploadMessage}">
                <br>(${requestScope.imageUploadMessage})
            </c:if>
        </p>
    </c:if>

    <form action="RegisterServlet" method="POST" enctype="multipart/form-data">
        
        <table>
            <tr>
                <th>商品名（必須）</th>
                <td><input type="text" name="productName" required></td>
            </tr>
            <tr>
                <th>カテゴリID（必須）</th>
                <td><input type="number" name="categoryId" min="1" required></td>
            </tr>
            <tr>
                <th>商品単価（必須）</th>
                <td><input type="number" name="price" min="1" required></td>
            </tr>
            <tr>
                <th>**商品画像**</th>
                <td>
                    <input type="file" name="image" accept="image/*">
                </td>
            </tr>
            <tr>
                <th>商品説明（必須）</th>
                <td>
                    <textarea name="productDescription" rows="8" cols="50" required></textarea>
                </td>
            </tr>
            <tr>
                <th>おすすめ選択</th>
                <td>
                    <input type="checkbox" name="isRecommended" value="true"> おすすめ商品として表示する
                </td>
            </tr>
        </table>
        
        <br>
        <button type="submit">上記内容で登録する</button>
        
    </form>
    
    <hr>
    <a href="ManagerServlet">管理メニューに戻る</a>
    <a href="index.jsp">トップに戻る</a>

</body>
</html>