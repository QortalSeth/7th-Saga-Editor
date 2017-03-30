package application.models.lists;

import java.io.Serializable;
import java.util.List;

import application.controllers.MainMenu;
import application.models.Armor;

public class Armors extends Equipment<Armor> implements Serializable
{
    private static final int armorListSize = 105;

    public Armors()
    {
	super();
    }

    public Armors(Armors addFrom, boolean addEmptyModel)
    {
	super(addFrom);
	if (addEmptyModel)
	{
	    this.addEmptyModel(models);
	}
    }

    public Armors(Armors addFrom, int equipCode)
    {
	super(addFrom, equipCode);
    }

    public Armors(List<Integer> gameIndexes)
    {
	super(gameIndexes);
    }

    public Armors(DModelsList<Armor> DmodelsList)
    {
	super(DmodelsList);
    }

    public Armors(Armors addFrom)
    {
	super(addFrom);
    }

    @Override
    public void initializeModel(List<Armor> models)
    {
	for (int i = 0; i < armorListSize; i++)
	{
	    Armor a = new Armor(i);
	    a.getValuesFromROM();
	    models.add(a);
	}
    }

    @Override
    public void saveModels()
    {
	for (Armor a : models)
	    a.writeValuesToROM();
    }

    // public static void addUsefulArmors(List<Armor> list, int equipCode)
    // {
    // for (Armor armor : Armor.getModels())
    // {
    // if ((equipCode & armor.getEquipCode()) > 0)
    // {
    // if (MainMenu.getShowEmptyValues() == true || !armor.getName().isEmpty())
    // {
    // list.add(armor);
    // }
    // }
    // }
    // resetListIndexes(list);
    // }

    public void addUsefulBodyArmors(Armors armors, int equipCode)
    {
	for (Armor a : armors.getModels())
	{
	    if (a.isBodyArmor() && (equipCode & a.getEquipCode()) > 0)
	    {
		if (MainMenu.getShowEmptyValues() == true || !a.getName().isEmpty())
		{
		    models.add(a);
		}
	    }
	}

	Dmodels.addAll(armors.getDModels());
    }

    public void addUsefulAccessories(Armors armors, int equipCode)
    {
	for (Armor a : armors.getModels())
	{
	    if (a.isAccessory() && (equipCode & a.getEquipCode()) > 0)
	    {
		if (MainMenu.getShowEmptyValues() == true || !a.getName().isEmpty())
		{
		    models.add(a);
		}
	    }
	}

	Dmodels.addAll(armors.getDModels());
    }

    @Override
    public void addEmptyModel(List<Armor> models)
    {
	Armor emptyArmor = new Armor(0);
	emptyArmor.setGameIndex(0);
	emptyArmor.setItemCode(0);
	emptyArmor.setName("");
	emptyArmor.setEquipCode(0);
	models.add(0, emptyArmor);
    }

}
