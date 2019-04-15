package howlingcodes.busloc;

public class Parent extends User{
    private Student son;
    private Position position;

    public void setSon(Student son) {
        this.son = son;
    }

    public Student getSon() {
        return son;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public Parent(){

    }
    public Parent(User u,Student s,Position p){
        this.setEmail(u.getEmail());
        this.setFname(u.getFname());
        this.setLname(u.getLname());
        this.setSon(s);
        this.setPosition(p);

    }

}
