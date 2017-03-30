package models.lists.states;

import models.Model;
import models.lists.Models;

public class DataState implements IModelState
{

    @Override
    public <T extends Model, S extends Models<T>> void Initialize(S models)
    {
	models.initializeModel(models.getModels());
    }

}
