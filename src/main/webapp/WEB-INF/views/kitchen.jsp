<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>キッチン｜注文料理一覧</title>

  <!-- ✅ 追加機能：自動更新（注文が飛んできたら勝手に増える） -->
  <script>
    setInterval(() => {
      location.reload();
    }, 3000); // 3秒ごと（好みで変更OK）
  </script>

  <style>
    body { font-family: sans-serif; }
    .wrap { width: 760px; margin: 40px auto; }
    .header { display:flex; justify-content: space-between; align-items:center; margin-bottom: 14px; }
    .tabs { display:flex; gap: 10px; }
    .tab {
      padding: 10px 14px; border-radius: 8px; text-decoration:none;
      border: 1px solid #ddd; color:#000; background:#fff;
    }
    .tab.active { background:#f2f2f2; font-weight: 700; }
    .count { color:#666; }

    .table { border: 1px solid #ddd; border-radius: 10px; overflow: hidden; }
    .row { display:grid; grid-template-columns: 1fr 120px 170px; align-items:center; padding: 14px 16px; border-top:1px solid #eee; }
    .row.head { background:#f2f2f2; font-weight:700; border-top:none; }
    .qty { text-align:center; }
    .toggleBox { text-align:center; }

    .toggleLink{
      display:inline-block; width: 46px; height: 26px; border-radius: 999px;
      background:#f1b5b5; position: relative; border:1px solid #d8aaaa;
      vertical-align: middle;
    }
    .toggleLink::after{
      content:""; width: 20px; height: 20px; border-radius: 50%;
      background:#000; position:absolute; top: 2px; left: 3px;
    }

    .empty { padding: 20px; text-align:center; color:#666; }
    .error { background:#ffe7e7; border:1px solid #ffb3b3; padding: 10px 12px; border-radius: 8px; margin-bottom: 10px; }
  </style>
</head>

<body>
<div class="wrap">

  <div class="header">
    <div class="tabs">
      <a class="tab active" href="${pageContext.request.contextPath}/kitchen">注文料理一覧</a>
      <a class="tab" href="${pageContext.request.contextPath}/kitchen/completed">完了済み商品</a>
    </div>

    <div class="count">
      注文件数：
      <c:out value="${kitchenList == null ? 0 : kitchenList.size()}" />件
    </div>
  </div>

  <c:if test="${not empty error}">
    <div class="error"><c:out value="${error}" /></div>
  </c:if>

  <div class="table">
    <div class="row head">
      <div>商品名</div>
      <div class="qty">注文数</div>
      <div class="toggleBox">調理完了チェック</div>
    </div>

    <c:choose>
      <c:when test="${empty kitchenList}">
        <div class="empty">注文料理はありません</div>
      </c:when>

      <c:otherwise>
        <c:forEach var="item" items="${kitchenList}">
          <div class="row">
            <div><c:out value="${item.productName}" /></div>
            <div class="qty"><c:out value="${item.quantityText}" /></div>
            <div class="toggleBox">
              <a class="toggleLink" href="${item.toggleUrl}"></a>
            </div>
          </div>
        </c:forEach>
      </c:otherwise>
    </c:choose>
  </div>

</div>
</body>
</html>
