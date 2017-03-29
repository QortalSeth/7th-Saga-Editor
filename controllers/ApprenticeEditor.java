package controllers;

import java.util.ArrayList;
import java.util.List;

import application.ControllerInitilizer;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import models.Apprentice;
import models.Armor;
import models.EquipmentAtLevel;
import models.Weapon;
import models.lists.Apprentices;
import models.lists.Armors;
import models.lists.Lists;
import models.lists.Weapons;
import staticClasses.Listeners;
import staticClasses.TextReader;

public class ApprenticeEditor implements ControllerInitilizer
{

	private TextField[][]	   dialogueTs	 = new TextField[7][6];
	private TextField[][]	   DDialogueTs	 = new TextField[7][6];
	private TextField[]		   levelTs	 = new TextField[10];
	private List<ComboBox<Weapon>> weaponsC	 = new ArrayList<ComboBox<Weapon>>();
	private List<ComboBox<Armor>>  armorsC	 = new ArrayList<ComboBox<Armor>>();
	private List<ComboBox<Armor>>  accessoriesC	 = new ArrayList<ComboBox<Armor>>();

	private TextField[]		   DlevelsT	 = new TextField[10];
	private TextField[]		   DweaponsT	 = new TextField[10];
	private TextField[]		   DarmorsT	 = new TextField[10];
	private TextField[]		   DaccessoriesT = new TextField[10];
	private Weapons		   weapons;
	private Armors		   armors;
	private Armors		   accessories;
	private static int		   startApprentice;
	private Apprentice		   previouslySelectedApprentice;
	private Apprentices		   apprentices;
	private static int selectedTextFieldIndex = Integer.MAX_VALUE;

	@FXML
	void initialize()
	{
		apprentices = new Apprentices();
		apprentices = Lists.getApprentices();
		apprenticesC.setItems(apprentices.getModels());

		weapons = new Weapons();
		armors = new Armors();
		accessories = new Armors();

		DgoldPerLevelT.setText(Integer.toString(apprentices.getDgoldPerLevel()));
		goldPerLevelT.setText(Integer.toString(apprentices.getGoldPerLevel()));
		dialogueTs[0][0] = a2kOJ;
		dialogueTs[0][1] = a2kBJ;
		dialogueTs[0][2] = a2kNO;
		dialogueTs[0][3] = a2kAS;
		dialogueTs[0][4] = a2kOF;
		dialogueTs[0][5] = a2kAA;

		dialogueTs[1][0] = a2oOJ;
		dialogueTs[1][1] = a2oBJ;
		dialogueTs[1][2] = a2oNO;
		dialogueTs[1][3] = a2oAS;
		dialogueTs[1][4] = a2oOF;
		dialogueTs[1][5] = a2oAA;

		dialogueTs[2][0] = a2eOJ;
		dialogueTs[2][1] = a2eBJ;
		dialogueTs[2][2] = a2eNO;
		dialogueTs[2][3] = a2eAS;
		dialogueTs[2][4] = a2eOF;
		dialogueTs[2][5] = a2eAA;

		dialogueTs[3][0] = a2wOJ;
		dialogueTs[3][1] = a2wBJ;
		dialogueTs[3][2] = a2wNO;
		dialogueTs[3][3] = a2wAS;
		dialogueTs[3][4] = a2wOF;
		dialogueTs[3][5] = a2wAA;

		dialogueTs[4][0] = a2xOJ;
		dialogueTs[4][1] = a2xBJ;
		dialogueTs[4][2] = a2xNO;
		dialogueTs[4][3] = a2xAS;
		dialogueTs[4][4] = a2xOF;
		dialogueTs[4][5] = a2xAA;

		dialogueTs[5][0] = a2vOJ;
		dialogueTs[5][1] = a2vBJ;
		dialogueTs[5][2] = a2vNO;
		dialogueTs[5][3] = a2vAS;
		dialogueTs[5][4] = a2vOF;
		dialogueTs[5][5] = a2vAA;

		dialogueTs[6][0] = a2lOJ;
		dialogueTs[6][1] = a2lBJ;
		dialogueTs[6][2] = a2lNO;
		dialogueTs[6][3] = a2lAS;
		dialogueTs[6][4] = a2lOF;
		dialogueTs[6][5] = a2lAA;

		DDialogueTs[0][0] = Da2kOJ;
		DDialogueTs[0][1] = Da2kBJ;
		DDialogueTs[0][2] = Da2kNO;
		DDialogueTs[0][3] = Da2kAS;
		DDialogueTs[0][4] = Da2kOF;
		DDialogueTs[0][5] = Da2kAA;

		DDialogueTs[1][0] = Da2oOJ;
		DDialogueTs[1][1] = Da2oBJ;
		DDialogueTs[1][2] = Da2oNO;
		DDialogueTs[1][3] = Da2oAS;
		DDialogueTs[1][4] = Da2oOF;
		DDialogueTs[1][5] = Da2oAA;

		DDialogueTs[2][0] = Da2eOJ;
		DDialogueTs[2][1] = Da2eBJ;
		DDialogueTs[2][2] = Da2eNO;
		DDialogueTs[2][3] = Da2eAS;
		DDialogueTs[2][4] = Da2eOF;
		DDialogueTs[2][5] = Da2eAA;

		DDialogueTs[3][0] = Da2wOJ;
		DDialogueTs[3][1] = Da2wBJ;
		DDialogueTs[3][2] = Da2wNO;
		DDialogueTs[3][3] = Da2wAS;
		DDialogueTs[3][4] = Da2wOF;
		DDialogueTs[3][5] = Da2wAA;

		DDialogueTs[4][0] = Da2xOJ;
		DDialogueTs[4][1] = Da2xBJ;
		DDialogueTs[4][2] = Da2xNO;
		DDialogueTs[4][3] = Da2xAS;
		DDialogueTs[4][4] = Da2xOF;
		DDialogueTs[4][5] = Da2xAA;

		DDialogueTs[5][0] = Da2vOJ;
		DDialogueTs[5][1] = Da2vBJ;
		DDialogueTs[5][2] = Da2vNO;
		DDialogueTs[5][3] = Da2vAS;
		DDialogueTs[5][4] = Da2vOF;
		DDialogueTs[5][5] = Da2vAA;

		DDialogueTs[6][0] = Da2lOJ;
		DDialogueTs[6][1] = Da2lBJ;
		DDialogueTs[6][2] = Da2lNO;
		DDialogueTs[6][3] = Da2lAS;
		DDialogueTs[6][4] = Da2lOF;
		DDialogueTs[6][5] = Da2lAA;

		ImageView i = (ImageView) apprenticeUp.getGraphic();
		i.setImage(new Image(this.getClass().getResourceAsStream("images/Triangle.png")));
		i = (ImageView) apprenticeDown.getGraphic();
		i.setImage(new Image(this.getClass().getResourceAsStream("images/Triangle.png")));

		goldPerLevelT.focusedProperty().addListener(Listeners.textFieldFocusListener(999));
		for (TextField[] ta : dialogueTs)
		{
			for (TextField t : ta)
			{
				t.focusedProperty().addListener(Listeners.textFieldFocusListener(100));
				t.focusedProperty().addListener(new ChangeListener()
				{
					@Override
					public void changed(ObservableValue observable, Object oldValue, Object newValue)
					{
						ReadOnlyBooleanProperty a = (ReadOnlyBooleanProperty) observable;
						TextField t = (TextField) a.getBean();
						Apprentice apprentice = apprenticesC.getSelectionModel().getSelectedItem();
						for (int i = 0; i < dialogueTs.length; i++)
						{
							for (int k = 0; k < dialogueTs[i].length; k++)
							{
								if (dialogueTs[i][k] == t)
								{
									dialogueDisplay.setText(TextReader.readText(apprentice.getDialoguePointers()[k], MainMenu.getBytes()));
									selectedTextFieldIndex = k;
								}
							}
						}
					}
				});
			}
		}

		levelTs[0] = level1T;
		levelTs[1] = level2T;
		levelTs[2] = level3T;
		levelTs[3] = level4T;
		levelTs[4] = level5T;
		levelTs[5] = level6T;
		levelTs[6] = level7T;
		levelTs[7] = level8T;
		levelTs[8] = level9T;
		levelTs[9] = level10T;

		weaponsC.add(weapon1);
		weaponsC.add(weapon2);
		weaponsC.add(weapon3);
		weaponsC.add(weapon4);
		weaponsC.add(weapon5);
		weaponsC.add(weapon6);
		weaponsC.add(weapon7);
		weaponsC.add(weapon8);
		weaponsC.add(weapon9);
		weaponsC.add(weapon10);

		armorsC.add(armor1);
		armorsC.add(armor2);
		armorsC.add(armor3);
		armorsC.add(armor4);
		armorsC.add(armor5);
		armorsC.add(armor6);
		armorsC.add(armor7);
		armorsC.add(armor8);
		armorsC.add(armor9);
		armorsC.add(armor10);

		accessoriesC.add(accessory1);
		accessoriesC.add(accessory2);
		accessoriesC.add(accessory3);
		accessoriesC.add(accessory4);
		accessoriesC.add(accessory5);
		accessoriesC.add(accessory6);
		accessoriesC.add(accessory7);
		accessoriesC.add(accessory8);
		accessoriesC.add(accessory9);
		accessoriesC.add(accessory10);

		for (ComboBox<Weapon> comboBox : weaponsC)
		{
			comboBox.setItems(weapons.getModels());
		}

		for (ComboBox<Armor> comboBox : armorsC)
		{
			comboBox.setItems(armors.getModels());
		}

		for (ComboBox<Armor> comboBox : accessoriesC)
		{
			comboBox.setItems(accessories.getModels());
		}

		DlevelsT[0] = Dlevel1;
		DlevelsT[1] = Dlevel2;
		DlevelsT[2] = Dlevel3;
		DlevelsT[3] = Dlevel4;
		DlevelsT[4] = Dlevel5;
		DlevelsT[5] = Dlevel6;
		DlevelsT[6] = Dlevel7;
		DlevelsT[7] = Dlevel8;
		DlevelsT[8] = Dlevel9;
		DlevelsT[9] = Dlevel10;

		DweaponsT[0] = Dweapon1;
		DweaponsT[1] = Dweapon2;
		DweaponsT[2] = Dweapon3;
		DweaponsT[3] = Dweapon4;
		DweaponsT[4] = Dweapon5;
		DweaponsT[5] = Dweapon6;
		DweaponsT[6] = Dweapon7;
		DweaponsT[7] = Dweapon8;
		DweaponsT[8] = Dweapon9;
		DweaponsT[9] = Dweapon10;

		DarmorsT[0] = Darmor1;
		DarmorsT[1] = Darmor2;
		DarmorsT[2] = Darmor3;
		DarmorsT[3] = Darmor4;
		DarmorsT[4] = Darmor5;
		DarmorsT[5] = Darmor6;
		DarmorsT[6] = Darmor7;
		DarmorsT[7] = Darmor8;
		DarmorsT[8] = Darmor9;
		DarmorsT[9] = Darmor10;

		DaccessoriesT[0] = Daccessory1;
		DaccessoriesT[1] = Daccessory2;
		DaccessoriesT[2] = Daccessory3;
		DaccessoriesT[3] = Daccessory4;
		DaccessoriesT[4] = Daccessory5;
		DaccessoriesT[5] = Daccessory6;
		DaccessoriesT[6] = Daccessory7;
		DaccessoriesT[7] = Daccessory8;
		DaccessoriesT[8] = Daccessory9;
		DaccessoriesT[9] = Daccessory10;

		apprenticesC.getSelectionModel().select(startApprentice);
		this.setSelectedApprentice();
	}

	@FXML
	public void setSelectedApprentice()
	{
		Apprentice apprentice = apprenticesC.getSelectionModel().getSelectedItem();
		Apprentice Dapprentice = apprentices.getDModel(apprentice);
		this.saveData();
		previouslySelectedApprentice = apprentice;

		for (int i = 0; i < dialogueTs.length; i++)
		{
			for (int k = 0; k < dialogueTs[i].length; k++)
			{
				dialogueTs[i][k].setText(Integer.toString(apprentice.getDialogues()[i][k]));
				;
			}
		}

		for (int i = 0; i < DDialogueTs.length; i++)
		{
			for (int k = 0; k < DDialogueTs[i].length; k++)
			{
				DDialogueTs[i][k].setText(Integer.toString(Dapprentice.getDialogues()[i][k]));
				;
			}
		}

		// reset data structures to have equipment that can only be equipped by
		// currently selected apprentice
		weapons.clear();
		armors.clear();
		accessories.clear();
		weapons.addUsefulModels(Lists.getWeapons(), apprentice.getEquipCode());
		armors.addUsefulBodyArmors(Lists.getArmors(), apprentice.getEquipCode());
		accessories.addUsefulAccessories(Lists.getArmors(), apprentice.getEquipCode());

		weapons.sortByAscendingPower();
		armors.sortByAscendingPower();
		accessories.sortByAscendingPower();

		int index = 0;
		for (EquipmentAtLevel entry : apprentice.getEquipmentAtLevels())
		{
			levelTs[index].setText(Integer.toString(entry.getLevel()));

			weaponsC.get(index).getSelectionModel().select(weapons.getIndex(entry.getEquipment()[0]));
			armorsC.get(index).getSelectionModel().select(armors.getIndex(entry.getEquipment()[1]));
			accessoriesC.get(index).getSelectionModel().select(accessories.getIndex(entry.getEquipment()[2]));
			index++;

		}

		index = 0;
		for (EquipmentAtLevel entry : Dapprentice.getEquipmentAtLevels())
		{
			DlevelsT[index].setText(Integer.toString(entry.getLevel()));

			DweaponsT[index].setText(weapons.getDName(entry.getEquipment()[0]));
			DarmorsT[index].setText(armors.getDName(entry.getEquipment()[1]));
			DaccessoriesT[index].setText(accessories.getDName(entry.getEquipment()[2]));
			index++;
		}
		if(selectedTextFieldIndex < Integer.MAX_VALUE)
		{dialogueDisplay.setText(TextReader.readText(apprentice.getDialoguePointers()[selectedTextFieldIndex], MainMenu.getBytes()));}
	}

	@FXML
	void characterIncrement()
	{
		apprenticesC.getSelectionModel().selectNext();
	}

	@FXML
	void characterDecrement()
	{
		apprenticesC.getSelectionModel().selectPrevious();
	}

	@FXML
	void removeNonNumbers(KeyEvent event)
	{
		Listeners.removeNonNumbers(event, null);
	}

	@Override
	public void saveData()
	{
		Apprentice apprentice = previouslySelectedApprentice;
		if (apprentice == null) { return; }

		for (int i = 0; i < dialogueTs.length; i++)
		{
			for (int k = 0; k < dialogueTs[i].length; k++)
			{
				apprentice.getDialogues()[i][k] = Integer.parseInt(dialogueTs[i][k].getText());
			}
		}
		List<EquipmentAtLevel> equipmentAtLevel = new ArrayList<EquipmentAtLevel>();
		for (int i = 0; i < 10; i++)
		{
			equipmentAtLevel.add(new EquipmentAtLevel(Integer.parseInt(levelTs[i].getText()), new int[]
					{
							weaponsC.get(i).getSelectionModel().getSelectedItem().getItemCode(), armorsC.get(i).getSelectionModel().getSelectedItem().getItemCode(),
							accessoriesC.get(i).getSelectionModel().getSelectedItem().getItemCode()
					}));
		}
		apprentice.getEquipmentAtLevels().clear();
		apprentice.setEquipmentAtLevels(equipmentAtLevel);
		apprentices.setGoldPerLevel(Integer.parseInt(goldPerLevelT.getText()));
	}

	@FXML
	private TextField		 a2eOJ;
	@FXML
	private TextField		 Da2vOF;
	@FXML
	private TextField		 a2wBJ;
	@FXML
	private TextField		 Da2oAA;
	@FXML
	private TextField		 a2eOF;
	@FXML
	private TextField		 Da2vOJ;
	@FXML
	private TextField		 a2oBJ;
	@FXML
	private TextField		 level4T;
	@FXML
	private TextField		 a2kBJ;
	@FXML
	private TextField		 Da2kAA;
	@FXML
	private TextField		 Da2xBJ;
	@FXML
	private TextField		 Da2vNO;
	@FXML
	private TextField		 a2kAS;
	@FXML
	private TextField		 Da2lBJ;
	@FXML
	private ComboBox<Armor>	 armor7;
	@FXML
	private ComboBox<Armor>	 armor6;
	@FXML
	private TextField		 a2wAA;
	@FXML
	private ComboBox<Armor>	 armor5;
	@FXML
	private ComboBox<Armor>	 armor4;
	@FXML
	private ComboBox<Armor>	 armor3;
	@FXML
	private ComboBox<Armor>	 armor2;
	@FXML
	private ComboBox<Armor>	 armor8;
	@FXML
	private ComboBox<Armor>	 armor9;
	@FXML
	private ComboBox<Armor>	 armor10;
	@FXML
	private TextField		 Da2xAS;
	@FXML
	private TextField		 level3T;
	@FXML
	private TextField		 level8T;
	@FXML
	private TextField		 level9T;
	@FXML
	private TextField		 level10T;
	@FXML
	private ComboBox<Armor>	 armor1;
	@FXML
	private TextField		 a2vOJ;
	@FXML
	private TextField		 Da2lAS;
	@FXML
	private TextField		 a2wAS;
	@FXML
	private TextField		 a2eNO;
	@FXML
	private TextField		 Da2xAA;
	@FXML
	private TextField		 a2oAS;
	@FXML
	private TextField		 a2xBJ;
	@FXML
	private TextField		 Da2wOF;
	@FXML
	private TextField		 Da2wOJ;
	@FXML
	private TextField		 Da2kOJ;
	@FXML
	private TextField		 Da2oOF;
	@FXML
	private TextField		 a2vNO;
	@FXML
	private TextField		 level6T;
	@FXML
	private TextField		 a2lBJ;
	@FXML
	private TextField		 Da2oOJ;
	@FXML
	private TextField		 Da2lAA;
	@FXML
	private TextField		 a2oAA;
	@FXML
	private TextField		 a2vOF;
	@FXML
	private TextField		 a2kAA;
	@FXML
	private TextField		 Da2kOF;
	@FXML
	private TextField		 Da2wNO;
	@FXML
	private TextField		 Da2eBJ;
	@FXML
	private TextField		 a2lAS;
	@FXML
	private TextField		 Da2oNO;
	@FXML
	private TextField		 a2xAA;
	@FXML
	private TextField		 level5T;
	@FXML
	private TextField		 a2wOJ;
	@FXML
	private TextField		 Da2kNO;
	@FXML
	private ComboBox<Weapon>	 weapon6;
	@FXML
	private ComboBox<Weapon>	 weapon7;
	@FXML
	private ComboBox<Weapon>	 weapon4;
	@FXML
	private ComboBox<Weapon>	 weapon5;
	@FXML
	private TextField		 a2xAS;
	@FXML
	private TextField		 Da2eAS;
	@FXML
	private ComboBox<Weapon>	 weapon2;
	@FXML
	private ComboBox<Weapon>	 weapon3;
	@FXML
	private ComboBox<Weapon>	 weapon1;
	@FXML
	private ComboBox<Weapon>	 weapon8;
	@FXML
	private ComboBox<Weapon>	 weapon9;
	@FXML
	private ComboBox<Weapon>	 weapon10;
	@FXML
	private TextField		 Da2xOF;
	@FXML
	private TextField		 Da2xOJ;
	@FXML
	private TextField		 Da2eAA;
	@FXML
	private TextField		 a2wNO;
	@FXML
	private TextField		 Da2lOJ;
	@FXML
	private TextField		 Da2vBJ;
	@FXML
	private TextField		 a2wOF;
	@FXML
	private TextField		 a2lAA;
	@FXML
	private TextField		 a2oOJ;
	@FXML
	private TextField		 Da2lOF;
	@FXML
	private ComboBox<Armor>	 accessory4;
	@FXML
	private ComboBox<Armor>	 accessory5;
	@FXML
	private TextField		 a2kOJ;
	@FXML
	private TextField		 a2oOF;
	@FXML
	private ComboBox<Armor>	 accessory2;
	@FXML
	private ComboBox<Armor>	 accessory3;
	@FXML
	private TextField		 Da2xNO;
	@FXML
	private TextField		 a2kOF;
	@FXML
	private ComboBox<Armor>	 accessory6;
	@FXML
	private ComboBox<Armor>	 accessory7;
	@FXML
	private ComboBox<Armor>	 accessory8;
	@FXML
	private ComboBox<Armor>	 accessory9;
	@FXML
	private ComboBox<Armor>	 accessory10;
	@FXML
	private TextField		 Da2lNO;
	@FXML
	private ComboBox<Armor>	 accessory1;
	@FXML
	private TextField		 a2eAS;
	@FXML
	private TextField		 level7T;
	@FXML
	private TextField		 Da2vAS;
	@FXML
	private TextField		 a2xOJ;
	@FXML
	private TextField		 a2oNO;
	@FXML
	private TextField		 a2eBJ;
	@FXML
	private TextField		 a2kNO;
	@FXML
	private TextField		 Da2vAA;
	@FXML
	private TextField		 a2vBJ;
	@FXML
	private ComboBox<Apprentice> apprenticesC;
	@FXML
	private TextField		 a2xNO;
	@FXML
	private TextField		 level2T;
	@FXML
	private TextField		 a2xOF;
	@FXML
	private TextField		 Da2wBJ;
	@FXML
	private TextField		 Da2eOJ;
	@FXML
	private TextField		 a2lOJ;
	@FXML
	private TextField		 Da2oBJ;
	@FXML
	private TextField		 a2eAA;
	@FXML
	private TextField		 a2lOF;
	@FXML
	private TextField		 Da2eOF;
	@FXML
	private TextField		 Da2kBJ;
	@FXML
	private TextField		 Da2eNO;
	@FXML
	private TextField		 a2vAA;
	@FXML
	private TextField		 Da2wAS;
	@FXML
	private TextField		 level1T;
	@FXML
	private TextField		 Da2kAS;
	@FXML
	private TextField		 a2lNO;
	@FXML
	private TextField		 Da2oAS;
	@FXML
	private TextField		 a2vAS;
	@FXML
	private TextField		 Da2wAA;
	@FXML
	private TextArea		 dialogueDisplay;
	@FXML
	private TextField		 Dlevel1;
	@FXML
	private TextField		 Dlevel2;
	@FXML
	private TextField		 Dlevel3;
	@FXML
	private TextField		 Dlevel4;
	@FXML
	private TextField		 Dlevel5;
	@FXML
	private TextField		 Dlevel6;
	@FXML
	private TextField		 Dlevel7;
	@FXML
	private TextField		 Dlevel8;
	@FXML
	private TextField		 Dlevel9;
	@FXML
	private TextField		 Dlevel10;
	@FXML
	private TextField		 Dweapon1;
	@FXML
	private TextField		 Dweapon2;
	@FXML
	private TextField		 Dweapon3;
	@FXML
	private TextField		 Dweapon4;
	@FXML
	private TextField		 Dweapon5;
	@FXML
	private TextField		 Dweapon6;
	@FXML
	private TextField		 Dweapon7;
	@FXML
	private TextField		 Dweapon8;
	@FXML
	private TextField		 Dweapon9;
	@FXML
	private TextField		 Dweapon10;
	@FXML
	private TextField		 Darmor1;
	@FXML
	private TextField		 Darmor2;
	@FXML
	private TextField		 Darmor3;
	@FXML
	private TextField		 Darmor4;
	@FXML
	private TextField		 Darmor5;
	@FXML
	private TextField		 Darmor6;
	@FXML
	private TextField		 Darmor7;
	@FXML
	private TextField		 Darmor8;
	@FXML
	private TextField		 Darmor9;
	@FXML
	private TextField		 Darmor10;
	@FXML
	private TextField		 Daccessory1;
	@FXML
	private TextField		 Daccessory2;
	@FXML
	private TextField		 Daccessory3;
	@FXML
	private TextField		 Daccessory4;
	@FXML
	private TextField		 Daccessory5;
	@FXML
	private TextField		 Daccessory6;
	@FXML
	private TextField		 Daccessory7;
	@FXML
	private TextField		 Daccessory8;
	@FXML
	private TextField		 Daccessory9;
	@FXML
	private TextField		 Daccessory10;
	@FXML
	private TextField		 goldPerLevelT;
	@FXML
	private TextField		 DgoldPerLevelT;
	@FXML
	private Button		 apprenticeUp;
	@FXML
	private Button		 apprenticeDown;

	@Override
	public void saveState()
	{
		startApprentice = apprenticesC.getSelectionModel().getSelectedIndex();
	}
}
