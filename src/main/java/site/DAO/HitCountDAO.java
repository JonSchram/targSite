package site.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class HitCountDAO {

	public int doHit(String counterID) {
		Connection conn = ConnectionManager.getConnection();

		if (counterID == null || "".equals(counterID)) {
			return -1;
		}

		if (conn == null) {
			return -1;
		}

		try {
			PreparedStatement hitStatement = conn
					.prepareStatement("INSERT INTO `site`.`hitCount` (id, hits) VALUES (?,1) ON duplicate key UPDATE hits = hits + 1;");
			hitStatement.setString(1, counterID);
			hitStatement.execute();

			PreparedStatement getStatement = conn
					.prepareStatement("SELECT hits FROM `site`.`hitCount` WHERE id = ?");
			getStatement.setString(1, counterID);
			ResultSet hitSet = getStatement.executeQuery();
			if (hitSet.next()) {
				return hitSet.getInt(1);
			}
			return -1;

		} catch (SQLException e) {
			e.printStackTrace();
			return -1;
		} finally {
			try {
				if (!conn.isClosed()) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}

}
