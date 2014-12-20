package site.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import site.calculators.ReimburseReward;

public class EventDAO {

    /**
     * @param wEvent
     * @return
     */
    public ReimburseReward getFullRewards(String wEvent) {
	Connection conn = ConnectionManager.getConnection();
	if (conn == null) {
	    return null;
	}

	ReimburseReward results = new ReimburseReward();
	// declare ResultSets outside of try block so they can be closed in
	// finally block
	ResultSet eventSet = null;
	ResultSet rewardSet = null;
	try {

	    // statement to get event info and return on usage
	    PreparedStatement eventStatement = conn
		    .prepareStatement("SELECT\n"
			    + "`ReimburseEvent`.`eventID`,\n"
			    + "`ItemAssets`.`name`,\n"
			    + "`ReimburseEvent`.`useReq`,\n"
			    + "`ReimburseEvent`.`reimburse`\n"
			    + "from `site`.`ReimburseEvent`\n"
			    + "left join `site`.`ItemAssets`\n"
			    + "on `site`.`ReimburseEvent`.`itemID` = `site`.`ItemAssets`.`itemID`\n"
			    + "where `active` = true and `shortName`=?");

	    // wEvent should be shortName
	    eventStatement.setString(1, wEvent);
	    eventSet = eventStatement.executeQuery();
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

	    int eventID = eventSet.getInt("eventID");

	    // using event id, get more rewards that should be awarded
	    PreparedStatement rewardsGetStatement = conn
		    .prepareStatement("select"
			    + "`ItemAssets`.`name`,"
			    + "`ReimburseRewards`.`amount`\n"
			    + "from `site`.`ReimburseRewards`\n"
			    + "join `site`.`ItemAssets`\n"
			    + "on `site`.`ReimburseRewards`.`itemID`=`site`.`ItemAssets`.`itemID`"
			    + "where `eventID`=?");

	    rewardsGetStatement.setInt(1, eventID);
	    rewardSet = rewardsGetStatement.executeQuery();
	    // put each pair of reward name and amount into the map
	    while (rewardSet.next()) {
		results.add(rewardSet.getString("name"),
			rewardSet.getInt("amount"));
	    }
	    // all rewards have been extracted

	} catch (SQLException e) {
	    e.printStackTrace();

	} finally {
	    // I had no idea it is good practice to close a ResultSet (or that
	    // it can be done). Recommendation from MySQL docs:
	    // http://dev.mysql.com/doc/connector-j/en/connector-j-usagenotes-statements.html
	    if (rewardSet != null) {
		try {
		    rewardSet.close();
		} catch (SQLException e) {
		    e.printStackTrace();
		}
		rewardSet = null;
	    }

	    if (eventSet != null) {
		try {
		    eventSet.close();
		} catch (SQLException e) {
		    e.printStackTrace();
		}
		eventSet = null;
	    }

	    if (conn != null) {
		try {
		    conn.close();
		} catch (SQLException e) {
		    e.printStackTrace();
		}
	    }
	}

	return results;
    }
}
