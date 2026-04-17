import java.awt.event.*;
import java.sql.*;
import javax.swing.*;

public class Login extends JFrame implements ActionListener {

    JLabel l1, l2;
    JTextField t1;
    JPasswordField t2;
    JButton b1, b2;

    Login() {
        setTitle("User Login");
        setSize(400, 250);
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        l1 = new JLabel("Email:");
        l1.setBounds(50, 50, 100, 30);
        add(l1);

        t1 = new JTextField();
        t1.setBounds(150, 50, 180, 30);
        add(t1);

        l2 = new JLabel("Password:");
        l2.setBounds(50, 100, 100, 30);
        add(l2);

        t2 = new JPasswordField();
        t2.setBounds(150, 100, 180, 30);
        add(t2);

        b1 = new JButton("Login");
        b1.setBounds(70, 160, 100, 30);
        b1.addActionListener(this);
        add(b1);

        b2 = new JButton("Register");
        b2.setBounds(200, 160, 100, 30);
        b2.addActionListener(this);
        add(b2);

        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == b1) {
            try {
                String email = t1.getText();
                String password = t2.getText();

                Connection con = DBConnection.getConnection();

                String query = "SELECT * FROM users WHERE email=? AND password=?";
                PreparedStatement pst = con.prepareStatement(query);

                pst.setString(1, email);
                pst.setString(2, password);

                ResultSet rs = pst.executeQuery();

                if (rs.next()) {
                    int id = rs.getInt("id");
                      new UserDashboard(id);
                      dispose();
                     } else {
                    JOptionPane.showMessageDialog(this, "Invalid Email or Password!");
                }

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex);
            }
        }

        if (e.getSource() == b2) {
            new Register();
        }
    }

    public static void main(String[] args) {
        new Login();
    }
}