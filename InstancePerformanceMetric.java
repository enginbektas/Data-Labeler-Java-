import java.util.ArrayList;

public class InstancePerformanceMetric {
    private transient Instance instance;
    private int instanceId;
    private int totalNumberOfLabel;
    private int numberOfUniqueLabelAssignment;
    private int numberOfUniqueUsers;
    private ArrayList<PLabel> mostFrequentLabel;
    private ArrayList<PLabel> labelsPercentages;
    private double entropy;
    private transient ArrayList<User> userList;
    private transient Label finalLabel;



    public InstancePerformanceMetric(Instance instance){
        this.instance=instance;
        this.totalNumberOfLabel =0;
        this.numberOfUniqueLabelAssignment=0;
        this.numberOfUniqueUsers=0;
        this.entropy=0;
        this.userList = new ArrayList<>();
        this.instanceId = instance.getId();
    }

    public void update(User user) {
        if(!userList.contains(user)) {
            userList.add(user);
        }
        setTotalNumberOfLabel();
        setUniqueLabels();
        setNumberOfUniqueLabelAssignment();
        setNumberOfUniqueUsers();
        setLabelsPercentages();
        setMostFrequentLabel();
        setEntropy();
        updateFinalLabel();

    }

    private void updateFinalLabel() {
        this.finalLabel = mostFrequentLabel.get(0).getLabel();
        this.instance.setFinalLabel(finalLabel);
    }

    public void setTotalNumberOfLabel() {
        totalNumberOfLabel =instance.getLabels().size();
    }

    public void setUniqueLabels() {
        ArrayList<Label> tempUniqueLabels = instance.getLabels();
    }

    public void setNumberOfUniqueLabelAssignment() {
        numberOfUniqueLabelAssignment = 0;
        int i=0,j=0;
        for(i=0;i<instance.getLabels().size();i++){
            for(j=i+1;j<instance.getLabels().size();j++){
                if(instance.getLabels().get(i)==instance.getLabels().get(j))
                    break;
            }
            if(j==instance.getLabels().size())
                numberOfUniqueLabelAssignment++;
        }
    }

    public void setNumberOfUniqueUsers() {
        numberOfUniqueUsers = userList.size();
    }

    public void setLabelsPercentages() {
        int i=0,j=0;

        labelsPercentages = new ArrayList<PLabel>();
        PLabel tplabel = new PLabel(instance.getLabels().get(0),1);
        tplabel.setPercentage(totalNumberOfLabel);
        labelsPercentages.add(tplabel);
        for(i=1;i<instance.getLabels().size();i++){
            for (j=0;j<labelsPercentages.size();j++){
                if(labelsPercentages.get(j).getLabel()==instance.getLabels().get(i)) {
                    labelsPercentages.get(j).incNum();
                    labelsPercentages.get(j).setPercentage(totalNumberOfLabel);
                    break;
                }
            }
            if(j==labelsPercentages.size()){
                tplabel= new PLabel(instance.getLabels().get(i),1);
                tplabel.setPercentage(totalNumberOfLabel);
                labelsPercentages.add(tplabel);
            }
        }
    }

    public void setMostFrequentLabel() {
        int i=0,j=0;

        mostFrequentLabel = new ArrayList<PLabel>();
        mostFrequentLabel.add(labelsPercentages.get(0));
        for (i=1,j=0;i<labelsPercentages.size();i++){
            if(labelsPercentages.get(i).getNum()>mostFrequentLabel.get(j).getNum()){
                mostFrequentLabel.clear();
                mostFrequentLabel.add(labelsPercentages.get(i));
                j=0;
            }
            else if (labelsPercentages.get(i).getNum()== mostFrequentLabel.get(j).getNum()){
                mostFrequentLabel.add(labelsPercentages.get(i));
                j++;
            }
        }
    }

    public void setEntropy(){
        entropy = 0;
        int i=0;
        for (i=0;i<labelsPercentages.size();i++){
            if (numberOfUniqueLabelAssignment != 1) {
                entropy = entropy + (0 - labelsPercentages.get(i).getPercentage() / 100.0 * Math.log(labelsPercentages.get(i).getPercentage() / 100.0) / Math.log(numberOfUniqueLabelAssignment));
            } else {
                entropy = Double.POSITIVE_INFINITY;
            }
        }

    }

    public int getTotalNumberOfLabel(){ return totalNumberOfLabel; }

    public int getNumberOfUniqueLabelAssignment(){ return numberOfUniqueLabelAssignment; }

    public int getNumberOfUniqueUsers(){ return numberOfUniqueUsers; }

    public ArrayList<PLabel> getLabelsPercentages(){ return labelsPercentages; }

    public ArrayList<PLabel> getMostFrequentLabel(){ return mostFrequentLabel; }

    public double getEntropy(){ return entropy; }

    public ArrayList<User> getUserList() {
        return userList;
    }

    public Label getFinalLabel() {
        return finalLabel;
    }
}
