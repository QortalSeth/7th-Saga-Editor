package models.lists;

import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import models.Model;

public class ModelsList<T extends Model> extends SimpleListProperty<T>
{
    public ModelsList()
    {
	super(FXCollections.observableArrayList());
    }

}
