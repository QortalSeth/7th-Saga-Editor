package application.models.lists;

import java.io.Serializable;
import java.util.List;

import application.models.Weapon;

public class Weapons extends Equipment<Weapon> implements Serializable
{

    private static int weaponListSize = 51;

    public Weapons()
    {
	super();
    }

    public Weapons(Weapons addFrom, boolean addEmptyModel)
    {
	super(addFrom);
	if (addEmptyModel)
	{
	    this.addEmptyModel(models);
	}
    }

    public Weapons(Weapons addFrom, int equipCode)
    {
	super(addFrom, equipCode);
    }

    public Weapons(Weapons addFrom)
    {
	super(addFrom);
    }

    public Weapons(List<Integer> gameIndexes)
    {
	super(gameIndexes);
    }

    public Weapons(DModelsList<Weapon> DmodelsList)
    {
	super(DmodelsList);
    }

    @Override
    public void initializeModel(List<Weapon> models)
    {
	for (int i = 0; i < weaponListSize; i++)
	{
	    Weapon w = new Weapon(i);
	    w.getValuesFromROM();
	    models.add(w);
	}
    }

    @Override
    public void saveModels()
    {
	for (Weapon w : models)
	    w.writeValuesToROM();
    }

    @Override
    public void addEmptyModel(List<Weapon> models)
    {
	Weapon emptyWeapon = new Weapon(0);
	emptyWeapon.setGameIndex(0);
	emptyWeapon.setItemCode(0);
	emptyWeapon.setName("");
	emptyWeapon.setPower(0);
	emptyWeapon.setEquipCode(255);
	models.add(0, emptyWeapon);
    }
}
