package application.models.lists;

import java.io.Serializable;
import java.util.List;

import application.ROM;
import application.controllers.MainMenu;
import application.models.Monster;

public class Monsters extends Models<Monster> implements Serializable
{
    private static final int monsterListSize = 98;

    public Monsters()
    {
	super();
    }

    public Monsters(Monsters addFrom, boolean keepEmptyModel)
    {
	super(addFrom, keepEmptyModel);
    }

    @Override
    public void initializeModel(List<Monster> models)
    {
	for (int i = 0; i < monsterListSize; i++)
	{
	    Monster monster = new Monster(i);
	    monster.getValuesFromROM();

	    if (ROM.showMonsterDuplicates == false)
	    {

		if (models.contains(monster)) // if monster is duplicate of
					      // existing monster, add it to
					      // that monsters list of aliases
		{
		    Monster monsterInList = models.get(models.indexOf(monster));
		    monsterInList.getAliases().add(monster);
		}

		else
		{
		    models.add(monster);
		}
	    }
	    else
	    {
		models.add(monster);
	    }
	}
    }

    @Override
    public void saveModels()
    {
	for (Monster m : models)
	{
	    m.writeValuesToROM();
	    for (Monster alias : m.getAliases())
	    {
		// System.out.println("alias of "+m.getName()+" is
		// "+alias.getName()+" gameIndexes are "+m.getGameIndex()
		// +" and "+ alias.getGameIndex());
		alias.writeValuesToROM();
	    }
	}
    }

}
