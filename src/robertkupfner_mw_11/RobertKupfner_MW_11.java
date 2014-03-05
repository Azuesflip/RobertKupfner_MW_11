/**
   This program is used to take an input file and put the first 20
   items. Then it will put it into order from largest to smallest 
   while also outputting its original position of how it's inputed.
   If the list is larger than 20 integers it will flush the rest
   to keep them from the array and prevent bugs.
   
   Robert Kupfner
   Program #11, CS 1050, MW
   Java 7.4, Lenovo Ideapad y400, Windows 8
 */

package robertkupfner_mw_11;

import java.io.*;
import java.text.DecimalFormat;
import java.util.*;
import java.lang.*;

public class RobertKupfner_MW_11 
{
    public static double federalWithholding = 0.18;
    public static double stateWithholding = 0.045;
    
    
    public static void main(String[] args) throws Exception
    {
        //Declarations for input and output files
        final int INITIALIZER = 30;
        final String INPUT_FILE = "C:\\School\\CS 1050\\AA.txt";
        final String OUTPUT_FILE = "C:\\School\\CS 1050\\Output.txt";
        
        //Sets up scanner to read information from input file
        Scanner inputFile = input_File(INPUT_FILE);
        Scanner inputFileNames = input_File(INPUT_FILE);
        Scanner inputFileNamesToSort = input_File(INPUT_FILE);
        Scanner inputFileAmount = input_File(INPUT_FILE);
        Scanner inputFileAmountToSort = input_File(INPUT_FILE);
        
        //Sets up writer to append data to output file
        PrintWriter outputFile = outputPrintFile(OUTPUT_FILE);
        
        //Declarations for various arrays to hold information to process
        double[][] hoursAndPay = hoursAndPayArray(inputFile, INITIALIZER);
        double[][] hoursAndPayToSort = hoursAndPayArray(inputFileAmountToSort, 
                                                                       INITIALIZER);
        double[][] dataOutput = calculateTotalTable(hoursAndPay, INITIALIZER);
        double[][] dataToSort = calculateTotalTable(hoursAndPayToSort, INITIALIZER);
        String[] names = nameInputArray(inputFileNames, INITIALIZER);
        String[] namesToSort = nameInputArray(inputFileNamesToSort, INITIALIZER);
        
        //Arrays to hold final sorted data
        String[] namesSorted = selectionSortNames(namesToSort, dataToSort, 
                                                                       INITIALIZER);
        double[][] dataSorted = selectionSortRates(namesToSort, dataToSort, 
                                                                    INITIALIZER,0 );
        
        //Strings to simplify method calls
        String tableHeading = tableHeading();
        String tableBodyUnsorted = tableBody(dataOutput, names, INITIALIZER);
        String footer = footer(inputFileAmount);
        String tableBodySorted = tableBody(dataSorted, namesSorted, INITIALIZER);
        
        //For loop to display names to user
        for(int i = 0; i < INITIALIZER; i++)
        {
            System.out.println(names[i]);
        }
        
        //Method calls to write data into output file
        outputFile.print(tableHeading);
        outputFile.print(tableBodyUnsorted);
        outputFile.printf(footer);
        outputFile.print(tableHeading);
        outputFile.print(tableBodySorted);
        outputFile.print(footer);
        outputFile.close();
    }
    
      

   /** input_File
       Creates a Scanner for the input file to be used in creating arrays
            
       Spec Reference

       Input
         @param INPUT_FILE The input file string to be converted
         
       Process    1. Takes in the input file
                  2. Converts file into a Scanner to use
         
       Output
         @return inputFile
         
       Note
   */
    public static Scanner input_File(String INPUT_FILE) throws Exception
    {
        File inputDataFile = new File(INPUT_FILE);
        
        Scanner inputFile = new Scanner(inputDataFile);
        
        return inputFile;
        
    }
    // *******************************************************************
    
    /** outputPrintFile
       Create a PrintWriter to create  or append a file and add information
            
       Spec Reference

       Input
         @param outputFileLocation file location to create or append
         
       Process    1. Takes in file location String
                  2. converts the string to make a file and print to it
         
       Output
         @return outputFile
         
       Note
   */
    public static PrintWriter outputPrintFile(String outputFileLocation
                                                            ) throws Exception
    {
       
        FileWriter outputDataFile = new 
                     FileWriter(outputFileLocation, false);
        
        PrintWriter outputFile = new PrintWriter(outputDataFile);
        return outputFile;
    }
    //****************************************************************************
    
    /** hoursAndPayArray
       Takes in the input file and forms an output string form.
            
       Spec Reference

       Input
         @param inp Scanner of the input file to use in printing out the
                    original array.
         @param arrayLength gives length of the input array to use
         
       Process    1. Creates a header for the output
                  2. Inputs the files grades into an array to printout
                  3. goes through the array and formats the output table
                     to print the first page table
                     
         
       Output
         @return outputArray
         
       Note
   */
    public static double[][] hoursAndPayArray (Scanner numberInput,
            int arraySize )
    {
        double[][] outputArray = new double[arraySize][2];
        int nRead = 0;
        String names;
        String clear = "";
        
        while (numberInput.hasNext())
        {
            if (nRead < arraySize)
            {
                outputArray[nRead][0] = numberInput.nextDouble();
                outputArray[nRead][1] = numberInput.nextDouble();
                names = numberInput.nextLine();
            }
            else
            {
                clear = numberInput.nextLine();
            }
            nRead++;       //Progress array memory segment
        }
        
        return outputArray;
    }
    //*****************************************************************************
    
    /** nameInputArray
       Takes in the input file and forms an output string form.
            
       Spec Reference

       Input
         @param inp Scanner of the input file to use in printing out the
                    original array.
         @param arrayLength gives length of the input array to use
         
       Process    1. Creates a header for the output
                  2. Inputs the files grades into an array to printout
                  3. goes through the array and formats the output table
                     to print the first page table
                     
         
       Output
         @return outputArray
         
       Note
   */
    public static String[] nameInputArray (Scanner nameInput, int arraySize)
    {
        
        String[] outputArray = new String[arraySize];
        double pay;
        double hours;
        int nRead = 0;
        int clear = 0;
        
        while (nameInput.hasNext())
        {
            
            if (nRead < arraySize)
            {
                hours = nameInput.nextDouble();
                pay = nameInput.nextDouble();
                outputArray[nRead] = nameInput.nextLine().trim();
            } // End if
            else // There's more data, but we're ignoring it
            { 
                clear = nameInput.nextInt();     // Flush unstored data
            }
            nRead++;       // Indicate a record has been read
            
        }
        
        return outputArray;
    }
    //****************************************************************************
    
    /** calculateTotalTable
       Takes in the input file and forms an output string form.
            
       Spec Reference

       Input
         @param hoursAndPay array to pull data from for calculations
         
         @param initializer sets number of files to use
         
       Process    1. Determines data to use from array
                  2. Calculates pay rates
                     
         
       Output
         @return outputTotalPay
         
       Note
   */
    public static double[][] calculateTotalTable (double hoursAndPay[][], 
                                                               int initializer)
    {
        double[][] outputTotalPay = new double[initializer][6];
        
        for(int i = 0; i < initializer; i++)
        {
            outputTotalPay[i][0] = hoursAndPay[i][0];
            outputTotalPay[i][1] = hoursAndPay[i][1];
        }
        
        for(int i = 0; i < initializer; i++)
        {
            if(hoursAndPay[i][0] <= 40.0)
            {
                outputTotalPay[i][2] = hoursAndPay[i][1] * hoursAndPay[i][0];
            }
            else if(hoursAndPay[i][0] > 40.0 && hoursAndPay[i][0] < 50.0)
            {
               outputTotalPay[i][2] = (hoursAndPay[i][1] * 1.5) * hoursAndPay[i][0];
            }
            else
            {
               outputTotalPay[i][2] = (hoursAndPay[i][1] * 2.0) * hoursAndPay[i][0];
            }
        }
        
        for (int j = 0; j < initializer; j++)
        {
            outputTotalPay[j][4] = roundNumber(outputTotalPay[j][2] * 
                                                              federalWithholding,2);
            outputTotalPay[j][5] = roundNumber(outputTotalPay[j][2] * 
                                                              stateWithholding,2);
        }
        
        for (int k = 0; k < initializer; k++)
        {
            outputTotalPay[k][3] = outputTotalPay[k][2] - 
                                        outputTotalPay[k][4] - outputTotalPay[k][5];
        }
        
        return outputTotalPay;
    }
    //******************************************************************************
        
    /** tableHeading
       Method to print out the table heading
            
       Spec Reference

       Input
         None
         
       Process    1. Creates a String variable with the table heading
                  2. Returns the heading for output
                  
         
       Output
         @return headings
         
       Note
   */
    public static String tableHeading()
    {
        return "                                    Fabulous Furniture Company" + 
               "\n" + "                                          Payroll Report" + 
               "\n" + "\n" +
               "|       Name      |    Net    |   Gross   |  Fed Tax  | State" + 
               " Tax |   Hours   | Pay Rate  |" + "\n" +
               "________________________________________________________________" +
               "___________________________" + "\n";
    }
    //*****************************************************************************
    
    /** outputFormater
       To format the body of the table for output
            
       Spec Reference introduced in assignment 7

       Input
         @param sortedArray 2d array of sorted indexes and grades
         @param arrayLength give the max array length
         
       Process    1. Uses a for loop to go through each column
                  2. It will then add each row to the output string and 
                     align them with the leftPad method
         
       Output
         @return outputString
         
       Note
   */
    public static String tableBody(double[][] data, String[] names, int initializer)
    {
        String outputString = "";
        String pad = " ";      // Padding character
        int places = 11;        // Number of places for the final number
        String mask = "##.00";     // Mask to use in DecimalFormat
        for(int i = 0; i < initializer; i++)
        {
            if(names[i] != null)
            {
                outputString += "|" + rightPad(names[i], 17, pad) + "|" + 
                        leftPad(data[i][3], places, mask, pad) + "|" +
                        leftPad(data[i][2], places, mask, pad) + "|" +
                        leftPad(data[i][4], places, mask, pad) + "|" +
                        leftPad(data[i][5], places, mask, pad) + "|" +
                        leftPad(data[i][0], places, mask, pad) + "|" +
                        leftPad(data[i][1], places, mask, pad) + "|" +
                        "\n";
            }
        }
        return outputString;
    }
    //*************************************************************************
    
    /** footer
       Method to print out the table footer
            
       Spec Reference

       Input
         None
         
       Process    1. Creates a String variable with the table heading
                  2. Returns the heading for output
                  
         
       Output
         @return outputString
         
       Note
   */
    public static String footer(Scanner input_File)
    {
        int counter = 0;
        String clear = "";
        String outputString = "_________________________________________________" +
                                "__________________________________________" + "\n";
        
        while(input_File.hasNext())
        {
            clear = input_File.nextLine();
            counter++;
        }
        
        outputString += "The number of employees: " + counter + "\n";
        
        return outputString;
    }
    //************************************************************************
    
    /** selectionSortNames
       Sort an integer array from smallest to largest and keep track of
       the original indexes
            
       Spec Reference   Introduced with Assignment 10

       Input
         @param   array An integer array to sort. It is already filled
         @param   index An array that contains the indexes of the original values
                        which are typically index[i] = i + 1 to number them
                        for humans to read easily. That is,
                           index[0] = 1 = the first  element
                           index[1] = 2 = the second element, and so on
         
         
       Process    1. Start at the first element and assume it is the smallest value.
                     Call it the "current element"
                  2. Search the rest of the array and if you find a smaller value,
                     note it
                  3. At the end of the loop, swap the locations of the smallest 
                     value with the current element of the array
                  4. Move to the next element of the array, call it the "current
                     element" and repeat steps 2 and 3 until we reach the next
                     to the last element. Then, the largest element will be in the
                     last location
         
       Output
         @return  None
         
       Note       The specification for this method was changed from the original
                  assignment by adding a parameter of the number of elements to
                  process in the sort. This allows for sorting a 
                  partially-filled array
   */
   public static String[] selectionSortNames(String[] ary, double[][] numbers, 
                                          int len)
   {
    /*
      The next two lines of code establish an array that can contain
      an entire row of the two-dimnesional 'numbers' array. The first line
      gets the number of columns in one row. We make two assumptions here:
      1. The 'numbers' array is not empty (there is at least one entry). 
      2. The number of columns in the 'numbers' array is the same for all rows
         (i.e., a rectangular matrix). We use this idea in the first line of
         code that follows to access the first entry to get the # of column
         in any row of the matrix
    */
    int nColumns = numbers[0].length;  // # of columns in the 2-dimensional array
    double[] oneRow = new double[nColumns];  // Will hold one row during a swap
    String[] outputArray = new String[len];
    
    int k, i;	 // Indexes into the array to find the minimum value
    int minIndex; // Index of the minimum value in the array
    int aryLen;   // Length of the parameter array
    double temp;  // Temporary storage while swapping array elements
    String strTemp;  // Ditto
    /*      
         Each pass determines the location, minIndex, of the largest value
    */
    
    for (k = 0;  k < len - 1;  k++)
    {    
       //  Find the location, minIndex, of the largest value in row k
       minIndex = k;  // Assume the minimum value is at location k
       for (i = k + 1;  i < len;  i++)
       {
           if(ary[i] != null)
           {
                if (ary[i].compareTo(ary[minIndex]) < 0) 
                    minIndex = i;
           }
       } // End for (i = k + 1;  i < len;  i++)

       // Swap elements in the minIndex and k positions of the arrays
       // First swap the names, the the columns

       strTemp = ary[minIndex];
       ary[minIndex] = ary[k];
       ary[k] = strTemp;

       // Now swap the column entries
       
       /**
          We swap entire rows on one fell swoop. This is much faster and
          takes a lot less code than the code in Version 1 (not shown).
          Note that not all programming languages support this kind of 
          matrix operation.
       */
       oneRow = numbers[minIndex];        // Copy row minIndex
       numbers[minIndex] = numbers[k];    // Copy row k to minIndex
       numbers[k] = oneRow;               // Copy original row minIndex to row k
       for(int fk = 0; fk < outputArray.length ; fk++)
         {
             outputArray[fk] = ary[fk];
         }
     } // End for (k = 0;  k < len - 1;  k++)
    return outputArray;
   } // End selectionSortString
    
    //***************************************************************************
    
    
    /** selectionSortRates
       Sort an integer array from smallest to largest and keep track of
       the original indexes
            
       Spec Reference   Introduced with Assignment 10

       Input
         @param   array An integer array to sort. It is already filled
         @param   index An array that contains the indexes of the original values
                        which are typically index[i] = i + 1 to number them
                        for humans to read easily. That is,
                           index[0] = 1 = the first  element
                           index[1] = 2 = the second element, and so on
         
         
       Process    1. Start at the first element and assume it is the smallest value.
                     Call it the "current element"
                  2. Search the rest of the array and if you find a smaller value,
                     note it
                  3. At the end of the loop, swap the locations of the smallest 
                     value with the current element of the array
                  4. Move to the next element of the array, call it the "current
                     element" and repeat steps 2 and 3 until we reach the next
                     to the last element. Then, the largest element will be in the
                     last location
         
       Output
         @return  None
         
       Note       The specification for this method was changed from the original
                  assignment by adding a parameter of the number of elements to
                  process in the sort. This allows for sorting a 
                  partially-filled array
   */
    public static double[][] selectionSortRates (String[] ary, double[][] numbers, 
                                     int len, int column)
   {
    /*
      The next two lines of code establish an array that can contain
      an entire row of the two-dimnesional 'numbers' array. The first line
      gets the number of columns in one row. We make two assumptions here:
      1. The 'numbers' array is not empty (there is at least one entry). 
      2. The number of columns in the 'numbers' array is the same for all rows
         (i.e., a rectangular matrix). We use this idea in the first line of
         code that follows to access the first entry to get the # of column
         in any row of the matrix
    */
    int nColumns = numbers[0].length;  // # of columns in the 2-dimensional array
    double[] oneRow = new double[nColumns];  // Will hold one row during a swap
    double[][] outputArray = new double[len][6];
    
    int k, i;	 // Indexes into the array to find the minimum value
    int minIndex; // Index of the minimum value in the array
    int aryLen;   // Length of the parameter array
    double temp;  // Temporary storage while swapping array elements
    String strTemp;  // Ditto
    /*      
         Each pass determines the location, minIndex, of the largest value
    */
    
    for (k = 0;  k < len - 1;  k++)
    {    
       //  Find the location, minIndex, of the largest value in row k
       minIndex = k;  // Assume the minimum value is at location k
       for (i = k + 1;  i < len;  i++)
       {
           if(ary[i] != null)
           {
                if (ary[i].compareTo(ary[minIndex]) < 0) 
                    minIndex = i;
           }
       } // End for (i = k + 1;  i < len;  i++)

       // Swap elements in the minIndex and k positions of the arrays
       // First swap the names, the the columns

       strTemp = ary[minIndex];
       ary[minIndex] = ary[k];
       ary[k] = strTemp;

       // Now swap the column entries
       
       /**
          We swap entire rows on one fell swoop. This is much faster and
          takes a lot less code than the code in Version 1 (not shown).
          Note that not all programming languages support this kind of 
          matrix operation.
       */
       oneRow = numbers[minIndex];        // Copy row minIndex
       numbers[minIndex] = numbers[k];    // Copy row k to minIndex
       numbers[k] = oneRow;               // Copy original row minIndex to row k

       
     } // End for (k = 0;  k < len - 1;  k++)
     
     for(int fi = 0; fi < outputArray.length; fi++)
     {
         for(int fk = 0; fk < outputArray[fi].length ; fk++)
         {
             outputArray[fi][fk] = numbers[fi][fk];
         }
     }
    
     return outputArray;
   }
    
    // *******************************************************************

   /** rightPad
       Pad a string on the right
   
       Spec Reference   Introduced along with Assignment 7

       Input
         @param   str      A string to be padded
         @param   width    The width of the resulting formatted number
         @param   padLeft  The string to use for padding on the left
          
       Process    1. Use the padItString method to do the work by supplying
                     an empty string to pad on the right         
       Output
         @return  strPad   The formatted number, padded if necessarray
 
       Note       None
   */    
    
   public static String rightPad(String str, int width, String padLeft)
   {
      String strPad;               // String to be returned

      strPad = padItString(str, width, padLeft, "");
      return strPad;
   } // end rightPad()
   
   public static String padItString(String str, int width, 
                                    String padLeft, String padRight)
   {
      String strPad = str;         // String to be returned, starts as the param
      int charsToPad;              // The number of charactes to pad
   
      // Using the length of the String str, calculate
      // the number of characters to pad on the left
      charsToPad = width - strPad.length();
   
      // Pad str by spaces on the left and/or right so that the
      // resulting length of strPad is 'width' characters
      for (int i = 0;  i < charsToPad;  i++)
      {
         strPad = padLeft + strPad + padRight;
      }
      return strPad;
   } // end padItNumber()
    
    
    // *************************** roundNumber *******************************
   
   // roundNumber - round a valueToRound to placesToRound places  
   
   public static double roundNumber(double valueToRound, int placesToRound)
   {
      double powerOfTen;  // To calculate 10^placesToRound 
                                                  // Example of roundNumber(1.256,2)
      powerOfTen = Math.pow(10,placesToRound);    // 10 ^ 2 = 100
      valueToRound = valueToRound * powerOfTen;   // 1.256 * 100 = 125.6
      valueToRound = Math.round(valueToRound);    // 125.6 rounded = 126
      return valueToRound / powerOfTen;           // 126 / 100 = 1.26
   }
    
    // *******************************************************************

    /** leftPad
       Convert a number to a string and pad it on the left

       Spec Reference   Introduced in Assignment 7

       Input
         @param   number   A double number to be formatted
         @param   width    The width of the formatted number after padding
         @param   mask     The DecimalFormat mask to use
         @param   padding  The string to use for padding, usually a space
         
       Process    1. Convert the number to a string
                  2. Use padItString to finish the job
         
       Output
         @return  strPad   The formatted number, padded if necessary
         
       Note
   */
    
    public static String leftPad (double number, int width, 
                                 String mask, String padding)
   {
         final int DEFAULT_WIDTH = 8; // If 'width' is out of range
         final int MIN_WIDTH = 3;     // Minimum width allowed
         String strPad;               // String to be returned
         int charsToPad;              // The number of charactes to pad

         //  Convert number to a String
         DecimalFormat fmt = new DecimalFormat(mask);
         strPad = fmt.format(number);

         // Use the default width if the width parameter is no good.
         // Using the length of the String str, calculate
         // the number of characters to pad on the left
         if (width < MIN_WIDTH) width = DEFAULT_WIDTH;
         charsToPad = width - strPad.length();

         // Pad str by spaces on the left so that the resulting length 
         // of str is 'width' characters
         for (int i = 0;  i < charsToPad;  i++)
         {
               strPad = padding + strPad;
         }
         return strPad;
   } // end leftPad()
}