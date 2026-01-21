package viewmodel;

import java.util.List;

import model.dto.CategoryDTO;
import model.dto.OptionDTO;
import model.dto.ProductDTO;

public class MenuListViewModel {
	// 画面表示に必要なリスト
    private List<CategoryDTO> categoryList;
    private List<ProductDTO> productList;
    private List<OptionDTO> optionList;
    
    // 以下、コンストラクタやGetter/Setter
    public MenuListViewModel() {
    }

    public List<CategoryDTO> getCategoryList() {
        return categoryList;
    }

    public void setCategoryList(List<CategoryDTO> categoryList) {
        this.categoryList = categoryList;
    }

    public List<ProductDTO> getProductList() {
        return productList;
    }

    public void setProductList(List<ProductDTO> productList) {
        this.productList = productList;
    }

    public List<OptionDTO> getOptionList() {
        return optionList;
    }

    public void setOptionList(List<OptionDTO> optionList) {
        this.optionList = optionList;
    }
}
