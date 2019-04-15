package howlingcodes.busloc;

public class Student {
    private String cne,fname,lname;
    private int classId;

    public void setLname(String lname) {
        this.lname = lname;

    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public void setClassId(int classId) {
        this.classId = classId;
    }

    public void setCne(String cne) {
        this.cne = cne;
    }

    public String getLname() {
        return lname;
    }

    public String getFname() {
        return fname;
    }

    public int getClassId() {
        return classId;
    }

    public String getCne() {
        return cne;
    }
    public Student(){

    }
    public Student(int classId,String cne,String fname,String lname){
        this.classId=classId;
        this.cne=cne;
        this.fname=fname;
        this.lname=lname;
    }
}
