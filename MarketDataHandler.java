import javassist.*;

public class MarketDataHandler {
    public static void main(String[] args) throws Exception {
        ClassPool pool = ClassPool.getDefault();
        CtClass cc = pool.makeClass("MarketData");

        // Add fields for time series data
        CtField pricesField = new CtField(pool.get("java.util.ArrayList"), "prices", cc);
        CtField timestampsField = new CtField(pool.get("java.util.ArrayList"), "timestamps", cc);
        cc.addField(pricesField, CtField.Initializer.byExpr("new java.util.ArrayList()"));
        cc.addField(timestampsField, CtField.Initializer.byExpr("new java.util.ArrayList()"));

        // Method to add data
        CtMethod addData = CtNewMethod.make(
            "public void addData(double price, long timestamp) {" +
            "  prices.add(price);" +
            "  timestamps.add(timestamp);" +
            "}", cc);
        cc.addMethod(addData);

        // Method to get the latest price
        CtMethod getLatestPrice = CtNewMethod.make(
            "public double getLatestPrice() {" +
            "  return prices.isEmpty() ? 0.0 : (double) prices.get(prices.size() - 1);" +
            "}", cc);
        cc.addMethod(getLatestPrice);

        // Method to calculate simple moving average (SMA) for the last N prices
        CtMethod calculateSMA = CtNewMethod.make(
            "public double calculateSMA(int period) {" +
            "  int start = Math.max(0, prices.size() - period);" +
            "  double sum = 0.0;" +
            "  for (int i = start; i < prices.size(); i++) {" +
            "    sum += (double) prices.get(i);" +
            "  }" +
            "  return prices.size() - start == 0 ? 0.0 : sum / (prices.size() - start);" +
            "}", cc);
        cc.addMethod(calculateSMA);

        // Load and instantiate the dynamic class
        Class<?> dynamicClass = cc.toClass();
        Object instance = dynamicClass.newInstance();

        // Reflection to invoke methods
        java.lang.reflect.Method methodAddData = dynamicClass.getMethod("addData", double.class, long.class);
        java.lang.reflect.Method methodGetLatestPrice = dynamicClass.getMethod("getLatestPrice");
        java.lang.reflect.Method methodCalculateSMA = dynamicClass.getMethod("calculateSMA", int.class);

        // Adding some data
        methodAddData.invoke(instance, 100.5, System.currentTimeMillis());
        methodAddData.invoke(instance, 102.0, System.currentTimeMillis());
        methodAddData.invoke(instance, 105.3, System.currentTimeMillis());

        // Using methods
        System.out.println("Latest Price: " + methodGetLatestPrice.invoke(instance));
        System.out.println("SMA of last 3 prices: " + methodCalculateSMA.invoke(instance, 3));
    }
}
