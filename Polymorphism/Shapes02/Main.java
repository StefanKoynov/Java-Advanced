package Polymorphism.Shapes02;

public class Main {

    public static void main(String[] args) {

        Shape rectangle = new Rectangle(2.00, 4.00);

        System.out.println(rectangle.calculatePerimeter());
        System.out.println(rectangle.calculateArea());

    }
}
