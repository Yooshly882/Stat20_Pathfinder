import java.lang.reflect.Array;

public class Character {
	String playerName;
	String characterName;
	String gender;
	Race cRace;
	Class cClass;
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
	String[] languages = new String[]{"Common"};

	public Character() {
		this.cRace = new Race();
		this.cClass = new Class();
	}
	public Character(Race cRace, Class cClass) {
		this.cRace = cRace;
		this.cClass = cClass;
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
	// Two *very* important setters: A great many things depend on what the character's class and race are.
	public void setRace(String charRace) {
		switch (charRace) {
		case "Dwarf":
			// For many races, two ability scores earn bonus points, and one loses a point.
			this.constitution += 1;
			this.wisdom += 1;
			this.charisma -=1;
			this.size = 'm';
			this.speed = 20;
			this.languages = new String[]{"Common", "Dwarven"};
			//Senses
			//DefTraits
			//OffTraits
			//SkillBonuses
			//Sp or Su Abilities

		case "Elf": ;
		case "Gnome": ;
		case "Half-Elf": ;
		case "Half-Orc": ;
		case "Halfling": ;
		case "Human": ;
		}
	}
	public void setClass(String charClass) {
		switch (charClass) {
		case "Barbarian": ;
		case "Bard": ;
		case "Cleric": ;
		case "Druid": ;
		case "Fighter": ;
		case "Monk": ;
		case "Paladin": ;
		case "Ranger": ;
		case "Rogue": ;
		case "Sorcerer": ;
		case "Wizard": ;
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
}
