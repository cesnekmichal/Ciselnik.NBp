/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ciselnik_dlouheho_drivi.gui;

import unidataz.components.table.cell_renderers.AbstractRenderer;

/**
 *
 * @author Michal
 */
public class HTMLConstants {
    
    public static String h(int i,String s){
        return "<h"+i+">"+s+"</h"+i+">";
    }
    public static String h(int i,String s,Align align){
        return "<h"+i+" style=\"text-align:"+align.text+"\">"+s+"</h"+i+">";
    }
    
    public static String td(Object s){
        return "<td>"+s.toString()+"</td>";
    }
    public static String td(Object s,Align align){
        return "<td style=\"text-align:"+align.text+"\">"+s.toString()+"</td>";
    }
    
    public static String p(String s){
        return "<p>"+s+"</p>";
    }
    public static String b(String s){
        return "<b>"+s+"</b>";
    }
    public static String p(String s,Align align){
        return "<p style=\"text-align:"+align.text+"\">"+s+"</p>";
    }
    public static enum Align{
        Left  ("left"),
        Right ("right"),
        Center("center");
        public final String text;
        private Align(String text) {
            this.text = text;
        }
    }
            
}
