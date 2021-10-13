/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package renderer.shapes;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Polygon;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import renderer.point.MyPoint;
import renderer.point.PointConverter;

/**
 *
 * @author bunya
 */
public class MyPolygon {
    public MyPoint[] points;
    private Color color;
    
    public MyPolygon(Color color,MyPoint... points) {
        this.color = color;
        this.points = new MyPoint[points.length];
        for(int i = 0; i < points.length; i++){
            MyPoint p = points[i];
            this.points[i] = new MyPoint(p.x, p.y, p.z);
        }
    }
    
    public MyPolygon(MyPoint... points) {
        this.color = Color.GREEN;
        this.points = new MyPoint[points.length];
        for(int i = 0; i < points.length; i++){
            MyPoint p = points[i];
            this.points[i] = new MyPoint(p.x, p.y, p.z);
        }
    }
    
    public void render(Graphics g){
        Polygon poly = new Polygon();
        for(MyPoint myPoint : this.points){
            Point p = PointConverter.convertPoint(myPoint);
            poly.addPoint(p.x, p.y);
        }
        g.setColor(this.color);
        g.fillPolygon(poly);
    }
    
    public void rotate(boolean CW, double xDegrees, double yDegrees, double zDegrees) {
        for(MyPoint p : points){
            PointConverter.rotateAxisX(p, CW, xDegrees);
            PointConverter.rotateAxisY(p, CW, yDegrees);
            PointConverter.rotateAxisZ(p, CW, zDegrees);
        }
    }

    void setColor(Color color) {
        this.color = color;
    }
    
    public double getAveraageX() {
        double sum = 0;
        for(MyPoint p : this.points) {
            sum += p.x;
        }
        return sum / this.points.length;
    }
    
    public static MyPolygon[] sortPolygons(MyPolygon[] polygons) {
        List<MyPolygon> polygonList = new ArrayList<>();
        polygonList.addAll(Arrays.asList(polygons));
        Collections.sort(polygonList, (MyPolygon t, MyPolygon t1) -> t1.getAveraageX() - t.getAveraageX() < 0 ? 1 : -1);
        
        
       
        for(int i =0 ; i < polygons.length; i++) {
            polygons[i] = polygonList.get(i);
        }
        
        return polygons;
    }
}
