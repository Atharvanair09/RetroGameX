package code.panels;

import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Collections;
import java.util.Comparator;
import java.util.Vector;

public class flappybirdf extends javax.swing.JFrame
{

    public flappybirdf()
    {
        initComponents();
    }

    private void initComponents()
    {
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jButton3 = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jButton2 = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(0, 153, 204));

        jLabel2.setIcon(new javax.swing.ImageIcon("src/main/java/images/flappy-bird-game.gif")); // NOI18N

        jButton3.setBackground(new java.awt.Color(153, 153, 153));
        jButton3.setFont(new java.awt.Font("Segoe UI Semilight", 3, 24)); // NOI18N
        jButton3.setForeground(new java.awt.Color(0, 51, 51));
        jButton3.setText("PLAY");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 190, Short.MAX_VALUE)
                                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(15, 15, 15))
        );
        jPanel1Layout.setVerticalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addContainerGap(138, Short.MAX_VALUE)
                                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(16, 16, 16))
        );

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel3.setText("LeaderBoard");

        jTable1.setFont(new java.awt.Font("Segoe UI Emoji", 1, 18)); // NOI18N
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
                new Object [][] {

                },
                new String [] {
                        "Sr No", "Username", "Score"
                }
        ) {
            Class[] types = new Class [] {
                    java.lang.Integer.class, java.lang.String.class, java.lang.Integer.class
            };
            boolean[] canEdit = new boolean [] {
                    false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable1.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(jTable1);

        jButton2.setFont(new java.awt.Font("Segoe UI Variable", 1, 24)); // NOI18N
        jButton2.setText("Refresh Table");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel4.setText("LeaderBoard");

        jButton1.setBackground(new java.awt.Color(0, 0, 0));
        jButton1.setIcon(new javax.swing.ImageIcon("src/main/java/images/left arrow.png")); // NOI18N
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 960, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 960, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(16, 16, 16))
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(layout.createSequentialGroup()
                                        .addGap(18, 18, 18)
                                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addContainerGap(801, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(29, 29, 29)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(37, 37, 37)
                                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 39, Short.MAX_VALUE)
                                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap())
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(layout.createSequentialGroup()
                                        .addGap(177, 177, 177)
                                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addContainerGap(402, Short.MAX_VALUE)))
        );

        pack();
    }

    private void loadTableData()
    {
        Vector<Vector<Object>> data = new Vector<>();
        Vector<String> columnNames = new Vector<>();
        columnNames.add("Sr No");
        columnNames.add("Username");
        columnNames.add("Score");

        String SUrl = "jdbc:MYSQL://localhost:3306/pacman";  // Assuming same database
        String SUser = "root";
        String SPass = "";

        try
        {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(SUrl, SUser, SPass);
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM game2");

            while(rs.next())
            {
                Vector<Object> row = new Vector<>();
                row.add(0);  // Placeholder for Sr No
                row.add(rs.getString("Player"));
                row.add(rs.getInt("Score"));
                data.add(row);
            }

            // Sort in descending order (highest scores first)
            Collections.sort(data, new Comparator<Vector<Object>>()
            {
                @Override
                public int compare(Vector<Object> row1, Vector<Object> row2) {
                    Integer score1 = (Integer) row1.get(2);
                    Integer score2 = (Integer) row2.get(2);
                    return score2.compareTo(score1);
                }
            });

            // Add serial numbers (1 to 5) and limit to top 5
            for (int i = 0; i < Math.min(data.size(), 5); i++) {
                data.get(i).set(0, i + 1);
            }

            // Remove extra rows beyond top 5
            while (data.size() > 5) {
                data.remove(5);
            }

            DefaultTableModel tableModel = new DefaultTableModel(data, columnNames)
            {
                @Override
                public Class<?> getColumnClass(int column)
                {
                    if (column == 0) return Integer.class;  // Sr No
                    if (column == 2) return Integer.class;  // Score
                    return String.class;                    // Username
                }

                @Override
                public boolean isCellEditable(int row, int column)
                {
                    return false;
                }
            };

            jTable1.setModel(tableModel);

            // Center align all columns
            DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
            centerRenderer.setHorizontalAlignment(javax.swing.JLabel.CENTER);
            for (int i = 0; i < jTable1.getColumnCount(); i++)
            {
                jTable1.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
            }

            // Set column widths
            jTable1.getColumnModel().getColumn(0).setPreferredWidth(70);   // Sr No
            jTable1.getColumnModel().getColumn(1).setPreferredWidth(150);  // Username
            jTable1.getColumnModel().getColumn(2).setPreferredWidth(100);  // Score

            // Center align column headers
            ((DefaultTableCellRenderer) jTable1.getTableHeader().getDefaultRenderer())
                    .setHorizontalAlignment(javax.swing.JLabel.CENTER);

            con.close();
        }
        catch(Exception ex)
        {
            System.out.println("Error: " + ex.getMessage());
        }
    }

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {

    }

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt)
    {
        loadTableData();
    }

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {
        setVisible(false);
    }

    public static void main(String args[])
    {

        java.awt.EventQueue.invokeLater(new Runnable()
        {
            public void run()
            {
                new flappybirdf().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    // End of variables declaration
}