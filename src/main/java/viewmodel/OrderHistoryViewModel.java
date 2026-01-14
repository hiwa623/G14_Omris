package viewmodel;

import java.util.List;

import model.dto.OrderHistoryDTO;

public class OrderHistoryViewModel {
	private List<OrderHistoryDTO> historyList;
    private String message;

    public List<OrderHistoryDTO> getHistoryList() { return historyList; }
    public void setHistoryList(List<OrderHistoryDTO> list) { this.historyList = list; }
    
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
}
