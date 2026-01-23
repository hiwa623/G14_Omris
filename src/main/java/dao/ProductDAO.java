package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import model.dto.OptionDTO;
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

	//商品更新メソッド
	@Override
	public boolean updateProduct(ProductDTO p) {
	    String sql = "UPDATE product SET category_id=?, product_name=?, price=?, product_description=?, product_image_url=?, favorite=?, updated_at=SYSDATE WHERE product_id=?";
	    
	    try (Connection con = DBManager.getConnection();
	         PreparedStatement ps = con.prepareStatement(sql)) {
	        
	        ps.setInt(1, p.getCategoryId());
	        ps.setString(2, p.getProductName());
	        ps.setInt(3, p.getPrice());
	        ps.setString(4, p.getProductDescription());
	        ps.setString(5, p.getProductImageUrl());
	        ps.setInt(6, p.isFavorite() ? 1 : 0);
	        ps.setInt(7, p.getProductId()); 
	        
	        int result = ps.executeUpdate();
	        
	        // ★これで boolean (true/false) が返せます
	        return result > 0; 

	    } catch (Exception e) {
	        e.printStackTrace();
	        // ★失敗時は false
	        return false; 
	    }
	}

	@Override
    public int deleteProduct(int productId) {
        int result = 0;
        String deleteOptionsSql = "DELETE FROM product_selectable_options WHERE product_id = ?";
        // DELETE_PRODUCT_SQL はクラス上部で定義済みと想定: "DELETE FROM product WHERE product_id = ?"

        // try-with-resourcesで接続を確保
        try (Connection conn = DBManager.getConnection()) {
            
            // ★重要: 自動コミットをオフにする（トランザクション開始）
            conn.setAutoCommit(false);

            try {
                // 1. オプション紐付けの削除
                try (PreparedStatement psOpt = conn.prepareStatement(deleteOptionsSql)) {
                    psOpt.setInt(1, productId);
                    psOpt.executeUpdate();
                }

                // 2. 商品本体の削除
                try (PreparedStatement psProd = conn.prepareStatement(DELETE_PRODUCT_SQL)) { // 既存の定数を使用
                    psProd.setInt(1, productId);
                    result = psProd.executeUpdate();
                }

                // ここまでエラーがなければコミット（確定）
                conn.commit();

            } catch (SQLException e) {
                // 何かエラーが起きたらロールバック（変更を取り消す）
                conn.rollback();
                
                // ★エラーコード 2292 は「整合性制約違反（子レコードあり）」
                if (e.getErrorCode() == 2292) {
                    System.err.println("【削除失敗】ID:" + productId + " は注文履歴が存在するため削除できません。");
                } else {
                    e.printStackTrace();
                }
                
                // 失敗したので result を 0 に戻す
                result = 0;
            }

        } catch (SQLException e) {
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
	
	/**
	 * 商品を登録し、発行されたシーケンスID(product_id)を返す
	 */
	public int insertProductAndReturnId(ProductDTO p) {
	    String sql = "INSERT INTO product (category_id, product_name, price, product_description, product_image_url, favorite) VALUES (?, ?, ?, ?, ?, ?)";
	    
	    // IDを取得するための設定 ("product_id" はテーブルのカラム名)
	    String[] generatedColumns = {"product_id"};
	    
	    try (Connection conn = DBManager.getConnection();
				PreparedStatement ps = conn.prepareStatement(sql, generatedColumns)) {
	        
	        ps.setInt(1, p.getCategoryId());
	        ps.setString(2, p.getProductName());
	        ps.setInt(3, p.getPrice());
	        ps.setString(4, p.getProductDescription());
	        ps.setString(5, p.getProductImageUrl());
	        ps.setInt(6, p.isFavorite() ? 1 : 0);
	        
	        int result = ps.executeUpdate();
	        
	        if (result > 0) {
	            // 生成されたIDを取得する
	            try (ResultSet rs = ps.getGeneratedKeys()) {
	                if (rs.next()) {
	                    return rs.getInt(1); // 新しい product_id を返す
	                }
	            }
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return -1; // 失敗時
	}
	
	/**
	 * 商品IDとオプションIDの紐付けを登録する
	 */
	public void registerProductOptions(int productId, List<Integer> optionIds) {
	    String sql = "INSERT INTO product_selectable_options (product_id, option_id) VALUES (?, ?)";
	    
	    try (Connection con = DBManager.getConnection();
	         PreparedStatement ps = con.prepareStatement(sql)) {
	        
	        for (Integer optionId : optionIds) {
	            ps.setInt(1, productId);
	            ps.setInt(2, optionId);
	            ps.addBatch(); // バッチ処理に追加
	        }
	        
	        ps.executeBatch(); // まとめて実行
	        
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
	

	// 2. その商品で選択済みのオプションIDを取得
	public List<Integer> getSelectedOptionIds(int productId) {
	    List<Integer> list = new ArrayList<>();
	    String sql = "SELECT option_id FROM product_selectable_options WHERE product_id = ?";
	    
	    try (Connection con = DBManager.getConnection();
	         PreparedStatement ps = con.prepareStatement(sql)) {
	        ps.setInt(1, productId);
	        try (ResultSet rs = ps.executeQuery()) {
	            while (rs.next()) {
	                list.add(rs.getInt("option_id"));
	            }
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return list;
	}

	// 3. オプション紐付けの更新（全削除→再登録）
	public void updateProductOptions(int productId, List<Integer> optionIds) {
	    // まず既存の紐付けを全て削除
	    String deleteSql = "DELETE FROM product_selectable_options WHERE product_id = ?";
	    // その後、新しい選択状態を登録
	    String insertSql = "INSERT INTO product_selectable_options (product_id, option_id) VALUES (?, ?)";
	    
	    try (Connection con = DBManager.getConnection()) {
	        // 削除
	        try (PreparedStatement psDel = con.prepareStatement(deleteSql)) {
	            psDel.setInt(1, productId);
	            psDel.executeUpdate();
	        }
	        
	        // 追加（選択されたものがあれば）
	        if (optionIds != null && !optionIds.isEmpty()) {
	            try (PreparedStatement psIns = con.prepareStatement(insertSql)) {
	                for (Integer optId : optionIds) {
	                    psIns.setInt(1, productId);
	                    psIns.setInt(2, optId);
	                    psIns.addBatch();
	                }
	                psIns.executeBatch();
	            }
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
	
	/**
     * 指定された商品IDに紐づくオプションリストを取得する
     * @param productId
     * @return List<OptionDTO>
     */
    public List<OptionDTO> getOptionsByProductId(int productId) {
        List<OptionDTO> list = new ArrayList<>();

        // ★修正ポイント:
        // 1. 中間テーブル名を 'product_selectable_options' (DDL定義通り) に変更
        // 2. オプションテーブル名は 'options' (DDL定義通り)
        String sql = "SELECT o.* FROM options o "
                   + "JOIN product_selectable_options po ON o.id = po.option_id "
                   + "WHERE po.product_id = ?";

        try (Connection conn = DBManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, productId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    OptionDTO option = new OptionDTO();
                    // DDLのカラム名に合わせてセット
                    option.setId(rs.getInt("id"));
                    option.setOptionName(rs.getString("option_name"));
                    option.setOptionPrice(rs.getInt("option_price"));
                    // option_limit などが必要ならここに追加
                    
                    list.add(option);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}