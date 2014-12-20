package site.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import site.calculators.GemEventReward;

public class GemEventDAO {

    /**
     * Gets reward information for an active gem event.
     * 
     * @return null if the database couldn't be reached, an empty GemEventReward
     *         if there is no active event, or otherwise a GemEventReward with
     *         data in it corresponding to the active event.
     */
    public GemEventReward getActiveRewards() {
	Connection conn = ConnectionManager.getConnection();
	if (conn == null) {
	    return null;
	}

	GemEventReward reward = new GemEventReward();

	ResultSet idResultSet = null;
	ResultSet bonusSet = null;
	ResultSet dataSet = null;
	try {
	    Statement s = conn.createStatement();
	    String idQuery = "Select `eventID` from `site`.`GemEvent` where `active`=true";
	    idResultSet = s.executeQuery(idQuery);

	    // get the eventID of the first active gem event, if there is more
	    // than one active (which would be on accident since there can only
	    // be one at a time
	    int activeID = -1;
	    if (idResultSet.next()) {
		activeID = idResultSet.getInt("eventID");
	    }

	    // active event exists
	    if (activeID > -1) {
		// query for number of bonus gems
		String bonusQuery = "SELECT `bonusLevel`, `amount` "
			+ "from `site`.`GemBonuses` where `eventID`=?";
		PreparedStatement bonusStatement = conn
			.prepareStatement(bonusQuery);
		bonusStatement.setInt(1, activeID);

		bonusSet = bonusStatement.executeQuery();

		while (bonusSet.next()) {
		    reward.setBonusGems(bonusSet.getInt("bonusLevel"),
			    bonusSet.getInt("amount"));
		}

		// query for additional rewards
		String dataQuery = "SELECT " + "`GemBonuses`.`bonusLevel`, "
			+ "`ItemAssets`.`name`, " + "`GemRewards`.`amount` "
			+ "from `site`.`GemBonuses` "
			+ "left join `site`.`GemRewards` "
			+ "on `GemBonuses`.`bonusID` = `GemRewards`.`bonusID` "
			+ "join `site`.`ItemAssets` "
			+ "on `ItemAssets`.`itemID` = `GemRewards`.`itemID` "
			+ "where `eventID`=?";

		PreparedStatement dataStatement = conn
			.prepareStatement(dataQuery);
		// set eventID to the active event
		dataStatement.setInt(1, activeID);

		dataSet = dataStatement.executeQuery();

		while (dataSet.next()) {
		    reward.addReward(dataSet.getInt("bonusLevel"),
			    dataSet.getString("name"), dataSet.getInt("amount"));
		}
	    }

	} catch (SQLException e) {
	    e.printStackTrace();
	} finally {
	    if (dataSet != null) {
		try {
		    dataSet.close();
		} catch (SQLException e) {
		    e.printStackTrace();
		}
	    }

	    if (bonusSet != null) {
		try {
		    bonusSet.close();
		} catch (SQLException e) {
		    e.printStackTrace();
		}
	    }

	    if (idResultSet != null) {
		try {
		    idResultSet.close();
		} catch (SQLException e) {
		    e.printStackTrace();
		}
	    }
	    if (conn != null) {
		try {
		    conn.close();
		} catch (SQLException e) {
		    e.printStackTrace();
		}
	    }
	}

	return reward;
    }
}
