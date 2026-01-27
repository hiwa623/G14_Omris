package model.service;

import java.util.List;

import dao.CategoryDAO;
import model.dto.CategoryDTO;

public class CategoryService {
	private CategoryDAO categoryDAO = new CategoryDAO();

	public List<CategoryDTO> getAllCategories() {
		return categoryDAO.findAll();
	}

	public boolean addCategory(String name) {
		if (name == null || name.trim().isEmpty()) {
			return false;
		}
		return categoryDAO.insert(name);
	}	
}
