package dao;

import java.util.List;

import model.dto.ProductDTO;

/**
 * 商品データアクセスのための共通インターフェース(rule)
 */
public interface IProductDAO {
	// 全商品を取得するメソッドの定義
    List<ProductDTO> findAll();
    
    /**
     * 商品情報をDBに新規登録する。
     * @param productDTO 登録する商品データ
     * @return 登録されたレコード数 (通常は 1)
     */
    int insertProduct(ProductDTO productDTO); 
}