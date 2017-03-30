package application.models;

import java.io.Serializable;

public class Equipment extends AbstractItem implements Serializable
{
    protected int	 power;
    protected int	 equipCode;
    protected int	 discount;

    protected static int baseOffset;
    protected static int bytesPerEquipment;
    protected static int listSize;

    public Equipment(int index)
    {
	super(index);
    }

    // public static <T extends Equipment> void addUsefulModels(List<T> listTo, List<T> listFrom, int equipCode)
    // {
    // for (T model : listFrom)
    // {
    // if ((equipCode & model.getEquipCode()) > 0)
    // {
    // if (MainMenu.getShowEmptyValues() == true || !model.getName().isEmpty())
    // {
    // listTo.add(model);
    // }
    // }
    // }
    // resetListIndexes(listTo);
    // }

    public int getPower()
    {
	return power;
    }

    public int getEquipCode()
    {
	return equipCode;
    }

    public int getDiscount()
    {
	return discount;
    }

    public void setPower(int power)
    {
	this.power = power;
    }

    public void setEquipCode(int equipCode)
    {
	this.equipCode = equipCode;
    }

    public void setUnknown(int unknown)
    {
	this.discount = unknown;
    }

    public Equipment(Equipment e, int listIndex)
    {
	super(e, listIndex);
	this.power = e.power;
	this.cost = e.cost;
	this.equipCode = e.equipCode;
	this.discount = e.discount;
    }
}
