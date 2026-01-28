package viewmodel;

import java.io.Serializable;

public class KitchenToggleViewModel implements Serializable {

    private long orderDetailId;
    private String noUrl;      // いいえで戻る先
    private String actionUrl;  // はいのPOST先

    public KitchenToggleViewModel() {}

    public long getOrderDetailId() { return orderDetailId; }
    public void setOrderDetailId(long orderDetailId) { this.orderDetailId = orderDetailId; }

    public String getNoUrl() { return noUrl; }
    public void setNoUrl(String noUrl) { this.noUrl = noUrl; }

    public String getActionUrl() { return actionUrl; }
    public void setActionUrl(String actionUrl) { this.actionUrl = actionUrl; }
}
