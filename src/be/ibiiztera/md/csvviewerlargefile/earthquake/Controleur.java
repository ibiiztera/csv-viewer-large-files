/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package be.ibiiztera.md.csvviewerlargefile.earthquake;

import java.io.File;

/**
 *
 * @author Mary
 */
public interface Controleur {
    public Data getData();
    public void notifyView(Data data);
    public void saveView(File image);
}
