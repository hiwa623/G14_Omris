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
            <th>おすすめ</th>
            <th>商品名</th>
            <th>カテゴリー</th> <%-- IDから名前に変更 --%>
            <th>値段</th>
            <th>削除</th>
            <th>編集</th>
        </tr>
    </thead>
    <%-- tbodyタグをなくし、直接 tr を並べる --%>
    <c:forEach var="product" items="${productList}">
        <tr>
            <td style="text-align: center;">
                <%-- おすすめ(favorite)が true の場合に ★ を表示 --%>
                <c:if test="${product.favorite}">
                    <span style="color: orange; font-weight: bold;">★</span>
                </c:if>
            </td>
            <td><c:out value="${product.productName}" /></td>
            
            <%-- categoryId ではなく、追加した categoryName を表示 --%>
            <td><c:out value="${product.categoryName}" /></td>
            
            <td><c:out value="${product.price}" />円</td>
            <td>
                <form action="DeleteProductServlet" method="post"
                    onsubmit="return confirm('本当に削除しますか？');">
                    <input type="hidden" name="productId"
                        value="${product.productId}"> 
                    <input type="submit" value="🗑️">
                </form>
            </td>
            <td><a href="EditServlet?productId=${product.productId}">＞</a></td>
        </tr>
    </c:forEach>
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