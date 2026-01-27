package viewmodel;

import java.io.Serializable;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import model.dto.KitchenRowDTO;

public class KitchenRowViewModel implements Serializable {

    private long orderDetailId;
    private String productName;
    private String quantityText;
    private String sortTimeText;
    private String toggleUrl;

    public KitchenRowViewModel() {}

    public static KitchenRowViewModel from(KitchenRowDTO dto, String toggleUrl, ZoneId zoneId) {
        KitchenRowViewModel vm = new KitchenRowViewModel();
        vm.orderDetailId = dto.getOrderDetailId();
        vm.productName = dto.getProductName();
        vm.quantityText = String.valueOf(dto.getQuantity());

        if (dto.getSortTime() != null) {
            DateTimeFormatter fmt = DateTimeFormatter.ofPattern("MM/dd HH:mm");
            vm.sortTimeText = dto.getSortTime().toInstant()
                    .atZone(zoneId)
                    .toLocalDateTime()
                    .format(fmt);
        } else {
            vm.sortTimeText = "";
        }

        vm.toggleUrl = toggleUrl;
        return vm;
    }

    public long getOrderDetailId() { return orderDetailId; }
    public String getProductName() { return productName; }
    public String getQuantityText() { return quantityText; }
    public String getSortTimeText() { return sortTimeText; }
    public String getToggleUrl() { return toggleUrl; }
}
