package application.models.lists.states;

import application.models.Model;
import application.models.lists.Models;

public interface IModelState
{
    public <T extends Model, S extends Models<T>> void Initialize(S models);

}
