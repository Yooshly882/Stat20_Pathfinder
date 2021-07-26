import java.awt.List;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Iterator;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellUtil;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.ss.usermodel.DataFormatter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public abstract class ExcelFileReaders {

	public static ArrayList<String> featReader() throws IOException {
		String featsFilePath = "C://Users//Emma//Java Workspaces//Personal//Tabletop RPG Project 2.0//src//excelData//featsTables.xlsx";
		FileInputStream featsInputStream = new FileInputStream(new File(featsFilePath));
		Workbook featsWB = new XSSFWorkbook(featsInputStream);
		Sheet featsTable = featsWB.getSheetAt(0);
		Iterator<Row> featsIterator = featsTable.iterator();

		ArrayList<String> featList = new ArrayList<String>();

		while (featsIterator.hasNext()) {
			Cell currentFeat = CellUtil.getCell(featsIterator.next(), 0);
			if (currentFeat.getRowIndex() > 2) {
				Cell currentFeatPrereqCount = CellUtil.getCell(currentFeat.getRow(), 1);
				if (currentFeatPrereqCount.getNumericCellValue() == 0) {
					featList.add(currentFeat.getStringCellValue());
				}
				else if (currentFeatPrereqCount.getNumericCellValue() == 1) {
					Iterator<Cell> prereqIterator = featsIterator.next().cellIterator();
					Cell prereqCell = CellUtil.getCell(featsIterator.next(), 1);
					while (prereqIterator.hasNext()) {
						prereqCell = prereqIterator.next();
						switch (prereqCell.getColumnIndex()) {
						//class features
						case 2: //System.out.println("Does this character have the " +prereqCell.getStringCellValue()+ " class feature?");
						if (Character.cClass.classFeatures.contains(prereqCell.getStringCellValue())) {
							featList.add(prereqCell.getStringCellValue());
							//System.out.println("You have the " + currentFeat.getStringCellValue() + " feat available, because you have the " + prereqCell.getStringCellValue() + " prerequisite.");
						} break;
						//prior feats
						case 3: //System.out.println("Does this character have the " +prereqCell.getStringCellValue()+ " feat?"); break;
						//base stats
						case 4: //System.out.println("Does this character have " +prereqCell.getStringCellValue() + "?"); break;
						//base armor bonus
						case 5: ;
						//class
						case 6: ;
						//caster level
						case 7: ;
						//character level
						case 8: ;
						//other
						case 9: ;
						default: continue;
						}
					}
				}
			}
		}
		featsWB.close();
		return featList;
	}

	public static ObservableList<String> equipmentReader(int categorySheet) throws IOException {
		String equipFilePath = "C://Users//Emma//Java Workspaces//Personal//Tabletop RPG Project 2.0//src//excelData//equipmentTables.xlsx";
		FileInputStream equipInputStream = new FileInputStream(new File(equipFilePath));
		Workbook equipWB = new XSSFWorkbook(equipInputStream);
		Sheet equipTable = equipWB.getSheetAt(categorySheet);
		int rowIndex = 2;

		ObservableList<String> equipmentList = FXCollections.observableArrayList();
		Cell equipment = null;

		if (categorySheet == 0) {
			while (equipTable.getRow(rowIndex) != null) {
				equipment = CellUtil.getCell(equipTable.getRow(rowIndex), 2);
				equipmentList.add(equipment.getStringCellValue());
				rowIndex++;
			}
		}
		else if (categorySheet == 1) {
			while (equipTable.getRow(rowIndex) != null) {
				equipment = CellUtil.getCell(equipTable.getRow(rowIndex), 1);
				equipmentList.add(equipment.getStringCellValue());
				rowIndex++;
			}
		}
		else {
			while (equipTable.getRow(rowIndex) != null) {
				equipment = CellUtil.getCell(equipTable.getRow(rowIndex), 0);
				equipmentList.add(equipment.getStringCellValue());
				rowIndex++;
			}
		}
		equipWB.close();
		return equipmentList;
	}
	public static String equipmentGeneralInfoReader(int categorySheet, int listRowIndex) throws IOException {
		String equipFilePath = "C://Users//Emma//Java Workspaces//Personal//Tabletop RPG Project 2.0//src//excelData//equipmentTables.xlsx";
		FileInputStream equipInputStream = new FileInputStream(new File(equipFilePath));
		Workbook equipWB = new XSSFWorkbook(equipInputStream);
		Sheet equipTable = equipWB.getSheetAt(categorySheet);
		//Where the index of the selection from the ListView is 0, the corresponding row in the file will be 2.
		//Therefore, there must be a conversion from the ListView selection to the rowindex.
		int wbRowIndex = listRowIndex + 2;
		DataFormatter stringFormat = new DataFormatter();

		ArrayList<String> itemData = new ArrayList<String>();
		int column = 0;
		Cell datum = CellUtil.getCell(equipTable.getRow(wbRowIndex), column);
		while (datum.getCellType() != CellType.BLANK) {
			datum = CellUtil.getCell(equipTable.getRow(wbRowIndex), column);
			itemData.add(stringFormat.formatCellValue(datum));
			column++;
		}
		String formattedOutput = itemData.get(0) +
				"\n Cost: " + itemData.get(1) +
				"\n Weight: " + itemData.get(2) + " lbs";
		return formattedOutput;
	}
	public static String equipmentArmorInfoReader(int categorySheet, int listRowIndex) throws IOException {
		String equipFilePath = "C://Users//Emma//Java Workspaces//Personal//Tabletop RPG Project 2.0//src//excelData//equipmentTables.xlsx";
		FileInputStream equipInputStream = new FileInputStream(new File(equipFilePath));
		Workbook equipWB = new XSSFWorkbook(equipInputStream);
		Sheet armorTable = equipWB.getSheetAt(categorySheet);

		int wbRowIndex = listRowIndex + 2;
		DataFormatter stringFormat = new DataFormatter();

		ArrayList<String> itemData = new ArrayList<String>();
		int column = 0;
		Cell datum = CellUtil.getCell(armorTable.getRow(wbRowIndex), column);
		while (datum.getCellType() != CellType.BLANK) {
			datum = CellUtil.getCell(armorTable.getRow(wbRowIndex), column);
			itemData.add(stringFormat.formatCellValue(datum));
			column++;
		}

		String formattedOutput = null;
		if (itemData.get(0) == "Extra") {
			formattedOutput = itemData.get(1) +
					"\n Armor Category: " + itemData.get(0) +
					"\n Cost: " + itemData.get(2) +
					"\n Weight: " + itemData.get(7) +
					"\n Dmg(S, M): " + itemData.get(8) + ", " + itemData.get(9) +
					"\n Critical: " + itemData.get(10) +
					"\n Type: " + itemData.get(11);
		}
		else {
			formattedOutput = itemData.get(1) +
					"\n Armor Category: " + itemData.get(0) +
					"\n Cost: " + itemData.get(2) +
					"\n Weight: " + itemData.get(7) +
					"\n Armor Bonus, Max Dex Bonus, Armor Check Penalty: " + itemData.get(3) + ", " + itemData.get(4) + ", " + itemData.get(5) +
					"\n Arcane Spell Failure Chance: " + itemData.get(6);
		}

		return formattedOutput;
	}
	public void equipmentShieldInfoReader(int itemRowIndex) {

	}
	public void equipmentWeaponInfoReader(int itemRowIndex) {
		//Takes a (selected) weapon info row and puts it into a form parseable by a text area.
	}
	public static double getItemCost(int sheet, int rowIndex) throws IOException {
		String equipFilePath = "C://Users//Emma//Java Workspaces//Personal//Tabletop RPG Project 2.0//src//excelData//equipmentTables.xlsx";
		FileInputStream equipInputStream = new FileInputStream(new File(equipFilePath));
		Workbook equipWB = new XSSFWorkbook(equipInputStream);
		Sheet equipTable = equipWB.getSheetAt(sheet);
		Row row = equipTable.getRow(rowIndex);

		String[] rawItemCost = new String[2];
		double pullValue = 0;
		String pullDenom = null;
		double finalItemCost = 0;

		// If it's a weapon, the cost is in column 3
		if (sheet == 0) {
			rawItemCost[0] = CellUtil.getCell(row, 3).getStringCellValue();
		}
		// If it's an armor, the cost is in column 2
		else if (sheet == 1) {
			rawItemCost[0] = CellUtil.getCell(row, 2).getStringCellValue();
		}
		// If it's a shield or anything else, the cost is in column 1
		else {
			rawItemCost[0] = CellUtil.getCell(row, 1).getStringCellValue();
		}

		rawItemCost = rawItemCost[0].split(" ");
		pullValue = Double.parseDouble(rawItemCost[0]);
		pullDenom = rawItemCost[1];

		if (pullDenom.contains("cp")) {
			finalItemCost = pullValue / 100;
		}
		else if (pullDenom.contains("sp")) {
			finalItemCost = pullValue / 10;
		}
		else if (pullDenom.contains("gp")) {
			finalItemCost = pullValue;
		}
		else if (pullDenom.contains("pp")) {
			finalItemCost = pullValue * 10;
		}
		else {
			finalItemCost = 0;
		}
		equipWB.close();
		return finalItemCost;
	}
	public static double getItemWeight(int sheet, int rowIndex) throws IOException {
		String equipFilePath = "C://Users//Emma//Java Workspaces//Personal//Tabletop RPG Project 2.0//src//excelData//equipmentTables.xlsx";
		FileInputStream equipInputStream = new FileInputStream(new File(equipFilePath));
		Workbook equipWB = new XSSFWorkbook(equipInputStream);
		Sheet equipTable = equipWB.getSheetAt(sheet);
		Row row = equipTable.getRow(rowIndex);

		double itemWeight = 0;
		if (sheet == 0) {
			itemWeight = CellUtil.getCell(row, 8).getNumericCellValue();
		}
		else if (sheet == 1) {
			itemWeight = CellUtil.getCell(row, 7).getNumericCellValue();
		}
		else if (sheet == 2) {
			itemWeight = CellUtil.getCell(row, 6).getNumericCellValue();
		}
		else {
			itemWeight = CellUtil.getCell(row, 2).getNumericCellValue();
		}
		equipWB.close();
		return itemWeight;
	}
	public static String getItemInfoAddress(int rowIndex, int sheet) throws IOException {
		String equipFilePath = "C://Users//Emma//Java Workspaces//Personal//Tabletop RPG Project 2.0//src//excelData//equipmentTables.xlsx";
		FileInputStream equipInputStream = new FileInputStream(new File(equipFilePath));
		Workbook equipWB = new XSSFWorkbook(equipInputStream);
		Sheet equipTable = equipWB.getSheetAt(sheet);
		Row row = equipTable.getRow(rowIndex);
		equipWB.close();
		return sheet + "," + row;
	}
}