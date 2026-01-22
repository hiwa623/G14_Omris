package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.dto.HistoryDetailDTO;
import model.dto.OrderHistoryDTO;

public class OrderDAO {
	//	MySQL形式
	//    private static final String INSERT_ORDER_SQL = 
	//        "INSERT INTO orders (order_date, total_price) VALUES (NOW(), ?)";

	// Oracle形式
	//table_id を追加
	private static final String INSERT_ORDER_SQL = "INSERT INTO orders (table_id, order_date, total_price) VALUES (?, SYSDATE, ?)";

	// 注文一覧を取得（新しい順）
	private static final String SELECT_ALL_ORDERS_SQL = "SELECT order_id, order_date, total_price FROM orders ORDER BY order_date DESC";

	// 特定の注文に紐づく明細を取得（商品名も結合）
	private static final String SELECT_DETAILS_BY_ORDER_ID_SQL = "SELECT d.quantity, d.price, p.product_name " +
			"FROM order_details d " +
			"JOIN product p ON d.product_id = p.product_id " +
			"WHERE d.order_id = ?";

	// 明細に紐づくオプションを保存するSQL
	private static final String INSERT_SPECIFIED_OPTION_SQL = "INSERT INTO order_specified_options (order_detail_id, option_id) VALUES (?, ?)";

	//    MYSQL用
	//    public int insertOrder(Connection conn, int totalPrice) throws SQLException {
	//        // RETURN_GENERATED_KEYS を指定して、自動採番された ID を取得可能にする
	//        try (PreparedStatement ps = conn.prepareStatement(INSERT_ORDER_SQL, Statement.RETURN_GENERATED_KEYS)) {
	//            ps.setInt(1, totalPrice);
	//            ps.executeUpdate();
	//
	//            // 発行された order_id を取得
	//            try (ResultSet rs = ps.getGeneratedKeys()) {
	//                if (rs.next()) {
	//                    return rs.getInt(1);
	//                } else {
	//                    return 0;
	//                }
	//            }
	//        }
	//    }

	/**
	 * 注文（親）を登録し、自動採番された order_id を返す
	 * @param conn トランザクション管理用のコネクション
	 * @param tableId テーブルID
	 * @param totalPrice 合計金額
	 * @return 生成された order_id
	 * @throws SQLException
	 */
	public int insertOrder(Connection conn, int tableId, int totalPrice) throws SQLException {
		// IDを取得するために第2引数にカラム名を指定
		String[] generatedColumns = { "order_id" };

		try (PreparedStatement ps = conn.prepareStatement(INSERT_ORDER_SQL, generatedColumns)) {
			// 1番目: table_id, 2番目: total_price
			ps.setInt(1, tableId);
			ps.setInt(2, totalPrice);

			int result = ps.executeUpdate();

			if (result == 0) {
				throw new SQLException("注文の登録に失敗しました。");
			}

			try (ResultSet rs = ps.getGeneratedKeys()) {
				if (rs.next()) {
					return rs.getInt(1);
				} else {
					throw new SQLException("注文IDの取得に失敗しました。");
				}
			}
		}
	}

	public List<OrderHistoryDTO> findAllOrders() {
		List<OrderHistoryDTO> orderList = new ArrayList<>();
		try (Connection conn = DBManager.getConnection();
				PreparedStatement ps = conn.prepareStatement(SELECT_ALL_ORDERS_SQL);
				ResultSet rs = ps.executeQuery()) {

			while (rs.next()) {
				OrderHistoryDTO dto = new OrderHistoryDTO();
				dto.setOrderId(rs.getInt("order_id"));
				dto.setOrderDate(rs.getTimestamp("order_date"));
				dto.setTotalPrice(rs.getInt("total_price"));

				// 各注文に対して明細を取得してセットする
				dto.setDetails(findDetailsByOrderId(dto.getOrderId()));
				orderList.add(dto);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return orderList;
	}

	private List<HistoryDetailDTO> findDetailsByOrderId(int orderId) {
		List<HistoryDetailDTO> details = new ArrayList<>();

		// SQLはそのまま使えます（SELECT_DETAILS_BY_ORDER_ID_SQL）
		try (Connection conn = DBManager.getConnection();
				PreparedStatement ps = conn.prepareStatement(SELECT_DETAILS_BY_ORDER_ID_SQL)) {
			ps.setInt(1, orderId);
			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					// ここで HistoryDetailDTO を使うように変更
					HistoryDetailDTO d = new HistoryDetailDTO();
					d.setProductName(rs.getString("product_name"));
					d.setQuantity(rs.getInt("quantity"));
					d.setPrice(rs.getInt("price"));

					// ※findAllOrders（管理画面用）ではステータスやオプションまでは
					//  とりあえず不要ならセットしなくてもOKです。
					//  （必要ならここでステータス取得の処理も書くことになりますが、まずはエラー解消を優先します）

					details.add(d);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return details;
	}

	/**
	 * 注文明細のオプションを登録する
	 * @param conn
	 * @param detailId 紐づく明細ID
	 * @param optionId オプションID
	 * @throws SQLException
	 */
	public void insertSpecifiedOption(Connection conn, int detailId, int optionId) throws SQLException {
		try (PreparedStatement ps = conn.prepareStatement(INSERT_SPECIFIED_OPTION_SQL)) {
			ps.setInt(1, detailId);
			ps.setInt(2, optionId);
			ps.executeUpdate();
		}
	}

	/**
	 * 【新規追加】指定されたテーブルIDの注文履歴を取得する（ステータス付き）
	 */
	public List<OrderHistoryDTO> findHistoryByTableId(int tableId) {
		List<OrderHistoryDTO> historyList = new ArrayList<>();

		// 1. そのテーブルの注文(Orders)をすべて取得
		// ※テーブル名は環境に合わせて orders としています
		String sqlOrder = "SELECT order_id, order_date, total_price FROM orders WHERE table_id = ? ORDER BY order_date DESC";

		try (Connection conn = DBManager.getConnection();
				PreparedStatement psOrder = conn.prepareStatement(sqlOrder)) {

			psOrder.setInt(1, tableId);

			try (ResultSet rsOrder = psOrder.executeQuery()) {
				while (rsOrder.next()) {
					OrderHistoryDTO order = new OrderHistoryDTO();
					order.setOrderId(rsOrder.getInt("order_id"));
					order.setOrderDate(rsOrder.getTimestamp("order_date"));
					order.setTotalPrice(rsOrder.getInt("total_price")); // DTOのフィールド名に合わせて修正 (totalAmount -> totalPrice)

					// 2. その注文に紐づく明細(Detail)を取得
					// ここで product_status テーブルも結合してステータス名を取得します
					// ※商品テーブル名は product, d.status から d.product_status_id に変更
					String sqlDetail = "SELECT d.detail_id, d.quantity, d.price, d.product_status_id, " +
							"       p.product_name, " +
							"       ps.name AS status_name " +
							"FROM order_details d " +
							"JOIN product p ON d.product_id = p.product_id " +
							"LEFT JOIN product_status ps ON d.product_status_id = ps.id " +
							"WHERE d.order_id = ?";

					try (PreparedStatement psDetail = conn.prepareStatement(sqlDetail)) {
						psDetail.setInt(1, order.getOrderId());

						try (ResultSet rsDetail = psDetail.executeQuery()) {
							List<HistoryDetailDTO> details = new ArrayList<>();

							while (rsDetail.next()) {
								HistoryDetailDTO detail = new HistoryDetailDTO();
								detail.setProductName(rsDetail.getString("product_name"));
								detail.setPrice(rsDetail.getInt("price"));
								detail.setQuantity(rsDetail.getInt("quantity"));

								String sId = rsDetail.getString("product_status_id");
								String sName = rsDetail.getString("status_name");

								if (sId == null) {
									sId = "NEW";
									sName = "未調理";
								}

								detail.setStatusId(sId);
								detail.setStatusName(sName);

								// 【修正】取得する際のカラム名も detail_id に変更
								int detailId = rsDetail.getInt("detail_id");

								// --- オプション取得処理 ---
								// ※もしここでもエラーが出る場合は、order_specified_optionsテーブル側のカラム名も確認してください
								//  (例: order_detail_id なのか detail_id なのか)
								String sqlOpt = "SELECT o.option_name FROM order_specified_options oso " +
										"JOIN options o ON oso.option_id = o.id " + // 【修正】o.option_id を o.id に変更
										"WHERE oso.order_detail_id = ?"; // ←ここも確認ポイント（後述）

								try (PreparedStatement psOpt = conn.prepareStatement(sqlOpt)) {
									// 先ほど修正した detail_id をセット
									psOpt.setInt(1, detailId);

									try (ResultSet rsOpt = psOpt.executeQuery()) {
										while (rsOpt.next()) {
											detail.addOptionName(rsOpt.getString("option_name"));
										}
									}
								}

								details.add(detail);
							}
							order.setDetails(details);
						}
					}
					historyList.add(order);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return historyList;
	}
}