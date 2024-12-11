module org.example.duetrockers {
    requires javafx.controls;
    requires jakarta.persistence;
    requires org.hibernate.orm.core;
    requires java.management;


    opens org.example.duetrockers.entities to org.hibernate.orm.core;
    exports org.example.duetrockers;
}