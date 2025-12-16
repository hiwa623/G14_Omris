package model.service;

import java.util.List;

import dao.ProductDAO;
import model.dto.ProductDTO;

/**
 * ProductService
 * DAOを呼び出し、取得したDTOを加工・変換してControllerに提供する。
 */
public class ProductService {

	//DB接続用
	private ProductDAO productDAO = new ProductDAO();

	
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
		// DAOのfindByIDメソッドが必要になります（DAOにはまだ実装されていませんが、利用を想定）
		// return productDAO.findByID(productId); 

		// DAOにfindByIDを実装していないため、findAllを使って一旦取得するダミー処理
		// 実際にはDAOにfindByIDを実装すべき
		return null;
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
}

