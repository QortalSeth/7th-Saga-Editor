package models;

import java.io.Serializable;

public class AbstractItem extends Model implements Serializable
{

    protected int itemCode;
    protected int cost;

    public AbstractItem(int index)
    {
	super(index);
    }

    public int getCost()
    {
	return cost;
    }

    public void setCost(int cost)
    {
	this.cost = cost;
    }

    public int getItemCode()
    {
	return itemCode;
    }

    public void setItemCode(int itemCode)
    {
	this.itemCode = itemCode;
    }

    public AbstractItem(AbstractItem i, int listIndex)
    {
	this.name = i.name;
	this.gameIndex = i.gameIndex;
	this.itemCode = i.itemCode;
	this.cost = i.cost;
	this.namePointer = i.namePointer;
    }

}