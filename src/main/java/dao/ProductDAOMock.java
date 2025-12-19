package dao;

import java.util.ArrayList;
import java.util.List;

import model.dto.ProductDTO;

public class ProductDAOMock implements IProductDAO {
	// 開発用のダミーデータリスト
	private List<ProductDTO> mockData = new ArrayList<>();
	private int nextId = 100; // ダミーの採番開始ID

	public ProductDAOMock() {
		// 固定のダミーデータを初期化
		mockData.add(new ProductDTO(
				101, // ID
				1, // CategoryID (例: 1=定番メニュー)
				"ふわとろデミグラスオムライス",
				"チキンライスを包む、とろける卵と濃厚な自家製デミグラスソース。",
				1200,
				"uploads/demiglace_omurice.jpg",
				true // おすすめ
		));

		// 2. 和風メニュー
		mockData.add(new ProductDTO(
				102, // ID
				2, // CategoryID (例: 2=和風・創作メニュー)
				"明太子の和風オムライス",
				"だし醤油で味付けしたご飯に、ぷちぷち明太子クリームソース。",
				1350,
				"uploads/mentaiko_omurice.jpg",
				false));

		// 3. 創作・変化球メニュー
		mockData.add(new ProductDTO(
				103, // ID
				2, // CategoryID (例: 2=和風・創作メニュー)
				"特製チーズフォンデュオムライス",
				"熱々濃厚な3種のチーズソースをたっぷりかけたオムライス。",
				1580,
				"uploads/cheese_omurice.jpg",
				true // おすすめ
		));

		// 4. サイドメニュー（画像なしのテスト用）
		mockData.add(new ProductDTO(
				104, // ID
				3, // CategoryID (例: 3=サイドメニュー)
				"ミニグリーンサラダ",
				"新鮮なレタスとキュウリ。食後の口直しに。",
				300,
				null, // 画像なし
				false));
	}

	// ===============================================
	// ★ 実際のDB接続やSQL実行を行わず、データを返すだけの実装
	// ===============================================

	@Override
	public List<ProductDTO> findAll() {
		// 全件検索の要求には、mockDataをそのまま返す
		System.out.println(">>> MOCK: findAll() が呼ばれました。サイズ: " + mockData.size());
		return mockData;
	}

	@Override
	public int insertProduct(ProductDTO productDTO) {
		// 登録要求が来たら、仮のIDを割り当ててリストに追加し、成功(1)を返す
		productDTO.setProductId(nextId++);
		mockData.add(productDTO);
		System.out.println("【MOCK】商品が仮登録されました: " + productDTO.getProductName());
		return 1;
	}

	@Override
	public ProductDTO findById(int productId) {
		// ID検索の要求には、リストから該当するIDのデータを返す
		// リストの中に保存されているダミーデータを一つずつ確認する
		for (ProductDTO p : mockData) {
			// 引数で届いたIDと、商品のIDが一致するかチェック
			if (p.getProductId() == productId) {
				System.out.println(">>> MOCK: ID=" + productId + " の商品が見つかりました！");
				return p; // 見つかったらその商品を返す
			}
		}

		// 全て見ても見つからなかった場合
		System.out.println(">>> MOCK: ID=" + productId + " はリスト内に存在しません。");
		return null;
	}

	@Override
	public int updateProduct(ProductDTO productDTO) {
		// 編集処理のモック (ここでは処理を省略し、成功を返すだけでもOK)
		System.out.println("【MOCK】商品ID:" + productDTO.getProductId() + "が仮更新されました。");
		return 1;
	}

	@Override
	public int deleteProduct(int productId) {
		// 削除処理のモック
		System.out.println("【MOCK】商品ID:" + productId + "が仮削除されました。");
		return 1;
	}

}
