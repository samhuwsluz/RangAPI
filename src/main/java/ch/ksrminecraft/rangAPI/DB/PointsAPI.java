package ch.ksrminecraft.rangAPI.DB;
import java.sql.Connection;

public class PointsAPI {
    Connection connection;
    public PointsAPI(Connection conn) {
        connection = conn;
    }
}
