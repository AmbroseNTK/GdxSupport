package kiet.nguyentuan.demo.Simple3DGame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.math.Vector3;

public class MapGenerator {
    public MapGenerator(){

    }
    public static CellTypes[][] loadMap(String fileName) {
        CellTypes[][] map;
        String data = Gdx.files.internal(fileName).readString();
        String[] lines = data.split("\r\n");
        width = Integer.parseInt(lines[0]);
        height = Integer.parseInt(lines[1]);

        map = new CellTypes[width][height];
        for(int i=0;i<width;i++) {
            String[] tokens = lines[i + 2].split(" ");
            for (int j = 0; j < height; j++) {
                int cell = Integer.parseInt(tokens[j]);
                switch (cell) {
                    case 0:
                        map[i][j] = CellTypes.GROUND;
                        break;
                    case 1:
                        map[i][j] = CellTypes.WALL;
                        break;
                    case 2:
                        map[i][j] = CellTypes.EMPTY;
                }
            }
        }
        playerPos=new Vector3(10,0,50);
        return map;
    }
    public static int width;
    public static int height;
    public static Vector3 playerPos;
}
