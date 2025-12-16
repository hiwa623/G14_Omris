package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import model.dto.ProductDTO;

/**
 * ProductDAO (Data Access Object)
 * データベースへのアクセス層。
 */
public class ProductDAO implements IProductDAO {
	/**】
	 * DBから全商品情報を取得し、ProductDTOのリストとして返却する。
	 * * @return 全商品のProductDTOリスト
	 */

	// SQL定義
	//テーブルのすべての商品情報を取得
	// ProductDAO.java のSQL定義を修正
	private static final String SELECT_ALL_PRODUCTS_SQL = "SELECT product_id, category_id, product_name, product_description, price, product_image_url, favorite, created_at, updated_at"
			+ " FROM product ORDER BY product_id";

	// 新規商品登録SQL (商品説明、画像URL、おすすめフラグを含む)
	private static final String INSERT_PRODUCT_SQL = "INSERT INTO product (product_id, category_id, product_name, product_description, price, product_image_url, favorite, created_at, updated_at) "
			+ "VALUES (SEQ_PRODUCT_ID.NEXTVAL, ?, ?, ?, ?, ?, ?, ?, ?)"; // Oracleのシーケンスを想定

	private static final String SELECT_PRODUCT_BY_ID_SQL = "SELECT product_id, category_id, product_name, product_description, price, product_image_url, favorite, created_at, updated_at "
			+ "FROM product WHERE product_id = ?";

	private static final String DELETE_PRODUCT_SQL = "DELETE FROM product WHERE product_id = ?";

	/**
	 * DBから全商品情報を取得し、ProductDTOのリストとして返却する。
	 * @return 全商品のProductDTOリスト
	 */
	public List<ProductDTO> findAll() {
		List<ProductDTO> productList = new ArrayList<>();

		// DBManager.getConnection() は SQLException をスローするため、try-catchが必要
		try (Connection conn = DBManager.getConnection(); // 接続取得
				PreparedStatement ps = conn.prepareStatement(SELECT_ALL_PRODUCTS_SQL); // SQL準備
				ResultSet rs = ps.executeQuery()) { // SQL実行と結果取得

			// ResultSetからDTOへの詰め替え処理 (ロジック本体)
			while (rs.next()) {
				ProductDTO product = createProductDTOFromResultSet(rs);
				productList.add(product);
			}

		} catch (SQLException e) {
			// DBアクセスエラー時の処理
			System.err.println("DBアクセスエラーが発生しました: " + e.getMessage());
			e.printStackTrace();
		}

		return productList;
	}

	/**
	 * 商品情報をDBに新規登録する。
	 * @param productDTO 登録する商品データ
	 * @return 登録されたレコード数
	 */
	@Override
	public int insertProduct(ProductDTO productDTO) { // ★このメソッドブロック全体を追加
		int result = 0;

		// ★DBManager.getConnection() はご自身のプロジェクトに合わせてください。
		try (Connection conn = DBManager.getConnection();
				PreparedStatement ps = conn.prepareStatement(INSERT_PRODUCT_SQL)) {

			Timestamp now = new Timestamp(System.currentTimeMillis());

			// プリペアドステートメントへの値のセット
			ps.setInt(1, productDTO.getCategoryId());
			ps.setString(2, productDTO.getProductName());
			ps.setString(3, productDTO.getProductDescription());
			ps.setInt(4, productDTO.getPrice());
			ps.setString(5, productDTO.getProductImageUrl());

			// favorite (boolean) を OracleのNUMBER(1) (1 or 0) に変換してセット
			ps.setInt(6, productDTO.isFavorite() ? 1 : 0);

			ps.setTimestamp(7, now); // created_at
			ps.setTimestamp(8, now); // updated_at

			result = ps.executeUpdate(); // SQL実行 (INSERT)

		} catch (SQLException e) {
			// DBアクセスエラー時の処理
			System.err.println("商品登録DBアクセスエラーが発生しました: " + e.getMessage());
			e.printStackTrace();
		}
		return result;
	}

	// ResultSetからDTOを作成する共通メソッド
	private ProductDTO createProductDTOFromResultSet(ResultSet rs) throws SQLException {
		ProductDTO dto = new ProductDTO();

		// ここが、DBのカラム名とDTOのフィールド名の対応付け
		dto.setProductId(rs.getInt("product_id"));
		dto.setCategoryId(rs.getInt("category_id"));
		dto.setProductName(rs.getString("product_name"));
		dto.setPrice(rs.getInt("price"));
		dto.setProductImageUrl(rs.getString("product_image_url"));
		int favoriteValue = rs.getInt("favorite");
		dto.setFavorite(favoriteValue == 1); // 1ならtrue、0ならfalse
		dto.setCreatedAt(rs.getTimestamp("CREATED_AT"));
		dto.setUpdateAt(rs.getTimestamp("UPDATED_AT"));

		return dto;
	}

	@Override
	public ProductDTO findById(int productId) {
		ProductDTO product = null;
		try (Connection conn = DBManager.getConnection();
				PreparedStatement ps = conn.prepareStatement(SELECT_PRODUCT_BY_ID_SQL)) {

			ps.setInt(1, productId);
			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					// createProductDTOFromResultSet() は既存のヘルパーメソッドを使用
					product = createProductDTOFromResultSet(rs);
				}
			}
		} catch (SQLException e) {
			System.err.println("ID検索DBアクセスエラーが発生しました: " + e.getMessage());
			e.printStackTrace();
		}
		return product;
	}

	// -------------------------------------------------------------

	private static final String UPDATE_PRODUCT_SQL = "UPDATE product SET category_id = ?, product_name = ?, product_description = ?, price = ?, product_image_url = ?, favorite = ?, updated_at = ? WHERE product_id = ?";

	@Override
	public int updateProduct(ProductDTO productDTO) {
		int result = 0;
		try (Connection conn = DBManager.getConnection();
				PreparedStatement ps = conn.prepareStatement(UPDATE_PRODUCT_SQL)) {

			Timestamp now = new Timestamp(System.currentTimeMillis());

			// パラメータの設定 (SQLの ? の順番に注意)
			ps.setInt(1, productDTO.getCategoryId());
			ps.setString(2, productDTO.getProductName());
			ps.setString(3, productDTO.getProductDescription());
			ps.setInt(4, productDTO.getPrice());
			ps.setString(5, productDTO.getProductImageUrl());
			ps.setInt(6, productDTO.isFavorite() ? 1 : 0);
			ps.setTimestamp(7, now); // updated_at
			ps.setInt(8, productDTO.getProductId()); // WHERE句の product_id

			result = ps.executeUpdate();
		} catch (SQLException e) {
			System.err.println("商品更新DBアクセスエラーが発生しました: " + e.getMessage());
			e.printStackTrace();
		}
		return result;
	}

	// -------------------------------------------------------------

	@Override
	public int deleteProduct(int productId) {
		int result = 0;
		try (Connection conn = DBManager.getConnection();
				PreparedStatement ps = conn.prepareStatement(DELETE_PRODUCT_SQL)) {

			ps.setInt(1, productId);

			result = ps.executeUpdate();
		} catch (SQLException e) {
			System.err.println("商品削除DBアクセスエラーが発生しました: " + e.getMessage());
			e.printStackTrace();
		}
		return result;
	}

}