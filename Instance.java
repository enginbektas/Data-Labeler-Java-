
import java.util.ArrayList;
import java.util.List;

class Instance {
    private int id;
    private String instance;
    private transient ArrayList<Label> labels;
    private transient ArrayList<User_Instance> user_instances;
    private transient InstancePerformanceMetric instancePerformanceMetrics;
    private Label finalLabel;

    public Instance(long id, String instance) {
        this.id = (int)id;
        this.instance = instance;
        this.labels = new ArrayList<Label>();
        user_instances = new ArrayList<>();
        this.instancePerformanceMetrics = new InstancePerformanceMetric(this);
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setInstance(String instance) {
        this.instance = instance;
    }

    public void setLabels(ArrayList<Label>  labels) {
        this.labels = labels;
    }

    public int getId() {
        return id;
    }

    public ArrayList<Label> getLabels() {
        return labels;
    }

    public String getInstance() {
        return instance;
    }

    public ArrayList<User_Instance> getUser_instances() {
        return user_instances;
    }

    public InstancePerformanceMetric getInstancePerformanceMetrics() {
        return instancePerformanceMetrics;
    }

    public void setFinalLabel(Label finalLabel) {
        this.finalLabel = finalLabel;
    }
}