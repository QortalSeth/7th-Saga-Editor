package application.models.lists;

import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import application.models.Model;

public class ModelsList<T extends Model> extends SimpleListProperty<T>
{
    public ModelsList()
    {
	super(FXCollections.observableArrayList());
    }

}
