public class PolyhedralDice {
	protected int noOfDice;
	protected int noOfSides;

	/*
	protected PolyhedralDice() {
		this.noOfDice = 0;
		this.noOfSides = 0;
	}
	*/

	protected PolyhedralDice(int dice, int sides) {
		this.noOfDice = dice;
		this.noOfSides = sides;
	}

	protected int rollDice() {
		int total = 0;
		for (int i = 0; i < noOfDice; i++) {
			total += (int)(Math.random() * noOfSides) + 1;
		}
		return total;
	}
}
