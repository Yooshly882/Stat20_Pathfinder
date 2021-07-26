public abstract class Stat20EventHandlers {

	public int updateGP(int wealth, int itemCost) {
		return wealth + itemCost;
	}
	public int updateCarryCap(int carryCap, int itemWeight) {
		return carryCap + itemWeight;
	}
}
