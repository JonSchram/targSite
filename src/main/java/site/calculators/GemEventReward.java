package site.calculators;

import java.util.HashMap;
import java.util.Map;

/**
 * Stores reward information where rewards are given for synthesizing a certain
 * level gem.
 * 
 * @author Jonathan
 *
 */
public class GemEventReward {
    /**
     * Stores rewards for each level gem synthesized.
     */
    Map<Integer, GemReward> levelRewards;

    boolean isEmpty;

    public GemEventReward() {
	levelRewards = new HashMap<Integer, GemReward>();
	isEmpty = true;
    }

    public void setBonusGems(int level, int amount) {
	if (level > 1 && level <= 12) {
	    // valid gem level, as lvl 1 gem can't be synthesized
	    if (!levelRewards.containsKey(level)) {
		// must add this reward level before setting bonus gems
		levelRewards.put(level, new GemReward());
	    }
	    // now free to set bonus gems
	    levelRewards.get(level).setBonusGems(amount);
	    isEmpty = false;
	}
    }

    public int getBonusGems(int level) {
	GemReward levelReward = levelRewards.get(level);
	// return number of bonus gems if there is a reward for that level, 0
	// otherwise
	return levelReward == null ? 0 : levelReward.bonusGems;
    }

    public void addReward(int level, String itemName, int amount) {
	if (level > 1 && level <= 12) {
	    // valid gem level, as lvl 1 gem can't be synthesized
	    if (!levelRewards.containsKey(level)) {
		// must add this reward level before setting bonus gems
		levelRewards.put(level, new GemReward());
	    }
	    // now free to set bonus gems
	    levelRewards.get(level).add(itemName, amount);
	    isEmpty = false;
	}
    }

    public Map<String, Integer> getRewards(int level) {
	GemReward levelReward = levelRewards.get(level);
	return levelReward.getMap();
    }

}
