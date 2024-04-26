# script.py
import sys

# Simple function to add two numbers
def add_numbers(x, y):
    return x + y

if __name__ == "__main__":
    # Takes two command line arguments for the numbers
    num1 = int(sys.argv[1])
    num2 = int(sys.argv[2])
    result = add_numbers(num1, num2)
    print(result)



  -------Java
  import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class PythonCaller {
    public static void main(String[] args) {
        try {
            // Command to execute Python script
            String command = "python script.py 5 3"; // Example adding 5 + 3
            Process process = Runtime.getRuntime().exec(command);
            
            // Reading the output from Python
            BufferedReader stdInput = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String s;
            while ((s = stdInput.readLine()) != null) {
                System.out.println("Python output: " + s);
            }
            
            int exitCode = process.waitFor();
            System.out.println("Exited with code " + exitCode);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}




Explanation:
Python Script: This script expects two command-line arguments, adds them, and prints the result. It's a simple demonstration of how Python can perform computations and return results.
Java Program: This program builds a command string to execute the Python script with arguments. It uses Runtime.getRuntime().exec() to start the process. The program then reads the output from Python using BufferedReader attached to the process's output stream. It prints the Python output in the Java console and also checks for the process's exit code to handle errors.
Considerations:
Error Handling: The Java example includes basic error handling for exceptions and should also handle standard error output from the Python script for complete integration.
Security: Be cautious with constructing commands that include user input to avoid injection attacks.
Performance: Running an external process can be heavy, especially if called frequently. Consider performance implications and possibly look into more integrated solutions like Jython or GraalVM for tighter integration if necessary.
This method provides a flexible way to run Python code from Java, suitable for applications that need occasional scripting and computation capabilities outside of Java's standard offerings.
