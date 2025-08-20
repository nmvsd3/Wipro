import com.example.dao.StudentDao;
import com.example.model.Student;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class MainApp {
    public static void main(String[] args) {
        // Initialize Spring context (scan config + DAO packages)
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext("com.example.config", "com.example.dao");

        StudentDao studentDao = context.getBean(StudentDao.class);

        // Insert student 1
        Student student1 = new Student();
        student1.setName("Madhu");
        student1.setEmail("madhu03@gmail.com");
        studentDao.save(student1);
        System.out.println("✅ Student inserted: " + student1);

        // Insert student 2
        Student student2 = new Student();
        student2.setName("Harsha");
        student2.setEmail("harsha@gmail.com");
        studentDao.save(student2);
        System.out.println("✅ Student inserted: " + student2);

        // Retrieve all students
        System.out.println("📌 All Students in DB:");
        studentDao.findAll().forEach(System.out::println);

        // Close context
        context.close();
    }
}
