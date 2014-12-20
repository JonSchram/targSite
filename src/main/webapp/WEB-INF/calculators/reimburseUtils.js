/**
 * Uses item and redeems reward until all items have been used (uses remaining
 * items even if using them gives no further rewards)
 */
function repeatUse(startAmount, required, returnAmount) {
	var used = 0;
	var current = startAmount;
	while (current >= required) {
		var rewardNum = Math.floor(current / required);
		var remaining = current % required;
		used += current - remaining;
		current = remaining + returnAmount * rewardNum;
	}
	used += current;
	return used;
}