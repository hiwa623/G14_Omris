package model.service;

import java.util.List;

import dao.IProductDAO;
import dao.ProductDAOMock;
import model.dto.ProductDTO;

/**
 * ProductService
 * DAOを呼び出し、取得したDTOを加工・変換してControllerに提供する。
 */
public class ProductService {

	//DB接続用
	//private ProductDAO productDAO = new ProductDAO();

	//モックDAOを使用(偽物Ver）
	private final IProductDAO productDAO = new ProductDAOMock();
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
}

