import java.util.TreeMap;

// A class to represent a bond with maturity and yield
class Bond {
    private final double maturity; // Maturity in years
    private final double yield;    // Yield as a percentage

    public Bond(double maturity, double yield) {
        this.maturity = maturity;
        this.yield = yield;
    }

    public double getMaturity() {
        return maturity;
    }

    public double getYield() {
        return yield;
    }

    @Override
    public String toString() {
        return String.format("Maturity: %.2f years, Yield: %.2f%%", maturity, yield);
    }
}

// A class to represent the yield curve using a TreeMap
public class YieldCurve {
    private TreeMap<Double, Bond> curve;

    public YieldCurve() {
        this.curve = new TreeMap<>();
    }

    // Adds or updates a bond in the yield curve
    public void addOrUpdateBond(double maturity, double yield) {
        curve.put(maturity, new Bond(maturity, yield));
    }

    // Retrieves a bond by maturity
    public Bond getBondByMaturity(double maturity) {
        return curve.get(maturity);
    }

    // Prints all bonds in the yield curve
    public void printCurve() {
        curve.values().forEach(System.out::println);
    }
}

// Main class to run examples
public class Main {
    public static void main(String[] args) {
        YieldCurve yieldCurve = new YieldCurve();
        yieldCurve.addOrUpdateBond(1, 1.5);
        yieldCurve.addOrUpdateBond(2, 1.7);
        yieldCurve.addOrUpdateBond(5, 2.0);
        yieldCurve.addOrUpdateBond(10, 2.5);
        yieldCurve.addOrUpdateBond(30, 3.0);

        yieldCurve.printCurve();
    }
}
