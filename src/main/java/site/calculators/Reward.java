package site.calculators;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Reward {
    private Map<String, Integer> items;

    public Reward() {
	items = new HashMap<String, Integer>();
    }

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
