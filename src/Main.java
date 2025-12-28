//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {

    public static void main(String[] args) {
        Parent p = new Child();
        System.out.println(p.getColor());
    }

}

class Parent {

    String color = "brown";

    public String getColor() {
        return color;
    }
}

class Child extends Parent {

    String color = "green";

    @Override
    public String getColor() {
        return color;
    }
}