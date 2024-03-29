/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package renderer.point;

import java.awt.Point;
import renderer.Display;

/**
 *
 * @author bunya
 */
public class PointConverter {
    private static double zoomSensivity = 1.2;
    private static double scale = 1;
    
    
    public static void zoomIn(){
        scale *= zoomSensivity;
    }
    
    public static void zoomOut() {
            scale /= zoomSensivity;
    }
    
    public static Point convertPoint(MyPoint point3D) {
        double x3d = point3D.y * scale;
        double y3d = point3D.z * scale;
        double depth = point3D.x * scale;
        double[] newVal = scale(x3d, y3d, depth);
        
        int x2d = (int)(Display.getWindowWidth() / 2 + newVal[0]);
        int y2d = (int)(Display.getWindowHeight() / 2 - newVal[1]);
        Point point2d = new Point(x2d, y2d);
        return point2d;
    }
    
    private static double[] scale (double x3d, double y3d, double depth) {
        double dist = Math.sqrt(Math.pow(x3d, 2)  + Math.pow(y3d, 2));
        double theta = Math.atan2(y3d, x3d);
        double depth2 = 15 - depth;
        double localScale = Math.abs(1400/(depth2+1400));
        dist *= localScale;
        double[] newVal = new double[2];
        newVal[0] = dist * Math.cos(theta);
        newVal[1] = dist * Math.sin(theta);
        return newVal;
    }
    
    public static void rotateAxisX(MyPoint p, boolean CW, double degrees){
        double radius = Math.sqrt(p.y*p.y + p.z*p.z);
        double theta = Math.atan2(p.z, p.y);
        theta += 2*Math.PI/360*degrees*(CW?-1:1);
        p.y = radius * Math.cos(theta);
        p.z = radius * Math.sin(theta);
    }
    public static void rotateAxisY(MyPoint p, boolean CW, double degrees){
        double radius = Math.sqrt(p.x*p.x + p.z*p.z);
        double theta = Math.atan2(p.x, p.z);
        theta += 2*Math.PI/360*degrees*(CW?-1:1);
        p.x = radius * Math.sin(theta);
        p.z = radius * Math.cos(theta);
    }
    public static void rotateAxisZ(MyPoint p, boolean CW, double degrees){
        double radius = Math.sqrt(p.y*p.y + p.x*p.x);
        double theta = Math.atan2(p.y, p.x);
        theta += 2*Math.PI/360*degrees*(CW?1:-1);
        p.y = radius * Math.sin(theta);
        p.x = radius * Math.cos(theta);
    }
    
    public static void setZoomSensivity(double x) {
        zoomSensivity = x;
    }
}
