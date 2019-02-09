/*
 * SawitView.java
 */

package sawit;

import java.awt.Image;
import org.jdesktop.application.Action;
import org.jdesktop.application.ResourceMap;
import org.jdesktop.application.SingleFrameApplication;
import org.jdesktop.application.FrameView;
import org.jdesktop.application.TaskMonitor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;
import java.sql.Connection;
import java.util.Arrays;
import javax.imageio.ImageIO;
import javax.swing.Timer;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.HeadlessException;
import java.sql.Connection;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import net.proteanit.sql.DbUtils;
/**
 * The application's main frame.
 */
public class SawitView extends FrameView {

    public SawitView(SingleFrameApplication app) {
        super(app);

        initComponents();
        getData();

        // status bar initialization - message timeout, idle icon and busy animation, etc
        ResourceMap resourceMap = getResourceMap();
        int messageTimeout = resourceMap.getInteger("StatusBar.messageTimeout");
        messageTimer = new Timer(messageTimeout, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                statusMessageLabel.setText("");
            }
        });
        messageTimer.setRepeats(false);
        int busyAnimationRate = resourceMap.getInteger("StatusBar.busyAnimationRate");
        for (int i = 0; i < busyIcons.length; i++) {
            busyIcons[i] = resourceMap.getIcon("StatusBar.busyIcons[" + i + "]");
        }
        busyIconTimer = new Timer(busyAnimationRate, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                busyIconIndex = (busyIconIndex + 1) % busyIcons.length;
                statusAnimationLabel.setIcon(busyIcons[busyIconIndex]);
            }
        });
        idleIcon = resourceMap.getIcon("StatusBar.idleIcon");
        statusAnimationLabel.setIcon(idleIcon);
        progressBar.setVisible(false);

        // connecting action tasks to status bar via TaskMonitor
        TaskMonitor taskMonitor = new TaskMonitor(getApplication().getContext());
        taskMonitor.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                String propertyName = evt.getPropertyName();
                if ("started".equals(propertyName)) {
                    if (!busyIconTimer.isRunning()) {
                        statusAnimationLabel.setIcon(busyIcons[0]);
                        busyIconIndex = 0;
                        busyIconTimer.start();
                    }
                    progressBar.setVisible(true);
                    progressBar.setIndeterminate(true);
                } else if ("done".equals(propertyName)) {
                    busyIconTimer.stop();
                    statusAnimationLabel.setIcon(idleIcon);
                    progressBar.setVisible(false);
                    progressBar.setValue(0);
                } else if ("message".equals(propertyName)) {
                    String text = (String)(evt.getNewValue());
                    statusMessageLabel.setText((text == null) ? "" : text);
                    messageTimer.restart();
                } else if ("progress".equals(propertyName)) {
                    int value = (Integer)(evt.getNewValue());
                    progressBar.setVisible(true);
                    progressBar.setIndeterminate(false);
                    progressBar.setValue(value);
                }
            }
        });
    }
    
    

    @Action
    public void showAboutBox() {
        if (aboutBox == null) {
            JFrame mainFrame = SawitApp.getApplication().getMainFrame();
            aboutBox = new SawitAboutBox(mainFrame);
            aboutBox.setLocationRelativeTo(mainFrame);
        }
        SawitApp.getApplication().show(aboutBox);
    }
    
    @Action
    public void showBox() {
        if (aboutBox == null) {
            JFrame mainFrame = SawitApp.getApplication().getMainFrame();
            aboutBox = new SawitAboutBox(mainFrame);
            aboutBox.setLocationRelativeTo(mainFrame);
        }
        SawitApp.getApplication().show(aboutBox);
    }
    
    @Action
    public  void selectImage1(){
        JFileChooser file = new JFileChooser();
          file.setCurrentDirectory(new File(System.getProperty("user.home")));
          //filter the files
          FileNameExtensionFilter filter = new FileNameExtensionFilter("*.Images", "jpg","gif","png");
          file.addChoosableFileFilter(filter);
          int result = file.showSaveDialog(null);
          
          if(result == JFileChooser.APPROVE_OPTION){
              File selectedFile = file.getSelectedFile();
              String path = selectedFile.getAbsolutePath();
              
              BufferedImage image1 = null;
              File file1 = null;
              
              FunctionSawit fc = new FunctionSawit();
              
              try {
                   file1 = new File(path);
                   et_gambar1.setText(path);
                   
                   
              } catch (Exception e) {
                  System.out.println("Gagal Memilih Gambar "+e.getMessage());
              }
              
              
              gambar1.setIcon(fc.LabeltoImage100R(path));
              
          }
          
           else if(result == JFileChooser.CANCEL_OPTION){
              System.out.println("No File Select");
          }
    }
    
    @Action
    public  void selectImage2(){
        JFileChooser file = new JFileChooser();
          file.setCurrentDirectory(new File(System.getProperty("user.home")));
          //filter the files
          FileNameExtensionFilter filter = new FileNameExtensionFilter("*.Images", "jpg","gif","png");
          file.addChoosableFileFilter(filter);
          int result = file.showSaveDialog(null);
          
          
          
          if(result == JFileChooser.APPROVE_OPTION){
              File selectedFile = file.getSelectedFile();
              String path = selectedFile.getAbsolutePath();
              
              BufferedImage image2 = null;
              File file2 = null;
              
              FunctionSawit fc = new FunctionSawit();
              
              try {
                   file2 = new File(path);
                   et_gambar2.setText(path);
                   
              } catch (Exception e) {
                  System.out.println("Gagal Memilih Gambar "+e.getMessage());
              }
              
              
              gambar2.setIcon(fc.LabeltoImage100R(path));
              
          }
          
           else if(result == JFileChooser.CANCEL_OPTION){
              System.out.println("No File Select");
          }
    }
    
    @Action
    public  void selectImage3(){
        JFileChooser file = new JFileChooser();
          file.setCurrentDirectory(new File(System.getProperty("user.home")));
          //filter the files
          FileNameExtensionFilter filter = new FileNameExtensionFilter("*.Images", "jpg","gif","png");
          file.addChoosableFileFilter(filter);
          int result = file.showSaveDialog(null);
          
          if(result == JFileChooser.APPROVE_OPTION){
              File selectedFile = file.getSelectedFile();
              String path = selectedFile.getAbsolutePath();
              
              BufferedImage image3 = null;
              File file3 = null;
              
              FunctionSawit fc = new FunctionSawit();
              
              try {
                   file3 = new File(path);
                      et_gambar3.setText(path);
                
                   
              } catch (Exception e) {
                  System.out.println("Gagal Memilih Gambar "+e.getMessage());
              }
              
              
              gambar3.setIcon(fc.LabeltoImage100R(path));
              
          }
          
           else if(result == JFileChooser.CANCEL_OPTION){
              System.out.println("No File Select");
          }
    }
    
    @Action
    public  void selectImage4(){
        
        FunctionSawit fc = new FunctionSawit();
        JFileChooser file = new JFileChooser();
          file.setCurrentDirectory(new File(System.getProperty("user.home")));
          //filter the files
          FileNameExtensionFilter filter = new FileNameExtensionFilter("*.Images", "jpg","gif","png");
          file.addChoosableFileFilter(filter);
          int result = file.showSaveDialog(null);
          
          if(result == JFileChooser.APPROVE_OPTION){
              File selectedFile = file.getSelectedFile();
              String path = selectedFile.getAbsolutePath();
              
              BufferedImage image4 = null;
              File file4 = null;
              
              
              try {
                   file4 = new File(path);
                    et_gambar4.setText(path);
                
                   
              } catch (Exception e) {
                  System.out.println("Gagal Memilih Gambar "+e.getMessage());
              }
              
              gambar4.setIcon(fc.LabeltoImage100R(path));
              
          }
          
           else if(result == JFileChooser.CANCEL_OPTION){
              System.out.println("No File Select");
          }
    }
    
    @Action
    public void getHSV(){
        
        BufferedImage image1 = null;
        File file1 = null;
        BufferedImage image2 = null;
        File file2 = null;
        BufferedImage image3 = null;
        File file3 = null;
        BufferedImage image4 = null;
        File file4 = null;
        
        String path1 = et_gambar1.getText();
        String path2 = et_gambar2.getText();
        String path3 = et_gambar3.getText();
        String path4 = et_gambar4.getText();
        
        FunctionSawit fs = new FunctionSawit();
          
          try {
              
              file1 = new File(path1);
              image1 = ImageIO.read(file1);
              fs.setRed1(fs.getRedinArray(image1));
              fs.setGreen1(fs.getGreeninArray(image1));
              fs.setBlue1(fs.getBlueinArray(image1));
              
              file2 = new File(path2);
              image2 = ImageIO.read(file2);
              fs.setRed2(fs.getRedinArray(image2));
              fs.setGreen2(fs.getGreeninArray(image2));
              fs.setBlue2(fs.getBlueinArray(image2));
              
              file3 = new File(path3);
              image3 = ImageIO.read(file3);
              fs.setRed3(fs.getRedinArray(image3));
              fs.setGreen3(fs.getGreeninArray(image3));
              fs.setBlue3(fs.getBlueinArray(image3));
              
              file4 = new File(path4);
              image4 = ImageIO.read(file4);
              fs.setRed4(fs.getRedinArray(image4));
              fs.setGreen4(fs.getGreeninArray(image4));
              fs.setBlue4(fs.getBlueinArray(image4));
              
              //gambar4.setIcon(fs.LabeltoImage100R(path1));
              //fs.printGrid(fs.getRed1());
            
        } catch (Exception e) {
            
              System.out.println("Gagal get dan set rgb"+e.getMessage());
              
        }
          
         
        int width=300,  height=300;
          
        double sumR [][]= new double[width][height];
        double sumG [][]= new double[width][height];
        double sumB [][]= new double[width][height];
        
        int [][]RR1 = fs.getRed1();
        int [][]RR2 = fs.getRed2();
        int [][]RR3 = fs.getRed3();
        int [][]RR4 = fs.getRed4();
        
        int [][]GG1 = fs.getGreen1();
        int [][]GG2 = fs.getGreen2();
        int [][]GG3 = fs.getGreen3();
        int [][]GG4 = fs.getGreen4();
        
        int [][]BB1 = fs.getBlue1();
        int [][]BB2 = fs.getBlue2();
        int [][]BB3 = fs.getBlue3();
        int [][]BB4 = fs.getBlue4();
        
        //JUMLAHKAN DAN BAGI 4
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                sumR[i][j] = fs.pembulatan4Angka((RR1[i][j]+RR2[i][j]+RR3[i][j]+RR4[i][j])/4);
                sumG[i][j] = fs.pembulatan4Angka((GG1[i][j]+GG2[i][j])+(GG3[i][j]+GG4[i][j])/4);
                sumB[i][j] = fs.pembulatan4Angka((BB1[i][j]+BB2[i][j])+(BB3[i][j]+BB4[i][j])/4);
            }
        }
        
        //NORMALISASI NILAI RGB
        double r [][] = new double[width][height];
        double g [][] = new double[width][height];
        double b [][] = new double[width][height]; 
        
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                r[i][j]=fs.pembulatan4Angka(((sumR[i][j])/(sumR[i][j]+sumG[i][j]+sumB[i][j])));
                g[i][j]=fs.pembulatan4Angka(((sumG[i][j])/(sumR[i][j]+sumG[i][j]+sumB[i][j])));
                b[i][j]=fs.pembulatan4Angka(((sumB[i][j])/(sumR[i][j]+sumG[i][j]+sumB[i][j])));
            }
        }
        
        //RGB TO HSV 
        double v [][] = new double[width][height];
        double s [][] = new double[width][height];
        double h [][] = new double[width][height];
        
        
        double v_min [][] = new double[width][height];
        
        double x [] = new double [width*height];
        
        //HITUNG NILAI V
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                double [] tab = {r[i][j],g[i][j],b[i][j]};
                v[i][j]= fs.pembulatan4Angka(Arrays.stream(tab).max().getAsDouble());
                
                v_min[i][j]= fs.pembulatan4Angka(Arrays.stream(tab).min().getAsDouble());
                
                //Hitung Nilai S
                if (v[i][j] == 0){
                    s[i][j] = 0;
                }else{
                    s[i][j] = fs.pembulatan4Angka(1- (v_min[i][j]/v[i][j]));
                }
                
                //hitung nilai H
                if(s[i][j]==0){
                    h[i][j] = 0;
                }
                else if( v[i][j] == r[i][j]){
                    h[i][j] = fs.pembulatan4Angka(60 * (0+((g[i][j]-b[i][j])/(s[i][j]*v[i][j]))));
                }else if (v[i][j] == g[i][j]){
                    h[i][j] = fs.pembulatan4Angka(60 * (2+((b[i][j]-r[i][j])/(s[i][j]*v[i][j]))));
                } else if (v[i][j] == b[i][j]){
                    h[i][j] = fs.pembulatan4Angka(60 * (4+((r[i][j]-g[i][j])/(s[i][j]*v[i][j]))));
                }else {
                    h[i][j] = 0;
                }
            }
        }
        
        //MENCARI NILAI MEAN H
        double sumH = 0; 
        double sumS = 0;
        double sumV = 0;
        
        
        
        for (int i = 0; i < width; i++) {
            
            for (int j = 0; j < height; j++) {
                sumH = fs.pembulatan4Angka(sumH+h[i][j]);
                sumS = fs.pembulatan4Angka(sumS+s[i][j]);
                sumV = fs.pembulatan4Angka(sumV+v[i][j]);
            }
        }
        
        //System.out.println("sumH="+sumH);
        //System.out.println("sumS="+sumS);
        //System.out.println("sumV="+sumV);
        
        double finalH= fs.pembulatan4Angka(sumH/(width*height));
        double finalS= fs.pembulatan4Angka(sumS/(width*height));
        double finalV= fs.pembulatan4Angka(sumV/(width*height));
        
        System.out.println("meanH"+finalH);
        System.out.println("meanS"+finalS);
        System.out.println("meanV"+finalV);
        
        tambahDataSawit(finalH,finalS,finalV,combo_sawit.getName().toString());
        
        
    }
    
    private void getData(){
          try {
        Connection conn =(Connection)Koneksi.koneksiDB();
        java.sql.Statement stm = conn.createStatement();
        java.sql.ResultSet sql = stm.executeQuery("select * from sawit");
        tbl_data.setModel(DbUtils.resultSetToTableModel(sql));
        stm.close();
    }
    catch (Exception e) {
        System.out.print("Gagal Get Data Sawit");
    }
    }
    
    private void tambahDataSawit(double hh , double ss , double vv, String target){
        try {
            String sql = "insert into sawit (H,S,V,Target)values('"+hh+"','"+ss+"','"+vv+"','"+target+"')";
            java.sql.Connection conn = (java.sql.Connection)Koneksi.koneksiDB();
            java.sql.PreparedStatement pst = conn.prepareStatement(sql);
            pst.execute();
            JOptionPane.showMessageDialog(null, "berhasil disimpan");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }

    }
    

    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        mainPanel = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        gambar1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        gambar2 = new javax.swing.JLabel();
        btn_gambar2 = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        gambar3 = new javax.swing.JLabel();
        btn_gambar3 = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        gambar4 = new javax.swing.JLabel();
        btn_gambar4 = new javax.swing.JButton();
        et_gambar1 = new javax.swing.JTextField();
        et_gambar3 = new javax.swing.JTextField();
        et_gambar2 = new javax.swing.JTextField();
        et_gambar4 = new javax.swing.JTextField();
        jPanel9 = new javax.swing.JPanel();
        jPanel14 = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        asd = new javax.swing.JInternalFrame();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbl_random = new javax.swing.JTable();
        btn_gambar5 = new javax.swing.JButton();
        jInternalFrame2 = new javax.swing.JInternalFrame();
        jScrollPane2 = new javax.swing.JScrollPane();
        tbl_data = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jComboBox1 = new javax.swing.JComboBox();
        jLabel7 = new javax.swing.JLabel();
        asd1 = new javax.swing.JInternalFrame();
        jScrollPane3 = new javax.swing.JScrollPane();
        tbl_normalisasi = new javax.swing.JTable();
        combo_sawit = new javax.swing.JComboBox();
        jLabel2 = new javax.swing.JLabel();
        btn_hsv = new javax.swing.JButton();
        menuBar = new javax.swing.JMenuBar();
        javax.swing.JMenu fileMenu = new javax.swing.JMenu();
        javax.swing.JMenuItem exitMenuItem = new javax.swing.JMenuItem();
        javax.swing.JMenu helpMenu = new javax.swing.JMenu();
        javax.swing.JMenuItem aboutMenuItem = new javax.swing.JMenuItem();
        statusPanel = new javax.swing.JPanel();
        javax.swing.JSeparator statusPanelSeparator = new javax.swing.JSeparator();
        statusMessageLabel = new javax.swing.JLabel();
        statusAnimationLabel = new javax.swing.JLabel();
        progressBar = new javax.swing.JProgressBar();

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(sawit.SawitApp.class).getContext().getResourceMap(SawitView.class);
        mainPanel.setBackground(resourceMap.getColor("mainPanel.background")); // NOI18N
        mainPanel.setName("mainPanel"); // NOI18N

        jPanel5.setBackground(resourceMap.getColor("jPanel5.background")); // NOI18N
        jPanel5.setName("jPanel5"); // NOI18N

        jLabel1.setBackground(resourceMap.getColor("jLabel1.background")); // NOI18N
        jLabel1.setFont(resourceMap.getFont("jLabel1.font")); // NOI18N
        jLabel1.setForeground(resourceMap.getColor("jLabel1.foreground")); // NOI18N
        jLabel1.setText(resourceMap.getString("jLabel1.text")); // NOI18N
        jLabel1.setName("jLabel1"); // NOI18N

        jPanel1.setBackground(resourceMap.getColor("jPanel1.background")); // NOI18N
        jPanel1.setName("jPanel1"); // NOI18N

        gambar1.setText(resourceMap.getString("gambar1.text")); // NOI18N
        gambar1.setName("gambar1"); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(gambar1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(gambar1, javax.swing.GroupLayout.DEFAULT_SIZE, 102, Short.MAX_VALUE)
        );

        jPanel2.setBackground(resourceMap.getColor("jPanel2.background")); // NOI18N
        jPanel2.setName("jPanel2"); // NOI18N

        gambar2.setText(resourceMap.getString("gambar2.text")); // NOI18N
        gambar2.setName("gambar2"); // NOI18N

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(gambar2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(gambar2, javax.swing.GroupLayout.DEFAULT_SIZE, 102, Short.MAX_VALUE)
        );

        javax.swing.ActionMap actionMap = org.jdesktop.application.Application.getInstance(sawit.SawitApp.class).getContext().getActionMap(SawitView.class, this);
        btn_gambar2.setAction(actionMap.get("selectImage2")); // NOI18N
        btn_gambar2.setText(resourceMap.getString("btn_gambar2.text")); // NOI18N
        btn_gambar2.setName("btn_gambar2"); // NOI18N

        jPanel4.setBackground(resourceMap.getColor("jPanel4.background")); // NOI18N
        jPanel4.setName("jPanel4"); // NOI18N

        gambar3.setText(resourceMap.getString("gambar3.text")); // NOI18N
        gambar3.setName("gambar3"); // NOI18N

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(gambar3, javax.swing.GroupLayout.DEFAULT_SIZE, 143, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(gambar3, javax.swing.GroupLayout.DEFAULT_SIZE, 102, Short.MAX_VALUE)
        );

        btn_gambar3.setAction(actionMap.get("selectImage3")); // NOI18N
        btn_gambar3.setText(resourceMap.getString("btn_gambar3.text")); // NOI18N
        btn_gambar3.setName("btn_gambar3"); // NOI18N

        jPanel3.setBackground(resourceMap.getColor("jPanel3.background")); // NOI18N
        jPanel3.setName("jPanel3"); // NOI18N

        gambar4.setText(resourceMap.getString("gambar4.text")); // NOI18N
        gambar4.setName("gambar4"); // NOI18N

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(gambar4, javax.swing.GroupLayout.DEFAULT_SIZE, 142, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(gambar4, javax.swing.GroupLayout.DEFAULT_SIZE, 102, Short.MAX_VALUE)
        );

        btn_gambar4.setAction(actionMap.get("selectImage4")); // NOI18N
        btn_gambar4.setText(resourceMap.getString("btn_gambar4.text")); // NOI18N
        btn_gambar4.setName("btn_gambar4"); // NOI18N

        et_gambar1.setEditable(false);
        et_gambar1.setText(resourceMap.getString("et_gambar1.text")); // NOI18N
        et_gambar1.setName("et_gambar1"); // NOI18N

        et_gambar3.setEditable(false);
        et_gambar3.setText(resourceMap.getString("et_gambar3.text")); // NOI18N
        et_gambar3.setName("et_gambar3"); // NOI18N

        et_gambar2.setEditable(false);
        et_gambar2.setText(resourceMap.getString("et_gambar2.text")); // NOI18N
        et_gambar2.setName("et_gambar2"); // NOI18N

        et_gambar4.setEditable(false);
        et_gambar4.setText(resourceMap.getString("et_gambar4.text")); // NOI18N
        et_gambar4.setAutoscrolls(false);
        et_gambar4.setName("et_gambar4"); // NOI18N

        jPanel9.setBackground(resourceMap.getColor("jPanel9.background")); // NOI18N
        jPanel9.setName("jPanel9"); // NOI18N

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 644, Short.MAX_VALUE)
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        jPanel14.setBackground(resourceMap.getColor("jPanel14.background")); // NOI18N
        jPanel14.setName("jPanel14"); // NOI18N

        jPanel8.setBackground(resourceMap.getColor("jPanel8.background")); // NOI18N
        jPanel8.setName("jPanel8"); // NOI18N

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 504, Short.MAX_VALUE)
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 428, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel14Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15))
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        asd.setTitle(resourceMap.getString("asd.title")); // NOI18N
        asd.setName("asd"); // NOI18N
        asd.setVisible(true);

        jScrollPane1.setName("jScrollPane1"); // NOI18N

        tbl_random.setBorder(javax.swing.BorderFactory.createCompoundBorder());
        tbl_random.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "ID SAWIT ", "H", "S", "V", "TARGET"
            }
        ));
        tbl_random.setName("tbl_random"); // NOI18N
        jScrollPane1.setViewportView(tbl_random);
        if (tbl_random.getColumnModel().getColumnCount() > 0) {
            tbl_random.getColumnModel().getColumn(0).setHeaderValue(resourceMap.getString("tbl_random.columnModel.title0")); // NOI18N
            tbl_random.getColumnModel().getColumn(1).setHeaderValue(resourceMap.getString("tbl_random.columnModel.title1")); // NOI18N
            tbl_random.getColumnModel().getColumn(2).setHeaderValue(resourceMap.getString("tbl_random.columnModel.title2")); // NOI18N
            tbl_random.getColumnModel().getColumn(3).setHeaderValue(resourceMap.getString("tbl_random.columnModel.title3")); // NOI18N
            tbl_random.getColumnModel().getColumn(4).setHeaderValue(resourceMap.getString("tbl_random.columnModel.title4")); // NOI18N
        }

        javax.swing.GroupLayout asdLayout = new javax.swing.GroupLayout(asd.getContentPane());
        asd.getContentPane().setLayout(asdLayout);
        asdLayout.setHorizontalGroup(
            asdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 824, Short.MAX_VALUE)
        );
        asdLayout.setVerticalGroup(
            asdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        btn_gambar5.setAction(actionMap.get("selectImage1")); // NOI18N
        btn_gambar5.setText(resourceMap.getString("btn_gambar5.text")); // NOI18N
        btn_gambar5.setName("btn_gambar5"); // NOI18N
        btn_gambar5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_gambar5ActionPerformed(evt);
            }
        });

        jInternalFrame2.setTitle(resourceMap.getString("jInternalFrame2.title")); // NOI18N
        jInternalFrame2.setName("jInternalFrame2"); // NOI18N
        jInternalFrame2.setVisible(true);

        jScrollPane2.setName("jScrollPane2"); // NOI18N

        tbl_data.setBorder(javax.swing.BorderFactory.createCompoundBorder());
        tbl_data.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tbl_data.setName("tbl_data"); // NOI18N
        jScrollPane2.setViewportView(tbl_data);
        if (tbl_data.getColumnModel().getColumnCount() > 0) {
            tbl_data.getColumnModel().getColumn(0).setHeaderValue(resourceMap.getString("tbl_data.columnModel.title0")); // NOI18N
            tbl_data.getColumnModel().getColumn(1).setHeaderValue(resourceMap.getString("tbl_data.columnModel.title1")); // NOI18N
            tbl_data.getColumnModel().getColumn(2).setHeaderValue(resourceMap.getString("tbl_data.columnModel.title2")); // NOI18N
            tbl_data.getColumnModel().getColumn(3).setResizable(false);
            tbl_data.getColumnModel().getColumn(3).setHeaderValue(resourceMap.getString("tbl_data.columnModel.title3")); // NOI18N
        }

        javax.swing.GroupLayout jInternalFrame2Layout = new javax.swing.GroupLayout(jInternalFrame2.getContentPane());
        jInternalFrame2.getContentPane().setLayout(jInternalFrame2Layout);
        jInternalFrame2Layout.setHorizontalGroup(
            jInternalFrame2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2)
        );
        jInternalFrame2Layout.setVerticalGroup(
            jInternalFrame2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jInternalFrame2Layout.createSequentialGroup()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 21, Short.MAX_VALUE))
        );

        jButton1.setText(resourceMap.getString("jButton1.text")); // NOI18N
        jButton1.setName("jButton1"); // NOI18N

        jButton2.setText(resourceMap.getString("jButton2.text")); // NOI18N
        jButton2.setName("jButton2"); // NOI18N

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "70:30", "80:20", "90:10" }));
        jComboBox1.setName("jComboBox1"); // NOI18N

        jLabel7.setBackground(resourceMap.getColor("jLabel7.background")); // NOI18N
        jLabel7.setFont(resourceMap.getFont("jLabel7.font")); // NOI18N
        jLabel7.setForeground(resourceMap.getColor("jLabel7.foreground")); // NOI18N
        jLabel7.setText(resourceMap.getString("jLabel7.text")); // NOI18N
        jLabel7.setName("jLabel7"); // NOI18N

        asd1.setTitle(resourceMap.getString("asd1.title")); // NOI18N
        asd1.setName("asd1"); // NOI18N
        asd1.setVisible(true);

        jScrollPane3.setName("jScrollPane3"); // NOI18N

        tbl_normalisasi.setBorder(javax.swing.BorderFactory.createCompoundBorder());
        tbl_normalisasi.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "ID SAWIT ", "H", "S", "V", "TARGET"
            }
        ));
        tbl_normalisasi.setName("tbl_normalisasi"); // NOI18N
        jScrollPane3.setViewportView(tbl_normalisasi);
        if (tbl_normalisasi.getColumnModel().getColumnCount() > 0) {
            tbl_normalisasi.getColumnModel().getColumn(0).setHeaderValue(resourceMap.getString("tbl_random.columnModel.title0")); // NOI18N
            tbl_normalisasi.getColumnModel().getColumn(1).setHeaderValue(resourceMap.getString("tbl_random.columnModel.title1")); // NOI18N
            tbl_normalisasi.getColumnModel().getColumn(2).setHeaderValue(resourceMap.getString("tbl_random.columnModel.title2")); // NOI18N
            tbl_normalisasi.getColumnModel().getColumn(3).setHeaderValue(resourceMap.getString("tbl_random.columnModel.title3")); // NOI18N
            tbl_normalisasi.getColumnModel().getColumn(4).setHeaderValue(resourceMap.getString("tbl_random.columnModel.title4")); // NOI18N
        }

        javax.swing.GroupLayout asd1Layout = new javax.swing.GroupLayout(asd1.getContentPane());
        asd1.getContentPane().setLayout(asd1Layout);
        asd1Layout.setHorizontalGroup(
            asd1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 824, Short.MAX_VALUE)
        );
        asd1Layout.setVerticalGroup(
            asd1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        combo_sawit.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Sawit Mentah", "Sawit Masak", "Sawit Busuk" }));
        combo_sawit.setName("combo_sawit"); // NOI18N

        jLabel2.setBackground(resourceMap.getColor("jLabel2.background")); // NOI18N
        jLabel2.setFont(resourceMap.getFont("jLabel2.font")); // NOI18N
        jLabel2.setForeground(resourceMap.getColor("jLabel2.foreground")); // NOI18N
        jLabel2.setText(resourceMap.getString("jLabel2.text")); // NOI18N
        jLabel2.setName("jLabel2"); // NOI18N

        btn_hsv.setAction(actionMap.get("getHSV")); // NOI18N
        btn_hsv.setText(resourceMap.getString("btn_hsv.text")); // NOI18N
        btn_hsv.setName("btn_hsv"); // NOI18N

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(btn_hsv, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(jInternalFrame2, javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel5Layout.createSequentialGroup()
                                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGap(18, 18, 18)
                                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(et_gambar4)
                                        .addComponent(et_gambar1, javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(et_gambar2)
                                        .addComponent(et_gambar3, javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                                            .addGap(0, 0, Short.MAX_VALUE)
                                            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(btn_gambar2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(btn_gambar3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(btn_gambar4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(btn_gambar5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                                .addComponent(jPanel9, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(jPanel5Layout.createSequentialGroup()
                                    .addComponent(jLabel2)
                                    .addGap(18, 18, 18)
                                    .addComponent(combo_sawit, 0, 445, Short.MAX_VALUE))))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(280, 280, 280)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addComponent(jLabel7)
                                .addGap(61, 61, 61)
                                .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(273, 273, 273)
                                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                        .addGap(168, 168, 168)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(asd1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(asd, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(402, 402, 402)
                .addComponent(jPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel5Layout.createSequentialGroup()
                                        .addGap(36, 36, 36)
                                        .addComponent(et_gambar1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(btn_gambar5)))
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel5Layout.createSequentialGroup()
                                        .addGap(24, 24, 24)
                                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel5Layout.createSequentialGroup()
                                        .addGap(40, 40, 40)
                                        .addComponent(et_gambar2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(btn_gambar2)))
                                .addGap(28, 28, 28)
                                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addGap(36, 36, 36)
                                .addComponent(asd1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(asd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(298, 298, 298)
                        .addComponent(et_gambar3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btn_gambar3)
                        .addGap(51, 51, 51)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addComponent(et_gambar4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btn_gambar4)))
                        .addGap(40, 40, 40)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(combo_sawit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(btn_hsv)))
                .addGap(24, 24, 24)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jInternalFrame2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(282, 282, 282)
                        .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(13, 13, 13))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton1)
                            .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7))
                        .addGap(18, 18, 18)
                        .addComponent(jButton2)
                        .addGap(65, 65, 65))))
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addComponent(jPanel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        asd.getAccessibleContext().setAccessibleName(resourceMap.getString("asd.AccessibleContext.accessibleName")); // NOI18N

        javax.swing.GroupLayout mainPanelLayout = new javax.swing.GroupLayout(mainPanel);
        mainPanel.setLayout(mainPanelLayout);
        mainPanelLayout.setHorizontalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, mainPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        mainPanelLayout.setVerticalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainPanelLayout.createSequentialGroup()
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, 1095, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 24, Short.MAX_VALUE))
        );

        menuBar.setName("menuBar"); // NOI18N

        fileMenu.setText(resourceMap.getString("fileMenu.text")); // NOI18N
        fileMenu.setName("fileMenu"); // NOI18N

        exitMenuItem.setAction(actionMap.get("quit")); // NOI18N
        exitMenuItem.setName("exitMenuItem"); // NOI18N
        fileMenu.add(exitMenuItem);

        menuBar.add(fileMenu);

        helpMenu.setText(resourceMap.getString("helpMenu.text")); // NOI18N
        helpMenu.setName("helpMenu"); // NOI18N

        aboutMenuItem.setAction(actionMap.get("showAboutBox")); // NOI18N
        aboutMenuItem.setName("aboutMenuItem"); // NOI18N
        helpMenu.add(aboutMenuItem);

        menuBar.add(helpMenu);

        statusPanel.setName("statusPanel"); // NOI18N

        statusPanelSeparator.setName("statusPanelSeparator"); // NOI18N

        statusMessageLabel.setName("statusMessageLabel"); // NOI18N

        statusAnimationLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        statusAnimationLabel.setName("statusAnimationLabel"); // NOI18N

        progressBar.setName("progressBar"); // NOI18N

        javax.swing.GroupLayout statusPanelLayout = new javax.swing.GroupLayout(statusPanel);
        statusPanel.setLayout(statusPanelLayout);
        statusPanelLayout.setHorizontalGroup(
            statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(statusPanelSeparator, javax.swing.GroupLayout.DEFAULT_SIZE, 2633, Short.MAX_VALUE)
            .addGroup(statusPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(statusMessageLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 2458, Short.MAX_VALUE)
                .addComponent(progressBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(statusAnimationLabel)
                .addContainerGap())
        );
        statusPanelLayout.setVerticalGroup(
            statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(statusPanelLayout.createSequentialGroup()
                .addComponent(statusPanelSeparator, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(statusMessageLabel)
                    .addComponent(statusAnimationLabel)
                    .addComponent(progressBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(3, 3, 3))
        );

        setComponent(mainPanel);
        setMenuBar(menuBar);
        setStatusBar(statusPanel);
    }// </editor-fold>//GEN-END:initComponents

    private void btn_gambar5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_gambar5ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_gambar5ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JInternalFrame asd;
    private javax.swing.JInternalFrame asd1;
    private javax.swing.JButton btn_gambar2;
    private javax.swing.JButton btn_gambar3;
    private javax.swing.JButton btn_gambar4;
    private javax.swing.JButton btn_gambar5;
    private javax.swing.JButton btn_hsv;
    private javax.swing.JComboBox combo_sawit;
    private javax.swing.JTextField et_gambar1;
    private javax.swing.JTextField et_gambar2;
    private javax.swing.JTextField et_gambar3;
    private javax.swing.JTextField et_gambar4;
    private javax.swing.JLabel gambar1;
    private javax.swing.JLabel gambar2;
    private javax.swing.JLabel gambar3;
    private javax.swing.JLabel gambar4;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JComboBox jComboBox1;
    private javax.swing.JInternalFrame jInternalFrame2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JProgressBar progressBar;
    private javax.swing.JLabel statusAnimationLabel;
    private javax.swing.JLabel statusMessageLabel;
    private javax.swing.JPanel statusPanel;
    private javax.swing.JTable tbl_data;
    private javax.swing.JTable tbl_normalisasi;
    private javax.swing.JTable tbl_random;
    // End of variables declaration//GEN-END:variables

    private final Timer messageTimer;
    private final Timer busyIconTimer;
    private final Icon idleIcon;
    private final Icon[] busyIcons = new Icon[15];
    private int busyIconIndex = 0;

    private JDialog aboutBox;
}
