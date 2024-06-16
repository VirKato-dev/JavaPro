package virkato.otus.hw10.cli;

import org.springframework.stereotype.Component;
import virkato.otus.hw10.model.Cart;
import virkato.otus.hw10.model.Product;
import virkato.otus.hw10.service.CartService;
import virkato.otus.hw10.service.ProductService;

import java.util.Scanner;


@Component
public class App {

    private final ProductService productService;
    private final Scanner scanner = new Scanner(System.in);

    private final CartService cartService;
    private Cart cart = null;

    public App(ProductService productService, CartService cartService) {
        this.productService = productService;
        this.cartService = cartService;
    }


    public void run() {
        initRepository();

        if (cart == null) {
            cart = cartService.createCart();
        }

        System.out.println("Welcome to Otus shop!");
        showMenu();

        while (true) {
            String input = scanner.nextLine().trim();
            if (input.length() == 1) {
                if (input.equalsIgnoreCase("Q")) {
                    System.out.println("Good bye!");
                    break;
                }
                if (input.equalsIgnoreCase("M")) {
                    showMenu();
                }
                if (input.equalsIgnoreCase("C")) {
                    showCart();
                }
            } else {
                String[] choice = input.split("\s+");
                int productId;
                try {
                    if (choice[0].equalsIgnoreCase("buy")) {
                        productId = Integer.parseInt(choice[1]);
                        cartService.addProductToCart(cart, productId);
                    } else if (choice[0].equalsIgnoreCase("remove")) {
                        productId = Integer.parseInt(choice[1]);
                        cartService.removeProductFromCart(cart, productId);
                    } else {
                        throw new Exception();
                    }
                    System.out.println("Done");
                } catch (Exception e) {
                    System.out.println("Invalid choice!");
                }
                showPromptShort();
            }
        }
    }


    private void initRepository() {
        productService.create(new Product("CocaCola", 10d));
        productService.create(new Product("Fanta", 10d));
        productService.create(new Product("Sprite", 10d));
        productService.create(new Product("Donat", 15d));
        productService.create(new Product("Burger", 30d));
        productService.create(new Product("Hot dog", 25d));
        productService.create(new Product("Frying potato", 20d));
        productService.create(new Product("Pizza", 35d));
        productService.create(new Product("Chips", 5d));
        productService.create(new Product("Snickers", 8d));
    }


    private void showMenu() {
        System.out.println("-".repeat(10) + "\nMenu:");
        productService.getAll().forEach(System.out::println);
        showPrompt();
    }


    private void showCart() {
        System.out.println(cart);
        showPrompt();
    }


    private void showPrompt() {
        System.out.println("-".repeat(10));
        System.out.println("Enter M for watching menu!");
        System.out.println("Enter C for watching your cart!");
        System.out.println("Enter Q for exit!");
        System.out.println("Enter your choice 'buy' or 'remove' + number of product.");
        showPromptShort();
    }

    private void showPromptShort() {
        System.out.print("--> ");
    }
}
