public class Node {

    private double x;
    private double y;
    private int index;


    public Node(){
        x = 0;
        y = 0;
        index = -1;
    }

    public Node(double v1,double v2, int idx){
        x = v1;
        y = v2;
        index = idx;

    }

    @Override
    public String toString() {
        return "Node=" + this.index +" ";
    }


    public int getIndex() { return index; }
    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }



}
