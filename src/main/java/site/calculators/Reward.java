package site.calculators;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Reward {
    private String itemName;
    private int useReq;
    private int returnAmount;
    private Map<String, Integer> additional;

    private boolean isValidUseReq = false;
    private boolean isValidReimburse = false;

    public Reward() {
	itemName = "";
	useReq = -1;
	returnAmount = -1;
	additional = new HashMap<String, Integer>();
    }

    public Reward(String itemName, int useReq, int returnAmount) {
	this.itemName = itemName;

	if (useReq > 0) {
	    isValidUseReq = true;
	    this.useReq = useReq;
	} else {
	    this.useReq = -1;
	}

	if (returnAmount > 0) {
	    isValidReimburse = true;
	    this.returnAmount = returnAmount;
	} else {
	    this.returnAmount = -1;
	}

	additional = new HashMap<String, Integer>();

    }

    public boolean isEmpty() {
	return !(isValidUseReq && isValidReimburse);
    }

    public void setItemName(String itemName) {
	this.itemName = itemName;
    }

    public void setUseRequired(int useReq) {
	if (useReq > 0) {
	    isValidUseReq = true;
	    this.useReq = useReq;
	} else {
	    this.useReq = -1;
	}
    }

    public void setReturnAmount(int returnAmount) {
	if (returnAmount > 0) {
	    isValidReimburse = true;
	    this.returnAmount = returnAmount;
	} else {
	    this.returnAmount = -1;
	}
    }

    public String getItemName() {
	return itemName;
    }

    public int getUseRequired() {
	return useReq;
    }

    public int getReturnAmount() {
	return returnAmount;
    }

    public void add(String key, int value) {
	additional.put(key, value);
    }

    public Map<String, Integer> getMap() {
	return additional;
    }

    public Set<String> getRewardNames() {
	return additional.keySet();
    }

    public Collection<Integer> getRewardAmounts() {
	return additional.values();
    }

    public int getNumberOfRewards() {
	return additional.size();
    }
}
