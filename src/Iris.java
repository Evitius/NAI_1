public class Iris {

    private double range;
    private double[] values;
    private String type;

    public Iris(double[] values, String id)
    {
        this.values = values;
        this.type=id;
    }

    //obliczanie odległości sąsiadów
    public void setRange(Iris otherIris)
    {
        double tempRange = 0;
        for (int i = 0; i < values.length; i++) {
            tempRange += Math.pow((otherIris.values[i] - this.values[i]), 2);
        }

        this.range = tempRange;
    }

    public double getRange() {
        return range;
    }
    public String getType() {
        return type;
    }
    public double[] getValues(){ return values;}
}
