
import Interfaces.IDrawable;
import java.awt.Color;
import java.awt.Graphics;
import renderer.Display;
import renderer.input.Mouse;
import renderer.point.MyPoint;
import renderer.point.PointConverter;
import renderer.shapes.MyPolygon;
import renderer.shapes.Tetrahedron;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author bunya
 */
public class MainClass implements IDrawable{
    /**
     * @param args the command line arguments
     */
    private Tetrahedron tetra;
    private static Display display;
    private static Mouse mouse;
    
    public static void main(String[] args) {
        display = new Display.DisplayBuilder().build(new MainClass());
        display.buildFrame(display);
        display.start();
        mouse = display.getMouse();
    }
    
    //onCreate -> update -> render()
    
    @Override // onCreate before start
    public void onCreate() {
        int size = 100;
        
        
        MyPoint p1 = new MyPoint( size/2, -size/2, -size/2);
        MyPoint p2 = new MyPoint( size/2,  size/2, -size/2);
        MyPoint p3 = new MyPoint( size/2,  size/2,  size/2);
        MyPoint p4 = new MyPoint( size/2, -size/2,  size/2);
        MyPoint p5 = new MyPoint(-size/2, -size/2, -size/2);
        MyPoint p6 = new MyPoint(-size/2,  size/2, -size/2);
        MyPoint p7 = new MyPoint(-size/2,  size/2,  size/2);
        MyPoint p8 = new MyPoint(-size/2, -size/2,  size/2);
        this.tetra = new Tetrahedron(
                
                
                new MyPolygon(Color.WHITE   ,p1, p2, p6, p5),
                new MyPolygon(Color.YELLOW  ,p1, p5, p8, p4),
                new MyPolygon(Color.GREEN   ,p2, p6, p7, p3),
                new MyPolygon(Color.RED     ,p1, p2, p3, p4),
                new MyPolygon(Color.ORANGE  ,p4, p3, p7, p8),
                new MyPolygon(Color.BLUE    ,p5, p6, p7, p8)
                
                
        );
    }
    
    private int initialX= 0;
    private int initialY= 0;
    @Override //until running 
    public void update() {
        
        if(mouse.isMouseClicked(Mouse.LEFT)) {
            int difX = initialX - mouse.getMouseX();
            int difY = initialY - mouse.getMouseY();
            tetra.rotate(true, 0, difY/2.5, -difX/2.5);
        }
        initialX = mouse.getMouseX();
        initialY = mouse.getMouseY();
        
        if(mouse.isScrollDown()) {
            //zoom out
            display.zoomOut();
        }else if (mouse.isScrollUp()) {
            //zoom in
            display.zoomIn();
        }
         mouse.resetScroll();
    }

    @Override //until running
    public void render(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, display.getWidth()*2, display.getHeight()*2);
        this.tetra.render(g);
    }

    
    
}
