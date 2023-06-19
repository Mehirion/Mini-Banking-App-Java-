package banking;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class bank {
    public static void main(String args[]) throws IOException {
        BufferedReader sc = new BufferedReader(new InputStreamReader(System.in));
        String name = "";
        int pass_code;
        int ac_no;
        int ch;

        while (true) {
            System.out.println("\n->|| Welcome to myBank ||<- \n");
            System.out.println("1)Create account");
            System.out.println("2)Log-in to an existing account");
            try {
                System.out.print("\n Enter input:");
                ch = Integer.parseInt(sc.readLine());

                switch (ch) {
                    case 1:
                        try {
                            System.out.print("Enter username: ");
                            name = sc.readLine();
                            System.out.print("Enter password: ");
                            pass_code = Integer.parseInt(sc.readLine());
                            if (bankManagement.createAccount(name, pass_code)) {
                                System.out.println("MSG : Account has been created successfully!");
                            } else {
                                System.out.println("ERR : Enter valid data!");
                            }
                        } catch (Exception e) {
                            System.out.println("ERR : Enter valid data!");
                        }
                        break;

                    case 2:
                        try {
                            System.out.print("Enter username: ");
                            name = sc.readLine();
                            System.out.print("Enter password: ");
                            pass_code = Integer.parseInt(sc.readLine());

                            if (bankManagement.loginAccount(name, pass_code)) {
                                System.out.println("MSG : Logout successful!\n");
                            } else {
                                System.out.print("ERR: Login failed");
                            }
                        } catch (Exception e) {
                            System.out.print("ERR: Enter valid data");
                        }
                        break;

                    default:
                        System.out.print("Invalid entry\n");
                }
                if (ch == 5) {
                    System.out.println("Exited successfully\n\n Thank you");
                    break; // Dodano break, aby wyjść z pętli while
                }
            } catch (Exception e) {
                System.out.println("Enter valid entry");
            }
        }

        sc.close(); // Zamknięcie strumienia na końcu programu
    }
}