package viewmodel;

import java.io.Serializable;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import model.dto.HallRowDTO;

public class HallRowViewModel implements Serializable {

    private long orderDetailId;
    private String productName;
    private String quantityText;
    private String tableNo;
    private String sortTimeText;
    private String toggleUrl;

    public HallRowViewModel() {}

    public static HallRowViewModel from(HallRowDTO dto, String toggleUrl, ZoneId zoneId) {
        HallRowViewModel vm = new HallRowViewModel();
        vm.orderDetailId = dto.getOrderDetailId();
        vm.productName = dto.getProductName();
        vm.quantityText = String.valueOf(dto.getQuantity());
        vm.tableNo = dto.getTableNo();
        vm.toggleUrl = toggleUrl;

        if (dto.getSortTime() != null) {
            DateTimeFormatter fmt = DateTimeFormatter.ofPattern("MM/dd HH:mm");
            vm.sortTimeText = dto.getSortTime().toInstant()
                    .atZone(zoneId)
                    .toLocalDateTime()
                    .format(fmt);
        } else {
            vm.sortTimeText = "";
        }

        return vm;
    }

    public long getOrderDetailId() { return orderDetailId; }
    public String getProductName() { return productName; }
    public String getQuantityText() { return quantityText; }
    public String getTableNo() { return tableNo; }
    public String getSortTimeText() { return sortTimeText; }
    public String getToggleUrl() { return toggleUrl; }
}
