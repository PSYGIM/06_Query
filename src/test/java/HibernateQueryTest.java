import cn.edu.ldu.PO.Student;
import cn.edu.ldu.utils.HbnUtils;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.Query;
import javax.persistence.SecondaryTable;
import javax.persistence.criteria.CriteriaQuery;
import javax.xml.namespace.QName;
import java.util.List;

public class HibernateQueryTest {
    private Session session;
    @Before
    public void init(){
        session = HbnUtils.getSession();
    }
    @org.junit.Test
    public void testData(){

        try {
            session.beginTransaction();
            for (int i = 0;i < 10 ; i++ ){
                Student student = new Student("name_"+i,15+i,75+i);
                session.save(student);
            }
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        }
    }
    @org.junit.Test
    public void testSQLQuery(){
        try {
            session.beginTransaction();
            String sql = "select * from t_student";
            List<Student> studentList = session.createSQLQuery(sql).addEntity(Student.class).list();
            for (Student student : studentList){
                System.out.println(student);
    }
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        }
    }
    @org.junit.Test
    public void testHQLQuery(){
        try {
            session.beginTransaction();
//            String hql = "from Student order by  score desc "; 排序查询
            String hql = "from Student where age > ?1 and score > ?2";

            List<Student> studentList = session.createQuery(hql).setParameter(1,18).setParameter(2,80.0).list();//通配符查询1

//            String hql = "from Student where age > :tage and score > :tscore";
//            List<Student> studentList = session.createQuery(hql).setParameter("tage",0).setParameter("tscore",60.0).list();//通配符查询2

            for (Student student : studentList){
                System.out.println(student);
            }
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        }
    }
    @org.junit.Test
    public void testQBCQuery(){
        try {
            session.beginTransaction();
//            CriteriaQuery<Student> criteriaQuery  = session.getCriteriaBuilder().createQuery(Student.class);
//            criteriaQuery.from(Student.class);
//            List<Student> studentList = session.createQuery(criteriaQuery).getResultList();

            // List<Student> studentList = session.createCriteria(Student.class).addOrder(Order.desc("score")).list();//排序查询

            List<Student> studentList = session.createCriteria(Student.class).add(Restrictions.gt("age",20)).add(Restrictions.lt("score","80")).list();//带参查询
            for (Student student : studentList){
                System.out.println(student);
            }
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        }
    }
    @org.junit.Test
    public void testPageHQLQuery(){//分页查询
        try {
            session.beginTransaction();

            String hql = "from Student ";

            List<Student> studentList = session.createQuery(hql).setFirstResult(3).setMaxResults(5).list();//通配符查询1

            for (Student student : studentList){
                System.out.println(student);
            }
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        }
    }
    @org.junit.Test
    public void testHQL_like(){//模糊查询
        try {
            session.beginTransaction();

            String hql = "from Student Where name like :tname" ;

            List<Student> studentList = session.createQuery(hql).setParameter("tname","%1%").list();//通配符查询1

            for (Student student : studentList){
                System.out.println(student);
            }
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        }
    }
    @org.junit.Test
    public void testHQL_uniqueResult(){//唯一性查询
        try {
            session.beginTransaction();

            String hql = "from Student Where id = ?1" ;

            List<Student> studentList = session.createQuery(hql).setParameter(1,20).list();//通配符查询1

            for (Student student : studentList){
                System.out.println(student);
            }
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        }
    }
    @org.junit.Test
    public void testHQL_count(){//聚合函数查询
        try {
            session.beginTransaction();

            String hql1 = "select count(*) from Student" ;
            Object total1 = session.createQuery(hql1).uniqueResult();
            System.out.println("学生总数="+total1);

            String hql2 = "select count(name) from Student" ;
            Object total2 = session.createQuery(hql2).uniqueResult();
            System.out.println("姓名非空的学生记录数="+total2);
            session.getTransaction().commit();
        }
        catch (Exception e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        }
    }
    @org.junit.Test
    public void testHQL_Projection(){//投影查询
        try {
            session.beginTransaction();

            String hql = "select new Student(name,age,score) from Student " ;

            List<Student> studentList = session.createQuery(hql).list();//通配符查询1

            for (Student student : studentList){
                System.out.println(student);
            }
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        }
    }
    @org.junit.Test
    public void testHQL_list(){//分组查询
        try {
            session.beginTransaction();

            String hql1 = "select age from Student group by age" ;

            List<Object> studentList_1 = session.createQuery(hql1).list();

            for (Object student : studentList_1){
                System.out.print(student+"\t");
            }
            String hql2 = "select age from Student group by age having count(age) > ?1";
            List<Object> studentList_2 = session.createQuery(hql2).setParameter(1,new Long(1)).list();

            for (Object student : studentList_2){
                System.out.println(student);
            }
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        }
    }
    @org.junit.Test
    public void testQBC_like(){
        session.beginTransaction();
        try{
        List<Student> studentList = session.createCriteria(Student.class).add(Restrictions.gt("age",20)).add(Restrictions.lt("score","80")).list();//带参查询
        for (Student student : studentList){
            System.out.println(student);
        }
        session.getTransaction().commit();
    } catch (Exception e) {
        e.printStackTrace();
        session.getTransaction().rollback();
    }
    }
}
