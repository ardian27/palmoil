/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sawit;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import net.proteanit.sql.DbUtils;
/**
 *
 * @author LENOVO
 */
public class FunctionSawit {
    
    
    int [][] red1, red2, red3, red4;
    int [][] green1, green2, green3, green4;
    int [][] blue1, blue2, blue3, blue4;
    int width=300, height=300;
    
    int [][] hsv;

    int [][] r;
    int [][] g;
    int [][] b;
    
    
    double [] d1,d2,d3;
    
    
    double datapelatihan[][];
    String datapelatihan_t[];
    double datapelatihan_random [][];
    String datapelatihan_random_t[];
    
    public FunctionSawit(){
    }
    
   
    public ImageIcon LabeltoImage100R(String imagepath)
    {
        
        ImageIcon MyImage  = new ImageIcon(imagepath);
        Image img  = MyImage.getImage();
        Image newImage = img.getScaledInstance(100,100,Image.SCALE_SMOOTH);
        ImageIcon image = new ImageIcon(newImage);
        
        //System.out.println("path gambar"+imagepath);
        
        //Mengambil nilai RGB dari gambar
        //getRGBinArray(imagepath);
        
        
        return image;
    }
    
    public int [][] getRedinArray(BufferedImage image)
    {
        int [][] r = new int[300][300];
        
        try {
            
        
            int w = image.getWidth();
            int h = image.getHeight();
            
            
            for (int i = 0; i < h; i++) {
                for (int j = 0; j < w; j++) {
                    //get pixel
                    int p = image.getRGB(i,j);
                     //get red
                    int rr = (p>>16) & 0xff;
                    r[i][j]= rr;
                }
            }   
            

        } catch (Exception e) {
            System.out.println("Kesalahan Pengambilan Gambar R "+e.getMessage());
        }
        
        
        
        return r;
    }
 
    public int [][] getGreeninArray(BufferedImage image)
    {
        int [][] g = new int[300][300];
        
        try {
            
        
            int w = image.getWidth();
            int h = image.getHeight();
            
            
            for (int i = 0; i < h; i++) {
                for (int j = 0; j < w; j++) {
                    //get pixel
                    int p = image.getRGB(i,j);
                    //get green
                    int gg = (p>>8) & 0xff;
                    g[i][j]= gg;
                }
            }   
            

        } catch (Exception e) {
            System.out.println("Kesalahan Pengambilan Gambar R "+e.getMessage());
        }
        
        return g;
    }
    
    public int [][] getBlueinArray(BufferedImage image)
    {
        int [][] b = new int[300][300];
        
        try {
            
        
            int w = image.getWidth();
            int h = image.getHeight();
            
            
            for (int i = 0; i < h; i++) {
                for (int j = 0; j < w; j++) {
                    //get pixel
                    int p = image.getRGB(i,j); 
                    //get blue
                    int bb = p & 0xff;
                    b[i][j]= bb;
                }
            }   
            

        } catch (Exception e) {
            System.out.println("Kesalahan Pengambilan Gambar R "+e.getMessage());
        }
        
        return b;
    }
    
    
    
    private void tambahDataNormalisasi(double hh , double ss , double vv, String target){
        try {
            
            String sql = "insert into normalisasi (H,S,V,Target)values('"+hh+"','"+ss+"','"+vv+"','"+target+"')";
            java.sql.Connection conn = (java.sql.Connection)Koneksi.koneksiDB();
            java.sql.PreparedStatement pst = conn.prepareStatement(sql);
            pst.execute();
            pst.close();
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }

    }
     
   
    
    public void data70Normalisasi(){
         
        FunctionSawit fc = new FunctionSawit();
        ArrayList<Double> h= new ArrayList<Double>();
        ArrayList<Double> s= new ArrayList<Double>();
        ArrayList<Double> v= new ArrayList<Double>();
        ArrayList<String> t= new ArrayList<String>();
        
        
        ArrayList<Double> x1= new ArrayList<Double>();
        ArrayList<Double> x2= new ArrayList<Double>();
        ArrayList<Double> x3= new ArrayList<Double>();
        ArrayList<String> xt= new ArrayList<String>();
        
         try {
             Connection conn =(Connection)Koneksi.koneksiDB(); 
             java.sql.Statement stm = conn.createStatement();
             String tc = "TRUNCATE normalisasi";
             stm.executeQuery(tc);
             java.sql.ResultSet sql = stm.executeQuery("select * from sawit limit 63");
             
            int i = 0;
             while((sql.next())){
                h.add(sql.getDouble("H"));
                s.add(sql.getDouble("S"));
                v.add(sql.getDouble("V"));
                t.add(sql.getString("Target"));
                
                i++;
            }
            sql.close();
            
            for(int a = 0; a<h.size(); a++){
                
                double minH = Collections.min(h);
                double maxH = Collections.max(h);
                double minS = Collections.min(s);
                double maxS = Collections.max(s);
                double minV = Collections.min(v);
                double maxV = Collections.max(v);
                 
                
                double ex1 = normalisasi(h.get(a),minH,maxH);
                double ex2 = normalisasi(s.get(a),minS,maxS);
                double ex3 = normalisasi(v.get(a),minV,maxV);
                
             /*   
                x1.add(fc.pembulatan(ex1));
                
                x2.add(fc.pembulatan(ex1));
                
                x3.add(fc.pembulatan(ex1));
                
                xt.add(t.get(a));
               */ 
             
                tambahDataNormalisasi(pembulatan(ex1),pembulatan(ex2),pembulatan(ex3),t.get(a));
                
            }
        }
        catch(Exception e){
        }
       
    }
    
    public void data80Normalisasi(){
         
        FunctionSawit fc = new FunctionSawit();
        ArrayList<Double> h= new ArrayList<Double>();
        ArrayList<Double> s= new ArrayList<Double>();
        ArrayList<Double> v= new ArrayList<Double>();
        ArrayList<String> t= new ArrayList<String>();
        
        
        ArrayList<Double> x1= new ArrayList<Double>();
        ArrayList<Double> x2= new ArrayList<Double>();
        ArrayList<Double> x3= new ArrayList<Double>();
        ArrayList<String> xt= new ArrayList<String>();
        
         try {
             Connection conn =(Connection)Koneksi.koneksiDB(); 
             java.sql.Statement stm = conn.createStatement();
             String tc = "TRUNCATE normalisasi";
             stm.executeQuery(tc);
             java.sql.ResultSet sql = stm.executeQuery("select * from sawit limit 72");
             
            int i = 0;
             while((sql.next())){
                h.add(sql.getDouble("H"));
                s.add(sql.getDouble("S"));
                v.add(sql.getDouble("V"));
                t.add(sql.getString("Target"));
                
                i++;
            }
            sql.close();
            
            for(int a = 0; a<h.size(); a++){
                
                double minH = Collections.min(h);
                double maxH = Collections.max(h);
                double minS = Collections.min(s);
                double maxS = Collections.max(s);
                double minV = Collections.min(v);
                double maxV = Collections.max(v);
                 
                
                double ex1 = normalisasi(h.get(a),minH,maxH);
                double ex2 = normalisasi(s.get(a),minS,maxS);
                double ex3 = normalisasi(v.get(a),minV,maxV);
                
             /*   
                x1.add(fc.pembulatan(ex1));
                
                x2.add(fc.pembulatan(ex1));
                
                x3.add(fc.pembulatan(ex1));
                
                xt.add(t.get(a));
               */ 
             
                tambahDataNormalisasi(pembulatan(ex1),pembulatan(ex2),pembulatan(ex3),t.get(a));
            }
        }
        catch(Exception e){
        }
       
    }
    
    public void data90Normalisasi(){
         
       
        ArrayList<Double> h= new ArrayList<Double>();
        ArrayList<Double> s= new ArrayList<Double>();
        ArrayList<Double> v= new ArrayList<Double>();
        ArrayList<String> t= new ArrayList<String>();
        
        
        ArrayList<Double> x1= new ArrayList<Double>();
        ArrayList<Double> x2= new ArrayList<Double>();
        ArrayList<Double> x3= new ArrayList<Double>();
        ArrayList<String> xt= new ArrayList<String>();
        
         try {
             Connection conn =(Connection)Koneksi.koneksiDB(); 
             java.sql.Statement stm = conn.createStatement();
             String tc = "TRUNCATE normalisasi";
             stm.executeQuery(tc);
             java.sql.ResultSet sql = stm.executeQuery("select * from sawit limit 81");
             
            int i = 0;
             while((sql.next())){
                h.add(sql.getDouble("H"));
                s.add(sql.getDouble("S"));
                v.add(sql.getDouble("V"));
                t.add(sql.getString("Target"));
                
                i++;
            }
            sql.close();
            
            for(int a = 0; a<h.size(); a++){
                
                double minH = Collections.min(h);
                double maxH = Collections.max(h);
                double minS = Collections.min(s);
                double maxS = Collections.max(s);
                double minV = Collections.min(v);
                double maxV = Collections.max(v);
                 
                
                double ex1 = normalisasi(h.get(a),minH,maxH);
                double ex2 = normalisasi(s.get(a),minS,maxS);
                double ex3 = normalisasi(v.get(a),minV,maxV);
                
             /*   
                x1.add(fc.pembulatan(ex1));
                
                x2.add(fc.pembulatan(ex1));
                
                x3.add(fc.pembulatan(ex1));
                
                xt.add(t.get(a));
               */ 
             
                tambahDataNormalisasi(pembulatan(ex1),pembulatan(ex2),pembulatan(ex3),t.get(a));
                
            }
        }
        catch(Exception e){
        }
       
    }
    
    public void getDataPelatihanFromDB(){
    
        //get data normalisasi dan data random
        
         try {
             
            ArrayList<Double> normalisasi_h= new ArrayList<Double>();
            ArrayList<Double> normalisasi_s= new ArrayList<Double>();
            ArrayList<Double> normalisasi_v= new ArrayList<Double>();
            ArrayList<String> normalisasi_t= new ArrayList<String>();



            ArrayList<Double> center_h= new ArrayList<Double>();
            ArrayList<Double> center_s= new ArrayList<Double>();
            ArrayList<Double> center_v= new ArrayList<Double>();
            ArrayList<String> center_t= new ArrayList<String>();
            
         
             Connection conn =(Connection)Koneksi.koneksiDB(); 
                 
             java.sql.Statement stm = conn.createStatement();
              
             java.sql.ResultSet normalisasi = stm.executeQuery("select * from normalisasi");   
           
             
             //data normalisasi
            int i = 0;
             while((normalisasi.next())){
                normalisasi_h.add(normalisasi.getDouble("H"));
                normalisasi_s.add(normalisasi.getDouble("S"));
                normalisasi_v.add(normalisasi.getDouble("V"));
                normalisasi_t.add(normalisasi.getString("Target"));
                
                i++;
            }
             
            double [][] data = new double[normalisasi_h.size()][3];
            String [] data_t = new String[normalisasi_h.size()];
             
             for (int j = 0; j < normalisasi_h.size(); j++) {
                 for (int k = 0; k < 1; k++) {
                     data[j][k]=normalisasi_h.get(j);
                     data[j][k+1]=normalisasi_s.get(j);
                     data[j][k+2]=normalisasi_v.get(j);
                 }
             }
             
             for (int j = 0; j < normalisasi_h.size(); j++) {
                 data_t[j]=normalisasi_t.get(j);
             }
             
             
             java.sql.ResultSet center = stm.executeQuery("select * from center_random ");
             
             // data center random
            int ii = 0;
             while((center.next())){
                center_h.add(center.getDouble("h"));
                center_s.add(center.getDouble("s"));
                center_v.add(center.getDouble("v"));
                center_t.add(center.getString("Target"));
                
                ii++;
            }
             
            double [][] random = new double[center_h.size()][3];
            String [] random_t = new String[center_h.size()];

             for (int j = 0; j < center_h.size(); j++) {
                 for (int k = 0; k < 1; k++) {
                     random[j][k]=center_h.get(j);
                     random[j][k+1]=center_s.get(j);
                     random[j][k+2]=center_v.get(j);
                 }
             }
             
             for (int j = 0; j < center_h.size(); j++) {
                 data_t[j]=center_t.get(j);
             }
             
             datapelatihan = new double[normalisasi_h.size()][3];
             datapelatihan_random = new double[center_h.size()][3];
             
             datapelatihan_t = new String[normalisasi_h.size()];
             datapelatihan_random_t = new String[center_h.size()];
            
             setDatapelatihan(data);
             setDatapelatihan_t(data_t);
             setDatapelatihan_random(random);
             setDatapelatihan_random_t(random_t);
             
             stm.execute("truncate table nilai_w");
             
             stm.close();
             
        }
        catch(Exception e){
            System.out.println("Gagal Get Data Pelatihan From DB "+e.getMessage().toString());
        }
         
    }
    
    public void pelatihan(){
        
        try {
            
            getDataPelatihanFromDB();
            int lengthData = datapelatihan.length;
            int pangkat = 2;

            double [][] euc = new double[lengthData][3];

            for (int i = 0; i < lengthData; i++) {
                for (int j = 0; j < 1; j++) {
                    euc[i][0]=pembulatan(Math.sqrt( 
                            Math.pow((datapelatihan[i][0]-datapelatihan_random[0][0]),pangkat ) + 
                            Math.pow((datapelatihan[i][1]-datapelatihan_random[0][1]),pangkat ) +
                            Math.pow((datapelatihan[i][2]-datapelatihan_random[0][2]),pangkat )  ));
                    
                    euc[i][1]=pembulatan(Math.sqrt( 
                            Math.pow((datapelatihan[i][0]-datapelatihan_random[1][0]),pangkat ) + 
                            Math.pow((datapelatihan[i][1]-datapelatihan_random[1][1]),pangkat ) +
                            Math.pow((datapelatihan[i][2]-datapelatihan_random[1][2]),pangkat )  ));
                    
                    euc[i][2]=pembulatan(Math.sqrt( 
                            Math.pow((datapelatihan[i][0]-datapelatihan_random[2][0]),pangkat ) + 
                            Math.pow((datapelatihan[i][1]-datapelatihan_random[2][1]),pangkat ) +
                            Math.pow((datapelatihan[i][2]-datapelatihan_random[2][2]),pangkat ) ));
                    
                }
            }
            
            double [][] gaussian = new double[lengthData][3];
            double b1 = 0.83225;
            
            for (int i = 0; i < lengthData; i++) {
                for (int j = 0; j < 1; j++) {
                    gaussian[i][0] = pembulatan(Math.exp((-(Math.pow((b1*euc[i][0]),pangkat)))));
                    gaussian[i][1] = pembulatan(Math.exp((-(Math.pow((b1*euc[i][1]),pangkat)))));
                    gaussian[i][2] = pembulatan(Math.exp((-(Math.pow((b1*euc[i][2]),pangkat)))));
                }
            }   
            
            double [][] matrixGBias = new double[lengthData][4];
            
            
            //matri Gaussian dan Bias
            for (int i = 0; i < lengthData; i++) {
                for (int j = 0; j < 1; j++) {
                    
                    matrixGBias[i][0] = gaussian[i][0];
                    matrixGBias[i][1] = gaussian[i][1];
                    matrixGBias[i][2] = gaussian[i][2];
                    matrixGBias[i][3] = 1;
                    
                }
            }
            
            //matrik Gbias Transpose
            
            double [][] g_transpose = transposeMatrix(matrixGBias);
            
            double [][] g_transpose_multiply = multiplicar(g_transpose,matrixGBias );
            
            double [][] invers_g_transpose_m = invert(g_transpose_multiply);
            
            double [][] invers_g_transpose_m_multiple_g_transpose = multiplicar(invers_g_transpose_m, g_transpose);
            
            double [][] target  = new double [lengthData][2];
            //String [] target  = splitString("00");
            
            for (int i = 0; i < lengthData; i++) {
                for (int j = 0; j < 1; j++) {
                    String [] textSplit = splitString(datapelatihan_t[i]);
                    target[i][0]= Double.parseDouble(textSplit[0]);
                    target[i][1]= Double.parseDouble(textSplit[1]);
                }
            }
            
            //perkalian hasil dengan target
            double [][] w = multiplicar(invers_g_transpose_m_multiple_g_transpose, target);
            double [][] w_pembulatan = new double[w.length][w[0].length];
            for (int i = 0; i < w.length; i++) {
                for (int j = 0; j < w[0].length; j++) {
                    w_pembulatan[i][j]= pembulatan(w[i][j]);
                    
                }
            }
            
            
            
            for (int i = 0; i < w_pembulatan.length; i++) {
                tambahDataW(w_pembulatan[i][0],w_pembulatan[i][1]);
            }
                      
            printGridDouble(w_pembulatan);
            
            
        } catch (Exception e) {
            System.out.println("Gagal Melakukan Pelatihan "+e.getMessage().toString());
        }
    
        
    }
    
    public  double[][] transposeMatrix(double [][] m){
        double[][] temp = new double[m[0].length][m.length];
        for (int i = 0; i < m.length; i++)
            for (int j = 0; j < m[0].length; j++)
                temp[j][i] = m[i][j];
        return temp;
    }
    
    public static double[][] multiplicar(double [][] A, double[][] B) {

        int aRows = A.length;
        int aColumns = A[0].length;
        int bRows = B.length;
        int bColumns = B[0].length;

        if (aColumns != bRows) {
            throw new IllegalArgumentException("A:Rows: " + aColumns + " did not match B:Columns " + bRows + ".");
        }

        double[][] C = new double[aRows][bColumns];
        for (int i = 0; i < aRows; i++) {
            for (int j = 0; j < bColumns; j++) {
                C[i][j] = 0.00000;
            }
        }

        for (int i = 0; i < aRows; i++) { // aRow
            for (int j = 0; j < bColumns; j++) { // bColumn
                for (int k = 0; k < aColumns; k++) { // aColumn
                    C[i][j] += A[i][k] * B[k][j];
                }
            }
        }

        return C;
    }
    
    public static double[][] invert(double a[][]) 
    {
        int n = a.length;
        double x[][] = new double[n][n];
        double b[][] = new double[n][n];
        int index[] = new int[n];
        for (int i=0; i<n; ++i) 
            b[i][i] = 1;
 
 // Transform the matrix into an upper triangle
        gaussian(a, index);
 
 // Update the matrix b[i][j] with the ratios stored
        for (int i=0; i<n-1; ++i)
            for (int j=i+1; j<n; ++j)
                for (int k=0; k<n; ++k)
                    b[index[j]][k]
                    	    -= a[index[j]][i]*b[index[i]][k];
 
 // Perform backward substitutions
        for (int i=0; i<n; ++i) 
        {
            x[n-1][i] = b[index[n-1]][i]/a[index[n-1]][n-1];
            for (int j=n-2; j>=0; --j) 
            {
                x[j][i] = b[index[j]][i];
                for (int k=j+1; k<n; ++k) 
                {
                    x[j][i] -= a[index[j]][k]*x[k][i];
                }
                x[j][i] /= a[index[j]][j];
            }
        }
        return x;
    }
 
// Method to carry out the partial-pivoting Gaussian
// elimination.  Here index[] stores pivoting order.
 
    public static void gaussian(double a[][], int index[]) 
    {
        int n = index.length;
        double c[] = new double[n];
 
 // Initialize the index
        for (int i=0; i<n; ++i) 
            index[i] = i;
 
 // Find the rescaling factors, one from each row
        for (int i=0; i<n; ++i) 
        {
            double c1 = 0;
            for (int j=0; j<n; ++j) 
            {
                double c0 = Math.abs(a[i][j]);
                if (c0 > c1) c1 = c0;
            }
            c[i] = c1;
        }
 
 // Search the pivoting element from each column
        int k = 0;
        for (int j=0; j<n-1; ++j) 
        {
            double pi1 = 0;
            for (int i=j; i<n; ++i) 
            {
                double pi0 = Math.abs(a[index[i]][j]);
                pi0 /= c[index[i]];
                if (pi0 > pi1) 
                {
                    pi1 = pi0;
                    k = i;
                }
            }
 
   // Interchange rows according to the pivoting order
            int itmp = index[j];
            index[j] = index[k];
            index[k] = itmp;
            for (int i=j+1; i<n; ++i) 	
            {
                double pj = a[index[i]][j]/a[index[j]][j];
 
 // Record pivoting ratios below the diagonal
                a[index[i]][j] = pj;
 
 // Modify other elements accordingly
                for (int l=j+1; l<n; ++l)
                    a[index[i]][l] -= pj*a[index[j]][l];
            }
        }
    }
    
    public String [] splitString (String string){
        String s = string;
        String[] arr = s.split("");    
        return arr;
    }
    
    private void tambahDataW(double Y0 , double Y1){
        try {
            
            String sql = "insert into nilai_w (y0,y1)values('"+Y0+"','"+Y1+"')";
            java.sql.Connection conn = (java.sql.Connection)Koneksi.koneksiDB();
            java.sql.PreparedStatement pst = conn.prepareStatement(sql);
            pst.execute();
            pst.close();
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "gagal mencari nilai w");
        }

    }
     
   
    
         
    public double pembulatan(double nilai){
        
        double hasilPembulatan= Double.parseDouble(String.format("%.6f", nilai));
        
        return hasilPembulatan;
    } 
    
    public double pembulatan4Angka(double nilai){
        
        double hasilPembulatan= Double.parseDouble(String.format("%.4f", nilai));
        
        return hasilPembulatan;
    }
    
    public double pembulatan2Angka(double nilai){
        
        double hasilPembulatan= Double.parseDouble(String.format("%.2f", nilai));
        
        return hasilPembulatan;
    }
    
    public double pembulatan1Angka(double nilai){
        
        double hasilPembulatan= Double.parseDouble(String.format("%.1f", nilai));
        return hasilPembulatan;
    }
    
    public int [][] sum4Array2Dimension(){
        
        for (int i = 0; i < 300; i++) {
            for (int j = 0; j < 300; j++) {
                
                
            }
        }
        return null;
    }
    
     public Double normalisasi(Double input, Double min, Double max) {
        return (input - min) / (max - min);
    }
 
    
    
    
    //PRINT , GET , SET Value
    //******************//******************//******************//******************//******************//******************
    
    public void printGrid(int[][] in){  
    for(int i = 0; i < in.length; i++){  
        for(int j = 0; j < in[0].length; j++){
            System.out.print(in[i][j] + "\t");
        }
        System.out.print("\n");
    }
    }
    
    public void printGrid1(double[] in){  
    for(int i = 0; i < in.length; i++){  
        
            System.out.print(in[i] + "\t");
        
        System.out.print("\n");
    }
    }
    
    public void printGridDouble(double[][] in){  
    for(int i = 0; i < in.length; i++){  
        for(int j = 0; j < in[0].length; j++){
            System.out.print(in[i][j] + "\t");
        }
        System.out.print("\n");
    }
    }
    
    public int[][] getR() {
        return r;
    }

    public void setR(int[][] r) {
        this.r = r;
    }

    public int[][] getG() {
        return g;
    }

    public void setG(int[][] g) {
        this.g = g;
    }

    public int[][] getB() {
        return b;
    }

    public void setB(int[][] b) {
        this.b = b;
    }
    
     public int getWidth() {
        return width;
    }

    public void setHeight(int height) {
        this.height = height;
    }
    
     public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public int[][] getRed1() {
        return red1;
    }

    public void setRed1(int[][] red1) {
        this.red1 = red1;
    }

    public int[][] getRed2() {
        return red2;
    }

    public void setRed2(int[][] red2) {
        
        this.red2 = red2;
    }

    public int[][] getRed3() {
        return red3;
    }

    public void setRed3(int[][] red3) {
        this.red3 = red3;
    }

    public int[][] getRed4() {
        return red4;
    }

    public void setRed4(int[][] red4) {
        this.red4 = red4;
    }

    public int[][] getGreen1() {
        return green1;
    }

    public void setGreen1(int[][] green1) {
        this.green1 = green1;
    }

    public int[][] getGreen2() {
        return green2;
    }

    public void setGreen2(int[][] green2) {
        this.green2 = green2;
    }

    public int[][] getGreen3() {
        return green3;
    }

    public void setGreen3(int[][] green3) {
        this.green3 = green3;
    }

    public int[][] getGreen4() {
        return green4;
    }

    public void setGreen4(int[][] green4) {
        this.green4 = green4;
    }

    public int[][] getBlue1() {
        return blue1;
    }

    public void setBlue1(int[][] blue1) {
        this.blue1 = blue1;
    }

    public int[][] getBlue2() {
        return blue2;
    }

    public void setBlue2(int[][] blue2) {
        this.blue2 = blue2;
    }

    public int[][] getBlue3() {
        return blue3;
    }

    public void setBlue3(int[][] blue3) {
        this.blue3 = blue3;
    }

    public int[][] getBlue4() {
        return blue4;
    }

    public void setBlue4(int[][] blue4) {
        this.blue4 = blue4;
    }

    public double[][] getDatapelatihan() {
        return datapelatihan;
    }

    public void setDatapelatihan(double[][] datapelatihan) {
        this.datapelatihan = datapelatihan;
    }

    public String[] getDatapelatihan_t() {
        return datapelatihan_t;
    }

    public void setDatapelatihan_t(String[] datapelatihan_t) {
        this.datapelatihan_t = datapelatihan_t;
    }

    public double[][] getDatapelatihan_random() {
        return datapelatihan_random;
    }

    public void setDatapelatihan_random(double[][] datapelatihan_random) {
        this.datapelatihan_random = datapelatihan_random;
    }

    public String[] getDatapelatihan_random_t() {
        return datapelatihan_random_t;
    }

    public void setDatapelatihan_random_t(String[] datapelatihan_random_t) {
        this.datapelatihan_random_t = datapelatihan_random_t;
    }

    

    
    
    
    public static void main(String[] args) {
        FunctionSawit fc = new  FunctionSawit();
        fc.pelatihan();
    }
       

    
}
