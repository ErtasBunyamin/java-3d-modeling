/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package renderer.input;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

/**
 *
 * @author bunya
 */
public class Mouse implements MouseListener, MouseMotionListener,MouseWheelListener{
    public static int LEFT = 1;
    public static int RIGHT = 2;
    public static int WHELL = 3;
    private int mouseX = -1;
    private int mouseY = -1;
    private int mouse0 = -1;
    private int scroll = 0;
    
    
    public int getMouseX(){
        return mouseX;
    }

    public int getMouseY() {
        return mouseY;
    }

    public int getButton() {
        return mouse0;
    }

    public int getScroll() {
        return scroll;
    }
    
    public boolean isMouseClicked(int key){
        return getButton() == key;
    }
    
    public boolean isScrollUp() {
        return scroll == -1;
    }
    
    public boolean isScrollDown() {
        return scroll == 1;
    }
    
    public void resetScroll() {
        this.scroll = 0;
    }
    
    @Override
    public void mouseClicked(MouseEvent me) {
    }

    @Override
    public void mousePressed(MouseEvent me) {
        mouse0 = me.getButton();
    }

    @Override
    public void mouseReleased(MouseEvent me) {
        mouse0 = -1;
    }

    @Override
    public void mouseEntered(MouseEvent me) {
    }

    @Override
    public void mouseExited(MouseEvent me) {
    }

    @Override
    public void mouseDragged(MouseEvent me) {
        this.mouseX = me.getX();
        this.mouseY = me.getY();
    }

    @Override
    public void mouseMoved(MouseEvent me) {
        this.mouseX = me.getX();
        this.mouseY = me.getY();
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent mwe) {
        this.scroll = mwe.getWheelRotation();
    }
    
}
