/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package renderer;

import Interfaces.IDrawable;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import renderer.input.Mouse;
import renderer.point.MyPoint;
import renderer.point.PointConverter;
import renderer.shapes.MyPolygon;
import renderer.shapes.Tetrahedron;

/**
 *
 * @author bunya
 */
public class Display extends Canvas implements Runnable{
    private static final long serialVersionUID = 1L;
   
    private Thread thread;
    private final JFrame frame;
    private final String TITLE;
    private static int WINDOW_WIDTH;
    private static int WINDOW_HEIGHT;
    private static boolean running = false;
    private final IDrawable drawable;
    private Mouse mouse;
    
    private Display(DisplayBuilder builder) {
        this.frame = new JFrame();
        //set mouse listener
        this.mouse = new Mouse();
        this.addMouseListener(this.mouse);
        this.addMouseMotionListener(this.mouse);
        this.addMouseWheelListener(mouse);
        
        this.TITLE = builder.TITLE;
        Display.WINDOW_HEIGHT = builder.HEIGHT;
        Display.WINDOW_WIDTH = builder.WIDTH;
        this.drawable = builder.drawable;
        Dimension size = new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT);
        this.setPreferredSize(size);
    }
   
   public void buildFrame(Display disp){
        this.frame.setTitle(TITLE);
        this.frame.add(disp);
        this.frame.pack();
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.frame.setLocationRelativeTo(null);
        this.frame.setResizable(false);
        this.frame.setVisible(true);
   }
   
   
   
   public synchronized void start(){
       running = true;
       this.thread = new Thread(this,"Display");
       this.thread.start();
   }
   
    public synchronized void stop(){
        running = false;
        try {
           this.thread.join();
        } catch (InterruptedException ex) {
            Logger.getLogger(Display.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void zoomIn() {
        PointConverter.zoomIn();
    }
    
    public void zoomOut() {
        PointConverter.zoomOut();
    }

    @Override
    public void run() {
        long lastTime = System.nanoTime();
        long timer = System.currentTimeMillis();
        final double ms = 1000000000.0/60;
        double delta = 0;
        int frames = 0;
        
        this.drawable.onCreate();
        
        while(running) {
            long now = System.nanoTime();
            delta += (now-lastTime) / ms;
            lastTime = now;
            while(delta >= 1) {
                drawable.update();
                render();
                delta--;
                frames++;
            }
            
            
            if(System.currentTimeMillis() - timer > 1000) {
                timer += 1000;
                this.frame.setTitle(TITLE+" | "+frames+" fps");
                frames = 0;
            }
        }
        stop();
    }
    
    private void render(){
        BufferStrategy bs = this.getBufferStrategy();
        if(bs==null){
            this.createBufferStrategy(3);
            return; 
        }
        Graphics g = bs.getDrawGraphics();
        drawable.render(g);
        
        
        g.dispose();
        bs.show();
    }
    
    public static int getWindowWidth(){
        return WINDOW_WIDTH;
    }
   
    public static int getWindowHeight(){
        return WINDOW_HEIGHT;
    }
   
    public Mouse getMouse(){
        return this.mouse;
    }
    
    public void setZoomSensivity(double sensivity) {
        PointConverter.setZoomSensivity(sensivity);
    }
    
    
    public static  class DisplayBuilder {
        private String TITLE = "3D Renderer";
        private int WIDTH = 800;
        private int HEIGHT = 600;
        private IDrawable drawable;
        
        public DisplayBuilder setTitle(String title) {
            this.TITLE = title;
            return this;
        }
   
        public DisplayBuilder setCanvasSize(int WIDTH, int HEIGHT) {
            this.WIDTH = WIDTH;
            this.HEIGHT = HEIGHT;
            return this;
        }
        
        public Display build(IDrawable object){
            drawable = object;
            return new Display(this);
        }
    }
}
