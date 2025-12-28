package abs;


public class MyStatic {

    public static void main(String[] args) {
        System.out.println(Student.universityName);

        Student s1 = new Student("Shantanu");
        System.out.println(s1.name);

        Student s2 = new Student("Praveen");
        System.out.println(s2.name);

//        Student.staticFunction();

    }
}


// Static Keyword

class Student {

    static {
        Student s = new Student("Sowniya");
        System.out.println("Hello World");
    }

    static String universityName = "GFG";

    String name;

    public Student(String name) {
        this.name = name;
    }

//    public static void staticFunction() {
//        System.out.println(name);
//        System.out.println(Student.universityName);
//    }

    public void nonStaticFunction() {
        System.out.println(name);
        System.out.println(Student.universityName);
    }
}
