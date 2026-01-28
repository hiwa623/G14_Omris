package model.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import dao.DBManager;
import dao.HallDAO;
import model.dto.HallRowDTO;

public class HallService {

    private final HallDAO hallDAO = new HallDAO();

    public List<HallRowDTO> getCookedRows() throws SQLException {
        try (Connection con = DBManager.getConnection()) {
            return hallDAO.findCookedRows(con);
        }
    }

    public List<HallRowDTO> getServedRowsWithin30Min() throws SQLException {
        try (Connection con = DBManager.getConnection()) {
            return hallDAO.findServedRowsWithin30Min(con);
        }
    }

    public void markServed(long orderDetailId) throws SQLException {
        try (Connection con = DBManager.getConnection()) {
            int updated = hallDAO.markServed(con, orderDetailId);
            if (updated != 1) throw new SQLException("更新件数不正: " + updated);
        }
    }

    public void cancelServed(long orderDetailId) throws SQLException {
        try (Connection con = DBManager.getConnection()) {
            int updated = hallDAO.cancelServed(con, orderDetailId);
            if (updated != 1) throw new SQLException("更新件数不正: " + updated);
        }
    }
}
