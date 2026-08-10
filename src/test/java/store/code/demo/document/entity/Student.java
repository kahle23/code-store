// 迁移注记（2026-08-09）：引用 Person 类，但 demo4j 中 Person 存在于 common/cglib、common/apache/beanutils、common/jdk/introspector 多处同名，未随本测试明确归属，编译找不到符号，故整类注释。
// package store.code.demo.document.entity;
//
// import java.io.Serializable;
//
// public class Student extends Person implements Serializable {
//     private Long studentId;
//     private String schoolName;
//     private Boolean graduated;
//
//     public Long getStudentId() {
//
//         return this.studentId;
//     }
//
//     public void setStudentId(Long studentId) {
//
//         this.studentId = studentId;
//     }
//
//     public String getSchoolName() {
//
//         return this.schoolName;
//     }
//
//     public void setSchoolName(String schoolName) {
//
//         this.schoolName = schoolName;
//     }
//
//     public Boolean getGraduated() {
//
//         return this.graduated;
//     }
//
//     public void setGraduated(Boolean graduated) {
//
//         this.graduated = graduated;
//     }
//
// }