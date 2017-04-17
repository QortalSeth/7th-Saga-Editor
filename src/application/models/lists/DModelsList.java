package application.models.lists;

import java.util.ArrayList;

import application.ROM;
import application.controllers.MainMenu;
import application.models.Model;

public class DModelsList<T extends Model> extends ArrayList<T>
{

    public DModelsList()
    {
	super();
    }

    public DModelsList(DModelsList<T> models)
    {
	super();
	addUsefulModels(models, true);
    }

    private void addUsefulModels(DModelsList<T> models, boolean keepEmptyModel)
    {
	for (T model : models)
	{
	    if (ROM.showEmptyValues == true || !model.getName().trim().isEmpty() || (keepEmptyModel == true && model.getGameIndex() == 0))
	    {
		add(model);
	    }
	}
    }
}
