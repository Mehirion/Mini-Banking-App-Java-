package banking;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;
public class bankManagement {
    private static final int NULL = 0;
    static Connection con = connection.getConnection();
    static String sql = "";
    public static boolean createAccount(String name, int passCode)
    {
        try {
        if (name == "" || passCode == NULL) {
            System.out.println("All field required");
            return false;
        }
        Statement st = con.createStatement();
        sql = "INSERT INTO customer(cname,balance,pass_code) values('" + name + "',1000," + passCode + ")";

        if (st.executeUpdate(sql) == 1) {
                System.out.println(name + ", You can now log-in!");
                return true;
        }

        }
        catch (SQLIntegrityConstraintViolationException e) {
            System.out.println("Username not available");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    public static boolean loginAccount(String name, int passCode) {
        try {
            if (name == "" || passCode == NULL) {
                System.out.println("All fields are required");
                return false;
            }
            sql = "SELECT * FROM customer WHERE cname='" + name + "' AND pass_code=" + passCode;
            PreparedStatement st = con.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            BufferedReader sc = new BufferedReader(new InputStreamReader(System.in));

            if (rs.next()) {
                int ch = 5;
                int amt = 0;
                int senderAc = rs.getInt("ac_no");
                ;
                int receiveAc;
                while (true) {
                    try {
                        System.out.println("Hello, " + rs.getString("cname"));
                        System.out.println("1) Transfer Money");
                        System.out.println("2) View Balance");
                        System.out.println("5) LogOut");

                        System.out.println("Enter Choice");
                        ch = Integer.parseInt(sc.readLine());
                        if (ch == 1) {
                            System.out.print("Enter Receiver A/c No:");
                            receiveAc = Integer.parseInt(sc.readLine());
                            System.out.print("Enter Amount:");
                            amt = Integer.parseInt(sc.readLine());

                            if (bankManagement.transferMoney(senderAc, receiveAc, amt)) {
                                System.out.println("MSG : Money sent successfully!\n");
                            }
                            else {
                                System.out.println("ERR : Transfer failed!\n");
                            }
                        } else if (ch == 2) {
                            bankManagement.getBalance(senderAc);
                        } else if (ch == 5) {
                            break;
                        } else {
                            System.out.println("ERR : Enter valid input\n");
                        }
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            else {
                return false;
            }
            return true;
        }
        catch (SQLIntegrityConstraintViolationException e) {
            System.out.println("Username not available");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    public static void getBalance(int acNo) {
        try {
            sql = "SELECT * FROM customer WHERE ac_no=" +acNo;
            PreparedStatement st = con.prepareStatement(sql);
            ResultSet rs = st.executeQuery(sql);
            System.out.println("-----------------------------------------------------------");
            System.out.printf("%12s %10s %10s\n", "Account no", "Name", "Balance");

            while (rs.next()) {
                System.out.printf("%12d %10s %10d.00\n",
                        rs.getInt("ac_no"),
                        rs.getString("cname"),
                        rs.getInt("balance"));
            }
            System.out.println("-----------------------------------------------------------\n");
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    public static boolean transferMoney (int sender_ac, int receiver_ac, int amount)
        throws SQLException {
            try {
                con.setAutoCommit(false);
                sql = "SELECT * FROM customer WHERE ac_no=" + sender_ac;
                PreparedStatement ps = con.prepareStatement(sql);
                ResultSet rs = ps.executeQuery();

                if (rs.next()) {
                    if (rs.getInt("balance") < amount){
                        System.out.println("Insufficient balance!");
                        return false;
                    }
                }
                Statement st = con.createStatement();
                con.setSavepoint();

                sql = "UPDATE customer SET balance=balance" + amount + "WHERE ac_no=" + sender_ac;
                if (st.executeUpdate(sql) == 1) {
                    System.out.println("Amount debited");
                }

                sql = "UPDATE customer SET balance=balance" + amount + "WHERE ac_no=" + receiver_ac;
                st.executeUpdate(sql);

                con.commit();
                return true;
            } catch (SQLException e) {
                e.printStackTrace();
                try {
                    con.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        return false;
    }
}
