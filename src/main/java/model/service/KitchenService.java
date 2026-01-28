package model.service;

import java.sql.SQLException;
import java.util.List;

import dao.KitchenDAO;
import model.dto.KitchenRowDTO;
import viewmodel.KitchenCompletedViewModel;
import viewmodel.KitchenListViewModel;
import viewmodel.KitchenRowViewModel;
import viewmodel.KitchenToggleViewModel;

public class KitchenService {

    private final KitchenDAO dao;

    public KitchenService() {
        this.dao = new KitchenDAO();
    }

    /**
     * 注文料理一覧（提供前）ViewModel
     */
    public KitchenListViewModel getKitchenListViewModel(String contextPath) throws SQLException {
        List<KitchenRowDTO> dtos = dao.findActiveRows();
        List<KitchenRowViewModel> rows = KitchenRowViewModel.fromKitchenActive(dtos, contextPath);

        KitchenListViewModel vm = new KitchenListViewModel();
        vm.setRows(rows);
        vm.setCount(rows == null ? 0 : rows.size());
        return vm;
    }

    /**
     * 完了済み商品 ViewModel
     */
    public KitchenCompletedViewModel getKitchenCompletedViewModel(String contextPath) throws SQLException {
        List<KitchenRowDTO> dtos = dao.findCompletedRows();
        List<KitchenRowViewModel> rows = KitchenRowViewModel.fromKitchenCompleted(dtos, contextPath);

        KitchenCompletedViewModel vm = new KitchenCompletedViewModel();
        vm.setRows(rows);
        vm.setCount(rows == null ? 0 : rows.size());
        return vm;
    }

    /**
     * 「調理完了」にする（SERVEDへ）
     */
    public boolean markCompleted(long orderDetailId) throws SQLException {
        return dao.markCompleted(orderDetailId);
    }

    /**
     * 「完了キャンセル」（COOKINGへ戻す）
     */
    public boolean cancelCompleted(long orderDetailId) throws SQLException {
        return dao.cancelCompleted(orderDetailId);
    }

    /**
     * 調理完了の確認ダイアログ用VM
     */
    public KitchenToggleViewModel getCompleteConfirmViewModel(long orderDetailId, String contextPath) {
        KitchenToggleViewModel vm = new KitchenToggleViewModel();
        vm.setOrderDetailId(orderDetailId);
        vm.setNoUrl(contextPath + "/kitchen");
        vm.setActionUrl(contextPath + "/kitchen/complete");
        return vm;
    }

    /**
     * 完了キャンセル確認ダイアログ用VM
     */
    public KitchenToggleViewModel getCancelConfirmViewModel(long orderDetailId, String contextPath) {
        KitchenToggleViewModel vm = new KitchenToggleViewModel();
        vm.setOrderDetailId(orderDetailId);
        vm.setNoUrl(contextPath + "/kitchen/completed");
        vm.setActionUrl(contextPath + "/kitchen/cancelComplete");
        return vm;
    }
}
