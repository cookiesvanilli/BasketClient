package ru.netology;

import org.json.JSONObject;
import org.json.JSONWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
        Scanner scan = new Scanner(System.in);
        String[] productName = {"Bread", "Apples", "Milk"};
        int[] prices = {100, 200, 300};
        ClientLog clientLog = new ClientLog();

        File file = new File("basket.txt");
        Basket basket = new Basket(productName, prices);

        if (file.exists()) {
            Basket.loadFromTxtFile(file);
        } else {
            basket.printAllProducts();
        }

        while (true) {
            System.out.println("Select the product and quantity or enter `end`");
            String input = scan.nextLine();
            if ("end".equals(input)) {
                break;
            }

            String[] productAndCount = input.split(" ");
            int productNum;
            try {
                productNum = Integer.parseInt(productAndCount[0]) - 1;
            } catch (NumberFormatException e) {
                System.out.println("Number format!!!");
                continue;
            }

            int productAmount;
            try {
                productAmount = Integer.parseInt(productAndCount[1]);
            } catch (NumberFormatException e) {
                System.out.println("Number format!!!");
                continue;
            }

            if (productNum > productName.length || productNum < 0) {
                System.out.println("This product is not exist!!!");
                continue;
            }
            clientLog.log(productNum, productAmount);
            basket.addToCart(productNum, productAmount);
        }
        basket.saveText(file);
        basket.toJsonFile();

        basket.printCart();

        clientLog.exportAsCSV(clientLog.file);
    }
}