import tech.tablesaw.api.Table;
import tech.tablesaw.api.StringColumn;
import tech.tablesaw.api.DoubleColumn;

public class DataFrameExample {

    public static void main(String[] args) {
        // Create a new table
        Table df = Table.create("Employee Data")
            .addColumns(
                StringColumn.create("Name", new String[]{"Alice", "Bob", "Charlie"}),
                DoubleColumn.create("Age", new double[]{25, 30, 35}),
                DoubleColumn.create("Salary", new double[]{50000, 55000, 60000})
            );

        // Print the original DataFrame
        System.out.println("Original DataFrame:");
        System.out.println(df);

        // Filter the DataFrame where salary is greater than 52000
        Table filteredDf = df.where(df.doubleColumn("Salary").isGreaterThan(52000));

        // Print the filtered DataFrame
        System.out.println("Filtered DataFrame:");
        System.out.println(filteredDf);

        // Perform a summary operation on the Age column
        System.out.println("Summary of Age Column:");
        System.out.println(df.doubleColumn("Age").summary());
    }
}
