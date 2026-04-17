import java.awt.event.*;
import java.sql.*;
import javax.swing.*;

public class UserDashboard extends JFrame implements ActionListener {

    JLabel l1, l2;
    JTextField t1;
    JTextArea ta;
    JButton b1;

    int userId;

    UserDashboard(int id) {
        userId = id;

        setTitle("User Dashboard");
        setSize(500, 400);
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        l1 = new JLabel("Subject:");
        l1.setBounds(50, 50, 100, 30);
        add(l1);

        t1 = new JTextField();
        t1.setBounds(150, 50, 250, 30);
        add(t1);

        l2 = new JLabel("Description:");
        l2.setBounds(50, 100, 100, 30);
        add(l2);

        ta = new JTextArea();
        JScrollPane sp = new JScrollPane(ta);
        sp.setBounds(150, 100, 250, 120);
        add(sp);

        b1 = new JButton("Submit Complaint");
        b1.setBounds(160, 260, 160, 35);
        b1.addActionListener(this);
        add(b1);

        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        try {
            String subject = t1.getText();
            String description = ta.getText();

            Connection con = DBConnection.getConnection();

            String query = "INSERT INTO complaints(user_id, subject, description) VALUES(?,?,?)";
            PreparedStatement pst = con.prepareStatement(query);

            pst.setInt(1, userId);
            pst.setString(2, subject);
            pst.setString(3, description);

            int x = pst.executeUpdate();

            if (x > 0) {
                JOptionPane.showMessageDialog(this, "Complaint Submitted!");
                t1.setText("");
                ta.setText("");
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex);
        }
    }

    public static void main(String[] args) {
        new UserDashboard(1);
    }
}