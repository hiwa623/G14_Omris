<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>商品編集</title>
<style>
/* register.jspと同じスタイル */
.option-box { border: 1px solid #ccc; padding: 10px; border-radius: 4px; background: #f9f9f9; max-height: 150px; overflow-y: auto; }
.option-item { display: block; margin-bottom: 5px; }
</style>
</head>
<body>

    <h1>商品編集</h1>

    <c:if test="${not empty vm.message}">
        <p style="color: ${vm.success ? 'green' : 'red'}; font-weight: bold;">
            <c:out value="${vm.message}" />
        </p>
    </c:if>

    <form action="EditProductServlet" method="POST" enctype="multipart/form-data">
        <%-- 更新対象のIDを隠しフィールドで送る --%>
        <input type="hidden" name="id" value="${vm.product.productId}">

        <table border="1" cellpadding="10" style="border-collapse: collapse;">
            <tr>
                <th>商品名</th>
                <td><input type="text" name="name" value="${vm.product.productName}" required></td>
            </tr>
            <tr>
                <th>カテゴリ</th>
                <td>
                    <select name="categoryId" required>
                        <c:forEach var="cat" items="${vm.categoryList}">
                            <%-- IDが一致する場合は selected をつける --%>
                            <option value="${cat.categoryId}" 
                                <c:if test="${cat.categoryId == vm.product.categoryId}">selected</c:if>>
                                <c:out value="${cat.categoryName}" />
                            </option>
                        </c:forEach>
                    </select>
                </td>
            </tr>
            <tr>
                <th>関連オプション</th>
                <td>
                    <div class="option-box">
                        <c:forEach var="opt" items="${vm.optionList}">
                            <%-- チェック済みか判定するフラグ --%>
                            <c:set var="isChecked" value="false" />
                            <c:forEach var="selId" items="${vm.selectedOptionIds}">
                                <c:if test="${opt.id == selId}">
                                    <c:set var="isChecked" value="true" />
                                </c:if>
                            </c:forEach>
                            
                            <label class="option-item">
                                <input type="checkbox" name="optionIds" value="${opt.id}" 
                                    <c:if test="${isChecked}">checked</c:if>>
                                <c:out value="${opt.optionName}" /> (+${opt.optionPrice}円)
                            </label>
                        </c:forEach>
                    </div>
                </td>
            </tr>
            <tr>
                <th>単価</th>
                <td><input type="number" name="price" value="${vm.product.price}" min="1" required></td>
            </tr>
            <tr>
                <th>商品画像</th>
                <td>
                    <%-- 現在の画像を表示 --%>
                    <img src="${pageContext.request.contextPath}/${vm.product.productImageUrl}" width="100"><br>
                    <input type="file" name="file" accept="image/*">
                    <br><small>※変更する場合のみファイルを選択してください</small>
                </td>
            </tr>
            <tr>
                <th>商品説明</th>
                <td><textarea name="description" rows="5" cols="40" required><c:out value="${vm.product.productDescription}"/></textarea></td>
            </tr>
            <tr>
                <th>おすすめ</th>
                <td>
                    <label>
                        <input type="checkbox" name="recommend" value="true"
                            <c:if test="${vm.product.favorite}">checked</c:if>>
                        おすすめ商品として表示する
                    </label>
                </td>
            </tr>
        </table>

        <br>
        <button type="submit">更新する</button>
    </form>

    <hr>
    <a href="ManagerServlet">管理メニューに戻る</a>
</body>
</html>