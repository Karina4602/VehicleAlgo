

public class edge
{
    vertex src, dest;
    double weight;
    edge next;

    public edge(vertex x, vertex y, double w)
    {
        src = x;
        dest = y;
        weight = w;
        next = null;
    }

    public void setedge(vertex src, vertex dest, double weight){
        this.src = src;
        this.dest = dest;
        this.weight = weight;
    }

    public void setedge2(edge edge){
        src = edge.getSrc();
        dest = edge.getDest();
        weight = edge.getWeight();
    }






    public vertex getSrc() {
        return src;
    }

    public vertex getDest() {
        return dest;
    }

    public double getWeight() {
        return weight;
    }

    public void displayEdge()
    {
        System.out.println("e("+src.getId()+","+dest.getId()+")- weight: "+weight);
    }
}
