package ch.ksrminecraft.rangAPI.DB;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PointsAPI {
    Connection connection;
    public PointsAPI(Connection conn) {
        connection = conn;
    }
    private String getSQL(String command) {
        try (PreparedStatement ps = connection.prepareStatement(command)) {
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getString("name");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    private void SQLUpdate(String command){
        try (PreparedStatement ps = connection.prepareStatement(command)) {
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
