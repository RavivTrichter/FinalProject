import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;


public class Vertices {

    private ArrayList<Node> Vertices;

    public Vertices(String filename){
        Vertices = new ArrayList<Node>();
        initVertices(filename);
    }


    public ArrayList<Node> getVertices() {
        return Vertices;
    }


    private void initVertices(String filename) {
        File file = new File(filename);
        BufferedReader reader = null;
        int sz = 0;
            try {
                reader = new BufferedReader(new FileReader(file));
                String line = "";
                while((line = reader.readLine()) != null ){
                    String[] coordinates = line.split(" ");
                    Vertices.add(new Node(Double.parseDouble(coordinates[1]), Double.parseDouble(coordinates[2]),sz++));
                }
            } catch(IOException e){
                e.printStackTrace();
            }

            finally{

                try{
                    if(reader != null)
                        reader.close();
                }
                catch(IOException e){
                    e.printStackTrace();
                }
            }

    }


    public ArrayList<Node> createRandomSubGraph( int size ) {

        ArrayList<Node> res = new ArrayList<Node>();
        Random rand = new Random();
        res.add(Vertices.get(0)); // always adding the restaurant
        int j;

        for (int i = 0; i < size; i++) {
            j = rand.nextInt(Vertices.size()-1) + 1; // in range [1,129] so the restaurant will always be there
            res.add(Vertices.get(j));
            Vertices.remove(j);
        }
        return res;
    }

}
