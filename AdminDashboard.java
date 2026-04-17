import java.awt.event.*;
import java.sql.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class AdminDashboard extends JFrame implements ActionListener {

    JTable table;
    DefaultTableModel model;

    JButton resolveBtn, searchBtn;
    JTextField searchField;
    JComboBox<String> filterBox;

    JLabel totalLbl, pendingLbl, resolvedLbl;

    AdminDashboard() {
        setTitle("Admin Dashboard");
        setSize(950, 550);
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Stats Labels
        totalLbl = new JLabel("Total: 0");
        totalLbl.setBounds(30, 10, 120, 30);
        add(totalLbl);

        pendingLbl = new JLabel("Pending: 0");
        pendingLbl.setBounds(160, 10, 120, 30);
        add(pendingLbl);

        resolvedLbl = new JLabel("Resolved: 0");
        resolvedLbl.setBounds(300, 10, 120, 30);
        add(resolvedLbl);

        // Search
        searchField = new JTextField();
        searchField.setBounds(500, 10, 180, 30);
        add(searchField);

        searchBtn = new JButton("Search");
        searchBtn.setBounds(690, 10, 100, 30);
        searchBtn.addActionListener(this);
        add(searchBtn);

        // Filter
        filterBox = new JComboBox<>(new String[]{
            "All", "Pending", "Resolved"
        });
        filterBox.setBounds(800, 10, 100, 30);
        filterBox.addActionListener(this);
        add(filterBox);

        // Table
        model = new DefaultTableModel();
        table = new JTable(model);

        model.addColumn("ID");
        model.addColumn("User ID");
        model.addColumn("Subject");
        model.addColumn("Description");
        model.addColumn("Status");
        model.addColumn("Date");

        JScrollPane sp = new JScrollPane(table);
        sp.setBounds(20, 60, 890, 350);
        add(sp);

        // Button
        resolveBtn = new JButton("Mark Resolved");
        resolveBtn.setBounds(370, 440, 180, 35);
        resolveBtn.addActionListener(this);
        add(resolveBtn);

        loadData();
        updateStats();

        setVisible(true);
    }

    void clearTable() {
        model.setRowCount(0);
    }

    void loadData() {
        clearTable();

        try {
            Connection con = DBConnection.getConnection();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM complaints");

            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getInt("id"),
                    rs.getInt("user_id"),
                    rs.getString("subject"),
                    rs.getString("description"),
                    rs.getString("status"),
                    rs.getString("created_at")
                });
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e);
        }
    }

    void searchData(String text) {
        clearTable();

        try {
            Connection con = DBConnection.getConnection();

            String q = "SELECT * FROM complaints WHERE subject LIKE ? OR status LIKE ?";
            PreparedStatement pst = con.prepareStatement(q);

            pst.setString(1, "%" + text + "%");
            pst.setString(2, "%" + text + "%");

            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getInt("id"),
                    rs.getInt("user_id"),
                    rs.getString("subject"),
                    rs.getString("description"),
                    rs.getString("status"),
                    rs.getString("created_at")
                });
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e);
        }
    }

    void filterData(String status) {
        if (status.equals("All")) {
            loadData();
            return;
        }

        clearTable();

        try {
            Connection con = DBConnection.getConnection();

            String q = "SELECT * FROM complaints WHERE status=?";
            PreparedStatement pst = con.prepareStatement(q);
            pst.setString(1, status);

            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getInt("id"),
                    rs.getInt("user_id"),
                    rs.getString("subject"),
                    rs.getString("description"),
                    rs.getString("status"),
                    rs.getString("created_at")
                });
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e);
        }
    }

    void updateStats() {
        try {
            Connection con = DBConnection.getConnection();

            Statement st = con.createStatement();

            ResultSet rs1 = st.executeQuery("SELECT COUNT(*) FROM complaints");
            if (rs1.next()) totalLbl.setText("Total: " + rs1.getInt(1));

            ResultSet rs2 = st.executeQuery("SELECT COUNT(*) FROM complaints WHERE status='Pending'");
            if (rs2.next()) pendingLbl.setText("Pending: " + rs2.getInt(1));

            ResultSet rs3 = st.executeQuery("SELECT COUNT(*) FROM complaints WHERE status='Resolved'");
            if (rs3.next()) resolvedLbl.setText("Resolved: " + rs3.getInt(1));

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e);
        }
    }

    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == searchBtn) {
            searchData(searchField.getText());
        }

        if (e.getSource() == filterBox) {
            filterData(filterBox.getSelectedItem().toString());
        }

        if (e.getSource() == resolveBtn) {
            try {
                int row = table.getSelectedRow();

                if (row == -1) {
                    JOptionPane.showMessageDialog(this, "Select complaint first!");
                    return;
                }

                int id = Integer.parseInt(model.getValueAt(row, 0).toString());

                Connection con = DBConnection.getConnection();

                PreparedStatement pst = con.prepareStatement(
                    "UPDATE complaints SET status='Resolved' WHERE id=?"
                );

                pst.setInt(1, id);
                pst.executeUpdate();

                loadData();
                updateStats();

                JOptionPane.showMessageDialog(this, "Status Updated!");

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex);
            }
        }
    }

    public static void main(String[] args) {
        new AdminDashboard();
    }
}