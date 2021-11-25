import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class UserPerformanceMetric {
    private User user;
    private transient ArrayList<Assignment> assignments;
    private transient ArrayList<Dataset> datasetsAssigned;
    private transient ArrayList<Instance> instancesLabeled;
    private transient ArrayList<Instance> uniqueInstancesLabeled;
    private transient ArrayList<Dataset> allDatasets;
    private transient ArrayList<Instance> instancesLabeledMoreThanOnce;

    private int datasetAssigned;
    private ArrayList<Percentage> datasetsCompletenessPercentage;
    private int numberOfInstancesLabeled;
    private int numberOfUniqueInstancesLabeled;
    private Percentage consistencyPercentage = new Percentage("", 0);
    private transient ArrayList<Percentage> usersCompleteness;
    private transient ArrayList<ArrayList<Object>> lastInstance;

    private transient double totalTimeSpentLabeling; //

    private double averageTimeSpentLabeling;
    private double stdDevOfTimeSpentLabelingInstances;


    public UserPerformanceMetric(User user) {
        
        this.user = user;
        this.assignments = new ArrayList<>();
        this.datasetsAssigned = new ArrayList<>();
        this.instancesLabeled = new ArrayList<>();
        this.uniqueInstancesLabeled = new ArrayList<>();
        this.allDatasets = new ArrayList<>();
        this.datasetsCompletenessPercentage = new ArrayList<>();
        this.usersCompleteness = new ArrayList<>();
        this.instancesLabeledMoreThanOnce = new ArrayList<>();
        this.lastInstance = new ArrayList<>();

    }

    public void update(Assignment assignment, Dataset dataset, Instance instance) {

        this.assignments.add(assignment);

        if (!instancesLabeled.contains(instance)){
            this.uniqueInstancesLabeled.add(instance);
        }
        else {
            if (!this.instancesLabeledMoreThanOnce.contains(instance))
            this.instancesLabeledMoreThanOnce.add(instance);
        }


        if (!datasetsAssigned.contains(dataset))
            this.datasetsAssigned.add(dataset);

        this.datasetAssigned = datasetsAssigned.size();

        this.instancesLabeled.add(instance);

        this.numberOfInstancesLabeled = instancesLabeled.size();

        this.numberOfUniqueInstancesLabeled = uniqueInstancesLabeled.size();

        calculateDatasetCompletenessPercentage();
        setConsistencyPercentage();
        calculateStd();

    }

    private void calculateDatasetCompletenessPercentage(){
        //usersCompleteness = new ArrayList<>();

        for (int i = 0; i < usersCompleteness.size(); i++) {
            usersCompleteness.get(i).setPercentage(0);
        }
        for (Instance instance: uniqueInstancesLabeled) {
            for (Assignment assignment: assignments) {
                if (instance.equals(assignment.getInstance()) && assignment.getUserId() == user.getId()){
                    if ( usersCompleteness.size() == 0){
                        usersCompleteness.add(new Percentage(assignment.getDataset().getId() + "", (1.0 / assignment.getDataset().getInstances().size()) * 100.0));
                        break;
                    }
                    else{
                        for (int i = 0; i < usersCompleteness.size(); i++) {
                            if (usersCompleteness.get(i).getName().equals(assignment.getDataset().getId() + "")){
                                usersCompleteness.get(i).setPercentage(usersCompleteness.get(i).getPercentage() + (1.0 / assignment.getDataset().getInstances().size()) * 100.0);
                                break;
                            }
                            if(i == usersCompleteness.size() - 1){
                                usersCompleteness.add(new Percentage(assignment.getDataset().getId() + "", (1.0 / assignment.getDataset().getInstances().size()) * 100.0));
                                break;
                            }
                        }
                    }
                    break;
                }
            }
        }
    }

    public ArrayList<Percentage> getUsersCompleteness() {
        return usersCompleteness;
    }

    public Percentage getConsistencyPercentage() {
        return consistencyPercentage;
    }

    public void setConsistencyPercentage() {

        for (Instance instance1 : instancesLabeledMoreThanOnce) {
            ArrayList<Label> reoccuringLabels = new ArrayList<>();
            ArrayList<Label> allLabels = new ArrayList<>();
            for (Assignment assignment : assignments) {
                allLabels.addAll(assignment.getLabelList());
                if (assignment.getInstance().equals(instance1)) {
                    for (Label label : assignment.getLabelList()) {
                        if (allLabels.contains(label)){
                            reoccuringLabels.add(label);
                        }
                    }
                }
            }
        }
    }

    private void calculateStd(){

    double sum = 0.0;
    double standardDeviation = 0.0;
    double mean = 0.0;
    double res = 0.0;
    double sq = 0.0;
    double[] arr = new double[user.getUser_instances().size()];
    int n = arr.length;
    int j = 0;
        for (User_Instance userInstance: user.getUser_instances()) {
            arr[j] = userInstance.getTime();
            j++;
        }

        for (int i = 0; i < n; i++) {
            sum = sum + arr[i];
        }

        mean = sum / (n);

        for (int i = 0; i < n; i++) {

            standardDeviation
                = standardDeviation + Math.pow((arr[i] - mean), 2);

        }

        sq = standardDeviation / n;
        res = Math.sqrt(sq);
        this.stdDevOfTimeSpentLabelingInstances = res;
    }

    public ArrayList<Dataset> getDatasetsAssigned() {
        return datasetsAssigned;
    }

    public ArrayList<Assignment> getAssignments() {
        return assignments;
    }

    public void setAssignments(ArrayList<Assignment> assignments) {
        this.assignments = assignments;
    }

    public void setDatasetsAssigned(ArrayList<Dataset> datasetsAssigned) {
        this.datasetsAssigned = datasetsAssigned;
    }

    public int getDatasetAssigned() {
        return datasetAssigned;
    }

    public void setDatasetAssigned() {
        this.datasetAssigned = getDatasetsAssigned().size();
    }

    public ArrayList<Instance> getInstancesLabeled() {
        return instancesLabeled;
    }

    public void setInstancesLabeled(ArrayList<Instance> instancesLabeled) {
        this.instancesLabeled = instancesLabeled;
    }

    public int getNumberOfInstancesLabeled() {
        return numberOfInstancesLabeled;
    }

    public void setNumberOfInstancesLabeled(int numberOfInstancesLabeled) {
        this.numberOfInstancesLabeled = this.instancesLabeled.size();
    }

    public ArrayList<Instance> getUniqueInstancesLabeled() {
        return uniqueInstancesLabeled;
    }

    public void setUniqueInstancesLabeled(ArrayList<Instance> uniqueInstancesLabeled) {
        this.uniqueInstancesLabeled = uniqueInstancesLabeled;
    }

    public int getNumberOfUniqueInstancesLabeled() {
        return numberOfUniqueInstancesLabeled;
    }

    public void setNumberOfUniqueInstancesLabeled() {
        this.numberOfUniqueInstancesLabeled = this.uniqueInstancesLabeled.size();
    }

    public ArrayList<Dataset> getAllDatasets() {
        return allDatasets;
    }

    public void setAllDatasets(ArrayList<Dataset> allDatasets) {
        this.allDatasets = allDatasets;
    }

    public ArrayList<Percentage> getDatasetsCompletenessPercentage() {
        return this.datasetsCompletenessPercentage;
    }

    public void setDatasetsCompletenessPercentage() {
        for (Dataset dataset : this.allDatasets) {
            this.datasetsCompletenessPercentage.add(new Percentage(dataset.getName(), dataset.getDatasetPerformanceMetric().getCompletenessPercentage()));
        }
    }

    public double getAverageTimeSpentLabeling() {
        return averageTimeSpentLabeling;
    }

    public void setAverageTimeSpentLabeling() {
        this.averageTimeSpentLabeling = totalTimeSpentLabeling / assignments.size();
    }

    public double getTotalTimeSpentLabeling() {
        return totalTimeSpentLabeling;
    }

    public void setTotalTimeSpentLabeling(double timeSpent) {

            totalTimeSpentLabeling = timeSpent;
    }
    public ArrayList<ArrayList<Object>> getLastInstance() {
        return lastInstance;
    }
}

