function filterCategory(catId, element) {
    // 1. 全てのボタンから 'active' クラスを外す
    var buttons = document.querySelectorAll('.category-btn');
    buttons.forEach(function(btn) {
        btn.classList.remove('active');
    });

    // 2. クリックされたボタンに 'active' クラスをつける
    element.classList.add('active');

    // 3. 商品カードを全て取得してループ処理
    var products = document.querySelectorAll('.product-card');

    products.forEach(function(card) {
        // カードのカテゴリーIDを取得
        var cardCatId = card.getAttribute('data-category-id');

        // 'all'が選ばれているか、IDが一致すれば表示
        if (catId === 'all' || cardCatId === catId) {
            card.style.display = 'flex'; // CSSで元々flex指定されているため
        } else {
            card.style.display = 'none'; // 非表示
        }
    });
}