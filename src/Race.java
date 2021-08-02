import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class Race {
	String raceName;
	String resourceName;
	// Ages are stored as 5 ints in an array, in sequence of Adulthood, Middle Age, Old, Venerable.
	// There is another feature of ages that are important to note, which is that a character has to be at or above a
	// certain age in order to be a certain class (one can be a young Barbarian because this is an intuitive 'job', but
	// must be trained to become a Wizard, and thus is older. Of course, if one wants to declare themselves a prodigy,
	// the user can simply enter their own age into the box--but because of these features, some aspects of agerolling
	// are handled in the Character superclass.
	int[] milestoneAges;
	PolyhedralDice intuitiveAgeRoll;
	PolyhedralDice selfTaughtAgeRoll;
	PolyhedralDice trainedAgeRoll;
	int baseMaleHeight;
	int baseMaleWeight;
	int baseFemaleHeight;
	int baseFemaleWeight;
	PolyhedralDice malePhysMod;
	PolyhedralDice femalePhysMod;
	int weightMultiplier;
	//
	int strBonus = 0;
	int dexBonus = 0;
	int conBonus = 0;
	int intBonus = 0;
	int wisBonus = 0;
	int chaBonus = 0;
	//

	public Race() {
		this.raceName = null;
	}
	public void setRace(String raceName) {
		if (raceName.equals("Elf")) {
			setRaceElf();
		}
		else if (raceName.equals("Dwarf")) {
			setRaceDwarf();
		}
		else if (raceName.equals("Human")) {
			setRaceHuman();
		}
		else if (raceName.equals("Gnome")) {
			setRaceGnome();
		}
		else if (raceName.equals("Half-Elf")) {
			setRaceHalfElf();
		}
		else if (raceName.equals("Half-Orc")) {
			setRaceHalfOrc();
		}
		else if (raceName.equals("Halfling")) {
			setRaceHalfling();
		}
	}
	public void setRaceHuman() {
		this.raceName = "Human";
		this.resourceName = "HumanBlurb.txt";
		milestoneAges = new int[]{15, 35, 53, 70};
		intuitiveAgeRoll = new PolyhedralDice(1, 4);
		selfTaughtAgeRoll = new PolyhedralDice(1, 6);
		trainedAgeRoll = new PolyhedralDice(2, 6);
		this.baseMaleHeight = 58;
		this.baseMaleWeight = 120;
		this.baseFemaleHeight = 53;
		this.baseFemaleWeight = 85;
		this.malePhysMod =  new PolyhedralDice(2, 10);
		this.femalePhysMod = malePhysMod;
		this.weightMultiplier = 5;
	}
	public void setRaceDwarf() {
		this.raceName = "Dwarf";
		this.resourceName = "DwarfBlurb.txt";
		milestoneAges = new int[]{40, 125, 188, 250};
		intuitiveAgeRoll = new PolyhedralDice(3, 6);
		selfTaughtAgeRoll = new PolyhedralDice(5, 6);
		trainedAgeRoll = new PolyhedralDice(7, 6);
		this.baseMaleHeight = 45;
		this.baseMaleWeight = 150;
		this.baseFemaleHeight = 43;
		this.baseFemaleWeight = 120;
		this.malePhysMod = new PolyhedralDice(2, 4);
		this.femalePhysMod = malePhysMod;
		this.weightMultiplier = 7;
	}
	public void setRaceElf() {
		this.raceName = "Elf";
		this.resourceName = "ElfBlurb.txt";
		milestoneAges = new int[]{110, 175, 263, 350};
		intuitiveAgeRoll = new PolyhedralDice(4, 6);
		selfTaughtAgeRoll = new PolyhedralDice(6, 6);
		trainedAgeRoll = new PolyhedralDice(10, 6);
		this.baseMaleHeight = 64;
		this.baseMaleWeight = 110;
		this.baseFemaleHeight = 64;
		this.baseFemaleWeight = 90;
		this.malePhysMod = new PolyhedralDice(2, 8);
		this.femalePhysMod = new PolyhedralDice(2, 6);
		this.weightMultiplier = 3;
	}
	public void setRaceGnome() {
		this.raceName = "Gnome";
		this.resourceName = "GnomeBlurb.txt";
		milestoneAges = new int[]{40, 100, 150, 200};
		intuitiveAgeRoll = new PolyhedralDice(4, 6);
		selfTaughtAgeRoll = new PolyhedralDice(6, 6);
		trainedAgeRoll = new PolyhedralDice(9, 6);
		this.baseMaleHeight = 36;
		this.baseMaleWeight = 35;
		this.baseFemaleHeight = 34;
		this.baseFemaleWeight = 30;
		this.malePhysMod =  new PolyhedralDice(2, 4);
		this.femalePhysMod = malePhysMod;
		this.weightMultiplier = 1;
	}
	public void setRaceHalfElf() {
		this.raceName = "Half-Elf";
		this.resourceName = "HalfElfBlurb.txt";
		milestoneAges = new int[]{20, 62, 93, 125};
		intuitiveAgeRoll = new PolyhedralDice(1, 6);
		selfTaughtAgeRoll = new PolyhedralDice(2, 6);
		trainedAgeRoll = new PolyhedralDice(3, 6);
		this.baseMaleHeight = 62;
		this.baseMaleWeight = 110;
		this.baseFemaleHeight = 60;
		this.baseFemaleWeight = 90;
		this.malePhysMod =  new PolyhedralDice(2, 8);
		this.femalePhysMod = malePhysMod;
		this.weightMultiplier = 5;
	}
	public void setRaceHalfOrc() {
		this.raceName = "Half-Orc";
		this.resourceName = "HalfOrcBlurb.txt";
		milestoneAges = new int[]{14, 30, 45, 60};
		intuitiveAgeRoll = new PolyhedralDice(1, 4);
		selfTaughtAgeRoll = new PolyhedralDice(1, 6);
		trainedAgeRoll = new PolyhedralDice(2, 6);
		this.baseMaleHeight = 58;
		this.baseMaleWeight = 150;
		this.baseFemaleHeight = 53;
		this.baseFemaleWeight = 110;
		this.malePhysMod =  new PolyhedralDice(2, 12);
		this.femalePhysMod = malePhysMod;
		this.weightMultiplier = 7;
	}
	public void setRaceHalfling() {
		this.raceName = "Halfling";
		this.resourceName = "HalflingBlurb.txt";
		milestoneAges = new int[]{20, 50, 75, 100};
		intuitiveAgeRoll = new PolyhedralDice(2, 4);
		selfTaughtAgeRoll = new PolyhedralDice(3, 6);
		trainedAgeRoll = new PolyhedralDice(4, 6);
		this.baseMaleHeight = 32;
		this.baseMaleWeight = 30;
		this.baseFemaleHeight = 30;
		this.baseFemaleWeight = 25;
		this.malePhysMod =  new PolyhedralDice(2, 4);
		this.femalePhysMod = malePhysMod;
		this.weightMultiplier = 1;
	}
	public String getRaceBlurb() {
		String blurb = new String();
		try {
			File file = new File("./readFiles/raceBlurbs/" + this.resourceName);
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
}
