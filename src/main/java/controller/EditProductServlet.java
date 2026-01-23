package controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import model.dto.ProductDTO;
import model.service.CustomerService; // カテゴリ取得用
import model.service.ProductService;
import viewmodel.MenuEditViewModel;

@WebServlet("/EditProductServlet")
@MultipartConfig(fileSizeThreshold = 1024 * 1024, maxFileSize = 1024 * 1024 * 10, maxRequestSize = 1024 * 1024 * 50)
public class EditProductServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final String UPLOAD_DIR = "uploads";

    private ProductService productService = new ProductService();
    private CustomerService customerService = new CustomerService();

    // 編集画面の表示
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idStr = request.getParameter("id");
        if (idStr == null) {
            response.sendRedirect("ManagerServlet"); // IDがなければ一覧へ戻す
            return;
        }

        int productId = Integer.parseInt(idStr);
        MenuEditViewModel vm = new MenuEditViewModel();

        // 必要なデータを全て取得してVMにセット
        vm.setProduct(productService.getProductById(productId));           // 商品情報
        vm.setCategoryList(customerService.getCategoryList());             // カテゴリ一覧
        vm.setOptionList(productService.getOptionList());                  // オプション一覧
        vm.setSelectedOptionIds(productService.getSelectedOptionIds(productId)); // 選択済みオプション

        request.setAttribute("vm", vm);
        request.getRequestDispatcher("/WEB-INF/views/edit.jsp").forward(request, response);
    }

    // 更新処理
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        MenuEditViewModel vm = new MenuEditViewModel();
        
        // IDの取得（重要）
        int productId = Integer.parseInt(request.getParameter("id"));
        // 現在の情報を再取得（画像パス引継ぎなどのため）
        ProductDTO currentProduct = productService.getProductById(productId);

        try {
            // 入力値の取得
            String name = request.getParameter("name");
            int price = Integer.parseInt(request.getParameter("price"));
            String description = request.getParameter("description");
            int categoryId = Integer.parseInt(request.getParameter("categoryId"));
            boolean isRecommended = request.getParameter("recommend") != null;
            
            // オプションID取得
            String[] optionIdStrs = request.getParameterValues("optionIds");
            List<Integer> selectedOptionIds = new ArrayList<>();
            if (optionIdStrs != null) {
                for (String s : optionIdStrs) selectedOptionIds.add(Integer.parseInt(s));
            }

            // 画像処理（ファイルが選択されていない場合は、元の画像を使う）
            String imageUrl = currentProduct.getProductImageUrl(); // 初期値は今の画像
            Part filePart = request.getPart("file");
            if (filePart != null && filePart.getSize() > 0 && filePart.getSubmittedFileName().length() > 0) {
                // 新しいファイルがアップロードされた場合のみ上書き
                String fileName = getFileName(filePart);
                String uploadPath = getServletContext().getRealPath("/" + UPLOAD_DIR);
                File uploadDir = new File(uploadPath);
                if (!uploadDir.exists()) uploadDir.mkdir();
                filePart.write(uploadPath + File.separator + fileName);
                imageUrl = UPLOAD_DIR + "/" + fileName;
            }

            // DTO更新
            ProductDTO updateDto = new ProductDTO();
            updateDto.setProductId(productId);
            updateDto.setProductName(name);
            updateDto.setPrice(price);
            updateDto.setProductDescription(description);
            updateDto.setCategoryId(categoryId);
            updateDto.setProductImageUrl(imageUrl);
            updateDto.setFavorite(isRecommended);

            // 更新実行
            boolean result = productService.updateProduct(updateDto, selectedOptionIds);
            
            if (result) {
                vm.setSuccess(true);
                vm.setMessage("商品情報を更新しました！");
                // 成功時は最新情報を再取得して表示
                vm.setProduct(productService.getProductById(productId));
            } else {
                vm.setSuccess(false);
                vm.setMessage("更新に失敗しました。");
                vm.setProduct(currentProduct); // 失敗時は元の情報を戻す
            }

        } catch (Exception e) {
            e.printStackTrace();
            vm.setSuccess(false);
            vm.setMessage("エラー: " + e.getMessage());
            vm.setProduct(currentProduct);
        }

        // 再表示用データ
        vm.setCategoryList(customerService.getCategoryList());
        vm.setOptionList(productService.getOptionList());
        vm.setSelectedOptionIds(productService.getSelectedOptionIds(productId));
        
        request.setAttribute("vm", vm);
        request.getRequestDispatcher("/WEB-INF/views/edit.jsp").forward(request, response);
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