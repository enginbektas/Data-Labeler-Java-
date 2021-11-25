import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Controller {
    private Log log;
    public Controller() {
        this.log = new Log();
    }

    public ArrayList<User> userReader(File configJson) {
        ArrayList<User> users = new ArrayList<User>();
        JSONParser parser = new JSONParser(); // create JSON parser
        try {

            Object obj = parser.parse(new FileReader(configJson));
            JSONObject jsonObject = (JSONObject) obj; // assign the parsed version of our file to a JSONObject

            JSONArray jsonArrayForUsers = (JSONArray) jsonObject.get("users");
            Instance[] instances = new Instance[jsonArrayForUsers.size()];

            for (int i = 0; i < jsonArrayForUsers.size(); i++) { // assigns the given instances in the input to the
                                                                 // instances array
                JSONObject obj2 = (JSONObject) jsonArrayForUsers.get(i); // declare obj2 to i'th element of JSON
                                                                         // classlabelsarray
                long userId = (long) obj2.get("user id"); // obj2 is now the element of the array
                String userName = (String) obj2.get("user name");
                String userType = (String) obj2.get("user type");
                double consistencyCheckProbability = (double) obj2.get("consistency check probability");
                String password = (String) obj2.get("password");


                JSONArray jsonArrayForDatasetIds = (JSONArray) obj2.get("dataset ids");
                ArrayList<Integer> datasetIds = new ArrayList<>(); // set dataset ids to user
                for (int j = 0; j < jsonArrayForDatasetIds.size(); j++) { // iterate size times
                    long datasetId = (long) jsonArrayForDatasetIds.get(j); //
                    datasetIds.add((int) datasetId); //
                }
                users.add(new User((int) userId, userName, userType, datasetIds, consistencyCheckProbability, password));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return users;
    }

    public ArrayList<Storage> configController(File configJson, ArrayList<User> userList) {
        // iterate through configs datasets, check if they have output, if so read
        // output with storageReader
        // hold dataset and assignments and users, call assigner and set assignments to
        // dataset,
        // set dataset.getStorage.setDataset, dataset.getStorage.setAssignments,
        // dataset.getStorage.setUsers
        // if not read dataset with datasetReader
        // add the dataset to the list, iterate for the next dataset
        // return the list of datasets
        ArrayList<Storage> storageList = new ArrayList<Storage>();

        Dataset dataset;
        JSONParser parser = new JSONParser(); // create JSON parser
        try {

            Object obj = parser.parse(new FileReader(configJson));
            JSONObject jsonObject = (JSONObject) obj; // assign the parsed version of our file to a JSONObject
            JSONArray jsonArrayForDatasetPointers = (JSONArray) jsonObject.get("datasets");

            for (int i = 0; i < jsonArrayForDatasetPointers.size(); i++) {

                Storage storage = new Storage();
                JSONObject obj2 = (JSONObject) jsonArrayForDatasetPointers.get(i); // declare obj2 to i'th element of
                                                                                   // JSON classlabelsarray
                long datasetId = (long) obj2.get("dataset id"); // obj2 is now the element of the array
                String path = (String) obj2.get("path");
                File outputFile = new File("Outputs\\Output" + datasetId + ".json");
                File inputFile = new File(path);
                if (outputFile.exists()) {
                    storage = storageReader(inputFile, outputFile, userList);
                }
                else { // if no output, read input
                    dataset = reader(inputFile);
                    storage.setDataset(dataset);

                }
                for (User user : userList) {
                    if (user.getDatasetIds().contains(storage.getDataset().getId()) && !(storage.getUsers().contains(user))) {
                        storage.getUsers().add(user);
                        storage.getDataset().getUsers().add(user);
                    }

                }

                storageList.add(storage);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return storageList;
    }

    public Dataset reader(File inputJson) {
        Dataset dataset = new Dataset();
        JSONParser parser = new JSONParser(); // create JSON parser
        try {

            Object obj = parser.parse(new FileReader(inputJson));
            JSONObject jsonObject = (JSONObject) obj; // assign the parsed version of our file to a JSONObject

            long id = (long) jsonObject.get("dataset id"); // setting dataset id
            dataset.setId((int) id);

            dataset.setName((String) jsonObject.get("dataset name"));
            dataset.setMaxNumOfLabelsPerInstance((int) (long) jsonObject.get("maximum number of labels per instance"));

            // this block gets the info of labels from the input and assigns it to an array
            // of labels
            JSONArray jsonArrayForClassLabels = (JSONArray) jsonObject.get("class labels");
            Label[] labels = new Label[jsonArrayForClassLabels.size()]; // create labels array

            for (int i = 0; i < jsonArrayForClassLabels.size(); i++) { // assigns the given labels in the input to the
                                                                       // labels array
                JSONObject obj2 = (JSONObject) jsonArrayForClassLabels.get(i); // declare obj2 to i'th element of JSON
                                                                               // classlabelsarray
                long labelId = (long) obj2.get("label id"); // obj2 is now the element of the array
                String labelText = (String) obj2.get("label text");
                labels[i] = new Label(labelId, labelText);
            }
            ArrayList<Label> labelList = new ArrayList<>();
            for (int i = 0; i < labels.length; i++)
                labelList.add(labels[i]);
            dataset.setLabels(labelList);

            // this block gets the info of instances from the input and assigns it to an
            // array of instances
            JSONArray jsonArrayForInstances = (JSONArray) jsonObject.get("instances");
            Instance[] instances = new Instance[jsonArrayForInstances.size()];
            for (int i = 0; i < jsonArrayForInstances.size(); i++) { // assigns the given instances in the input to the
                                                                     // instances array
                JSONObject obj2 = (JSONObject) jsonArrayForInstances.get(i); // declare obj2 to i'th element of JSON
                                                                             // classlabelsarray
                long instanceId = (long) obj2.get("id"); // obj2 is now the element of the array
                String instance = (String) obj2.get("instance");
                instances[i] = new Instance(instanceId, instance);
            }
            ArrayList<Instance> instanceList = new ArrayList<>();
            for (int i = 0; i < instances.length; i++)
                instanceList.add(instances[i]);
            dataset.setInstances(instanceList);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return dataset;
    }

    private Storage storageReader(File inputJson, File outputJson, ArrayList<User> userList) {
        Storage storage = new Storage();
        Dataset dataset = new Dataset();
        JSONParser parser = new JSONParser(); // create JSON parser
        try {
            // Taking users from the file

            Object obj = parser.parse(new FileReader(outputJson));
            JSONObject jsonObject = (JSONObject) obj; // assign the parsed version of our file to a JSONObject
            // Taking dataset from the file
            dataset = reader(inputJson);
            storage.setDataset(dataset);
            // Taking assignments from the file
            JSONArray jsonArrayForAssignments = (JSONArray) jsonObject.get("class label assignments");
            Assignment[] assignments = new Assignment[jsonArrayForAssignments.size()];
            for (int i = 0; i < jsonArrayForAssignments.size(); i++) { 
                /* 
                assigns the given instances in the input to
                  the instances array
                */ 
                JSONObject obj2 = (JSONObject) jsonArrayForAssignments.get(i); // declare obj2 to i'th element of JSON
                
                long instanceId = (long) obj2.get("instance id"); // obj2 is now the element of the array

                JSONArray jsonArrayForClassLabelIds = (JSONArray) obj2.get("class label ids");
                ArrayList<Integer> classLabelIds = new ArrayList<>();
                for (int j = 0; j < jsonArrayForClassLabelIds.size(); j++) { // iterate size times
                    long classLabelId = (long) jsonArrayForClassLabelIds.get(j); //
                    classLabelIds.add((int) classLabelId); 
                }

                long userId = (long) obj2.get("user id");
                String date = (String) obj2.get("datetime");
                double time = (double) obj2.get("time");
                Instance instance = storage.getDataset().getInstance((int) instanceId);

                User user = new User();
                ArrayList<Label> labels = dataset.getLabelListFromId(classLabelIds);
                for (User userj : userList){
                    if (userj.getId() == userId)
                        user = userj;
                }
                if (!dataset.getUsers().contains(user)) {
                    dataset.getUsers().add(user);
                }

                assignments[i] = new Assignment(dataset, userList, instance, user, date, labels, time);
            }

            ArrayList<Assignment> assignmentList = new ArrayList<>();
            for (int i = 0; i < assignments.length; i++)
                assignmentList.add(assignments[i]);

            storage.setAssigments(assignmentList);
            log.write("Dataset" + storage.getDataset().getId() + "'s previous assignments are read.");
            for (Assignment assignmentIter : storage.getAssigments()) {
                assignmentIter.setDataset(dataset); // Set dataset
                assignmentIter.setUserList(userList); // Set userList
                assignmentIter.setUser(); // Set user
                assignmentIter.setInstance();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return storage;
    }

    public int getCurrentDatasetId(File file) {
        long currentDatasetId = 0;
        JSONParser parser = new JSONParser(); // create JSON parser
        try {
            Object obj = parser.parse(new FileReader(file));
            JSONObject jsonObject = (JSONObject) obj; // assign the parsed version of our file to a JSONObject
            currentDatasetId = (long) jsonObject.get("current dataset id");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return (int) currentDatasetId;
    }
}
