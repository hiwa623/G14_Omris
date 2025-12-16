package dao;

import java.util.List;

import model.dto.ProductDTO;

/**
 * 商品データアクセスのための共通インターフェース(rule)
 */
public interface IProductDAO {
	// 全商品を取得するメソッドの定義
	List<ProductDTO> findAll();

	// IDで単一の商品を取得するメソッドの定義 (R: Read - Single)
	/**
	 * 指定されたIDの商品情報をDBから取得する。
	 * @param productId 検索対象の商品ID
	 * @return 該当する商品データ (見つからない場合は null)
	 */
	ProductDTO findById(int productId);

	/**
	 * 商品情報をDBに新規登録する。
	 * @param productDTO 登録する商品データ
	 * @return 登録されたレコード数 (通常は 1)
	 */
	int insertProduct(ProductDTO productDTO);

	// 商品情報をDBで更新する。 (U: Update)
	/**
	 * 既存の商品情報をDBで更新する。
	 * @param productDTO 更新する商品データ (IDを含む)
	 * @return 更新されたレコード数 (通常は 1)
	 */
	int updateProduct(ProductDTO productDTO);

	// 商品をDBから削除する。 (D: Delete)
	/**
	 * 指定されたIDの商品をDBから削除する。
	 * @param productId 削除対象の商品ID
	 * @return 削除されたレコード数 (通常は 1)
	 */
	int deleteProduct(int productId);
}