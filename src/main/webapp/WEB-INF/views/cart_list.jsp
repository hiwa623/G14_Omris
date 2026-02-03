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
		<h1>ご注文内容の確認</h1>
		<p>
			テーブル番号: <strong>${sessionScope.tableId}</strong> / 人数: <strong>${sessionScope.customerCount}</strong>名
		</p>

		<c:choose>
			<c:when test="${empty vm.cartItems}">
				<p style="text-align: center; padding: 30px;">カートに商品が入っていません。</p>
				<div style="text-align: center;">
					<a href="MenuListServlet" class="btn btn-back"
						style="display: inline-block; width: 200px;">メニューに戻る</a>
				</div>
			</c:when>

			<c:otherwise>
				<table>
					<thead>
						<tr>
							<th>商品名 / オプション</th>
							<th>単価</th>
							<th>数量</th>
							<th class="price-col">小計</th>
							<th>削除</th>
						</tr>
					</thead>
					<tbody>
						<%-- varStatus="status" でループのインデックス(0,1,2...)を取得 --%>
						<c:forEach var="item" items="${vm.cartItems}" varStatus="status">
							<tr>
								<td><strong>${item.product.productName}</strong> <c:forEach
										var="opt" items="${item.optionList}">
										<div class="option-text">+ ${opt.optionName}
											(¥${opt.optionPrice})</div>
									</c:forEach></td>
								<td>
									<%-- 単価表示 --%> ¥ ${item.product.price}
								</td>

								<td>
									<form action="UpdateCartServlet" method="post"
										class="form-inline">
										<input type="hidden" name="action" value="update"> <input
											type="hidden" name="index" value="${status.index}"> <input
											type="number" name="quantity" value="${item.quantity}"
											min="1" max="99" class="qty-input">

										<%-- ★修正: butto n のスペースを削除しました --%>
										<button type="submit" class="btn-update">変更</button>
									</form>
								</td>

								<td class="price-col">
									<%-- 小計計算（商品単価×個数 ＋ オプション単価×個数） --%> <c:set var="itemTotal"
										value="${item.product.price * item.quantity}" /> <c:forEach
										var="opt" items="${item.optionList}">
										<c:set var="itemTotal"
											value="${itemTotal + (opt.optionPrice * item.quantity)}" />
									</c:forEach> ¥ <fmt:formatNumber value="${itemTotal}" />
								</td>

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

				<div class="total-area">
					合計金額: <span class="total-price">¥ <fmt:formatNumber
							value="${vm.totalPrice}" /></span>
				</div>

				<form action="PlaceOrderServlet" method="post">
					<div class="btn-group">
						<a href="MenuListServlet" class="btn btn-back">買い物を続ける</a>
						<%-- ★修正: 注文確定時の確認ダイアログを追加しました --%>
						<button type="submit" class="btn btn-order"
							onclick="return confirm('注文を確定してもよろしいですか？');">注文を確定する</button>
					</div>
				</form>
			</c:otherwise>
		</c:choose>
	</div>

</body>
</html>