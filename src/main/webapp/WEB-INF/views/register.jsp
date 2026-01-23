<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>商品メニュー新規登録</title>
<style>
/* オプション一覧を見やすくするための簡易スタイル */
.option-box {
	border: 1px solid #ccc;
	padding: 10px;
	border-radius: 4px;
	background: #f9f9f9;
	max-height: 150px;
	overflow-y: auto;
}
.option-item {
	display: block;
	margin-bottom: 5px;
}
</style>
</head>
<body>

	<h1>商品メニュー新規登録</h1>

	<%-- 
       ★修正: Servletで request.setAttribute("vm", vm); としている前提です。
       viewModel ではなく vm を使用します。
    --%>
	<c:if test="${not empty vm.message}">
		<p style="color: ${vm.success ? 'green' : 'red'}; font-weight: bold;">
			<c:out value="${vm.message}" />
		</p>
	</c:if>

	<%-- 画像を送るために enctype が必要です（既存通りでOK） --%>
	<form action="RegisterServlet" method="POST" enctype="multipart/form-data">

		<table border="1" cellpadding="10" style="border-collapse: collapse;">
			<tr>
				<th>商品名（必須）</th>
				<%-- Servlet側: request.getParameter("name") に合わせる --%>
				<td><input type="text" name="name" required></td>
			</tr>
			<tr>
				<th>カテゴリ名（必須）</th>
				<td>
					<select name="categoryId" required>
						<option value="">カテゴリを選択してください</option>
						<%-- 
                           ★修正: ViewModelの中のリストを使います
                           vm.categoryList
                        --%>
						<c:forEach var="cat" items="${vm.categoryList}">
							<option value="${cat.categoryId}">
								<c:out value="${cat.categoryName}" />
							</option>
						</c:forEach>
					</select>
				</td>
			</tr>
			
			<%-- ★追加: オプション（トッピング）選択エリア --%>
			<tr>
				<th>関連オプション</th>
				<td>
					<div class="option-box">
						<c:choose>
							<c:when test="${empty vm.optionList}">
								<span style="color:#666;">登録可能なオプションがありません</span>
							</c:when>
							<c:otherwise>
								<c:forEach var="opt" items="${vm.optionList}">
									<label class="option-item">
										<%-- Servlet側: request.getParameterValues("optionIds") --%>
										<input type="checkbox" name="optionIds" value="${opt.id}">
										<c:out value="${opt.optionName}" /> (+${opt.optionPrice}円)
									</label>
								</c:forEach>
							</c:otherwise>
						</c:choose>
					</div>
					<small>※この商品で選択可能にするオプションにチェックを入れてください</small>
				</td>
			</tr>

			<tr>
				<th>商品単価（必須）</th>
				<%-- Servlet側: request.getParameter("price") --%>
				<td><input type="number" name="price" min="1" required></td>
			</tr>
			<tr>
				<th>商品画像（必須）</th>
				<%-- Servlet側: request.getPart("file") に合わせる --%>
				<td><input type="file" name="file" accept="image/*" required></td>
			</tr>
			<tr>
				<th>商品説明（必須）</th>
				<%-- Servlet側: request.getParameter("description") に合わせる --%>
				<td><textarea name="description" rows="5" cols="40" required></textarea></td>
			</tr>
			<tr>
				<th>おすすめ選択</th>
				<%-- Servlet側: request.getParameter("recommend") --%>
				<td>
					<label>
						<input type="checkbox" name="recommend" value="true">
						おすすめ商品として表示する
					</label>
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