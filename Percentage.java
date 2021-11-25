public class Percentage {
    private String name;
    private double percentage;


    public Percentage(String name, double percentage) {
        this.name = name;
        this.percentage = percentage;
    }



    public void setName(String name) {
        this.name = name;
    }

    public void setPercentage(double percentage) {
        this.percentage = percentage;
    }

    public double getPercentage() {
        return percentage;
    }

    public String getName() {
        return name;
    }
}