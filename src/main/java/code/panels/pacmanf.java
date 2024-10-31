package code.panels;

import code.instructions.PacmanInstructions;
import raven.popup.GlassPanePopup;

import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Collections;
import java.util.Comparator;
import java.util.Vector;


public class pacmanf extends javax.swing.JFrame
{

    public pacmanf()
    {
        GlassPanePopup.install(this);
        initComponents();
        loadTableData();
    }

    private void initComponents()
    {
        jPanel1 = new javax.swing.JPanel();
        jButton3 = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        model = new javax.swing.JTable();
        jButton2 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 100, Short.MAX_VALUE)
        );

        jButton3.setBackground(new java.awt.Color(0, 0, 0));
        jButton3.setIcon(new javax.swing.ImageIcon("src/main/java/images/left arrow.png")); // NOI18N
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel2.setBackground(new java.awt.Color(0, 0, 0));

        jLabel1.setIcon(new javax.swing.ImageIcon("src/main/java/images/pacman1.gif")); // NOI18N

        jButton1.setBackground(new java.awt.Color(153, 153, 153));
        jButton1.setFont(new java.awt.Font("Segoe UI Semilight", 3, 24)); // NOI18N
        jButton1.setForeground(new java.awt.Color(0, 51, 51));
        jButton1.setText("PLAY");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
                jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                .addContainerGap(250, Short.MAX_VALUE)
                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 516, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(56, 56, 56)
                                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(23, 23, 23))
        );
        jPanel2Layout.setVerticalGroup(
                jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(51, 51, 51))
                        .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 251, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
        );

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel2.setText("LeaderBoard");

        model.setFont(new java.awt.Font("Segoe UI Emoji", 1, 18)); // NOI18N
        model.setModel(new javax.swing.table.DefaultTableModel(
                new Object [][] {
                        {},
                        {},
                        {},
                        {},
                        {}
                },
                new String [] {

                }
        ));
        model.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(model);

        jButton2.setFont(new java.awt.Font("Segoe UI Variable", 1, 24)); // NOI18N
        jButton2.setText("Refresh Table");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton4.setBackground(new java.awt.Color(0, 0, 0));
        jButton4.setIcon(new javax.swing.ImageIcon("src/main/java/images/left arrow.png")); // NOI18N
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addGap(19, 19, 19)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                        .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 960, Short.MAX_VALUE)))
                                        .addGroup(layout.createSequentialGroup()
                                                .addContainerGap()
                                                .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 223, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(26, 26, 26)
                                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(32, 32, 32)
                                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 47, Short.MAX_VALUE)
                                .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap())
        );

        pack();
    }

    private void loadTableData()
    {
        Vector<Vector<Object>> data = new Vector<>();
        Vector<String> columnNames = new Vector<>();
        columnNames.add("Sr No");  // Add Serial Number column
        columnNames.add("Username");
        columnNames.add("Score");

        String SUrl = "jdbc:MYSQL://localhost:3306/pacman";
        String SUser = "root";
        String SPass = "";

        try
        {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(SUrl, SUser, SPass);
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM game1");

            while(rs.next())
            {
                Vector<Object> row = new Vector<>();
                row.add(0); // Placeholder for Sr No, will be set after sorting
                row.add(rs.getString("Player"));
                row.add(rs.getInt("Score"));
                data.add(row);
            }

            // Sort in descending order (highest scores first)
            Collections.sort(data, new Comparator<Vector<Object>>()
            {
                @Override
                public int compare(Vector<Object> row1, Vector<Object> row2)
                {
                    Integer score1 = (Integer) row1.get(2); // Index changed to 2 because of Sr No column
                    Integer score2 = (Integer) row2.get(2);
                    return score2.compareTo(score1);
                }
            });

            // Add serial numbers (1 to 5)
            for (int i = 0; i < Math.min(data.size(), 5); i++)
            {
                data.get(i).set(0, i + 1);
            }

            // Remove extra rows beyond top 5
            while (data.size() > 5)
            {
                data.remove(5);
            }

            DefaultTableModel tableModel = new DefaultTableModel(data, columnNames)
            {
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            };

            model.setModel(tableModel);

            // Center align all columns
            DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
            centerRenderer.setHorizontalAlignment(javax.swing.JLabel.CENTER);
            for (int i = 0; i < model.getColumnCount(); i++) {
                model.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
            }

            // Set column widths
            model.getColumnModel().getColumn(0).setPreferredWidth(70);  // Sr No column
            model.getColumnModel().getColumn(1).setPreferredWidth(150); // Player column
            model.getColumnModel().getColumn(2).setPreferredWidth(100); // Score column

            // Center align column headers
            ((DefaultTableCellRenderer) model.getTableHeader().getDefaultRenderer())
                    .setHorizontalAlignment(javax.swing.JLabel.CENTER);

            con.close();
        }
        catch(Exception ex)
        {
            System.out.println("Error: " + ex.getMessage());
        }
    }

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {
        PacmanInstructions instructions = new PacmanInstructions();
        setVisible(false);
        instructions.setVisible(true);
        instructions.setLocationRelativeTo(null);
    }

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {
        loadTableData();
    }

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {

    }

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {
        setVisible(false);
    }



    public static void main(String args[])
    {

        java.awt.EventQueue.invokeLater(new Runnable()
        {
            public void run()
            {
                new pacmanf().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable model;
    // End of variables declaration
}
