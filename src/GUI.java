import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellAddress;
import org.apache.poi.ss.util.CellUtil;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class GUI extends Application {
	// newChar is the object that saves all information in real time, without needing to be passed from method to method.
	Character newChar = new Character();

	@Override
	public void start (Stage primaryStage) throws Exception {
		openScreen(primaryStage);
	}
	// Opening screen. Asks if you'd like to make a new character from scratch, or update a previous character.
	private void openScreen(Stage primaryStage) {
		Button btCreateNew = new Button("Create New Character");
		Button btUpdateOld = new Button("Update Previous Character");
		VBox vbButtons = new VBox(20, new Label("What do you want to do?"), new HBox(20, btCreateNew, btUpdateOld));
		vbButtons.setAlignment(Pos.CENTER);
		vbButtons.setPadding(new Insets(20));

		Pane pane = new Pane(vbButtons);

		Scene scene = new Scene(pane);
		primaryStage.setTitle("Pathfinder Character Manager"); 		// Set title
	    primaryStage.setScene(scene); 								// Place the scene in the stage
	    primaryStage.show(); 										// Display the stage

	    btCreateNew.setOnAction(e -> createNew1Of5(primaryStage));
		btUpdateOld.setOnAction(e -> updateOld(primaryStage));
	}

	// The first stage is all about defining who the character is: Name, Gender, Race/Class, and other important statistics.
	private void createNew1Of5(Stage previousStage) {
		// Upon being passed the previous stage, immediately closes.
		previousStage.close();

		// Leftmost elements: Player name, Character name, Gender, Race, Class, Race blurb
		// Player name and character name are vertically displayed.
		TextField tfPlayerName = new TextField();
		TextField tfCharacterName = new TextField();
		HBox hbPlayerName = new HBox(29, new Label ("Player Name"), tfPlayerName);
		HBox hbCharacterName = new HBox(10, new Label ("Character Name"), tfCharacterName);
		VBox vbNames = new VBox(10, hbPlayerName, hbCharacterName);

		// Gender options and label are vertically displayed.
		RadioButton rbMale = new RadioButton("Male");
		rbMale.setUserData("Male");
		RadioButton rbFemale = new RadioButton("Female");
		rbFemale.setUserData("Female");
		ToggleGroup genderGroup = new ToggleGroup();
		rbMale.setToggleGroup(genderGroup);
		rbFemale.setToggleGroup(genderGroup);
		VBox vbGender = new VBox(10, new Label("Gender"), rbMale, rbFemale);

		// Race and Class comboboxes are horizontally displayed. Race consistently precedes Class.
		// This builder is configured to offer strictly the core Races and Classes, for the sake of initial simplicity.
		// These include the Races: Dwarf, Elf, Gnome, Half-Elf, Half-Orc, Halfling, and Human, and
		// the Classes: Barbarian, Bard, Cleric, Druid, Fighter, Monk, Paladin, Ranger, Rogue, Sorcerer, and Wizard.
		ComboBox<String> cbRace = new ComboBox<String>();
			cbRace.getItems().addAll("Dwarf", "Elf", "Gnome", "Half-Elf", "Half-Orc", "Halfling", "Human");
			cbRace.setVisibleRowCount(5);
		ComboBox<String> cbClass = new ComboBox<String>();
			cbClass.getItems().addAll("Barbarian", "Bard", "Cleric", "Druid", "Fighter", "Monk", "Paladin", "Ranger", "Rogue", "Sorcerer", "Wizard");
			cbClass.setVisibleRowCount(5);
		HBox hbRaceClass = new HBox(20, new Label("Race"), cbRace, new Label("Class"), cbClass);

		// Textarea for race.
		Label lRaceText = new Label("About This Race");
		TextArea taRaceText = new TextArea();
		taRaceText.setWrapText(true);
		taRaceText.setPrefSize(200, 200);
		VBox vbRaceText = new VBox(5, lRaceText, taRaceText);
		vbRaceText.setPadding(new Insets(25, 0, 0, 0));

		VBox vbFirst = new VBox(15, vbNames, vbGender, hbRaceClass, vbRaceText);
		vbFirst.setPadding(new Insets(10));
		vbFirst.setMaxSize(500, 500);
		vbFirst.setAlignment(Pos.CENTER);

		// Next column elements: Alignment, Age, Height, Gender, Class blurb
		// The Alignment sliders each default to true neutral, and their default label reflects such.
		Slider law = new Slider(0, 2, 1);
		law.setSnapToTicks(true);
		law.setShowTickMarks(true);
		law.setMajorTickUnit(4);
		HBox hbLaw = new HBox(new Label("Lawful"), law, new Label("Unlawful"));
		Slider moral = new Slider(0, 2, 1);
		moral.setSnapToTicks(true);
		moral.setShowTickMarks(true);
		moral.setMajorTickUnit(4);
		HBox hbMoral = new HBox(new Label("Good"), moral, new Label("Evil"));
		Label lLaw = new Label("\"True");
		Label lMoral = new Label(" Neutral\"");
		law.valueProperty().addListener(new ChangeListener<Object>() {
			@Override
			public void changed(ObservableValue<?> arg0, Object arg1, Object arg2) {
				if (law.getValue() == 0) {
					lLaw.textProperty().setValue("\"Lawful");
				}
				else if (law.getValue() == 1 && moral.getValue() != 1) {
					lLaw.textProperty().setValue("\"Neutral");
				}
				else if (law.getValue() == 2) {
					lLaw.textProperty().setValue("\"Unlawful");
				}
				// All else as expected; if both are == 1, the full label should be "True Neutral".
				else if (law.getValue() == 1 && moral.getValue() == 1) {
					lLaw.textProperty().setValue("\"True");
					lMoral.textProperty().setValue(" Neutral\"");
				}
			}
        });
		moral.valueProperty().addListener(new ChangeListener<Object>() {
			@Override
			public void changed(ObservableValue<?> arg0, Object arg1, Object arg2) {
				// For the cases in which the moral slider is moved from neutral and law is not,
				// law must be reset to "Neutral," rather than "True."
				if (moral.getValue() == 0) {
					lMoral.textProperty().setValue(" Good\"");
					if (law.getValue() == 1) {
						lLaw.textProperty().setValue("\"Neutral");
					}
				}
				else if (moral.getValue() == 1 && law.getValue() != 1) {
					lMoral.textProperty().setValue(" Neutral\"");
				}
				else if (moral.getValue() == 2) {
					lMoral.textProperty().setValue(" Evil\"");
					if (law.getValue() == 1) {
						lLaw.textProperty().setValue("\"Neutral");
					}
				}
				else if (moral.getValue() == 1 && law.getValue() == 1) {
					lLaw.textProperty().setValue("\"True");
					lMoral.textProperty().setValue(" Neutral\"");
				}
			}
		});
		VBox vbAlignment = new VBox(10, new Label("Alignment"), hbLaw, hbMoral, new HBox(lLaw, lMoral));

		// 3 by 3 grid of labels, textfields, and buttons for Age, Height, and Weight.
		GridPane gPhysique = new GridPane();
		gPhysique.setHgap(5);
		gPhysique.setVgap(5);
		Label lAge = new Label("Age");
		Label lHeight = new Label("Height");
		Label lWeight = new Label("Weight");
		TextField tfAge = new TextField();
		TextField tfHeight = new TextField();
		TextField tfWeight = new TextField();
		Button bRollAge = new Button("Roll For Me");
		Button bRollHeight = new Button("Roll For Me");
		Button bRollWeight = new Button("Roll For Me");
		gPhysique.addColumn(0, lAge, lHeight, lWeight);
		gPhysique.addColumn(1, tfAge, tfHeight, tfWeight);
		gPhysique.addColumn(2, bRollAge, bRollHeight, bRollWeight);

		// Textarea for class.
		Label lClassText = new Label("About This Class");
		TextArea taClassText = new TextArea();
		taClassText.setWrapText(true);
		taClassText.setPrefSize(200,200);
		VBox vbClassText = new VBox(5, lClassText, taClassText);

		VBox vbSecond = new VBox(15, vbAlignment, gPhysique, vbClassText);
		vbSecond.setPadding(new Insets(10));
		vbSecond.setMaxSize(500, 500);
		vbSecond.setAlignment(Pos.CENTER);

		// Third column elements: Base Stats, HP, Speed, AC, Initiative...
		GridPane gBaseStats = new GridPane();
		gBaseStats.setHgap(10);
		gBaseStats.setVgap(5);
		gBaseStats.addRow(0, new Label(), new Label(), new Label("Bonus"), new Label("Total"));
		gBaseStats.addColumn(0, new Label("Strength:"), new Label("Dexterity:"), new Label("Constitution:"),
				new Label("Intelligence:"), new Label("Wisdom:"), new Label("Charisma:"));
		TextField tfStr = new TextField("0");
		TextField tfDex = new TextField("0");
		TextField tfCon = new TextField("0");
		TextField tfInt = new TextField("0");
		TextField tfWis = new TextField("0");
		TextField tfCha = new TextField("0");
		gBaseStats.addColumn(1, tfStr, tfDex, tfCon, tfInt, tfWis, tfCha);
		//
		Label lStrBonus = new Label("+ " + newChar.cRace.strBonus);
		Label lDexBonus = new Label("+ " + newChar.cRace.dexBonus);
		Label lConBonus = new Label("+ " + newChar.cRace.conBonus);
		Label lIntBonus = new Label("+ " + newChar.cRace.intBonus);
		Label lWisBonus = new Label("+ " + newChar.cRace.wisBonus);
		Label lChaBonus = new Label("+ " + newChar.cRace.chaBonus);
		gBaseStats.addColumn(2, lStrBonus, lDexBonus, lConBonus, lIntBonus, lWisBonus, lChaBonus);

		//
		// (Integer.parseInt(tfStr.getText()) + newChar.cRace.strBonus).toString
		Label lStrTotal = new Label(String.valueOf(Integer.parseInt(tfStr.getText()) + newChar.cRace.strBonus));
		Label lDexTotal = new Label(String.valueOf(Integer.parseInt(tfDex.getText()) + newChar.cRace.dexBonus));
		Label lConTotal = new Label(String.valueOf(Integer.parseInt(tfCon.getText()) + newChar.cRace.conBonus));
		Label lIntTotal = new Label(String.valueOf(Integer.parseInt(tfInt.getText()) + newChar.cRace.intBonus));
		Label lWisTotal = new Label(String.valueOf(Integer.parseInt(tfWis.getText()) + newChar.cRace.wisBonus));
		Label lChaTotal = new Label(String.valueOf(Integer.parseInt(tfCha.getText()) + newChar.cRace.chaBonus));
		gBaseStats.addColumn(3, lStrTotal, lDexTotal, lConTotal, lIntTotal, lWisTotal, lChaTotal);
		//
		gBaseStats.getColumnConstraints().addAll(new ColumnConstraints(), new ColumnConstraints(40));
		Button bRollBaseStats = new Button("Roll For Me");
		VBox vbBaseStats = new VBox(gBaseStats, bRollBaseStats);
		vbBaseStats.setAlignment(Pos.CENTER);

		VBox vbThird = new VBox(vbBaseStats);
		/// Last column elements: Saving Throws, BAB, CMB, CMD, etc.

		// Bottom Elements: Back and Reset buttons.
		Button bBack = new Button("Back");
		Button bReset = new Button("Reset");
		Button bNext = new Button("Next");
		HBox hbButtons = new HBox(15, bBack, bReset, bNext);
		hbButtons.setAlignment(Pos.CENTER);

		VBox vbBottom = new VBox(15, hbButtons);

		HBox fullhbox = new HBox(vbFirst, vbSecond, vbThird);
		BorderPane borderpane = new BorderPane();
		borderpane.setPadding(new Insets(10));
		borderpane.setTop(fullhbox);
		borderpane.setBottom(vbBottom);

		Scene scene = new Scene(borderpane);
	    Stage currentStage = new Stage();
		currentStage.setTitle("Create New Character: About You"); 	// Set title
	    currentStage.setScene(scene); 								// Place the scene in the stage
	    currentStage.show(); 										// Display the stage

	    bBack.setOnAction(e -> openScreen(currentStage));			// Goes back to the opening stage
	    bReset.setOnAction(e -> createNew1Of5(currentStage));		// Closes and reopens the stage
	    bNext.setOnAction(new EventHandler<ActionEvent>() {			// Records all the information in newChar object and moves on to next method
	    	@Override
	    	public void handle(ActionEvent e) {
	    		newChar.playerName = tfPlayerName.getText();
	    		newChar.characterName = tfPlayerName.getText();
	    		//Gender is already recorded by the radio button action bindings. Does not need to be re-recorded.
	    		//newChar.gender = (String)genderGroup.getSelectedToggle().getUserData();
	    		newChar.cRace.raceName = cbRace.getValue();
	    		newChar.cClass.className = cbClass.getValue();
	    		newChar.alignment = lLaw.getText() + lMoral.getText();
	    		//THERE IS A PROBLEM RIGHT HERE THAT CRASHES MY MOVEMENT TO THE NEXT STAGE
	    		/*
	    		newChar.age = Integer.parseInt(tfAge.getText());
	    		newChar.height = Integer.parseInt(tfHeight.getText());
	    		newChar.weight = Integer.parseInt(tfWeight.getText());
	    		*/
	    		newChar.strength = Integer.parseInt(tfStr.getText());
	    		newChar.dexterity = Integer.parseInt(tfDex.getText());
	    		newChar.constitution = Integer.parseInt(tfCon.getText());
	    		newChar.intelligence = Integer.parseInt(tfInt.getText());
	    		newChar.wisdom = Integer.parseInt(tfWis.getText());
	    		newChar.charisma = Integer.parseInt(tfCha.getText());

	    		try {
					createNew2Of5(currentStage);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
	    	}
	    });
	    rbMale.setOnAction(new EventHandler<ActionEvent>() {
			@Override
	    	public void handle(ActionEvent e) {
				newChar.gender = "Male";
				// Have to reset age, height and weight upon changing gender.
				newChar.age = 0;
				newChar.height = 0;
				newChar.weight = 0;
				tfAge.setText(Integer.toString(newChar.age));
				tfHeight.setText(Integer.toString(newChar.height / 12) + "\'" + Integer.toString(newChar.height % 12) + "\"");
				tfWeight.setText(Integer.toString(newChar.weight));
			}
	    });
	    rbFemale.setOnAction(new EventHandler<ActionEvent>() {
			@Override
	    	public void handle(ActionEvent e) {
				newChar.gender = "Female";
				newChar.age = 0;
				newChar.height = 0;
				newChar.weight = 0;
				tfAge.setText(Integer.toString(newChar.age));
				tfHeight.setText(Integer.toString(newChar.height / 12) + "\'" + Integer.toString(newChar.height % 12) + "\"");
				tfWeight.setText(Integer.toString(newChar.weight));
			}
	    });
	    cbRace.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
	    		if (cbRace.getValue() == "Human") {
	    			newChar.cRace.setRace("Human");
	    			taRaceText.setText(newChar.cRace.getRaceBlurb());
	    			newChar.age = 0;
					newChar.height = 0;
					newChar.weight = 0;
					tfAge.setText(Integer.toString(newChar.age));
					tfHeight.setText(Integer.toString(newChar.height / 12) + "\'" + Integer.toString(newChar.height % 12) + "\"");
					tfWeight.setText(Integer.toString(newChar.weight));
	    		}
	    		else if (cbRace.getValue() == "Elf") {
	    			newChar.cRace.setRace("Elf");
	    			taRaceText.setText(newChar.cRace.getRaceBlurb());
	    			newChar.age = 0;
					newChar.height = 0;
					newChar.weight = 0;
					tfAge.setText(Integer.toString(newChar.age));
					tfHeight.setText(Integer.toString(newChar.height / 12) + "\'" + Integer.toString(newChar.height % 12) + "\"");
					tfWeight.setText(Integer.toString(newChar.weight));
	    		}
	    		else if (cbRace.getValue() == "Dwarf") {
	    			newChar.cRace.setRace("Dwarf");
	    			taRaceText.setText(newChar.cRace.getRaceBlurb());
	    			newChar.age = 0;
					newChar.height = 0;
					newChar.weight = 0;
					tfAge.setText(Integer.toString(newChar.age));
					tfHeight.setText(Integer.toString(newChar.height / 12) + "\'" + Integer.toString(newChar.height % 12) + "\"");
					tfWeight.setText(Integer.toString(newChar.weight));
	    		}
			}
	    });
	    cbClass.setOnAction(new EventHandler<ActionEvent>() {
			@Override
	    	public void handle(ActionEvent e) {
	    		if (cbClass.getValue() == "Barbarian") {
	    			newChar.cClass.setClass("Barbarian");
	    		}
	    		else if (cbClass.getValue() == "Bard") {
	    			newChar.cClass.setClass("Bard");
	    			taClassText.setText(newChar.cClass.getClassBlurb());
	    			newChar.age = 0;
					newChar.height = 0;
					newChar.weight = 0;
					tfAge.setText(Integer.toString(newChar.age));
					tfHeight.setText(Integer.toString(newChar.height / 12) + "\'" + Integer.toString(newChar.height % 12) + "\"");
					tfWeight.setText(Integer.toString(newChar.weight));
	    		}
	    		else if (cbClass.getValue() == "Paladin") {
	    			newChar.cClass.setClass("Paladin");
	    			taClassText.setText(newChar.cClass.getClassBlurb());
	    			newChar.age = 0;
					newChar.height = 0;
					newChar.weight = 0;
					tfAge.setText(Integer.toString(newChar.age));
					tfHeight.setText(Integer.toString(newChar.height / 12) + "\'" + Integer.toString(newChar.height % 12) + "\"");
					tfWeight.setText(Integer.toString(newChar.weight));
	    		}
	    	}
	    });
	    bRollAge.setOnAction(new EventHandler<ActionEvent>() {
			@Override
	    	public void handle(ActionEvent e) {
				newChar.rollAge();
				tfAge.setText(Integer.toString(newChar.age));
			}
	    });
	    bRollHeight.setOnAction(new EventHandler<ActionEvent>() {
			@Override
	    	public void handle(ActionEvent e) {
				newChar.rollHeight();
				tfHeight.setText(Integer.toString(newChar.height / 12) + "\'" + Integer.toString(newChar.height % 12) +"\"");
			}
	    });
	    bRollWeight.setOnAction(new EventHandler<ActionEvent>() {
			@Override
	    	public void handle(ActionEvent e) {
				newChar.rollWeight();
				tfWeight.setText(Integer.toString(newChar.weight));
			}
	    });
	    bRollBaseStats.setOnAction(new EventHandler<ActionEvent>() {
	    	@Override
	    	public void handle (ActionEvent e) {
	    		tfStr.setText(Integer.toString(newChar.rollBaseStats()));
	    		tfDex.setText(Integer.toString(newChar.rollBaseStats()));
	    		tfCon.setText(Integer.toString(newChar.rollBaseStats()));
	    		tfInt.setText(Integer.toString(newChar.rollBaseStats()));
	    		tfWis.setText(Integer.toString(newChar.rollBaseStats()));
	    		tfCha.setText(Integer.toString(newChar.rollBaseStats()));
	    	}
	    });
	}
	//And this concludes the first stage.

	//Second Stage is about Skills, Feats, and Traits.
	private void createNew2Of5(Stage previousStage) throws IOException {
		// Close the previous stage, in preparation for the next.
		previousStage.close();

		// Firstly, the skills list.
		// At the top should be a centered header, "Welcome, [character name], the brave [race][class]!
		// Then there should be a dynamic ticker that keeps track of how many class skills (and feats) the player has left to select.
		String skillsFilePath = "C://Users//Emma//Java Workspaces//Personal//Tabletop RPG Project 2.0//src//excelData//skillsTables.xlsx";
		FileInputStream skillsInputStream = new FileInputStream(new File(skillsFilePath));
		Workbook skillsWB = new XSSFWorkbook(skillsInputStream);
		Sheet classSkillsTable = skillsWB.getSheetAt(0);
		Iterator<Row> skillsIterator = classSkillsTable.iterator();

		VBox vbSkillsCol1 = new VBox(8);
		VBox vbSkillsCol2 = new VBox(8);

		Label lSkills = new Label("Skills List: " + newChar.calcInitialSkillRanks() + " Skills Availible");

		CheckBox cSkill = new CheckBox();
		while (skillsIterator.hasNext()) {
			Row nextRow = skillsIterator.next();
			if (nextRow.getRowNum() > 1) {
				Iterator<Cell> cellIterator = nextRow.cellIterator();
				Cell skillCell = cellIterator.next();
				cSkill = new CheckBox(skillCell.getStringCellValue());
				if (skillCell.getRowIndex() < 20) {
					vbSkillsCol1.getChildren().add(cSkill);
				}
				else {
					vbSkillsCol2.getChildren().add(cSkill);
				}

				Cell isClassSkill = CellUtil.getCell(nextRow, newChar.cClass.skillTableCol);
				if (isClassSkill.getStringCellValue().equals("C")) {
					cSkill.setSelected(true);
					cSkill.setDisable(true);
				}
				else {
					cSkill.setSelected(false);
				}
			}
			else {
				continue;
			}
		}

		try {
			skillsWB.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			skillsInputStream.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Label lFeats = new Label("Feats");
		ComboBox<String> cbFeats = new ComboBox<String>();
		ArrayList<String> featsList = ExcelFileReaders.featReader();
		cbFeats.getItems().addAll(featsList);
		cbFeats.setVisibleRowCount(10);

		HBox hbSkills = new HBox (vbSkillsCol1, vbSkillsCol2);
		VBox vbAllSkills = new VBox(10, lSkills, hbSkills);
		VBox vbFeats = new VBox (10, lFeats, cbFeats);
		HBox hbSkillsAndFeats = new HBox (vbAllSkills, vbFeats);

		Button bBack = new Button("Back");
		Button bReset = new Button("Reset");
		Button bNext = new Button("Next");
		HBox hbButtons = new HBox(15, bBack, bReset, bNext);
		VBox vbAllStage2 = new VBox(hbSkillsAndFeats, hbButtons);
		hbButtons.setAlignment(Pos.CENTER);

		Pane pane = new Pane(vbAllStage2);

		Scene scene = new Scene(pane);
	    Stage currentStage = new Stage();
		currentStage.setTitle("Create New Character: Skills and Feats"); 	// Set title
	    currentStage.setScene(scene); 								// Place the scene in the stage
	    currentStage.show(); 										// Display the stage

	    cSkill.setOnAction(e -> {
	    	if (e.getSource() instanceof CheckBox) {
	    		CheckBox cb = (CheckBox) e.getSource();
	    		if (cb.isSelected()) {
	    			newChar.applySkillRank();
	    		}
	    		else {
	    			newChar.unApplySkillRank();
	    		}
	    	}
	    	lSkills.setText("Skills List: " + newChar.getAvailibleSkillRanks() + " Skills Availible");
	    });
		bBack.setOnAction(e -> createNew1Of5(currentStage));			// Goes back to the opening stage
	    bReset.setOnAction(e -> {										// Resets the current stage
			try {
				createNew2Of5(currentStage);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
	    bNext.setOnAction(new EventHandler<ActionEvent>() {			// Records all the information in newChar object and moves on to next method
	    	@Override
	    	public void handle(ActionEvent e) {
	    		//Record the features needed by this stage.

	    		createNew3Of5(currentStage);
	    	}
	    });

		previousStage.close();
	}

	//Third Stage is for equipment of all kinds.
	private void createNew3Of5(Stage previousStage) {
		previousStage.close();

		Label lEquipCat = new Label("Choose an equipment category:");
		ComboBox<String> cbEquipmentCategories = new ComboBox<String>();
		cbEquipmentCategories.setItems(FXCollections.observableArrayList("Weapons", "Armor", "Shields", "Adventuring Gear", "Clothing",
				"Food, Drink, & Lodging", "Tools & Skill Kits", "Transport, Mounts, & Related Gear", "Spellcasting & Services", "Special Substances & Items"));

		ListView<String> lvEquipment = new ListView<String>();

		Label lItemText = new Label("About This Item");
		Label lWallet = new Label("Current Funds: " + newChar.getGP() + " gp");
		Label lCarryCap = new Label("Current Carrying Capacity: " + newChar.getCarryCap() + " lbs");
		HBox hbCheckLabels = new HBox(10, lWallet, lCarryCap);
		VBox vbItemLabels = new VBox(lItemText, hbCheckLabels);
		TextArea taItemText = new TextArea();
		taItemText.setWrapText(true);
		taItemText.setPrefSize(200,200);
		Button bPack = new Button("Pack");
		Button bUnpack = new Button("Unpack");
		VBox packAndUnpack = new VBox(vbItemLabels, taItemText, bPack, bUnpack);

		HBox hbEquip = new HBox(cbEquipmentCategories, lvEquipment, packAndUnpack);

		Button bBack = new Button("Back");
		Button bReset = new Button("Reset");
		Button bNext = new Button("Next");
		HBox hbButtons = new HBox(15, bBack, bReset, bNext);
		VBox vbAllStage2 = new VBox(hbEquip, hbButtons);
		hbButtons.setAlignment(Pos.CENTER);

		Pane pane = new Pane(vbAllStage2);

		Scene scene = new Scene(pane);
	    Stage currentStage = new Stage();
		currentStage.setTitle("Create New Character: Equipment"); 	// Set title
	    currentStage.setScene(scene); 								// Place the scene in the stage
	    currentStage.show(); 										// Display the stage

		bBack.setOnAction(e -> {
			try {
				createNew2Of5(currentStage);
			} catch (IOException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
		});			// Goes back to the opening stage
	    bReset.setOnAction(e -> {										// Resets the current stage
			createNew3Of5(currentStage);
		});
	    bNext.setOnAction(new EventHandler<ActionEvent>() {			// Records all the information in newChar object and moves on to next method
	    	@Override
	    	public void handle(ActionEvent e) {
	    		//Record the features needed by this stage.

	    		createNew4Of5(currentStage);
	    	}
	    });

	    cbEquipmentCategories.setOnAction(e -> {
	    	try {
				ObservableList<String> equipmentList = ExcelFileReaders.equipmentReader(cbEquipmentCategories.getSelectionModel().getSelectedIndex());
				lvEquipment.setItems(equipmentList);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
	    });

	    lvEquipment.setOnMouseClicked(e -> {
	    	try {
	    		int sheetIndex = cbEquipmentCategories.getSelectionModel().getSelectedIndex();
	    		int lvSelectionIndex = lvEquipment.getSelectionModel().getSelectedIndex();
	    		if (sheetIndex == 0) {
	    			taItemText.setText("I don't have the data reader for Weapons availible yet!");
	    		}
	    		else if (sheetIndex == 1) {
	    			String itemData = ExcelFileReaders.equipmentArmorInfoReader(sheetIndex, lvSelectionIndex);
	    			taItemText.setText(itemData);
	    		}
	    		else if (sheetIndex == 2) {
	    			taItemText.setText("I don't have the data reader for Shields availible yet!");
	    		}
	    		else {
	    			String itemData = ExcelFileReaders.equipmentGeneralInfoReader(sheetIndex, lvSelectionIndex);
	    			taItemText.setText(itemData);
	    		}
	    		//
	    	}
	    	catch (IOException e2) {
	    		// TODO Auto-generated catch block
	    		e2.printStackTrace();
	    	}
	    });

	    bPack.setOnAction(e -> {
	    	int sheetIndex = cbEquipmentCategories.getSelectionModel().getSelectedIndex();
    		int lvSelectionIndex = lvEquipment.getSelectionModel().getSelectedIndex();
	    	try {
	    		newChar.packItem(sheetIndex, lvSelectionIndex);
	    		lWallet.setText("Current Funds: " + newChar.getGP() + " gp");
	    		lCarryCap.setText("Current Carrying Capacity: " + newChar.getCarryCap() + " lbs");
	    	}
	    	catch (IOException e1) {
	    		e1.printStackTrace();
	    	}
	    });
	}

	//Fourth Stage is for magic and other class powers (which could be skipped if irrelevant).
	private void createNew4Of5(Stage previousStage) {
		previousStage.close();
	}

	private void updateOld(Stage previousStage) {
		previousStage.close();
		//Decide what to do next.
	}
	public static void main(String[] args) {
		launch(args);
	}
}
