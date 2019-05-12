import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

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

    public ArrayList<Vertex> createSubGraph( int size, int ModuloEqual) {
        ArrayList<Vertex> res = new ArrayList<Vertex>();
        res.add(Vertices.get(0)); // where the restaurant is located.
        for (int i = 1; res.size() < size; i++) {
            if(i % 2 == ModuloEqual)
                res.add(Vertices.get(i));
        }
        return res;
    }

}
