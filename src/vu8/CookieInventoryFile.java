/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vu8;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import prog24178.labs.objects.CookieInventoryItem;

/**
 *
 * @author Nam Vu
 */
public class CookieInventoryFile extends ArrayList<CookieInventoryItem> {

    public CookieInventoryFile() {
    }

    public CookieInventoryFile(File file) {
        this.loadFromFile(file);
    }

    public CookieInventoryItem find(int flavourId) {
        for (int i = 0; i < super.size(); i++) {
            if (super.get(i).cookie.getId() == flavourId) {
                return super.get(i);
            }
        }
        return null;
    }

    public void loadFromFile(File file) {
        try {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String input = scanner.nextLine();
                String[] splittedInput = input.split("\\|");
                
                int id = Integer.parseInt(splittedInput[0]);
                int qty = Integer.parseInt(splittedInput[1]);
                super.add(new CookieInventoryItem(id, qty));
            }
            scanner.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        catch(NumberFormatException e){
            System.out.println("File data is not valid");
        }
    }

    public void writeToFile(File file) {
        try {
            PrintWriter fw = new PrintWriter(file);
            BufferedWriter bf = new BufferedWriter(fw);
            PrintWriter fileOut = new PrintWriter(bf);
            for (int i = 0; i < super.size(); i++) {
                fileOut.println(super.get(i).toFileString());
            }
            fileOut.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

    }

}
