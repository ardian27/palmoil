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
import java.util.Arrays;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
/**
 *
 * @author LENOVO
 */
public class FunctionSawit {
    
    public int [][] r1, r2, r3, r4;
    public int [][] g1, g2, g3, g4;
    public int [][] b1, b2, b3, b4;
    public int width=300, height=300;
    
    public int [][] hsv;

    public int [][] r;
    public int [][] g;
    public int [][] b;

    
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
        
        
        printGrid(r);
        
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
    
    public void getHSV(){
        
        //int sumR = getR1()
        double sumR [][]= new double[width][height];
        double sumG [][]= new double[width][height];
        double sumB [][]= new double[width][height];
        
        int [][]R1 = getR1();
        int [][]R2 = getR2();
        int [][]R3 = getR3();
        int [][]R4 = getR4();
                
        int [][]G1 = getG1();
        int [][]G2 = getG2();
        int [][]G3 = getG3();
        int [][]G4 = getG4();
        
        int [][]B1 = getB1();
        int [][]B2 = getB2();
        int [][]B3 = getB3();
        int [][]B4 = getB4();
        
        //JUMLAHKAN DAN BAGI 4
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
             //   sumR[i][j] = (R1[i][j]+R2[i][j]);
    //            sumG[i][j] = (G1[i][j]+G2[i][j])+(G3[i][j]+G4[i][j]);
      //          sumB[i][j] = (B1[i][j]+B2[i][j])+(B3[i][j]+B4[i][j]);
            }
        }
      /*  
        //NORMALISASI NILAI RGB
        double r [][] = new double[width][height];
        double g [][] = new double[width][height];
        double b [][] = new double[width][height]; 
        
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                r[i][j]=((sumR[i][j])/(sumR[i][j]+sumG[i][j]+sumB[i][j]));
                g[i][j]=((sumG[i][j])/(sumR[i][j]+sumG[i][j]+sumB[i][j]));
                b[i][j]=((sumB[i][j])/(sumR[i][j]+sumG[i][j]+sumB[i][j]));
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
                v[i][j]= Arrays.stream(tab).max().getAsDouble();
                
                v_min[i][j]= Arrays.stream(tab).min().getAsDouble();
                
                //Hitung Nilai S
                if (v[i][j] == 0){
                    s[i][j] = 0;
                }else{
                    s[i][j] = 1- (v_min[i][j]/v[i][j]);
                }
                
                if( v[i][j] == r[i][j]){
                    h[i][j] = 60 * (0+((g[i][j]-b[i][j])/(s[i][j]*v[i][j])));
                }else if (v[i][j] == g[i][j]){
                    h[i][j] = 60 * (2+((b[i][j]-r[i][j])/(s[i][j]*v[i][j])));
                } else if (v[i][j] == b[i][j]){
                    h[i][j] = 60 * (4+((r[i][j]-g[i][j])/(s[i][j]*v[i][j])));
                }else {
                    h[i][j] = 0;
                } 
                
                
            }
        }
       /*
        //MENCARI NILAI MEAN H
        double sumH = 0; 
        double sumS = 0;
        double sumV = 0;
        
        for (int i = 0; i < width; i++) {
            
            for (int j = 0; j < height; j++) {
                sumH += h[i][j];
                sumS += s[i][j];
                sumV += v[i][j];
            }
        }
        
        System.out.println("Nilai sumH"+(sumH/(height*width)));
        */
        
    }
    
    public double pembulatan(double nilai){
        
        double hasilPembulatan= Double.parseDouble(String.format("%.4f", nilai));
        
        return hasilPembulatan;
    }
    
    public int [][] sum4Array2Dimension(){
        
        for (int i = 0; i < 300; i++) {
            for (int j = 0; j < 300; j++) {
                
                
            }
        }
        return null;
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
    
    
     public int[][] getR1() {
        return r1;
    }

    public void setR1(int[][] r1) {
        this.r1 = r1;
    }

    public int[][] getR2() {
        return r2;
    }

    public void setR2(int[][] r2) {
        this.r2 = r2;
    }

    public int[][] getR3() {
        return r3;
    }

    public void setR3(int[][] r3) {
        this.r3 = r3;
    }

    public int[][] getR4() {
        return r4;
    }

    public void setR4(int[][] r4) {
        this.r4 = r4;
    }

    public int[][] getG1() {
        return g1;
    }

    public void setG1(int[][] g1) {
        this.g1 = g1;
    }

    public int[][] getG2() {
        return g2;
    }

    public void setG2(int[][] g2) {
        this.g2 = g2;
    }

    public int[][] getG3() {
        return g3;
    }

    public void setG3(int[][] g3) {
        this.g3 = g3;
    }

    public int[][] getG4() {
        return g4;
    }

    public void setG4(int[][] g4) {
        this.g4 = g4;
    }

    public int[][] getB1() {
        return b1;
    }

    public void setB1(int[][] b1) {
        this.b1 = b1;
    }

    public int[][] getB2() {
        return b2;
    }

    public void setB2(int[][] b2) {
        this.b2 = b2;
    }

    public int[][] getB3() {
        return b3;
    }

    public void setB3(int[][] b3) {
        this.b3 = b3;
    }

    public int[][] getB4() {
        return b4;
    }

    public void setB4(int[][] b4) {
        this.b4 = b4;
    }
    
}
