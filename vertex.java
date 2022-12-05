public class vertex
{
    int id;
    double x, y;


    public vertex(int a, double b, double c)
    {
        id = a;
        x = b;
        y = c;
    }

    public vertex(){
        id = -999;
        x = -999;
        y = -999;
    }

    void setData(int a, double b, double c){
        id = a;
        x = b;
        y = c;
    }

    public double getX() {
        return x;
    }


    public double getY() {
        return y;
    }

    public int getId() {
        return id;
    }

    public void displayVertex()
    {
        System.out.println("\n ID: " + id + " (" + x + "," + y + ")");
    }
}