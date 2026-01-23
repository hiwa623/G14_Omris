package model.service;

import java.util.List;

import dao.SalesDAO;
import model.dto.SalesDTO;

public class SalesService {
	private SalesDAO salesDAO = new SalesDAO();

	public List<SalesDTO> getDailySales(String startDate, String endDate) {
        return salesDAO.getDailySales(startDate, endDate);
    }

    public List<SalesDTO> getProductRanking(String startDate, String endDate) {
        return salesDAO.getProductRanking(startDate, endDate);
    }
}