package viewmodel;

import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

import model.dto.HallRowDTO;

public class HallViewModelFactory {

    private HallViewModelFactory() {}

    public static List<HallRowViewModel> toServeConfirmVMs(List<HallRowDTO> dtos, String contextPath) {
        ZoneId zoneId = ZoneId.of("Asia/Tokyo");
        List<HallRowViewModel> vms = new ArrayList<>();

        for (HallRowDTO dto : dtos) {
            String url = contextPath + "/hall/serveConfirm?orderDetailId=" + dto.getOrderDetailId();
            vms.add(HallRowViewModel.from(dto, url, zoneId));
        }
        return vms;
    }

    public static List<HallRowViewModel> toCancelConfirmVMs(List<HallRowDTO> dtos, String contextPath) {
        ZoneId zoneId = ZoneId.of("Asia/Tokyo");
        List<HallRowViewModel> vms = new ArrayList<>();

        for (HallRowDTO dto : dtos) {
            String url = contextPath + "/hall/cancelConfirm?orderDetailId=" + dto.getOrderDetailId();
            vms.add(HallRowViewModel.from(dto, url, zoneId));
        }
        return vms;
    }
}
