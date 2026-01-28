package controller;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import viewmodel.CashRegisterCompleteViewModel;

/**
 * Servlet implementation class CashRegisterCompleteServlet
 */
@WebServlet("/CashRegisterCompleteServlet")
public class CashRegisterCompleteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 1. ViewModelの準備
        CashRegisterCompleteViewModel viewModel = new CashRegisterCompleteViewModel();
        viewModel.setTitle("会計完了");
        viewModel.setMessage("ご利用ありがとうございました。またのお越しをお待ちしております。");
        
        // 2. データセット
        request.setAttribute("viewModel", viewModel);
        
        // 3. 画面表示
        request.getRequestDispatcher("/WEB-INF/views/CashRegisterComplete.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
