package at.htlkaindorf.kopfschmerztagebuch.ui;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

import at.htlkaindorf.kopfschmerztagebuch.beans.Entry;

public class SharedViewModel extends ViewModel {
    private List<Entry> list = new ArrayList<>();
    private MutableLiveData<List<Entry>> liveData = new MutableLiveData<>();

    public List<Entry> getLiveData() {
        return liveData.getValue();
    }

    public void setLiveData(Entry liveData) {
        list.add(liveData);
        this.liveData.setValue(list);
    }

    public void addEntry(Entry entry){
        list.add(entry);
    }

    public List<Entry> getList() {
        return list;
    }
}
