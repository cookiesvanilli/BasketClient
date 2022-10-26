package ru.netology;

import java.io.File;
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
          //  Basket.loadFromTxtFile(file);
            Basket.fromJsonFile("basket.json");
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

            basket.addToCart(productNum, productAmount);
            clientLog.log(productNum, productAmount);
        }
        basket.saveText(file);
        basket.toJsonFile("basket.json");

        basket.printCart();

        File fileCSV = new File("log.csv");
        clientLog.exportAsCSV(fileCSV);

        XMLReader xmlReader = new XMLReader();
        xmlReader.xml();
    }
}