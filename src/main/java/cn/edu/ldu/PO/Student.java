package cn.edu.ldu.PO;

import javax.persistence.*;

@Entity
@Table(name = "t_student")
public class Student {
    private Integer id;
    private String name;
    private Double score;
    private int age;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
    @Column(name = "t_name",length = 30)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public Student() {

    }
    public  Student(String name,int age,double score ){
        this.name = name;
        this.age = age;
        this.score = score;
    }
    public String toString(){
        return "Student {" +
                "id="+id+",name='" + name +'\'' + ",age='" + age +'\''+"}";
    }
}