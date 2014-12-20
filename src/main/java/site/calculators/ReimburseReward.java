package site.calculators;

/**
 * Stores reward information where rewards are given for using an item, and that
 * item is returned as a reward.
 * 
 * @author Jonathan
 *
 */
public class ReimburseReward extends Reward {
    private String itemName;
    private int useReq;
    private int returnAmount;

    private boolean isValidUseReq = false;
    private boolean isValidReimburse = false;

    public ReimburseReward() {
	super();
	itemName = "";
	useReq = -1;
	returnAmount = -1;
    }

    public ReimburseReward(String itemName, int useReq, int returnAmount) {
	super();
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

}
