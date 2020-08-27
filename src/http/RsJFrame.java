/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package http;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.RowFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.protocol.HTTP;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author ASUS
 */
public class RsJFrame extends javax.swing.JFrame {

    List<Object[]> list = new ArrayList<>();
    DefaultTableModel model = new DefaultTableModel();
    int index = 0;
    HttpURLConnection con;
    HttpClient client;
    int page = 0;
    int limit = 4;

    /**
     * Creates new form RsJFrame
     */
    public RsJFrame() {

        initComponents();
        setLocationRelativeTo(null);
        cboClazz();
        try {
            this.display(page, limit);
        } catch (IOException ex) {
            Logger.getLogger(RsJFrame.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JSONException ex) {
            Logger.getLogger(RsJFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    void display(int page, int limit) throws MalformedURLException, IOException, JSONException {
        String url = "https://serverstudent.herokuapp.com/";
        HttpURLConnection con = (HttpURLConnection) new URL(url).openConnection();
        int responCode = con.getResponseCode();
        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputline;
        StringBuffer response = new StringBuffer();
        while ((inputline = in.readLine()) != null) {
            response.append(inputline);
        }
        in.close();
        JSONArray myresponse = new JSONArray(response.toString());
        model = (DefaultTableModel) tblRespone.getModel();
        model.setRowCount(0);

        for (int i = 0; i < myresponse.length(); i++) {
            String massv = myresponse.getJSONObject(i).getString("massv");
            String name = myresponse.getJSONObject(i).getString("name");
            int age = myresponse.getJSONObject(i).getInt("age");
            JSONObject json = new JSONObject(myresponse.getJSONObject(i).get("clazz").toString());
            String idClazz = json.getString("id");
            String nameClazz = json.getString("name");
            Vector row = new Vector();
            row.add(massv);
            row.add(name);
            row.add(age);
            row.add(nameClazz);
            if (i >= page && i <= limit) {
                model.addRow(row);
            }
        }
        boolean insertable = false;
        boolean page1 = this.page > 0;
        boolean limit1 = this.limit < myresponse.length();

        lblPage.setEnabled(!insertable && page1);
        lblLimit.setEnabled(!insertable && limit1);
    }

    void setStatus(boolean insertable) {
        boolean first = this.index > 0;
        boolean last = this.index < tblRespone.getRowCount() - 1;
        btnBegin.setEnabled(!insertable && first);
        btnPre.setEnabled(!insertable && first);
        btnNext.setEnabled(!insertable && last);
        btnEnd.setEnabled(!insertable && last);
    }

    void cboClazzName(String name) {
        try {
            String nameClazz = name.replace(" ", "%20");
            String url = "https://serverstudent.herokuapp.com/findClazzByName/" + nameClazz;
            HttpURLConnection con = (HttpURLConnection) new URL(url).openConnection();
            int responCode = con.getResponseCode();
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputline;
            StringBuffer response = new StringBuffer();
            while ((inputline = in.readLine()) != null) {
                response.append(inputline);
            }
            in.close();
            JSONObject myresponse = new JSONObject(response.toString());
            DefaultComboBoxModel model = (DefaultComboBoxModel) cboClazz.getModel();
            model.removeAllElements();
            String a = myresponse.toString();
            if (a.contains(myresponse.toString())) {
                model.addElement(myresponse.getString("id"));
            }

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    void cboClazz() {
        try {
            String url = "https://serverstudent.herokuapp.com/getAllClazz";
            HttpURLConnection con = (HttpURLConnection) new URL(url).openConnection();
            int responCode = con.getResponseCode();
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputline;
            StringBuffer response = new StringBuffer();
            while ((inputline = in.readLine()) != null) {
                response.append(inputline);
            }
            in.close();
            JSONArray myresponse = new JSONArray(response.toString());
            DefaultComboBoxModel model = (DefaultComboBoxModel) cboClazz.getModel();
            model.removeAllElements();
            for (int i = 0; i < myresponse.length(); i++) {
                model.addElement(myresponse.getJSONObject(i).getString("id"));
            }

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    void findDataId() {
        try {
            String id = (String) tblRespone.getValueAt(this.index, 0);
            String url = "https://serverstudent.herokuapp.com/findStudent" + id;
            HttpURLConnection con = (HttpURLConnection) new URL(url).openConnection();
            int responCode = con.getResponseCode();
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputline;
            StringBuffer response = new StringBuffer();
            while ((inputline = in.readLine()) != null) {
                response.append(inputline);
            }
            in.close();
            System.out.println(response.toString());
            JSONObject myresponse = new JSONObject(response.toString());
            String massv = myresponse.getString("massv");
            String name = myresponse.getString("name");
            int age = myresponse.getInt("age");
            JSONObject json = new JSONObject(myresponse.get("clazz").toString());
            String nameClazz = json.getString("name");
            txtMasv.setText(massv);
            txtName.setText(name);
            txtAge.setText(String.valueOf(age));
            this.cboClazzName(nameClazz);
            if (response.toString() != null) {
                this.setStatus(false);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    void deleteData() {
        Thread t = new Thread() {
            public void run() {
                HttpClient client = new DefaultHttpClient();
                HttpConnectionParams.setConnectionTimeout(client.getParams(), 1000); //Timeout Limit
                HttpResponse response = null;
                JSONObject json = new JSONObject();
                String massv = txtMasv.getText();
                try {
                    String url = "https://serverstudent.herokuapp.com/student" + massv;
                    HttpDelete delete = new HttpDelete(url);

                    response = client.execute(delete);
                    int responseCode = response.getStatusLine().getStatusCode();
                    String statusPhrase = response.getStatusLine().getReasonPhrase();
                    response.getEntity().getContent().close();

                    System.out.println("response " + responseCode);
                    System.out.println("status " + statusPhrase);

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        };
        t.start();
    }

    void addData() {
        Thread t = new Thread() {
            public void run() {
                HttpClient client = new DefaultHttpClient();
                HttpConnectionParams.setConnectionTimeout(client.getParams(), 1000); //Timeout Limit
                HttpResponse response;
                JSONObject json = new JSONObject();

                try {
                    HttpPost post = new HttpPost("https://serverstudent.herokuapp.com/student");
                    Map<String, Object> map = new HashMap<>();
                    map.put("id", cboClazz.getSelectedItem());
                    map.put("name", "");
                    System.out.println(map.toString());
                    json.put("massv", txtMasv.getText());
                    json.put("name", txtName.getText());
                    json.put("age", Integer.valueOf(txtAge.getText()));
                    json.put("clazz", map);
                    System.out.println(json.toString());
                    StringEntity se = new StringEntity(json.toString());
                    post.setHeader(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
                    post.setEntity(se);
                    response = client.execute(post);
                    /*Checking response */
                    if (response != null) {
                        InputStream in = response.getEntity().getContent(); //Get the data in the entity
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        };
        t.start();
    }

    void update() {
        Thread t = new Thread() {
            public void run() {
                HttpClient client = new DefaultHttpClient();
                HttpConnectionParams.setConnectionTimeout(client.getParams(), 1000); //Timeout Limit
                HttpResponse response;
                JSONObject json = new JSONObject();
                try {
                    HttpPut post = new HttpPut("https://serverstudent.herokuapp.com/student");
                    Map<String, Object> map = new HashMap<>();
                    map.put("id", cboClazz.getSelectedItem());
                    map.put("name", "");
                    System.out.println(map.toString());
                    json.put("massv", txtMasv.getText());
                    json.put("name", txtName.getText());
                    json.put("age", Integer.valueOf(txtAge.getText()));
                    json.put("clazz", map);
                    System.out.println(json.toString());
                    StringEntity se = new StringEntity(json.toString());
                    post.setHeader(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
                    post.setEntity(se);
                    response = client.execute(post);

                    /*Checking response */
                    if (response != null) {
                        InputStream in = response.getEntity().getContent(); //Get the data in the entity
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        };
        t.start();
    }

    void check() {
        if (page == 0) {
            lblPage.setEnabled(false);
        }
    }

    void clearForm() {
        txtMasv.setText("");
        txtName.setText("");
        txtAge.setText("");
        txtMasv.setEditable(true);
    }
    
    void xuatfile(){
        DefaultTableModel modelb = (DefaultTableModel) tblRespone.getModel();
        JFileChooser excelFile = new JFileChooser();
        excelFile.setDialogTitle("Save As");
        
        FileNameExtensionFilter fnef = new FileNameExtensionFilter("EXCEL FILES","xls","xlsx","xlsm");
        excelFile.setFileFilter(fnef);
        
        int excelChooser = excelFile.showSaveDialog(null);
        if(excelChooser == JFileChooser.APPROVE_OPTION){
            FileOutputStream excelOut = null;
            BufferedOutputStream excelBuf = null;
            XSSFWorkbook excelJTable = null;
            try {
                excelJTable = new XSSFWorkbook();
                XSSFSheet excelSheet = excelJTable.createSheet("DS_SinhVien");
                for(int i = 0;i< modelb.getRowCount();i++){
                    XSSFRow excelRow = excelSheet.createRow(i);
                    
                    for(int j = 0; j < modelb.getColumnCount();j++){
                        XSSFCell excelCell = excelRow.createCell(j);
   
                        excelCell.setCellValue(modelb.getValueAt(i,j).toString());
                    }
                }   
                excelOut = new FileOutputStream(excelFile.getSelectedFile()+".xlsx");
                excelBuf = new BufferedOutputStream(excelOut);
                excelJTable.write(excelBuf);
                JOptionPane.showMessageDialog(this, "Xuất file thành công");
            } catch (FileNotFoundException ex) {
                Logger.getLogger(RsJFrame.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(RsJFrame.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                try {
                    if(excelBuf != null){
                    excelBuf.close();
                    }
                    if(excelOut != null){
                    excelOut.close();
                    }
                    
                    if(excelJTable != null){
                    excelJTable.close();
                    }
                } catch (IOException ex) {
                    Logger.getLogger(RsJFrame.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }   
        
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        tblRespone = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        btnAdd = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        txtName = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtMasv = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtAge = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        btnClear = new javax.swing.JButton();
        btnDisplay = new javax.swing.JButton();
        txtSearch = new javax.swing.JTextField();
        btnBegin = new javax.swing.JButton();
        btnPre = new javax.swing.JButton();
        btnNext = new javax.swing.JButton();
        btnEnd = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        cboClazz = new javax.swing.JComboBox<>();
        jLabel6 = new javax.swing.JLabel();
        lblPage = new javax.swing.JLabel();
        lblLimit = new javax.swing.JLabel();
        btnXuatFile = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("StudentData");
        setBackground(new java.awt.Color(153, 255, 153));
        setUndecorated(true);
        setResizable(false);

        tblRespone.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        tblRespone.setForeground(new java.awt.Color(0, 0, 153));
        tblRespone.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "MASSV", "NAME", "AGE", "CLAZZ"
            }
        ));
        tblRespone.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        tblRespone.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblResponeMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblRespone);

        jLabel1.setFont(new java.awt.Font("Snap ITC", 1, 28)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 0, 51));
        jLabel1.setText("STUDENT DATA");

        btnAdd.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        btnAdd.setForeground(new java.awt.Color(0, 51, 255));
        btnAdd.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/Create.png"))); // NOI18N
        btnAdd.setText("Add");
        btnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 0, 255));
        jLabel2.setText("MaSV :");

        txtName.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        txtName.setForeground(new java.awt.Color(0, 51, 255));
        txtName.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(51, 153, 255)));

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 0, 255));
        jLabel3.setText("Name :");

        txtMasv.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        txtMasv.setForeground(new java.awt.Color(0, 51, 255));
        txtMasv.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(51, 153, 255)));

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 0, 255));
        jLabel4.setText("Age :");

        txtAge.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        txtAge.setForeground(new java.awt.Color(0, 51, 255));
        txtAge.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(51, 153, 255)));

        jButton1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jButton1.setForeground(new java.awt.Color(0, 51, 255));
        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/Add.png"))); // NOI18N
        jButton1.setText("Update");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jButton2.setForeground(new java.awt.Color(0, 51, 255));
        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/Delete.png"))); // NOI18N
        jButton2.setText("Delete");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        btnClear.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        btnClear.setForeground(new java.awt.Color(0, 51, 255));
        btnClear.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/No.png"))); // NOI18N
        btnClear.setText("Clear");
        btnClear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClearActionPerformed(evt);
            }
        });

        btnDisplay.setText("Display");
        btnDisplay.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDisplayActionPerformed(evt);
            }
        });

        txtSearch.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        txtSearch.setForeground(new java.awt.Color(51, 51, 255));
        txtSearch.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(51, 153, 255)));
        txtSearch.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtSearchKeyPressed(evt);
            }
        });

        btnBegin.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/Up.png"))); // NOI18N
        btnBegin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBeginActionPerformed(evt);
            }
        });

        btnPre.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/Left.png"))); // NOI18N
        btnPre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPreActionPerformed(evt);
            }
        });

        btnNext.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/Right.png"))); // NOI18N
        btnNext.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNextActionPerformed(evt);
            }
        });

        btnEnd.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/Down.png"))); // NOI18N
        btnEnd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEndActionPerformed(evt);
            }
        });

        jButton7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/Search.png"))); // NOI18N
        jButton7.setText("Search");
        jButton7.setEnabled(false);
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        jPanel2.setBackground(new java.awt.Color(102, 204, 255));

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("X");
        jLabel5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel5MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel5)
                .addContainerGap(20, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel5)
                .addContainerGap())
        );

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(0, 0, 255));
        jLabel6.setText("Clazz :");

        lblPage.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lblPage.setText("<< Trước");
        lblPage.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblPageMouseClicked(evt);
            }
        });

        lblLimit.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lblLimit.setText("Kế tiếp >>");
        lblLimit.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblLimitMouseClicked(evt);
            }
        });

        btnXuatFile.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/Text.png"))); // NOI18N
        btnXuatFile.setText("Xuất File");
        btnXuatFile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXuatFileActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 326, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(195, 195, 195)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(layout.createSequentialGroup()
                .addGap(60, 60, 60)
                .addComponent(btnBegin, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnPre, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnNext, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnEnd, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 64, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 211, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton7))
                .addGap(45, 45, 45))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton1)
                        .addGap(18, 18, 18)
                        .addComponent(jButton2)
                        .addGap(18, 18, 18)
                        .addComponent(btnClear, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnDisplay))
                    .addComponent(jScrollPane1))
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(43, 43, 43)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addComponent(jLabel3)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(txtName)
                        .addComponent(txtAge)
                        .addComponent(txtMasv, javax.swing.GroupLayout.PREFERRED_SIZE, 554, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(cboClazz, javax.swing.GroupLayout.PREFERRED_SIZE, 242, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblPage)
                .addGap(60, 60, 60)
                .addComponent(lblLimit)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnXuatFile)
                .addGap(46, 46, 46))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(26, 26, 26)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel3)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel4))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(txtMasv, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(txtName, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(txtAge, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(18, 18, 18)
                        .addComponent(cboClazz, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(btnPre)
                        .addComponent(btnNext, javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnBegin)
                            .addComponent(btnEnd)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jButton7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblPage)
                    .addComponent(lblLimit)
                    .addComponent(btnXuatFile))
                .addGap(24, 24, 24)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAdd)
                    .addComponent(jButton1)
                    .addComponent(jButton2)
                    .addComponent(btnClear)
                    .addComponent(btnDisplay))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnDisplayActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDisplayActionPerformed
        // TODO add your handling code here:
        try {
            this.display(page, limit);
        } catch (IOException ex) {
            Logger.getLogger(RsJFrame.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JSONException ex) {
            Logger.getLogger(RsJFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnDisplayActionPerformed

    private void btnClearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClearActionPerformed
        // TODO add your handling code here:
        this.clearForm();

    }//GEN-LAST:event_btnClearActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        this.deleteData();

        try {
            Thread.sleep(2000);
            this.display(page,limit);
        } catch (IOException ex) {
            Logger.getLogger(RsJFrame.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JSONException ex) {
            Logger.getLogger(RsJFrame.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            Logger.getLogger(RsJFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.clearForm();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        this.update();
        try {
            Thread.sleep(2000);
            this.display(page,limit);
        } catch (IOException ex) {
            Logger.getLogger(RsJFrame.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JSONException ex) {
            Logger.getLogger(RsJFrame.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            Logger.getLogger(RsJFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.clearForm();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
        // TODO add your handling code here:
        this.addData();
        try {
            Thread.sleep(2000);
            this.display(page,limit);
        } catch (IOException ex) {
            Logger.getLogger(RsJFrame.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JSONException ex) {
            Logger.getLogger(RsJFrame.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            Logger.getLogger(RsJFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.clearForm();
    }//GEN-LAST:event_btnAddActionPerformed

    private void tblResponeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblResponeMouseClicked
        // TODO add your handling code here:
//        if (evt.getClickCount() == 2) {

        index = tblRespone.getSelectedRow();
        txtMasv.setText(tblRespone.getValueAt(index, 0).toString());
        txtName.setText(tblRespone.getValueAt(index, 1).toString());
        txtAge.setText(tblRespone.getValueAt(index, 2).toString());
        this.cboClazzName(tblRespone.getValueAt(index, 3).toString());
        txtMasv.setEditable(false);
//        }
    }//GEN-LAST:event_tblResponeMouseClicked

    private void txtSearchKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSearchKeyPressed
        // TODO add your handling code here:
        DefaultTableModel table = (DefaultTableModel) tblRespone.getModel();
        String search = txtSearch.getText().trim();
        TableRowSorter<DefaultTableModel> tr = new TableRowSorter<DefaultTableModel>(table);
        tblRespone.setRowSorter(tr);
        tr.setRowFilter(RowFilter.regexFilter(search));
    }//GEN-LAST:event_txtSearchKeyPressed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        // TODO add your handling code here:
        //        this.findDataId();
    }//GEN-LAST:event_jButton7ActionPerformed

    private void btnEndActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEndActionPerformed
        // TODO add your handling code here:
        this.index = tblRespone.getRowCount() - 1;
        this.findDataId();
        tblRespone.setRowSelectionInterval(index, index);
    }//GEN-LAST:event_btnEndActionPerformed

    private void btnNextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNextActionPerformed
        // TODO add your handling code here:
        this.index++;
        this.findDataId();
        tblRespone.setRowSelectionInterval(index, index);
    }//GEN-LAST:event_btnNextActionPerformed

    private void btnPreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPreActionPerformed
        // TODO add your handling code here:
        this.index--;
        this.findDataId();
        tblRespone.setRowSelectionInterval(index, index);
    }//GEN-LAST:event_btnPreActionPerformed

    private void btnBeginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBeginActionPerformed
        // TODO add your handling code here:
        this.index = 0;
        this.findDataId();
        tblRespone.setRowSelectionInterval(index, index);
    }//GEN-LAST:event_btnBeginActionPerformed

    private void jLabel5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel5MouseClicked
        // TODO add your handling code here:
        System.exit(0);
    }//GEN-LAST:event_jLabel5MouseClicked

    private void lblPageMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblPageMouseClicked
        // TODO add your handling code here:

        page -= 5;
        limit -= 5;
        try {
            display(page, limit);
        } catch (IOException ex) {
            Logger.getLogger(RsJFrame.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JSONException ex) {
            Logger.getLogger(RsJFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_lblPageMouseClicked

    private void lblLimitMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblLimitMouseClicked
        // TODO add your handling code here:
        page += 5;
        limit += 5;
        try {
            display(page, limit);
        } catch (IOException ex) {
            Logger.getLogger(RsJFrame.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JSONException ex) {
            Logger.getLogger(RsJFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_lblLimitMouseClicked

    private void btnXuatFileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXuatFileActionPerformed
        // TODO add your handling code here:
        this.xuatfile();
    }//GEN-LAST:event_btnXuatFileActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(RsJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(RsJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(RsJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(RsJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new RsJFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnBegin;
    private javax.swing.JButton btnClear;
    private javax.swing.JButton btnDisplay;
    private javax.swing.JButton btnEnd;
    private javax.swing.JButton btnNext;
    private javax.swing.JButton btnPre;
    private javax.swing.JButton btnXuatFile;
    private javax.swing.JComboBox<String> cboClazz;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton7;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblLimit;
    private javax.swing.JLabel lblPage;
    private javax.swing.JTable tblRespone;
    private javax.swing.JTextField txtAge;
    private javax.swing.JTextField txtMasv;
    private javax.swing.JTextField txtName;
    private javax.swing.JTextField txtSearch;
    // End of variables declaration//GEN-END:variables
}
