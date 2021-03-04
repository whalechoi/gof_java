import java.util.Scanner;

public class LoginForm {
    private UserDAO dao = new UserDAO();
    private String userName = "";
    private String userPassword = "";

    public void init() {
        System.out.println("Welcome to GoF(JAVA version) ex1!");
        this.display();
    }

    public void display() {
        Scanner s = new Scanner(System.in);
        System.out.println("Please login!");
        System.out.print("User Name:");
        if (s.hasNext()) {
            userName = s.next();
        }
        System.out.print("User Password:");
        if (s.hasNext()) {
            userPassword = s.next();
        }
        s.close();
        this.validate();
    }

    public void validate() {
        try {
            if (dao.findUser(userName, userPassword)) {
                System.out.println("Login success!");
            } else {
                System.out.println("Login failed!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Bye!");
    }
}
