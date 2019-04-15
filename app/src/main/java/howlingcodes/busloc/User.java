package howlingcodes.busloc;

public class User {

    private String email;
    private String fname;
    private String lname;
    private String type;
    public User(){
    }



    public String getEmail() {
        return email;
    }

    public String getFname() {
        return fname;
    }

    public String getLname() {
        return lname;
    }

    public String getType() {
        return type;
    }



    public void setEmail(String email) {
        this.email = email;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public void setType(String type) {
        this.type = type;
    }

    public User(String email, String fname, String lname, String type){

        this.email=email;
        this.fname=fname;
        this.lname=lname;
        this.type=type;
    }
}
