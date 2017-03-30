package application.models.lists.states;

import application.models.Model;
import application.models.lists.Models;

public class DataState implements IModelState
{

    @Override
    public <T extends Model, S extends Models<T>> void Initialize(S models)
    {
	models.initializeModel(models.getModels());
    }

}
