package at.htlkaindorf.kopfschmerztagebuch.ui;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import at.htlkaindorf.kopfschmerztagebuch.beans.Entry;

public class SharedViewModel extends ViewModel {
    private final MutableLiveData<Entry> entry = new MutableLiveData<>();

    public MutableLiveData<Entry> getEntry() {
        return this.entry;
    }

    public void setEntry(Entry entry) {
        this.entry.setValue(entry);
    }
}
