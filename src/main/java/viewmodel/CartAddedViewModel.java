package viewmodel;

import model.dto.ProductDTO;

public class CartAddedViewModel {
	private ProductDTO lastAddedProduct; // 最後に追加した商品
    private int addedQuantity;           // 追加した個数

    public ProductDTO getLastAddedProduct() { return lastAddedProduct; }
    public void setLastAddedProduct(ProductDTO p) { this.lastAddedProduct = p; }

    public int getAddedQuantity() { return addedQuantity; }
    public void setAddedQuantity(int q) { this.addedQuantity = q; }
}
