public class Vertex {

    private double x;
    private double y;
    private int index;


    public Vertex(){
        x = 0;
        y = 0;
        index = -1;
    }

    public Vertex(double v1,double v2, int idx){
        x = v1;
        y = v2;
        index = idx;

    }

    @Override
    public String toString() {
        return "Vertex=" + this.index +" ";
    }


    public int getIndex() { return index; }
    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }



}
