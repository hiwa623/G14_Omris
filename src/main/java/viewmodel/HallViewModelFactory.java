package viewmodel;

import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

import model.dto.HallRowDTO;

public class HallViewModelFactory {

    private HallViewModelFactory() {}

    // 提供前（COOKED）→ 配膳確認へ
    public static List<HallRowViewModel> toServeConfirmVMs(List<HallRowDTO> dtos, String contextPath) {
        ZoneId zoneId = ZoneId.of("Asia/Tokyo");
        List<HallRowViewModel> vms = new ArrayList<>();
        if (dtos == null) return vms;

        for (HallRowDTO dto : dtos) {
            String url = contextPath + "/HallServeConfirmServlet?orderDetailId=" + dto.getOrderDetailId();
            vms.add(HallRowViewModel.from(dto, url, zoneId));
        }
        return vms;
    }

    // 提供済み（SERVED）→ キャンセル確認へ
    public static List<HallRowViewModel> toCancelConfirmVMs(List<HallRowDTO> dtos, String contextPath) {
        ZoneId zoneId = ZoneId.of("Asia/Tokyo");
        List<HallRowViewModel> vms = new ArrayList<>();
        if (dtos == null) return vms;

        for (HallRowDTO dto : dtos) {
            String url = contextPath + "/HallCancelConfirmServlet?orderDetailId=" + dto.getOrderDetailId();
            vms.add(HallRowViewModel.from(dto, url, zoneId));
        }
        return vms;
    }
}
