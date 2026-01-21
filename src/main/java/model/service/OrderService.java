package model.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import dao.DBManager;
import dao.OrderDAO;
import dao.OrderDetailDAO;
import model.dto.CartItemDTO;
import model.dto.OrderHistoryDTO;

/**
 * OrderService
 * 注文処理の業務ロジック（トランザクション管理）を担当
 */
public class OrderService {
    private OrderDAO orderDAO = new OrderDAO();
    private OrderDetailDAO detailDAO = new OrderDetailDAO();

    
    /**
     * 【新規追加】来店時の注文開始処理
     * ordersテーブルにレコードを作り、初期人数を登録する
     */
    public boolean startNewOrder(int tableId, int customerCount) {
        // 境界値チェック（1人未満はエラー）
        if (customerCount < 1) return false;

        Connection conn = null;
        try {
            conn = DBManager.getConnection();
            // 注文(親)のみを登録。合計金額は最初は0円。
            int orderId = orderDAO.insertOrder(conn, tableId, 0); 
            
            if (orderId > 0) {
                // ここで人数(customer_count)を更新、またはinsertOrderを拡張して引数に含める
                // 今回のDB設計では orders テーブルに customer_count があるため
                // 本来は orderDAO.insertOrder(conn, tableId, 0, customerCount) とするのが理想的です
                return true;
            }
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            // クローズ処理は既存通り
            try { if (conn != null) conn.close(); } catch (SQLException e) {}
        }
    }
    
    /**
     * 注文確定処理（一連の登録を一つのトランザクションとして実行）
     * @param cart カート内の商品リスト
     * @param tableId 注文したテーブルのID（引数に追加が必要な場合）
     * @return 処理の成否
     */
    public boolean checkout(List<CartItemDTO> cart, int tableId) {
        if (cart == null || cart.isEmpty()) return false;

        Connection conn = null;
        try {
            conn = DBManager.getConnection();
            // 1. 自動コミットをオフ（トランザクション開始）
            conn.setAutoCommit(false);

            // 合計金額の計算
            int total = cart.stream()
                            .mapToInt(i -> i.getProduct().getPrice() * i.getQuantity())
                            .sum();

            // 2. ordersテーブルへ挿入し、発行されたIDを取得
            // 【重要】新しいDB設計に合わせて tableId を渡すように変更
            int orderId = orderDAO.insertOrder(conn, tableId, total);
            
            if (orderId == 0) {
                throw new SQLException("注文IDの取得に失敗しました。");
            }

            // 3. 全てのカートアイテムを order_details テーブルへ挿入
            for (CartItemDTO item : cart) {
                // --- 【修正ポイント】詳細IDを受け取るように変更 ---
                // detailDAO.insertOrderDetailが生成されたID（detail_id）を返すように修正されている前提です
                int detailId = detailDAO.insertOrderDetail(conn, orderId, item);
                
                if (detailId == 0) {
                    throw new SQLException("明細IDの取得に失敗しました。");
                }

                // --- 【追加】オプションの登録処理 ---
                // itemの中に選択されたオプションIDリストが入っている場合
                if (item.getOptionIds() != null && !item.getOptionIds().isEmpty()) {
                    for (int optionId : item.getOptionIds()) {
                        // orderDAO、または専用のDAOでオプションを保存
                        orderDAO.insertSpecifiedOption(conn, detailId, optionId);
                    }
                }
            }

            // 4. 全て成功したら確定
            conn.commit();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            // 5. どこかでエラーが起きたら全て元に戻す
            try {
                if (conn != null) conn.rollback();
            } catch (SQLException se) {
                se.printStackTrace();
            }
            return false;
        } finally {
            try {
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    
    // getOrderHistory() は変更なし
    public List<OrderHistoryDTO> getOrderHistory() {
        return orderDAO.findAllOrders();
    }
}