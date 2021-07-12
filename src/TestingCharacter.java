public class TestingCharacter {
	public static void main(String[] args) {
		Character newChar = new Character(new Race("Human"), new Class("Alchemist"));
		newChar.gender = "Female";
		System.out.println(newChar.age);
		System.out.println(newChar.height);
		System.out.println(newChar.weight);
		System.out.println();
		newChar.rollAge();
		newChar.rollHeight();
		newChar.rollWeight();
		System.out.println(newChar.age);
		System.out.println(newChar.height + " = " + newChar.height / 12 + "\'" + newChar.height % 12 + "\"");
		System.out.println(newChar.weight);
	}
}
