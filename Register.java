import java.awt.event.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import javax.swing.*;

public class Register extends JFrame implements ActionListener {

    JLabel l1, l2, l3;
    JTextField t1, t2;
    JPasswordField t3;
    JButton b1;

    Register() {
        setTitle("User Registration");
        setSize(400, 300);
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        l1 = new JLabel("Name:");
        l1.setBounds(50, 50, 100, 30);
        add(l1);

        t1 = new JTextField();
        t1.setBounds(150, 50, 180, 30);
        add(t1);

        l2 = new JLabel("Email:");
        l2.setBounds(50, 100, 100, 30);
        add(l2);

        t2 = new JTextField();
        t2.setBounds(150, 100, 180, 30);
        add(t2);

        l3 = new JLabel("Password:");
        l3.setBounds(50, 150, 100, 30);
        add(l3);

        t3 = new JPasswordField();
        t3.setBounds(150, 150, 180, 30);
        add(t3);

        b1 = new JButton("Register");
        b1.setBounds(130, 210, 120, 35);
        b1.addActionListener(this);
        add(b1);

        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        try {
            String name = t1.getText();
            String email = t2.getText();
            String password = t3.getText();

            Connection con = DBConnection.getConnection();

            String query = "INSERT INTO users(name,email,password) VALUES(?,?,?)";
            PreparedStatement pst = con.prepareStatement(query);

            pst.setString(1, name);
            pst.setString(2, email);
            pst.setString(3, password);

            int x = pst.executeUpdate();

            if (x > 0) {
                JOptionPane.showMessageDialog(this, "Registered Successfully!");
            } else {
                JOptionPane.showMessageDialog(this, "Registration Failed!");
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex);
        }
    }

    public static void main(String[] args) {
        new Register();
    }
}