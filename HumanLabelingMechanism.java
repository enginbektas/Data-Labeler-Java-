import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class HumanLabelingMechanism extends Mechanism{

    private String mechanismName;
    private transient ArrayList<Assignment> assignments;
    private Log log;

    public HumanLabelingMechanism(String mechanismName) {
        this.mechanismName=mechanismName;
        this.assignments = new ArrayList<Assignment>();
        this.log = new Log();
    }
    public Assignment humanMechanism(ArrayList<User> userList, Dataset dataset, Instance instance, User user) {
        System.out.println("You are labeling instance " + instance.getId() + " -> " +instance.getInstance());
        for (Label label: dataset.getLabels()) {
            System.out.println(label.getId() + " -> " + label.getText());
        }
        double labelingTimeStart = System.currentTimeMillis();
        System.out.println("Please enter the number of labels you want to assign");
        Scanner input = new Scanner(System.in);
        int labelAmount = 0;
        while (true) {
            int tempInput = input.nextInt();
            if (tempInput <= dataset.getMaxNumOfLabelsPerInstance() - instance.getLabels().size()) {
                labelAmount = tempInput;
                break;
            }
            else if (dataset.getMaxNumOfLabelsPerInstance() - instance.getLabels().size() == 0) {
                break;
            }
            else {
                System.out.println("You entered a improper value, please try again. Avaliable label number is " + (dataset.getMaxNumOfLabelsPerInstance() - instance.getLabels().size()));
            }
        }
        ArrayList<Label> labelsToUse = new ArrayList<>();
        for (int i = 0; i < labelAmount; i++) {
            System.out.println("Please enter label id that you want to assign");

            while (true) {
                int tempInput = input.nextInt();
                if (tempInput <= dataset.getLabels().size()) {
                    int labelId = tempInput;
                    for (Label label : dataset.getLabels()) {
                        if (labelId == label.getId()) {
                            labelsToUse.add(label);
                            log.write("User " + user.getId() + " labeled instance " + instance.getId() + " by label " + label.getId() + ".");
                        }
                    }
                    break;
                }

                else {
                    System.out.println("You entered a improper value, please try again.");
                }
            }
        }
        //creating date formatter
        SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss ");
        //s holds the current time
        Date date = new Date(System.currentTimeMillis());
        String dateString =  formatter.format(date);
        double labelingTimeEnd = System.currentTimeMillis();
        double labelingTime = labelingTimeEnd - labelingTimeStart;
        Assignment assignment = new Assignment(dataset, userList, instance, user, dateString, labelsToUse, labelingTime);
        assignments.add(assignment);
        return assignment;
    }
}


