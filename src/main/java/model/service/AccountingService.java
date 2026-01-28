package model.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dao.OrderDAO;
import dao.TableMasterDAO;
import model.dto.BillItemDTO;
import model.dto.HistoryDetailDTO;
import model.dto.OrderHistoryDTO;
import viewmodel.CashRegisterPaymentViewModel;

public class AccountingService {

	// 必要なDAOをインスタンス化
	private TableMasterDAO tableDAO = new TableMasterDAO();
	private OrderDAO orderDAO = new OrderDAO();

	/**
	 * 指定テーブルの会計情報を取得し、ViewModelを作成して返す
	 */
	public CashRegisterPaymentViewModel getPaymentData(String tableNo) {
		CashRegisterPaymentViewModel vm = new CashRegisterPaymentViewModel();
		vm.setTableNo(tableNo);

		List<BillItemDTO> items = new ArrayList<>();
		int total = 0;

		try {
			int tableId = tableDAO.getTableId(tableNo);

			if (tableId > 0) {
				// DBからすべての注文履歴を取得
				List<OrderHistoryDTO> historyList = orderDAO.findHistoryByTableId(tableId);

				for (OrderHistoryDTO order : historyList) {
					for (HistoryDetailDTO detail : order.getDetails()) {

						// ★追加: ステータスチェック
						// ステータスIDが "SERVED" のものだけをリストに追加する
						// (もし "提供済み" という日本語名で判定したい場合は detail.getStatusName().equals("提供済み") に変更)
						if ("SERVED".equals(detail.getStatusId())) {

							BillItemDTO billItem = new BillItemDTO(
									detail.getProductName(),
									detail.getPrice(),
									detail.getQuantity());

							items.add(billItem);
						}
					}
				}
			} else {
				System.out.println("テーブル番号 " + tableNo + " は存在しません。");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		vm.setBillItems(items);

		// 合計金額も、フィルタリングされた items だけを元に計算されるので自動的に合います
		for (BillItemDTO item : items) {
			total += item.getSubTotal();
		}
		vm.setTotalPrice(total);

		return vm;
	}

	// 会計確定処理
	public void finishCheckout(String tableNo) {
		try {
			int tableId = tableDAO.getTableId(tableNo);

			if (tableId > 0) {
				// 修正: 数値の 4 ではなく、文字列の "PAID" を渡す
				String paidStatusId = "PAID";
				orderDAO.updateStatusToPaid(tableId, paidStatusId);

				System.out.println("テーブル " + tableNo + " の商品をステータス " + paidStatusId + " に更新しました。");
			}

			// テーブルのステータスを空席(0)に戻す
			tableDAO.updateStatus(tableNo, 0);

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
