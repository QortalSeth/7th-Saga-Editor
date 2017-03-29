package models.lists.states;

import models.Model;
import models.lists.Models;

public interface IModelState
{
    public <T extends Model, S extends Models<T>> void Initialize(S models);

}
