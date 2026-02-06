<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>カートの確認</title>
<link rel="stylesheet" href="assets/css/cart_list.css">
</head>
<body>

	<div class="cart-container">
		<div class="cart-header">
			<h1>カート(ご注文内容の確認)</h1>
			<p>
				テーブル番号: <strong>${sessionScope.tableId}</strong> / 人数: <strong>${sessionScope.customerCount}</strong>名
			</p>
		</div>

		<c:choose>
			<c:when test="${empty vm.cartItems}">
				<div class="empty-msg">
					<p>カートに商品が入っていません。</p>
					<a href="MenuListServlet" class="btn btn-back">メニューに戻る</a>
				</div>
			</c:when>

			<c:otherwise>
				<div class="cart-scroll-area">
					<table>
						<thead>
							<tr>
								<th>商品名 / オプション</th>
								<th>単価</th>
								<th>　　数量</th>
								<%-- ★削除: <th class="price-col">小計</th> --%>
								<th>削除</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="item" items="${vm.cartItems}" varStatus="status">
								<tr>
									<td><strong>${item.product.productName}</strong> <c:forEach
											var="opt" items="${item.optionList}">
											<div class="option-text">+ ${opt.optionName}
												(¥${opt.optionPrice})</div>
										</c:forEach></td>
									<td>¥ ${item.product.price}</td>
									<td>
										<form action="UpdateCartServlet" method="post"
											class="qty-form">
											<input type="hidden" name="action" value="update"> <input
												type="hidden" name="index" value="${status.index}">

											<button type="submit" name="quantity"
												value="${item.quantity - 1}" class="btn-qty"
												${item.quantity <= 1 ? 'disabled' : ''}>－</button>

											<span class="qty-display">${item.quantity}</span>

											<button type="submit" name="quantity"
												value="${item.quantity + 1}" class="btn-qty">＋</button>
										</form>
									</td>

									<%-- ★削除: ここにあった小計計算（c:set 等）を含む <td>...</td> を丸ごと削除 --%>

									<td class="action-col">
										<form action="UpdateCartServlet" method="post">
											<input type="hidden" name="action" value="delete"> <input
												type="hidden" name="index" value="${status.index}">
											<button type="submit" class="btn-delete"
												onclick="return confirm('削除してもよろしいですか？');">削除</button>
										</form>
									</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
				<div class="cart-footer">
					<div class="total-area">
						合計金額: <span class="total-price">¥ <fmt:formatNumber
								value="${vm.totalPrice}" /></span>
					</div>

					<form action="PlaceOrderServlet" method="post" style="width: 100%;">
						<div class="btn-group">
							<a href="MenuListServlet" class="btn btn-back">買い物を続ける</a>
							<button type="submit" class="btn btn-order"
								onclick="return confirm('注文を確定してもよろしいですか？');">注文を確定する</button>
						</div>
					</form>
				</div>
			</c:otherwise>
		</c:choose>
	</div>

</body>
</html>
