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
             java.sql.ResultSet sql = stm.executeQuery("select * from sawit limit 7");
             
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
             
                tambahDataNormalisasi(ex1,ex2,ex3,t.get(a));
                
            }
        }
        catch(Exception e){
        }
       
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
    
    
    
    
    
}
