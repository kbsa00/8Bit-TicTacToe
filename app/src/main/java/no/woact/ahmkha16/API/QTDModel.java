package no.woact.ahmkha16.API;

/**
 * Created by Khalid B. Said
 *
 * Simple model class for the Quote of the Day. This model
 * is used for creating a object of the Quote of the day API.
 */

public class QTDModel {

    private String mAuthorName;
    private String mQuote;

    public QTDModel(String mAuthorName, String mQuote) {
        this.mAuthorName = mAuthorName;
        this.mQuote = mQuote;
    }

    public QTDModel() {

    }

    public String getAuthorName() {
        return mAuthorName;
    }

    public void setAuthorName(String mAuthorName) {
        this.mAuthorName = mAuthorName;
    }

    public String getQuote() {
        return mQuote;
    }

    public void setQuote(String mQuote) {
        this.mQuote = mQuote;
    }
}
