import javassist.*;

public class TimeSeriesHandler {
    public static void main(String[] args) throws Exception {
        ClassPool pool = ClassPool.getDefault();
        CtClass cc = pool.makeClass("TimeSeries");

        // Add a double field to store sum of values
        CtField fieldSum = new CtField(CtClass.doubleType, "sum", cc);
        cc.addField(fieldSum, CtField.Initializer.constant(0.0));

        // Add an integer field to count the number of data points
        CtField fieldCount = new CtField(CtClass.intType, "count", cc);
        cc.addField(fieldCount, CtField.Initializer.constant(0));

        // Add a method to add a new data point
        CtMethod addData = new CtMethod(CtClass.voidType, "addData", 
            new CtClass[]{CtClass.doubleType}, cc);
        addData.setBody("{ this.sum += $1; this.count += 1; }");
        cc.addMethod(addData);

        // Add a method to calculate the average
        CtMethod calculateAverage = new CtMethod(CtClass.doubleType, "calculateAverage", 
            new CtClass[]{}, cc);
        calculateAverage.setBody("{ return this.count == 0 ? 0.0 : this.sum / this.count; }");
        cc.addMethod(calculateAverage);

        // Load and instantiate the dynamic class
        Class<?> dynamicClass = cc.toClass();
        Object instance = dynamicClass.newInstance();

        // Use reflection to invoke methods
        java.lang.reflect.Method methodAddData = dynamicClass.getMethod("addData", double.class);
        java.lang.reflect.Method methodCalculateAverage = dynamicClass.getMethod("calculateAverage");

        // Add data points
        methodAddData.invoke(instance, 10.0);
        methodAddData.invoke(instance, 20.0);
        methodAddData.invoke(instance, 30.0);

        // Calculate average
        Double average = (Double) methodCalculateAverage.invoke(instance);
        System.out.println("Average: " + average);
    }
}
