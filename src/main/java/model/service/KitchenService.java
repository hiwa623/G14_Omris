package model.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import dao.DBManager;
import dao.KitchenDAO;
import model.dto.KitchenRowDTO;

public class KitchenService {

    private final KitchenDAO kitchenDAO = new KitchenDAO();

    public List<KitchenRowDTO> getCookingRows() throws SQLException {
        try (Connection con = DBManager.getConnection()) {
            return kitchenDAO.findCookingRows(con);
        }
    }

    public List<KitchenRowDTO> getCookedRows() throws SQLException {
        try (Connection con = DBManager.getConnection()) {
            return kitchenDAO.findCookedRows(con);
        }
    }

    public void markCooked(long orderDetailId) throws SQLException {
        try (Connection con = DBManager.getConnection()) {
            int updated = kitchenDAO.markCooked(con, orderDetailId);
            if (updated != 1) throw new SQLException("更新件数不正: " + updated);
        }
    }

    public void cancelCompleted(long orderDetailId) throws SQLException {
        try (Connection con = DBManager.getConnection()) {
            int updated = kitchenDAO.cancelCompleted(con, orderDetailId);
            if (updated != 1) throw new SQLException("更新件数不正: " + updated);
        }
    }
}
