package viewmodel;

import java.io.Serializable;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import model.dto.KitchenRowDTO;

public class KitchenRowViewModel implements Serializable {

    private long orderDetailId;
    private String productName;
    private String quantityText;
    private String sortTimeText;
    private String toggleUrl;

    public KitchenRowViewModel() {}

    private static KitchenRowViewModel from(KitchenRowDTO dto, String toggleUrl, ZoneId zoneId) {
        KitchenRowViewModel vm = new KitchenRowViewModel();
        vm.orderDetailId = dto.getOrderDetailId();
        vm.productName = dto.getProductName();
        vm.quantityText = String.valueOf(dto.getQuantity());
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

    /**
     * キッチン：提供前（/kitchen）一覧用
     * トグル → /kitchen/confirm
     */
    public static List<KitchenRowViewModel> fromKitchenActive(List<KitchenRowDTO> dtos, String contextPath) {
        ZoneId zoneId = ZoneId.of("Asia/Tokyo");
        List<KitchenRowViewModel> vms = new ArrayList<>();
        if (dtos == null) return vms;

        for (KitchenRowDTO dto : dtos) {
            String url = contextPath + "/kitchen/confirm?orderDetailId=" + dto.getOrderDetailId();
            vms.add(from(dto, url, zoneId));
        }
        return vms;
    }

    /**
     * キッチン：完了済み（/kitchen/completed）一覧用
     * トグル → /kitchen/cancelConfirm
     */
    public static List<KitchenRowViewModel> fromKitchenCompleted(List<KitchenRowDTO> dtos, String contextPath) {
        ZoneId zoneId = ZoneId.of("Asia/Tokyo");
        List<KitchenRowViewModel> vms = new ArrayList<>();
        if (dtos == null) return vms;

        for (KitchenRowDTO dto : dtos) {
            String url = contextPath + "/kitchen/cancelConfirm?orderDetailId=" + dto.getOrderDetailId();
            vms.add(from(dto, url, zoneId));
        }
        return vms;
    }

    public long getOrderDetailId() { return orderDetailId; }
    public String getProductName() { return productName; }
    public String getQuantityText() { return quantityText; }
    public String getSortTimeText() { return sortTimeText; }
    public String getToggleUrl() { return toggleUrl; }
}
