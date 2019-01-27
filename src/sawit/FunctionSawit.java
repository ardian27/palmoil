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
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
/**
 *
 * @author LENOVO
 */
public class FunctionSawit {
    
    private int [][] r1, r2, r3, r4;
    private int [][] g1, g2, g3, g4;
    private int [][] b1, b2, b3, b4;

    
    public ImageIcon LabeltoImage100R(String imagepath)
    {
        
        ImageIcon MyImage  = new ImageIcon(imagepath);
        Image img  = MyImage.getImage();
        Image newImage = img.getScaledInstance(100,100,Image.SCALE_SMOOTH);
        ImageIcon image = new ImageIcon(newImage);
        
        System.out.println("path gambar"+imagepath);
        
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
        
        System.out.println("nilai r 00 = "+r[0][0]);
        
        return r;
    }
    
    
    
    
    
            
            
    
    
}
