/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interfaces;

import java.awt.Graphics;

/**
 *
 * @author bunya
 */
public interface IDrawable {
    public void onCreate();
    public void update();
    public void render(Graphics g);
}
