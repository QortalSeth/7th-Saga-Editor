package application.models;

import java.io.Serializable;

public class EquipmentAtLevel implements Comparable<EquipmentAtLevel>, Serializable
{
	int level;
	int[] equipment;
	public EquipmentAtLevel(int level, int[] equipment) 
	{
		this.level = level;
		this.equipment = equipment;
	}


	public int getLevel() {return level;}
	public int[] getEquipment() {return equipment;}


	@Override
	public int compareTo(EquipmentAtLevel e) 
	{return this.level-e.level;}

}
