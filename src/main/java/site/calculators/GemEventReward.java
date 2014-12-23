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

    private boolean empty;

    public GemEventReward() {
	levelRewards = new HashMap<Integer, GemReward>();
	empty = true;
    }

    public boolean isEmpty() {
	return empty;
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
	    empty = false;
	}
    }

    public int getBonusGems(int level) {
	GemReward levelReward = levelRewards.get(level);
	// return number of bonus gems if there is a reward for that level, 0
	// otherwise
	return levelReward == null ? 0 : levelReward.bonusGems;
    }

    /**
     * Sets the level of the gem given as reward for synthesizing a certain
     * level gem.
     * 
     * @param synthesisLevel Gem level synthesized.
     * @param bonusLevel Gem level awarded as a bonus
     */
    public void setBonusGemLevel(int synthesisLevel, int bonusLevel) {
	if (synthesisLevel > 1 && synthesisLevel <= 12) {
	    // valid gem level, as lvl 1 gem can't be synthesized
	    if (!levelRewards.containsKey(synthesisLevel)) {
		// must add this reward level before setting level
		levelRewards.put(synthesisLevel, new GemReward());
	    }
	    // now free to set bonus gem level
	    levelRewards.get(synthesisLevel).setLevel(bonusLevel);
	    empty = false;
	}
    }

    public int getBonusGemLevel(int level) {
	GemReward levelReward = levelRewards.get(level);
	// return bonus gem level if there is a reward for that level, 0
	// otherwise
	return levelReward == null ? 0 : levelReward.getLevel();
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
	    empty = false;
	}
    }

    public Map<String, Integer> getRewards(int level) {
	GemReward levelReward = levelRewards.get(level);
	return levelReward.getMap();
    }

    public Map<Integer, GemReward> getMap() {
	return levelRewards;
    }

}
