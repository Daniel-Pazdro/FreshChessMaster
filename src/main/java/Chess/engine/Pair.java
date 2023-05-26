package Chess.engine;

import java.util.Objects;

public class Pair {

      int x;
     int y;
     public Pair(int corX, int corY){
         x = corX;
         y = corY;
     }

    public Pair(Pair p){
        x = p.x;
        y = p.y;
    }
     public int getX(){
         return x;}

    public void addCoordinates(Pair p){
         x = x + p.getX();
         y = y + p.getY();
    }

    public void addCoordinates(int x, int y) {
        this.x += x;
        this.y += y;
    }
    public int getY(){
        return y;
    }

    public void setX(int sX){
         x=sX;
    }
    public void setY(int sY){ y=sY;}
    public void setPair(Pair p){ x = p.getX(); y = p.getY();}



    public boolean equals(Object o){
         if(this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pair p = (Pair) o;
        return Objects.equals(x, p.getX()) &&
                Objects.equals(y, p.getY());
    }
    public int hashCode() {
        return Objects.hash(x, y);
    }





}
