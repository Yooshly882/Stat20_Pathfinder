import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class Class {
	String className = null;
	File blurbAddress = null;
	int skillTableCol = 0;
	int baseSkillRanks = 0;
	List classFeatures;
	int wealth;

	//Two constructors for the Class class (lol)
	public Class() {
		this.className = null;
	}
	public Class(String className) {
		this.className = className;
		setClassData();
	}

	//Setters
	public void setClass(String className) {
		this.className = className;
		setClassData();
	}
	public void setClassData() {
		if (this.className == "Barbarian") {
			//fileName = new File("C:\\Users\\Emma\\Java Workspaces\\Personal\\Tabletop RPG Project 2.0\\readFiles\\classBlurbs\\AlchemistBlurb.txt");
			this.skillTableCol = 3;
			this.baseSkillRanks = 4;
			this.classFeatures = Arrays.asList("Weapon Proficiency (simple)", "Weapon Proficiency (martial)", "Armor Proficiency (light)",
					"Armor Proficiency (medium)", "Shield Proficiency"/*minus tower shields*/, "Fast Movement", "Rage", "Rage Powers",
					"Uncanny Dodge");
		}
		else if (this.className == "Bard") {
			blurbAddress = new File("C:\\Users\\Emma\\Java Workspaces\\Personal\\Tabletop RPG Project 2.0\\readFiles\\classBlurbs\\BardBlurb.txt");
			this.skillTableCol = 4;
			this.baseSkillRanks = 6;
		}
		//else if (this.className == "Cleric") {}
		//else if (this.className == "Druid") {}
		//else if (this.className == "Fighter") {}
		//else if (this.className == "Monk") {}
		else if (this.className == "Paladin") {
			blurbAddress = new File("C:\\Users\\Emma\\Java Workspaces\\Personal\\Tabletop RPG Project 2.0\\readFiles\\classBlurbs\\PaladinBlurb.txt");
		}
		//else if (this.className == "Ranger") {}
		//else if (this.className == "Rogue") {}
		//else if (this.className == "Sorcerer") {}
		//else if (this.className == "Wizard") {}
	}

	//Getters
	public String getClassBlurb() {
		String blurb = new String();
		try {
			BufferedReader reader = new BufferedReader(new FileReader(blurbAddress));
			String line;
			while ((line = reader.readLine()) != null) {
				blurb += line;
			}
			reader.close();
			return blurb;
		}
		catch (Exception e) {
			System.err.format("Exception occured trying to read '%s'.", blurbAddress);
			e.printStackTrace();
			return null;
		}
	}
	public String getAgeCategory() {
		String ageCategory = null;
		if (className == "Barbarian" || className == "Rogue" || className == "Sorcerer") {
			ageCategory = "Intuitive";
		}
		else if (className == "Bard" || className == "Fighter" || className == "Paladin" || className == "Ranger") {
			ageCategory = "Self-Taught";
		}
		else if (className == "Cleric" || className == "Druid" || className == "Monk" || className == "Wizard") {
			ageCategory = "Trained";
		}
		return ageCategory;
	}
	protected int rollWealth() {
		PolyhedralDice wealthDice = null;
		if (className.equals("Monk")) {
			wealthDice = new PolyhedralDice(1, 6);
		}
		if (className.equals("Druid") || className.equals("Sorcerer") || className.equals("Wizard")) {
			wealthDice = new PolyhedralDice(2, 6);
		}
		if (className.contains("B")) { //It's both the barbarian and the bard. Change it when the classlist gets expanded.
			wealthDice = new PolyhedralDice(3, 6);
		}
		if (className.equals("Cleric") || className.equals("Rogue")) {
			wealthDice = new PolyhedralDice(4, 6);
		}
		if (className.equals("Fighter") || className.equals("Paladin") || className.equals("Ranger")) {
			wealthDice = new PolyhedralDice(5, 6);
		}
		return wealthDice.rollDice() * 10;
	}
}
