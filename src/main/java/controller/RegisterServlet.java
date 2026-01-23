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
import viewmodel.MenuRegisterViewModel;

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
    private CustomerService customerService = new CustomerService();

    // --- 画面表示 ---
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	// 1. カテゴリ一覧取得（既存）
        CategoryDAO categoryDAO = new CategoryDAO();
        List<CategoryDTO> categoryList = categoryDAO.findAll();
        
        // 2. ★追加: オプション一覧取得
        List<OptionDTO> optionList = productService.getOptionList();

        // 3. ViewModel作成
        MenuRegisterViewModel vm = new MenuRegisterViewModel();
        vm.setCategoryList(categoryList); // ←もしViewModelにCategoryListも持たせる修正をした場合
        // もしくは request.setAttribute("categoryList", categoryList); のままならそのままでOK
        
        // ★追加: ViewModelにオプションリストをセット
        vm.setOptionList(optionList);

        // 4. リクエストスコープへセット
        request.setAttribute("vm", vm);
        // (JSP側で ${categoryList} を使っている場合は以下も残す)
        request.setAttribute("categoryList", categoryList);

        request.getRequestDispatcher("/WEB-INF/views/register.jsp").forward(request, response);
    }

    // --- 登録処理 ---
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        MenuRegisterViewModel vm = new MenuRegisterViewModel();
        String nextView = null; // ★初期値がnull
        try {
            // 基本情報の取得
            String productName = request.getParameter("name");
            String priceStr = request.getParameter("price");
            String description = request.getParameter("description");
            String categoryIdStr = request.getParameter("categoryId");
            boolean isRecommended = request.getParameter("recommend") != null;

            // ★追加: チェックボックスで選ばれたオプションID配列を取得
            String[] optionIdStrs = request.getParameterValues("optionIds");
            List<Integer> selectedOptionIds = new ArrayList<>();
            if (optionIdStrs != null) {
                for (String id : optionIdStrs) {
                    selectedOptionIds.add(Integer.parseInt(id));
                }
            }

            // 画像処理（省略：前回と同じコード）
            String productImageUrl = null;
            Part filePart = request.getPart("file");
            if (filePart != null && filePart.getSize() > 0) {
                String fileName = getFileName(filePart);
                String uploadPath = getServletContext().getRealPath("/" + UPLOAD_DIR);
                File uploadDir = new File(uploadPath);
                if (!uploadDir.exists()) uploadDir.mkdir();
                filePart.write(uploadPath + File.separator + fileName);
                productImageUrl = UPLOAD_DIR + "/" + fileName;
            }

            // DTO作成
            int price = Integer.parseInt(priceStr);
            int categoryId = Integer.parseInt(categoryIdStr);

            ProductDTO product = new ProductDTO();
            product.setProductName(productName);
            product.setPrice(price);
            product.setProductDescription(description);
            product.setCategoryId(categoryId);
            product.setProductImageUrl(productImageUrl);
            product.setFavorite(isRecommended);

            // ★重要: Serviceへ商品と「紐付けたいオプションIDリスト」を渡す
            // ※ProductService側にこのメソッド(registerProductWithOptionsなど)を作る必要があります
            productService.registerProduct(product, selectedOptionIds);
            
            vm.setSuccess(true);
            vm.setMessage("商品とオプション設定の登録が完了しました！");

        } catch (Exception e) {
            e.printStackTrace();
            nextView = "/WEB-INF/views/error.jsp";
            vm.setSuccess(false);
            vm.setMessage("登録失敗: " + e.getMessage());
        } finally {
            // カテゴリ再取得（既存）
            CategoryDAO categoryDAO = new CategoryDAO();
            List<CategoryDTO> categoryList = categoryDAO.findAll();
            request.setAttribute("categoryList", categoryList);
            
            // ★追加: オプション再取得
            List<OptionDTO> optionList = productService.getOptionList();
            if (vm != null) {
                vm.setOptionList(optionList);
            }

            request.setAttribute("vm", vm); // 名前を "vm" に統一してください
            request.getRequestDispatcher(nextView).forward(request, response);
        }

        // --- 再表示用データセット ---
        vm.setCategoryList(customerService.getCategoryList());
        // ★ここも忘れずに
        vm.setOptionList(customerService.getOptionList());
        
        request.setAttribute("vm", vm);
        request.getRequestDispatcher("/WEB-INF/views/register.jsp").forward(request, response);
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