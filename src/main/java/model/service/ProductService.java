package model.service;

import java.util.List;

import dao.OptionDAO;
import dao.ProductDAO;
import model.dto.OptionDTO;
import model.dto.ProductDTO;

/**
 * ProductService
 * DAOを呼び出し、取得したDTOを加工・変換してControllerに提供する。
 */
public class ProductService {

	//DB接続用
	private ProductDAO productDAO = new ProductDAO();
//	private IProductDAO productDAO = new ProductDAOMock(); // ★ 仮データで動かすためのコード

	// ★追加: OptionDAOの定義（なければインスタンス化）
    private OptionDAO optionDAO = new OptionDAO();
	
 // 編集画面表示用に、現在選択されているオプションIDを取得する
    public List<Integer> getSelectedOptionIds(int productId) {
        return productDAO.getSelectedOptionIds(productId);
    }

    // 商品情報の更新処理
    public boolean updateProduct(ProductDTO product, List<Integer> optionIds) {
        // 1. 商品テーブルの更新
        boolean isUpdated = productDAO.updateProduct(product);
        
        if (isUpdated) {
            // 2. オプション紐付けの更新（一度消して登録し直す）
            productDAO.updateProductOptions(product.getProductId(), optionIds);
            return true;
        }
        return false;
    }
    
	/**
     * 商品と、その商品に紐づくオプションを登録するメソッド
     * @param productDTO 商品情報
     * @param optionIds 選択されたオプションIDのリスト
     * @return 成功したらtrue
     */
    public boolean registerProduct(ProductDTO productDTO, List<Integer> optionIds) {
        
        // 1. 入力チェック（既存のロジックと同じ）
        if (productDTO.getProductName() == null || productDTO.getProductName().trim().isEmpty()) {
            System.err.println("【Service Error】商品名が未入力です。");
            return false;
        }
        if (productDTO.getPrice() <= 0) {
            System.err.println("【Service Error】単価が不正です。");
            return false;
        }

        try {
            // 2. 商品を登録し、登録された「商品ID」を取得する
            // ※ProductDAOに insertProductAndReturnId というメソッドを作る必要があります（後述）
            int newProductId = productDAO.insertProductAndReturnId(productDTO);
            
            if (newProductId > 0) {
                // 3. オプションが選択されていれば、中間テーブルに登録する
                if (optionIds != null && !optionIds.isEmpty()) {
                    // ※ProductDAOに registerProductOptions というメソッドを作る必要があります（後述）
                    productDAO.registerProductOptions(newProductId, optionIds);
                }
                return true;
            } else {
                return false;
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
	
	/**
	 * 商品一覧画面表示に必要な情報を取得する。
	 * * @return DBから取得したProductDTOのリスト
	 */
	public List<ProductDTO> getProductListForDisplay() {
		//DAOの呼び出し、DBからProductDTOのリストを取得
		List<ProductDTO> productList = productDAO.findAll();

		return productList;
	}

	/**
	 * 特定の商品IDの商品情報を取得する。
	 * @param productId 取得したい商品のID
	 * @return 該当するProductDTO、見つからない場合はnull
	 */
	public ProductDTO getProductById(int productId) {
		// DAOのfindByIdを呼び出して、結果をそのまま返す
	    return productDAO.findById(productId);
	}
	
	/**
     * 新しいメニューを登録する業務処理。
     * Controllerからの入力を検証し、DAOを呼び出す。
     * @param productDTO 登録する商品データ（商品説明を含む）
     * @return 登録が成功すれば true、失敗すれば false
     */
    public boolean registerMenuItem(ProductDTO productDTO) {
        
        // 1. 業務ルールに基づく入力値チェック
        // ManagerServletで数値変換エラーをキャッチ済みの場合でも、Serviceで再度チェックすることが重要です。
        
        if (productDTO.getProductName() == null || productDTO.getProductName().trim().isEmpty()) {
            System.err.println("【Service Error】商品名が未入力です。");
            return false;
        }

        if (productDTO.getProductDescription() == null || productDTO.getProductDescription().trim().isEmpty()) {
            System.err.println("【Service Error】商品説明が未入力です。");
            return false;
        }
        
        if (productDTO.getPrice() <= 0) {
            System.err.println("【Service Error】単価が不正です。");
            return false;
        }
        
//         2. DAO層への登録依頼
        try {
            // ProductDAO.insertProductを実行（実際のDB INSERT）
            int result = productDAO.insertProduct(productDTO);
            
            // 登録が1件成功すれば true を返す
            return result == 1;
            
        } catch (Exception e) {
            System.err.println("【Service Error】商品登録処理中に予期せぬエラーが発生しました。");
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * 削除メソッド
     * @return 削除成功なら true
     */
    // ★ void から boolean に変更
    public boolean deleteProduct(int productId) {
        int result = productDAO.deleteProduct(productId);
        return result > 0;
    }
    
    /**
     * 商品情報を更新する
     * @param product 更新内容を含むDTO
     */
    public void updateProduct(ProductDTO product) {
        productDAO.updateProduct(product);
    }
    
    /**
     * ★追加: オプション一覧を取得するメソッド
     */
    public List<OptionDTO> getOptionList() {
        return optionDAO.findAll();
    }
    
    /**
     * 商品IDに紐づくオプションリスト（DTO）を取得する
     * （お客様画面の商品詳細などで使用）
     */
    public List<OptionDTO> getOptionsByProductId(int productId) {
        return productDAO.getOptionsByProductId(productId);
    }	
}

