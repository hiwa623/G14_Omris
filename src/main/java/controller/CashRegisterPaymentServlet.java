package controller;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.service.AccountingService;
import viewmodel.CashRegisterPaymentViewModel;

/**
 * Servlet implementation class CashRegisterPaymentServlet
 */
@WebServlet("/CashRegisterPaymentServlet")
public class CashRegisterPaymentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	// 業務ロジッククラスのインスタンス化
	private AccountingService accountingService = new AccountingService();

	/**
	 * GET: 支払い画面を表示する
	 * 前の画面（確認画面）から tableNo と totalPrice を受け取る想定
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		
request.setCharacterEncoding("UTF-8");
        
        // 前画面（テーブル入力）からテーブル番号を受け取る想定
        // ※テスト用URL: CashRegisterPaymentServlet?table_no=C-1
        String tableNo = request.getParameter("table_no");
        
        if (tableNo == null || tableNo.isEmpty()) {
            tableNo = "C-1"; // テスト用デフォルト
        }
        
        // Serviceを使ってデータを一括取得（明細＋合計）
        CashRegisterPaymentViewModel viewModel = accountingService.getPaymentData(tableNo);
        
        request.setAttribute("viewModel", viewModel);
        request.getRequestDispatcher("/WEB-INF/views/CashRegisterPayment.jsp").forward(request, response);
	}

	/**
     * POST: 「支払い完了」ボタンが押された時の処理
     * 会計を確定し、テーブルステータスを更新して、完了画面へ遷移する
     */
    @Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
request.setCharacterEncoding("UTF-8");
        
        // 1. 画面からの入力値取得
        String tableNo = request.getParameter("table_no");
        // ※必要であれば「預かり金」などをここで取得し、お釣り計算ロジックなどを挟む
        
        // 2. Serviceを利用して業務処理実行
        // (テーブルのステータスを空席に戻す処理)
        accountingService.finishCheckout(tableNo);
        
        // 3. 次の画面（会計終了画面）へ遷移
        // 処理完了後は、ブラウザ更新での再送信を防ぐため、ForwardではなくRedirectが一般的ですが、
        // データを渡したい場合はServlet経由でForwardすることも可能です。
        // ここでは設計思想の「1画面=1Servlet」に従い、次のServletへ渡します。
        
        response.sendRedirect(request.getContextPath() + "/CashRegisterCompleteServlet");
	}

}
