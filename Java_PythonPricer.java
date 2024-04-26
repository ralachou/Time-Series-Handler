import sys

def calculate_bond_price(face_value, coupon_rate, years_to_maturity, market_rate):
    coupon_payment = face_value * coupon_rate
    present_value_coupons = sum([coupon_payment / (1 + market_rate)**n for n in range(1, years_to_maturity + 1)])
    present_value_face_value = face_value / (1 + market_rate)**years_to_maturity
    return present_value_coupons + present_value_face_value

if __name__ == "__main__":
    face_value = float(sys.argv[1])
    coupon_rate = float(sys.argv[2])
    years_to_maturity = int(sys.argv[3])
    market_rate = float(sys.argv[4])

    price = calculate_bond_price(face_value, coupon_rate, years_to_maturity, market_rate)
    print(f"{price:.2f}")



-----Java
  import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class BondPricerCaller {
    public static void main(String[] args) {
        try {
            // Bond parameters
            double faceValue = 1000.0; // $1000
            double couponRate = 0.05; // 5%
            int yearsToMaturity = 10; // 10 years
            double marketRate = 0.03; // 3%

            // Prepare the command to execute the Python script
            String command = String.format("python bond_pricer.py %.2f %.2f %d %.2f",
                                           faceValue, couponRate, yearsToMaturity, marketRate);

            // Execute the command
            Process process = Runtime.getRuntime().exec(command);
            
            // Read the output from the Python script
            BufferedReader stdInput = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String output;
            while ((output = stdInput.readLine()) != null) {
                System.out.println("Bond price: " + output);
            }
            
            // Wait for the process to exit and check for errors
            int exitCode = process.waitFor();
            if (exitCode != 0) {
                System.err.println("Python script exited with error code: " + exitCode);
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}



Explanation:
Python Script: The script bond_pricer.py takes four arguments representing the bond's face value, coupon rate, years to maturity, and the market interest rate. It calculates the bond price and prints it.
Java Program: The BondPricerCaller class constructs a command string with the bond parameters and executes the Python script. It reads and prints the bond price from the script’s standard output.
