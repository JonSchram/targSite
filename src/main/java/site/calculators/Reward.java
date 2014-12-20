package site.calculators;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Wrapper to store item names and amounts given as a reward.
 * 
 * @author Jonathan
 *
 */
public class Reward {

    /**
     * Stores items given as awards. Keys are item names, values are amounts.
     */
    private Map<String, Integer> items;

    public Reward() {
	items = new HashMap<String, Integer>();
    }

    /**
     * Adds the given item name and reward amount to the reward list
     * 
     * @param key Name of item
     * @param value Amount awarded
     */
    public void add(String key, int value) {
	items.put(key, value);
    }

    public Map<String, Integer> getMap() {
	return items;
    }

    public Set<String> getRewardNames() {
	return items.keySet();
    }

    public Collection<Integer> getRewardAmounts() {
	return items.values();
    }

    public int getNumberOfRewards() {
	return items.size();
    }
}
