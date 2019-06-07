import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;


public class Vertices {

    private ArrayList<Vertex> Vertices;

    public Vertices(String filename){
        Vertices = new ArrayList<Vertex>();
        initVertices(filename);
    }


    public ArrayList<Vertex> getVertices() {
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
                    Vertices.add(new Vertex(Double.parseDouble(coordinates[1]), Double.parseDouble(coordinates[2]),sz++));
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


    public ArrayList<Vertex> createRandomSubGraph( int size ) {

        ArrayList<Vertex> res = new ArrayList<Vertex>();
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
