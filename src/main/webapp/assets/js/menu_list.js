/**
 * カテゴリフィルタリング関数
 * @param {string} categoryId - 選択されたカテゴリID ('all' または 数値)
 * @param {HTMLElement} element - クリックされたボタン要素
 */
function filterCategory(categoryId, element) {
    // 1. ボタンのアクティブ状態を切り替え
    // すべてのボタンから 'active' クラスを削除
    const buttons = document.querySelectorAll('.category-btn');
    buttons.forEach(btn => btn.classList.remove('active'));
    
    // クリックされたボタンに 'active' クラスを追加
    if (element) {
        element.classList.add('active');
    }

    // 2. 商品の表示・非表示を切り替え
    const products = document.querySelectorAll('.product-card');

    products.forEach(product => {
        const productCatId = product.getAttribute('data-category-id');

        if (categoryId === 'all' || categoryId === productCatId) {
            // 表示する場合: hiddenクラスを消す
            product.classList.remove('hidden');
        } else {
            // 非表示にする場合: hiddenクラスをつける
            product.classList.add('hidden');
        }
    });
}