package model.dto;

import java.io.Serializable;
import java.util.ArrayList; // 追加
import java.util.List;      // 追加

public class CartItemDTO implements Serializable {
    private ProductDTO product;
    private int quantity;
    // --- 【追加】選択されたオプションIDを保持するリスト ---
    private List<Integer> optionIds = new ArrayList<>();
    
    public CartItemDTO(ProductDTO product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }
    
    // --- 【追加】オプションIDリストのGetter ---
    public List<Integer> getOptionIds() {
        return optionIds;
    }

    // --- 【追加】オプションIDリストのSetter ---
    public void setOptionIds(List<Integer> optionIds) {
        this.optionIds = optionIds;
    }

    public ProductDTO getProduct() {
        return product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}