//#lines 16

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class User {
    @SerializedName("user id")
    private int id;
    @SerializedName("user name")
    private String userName;
    @SerializedName("user type")
    private String userType;
    private transient UserPerformanceMetric userPerformanceMetrics;
    @SerializedName("dataset ids")
    private ArrayList<Integer> datasetIds;
    @SerializedName("consistency check probability")
    private double consistencyCheckProbability;
    private String password;

    private transient ArrayList<User_Instance> user_instances;

    public User(){
        userPerformanceMetrics = new UserPerformanceMetric(this);
    }

    public User(int id, String userName, String userType,ArrayList<Integer> datasetIds, double consistencyCheckProbability, String password){
        this.id=id;
        this.userName=userName;
        this.userType=userType;
        this.datasetIds=datasetIds;
        this.consistencyCheckProbability = consistencyCheckProbability;
        userPerformanceMetrics = new UserPerformanceMetric(this);
        user_instances = new ArrayList<>();
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserType() {
        return userType;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public UserPerformanceMetric getUserPerformanceMetrics() {
        return userPerformanceMetrics;
    }

    public void setUserPerformanceMetrics(UserPerformanceMetric userPerformanceMetrics) {
        this.userPerformanceMetrics = userPerformanceMetrics;
    }

    public ArrayList<Integer> getDatasetIds() {
        return datasetIds;
    }

    public ArrayList<User_Instance> getUser_instances() {
        return user_instances;
    }

    public double getConsistencyCheckProbability() {
        return consistencyCheckProbability;
    }

    public String getPassword() {
        return password;
    }
}
