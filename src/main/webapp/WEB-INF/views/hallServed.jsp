<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>ホール｜提供済み一覧</title>

  <!-- ✅ 30分経過で自然に消える（SQLが絞るので更新で消える） -->
  <script>
    setInterval(() => {
      location.reload();
    }, 3000);
  </script>

  <style>
    body { font-family: sans-serif; }
    .wrap { width: 820px; margin: 40px auto; }
    .header { display:flex; justify-content: space-between; align-items:center; margin-bottom: 14px; }
    .tabs { display:flex; gap: 10px; align-items:center; }
    .tab {
      padding: 10px 14px; border-radius: 8px; text-decoration:none;
      border: 1px solid #ddd; color:#000; background:#fff;
    }
    .tab.active { background:#f2f2f2; font-weight: 700; }
    .count { color:#666; }

    .table { border: 1px solid #ddd; border-radius: 10px; overflow: hidden; }
    .row { display:grid; grid-template-columns: 1fr 110px 110px 170px; align-items:center; padding: 14px 16px; border-top:1px solid #eee; }
    .row.head { background:#f2f2f2; font-weight:700; border-top:none; }
    .center { text-align:center; }
    .toggleBox { text-align:center; }

    .toggleLink{
      display:inline-block; width: 46px; height: 26px; border-radius: 999px;
      background:#c8f0c8; position: relative; border:1px solid #a7d7a7;
      vertical-align: middle;
    }
    .toggleLink::after{
      content:""; width: 20px; height: 20px; border-radius: 50%;
      background:#000; position:absolute; top: 2px; left: 23px;
    }

    .empty { padding: 20px; text-align:center; color:#666; }
    .error { background:#ffe7e7; border:1px solid #ffb3b3; padding: 10px 12px; border-radius: 8px; margin-bottom: 10px; }
  </style>
</head>

<body>
<div class="wrap">

  <div class="header">
    <div class="tabs">
      <a class="tab" href="${pageContext.request.contextPath}/hall">提供前一覧</a>
      <a class="tab active" href="${pageContext.request.contextPath}/hall/served">完了済み商品</a>
    </div>

    <div class="count">
      提供済み件数（30分以内）：
      <c:out value="${servedList == null ? 0 : servedList.size()}" />件
    </div>
  </div>

  <c:if test="${not empty error}">
    <div class="error"><c:out value="${error}" /></div>
  </c:if>

  <div class="table">
    <div class="row head">
      <div>商品名</div>
      <div class="center">注文数</div>
      <div class="center">席番号</div>
      <div class="toggleBox">完了キャンセル</div>
    </div>

    <c:choose>
      <c:when test="${empty servedList}">
        <div class="empty">提供済みの商品はありません（30分以内）</div>
      </c:when>

      <c:otherwise>
        <c:forEach var="item" items="${servedList}">
          <div class="row">
            <div><c:out value="${item.productName}" /></div>
            <div class="center"><c:out value="${item.quantityText}" /></div>
            <div class="center"><c:out value="${item.tableNo}" /></div>
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
