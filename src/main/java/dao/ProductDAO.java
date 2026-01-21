package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import model.dto.ProductDTO;

public class ProductDAO implements IProductDAO {

	//    // SQL定義 (MySQL用に調整)
	//	private static final String SELECT_ALL_PRODUCTS_SQL = 
	//		    "SELECT p.*, c.name AS category_name " +
	//		    "FROM product p " +
	//		    "LEFT JOIN category c ON p.category_id = c.id " +
	//		    "ORDER BY p.product_id";
	//
	//    //MySQLはAUTO_INCREMENTなので、product_idとシーケンス(NEXTVAL)は記述しません
	//    private static final String INSERT_PRODUCT_SQL = 
	//            "INSERT INTO product (category_id, product_name, product_description, price, product_image_url, favorite, created_at, updated_at) " +
	//            "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
	//
	//    private static final String SELECT_PRODUCT_BY_ID_SQL = 
	//    	    "SELECT p.*, c.name AS category_name " +
	//    	    "FROM product p " +
	//    	    "LEFT JOIN category c ON p.category_id = c.id " +
	//    	    "WHERE p.product_id = ?";
	//
	//    private static final String UPDATE_PRODUCT_SQL = 
	//            "UPDATE product SET category_id = ?, product_name = ?, product_description = ?, price = ?, product_image_url = ?, favorite = ?, updated_at = ? " +
	//            "WHERE product_id = ?";
	//
	//    private static final String DELETE_PRODUCT_SQL = "DELETE FROM product WHERE product_id = ?";

	// 全商品取得（カテゴリ名も結合）
	private static final String SELECT_ALL_PRODUCTS_SQL = "SELECT p.*, c.name AS category_name " +
			"FROM product p " +
			"LEFT JOIN category c ON p.category_id = c.id " +
			"ORDER BY p.product_id";

	// 商品挿入
	// 【修正】PRODUCT_IDはGENERATED AS IDENTITYなのでINSERT対象から除外（Oracleが自動採番）
	// 【修正】created_at, updated_atもDEFAULT値(SYSDATE)に任せるため除外
	private static final String INSERT_PRODUCT_SQL = "INSERT INTO product (category_id, product_name, product_description, price, product_image_url, favorite) "
			+
			"VALUES (?, ?, ?, ?, ?, ?)";

	// IDで商品取得
	private static final String SELECT_PRODUCT_BY_ID_SQL = "SELECT p.*, c.name AS category_name " +
			"FROM product p " +
			"LEFT JOIN category c ON p.category_id = c.id " +
			"WHERE p.product_id = ?";

	// 【修正】更新時は updated_at を SYSDATE で明示的に更新し、変更日時を記録する
	private static final String UPDATE_PRODUCT_SQL = "UPDATE product SET category_id = ?, product_name = ?, product_description = ?, "
			+
			"price = ?, product_image_url = ?, favorite = ?, updated_at = SYSDATE " +
			"WHERE product_id = ?";

	// 商品削除
	private static final String DELETE_PRODUCT_SQL = "DELETE FROM product WHERE product_id = ?";

	@Override
	public List<ProductDTO> findAll() {
		List<ProductDTO> productList = new ArrayList<>();
		try (Connection conn = DBManager.getConnection();
				PreparedStatement ps = conn.prepareStatement(SELECT_ALL_PRODUCTS_SQL);
				ResultSet rs = ps.executeQuery()) {

			while (rs.next()) {
				productList.add(createProductDTOFromResultSet(rs));
			}
		} catch (SQLException e) {
			System.err.println("DBアクセスエラー(findAll): " + e.getMessage());
			e.printStackTrace();
		}
		return productList;
	}

	@Override
	public int insertProduct(ProductDTO productDTO) {
		int result = 0;
		try (Connection conn = DBManager.getConnection();
				PreparedStatement ps = conn.prepareStatement(INSERT_PRODUCT_SQL)) {

			Timestamp now = new Timestamp(System.currentTimeMillis());

			// 【修正箇所】パラメータの順番をSQLの?に合わせて修正
			ps.setInt(1, productDTO.getCategoryId());
			ps.setString(2, productDTO.getProductName());
			ps.setString(3, productDTO.getProductDescription());
			ps.setInt(4, productDTO.getPrice());
			ps.setString(5, productDTO.getProductImageUrl());
			ps.setInt(6, productDTO.isFavorite() ? 1 : 0);

			result = ps.executeUpdate();
		} catch (SQLException e) {
			System.err.println("商品登録エラー: " + e.getMessage());
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public ProductDTO findById(int productId) {
		ProductDTO product = null;
		try (Connection conn = DBManager.getConnection();
				PreparedStatement ps = conn.prepareStatement(SELECT_PRODUCT_BY_ID_SQL)) {

			ps.setInt(1, productId);
			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					product = createProductDTOFromResultSet(rs);
				}
			}
		} catch (SQLException e) {
			System.err.println("ID検索エラー: " + e.getMessage());
			e.printStackTrace();
		}
		return product;
	}

	@Override
	public int updateProduct(ProductDTO productDTO) {
		int result = 0;
		try (Connection conn = DBManager.getConnection();
				PreparedStatement ps = conn.prepareStatement(UPDATE_PRODUCT_SQL)) {

			// 1番目 : category_id
			ps.setInt(1, productDTO.getCategoryId());
			// 2番目 : product_name
			ps.setString(2, productDTO.getProductName());
			// 3番目 : product_description
			ps.setString(3, productDTO.getProductDescription());
			// 4番目 : price
			ps.setInt(4, productDTO.getPrice());
			// 5番目 : product_image_url
			ps.setString(5, productDTO.getProductImageUrl());
			// 6番目 : favorite (1 or 0)
			ps.setInt(6, productDTO.isFavorite() ? 1 : 0);
			// 7番目 : product_id (WHERE句の指定)
			ps.setInt(7, productDTO.getProductId());
			

			result = ps.executeUpdate();
		} catch (SQLException e) {
			System.err.println("商品更新エラー: " + e.getMessage());
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public int deleteProduct(int productId) {
		int result = 0;
		try (Connection conn = DBManager.getConnection();
				PreparedStatement ps = conn.prepareStatement(DELETE_PRODUCT_SQL)) {
			ps.setInt(1, productId);
			result = ps.executeUpdate();
		} catch (SQLException e) {
			System.err.println("商品削除エラー: " + e.getMessage());
			e.printStackTrace();
		}
		return result;
	}

	// ResultSetからDTOを作成する共通メソッド
	private ProductDTO createProductDTOFromResultSet(ResultSet rs) throws SQLException {
		ProductDTO dto = new ProductDTO();

		dto.setProductId(rs.getInt("product_id"));
		dto.setCategoryId(rs.getInt("category_id"));
		dto.setProductName(rs.getString("product_name"));
		dto.setCategoryName(rs.getString("category_name"));
		dto.setProductDescription(rs.getString("product_description"));
		dto.setPrice(rs.getInt("price"));
		dto.setProductImageUrl(rs.getString("product_image_url"));

		dto.setFavorite(rs.getInt("favorite") == 1);
		//小文字のカラム名に合わせる（MySQLは大文字小文字を区別する場合があるため）
		dto.setCreatedAt(rs.getTimestamp("created_at"));
		dto.setUpdateAt(rs.getTimestamp("updated_at"));

		return dto;
	}
}