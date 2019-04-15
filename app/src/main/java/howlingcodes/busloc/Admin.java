package howlingcodes.busloc;

public class Admin extends User {
    boolean isValid;
    public Admin(){

    }

    public boolean isValid() {
        return isValid;
    }

    public void setValid(boolean valid) {
        this.isValid = valid;
    }

    public Admin(User u, boolean isValid){
        this.isValid = isValid;
        this.setEmail(u.getEmail());
        this.setFname(u.getFname());
        this.setLname(u.getLname());
        this.setType(u.getType());
    }
}
