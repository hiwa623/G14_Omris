package model.service;

import java.util.List;

import dao.OptionDAO;
import model.dto.OptionDTO;

public class OptionService {
	private OptionDAO optionDAO = new OptionDAO();

	public List<OptionDTO> getAllOptions() {
		return optionDAO.findAll();
	}

	public boolean addOption(String name, String priceStr) {
		if (name == null || name.trim().isEmpty())
			return false;

		int price = 0;
		try {
			price = Integer.parseInt(priceStr);
		} catch (NumberFormatException e) {
			return false;
		}

		OptionDTO dto = new OptionDTO();
		dto.setOptionName(name);
		dto.setOptionPrice(price);

		return optionDAO.insert(dto);
	}
}
