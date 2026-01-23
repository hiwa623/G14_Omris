package controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import dao.CategoryDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import model.dto.CategoryDTO;
import model.dto.OptionDTO;
import model.dto.ProductDTO;
import model.service.CustomerService;
import model.service.ProductService;
import viewmodel.LineupViewModel; // ★重要: 一覧画面用のViewModel
import viewmodel.MenuRegisterViewModel; // 登録画面用のViewModel

@WebServlet("/RegisterServlet")
@MultipartConfig(
    fileSizeThreshold = 1024 * 1024, 
    maxFileSize = 1024 * 1024 * 10, 
    maxRequestSize = 1024 * 1024 * 50
)
public class RegisterServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final String UPLOAD_DIR = "uploads"; 

    private ProductService productService = new ProductService();
    // カテゴリ一覧取得などで使用
    private CustomerService customerService = new CustomerService();

    // --- 画面表示 (GET) ---
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 1. カテゴリ一覧取得
        CategoryDAO categoryDAO = new CategoryDAO();
        List<CategoryDTO> categoryList = categoryDAO.findAll();
        
        // 2. オプション一覧取得
        List<OptionDTO> optionList = productService.getOptionList();

        // 3. ViewModel作成 (登録画面用)
        MenuRegisterViewModel vm = new MenuRegisterViewModel();
        vm.setCategoryList(categoryList);
        vm.setOptionList(optionList);

        // 4. リクエストスコープへセット
        request.setAttribute("vm", vm);
        
        // 登録画面へフォワード
        request.getRequestDispatcher("/WEB-INF/views/register.jsp").forward(request, response);
    }

    // --- 登録処理 (POST) ---
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        
        // エラー時再表示用のViewModel
        MenuRegisterViewModel registerVm = new MenuRegisterViewModel();

        try {
            // 1. 入力値の取得
            String productName = request.getParameter("name");
            String priceStr = request.getParameter("price");
            String description = request.getParameter("description");
            String categoryIdStr = request.getParameter("categoryId");
            boolean isRecommended = request.getParameter("recommend") != null;

            // チェックボックスで選ばれたオプションID配列を取得
            String[] optionIdStrs = request.getParameterValues("optionIds");
            List<Integer> selectedOptionIds = new ArrayList<>();
            if (optionIdStrs != null) {
                for (String id : optionIdStrs) {
                    selectedOptionIds.add(Integer.parseInt(id));
                }
            }

            // 2. 画像処理
            String productImageUrl = null;
            Part filePart = request.getPart("file");
            if (filePart != null && filePart.getSize() > 0 && filePart.getSubmittedFileName().length() > 0) {
                String fileName = getFileName(filePart);
                String uploadPath = getServletContext().getRealPath("/" + UPLOAD_DIR);
                File uploadDir = new File(uploadPath);
                if (!uploadDir.exists()) uploadDir.mkdir();
                filePart.write(uploadPath + File.separator + fileName);
                productImageUrl = UPLOAD_DIR + "/" + fileName;
            }

            // 3. DTO作成
            int price = Integer.parseInt(priceStr);
            int categoryId = Integer.parseInt(categoryIdStr);

            ProductDTO product = new ProductDTO();
            product.setProductName(productName);
            product.setPrice(price);
            product.setProductDescription(description);
            product.setCategoryId(categoryId);
            product.setProductImageUrl(productImageUrl);
            product.setFavorite(isRecommended);

            // 4. 登録実行 (Service)
            // ProductService.java で定義されているメソッドを呼び出し
            productService.registerProduct(product, selectedOptionIds);
            
            // ---------------------------------------------------------
            // ★成功時の処理: admin-lineup.jsp (一覧画面) へ遷移する
            // ---------------------------------------------------------
            
            // ① 最新の商品一覧を取得する
            // ProductService.java にある "getProductListForDisplay" を使用
            List<ProductDTO> productList = productService.getProductListForDisplay(); 

            // ② 一覧画面用のViewModel (LineupViewModel) を作成する
            LineupViewModel lineupVm = new LineupViewModel();
            lineupVm.setProductList(productList);
            lineupVm.setMessage("商品とオプション設定の登録が完了しました！");
            
            // ③ リクエストスコープに "vm" という名前でセット
            // (これで admin-lineup.jsp が vm.productList や vm.message を読み取れます)
            request.setAttribute("vm", lineupVm);

            // ④ 一覧画面へフォワード
            request.getRequestDispatcher("/WEB-INF/views/admin-lineup.jsp").forward(request, response);
            return; // 処理終了

        } catch (Exception e) {
            // ---------------------------------------------------------
            // ★失敗時の処理: 登録画面(register.jsp)に戻す
            // ---------------------------------------------------------
            e.printStackTrace();
            
            registerVm.setSuccess(false);
            registerVm.setMessage("登録失敗: " + e.getMessage());

            // フォーム再表示のためにリストを再取得してセットする
            CategoryDAO categoryDAO = new CategoryDAO();
            registerVm.setCategoryList(categoryDAO.findAll());
            registerVm.setOptionList(productService.getOptionList());
            
            request.setAttribute("vm", registerVm);
            request.getRequestDispatcher("/WEB-INF/views/register.jsp").forward(request, response);
        }
    }
    
    private String getFileName(Part part) {
        String contentDisp = part.getHeader("content-disposition");
        for (String token : contentDisp.split(";")) {
            if (token.trim().startsWith("filename")) {
                return token.substring(token.indexOf("=") + 2, token.length() - 1);
            }
        }
        return "";
    }
}