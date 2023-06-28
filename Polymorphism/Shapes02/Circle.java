package Polymorphism.Shapes02;

public class Circle implements Shape{


    private Double radius;

    public Circle(Double radius) {
        this.radius = radius;
    }


    public Double getRadius() {
        return radius;
    }

    @Override
    public Double calculatePerimeter() {
        return 2 * Math.PI * radius;
    }

    @Override
    public Double calculateArea() {
        return Math.PI * radius * radius;
    }
}
