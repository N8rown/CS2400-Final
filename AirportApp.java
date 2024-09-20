//
// Name: Brown, Nathan 
// Project: 5
// Due: 12/08/2023
// Course: cs-2400-02-f23
//
// Description:
// An implementation of a grapht ADT to map airports along with an alorgithm that finds the shortest path between said airports.
//

import java.io.File;
import java.util.Scanner;
import java.io.FileNotFoundException;
import java.util.Iterator;

public class AirportApp 
{
    public static void main (String[] args)
    {
        String key;
        String data;
        String temp;
        String port1;
        String port2;
        int distance;
        DictionaryInterface<String, String> airports  = new Dictionary<String, String>();
        GraphInterface<String> distances  = new DirectedGraph<String>();
        StackInterface<String> pathStack = new Stack<>();
        File airportFile = new File ("airports.csv");
        File distanceFile = new File ("distances.csv");
        Scanner inputFile1;
        Scanner inputFile2;
        try 
        {
            inputFile1 = new Scanner(airportFile);
            inputFile1.useDelimiter(",|$");
        } 
        catch (FileNotFoundException e) 
        {
            throw new RuntimeException(e);
        }
        while (inputFile1.hasNext()) //while there is another word in constitution
        {
            key = inputFile1.next(); 
            data = inputFile1.nextLine();
            data = data.substring(1); //gets rid of comma at start of string
            airports.add(key, data);
            distances.addVertex(key);
        }
        inputFile1.close();
        
        try 
        {
            inputFile2 = new Scanner(distanceFile);
            inputFile2.useDelimiter(",|$");
        } 
        catch (FileNotFoundException e) 
        {
            throw new RuntimeException(e);
        }
        while (inputFile2.hasNext() ) //while there is another word in constitution
        {
            temp = inputFile2.nextLine();
            port1 = temp.substring(0,3);
            port2 = temp.substring(4,7); 
            distance = Integer.parseInt(temp.substring(8));
            distances.addEdge(port1, port2, distance);
        }
        inputFile2.close();
        

        Scanner scan = new Scanner(System.in);
        System.out.println("Airports v0.1 by N. Brown");
        System.out.println();
        System.out.print("Command? ");
        String playerInput = scan.next().toUpperCase();
        while (!playerInput.equals("E"))
        {
            if (playerInput.equals("H"))
            {
                System.out.println("Q Query the airport information by entering the airport code");
                System.out.println("D Find the minimum distance between two airports.");
                System.out.println("H Display this message");
                System.out.println("E Exit.");
                
            }
            else if (playerInput.equals("Q"))
            {
                System.out.print("Airport code? ");
                playerInput = scan.next().toUpperCase();
                if (airports.getValue(playerInput) != null)
                    System.out.println(airports.getValue(playerInput));
                else
                    System.out.println("Airport code unknown");
            }
            else if (playerInput.equals("D"))
            {
                System.out.print("Airport codes from to? ");
                port1 = scan.next().toUpperCase();
                port2 = scan.next().toUpperCase();
                if (airports.getValue(port1) == null || airports.getValue(port2) == null)
                    System.out.println("Airport code unknown");
                else {
                    double cheapDistance = distances.getCheapestPath(port1, port2, pathStack);
                    if (cheapDistance <0)
                        System.out.println("Airports not connected");
                    else
                    {
                        System.out.println("The minimum distance between " + airports.getValue(port1) + " and " + airports.getValue(port2) + 
                                            " is " + (int)cheapDistance + " through the route: ");
                        while(pathStack.isEmpty() == false)
                        {
                            System.out.println(airports.getValue(pathStack.pop()));
                        }
                    }
                }

            }
            else if (playerInput.equals("E"))
            {}
            else
            {
                System.out.println("Invalid command");
            }
            System.out.print("Command? ");
            playerInput = scan.next().toUpperCase();
        }
        scan.close();
    }
}
