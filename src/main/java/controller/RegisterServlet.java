package controller;

import java.io.File;
import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import model.dto.ProductDTO;
import model.service.ProductService;
import viewmodel.MenuRegisterViewModel;

/**
 * Servlet implementation class RegisterServlet
 */
@WebServlet("/RegisterServlet")
@MultipartConfig(
	    fileSizeThreshold = 1024 * 1024, // 1MB
	    maxFileSize = 1024 * 1024 * 10,  // 10MB
	    maxRequestSize = 1024 * 1024 * 50 // 50MB
	)
public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String UPLOAD_DIR = "uploads"; // アップロード先ディレクトリ名
	private ProductService productService = new ProductService();
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RegisterServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		// 商品登録フォーム用のJSPへフォワード
        // パスは /WEB-INF/views/register.jsp と仮定
        request.getRequestDispatcher("/WEB-INF/views/register.jsp").forward(request, response);
    }
	

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO: 登録処理（Service呼び出し、DAO呼び出し、結果判定）を実装する
// multipart/form-data のため、ここでは request.setCharacterEncoding("UTF-8"); は不要
        
        MenuRegisterViewModel viewModel = new MenuRegisterViewModel();
        String nextView = "/WEB-INF/views/register.jsp"; 
        
        // DTOのインスタンスを先に生成（エラー時に値を保持するため）
        ProductDTO productDTO = new ProductDTO();
        String name = null; // DTO格納前に使用するため、tryの外で宣言

        try {
            // 1. ユーザー入力の取得 (Partとして取得するファイル以外)
            name = request.getParameter("productName");
            String description = request.getParameter("productDescription"); 
            String priceStr = request.getParameter("price");
            String categoryIdStr = request.getParameter("categoryId");
            String isRecommendedStr = request.getParameter("isRecommended");
            
            // 文字列から数値への変換
            int price = Integer.parseInt(priceStr);
            int categoryId = Integer.parseInt(categoryIdStr);
            
            // DTOに設定
            productDTO.setProductName(name);
            productDTO.setProductDescription(description); 
            productDTO.setPrice(price);
            productDTO.setCategoryId(categoryId);
            productDTO.setFavorite(isRecommendedStr != null); 
            
            // 2. ★ 画像アップロード処理の実行とファイルパスの取得
            Part filePart = request.getPart("image"); // name="image" の Part を取得
            String applicationPath = request.getServletContext().getRealPath("");
            String uploadPath = applicationPath + File.separator + UPLOAD_DIR;
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }
            
            // ファイル名を取得
            String imageFileName = getFileName(filePart); 
            
            if (imageFileName != null && !imageFileName.isEmpty()) {
                // ファイルをサーバーに書き出す
                filePart.write(uploadPath + File.separator + imageFileName);
                
                // DBに保存するパス (例: uploads/filename.jpg) を設定
                // Webからアクセス可能な相対パスで保存するのが一般的
                productDTO.setProductImageUrl(UPLOAD_DIR + "/" + imageFileName); // Unix/Webパス形式に統一
                request.setAttribute("imageUploadMessage", imageFileName + "をアップロードしました。");
            } else {
                productDTO.setProductImageUrl(null); // ファイル未選択の場合はnullをDTOに設定
            }
            
            // 3. Service層に業務処理を依頼
            boolean result = productService.registerMenuItem(productDTO);
            
            // 4. 結果の判定とViewModelへの格納
            if (result) {
                viewModel.setSuccess(true);
                viewModel.setMessage("メニュー「" + name + "」の登録が完了しました。");
            } else {
                viewModel.setSuccess(false);
                viewModel.setMessage("メニュー登録に失敗しました。Service層でエラーが発生しました。");
            }

        } catch (NumberFormatException e) {
            viewModel.setSuccess(false);
            viewModel.setMessage("入力された数値が不正です。");
            e.printStackTrace();
        } catch (Exception e) {
            // アップロード失敗やその他のシステムエラー
            viewModel.setSuccess(false);
            viewModel.setMessage("システムエラーが発生しました。アップロードまたは登録に失敗しました。");
            e.printStackTrace();
            nextView = "/WEB-INF/views/error.jsp"; 
        } finally {
            // DTOとViewModelをリクエストスコープに格納（再表示時にフォームの値を維持するためなど）
            request.setAttribute("product", productDTO);
            request.setAttribute("viewModel", viewModel);
            request.getRequestDispatcher(nextView).forward(request, response);
        }
    }

    /**
     * Partオブジェクトからファイル名を取得するヘルパーメソッド
     */
    private String getFileName(Part part) {
        String contentDisp = part.getHeader("content-disposition");
        for (String token : contentDisp.split(";")) {
            if (token.trim().startsWith("filename")) {
                return token.substring(token.indexOf('=') + 1).trim().replace("\"", "");
            }
        }
        return null;
    }
	

}
