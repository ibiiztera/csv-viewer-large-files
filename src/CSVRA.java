
import java.io.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Mary
 */
public class CSVRA implements RandomAccess{
    private String ligneSep = "\n";
    private String colonSep = ";";
    private File f;
    FileInputStream fis;
    ArrayList<Integer> index = new ArrayList<Integer>();
    private ArrayList<String> colonnes;
    @Override
    public void init(File csv, String ligneSeparator, String colonneSeparator) {
        this.ligneSep=ligneSeparator;
        this.colonSep=colonneSeparator;
        this.f = csv;
        try {
            fis = new FileInputStream(f);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(CSVRA.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void buildIndex(File ind0) {
        BufferedInputStream bis = null;
        try {
            bis = new BufferedInputStream(new FileInputStream(f));
            byte[] b = new byte[65536];
            int read= 0;
            int ligne = 0;
            int colonne = 0;
            int debutligne = 0;
            int debutcol1 = 0;
            int total=0;
            boolean isLigneFinished = false;
            while((read = bis.read(b))>0)
            {
                String s = new String(b, 0, read);
                int i = 0;                  
                while(i<s.length())
                {
                    if(s.substring(i, 1).equals(ligneSep))
                    {   
                        ligne++;
                        index.add(total+2);
                    }
                    else if(s.substring(i,1).equals(colonSep) && ligne==0)
                    {
                        colonnes.add(s.substring(debutcol1, i));
                        debutcol1= i;
                        
                    }
                    total += 1;
                }
                    
                    
            }
            index.add(total);
        } catch (IOException ex) {
            Logger.getLogger(CSVRA.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                bis.close();
            } catch (IOException ex) {
                Logger.getLogger(CSVRA.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
            
    }

    @Override
    public String ligne(int num) {
        try {
            RandomAccessFile raf = new RandomAccessFile(f, "r");
            raf.seek(index.get(num));
            byte [] b =  new byte[index.get(num+1)-index.get(num)];
            int read = raf.read(b);
            String l = new String(b, 0, read);
            return l;
        } catch (IOException ex) {
            Logger.getLogger(CSVRA.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    private int colonne(String nom)
    {
        return colonnes.indexOf(nom);
    }
    @Override
    public String colonne(String ligne, String nom) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public ArrayList<String> colonnes() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public ArrayList<ArrayList<String>> select(ArrayList<String> colonnes, int debut, int fin) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
