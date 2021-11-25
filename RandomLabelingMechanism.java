import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;



public class RandomLabelingMechanism extends Mechanism{
    private String mechanismName;
    private transient ArrayList<Assignment> assignments;
    private Log log;

    public RandomLabelingMechanism(String mechanismName){
        this.mechanismName=mechanismName;
        this.assignments = new ArrayList<Assignment>();
        this.log = new Log();
    }

    public Assignment randomMechanism(ArrayList<User> userList, Dataset dataset, Instance instance, User user) throws InterruptedException {
        double labelingTimeStart = System.currentTimeMillis();
        //number of empty labels in the instance
        int labelNumberLeft = dataset.getMaxNumOfLabelsPerInstance() - instance.getLabels().size();
        if (labelNumberLeft == 0)
            return null;
        //how many labels to assign, randomly created
        int numberOfLabelsToAssign = (int) ((Math.random() * (labelNumberLeft - 1 )) + 1);
        //creates a list that holds the labels that are going to be assigned to the instance
        ArrayList<Label> labelsToUse = labelsToUse(dataset, instance, numberOfLabelsToAssign, user);
        //adds the new labels to the instances label list

        //instance.getLabels().addAll(labelsToUse);
        //creating date formatter
        SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss ");
        //s holds the current time
        Date date = new Date(System.currentTimeMillis());
        String dateString =  formatter.format(date);
        //Labels are assigned. Now creating an assignment object
        Thread.sleep((long) (Math.random() * 250));
        double labelingTimeEnd = System.currentTimeMillis();
        double labelingTime = labelingTimeEnd - labelingTimeStart;
        Assignment assignment = new Assignment(dataset, userList, instance, user, dateString, labelsToUse, labelingTime);
        assignments.add(assignment);
        return assignment;
    }
    //returns a list of labels to assign to an instance
    private ArrayList<Label> labelsToUse(Dataset dataset, Instance instance, int numberOfLabelsToAssign, User user) throws InterruptedException {
        ArrayList<Label> labelsToUse = new ArrayList<Label>();
        for (int i = 0; i < numberOfLabelsToAssign; i++){//Loop for chosing labels to use
            while(true){
                int k = (int) (Math.random() * (dataset.getLabels().size() - 1));//Get a random int value for choosing label from label list
                if ( true ){ //Checks if instance has that label or labels to use has it
                    labelsToUse.add(dataset.getLabels().get(k));
                    log.write("User " + user.getId() + " labeled instance " + instance.getId() + " by label " + dataset.getLabels().get(k).getId() + ".");
                    break;
                }
            }
        }
        return labelsToUse;
    }


    //Getters Setters
    public String getMechanismName() {
        return mechanismName;
    }

    public ArrayList<Assignment> getAssignments() {
        return assignments;
    }

    public void setMechanismName(String mechanismName) {
        this.mechanismName = mechanismName;
    }

    public void setAssignments(ArrayList<Assignment> assignments) {
        this.assignments = assignments;
    }
}
