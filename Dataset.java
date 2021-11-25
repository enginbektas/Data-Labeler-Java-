import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;
//#m//#lines 24
public class Dataset {
    @SerializedName("dataset id")
    private int id;
    @SerializedName("dataset name")
    private String name;
    @SerializedName("maximum number of labels per instance")
    private int maxNumOfLabelsPerInstance;
    @SerializedName("class labels")
    private ArrayList<Label> labels;
    private ArrayList<Instance> instances;
    private transient ArrayList<User> users;
    private transient DatasetPerformanceMetric datasetPerformanceMetric;


    public Dataset(){
        datasetPerformanceMetric = new DatasetPerformanceMetric(this);

        this.users = new ArrayList<>();
    }

    public ArrayList<Label> getLabelListFromId(ArrayList<Integer> classLabelIds) {
        ArrayList<Label> labels = new ArrayList<>();
        for (int classLabelId : classLabelIds){
            for (Label labelj : this.labels){
                if (labelj.getId() == classLabelId){
                    labels.add(labelj);
                    break;
                }
            }
        }
        return labels;

    }

    public ArrayList<Label> getLabels() {
        return labels;
    }

    public void setLabels(ArrayList<Label> labels) {
        this.labels = labels;
    }

    public ArrayList<Instance> getInstances() {
        return instances;
    }

    public void setInstances(ArrayList<Instance> instances) {
        this.instances = instances;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
        this.datasetPerformanceMetric.setDatasetId(id);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMaxNumOfLabelsPerInstance() {
        return maxNumOfLabelsPerInstance;
    }

    public void setMaxNumOfLabelsPerInstance(int maxNumOfLabelsPerInstance) {
        this.maxNumOfLabelsPerInstance = maxNumOfLabelsPerInstance;
    }

    public Label getLabelWithId(int labelId){
        Label label2 = new Label(0, "s");
        for (Label label: labels) {
            if (labelId == label.getId())
                return label;
        }
        return label2;
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    public void setUsers(ArrayList<User> users) {
        this.users = users;
    }

    public DatasetPerformanceMetric getDatasetPerformanceMetric() {
        return datasetPerformanceMetric;
    }

    public void setDatasetPerformanceMetric(DatasetPerformanceMetric datasetPerformanceMetric) {
        this.datasetPerformanceMetric = datasetPerformanceMetric;
    }

    public Label getLabel(int id){
        Label label = new Label(0,"");
        for (Label labelj : labels) {
            if (labelj.getId() == id)
                label = labelj;
        }
        return label;
    }

    public Instance getInstance(int id){
        Instance instance = new Instance(0, "");
        for (Instance instancej: instances) {
            if (instancej.getId() == id)
                instance = instancej;
        }
        return instance;
    }
}
