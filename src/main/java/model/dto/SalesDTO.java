package model.dto;

public class SalesDTO {
    // 日別集計用
    private String salesDate; // 日付 (YYYY-MM-DD)
    private int totalSales;   // 合計金額

    // 商品別ランキング用
    private String productName;
    private int totalQuantity; // 販売個数

    // コンストラクタ
    public SalesDTO() {}

    // Getter / Setter
    public String getSalesDate() { return salesDate; }
    public void setSalesDate(String salesDate) { this.salesDate = salesDate; }

    public int getTotalSales() { return totalSales; }
    public void setTotalSales(int totalSales) { this.totalSales = totalSales; }

    public String getProductName() { return productName; }
    public void setProductName(String productName) { this.productName = productName; }

    public int getTotalQuantity() { return totalQuantity; }
    public void setTotalQuantity(int totalQuantity) { this.totalQuantity = totalQuantity; }
}