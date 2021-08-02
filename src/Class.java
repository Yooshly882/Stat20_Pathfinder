import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Arrays;
import java.util.List;

public class Class {
	String className = null;
	String resourceName;
	int skillTableCol = 0;
	int baseSkillRanks = 0;
	List<String> initialClassFeatures;
	List<String> bonusLanguages;
	int wealth;

	//Two constructors for the Class class (lol)
	public Class() {
		this.className = null;
	}
	//Setters
	public void setClass(String className) {
		if (className.equals("Barbarian")) {
			setClassBarbarian();
		}
		else if (className.equals("Bard")) {
			setClassBard();
		}
		else if (className.equals("Cleric")) {
			setClassCleric();
		}
		else if (className.equals("Druid")) {
			setClassDruid();
		}
		else if (className.equals("Fighter")) {
			setClassFighter();
		}
		else if (className.equals("Monk")) {
			setClassMonk();
		}
		else if (className.equals("Paladin")) {
			setClassPaladin();
		}
		else if (className.equals("Ranger")) {
			setClassRanger();
		}
		else if (className.equals("Rogue")) {
			setClassRogue();
		}
		else if (className.equals("Sorcerer")) {
			setClassSorcerer();
		}
		else if (className.equals("Wizard")) {
			setClassWizard();
		}
	}
	public void setClassBarbarian() {
		this.className = "Barbarian";
		this.resourceName = "BarbarianBlurb.txt";
		this.skillTableCol = 3;
		this.baseSkillRanks = 4;
		this.initialClassFeatures = Arrays.asList("Weapon Proficiency (simple)", "Weapon Proficiency (martial)", "Armor Proficiency (light)",
				"Armor Proficiency (medium)", "Shield Proficiency"/*minus tower shields*/, "Fast Movement", "Rage", "Rage Powers");
	}
	public void setClassBard() {
		this.className = "Bard";
		this.resourceName = "BardBlurb.txt";
		this.skillTableCol = 4;
		this.baseSkillRanks = 6;
		this.initialClassFeatures = Arrays.asList("Weapon Proficiency (simple)", "Weapon Proficiency (longsword)", "Weapon Proficiency (rapier)",
				"Weapon Proficiency (sap)", "Weapon Proficiency (short sword)", "Weapon Proficiency (shortbow)", "Weapon Proficiency (whip)",
				"Armor Proficiency (light)", "Shield Proficiency"/*minus tower shields*/, "Arcane Spellcasting", "Bardic Knowledge", "Bardic Performance",
				"Cantrips");
	}
	public void setClassCleric() {
		this.className = "Cleric";
		this.resourceName = "ClericBlurb.txt";
		this.skillTableCol = 5;
		this.baseSkillRanks = 2;
		this.initialClassFeatures = Arrays.asList("Weapon Proficiency (simple)", "Armor Proficiency (light)", "Armor Proficiency (medium)",
				"Shield Proficiency"/*minus tower shields*/ /*Weapon Proficiency with weapon of deity*/, "Aura", "Divine Spellcasting", "Channel Energy",
				"Domains", "Orisons", "Spontaneous Casting", "Bonus Languages");
		bonusLanguages = Arrays.asList("Celestial", "Abyssal", "Infernal" /*languages of good, chaotic evil, and lawful evil outsiders, respectively*/);
	}
	public void setClassDruid() {
		this.className = "Druid";
		this.resourceName = "DruidBlurb.txt";
		this.skillTableCol = 6;
		this.baseSkillRanks = 4;
		this.initialClassFeatures = Arrays.asList("Weapon Proficiency (club)", "Weapon Proficiency (dagger)", "Weapon Proficiency (dart)",
				"Weapon Proficiency (quarterstaff)", "Weapon Proficiency (scythe)", "Weapon Proficiency (sickle)", "Weapon Proficiency (shortspear)",
				"Weapon Proficiency (sling)", "Weapon Proficiency (spear)", "Armor Proficiency (light)", "Armor Proficency (medium)", /*unable to wear metal*/
				"Shield Proficiency"/*minus tower shields; only able to use wood*/, "Divine Spellcasting", "Spontaneous Casting", "Orisons", "Nature Bond",
				"Nature Sense", "Wild Empathy");
		bonusLanguages = Arrays.asList("Sylvan", "Druidic" /*Druidic is free*/);
	}
	public void setClassFighter() {
		this.className = "Fighter";
		this.resourceName = "FighterBlurb.txt";
		this.skillTableCol = 7;
		this.initialClassFeatures = Arrays.asList("Weapon Proficiency (simple)", "Weapon Proficiency (martial)", "Armor Proficiency (light)",
				"Armor Proficiency (medium)", "Armor Proficiency (heavy)", "Shield Proficiency"/*including tower shields*/, "Bonus Feats");
		this.baseSkillRanks = 2;
	}
	public void setClassMonk() {
		this.className = "Monk";
		this.resourceName = "MonkBlurb.txt";
		this.skillTableCol = 8;
		this.baseSkillRanks = 4;
		this.initialClassFeatures = Arrays.asList("Weapon Proficiency (club)", "Weapon Proficiency (crossbow, light)", "Weapon Proficiency (crossbow, heavy)", "Weapon Proficiency (dagger)",
				"Weapon Proficiency (handaxe)", "Weapon Proficiency (javelin)", "Weapon Proficiency (kama)", "Weapon Proficiency (nunchaku)",
				"Weapon Proficiency (quarterstaff)", "Weapon Proficiency (sai)", "Weapon Proficiency (shortspear)", "Weapon Proficiency (short sword)",
				"Weapon Proficiency (shuriken)", "Weapon Proficiency (siangham)", "Weapon Proficiency (sling)", "Weapon Proficiency (spear)", "AC Bonus",
				"Flurry of Blows", "Unarmed Strike", "Bonus Feats", "Stunning Fist");
	}
	public void setClassPaladin() {
		this.className = "Paladin";
		this.resourceName = "PaladinBlurb.txt";
		this.skillTableCol = 11;
		this.baseSkillRanks = 2;
		this.initialClassFeatures = Arrays.asList("Weapon Proficiency (simple)", "Weapon Proficiency (martial)", "Armor Proficiency (light)",
				"Armor Proficiency (medium)", "Armor Proficiency (heavy)", "Shield Proficiency"/*except tower shields*/, "Aura of Good", "Detect Evil",
				"Smite Evil");
	}
	public void setClassRanger() {
		this.className = "Ranger";
		this.resourceName = "RangerBlurb.txt";
		this.skillTableCol = 9;
		this.baseSkillRanks = 6;
		this.initialClassFeatures = Arrays.asList("Weapon Proficiency (simple)", "Weapon Proficiency (martial)", "Armor Proficiency (light)",
				"Armor Proficiency (medium)", "Shield Proficiency"/*except tower shields*/, "Favored Enemy", "Track", "Wild Empathy");
	}
	public void setClassRogue() {
		this.className = "Rogue";
		this.resourceName = "RogueBlurb.txt";
		this.skillTableCol = 10;
		this.baseSkillRanks = 8;
		this.initialClassFeatures = Arrays.asList("Weapon Proficiency (simple)", "Weapon Proficiency (hand crossbow)", "Weapon Proficiency (rapier)",
				"Weapon Proficiency (sap)", "Weapon Proficiency (shortbow)", "Weapon Proficiency (short sword)", "Armor Proficiency (light)",
				"Trapfinding");
	}
	public void setClassSorcerer() {
		this.className = "Sorcerer";
		this.resourceName = "SorcererBlurb.txt";
		this.skillTableCol = 12;
		this.baseSkillRanks = 2;
		this.initialClassFeatures = Arrays.asList("Weapon Proficiency (simple)", "Arcane Spellcasting", "Bloodline", "Cantrips", "Eschew Materials");
	}
	public void setClassWizard() {
		this.className = "Wizard";
		this.resourceName = "WizardBlurb.txt";
		this.skillTableCol = 13;
		this.baseSkillRanks = 2;
		this.initialClassFeatures = Arrays.asList("Weapon Proficiency (club)", "Weapon Proficiency (dagger)", "Weapon Proficiency (crossbow, heavy)",
				"Weapon Proficiency(crossbow, light)", "Weapon Proficiency (quarterstaff)", "Arcane Spellcasting", "Arcane Bond", "Arcane School", "Cantrips",
				"Scribe Scroll", "Spellbooks");
		bonusLanguages = Arrays.asList("Draconic" /*Substitute for a racial language*/);
	}

	//Getters
	public int getBaseSkillRanks() {
		return this.baseSkillRanks;
	}
	public String getClassBlurb() {
		String blurb = new String();
		try {
			File file = new File("./readFiles/classBlurbs/" + this.resourceName);
			BufferedReader reader = new BufferedReader(new FileReader(file));
			String line;
			while ((line = reader.readLine()) != null) {
				blurb += line;
			}
			reader.close();
			return blurb;
		}
		catch (Exception e) {
			System.err.format("Exeption occured trying to read '%s'.", resourceName);
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
