package lab5;

import java.util.*;
import java.util.stream.Collectors;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class Lab5 {

    public static class Person{
        protected String firstName, middleName, lastName, address;
        protected int id;
        protected int age;
        Person(int id, String firstName, String middleName, String lastName,String address, int age){
            this.id=id;
            this.firstName=firstName;
            this.middleName=middleName;
            this.lastName=lastName;
            this.address=address;
            this.age=age;
        }
        public Person(){}
        public String getFirstName(){
            return this.firstName;
        }
        public String getMiddleName(){
            return this.middleName;
        }
        public String getLastName(){
            return this.lastName;
        }
        public int getId(){return this.id;}
        public int getAge(){
            return this.age;
        }
        public String getAddress(){
            return this.address;
        }
    }

    /**
     * Patient class. Inherited from Person.
     */
    public static class Patient extends Person implements Comparable<Patient>{
        String socialGroup;
        String bloodGroup;
        private Patient(Builder b) {
            super(b.id,b.firstName, b.middleName, b.lastName,b.address, b.age);
            this.socialGroup=b.socialGroup;
            this.bloodGroup=b.bloodGroup;
        }
        public Patient(int id,String fname, String mname, String lname, String add,Integer age, String sG, String bG){
            super(id,fname, mname, lname, add, age);
            this.socialGroup=sG;
            this.bloodGroup=bG;
        }
        public Patient(){
            super();
        }
        public String getSocialGroup(){return this.socialGroup;}
        public String getBloodGroup(){return this.bloodGroup;}

        @Override
        public int compareTo(Patient other){
            return Integer.compare(getAge(), other.getAge());
        }

        public static class Builder{
            @NotNull(message = "Cannot be null")
            private String firstName;
            @NotNull(message = "Cannot be null")
            private String middleName;
            @NotNull(message = "Cannot be null")
            private String lastName;

            private String socialGroup;
            @Size(min=2, max=2, message = "Blood group can should contain only 2 characters")
            private String  bloodGroup;
            private String  address;
            @Min(value=0, message="Age should be bigger than 0")
            private int age;
            private int id;
            public Builder(){}
            public Builder setFirstName(String name){
                this.firstName = name;
                return this;
            }
            public Builder setMiddleName(String name){
                this.middleName = name;
                return this;
            }
            public Builder setLastName(String name){
                this.lastName = name;
                return this;
            }
            public Builder setAddress(String address){
                this.address = address;
                return this;
            }
            public Builder setBloodGroup(String blood_group){
                this.bloodGroup=blood_group;
                return this;
            }
            public Builder setId(int id){
                this.id=id;
                return this;
            }
            public Builder setSocialGroup(String social_group){
                this.socialGroup=social_group;
                return this;
            }
            public Builder setAge(int age){
                this.age=age;
                return this;
            }
            public Patient build(){
                Patient p=new Patient(this);
                validate(p);
                return p;
            }

            private void validate(Patient p) {
                ValidatorFactory f= Validation.buildDefaultValidatorFactory();
                Validator validator=f.getValidator();
                Set<ConstraintViolation<Builder>> constraintViolationSet=validator.validate(this);
                String e=new String();
                e="";
                for (var c:constraintViolationSet){
                    e+="Message "+c.getMessage()+"\n";

                }
                if(e!=""){
                    throw new IllegalArgumentException(e);
                }
            }

        }

        @Override
        public String toString() {
            return  firstName + '\n' +
                    id + '\n' +
                    middleName + '\n' +
                    lastName + '\n' +
                    address + '\n' +
                    age + '\n' +
                    socialGroup + '\n' +
                    bloodGroup + '\n';
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Patient patient = (Patient) o;
            return Objects.equals(firstName, patient.firstName) && Objects.equals(middleName, patient.middleName) &&Objects.equals(lastName, patient.lastName) &&Objects.equals(address, patient.address) &&Objects.equals(age, patient.age) && Objects.equals(socialGroup, patient.socialGroup) && Objects.equals(bloodGroup, patient.bloodGroup);
        }

        @Override
        public int hashCode() {
            return Objects.hash(firstName, middleName,lastName, address, age, bloodGroup);
        }
    }
    static class MyComparator implements Comparator<Patient> {
        @Override
        public int compare(Patient o1, Patient o2) {
            Integer age1=o1.getAge();
            Integer age2=o2.getAge();
            return age1.compareTo(age2);
        }
    }
    public static class Hospital{
        //        Patient[] patients[];
        List<Patient> patients= new ArrayList<>();
        String name;
        int id;
        public Hospital(String name){
            this.name=name;
        }
        private Hospital(Builder b) {
            this.id=b.id;
            this.name=b.name;
        }
        public static class Builder{
            int id;
            String name;
            public Builder(){}
            public Builder setName(String name){
                this.name= name;
                return this;
            }
            public Builder setId(int id){
                this.id= id;
                return this;
            }
            public Hospital build(){
                Hospital p=new Hospital(this);
                return p;
            }
        }
        public Hospital(){}
        public void add_patient(Patient p){
            patients.add(p);
        }
        public void print(){
            for(Patient p:patients){
                System.out.println(p.toString());
            }
        }
        public void sort_p(){
            Collections.sort(patients, new MyComparator());
        }
        public List<Patient> filter(String name){
            List<Patient> p= new ArrayList<>();
            for (Patient patient : patients) {
                if (Objects.equals(patient.firstName, name)) {
                    p.add(patient);
                }
            }
            return p;
        }

        public void update(int age){
            for (Patient p: patients){
                if (p.age>=age){
                    p.socialGroup="Old" ;
                }
            }
        }
        public void sort_p_s(){
            patients=patients.stream().sorted(Comparator.comparing(Patient::getAge)).collect(Collectors.toList());
        }
        public List<Patient> filter_s(String name) {
            return patients.stream().filter(pat->pat.getFirstName().equals(name)).collect(Collectors.toList());
        }
    }

}
