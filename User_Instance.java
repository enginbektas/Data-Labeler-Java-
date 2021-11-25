import java.util.ArrayList;

public class User_Instance {
    private ArrayList<Label> labels;
    private User user;
    private Instance instance;
    private double time;

    public User_Instance(User user, Instance instance, ArrayList<Label> newLabels){
        labels = new ArrayList<>();
        addLabels(newLabels);
        this.user = user;
        this.instance = instance;

    }
    public void addLabels(ArrayList<Label> newLabels){
        labels.addAll(newLabels);
    }

    public Instance getInstance() {
        return instance;
    }

    public ArrayList<Label> getLabels() {
        return labels;
    }

    public User getUser() {
        return user;
    }

    public void setTime(double time) {
        this.time = time;
    }

    public double getTime() {
        return time;
    }
}
