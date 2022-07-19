package at.htlkaindorf.kopfschmerztagebuch.ui.entry;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class EntryViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public EntryViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is intensity1 fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}