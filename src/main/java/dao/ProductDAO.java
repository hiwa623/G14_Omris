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
public class ProductDAO implements IProductDAO{
	/**】
     * DBから全商品情報を取得し、ProductDTOのリストとして返却する。
     * * @return 全商品のProductDTOリスト
     */
	
	// SQL定義
	//テーブルのすべての商品情報を取得
    private static final String SELECT_ALL_PRODUCTS_SQL = 
            "SELECT product_id, category_id, product_name, price, product_image_url, favorite"
            + " FROM product ORDER BY product_id"; 

 // 新規商品登録SQL (商品説明、画像URL、おすすめフラグを含む)
    private static final String INSERT_PRODUCT_SQL = 
            "INSERT INTO product (product_id, category_id, product_name, product_description, price, product_image_url, favorite, created_at, updated_at) "
            + "VALUES (SEQ_PRODUCT_ID.NEXTVAL, ?, ?, ?, ?, ?, ?, ?, ?)"; // Oracleのシーケンスを想定
    
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
        dto.setFavorite(rs.getBoolean("favorite"));
        dto.setCreatedAt(rs.getTimestamp("CREATED_AT")); 
        dto.setUpdateAt(rs.getTimestamp("UPDATED_AT"));
        
        return dto;
    }
    
    
}