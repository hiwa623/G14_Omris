<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>商品管理一覧</title>
</head>
<body>
    <h1>商品一覧</h1>

    <div>
        <button onclick="location.href='RegisterServlet'">追加</button>
    </div>

    <table border="1">
        <thead>
            <tr>
                <th>商品名</th>
                <th>カテゴリー</th>
                <th>値段</th>
                <th>削除</th>
                <th>編集</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="product" items="${productList}">
                <tr>
                    <td>${product.productName}</td>
                    <td>${product.categoryId}</td>
                    <td>${product.price}円</td>
                    <td>
                        <form action="DeleteProductServlet" method="post"
                            onsubmit="return confirm('本当に削除しますか？');">
                            <input type="hidden" name="productId"
                                value="${product.productId}"> <input type="submit"
                                value="🗑️">
                        </form>
                    </td>
                    <td><a href="EditServlet?productId=${product.productId}">＞</a>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>

    <p>
        <a href="ManagerServlet">管理メニューに戻る</a>
    </p>

    <script>
        // URLのパラメータを確認する
        const urlParams = new URLSearchParams(window.location.search);

        // もし status=deleted が含まれていたらダイアログを出す
        if (urlParams.get('status') === 'deleted') {
            alert('商品を削除しました。');

            // URLからパラメータを消してスッキリさせる（ブラウザの履歴操作）
            // これをしないと、再読み込みした時にまたアラートが出てしまうため
            window.history.replaceState(null, '', window.location.pathname);
        }
    </script>
</body>
</html>