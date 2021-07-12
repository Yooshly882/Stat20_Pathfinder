import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class Race {
	String raceName;
	File fileName;
	//
	int baseAge;
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

	public Race(String raceName) {
		this.raceName = raceName;
		setRaceData();
	}
	public void setRace(String raceName) {
		this.raceName =  raceName;
		setRaceData();
	}
	public void setRaceData() {
		if (this.raceName == "Elf") {
			this.fileName = new File("C:\\Users\\Emma\\Java Workspaces\\Personal\\Tabletop RPG Project 2.0\\readFiles\\RaceBlurbs\\ElfBlurb.txt");
			//
			this.baseAge = 110;
			this.intuitiveAgeRoll = new PolyhedralDice(4, 6);
			this.selfTaughtAgeRoll = new PolyhedralDice(6, 6);
			this.trainedAgeRoll = new PolyhedralDice(10, 6);
			//
			this.baseMaleHeight = 64;
			this.baseMaleWeight = 110;
			this.baseFemaleHeight = 64;
			this.baseFemaleWeight = 90;
			this.malePhysMod = new PolyhedralDice(2, 8);
			this.femalePhysMod = new PolyhedralDice(2, 6);
			this.weightMultiplier = 3;
		}
		if (this.raceName == "Dwarf") {
			this.fileName = new File("C:\\Users\\Emma\\Java Workspaces\\Personal\\Tabletop RPG Project 2.0\\readFiles\\RaceBlurbs\\DwarfBlurb.txt");
			this.baseAge = 40;
			this.intuitiveAgeRoll = new PolyhedralDice(3, 6);
			this.selfTaughtAgeRoll = new PolyhedralDice(5, 6);
			this.trainedAgeRoll = new PolyhedralDice(7, 6);
			//
			this.baseMaleHeight = 45;
			this.baseMaleWeight = 150;
			this.baseFemaleHeight = 43;
			this.baseFemaleWeight = 120;
			this.malePhysMod = new PolyhedralDice(2, 4);
			this.femalePhysMod = malePhysMod;
			this.weightMultiplier = 7;
		}
		if (this.raceName == "Human") {
			this.fileName = new File("C:\\Users\\Emma\\Java Workspaces\\Personal\\Tabletop RPG Project 2.0\\readFiles\\RaceBlurbs\\HumanBlurb.txt");
			this.baseAge = 15;
			this.intuitiveAgeRoll = new PolyhedralDice(1, 4);
			this.selfTaughtAgeRoll = new PolyhedralDice(1, 6);
			this.trainedAgeRoll = new PolyhedralDice(2, 6);
			//
			this.baseMaleHeight = 58;
			this.baseMaleWeight = 120;
			this.baseFemaleHeight = 53;
			this.baseFemaleWeight = 85;
			this.malePhysMod =  new PolyhedralDice(2, 10);
			this.femalePhysMod = malePhysMod;
			this.weightMultiplier = 5;
		}
	}
	public String getRaceBlurb() {
		String blurb = new String();
		try {
			BufferedReader reader = new BufferedReader(new FileReader(fileName));
			String line;
			while ((line = reader.readLine()) != null) {
				blurb += line;
			}
			reader.close();
			return blurb;
		}
		catch (Exception e) {
			System.err.format("Exeption occured trying to read '%s'.", fileName);
			e.printStackTrace();
			return null;
		}
	}
}
