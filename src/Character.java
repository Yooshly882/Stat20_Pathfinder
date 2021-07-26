import java.lang.reflect.Array;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellUtil;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class Character {
	String playerName;
	String characterName;
	String gender;
	Race cRace;
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
	int carryCap;
	ArrayList<String> itemAddresses = new ArrayList<String>();

	public Character() {
		this.cRace = new Race();
		this.cClass = new Class();
	}
	public Character(Race cRace, Class cClass) {
		this.cRace = cRace;
		this.cClass = cClass;
		this.availibleSkillRanks = this.calcInitialSkillRanks();
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
		int mod = -100;
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
		}
		return mod;
	}

	public int calcInitialSkillRanks() {
		if ((this.cRace).equals("Human")) {
			return (this.cClass.baseSkillRanks + calcModifier(this.intelligence) + 1);
		}
		else {
			return (this.cClass.baseSkillRanks + calcModifier(this.intelligence));
		}
	}
	public int getAvailibleSkillRanks() {
		return this.availibleSkillRanks;
	}
	public void applySkillRank() {
		this.availibleSkillRanks--;
	}
	public void unApplySkillRank() {
		if (this.calcInitialSkillRanks() <= this.availibleSkillRanks) {
			this.availibleSkillRanks--;
		}
	}
	public void resetLanguages() {
		this.languages = new String[]{};
	}
	public void rollAge() throws NullPointerException {
		if (cClass.getAgeCategory() == "Intuitive") {
			this.age = cRace.baseAge + cRace.intuitiveAgeRoll.rollDice();
		}
		else if (cClass.getAgeCategory() == "Self-Taught") {
			this.age = cRace.baseAge + cRace.selfTaughtAgeRoll.rollDice();
		}
		else if (cClass.getAgeCategory() == "Trained") {
			this.age = cRace.baseAge + cRace.selfTaughtAgeRoll.rollDice();
		}
	}
	public void rollHeight() throws NullPointerException {
		if (this.gender == "Male") {
			this.height = cRace.baseMaleHeight + cRace.malePhysMod.rollDice();
		}
		else if (this.gender == "Female") {
			this.height = cRace.baseFemaleHeight + cRace.femalePhysMod.rollDice();
		}
	}
	public void rollWeight() throws NullPointerException {
		if (this.gender == "Male") {
			this.weight = cRace.baseMaleWeight + (cRace.weightMultiplier * cRace.malePhysMod.rollDice());
		}
		else if (this.gender == "Female") {
			this.weight = cRace.baseFemaleWeight + (cRace.weightMultiplier * cRace.femalePhysMod.rollDice());
		}
	}
	public int rollBaseStats() throws NullPointerException {
		PolyhedralDice baseStatDice = new PolyhedralDice(3, 6);
		return baseStatDice.rollDice();
	}
	public double getGP() {
		if (this.gp == 0) {
			this.gp = this.cClass.rollWealth();
		}
		return this.gp;
	}
	public int getCarryCap() {
		return this.carryCap;
	}
	public void packItem(int sheet, int selectionIndex) throws IOException {

		int rowIndex = selectionIndex + 2;
		double itemCost = ExcelFileReaders.getItemCost(sheet, rowIndex);
		double itemWeight = ExcelFileReaders.getItemWeight(sheet, rowIndex);

		if (this.getGP() >= itemCost /*&& this.getCarryCap >= weight*/) {
			this.itemAddresses.add(ExcelFileReaders.getItemInfoAddress(rowIndex, sheet));
			this.gp -= itemCost;
			this.carryCap -= itemWeight;
		}
		else if (this.getGP() < itemCost) {
			System.out.println("Not enough money!");
		}
		else if (this.getCarryCap() < itemWeight) {
			System.out.println("Too weak!");
		}
	}
}
