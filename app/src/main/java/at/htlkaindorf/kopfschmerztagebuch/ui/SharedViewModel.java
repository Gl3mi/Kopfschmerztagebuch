package at.htlkaindorf.kopfschmerztagebuch.ui;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

import at.htlkaindorf.kopfschmerztagebuch.beans.Entry;

public class SharedViewModel extends ViewModel {
    private final MutableLiveData<List<Entry>> entry = new MutableLiveData<>();
    private final List<Entry> list = new ArrayList<>();

    public MutableLiveData<List<Entry>> getEntry() {
        return this.entry;
    }

    public void setEntry(Entry entry){
        list.add(entry);
        this.entry.setValue(list);
    }
}
