package site.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import site.calculators.Reward;

public class EventDAO {

    public Reward getFullRewards(int wEvent) {
	Connection conn = ConnectionManager.getConnection();
	if (conn == null) {
	    return null;
	}

	Reward results = new Reward();

	try {
	    // statement to get event info and return on usage
	    PreparedStatement eventStatement = conn
		    .prepareStatement("SELECT\n"
			    + "`itemAssets`.`name`,"
			    + "`reimburseEvent`.`useReq`,"
			    + "`reimburseEvent`.`reimburse`"
			    + "from `site`.`reimburseEvent`\n"
			    + "left join `site`.`itemAssets`\n"
			    + "on `site`.`reimburseEvent`.`itemID` = `site`.`itemAssets`.`ID`\n"
			    + "where eventID=?");
	    eventStatement.setInt(1, wEvent);

	    ResultSet eventSet = eventStatement.executeQuery();

	    if (eventSet.next()) {
		// get name of event item from database results
		results.setItemName(eventSet.getString("name"));
		// save the required amounts and reward amounts
		results.setUseRequired(eventSet.getInt("useReq"));
		results.setReturnAmount(eventSet.getInt("reimburse"));
	    } else {
		// the event doesn't exist (shouldn't happen, but might)
		return results;
	    }

	    // using event id, get more rewards that should be awarded
	    PreparedStatement rewardsGetStatement = conn
		    .prepareStatement("select"
			    + "`itemAssets`.`name`,"
			    + "`reimburseRewards`.`amount`\n"
			    + "from `site`.`reimburseRewards`\n"
			    + "join `site`.`itemAssets`\n"
			    + "on `site`.`reimburseRewards`.`itemID`=`site`.`itemAssets`.`ID`"
			    + "where `owningID`=?");
	    rewardsGetStatement.setInt(1, wEvent);

	    ResultSet rewardSet = rewardsGetStatement.executeQuery();

	    // put each pair of reward name and amount into the map
	    while (rewardSet.next()) {
		results.add(rewardSet.getString("name"),
			rewardSet.getInt("amount"));
	    }

	    // all rewards have been extracted

	} catch (SQLException e) {
	    e.printStackTrace();

	} finally {
	    try {
		if (conn != null && !conn.isClosed()) {
		    conn.close();
		}
	    } catch (SQLException e) {
		e.printStackTrace();
	    }
	}

	return results;
    }
}
