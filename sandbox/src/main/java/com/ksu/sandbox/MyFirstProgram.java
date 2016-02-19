package com.ksu.sandbox;

public class MyFirstProgram {

    public static void main(String[] args) {

        Point a = new Point(3.5, 4);
        Point b = new Point(4, 9.5);

        System.out.printf("Distance between two points with coordinates (%.3f, %.3f) and (%.3f, %.3f):\n", a.x, a.y, b.x, b.y);
        System.out.printf("MyFirstProgram.distance() result = %.3f\n", distance(a, b));
        System.out.printf("a.distance() result = %.3f\n", a.distance(b));
        System.out.printf("b.distance() result = %.3f\n", b.distance(a));
    }

    public static double distance(Point p1, Point p2)
    {
        return Math.sqrt(Math.pow((p1.x - p2.x), 2) + Math.pow((p1.y - p2.y), 2));
    }
}