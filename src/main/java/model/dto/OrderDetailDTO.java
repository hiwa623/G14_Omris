package model.dto;

import java.io.Serializable;

public class OrderDetailDTO implements Serializable {
    private String productName; // 商品名（productテーブルから結合して取得）
    private int quantity;       // 数量
    private int price;          // 購入時の単価

    // Getter & Setter
    public String getProductName() { return productName; }
    public void setProductName(String productName) { this.productName = productName; }
    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
    public int getPrice() { return price; }
    public void setPrice(int price) { this.price = price; }
}
