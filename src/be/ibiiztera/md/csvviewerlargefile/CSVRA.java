package be.ibiiztera.md.csvviewerlargefile;


import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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
    private String ligneSep = java.util.ResourceBundle.getBundle("be/ibiiztera/md/csvviewerlargefile/Bundle").getString("ligne");
    private String colonSep = java.util.ResourceBundle.getBundle("be/ibiiztera/md/csvviewerlargefile/Bundle").getString("colonne");
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
    public String ligne(int num) 
        throws LigneIndexTooBigException, LigneIndexTooSmallException
    {
        try {
            RandomAccessFile raf = new RandomAccessFile(f, java.util.ResourceBundle.getBundle("be/ibiiztera/md/csvviewerlargefile/Bundle").getString("R"));
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
        return colonnes.indexOf(nom)+1;
    }
    @Override
    public String colonne(String ligne, String nom)
            throws ColonneIndexTooBigException, ColonneIndexTooSmallException, LectureColonneException{
        int colIdx = 0;
        int colonne = colonne(nom);
        if(colonne>colonnes.size())
            throw new ColonneIndexTooBigException();
        if(colonne<=0)
            throw new ColonneIndexTooSmallException();
        int i=0;
        while(colIdx<colonnes.size() &&i<ligne.length())
            {
                if(ligne.substring(i, 1).equals(ligne.indexOf(ligneSep)))
                colIdx++;
                if(colIdx==colonne)
                    return ligne.substring(i, ligne.indexOf(ligneSep, i+1)); 
                i++;
            }
        throw new LectureColonneException();
    }
    protected ArrayList<String> selectColonnes(List<String> ligne, ArrayList<String> col)
    {
        ArrayList<String> ret = new ArrayList<String>();
        for(int i=0; i<col.size(); i++)
        {
            int pos = -1;
            if((pos=java.util.Collections.binarySearch(ligne, col.get(i)))>=0)
            {
                ret.add(ligne.get(pos));
            }
            
        }
        return ret;
    }
    @Override
    public ArrayList<String> colonnes() {
        return colonnes;
    }

    @Override
    public ArrayList<ArrayList<String>> select(ArrayList<String> scol, int debut, int fin) 
    throws LigneIndexTooBigException, LigneIndexTooSmallException{
        ArrayList<ArrayList<String>> lignes = new ArrayList<ArrayList<String>>();
        for(int i=debut; i<fin; i++)
        {
            String ligne = ligne(i);
            String colOrig []  = ligne.split(ligneSep);
            lignes.add(selectColonnes(Arrays.asList(colOrig), scol));
        }
        return lignes;
        
    }

}
