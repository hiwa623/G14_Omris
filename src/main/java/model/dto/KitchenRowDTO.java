package model.dto;

import java.io.Serializable;
import java.sql.Timestamp;

public class KitchenRowDTO implements Serializable {

    private long orderDetailId;
    private String productName;
    private long quantity;

    private String tableNo;              // ★ テーブル番号
    private String productStatusId;      // ★ NEW / COOKING / SERVED
    private String productStatusName;    // ★ 未調理 / 調理中 / 提供済み

    private Timestamp sortTime;           // 並び替え用（created_at / order_date）
    private Timestamp updatedAt;          // ★ 提供済み30分判定用

    public KitchenRowDTO() {}

    // よく使うコンストラクタ（一覧表示用）
    public KitchenRowDTO(long orderDetailId, String productName, long quantity,
                         String tableNo, String productStatusId, String productStatusName,
                         Timestamp sortTime, Timestamp updatedAt) {
        this.orderDetailId = orderDetailId;
        this.productName = productName;
        this.quantity = quantity;
        this.tableNo = tableNo;
        this.productStatusId = productStatusId;
        this.productStatusName = productStatusName;
        this.sortTime = sortTime;
        this.updatedAt = updatedAt;
    }

    public long getOrderDetailId() { return orderDetailId; }
    public void setOrderDetailId(long orderDetailId) { this.orderDetailId = orderDetailId; }

    public String getProductName() { return productName; }
    public void setProductName(String productName) { this.productName = productName; }

    public long getQuantity() { return quantity; }
    public void setQuantity(long quantity) { this.quantity = quantity; }

    public String getTableNo() { return tableNo; }
    public void setTableNo(String tableNo) { this.tableNo = tableNo; }

    public String getProductStatusId() { return productStatusId; }
    public void setProductStatusId(String productStatusId) { this.productStatusId = productStatusId; }

    public String getProductStatusName() { return productStatusName; }
    public void setProductStatusName(String productStatusName) { this.productStatusName = productStatusName; }

    public Timestamp getSortTime() { return sortTime; }
    public void setSortTime(Timestamp sortTime) { this.sortTime = sortTime; }

    public Timestamp getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Timestamp updatedAt) { this.updatedAt = updatedAt; }
}
