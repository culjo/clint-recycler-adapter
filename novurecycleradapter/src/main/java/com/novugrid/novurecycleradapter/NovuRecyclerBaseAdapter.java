package com.novugrid.novurecycleradapter;

import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by appy on 29/09/2017.
 */

public abstract class NovuRecyclerBaseAdapter<T> extends RecyclerView.Adapter{


    protected List<T> dataObjects;
    protected boolean isLoadingAdded = false;


    public NovuRecyclerBaseAdapter(@Nullable final List<T> objects) {
        dataObjects = objects != null ? objects : new ArrayList<T>();
    }

    public NovuRecyclerBaseAdapter(){
        this(null);
    }

    /* ******************************* */
    /* *** HELPERS *** */
    /* ******************************* */

    public T getItem(int position){
        try {
            return dataObjects.get(position);
        }catch (IndexOutOfBoundsException e){
            return null;
        }
    }

    public int getPosition (final T item){
        return dataObjects.indexOf(item);
    }

    public void updateAllItems(List<T> newItems){
        this.dataObjects.clear();
        dataObjects = newItems;
        notifyDataSetChanged();
    }

    public void addItem(final T newData, int index){
        this.dataObjects.add(index, newData);//Adds a new data Object to the list
        notifyItemInserted(index);
    }

    public void addItem(final T newItem){
        dataObjects.add(newItem);
        notifyItemInserted(dataObjects.size() - 1);
    }

    public void addAllItems(List<T> newItems){
        for (T item : newItems){
            addItem(item);
        }
    }

    public void removeItem(T item){
        int position = dataObjects.indexOf(item);
        if(position > -1){
            dataObjects.remove(position);
            notifyItemRemoved(position);
        }
    }

    public void removeItem(int index){
        dataObjects.remove(index);
        notifyItemRemoved(index);
    }

    public void clearItems(){
        isLoadingAdded = false;
        while (getItemCount() > 0){
            removeItem(getItem(0));
        }
    }

    public boolean isEmpty(){
        return getItemCount() == 0;
    }


    public void addLoadingFooter(T loadingItem) {
        isLoadingAdded = true;
        //T item;
        addItem(loadingItem);
    }

    public void removeLoadingFooter() {
        isLoadingAdded = false;

        int position = dataObjects.size() - 1;
        T item = getItem(position);

        if (item != null) {
            dataObjects.remove(position);
            notifyItemRemoved(position);
        }

    }

    public void removeLastObject(){
        dataObjects.remove(dataObjects.size() - 1);
        notifyItemRemoved(dataObjects.size() - 1);
    }

    public void replaceItem(final T oldDataObject, final T newDataObject) {

        final int position = getPosition(oldDataObject);
        dataObjects.remove(position);
        dataObjects.add(position, newDataObject);
        notifyItemChanged(position);

    }

    public void replaceItemAtPosition(final T oldDataObject, final T newDataObject, final int position) {


        dataObjects.remove(position);
        dataObjects.add(position, newDataObject);
        notifyItemChanged(position);

    }

    public void replaceItemWithHeader(final T oldDataObject, final T newDataObject) {

        final int position = getPosition(oldDataObject);
        dataObjects.remove(position);
        dataObjects.add(position, newDataObject);
        notifyItemChanged(position + 1);

    }

    public void sort(Comparator<? super T> comparator) {
        Collections.sort(dataObjects, comparator);
        notifyItemRangeChanged(0, getItemCount());
    }



}
