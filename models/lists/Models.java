package models.lists;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import controllers.MainMenu;
import models.Model;

public abstract class Models<T extends Model> implements Serializable
{
    transient protected ModelsList<T> models;
    protected DModelsList<T>	      Dmodels;

    public Models()
    {
	models = new ModelsList<>();
	Dmodels = new DModelsList<>();
    }

    public Models(DModelsList<T> DmodelsList)
    {
	models = new ModelsList<>();
	Dmodels = new DModelsList<>(DmodelsList);
    }

    public Models(Models<T> addFrom, boolean keepEmptyModel)
    {
	models = new ModelsList<>();
	Dmodels = new DModelsList<>();
	this.addUsefulModels(addFrom, keepEmptyModel);
    }

    public Models(Models<T> addFrom)
    {
	models = new ModelsList<>();
	Dmodels = new DModelsList<>();
	this.addUsefulModels(addFrom, false);
    }

    // public static <T extends Model> void resetListIndexes(List<T> list)
    // {
    // int index = 0;
    // for (Model m : list)
    // {
    // m.setListIndex(index);
    // index++;
    // }
    // }

    public void initializeModel(List<T> models)
    {
    }

    public void saveModels()
    {
    }

    public void clear()
    {
	models.clear();
	Dmodels.clear();
    }

    public void add(T model)
    {
	models.add(model);
	Dmodels.add(this.getDModel(model));
    }

    // public static <T extends Model> void sortList(List<? extends T> list, Comparator<T> c)
    // {
    // list.sort(c);
    // resetListIndexes(list);
    // }

    public void sortList(Comparator<T> c)
    {
	models.sort(c);
	Dmodels.sort(c);
    }

    // public static <T extends Model> void gameIndexSort(List<T> list)
    // {
    // sortList(list, new Comparator<T>()
    // {
    // @Override
    // public int compare(T m1, T m2)
    // {
    // return m1.getGameIndex() - m2.getGameIndex();
    // }
    // });
    // }

    public void gameIndexSort()
    {
	sortList(new Comparator<T>()
	{
	    @Override
	    public int compare(T m1, T m2)
	    {
		return m1.getGameIndex() - m2.getGameIndex();
	    }
	});
    }

    // public static <T extends Model> void chronologicalIndexSort(List<T> list)
    // {
    // sortList(list, new Comparator<T>()
    // {
    // @Override
    // public int compare(T m1, T m2)
    // {
    // return m1.getChronologicalIndex() - m2.getChronologicalIndex();
    // }
    // });
    // }

    public void chronologicalIndexSort()
    {
	sortList(new Comparator<T>()
	{
	    @Override
	    public int compare(T m1, T m2)
	    {
		return m1.getChronologicalIndex() - m2.getChronologicalIndex();
	    }
	});
    }

    // public static <T extends Model> void addUsefulModels(List<T> addTo, List<? extends T> addFrom, boolean keepEmptyModel)
    // {
    //
    // for (T model : addFrom)
    // {
    // if (MainMenu.getShowEmptyValues() == true || !model.getName().trim().isEmpty() || (keepEmptyModel == true && model.getGameIndex() == 0))
    // {
    // addTo.add(model);
    // }
    // }
    // resetListIndexes(addTo);
    // }

    public <R extends T> void addUsefulModels(Models<R> addFrom, boolean keepEmptyModel)
    {

	for (T model : addFrom.getModels())
	{
	    if (MainMenu.getShowEmptyValues() == true || !model.getName().trim().isEmpty() || (keepEmptyModel == true && model.getGameIndex() == 0))
	    {
		models.add(model);
	    }
	}

	Dmodels.addAll(addFrom.getDModels());
    }

    public T getDModel(T model)
    {
	for (T m : Dmodels)
	{
	    if (model.getGameIndex() == m.getGameIndex()) { return m; }
	}
	System.out.println("Model " + model.getName() + " has no default!");
	return null;
    }

    public T getDModel(int gameIndex)
    {
	for (T model : models)
	{
	    if (model.getGameIndex() == gameIndex) { return model; }
	}
	return null;
    }

    // public static <T extends Model> T searchByGameIndex(List<T> models, int gameIndex)
    // {
    // for (T model : models)
    // {
    // if (model.getGameIndex() == gameIndex) { return model; }
    // }
    // return null;
    // }

    public T getModel(int gameIndex)
    {
	for (T model : models)
	{
	    if (model.getGameIndex() == gameIndex) { return model; }
	}
	return null;
    }

    public int getIndex(int index)
    {
	int i = 0;
	for (T model : models)
	{
	    if (model.getGameIndex() == index) { return i; }
	    i++;
	}
	return -1;
    }

    public int getDIndex(int index)
    {
	int i = 0;
	for (T model : Dmodels)
	{
	    if (model.getGameIndex() == index) { return i; }
	    i++;
	}
	return -1;
    }

    public String getName(int gameIndex)
    {
	for (T model : models)
	{
	    if (model.getGameIndex() == gameIndex) { return model.toString(); }
	}
	return "Model not found";
    }

    public String getDName(int gameIndex)
    {
	for (T model : Dmodels)
	{
	    if (model.getGameIndex() == gameIndex) { return model.toString(); }
	}
	return "DModel not found";
    }

    public List<String> getNames(List<Integer> gameIndexes)
    {
	List<String> names = new ArrayList<String>();

	for (Integer i : gameIndexes)
	{
	    names.add(getName(i));
	}
	return names;
    }

    public List<String> getDNames(List<Integer> gameIndexes)
    {
	List<String> names = new ArrayList<String>();

	for (Integer i : gameIndexes)
	{
	    names.add(getDName(i));
	}
	return names;
    }

    public List<Integer> toIntegers()
    {
	List<Integer> indexes = new ArrayList<Integer>();

	for (Model m : models)
	{
	    indexes.add(m.getGameIndex());
	}
	return indexes;
    }

    public List<Integer> toDIntegers()
    {
	List<Integer> indexes = new ArrayList<Integer>();

	for (Model m : Dmodels)
	{
	    indexes.add(m.getGameIndex());
	}
	return indexes;
    }

    public ModelsList<T> getModels()
    {
	return models;
    }

    public DModelsList<T> getDModels()
    {
	return Dmodels;
    }

    public void setDmodels(DModelsList<T> dmodels)
    {
	Dmodels = dmodels;
    }

    public void setModels(ModelsList<T> models)
    {
	this.models = models;
    }

}
