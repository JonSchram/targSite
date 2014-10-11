package site.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import site.EventType;
import site.calculators.Reward;

public class EventDAO {

    public Reward getFullRewards(EventType wEvent) {
	Connection conn = ConnectionManager.getConnection();
	if (conn == null) {
	    return null;
	}

	Reward results = new Reward();

	if (wEvent != null) {
	    try {
		// get event name as it appears in the database
		String databaseName = wEvent.getDatabaseName();

		// statement to get event info and return on usage
		PreparedStatement eventStatement = conn
			.prepareStatement("select `eventID`, `usageReq`, `rewardAmount` from `site`.`eventTypes` where `eventName` = ?");
		eventStatement.setString(1, databaseName);

		ResultSet eventSet = eventStatement.executeQuery();

		// extract event ID from event to get additional rewards
		int eventID;
		if (eventSet.next()) {
		    eventID = eventSet.getInt("eventID");
		    // save the required amounts and reward amounts
		    results.setUseRequired(eventSet.getInt("usageReq"));
		    results.setReturnAmount(eventSet.getInt("rewardAmount"));
		} else {
		    // the event doesn't exist (shouldn't happen, but might)
		    return results;
		}

		// using event id, get more rewards that should be awarded
		PreparedStatement rewardsGetStatement = conn
			.prepareStatement("select `rewardName`, `rewardAmount` from `site`.`eventRewards` where `owningEvent` = ?");

		rewardsGetStatement.setInt(1, eventID);

		ResultSet rewardSet = rewardsGetStatement.executeQuery();

		// put each pair of reward name and amount into the map
		while (rewardSet.next()) {
		    results.add(rewardSet.getString("rewardName"),
			    rewardSet.getInt("rewardAmount"));
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
	}

	return results;
    }
}
