<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>登録商品一覧</title>
<link rel="stylesheet" href="assets/css/admin-lineup.css">
</head>
<body>
<a href="RegisterServlet" class="btn btn-green">✨ 新規商品登録</a>
	<div class="container">
		<a href="ManagerServlet" class="btn btn-back">← メニューに戻る</a>
		<h1>登録商品一覧</h1>

		<%-- メッセージ表示エリア --%>
		<c:if test="${not empty vm.errorMessage}">
			<div class="msg"><c:out value="${vm.errorMessage}" /></div>
		</c:if>
		<%-- 成功メッセージなどがある場合用 --%>
		<c:if test="${not empty vm.message}">
			<div class="msg"><c:out value="${vm.message}" /></div>
		</c:if>

		<table>
			<thead>
				<tr>
					<th>ID</th>
					<th>画像</th>
					<th>商品名</th>
					<th>価格</th>
					<th>操作</th>
					<%-- ★修正1: ここに書いてあった <a href...> を削除しました --%>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="p" items="${vm.productList}">
					<tr>
						<td>${p.productId}</td>
						<td>
							<%-- 画像パスにコンテキストパスを追加 --%>
							<img src="${pageContext.request.contextPath}/${p.productImageUrl}" alt="商品画像">
						</td>
						<td>
							<strong><c:out value="${p.productName}" /></strong><br>
							<span style="font-size: 0.8rem; color: #666;"><c:out value="${p.productDescription}" /></span>
						</td>
						<td>¥ <fmt:formatNumber value="${p.price}" /></td>
						<td style="white-space: nowrap;">
							<%-- ★修正2: リンク先を 'EditProductServlet' に変更 --%>
							<%-- ★修正3: パラメータ名を 'productId' から 'id' に変更 (Servlet側が getParameter("id") なので) --%>
							<a href="EditProductServlet?id=${p.productId}" class="btn btn-edit">編集</a>
							
							<%-- 削除ボタン --%>
							<a href="DeleteProductServlet?id=${p.productId}" 
							   class="btn btn-delete"
							   onclick="return confirm('本当に「${p.productName}」を削除しますか？');">削除</a>
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
	
	<%-- エラーメッセージの表示エリア --%>
<c:if test="${param.error == 'delete_failed'}">
    <div style="color: red; background: #ffdddd; padding: 10px; border-radius: 5px; margin-bottom: 15px;">
        <strong>削除エラー：</strong> 注文履歴などの関連データが存在するため、この商品は削除できません。
    </div>
</c:if>

<c:if test="${param.msg == 'deleted'}">
    <div style="color: green; background: #ddffdd; padding: 10px; border-radius: 5px; margin-bottom: 15px;">
        商品を削除しました。
    </div>
</c:if>
</body>
</html>