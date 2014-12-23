package site.calculators;

/**
 * Stores reward information where both a bonus gem and additional items are
 * given as a reward.
 * 
 * @author Jonathan
 *
 */
public class GemReward extends Reward {
    /**
     * The number of bonus gems (of the same level) that are awarded for
     * synthesizing this level gem.
     */
    int bonusGems;

    /**
     * Level of gem awarded
     */
    int level;

    /**
     * Constructs a GemReward object with a default of 0 bonus gems
     */
    public GemReward() {
	super();
	bonusGems = 0;
	level = 0;
    }

    /**
     * Constructs a GemReward object with the specified number of bonus gems.
     * 
     * @param bonusGems Number of bonus gems awarded for this level.
     */
    public GemReward(int bonusGems, int level) {
	super();
	setBonusGems(bonusGems);
	setLevel(level);
    }

    public void setLevel(int level) {
	if (level >= 1 && level <= 12) {
	    this.level = level;
	} else {
	    level = 0;
	}
    }

    /**
     * Sets the number of bonus gems of this level that are awarded.
     * 
     * @param number Number of bonus gems as a reward
     */
    public void setBonusGems(int number) {
	// if number < 0 set bonusGems to 0 instead
	bonusGems = number >= 0 ? number : 0;
    }

    public int getBonusGems() {
	return bonusGems;
    }

    public int getLevel() {
	return level;
    }
}
