package dao;

import java.util.List;

import model.dto.ProductDTO;

/**
 * 商品データアクセスのための共通インターフェース(rule)
 */
public interface IProductDAO {
	// 全商品を取得するメソッドの定義
	// 「商品一覧(List<ProductDTO>)を返す findAll という機能を持つこと」というルール
    // ※ここに具体的な処理（SQLなど）は書きません。
    List<ProductDTO> findAll();
}
