package model.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CartItemDTO implements Serializable {
    private ProductDTO product;
    private int quantity;
    
    // 【修正】IDだけでなく、オプション情報(名前や価格)を丸ごと持つリストに変更
    private List<OptionDTO> optionList = new ArrayList<>();
    
    public CartItemDTO(ProductDTO product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }
    
    // --- Getter / Setter ---

    public List<OptionDTO> getOptionList() {
        return optionList;
    }

    public void setOptionList(List<OptionDTO> optionList) {
        this.optionList = optionList;
    }

    public ProductDTO getProduct() {
        return product;
    }

    public void setProduct(ProductDTO product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}