import com.google.gson.annotations.SerializedName;

class Label {
    @SerializedName("label id")
    private int id;
    @SerializedName("label text")
    private String text;
    private int numberOfUses;

    public Label(long id, String text) {
        this.id = (int)id;
        this.text = text;
    }

    public int getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setNumberOfUses(int numberOfUses) {
        this.numberOfUses = numberOfUses;
    }


    public void incrementNumberOfUses() {
        this.numberOfUses++;
    }

    public int getNumberOfUses() {
        return numberOfUses;
    }
}