package viewmodel;

import java.util.List;

import model.dto.CategoryDTO;
import model.dto.OptionDTO;
import model.dto.ProductDTO;

public class MenuEditViewModel {
    private boolean success;
    private String message;
    
    private ProductDTO product;           // 編集対象の商品データ
    private List<CategoryDTO> categoryList; // カテゴリ選択肢
    private List<OptionDTO> optionList;     // 全オプション選択肢
    private List<Integer> selectedOptionIds; // 選択済みのオプションID
    
    // Getter/Setter
    public boolean isSuccess() { return success; }
    public void setSuccess(boolean success) { this.success = success; }
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
    
    public ProductDTO getProduct() { return product; }
    public void setProduct(ProductDTO product) { this.product = product; }
    
    public List<CategoryDTO> getCategoryList() { return categoryList; }
    public void setCategoryList(List<CategoryDTO> categoryList) { this.categoryList = categoryList; }
    
    public List<OptionDTO> getOptionList() { return optionList; }
    public void setOptionList(List<OptionDTO> optionList) { this.optionList = optionList; }
    
    public List<Integer> getSelectedOptionIds() { return selectedOptionIds; }
    public void setSelectedOptionIds(List<Integer> selectedOptionIds) { this.selectedOptionIds = selectedOptionIds; }
}