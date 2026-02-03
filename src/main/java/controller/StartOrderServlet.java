package controller;

import java.io.IOException;
import java.util.ArrayList;

import dao.TableMasterDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.dto.CartItemDTO;

/**
 * Servlet implementation class StartOrderServlet
 */
@WebServlet("/StartOrderServlet")
public class StartOrderServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	// DAOを用意
    private TableMasterDAO tableDAO = new TableMasterDAO();

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        // --- ★追加機能: リセットリクエスト (URLに ?action=reset がある場合) ---
        String action = request.getParameter("action");
        if ("reset".equals(action)) {
            // Cookieを削除する（有効期限を0にする）
            Cookie cookie = new Cookie("savedTableId", "");
            cookie.setMaxAge(0);
            cookie.setPath("/"); // アプリ全体で有効にするためパスを指定
            response.addCookie(cookie);
            
            // 通常の入力画面へ
            request.getRequestDispatcher("/WEB-INF/views/start_order.jsp").forward(request, response);
            return;
        }

        // --- ★追加機能: Cookieからテーブル番号を探す ---
        Cookie[] cookies = request.getCookies();
        String savedTableId = null;

        if (cookies != null) {
            for (Cookie c : cookies) {
                if ("savedTableId".equals(c.getName())) {
                    savedTableId = c.getValue();
                    break;
                }
            }
        }

        // 保存されたテーブル番号があれば、自動で次へ進む
        if (savedTableId != null && !savedTableId.isEmpty()) {
            try {
                int tableId = Integer.parseInt(savedTableId);

                // 念のため、ここでもステータスを「1 (使用中)」に更新しておく
                // (端末を再起動してブラウザを開いた場合などを考慮)
                tableDAO.updateStatusById(tableId, 1);
                
                // セッションに保存
                HttpSession session = request.getSession();
                session.setAttribute("tableId", tableId);
                
                // カート初期化
                if (session.getAttribute("cart") == null) {
                    session.setAttribute("cart", new ArrayList<CartItemDTO>());
                }
                
                // 入力画面をスキップして「人数入力画面」へ
                request.getRequestDispatcher("/WEB-INF/views/customer_count.jsp").forward(request, response);
                return;

            } catch (Exception e) {
                // Cookieの値がおかしい場合は無視して通常の画面へ
                e.printStackTrace();
            }
        }

		// 保存されていなければ、通常の入力画面を表示
        request.getRequestDispatcher("/WEB-INF/views/start_order.jsp").forward(request, response);
    }
	

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");

		try {
            // 1. テーブル番号を取得
            String tableIdStr = request.getParameter("tableId");
            // nullチェック
            if(tableIdStr == null || tableIdStr.isEmpty()){
                 response.sendRedirect("StartOrderServlet"); 
                 return;
            }
            int tableId = Integer.parseInt(tableIdStr);
            
            // 2. テーブルステータスを「1 (使用中)」に更新
            tableDAO.updateStatusById(tableId, 1);
            
            // 3. セッションに保存
            HttpSession session = request.getSession();
            session.setAttribute("tableId", tableId);
            
            // カートの初期化
            if (session.getAttribute("cart") == null) {
                session.setAttribute("cart", new ArrayList<CartItemDTO>());
            }

            // --- ★追加機能: Cookieにテーブル番号を保存する ---
            Cookie tableCookie = new Cookie("savedTableId", tableIdStr);
            tableCookie.setMaxAge(60 * 60 * 24 * 365); // 有効期限: 1年間 (秒単位)
            tableCookie.setPath("/"); // アプリ全体で有効
            response.addCookie(tableCookie);
            // ------------------------------------------------

            // 4. 次の画面へ
            request.getRequestDispatcher("/WEB-INF/views/customer_count.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("StartOrderServlet");
        }
	}
}