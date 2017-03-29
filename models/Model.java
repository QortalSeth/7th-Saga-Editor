package models;

import java.io.Serializable;

public abstract class Model implements Serializable
{
    protected String name;
    protected int    gameIndex;
    protected int    namePointer;
    protected int    chronologicalIndex;

    public Model(int index)
    {
	gameIndex = index;
    }

    public Model()
    {
    }

    public Model(Model m)
    {
	name = m.getName();
	gameIndex = m.getGameIndex();
	namePointer = m.getNamePointer();
	chronologicalIndex = m.getChronologicalIndex();
    }

    @Override
    public String toString()
    {
	if (name.isEmpty()) { return "(empty)"; }
	return name;
    }

    public String getName()
    {
	return name;
    }

    public int getGameIndex()
    {
	return gameIndex;
    }

    public int getNamePointer()
    {
	return namePointer;
    }

    public void setName(String name)
    {
	this.name = name;
    }

    public void setGameIndex(int gameIndex)
    {
	this.gameIndex = gameIndex;
    }

    public int getChronologicalIndex()
    {
	return chronologicalIndex;
    }

    public void setChronologicalIndex(int chronologicalIndex)
    {
	this.chronologicalIndex = chronologicalIndex;
    }

}
