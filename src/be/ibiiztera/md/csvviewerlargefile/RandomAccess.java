package be.ibiiztera.md.csvviewerlargefile;


import java.io.File;
import java.util.ArrayList;
import java.util.Map;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Mary
 */
public interface RandomAccess {
    /**
     * lire nom colonnes et initialisations
     * @param csv fichier csv
     * @param ligneSeparator séparateur de ligne (défaut \n)
     * @param colonneSeparator séparateur de colonnes (défaut: ;)
     */
    public void init(File csv, String ligneSeparator, String colonneSeparator);
    /**
     * Construire l'index
     * @param ind0 fichier d'index éventuel
     */
    public void buildIndex(File ind0);
    /**
     * retourne une ligne 
     * @param num numéro de la ligne
     * @return ligne telle quelle
     */
    public String ligne(int num)
        throws LigneIndexTooBigException, LigneIndexTooSmallException;
    /***
     * retourne une colonne telle quelle
     * @param ligne numéro de la ligne
     * @param nom
     * @return 
     */
    public String colonne(String ligne, String nom)
            throws ColonneIndexTooBigException, ColonneIndexTooSmallException, LectureColonneException;
    /**
     * liste des colonnes
     * */
    public ArrayList<String> colonnes();
    /**
     * sélectionne ligne de début à fin
     * @param colonnes sélection des colonnes
     * @param debut ligne de début 
     * @param fin  ligne de fin (exclus)
     */
    public ArrayList<ArrayList<String>> select(ArrayList<String> colonnes, int debut, int fin)
        throws LigneIndexTooBigException, LigneIndexTooSmallException;
            ;
}
