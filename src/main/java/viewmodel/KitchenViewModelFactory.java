package viewmodel;

import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

import model.dto.KitchenRowDTO;

public class KitchenViewModelFactory {

    private KitchenViewModelFactory() {}

    public static List<KitchenRowViewModel> toConfirmVMs(List<KitchenRowDTO> dtos, String contextPath) {
        ZoneId zoneId = ZoneId.of("Asia/Tokyo");
        List<KitchenRowViewModel> vms = new ArrayList<>();
        for (KitchenRowDTO dto : dtos) {
            String url = contextPath + "/kitchen/confirm?orderDetailId=" + dto.getOrderDetailId();
            vms.add(KitchenRowViewModel.from(dto, url, zoneId));
        }
        return vms;
    }

    public static List<KitchenRowViewModel> toCancelConfirmVMs(List<KitchenRowDTO> dtos, String contextPath) {
        ZoneId zoneId = ZoneId.of("Asia/Tokyo");
        List<KitchenRowViewModel> vms = new ArrayList<>();
        for (KitchenRowDTO dto : dtos) {
            String url = contextPath + "/kitchen/cancelConfirm?orderDetailId=" + dto.getOrderDetailId();
            vms.add(KitchenRowViewModel.from(dto, url, zoneId));
        }
        return vms;
    }
}
