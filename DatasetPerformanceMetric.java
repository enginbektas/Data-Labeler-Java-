import java.util.ArrayList;
import java.util.List;

public class DatasetPerformanceMetric {
    private transient Dataset dataset;
    private int datasetId;
    private ArrayList<Percentage> labelDistributionPercentage;
    private double completenessPercentage;
    private ArrayList<Percentage> numberUniqueInstancesForEachLabel;
    private int numberOfUsers;
    private ArrayList<Percentage> usersCompleteness;
    private ArrayList<Percentage> consistencyPercentageOfUsers;

    public DatasetPerformanceMetric(Dataset dataset) {
        this.dataset = dataset;
        this.datasetId = dataset.getId();
        this.usersCompleteness = new ArrayList<>();
        this.consistencyPercentageOfUsers = new ArrayList<>();
    }
    //Update performance metric
    public void update(){
        this.datasetId = dataset.getId();
        this.calculateCompleteness();
        this.calculateConsistencyPercentageOfUsers();
        this.calculateLabelDistribution();
        this.calculateNumberOfUsers();
        this.calculateNumberUniqueInstancesForEachLabel();
        this.calculateUsersCompleteness();
        this.numberOfUsers = dataset.getUsers().size();
    }
    //Calculate completeness using instances
    private void calculateCompleteness(){
        this.completenessPercentage = 0;
        //for every instance of dataset
        for (Instance instance: dataset.getInstances() ) {
            //check instances has labels
            if(instance.getLabels().size() != 0){
                this.completenessPercentage++;
            }
        }
        this.completenessPercentage /= dataset.getInstances().size();
        this.completenessPercentage *= 100;
    }
    //Calculate label distribution
    private void calculateLabelDistribution(){
        //Clear label distribution array
        this.labelDistributionPercentage = new ArrayList<>();
        int totalUsage = 0;
        //for every label get number of uses
        for (Label label: dataset.getLabels()) {
            totalUsage += label.getNumberOfUses();
        }
        //Save label distributions for every label
        if (totalUsage == 0){
            for (Label label: dataset.getLabels()) {
                this.labelDistributionPercentage.add(new Percentage(label.getText(), 0));
            }
        }
        for (Label label: dataset.getLabels()) {
            this.labelDistributionPercentage.add(new Percentage(label.getText(), label.getNumberOfUses()  / (totalUsage * 1.0)));
        }
    }
    //Calculate number of unique instances for each label
    private void calculateNumberUniqueInstancesForEachLabel(){
        //Clear array
        this.numberUniqueInstancesForEachLabel = new ArrayList<>();
        //For every label of dataset
        for (Label label: dataset.getLabels()) {
            int numberOfUniqueInstances = 0;
            //Increment number of unique instances
            for (Instance instance: dataset.getInstances()) {
                if(instance.getLabels().contains(label)){
                    numberOfUniqueInstances++;
                }
            }
            this.numberUniqueInstancesForEachLabel.add(new Percentage(label.getText(), numberOfUniqueInstances));
        }
    }
    //Calculate number of users using user list of dataset
    private void calculateNumberOfUsers(){
        numberOfUsers = dataset.getUsers().size();
    }
    //Calculate user completeness from user performance metric
    private void calculateUsersCompleteness(){
        //Clear array
        this.usersCompleteness = new ArrayList<>();
        //For every user
        for (User user: dataset.getUsers()) {
            double percentage = 0;
            //Get user completeness from user metric
            for (int i = 0; i < user.getUserPerformanceMetrics().getUsersCompleteness().size(); i++) {
                if (user.getUserPerformanceMetrics().getUsersCompleteness().get(i).getName().equals(dataset.getId() + "")){
                    this.usersCompleteness.add( new Percentage(user.getUserName(), user.getUserPerformanceMetrics().getUsersCompleteness().get(i).getPercentage()));
                }
            }
        }
    }
    //Calculate consistency percentage of users from user metric
    private void calculateConsistencyPercentageOfUsers(){
        //Clear list
        consistencyPercentageOfUsers = new ArrayList<>();
        //Get consistency percentage from every users' performance metric
        for (User use: dataset.getUsers()) {
            consistencyPercentageOfUsers.add(new Percentage(use.getUserName(), use.getUserPerformanceMetrics().getConsistencyPercentage().getPercentage()));
        }
    }
    public void setDataset(Dataset dataset) {
        this.dataset = dataset;
    }
    public Dataset getDataset() {
        return dataset;
    }
    public double getCompletenessPercentage() {
        return completenessPercentage;
    }
    public void setDatasetId(int datasetId) {
        this.datasetId = datasetId;
    }
}


