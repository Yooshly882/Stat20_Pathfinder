import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class Character {
	String playerName;
	String characterName;
	String gender;
	static Race cRace;
	static Class cClass;
	String alignment;
	//
	int age;
	char size;
	int height;
	int weight;
	int speed;
	//
	int strength;
	int dexterity;
	int constitution;
	int intelligence;
	int wisdom;
	int charisma;
	//
	int availibleSkillRanks = 0;
	String[] languages = new String[]{"Common"};
	double gp = 0;
	int burden = 0;
	int[] carryCap = new int[3];
	String burdenCategory;
	ArrayList<String> itemAddresses = new ArrayList<String>();

	public Character() {
		Character.cRace = new Race();
		Character.cClass = new Class();
	}
	public void setCharacterRace(String raceName) {
		Character.cRace.setRace(raceName);
	}
	public void setCharacterClass(String className) {
		Character.cClass.setClass(className);
		calcInitialSkillRanks();
	}
	public String getCharacterRace() {
		return cRace.raceName;
	}
	public String getCharacterClass() {
		return cClass.className;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public int calcModifier(int stat) {
		int mod = -789;
		switch(stat) {
		case 1: mod = -5; break;
		case 2:
		case 3: mod = -4; break;
		case 4:
		case 5: mod = -3; break;
		case 6:
		case 7: mod = -2; break;
		case 8:
		case 9: mod = -1; break;
		case 10:
		case 11: mod = 0; break;
		case 12:
		case 13: mod = 1; break;
		case 14:
		case 15: mod = 2; break;
		case 16:
		case 17: mod = 3; break;
		case 18: mod = 4; break;
		default: mod = 9999999; break;
		}
		return mod;
	}

	public int getBaseStat(String statName) {
		int statValue = -1;
		if (statName.equals("Str")) {
			statValue = this.strength;
		}
		else if (statName.equals("Dex")) {
			statValue = this.dexterity;
		}
		else if (statName.equals("Con")) {
			statValue = this.constitution;
		}
		else if (statName.equals("Int")) {
			statValue = this.intelligence;
		}
		else if (statName.equals("Wis")) {
			statValue = this.wisdom;
		}
		else if (statName.equals("Cha")) {
			statValue = this.charisma;
		}
		return statValue;
	}
	// The calculation of initial skill ranks depends upon the race, class, and intelligence modifier.
	// Therefore, this method can only be called when race!=null, class!=null, and intelligence!=0.
	public void calcInitialSkillRanks() {
		int initialSkillRanks = 0;
		if (Character.cRace!=null && Character.cClass!=null && this.intelligence!=0) {
			if ((Character.cRace).equals("Human")) {
				initialSkillRanks = Character.cClass.getBaseSkillRanks() + calcModifier(this.intelligence) + 1;
			}
			else {
				initialSkillRanks = Character.cClass.getBaseSkillRanks() + calcModifier(this.intelligence);
			}
			this.availibleSkillRanks = initialSkillRanks;
		}
		else {
			throw new NullPointerException();
		}
	}
	public int getAvailibleSkillRanks() {
		return this.availibleSkillRanks;
	}
	public void applySkillRank() {
		this.availibleSkillRanks--;
	}
	public void unApplySkillRank() {
		this.availibleSkillRanks++;
	}
	public void resetLanguages() {
		this.languages = new String[]{};
	}
	public void rollAge() throws NullPointerException {
		if (cClass.getAgeCategory() == "Intuitive") {
			this.age = cRace.milestoneAges[0] + cRace.intuitiveAgeRoll.rollDice();
		}
		else if (cClass.getAgeCategory() == "Self-Taught") {
			this.age = cRace.milestoneAges[0] + cRace.selfTaughtAgeRoll.rollDice();
		}
		else if (cClass.getAgeCategory() == "Trained") {
			this.age = cRace.milestoneAges[0] + cRace.trainedAgeRoll.rollDice();
		}
	}
	public int getAge() {
		if (this.age == 0) {
			rollAge();
		}
		return this.age;
	}
	public void rollHeight() throws NullPointerException {
		if (this.gender == "Male") {
			this.height = cRace.baseMaleHeight + cRace.malePhysMod.rollDice();
		}
		else if (this.gender == "Female") {
			this.height = cRace.baseFemaleHeight + cRace.femalePhysMod.rollDice();
		}
		else {
			throw new NullPointerException("No gender selected!");
		}
	}
	public int getHeight() {
		if (this.height == 0) {
			rollHeight();
		}
		return this.height;
	}
	public int convertHeightStringToInt(String height) {
		int feetMarker = height.lastIndexOf("\'");
		int inchesMarker = height.lastIndexOf("\"");
		int feet = Integer.parseInt(height.substring(0, feetMarker));
		int inches = Integer.parseInt(height.substring(feetMarker + 1, inchesMarker));
		return ((feet * 12) + inches);
	}
	public void rollWeight() throws NullPointerException {
		if (this.gender == "Male") {
			this.weight = cRace.baseMaleWeight + (cRace.weightMultiplier * cRace.malePhysMod.rollDice());
		}
		else if (this.gender == "Female") {
			this.weight = cRace.baseFemaleWeight + (cRace.weightMultiplier * cRace.femalePhysMod.rollDice());
		}
	}
	public int getWeight() {
		if (this.weight == 0) {
			rollWeight();
		}
		return this.weight;
	}
	public int convertWeightStringToInt(String weight) {
		return Integer.parseInt(weight.substring(0, weight.lastIndexOf(" ")));
	}
	public int rollBaseStats() throws NullPointerException {
		PolyhedralDice baseStatDice = new PolyhedralDice(3, 6);
		return baseStatDice.rollDice();
	}
	public double getGP() {
		if (this.gp == 0) {
			this.gp = Character.cClass.rollWealth();
		}
		return this.gp;
	}
	public void setCarryCap() {
		//This method will return the light, medium, and heavy load for each possible strength score.
		switch(this.strength) {
		case 1: carryCap = new int[]{3, 6, 10}; break;
		case 2: carryCap = new int[]{6, 13, 20}; break;
		case 3: carryCap = new int[]{10, 20, 30}; break;
		case 4: carryCap = new int[]{13, 26, 40}; break;
		case 5: carryCap = new int[]{16, 33, 50}; break;
		case 6: carryCap = new int[]{20, 40, 60}; break;
		case 7: carryCap = new int[]{23, 46, 70}; break;
		case 8: carryCap = new int[]{26, 53, 80}; break;
		case 9: carryCap = new int[]{30, 60, 90}; break;
		case 10: carryCap = new int[]{33, 66, 100}; break;
		case 11: carryCap = new int[]{38, 76, 115}; break;
		case 12: carryCap = new int[]{43, 86, 130}; break;
		case 13: carryCap = new int[]{50, 100, 150}; break;
		case 14: carryCap = new int[]{58, 116, 175}; break;
		case 15: carryCap = new int[]{66, 133, 200}; break;
		case 16: carryCap = new int[]{76, 153, 230}; break;
		case 17: carryCap = new int[]{86, 173, 260}; break;
		case 18: carryCap = new int[]{100, 200, 300}; break;
		default: throw new NullPointerException();
		}
	}
	public int getCarryCap() {
		int limit = 0;
		if (this.burdenCategory.equals("light")) {
			limit = carryCap[0];
		}
		else if (this.burdenCategory.equals("medium")) {
			limit = carryCap[1];
		}
		else if (this.burdenCategory.equals("heavy")) {
			limit = carryCap[2];
		}
		return limit;
	}
	public void addBurden(double itemWeight) {
		String burdenCategory = null;
		if (this.burden + itemWeight <= carryCap[0]) {
			//the character still has a 'light' burden
			this.burden += itemWeight;
			this.burdenCategory = "light";
		}
		else if (this.burden + itemWeight <= carryCap[1]) {
			//the character now has a 'medium' burden
			this.burden += itemWeight;
			this.burdenCategory = "medium";
		}
		else if (this.burden + itemWeight <= carryCap[2]) {
			//the character has a 'heavy' burden
			this.burden += itemWeight;
			this.burdenCategory = "heavy";
		}
		else if (this.burden + itemWeight > carryCap[2]) {
			//the character has a greater-than-heavy burden--this is disallowed
		}
	}
	public void removeBurden(double itemWeight) {
		String burdenCategory = null;
		if (this.burden - itemWeight < carryCap[0]) {
			//the character has a 'light' burden
			this.burden -= itemWeight;
			this.burdenCategory = "light";
		}
		else if (this.burden - itemWeight < carryCap[1]) {
			//the character has a 'medium' burden
			this.burden -= itemWeight;
			this.burdenCategory = "medium";
		}
		else if (this.burden - itemWeight < carryCap[2]) {
			//the character has a 'heavy' burden
			this.burden -= itemWeight;
			this.burdenCategory = "heavy";
		}
	}
	public void packItem(int sheet, int selectionIndex) throws IOException {

		int rowIndex = selectionIndex + 2;
		double itemCost = ExcelFileReaders.getItemCost(sheet, rowIndex);
		double itemWeight = ExcelFileReaders.getItemWeight(sheet, rowIndex);

		if (this.getGP() >= itemCost && this.carryCap[2] >= (itemWeight + this.burden)) {
			this.itemAddresses.add(ExcelFileReaders.getItemInfoAddress(sheet, rowIndex));
			this.gp -= itemCost;
			addBurden(itemWeight);

		}
		else if (this.getGP() < itemCost) {
			System.out.println("Not enough money!");
		}
		else if (this.carryCap[2] < itemWeight + this.burden) {
			System.out.println("Too weak!");
		}

	}
	public void unpackItem(int sheet, int selectionIndex) throws IOException {
		int rowIndex = selectionIndex + 2;
		double itemCost = ExcelFileReaders.getItemCost(sheet, rowIndex);
		double itemWeight = ExcelFileReaders.getItemWeight(sheet, rowIndex);

		if (this.itemAddresses.contains(ExcelFileReaders.getItemInfoAddress(sheet, rowIndex))) {
			this.itemAddresses.remove(ExcelFileReaders.getItemInfoAddress(sheet, rowIndex));
			this.gp += itemCost;
			removeBurden(itemWeight);
		}
		else {
			System.out.println("Cannot remove; that item is not in your inventory!");
		}
	}
	public void writeCharacterToFile() {
		//Write the output to this method.
	}
}
