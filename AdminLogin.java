import java.awt.event.*;
import java.sql.*;
import javax.swing.*;

public class AdminLogin extends JFrame implements ActionListener {

    JLabel l1, l2;
    JTextField t1;
    JPasswordField t2;
    JButton b1;

    AdminLogin() {
        setTitle("Admin Login");
        setSize(400, 250);
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        l1 = new JLabel("Username:");
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
        b1.setBounds(130, 160, 100, 30);
        b1.addActionListener(this);
        add(b1);

        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        try {
            String user = t1.getText();
            String pass = t2.getText();

            Connection con = DBConnection.getConnection();

            String query = "SELECT * FROM admin WHERE username=? AND password=?";
            PreparedStatement pst = con.prepareStatement(query);

            pst.setString(1, user);
            pst.setString(2, pass);

            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                new AdminDashboard();
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Invalid Admin Login!");
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex);
        }
    }

    public static void main(String[] args) {
        new AdminLogin();
    }
}