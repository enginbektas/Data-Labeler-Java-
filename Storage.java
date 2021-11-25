import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

//#methods =13
public class Storage {

    private Dataset dataset = new Dataset();
    @SerializedName("class label assignments")
    private ArrayList<Assignment> assignments;
    private ArrayList<User> users;
    /*
    private ArrayList<InstancePerformanceMetric> instancePerformanceMetrics;
    private ArrayList<UserPerformanceMetric> userPerformanceMetrics;
    private DatasetPerformanceMetric datasetPerformanceMetric;

     */

    public Storage() {

    }

    public void setDataset(Dataset dataset) {
        this.dataset = dataset;
        this.assignments = new ArrayList<>();
        this.users = new ArrayList<>();

    }

    public Dataset getDataset() {
        return dataset;
    }

    public void setAssigments(ArrayList<Assignment> assignments) {
        this.assignments = assignments;
    }

    public ArrayList<Assignment> getAssigments() {
        return assignments;
    }

    public void setUsers(ArrayList<User> users) {
        this.users = users;
    }

    public ArrayList<User> getUsers() {
        return users;
    }

}
