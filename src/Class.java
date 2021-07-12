import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
public class Class {
	String className = null;
	File fileName = null;

	public Class() {
		this.className = null;
	}

	public Class(String className) {
		this.className = className;
		setClassData();
	}
	public void setClass(String className) {
		this.className = className;
		setClassData();
	}
	public void setClassData() {
		if (this.className == "Alchemist") {
			fileName = new File("C:\\Users\\Emma\\Java Workspaces\\Personal\\Tabletop RPG Project 2.0\\readFiles\\classBlurbs\\AlchemistBlurb.txt");
		}
		//else if (this.className == "Barbarian") {}
		else if (this.className == "Bard") {
			fileName = new File("C:\\Users\\Emma\\Java Workspaces\\Personal\\Tabletop RPG Project 2.0\\readFiles\\classBlurbs\\BardBlurb.txt");
		}
		//else if (this.className == "Cavalier") {}
		//else if (this.className == "Cleric") {}
		//else if (this.className == "Druid") {}
		//else if (this.className == "Gunslinger") {}
		//else if (this.className == "Fighter") {}
		//else if (this.className == "Inquisitor") {}
		//else if (this.className == "Magi") {}
		//else if (this.className == "Monk") {}
		else if (this.className == "Paladin") {
			fileName = new File("C:\\Users\\Emma\\Java Workspaces\\Personal\\Tabletop RPG Project 2.0\\readFiles\\classBlurbs\\PaladinBlurb.txt");
		}
		//else if (this.className == "Ranger") {}
		//else if (this.className == "Rogue") {}
		//else if (this.className == "Sorcerer") {}
		//else if (this.className == "Summoner") {}
		//else if (this.className == "Witch") {}
		//else if (this.className == "Wizard") {}
	}
	public String getClassBlurb() {
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
			System.err.format("Exception occured trying to read '%s'.", fileName);
			e.printStackTrace();
			return null;
		}
	}
	public String getAgeCategory() {
		String ageCategory = null;
		if (className == "Barbarian" || className == "Oracle" || className == "Rogue" || className == "Sorcerer") {
			ageCategory = "Intuitive";
		}
		else if (className == "Bard" || className == "Cavalier" || className == "Fighter" || className == "Gunslinger" ||
				className == "Paladin" || className == "Ranger" || className == "Summoner" || className == "Witch") {
			ageCategory = "Self-Taught";
		}
		else if (className == "Alchemist" || className == "Cleric" || className == "Druid" || className == "Inquisitor" || className == "Magi" || className == "Monk" || className == "Wizard") {
			ageCategory = "Trained";
		}
		return ageCategory;
	}
}
