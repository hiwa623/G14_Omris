package dao;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import model.dto.ProductDTO;

public class ProductDAOMock implements IProductDAO{
	@Override
	public List<ProductDTO> findAll(){
		List<ProductDTO> list = new ArrayList<>();
        Timestamp now = new Timestamp(System.currentTimeMillis());

        // --- テストデータ1: オムライス ---
        ProductDTO p1 = new ProductDTO();
        p1.setProductId(100);
        p1.setCategoryId(1);
        p1.setProductName("【テスト】デミグラスオムライス");
        p1.setPrice(1200);
        p1.setProductImageUrl("/images/om_demiglace.jpg");
        p1.setFavorite(true);
        p1.setProductDescription("これはDBなしで表示しているテストデータです。");
        p1.setCreatedAt(now);
        p1.setUpdateAt(now);
        list.add(p1);

        // --- テストデータ2: サラダ ---
        ProductDTO p2 = new ProductDTO();
        p2.setProductId(101);
        p2.setCategoryId(4);
        p2.setProductName("【テスト】シーザーサラダ");
        p2.setPrice(450);
        p2.setProductImageUrl("/images/salad.jpg");
        p2.setFavorite(false);
        p2.setProductDescription("新鮮な野菜を使ったサラダです。");
        p2.setCreatedAt(now);
        p2.setUpdateAt(now);
        list.add(p2);

        return list;
	}
}
