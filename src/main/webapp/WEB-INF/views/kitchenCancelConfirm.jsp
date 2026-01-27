<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>キッチン｜完了キャンセル確認</title>
  <style>
    body { font-family: sans-serif; }
    .wrap { width: 900px; margin: 60px auto; }
    .card { border: 1px solid #ddd; padding: 90px 40px; text-align: center; }
    .title { font-size: 22px; margin-bottom: 40px; }
    .btns { display:flex; justify-content:center; gap: 26px; }
    .btnLink {
      display:inline-block; padding: 16px 42px; border-radius: 8px;
      text-decoration:none; background: #d9d9d9; color:#000;
    }
    .btn {
      padding: 16px 42px; border-radius: 8px; border: 0; cursor: pointer;
      background: #d9d9d9;
    }
  </style>
</head>

<body>
<div class="wrap">
  <div class="card">
    <div class="title">変更してよろしいですか</div>
    <div class="btns">
      <a class="btnLink" href="${pageContext.request.contextPath}/kitchen/completed">いいえ</a>
      <form method="post" action="${pageContext.request.contextPath}/kitchen/cancelComplete">
        <input type="hidden" name="orderDetailId" value="${orderDetailId}">
        <button class="btn" type="submit">はい</button>
      </form>
    </div>
  </div>
</div>
</body>
</html>
