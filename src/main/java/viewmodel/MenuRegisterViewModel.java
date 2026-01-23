package viewmodel;

import java.util.List;

import model.dto.CategoryDTO;
import model.dto.OptionDTO; // ★追加

public class MenuRegisterViewModel {
    private boolean success;
    private String message;
    
    // カテゴリー用
    private List<CategoryDTO> categoryList;
    
    // ★追加: オプション選択用（全オプションリスト）
    private List<OptionDTO> optionList;

    // 既存のGetter/Setter
    public boolean isSuccess() { return success; }
    public void setSuccess(boolean success) { this.success = success; }
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
    public List<CategoryDTO> getCategoryList() { return categoryList; }
    public void setCategoryList(List<CategoryDTO> categoryList) { this.categoryList = categoryList; }
    
    // ★追加分のGetter/Setter
    public List<OptionDTO> getOptionList() { return optionList; }
    public void setOptionList(List<OptionDTO> optionList) { this.optionList = optionList; }
}