package application.models.lists;

import java.io.Serializable;
import java.util.List;

import application.controllers.MainMenu;
import javafx.collections.FXCollections;
import application.models.Apprentice;

public class Apprentices extends Models<Apprentice> implements Serializable
{
    private static String[] dialogueTypes      =
    {
	    "Offer to Join", "Beg to Join", "Make no Offer", "Ask to get Stronger", "Offer to Fight", "Ask to go Away"
    };

    private final int	    apprenticeListSize = 7;

    private int		    goldPerLevel;
    private int		    DgoldPerLevel;

    public Apprentices()
    {
	super();
    }

    public Apprentices(Apprentices addFrom)
    {
	super(addFrom);
    }

    @Override
    public void initializeModel(List<Apprentice> models)
    {
	if (models == null)
	{
	    models = FXCollections.observableArrayList();
	}
	for (int i = 0; i < apprenticeListSize; i++)
	{
	    Apprentice apprentice = new Apprentice(i);
	    apprentice.getValuesFromROM();
	    models.add(apprentice);
	}
	goldPerLevel = MainMenu.getBytes()[0x280c2 + MainMenu.getHeader()];
    }

    @Override
    public void saveModels()
    {
	for (Apprentice a : models)
	    a.writeValuesToROM();
	MainMenu.getBytes()[0x280c2 + MainMenu.getHeader()] = (byte) goldPerLevel;
    }

    public int getGoldPerLevel()
    {
	return goldPerLevel;
    }

    public void setGoldPerLevel(int goldPerLevel)
    {
	this.goldPerLevel = goldPerLevel;
    }

    public int getDgoldPerLevel()
    {
	return DgoldPerLevel;
    }

    public void setDgoldPerLevel(int dgoldPerLevel)
    {
	DgoldPerLevel = dgoldPerLevel;
    }

    public static String[] getDialogueTypes()
    {
	return dialogueTypes;
    }

}
