import java.io.File;
import java.io.IOException;
import java.util.*;

public class Main {
        vertex Vert[];
        edge ed;

        void txt_Import() {
            int n = 0;
            double maxRange = 0;
            File data = new File("data.txt");
            Scanner s;
            try {
                s = new Scanner(data);
                n = s.nextInt();
                maxRange = s.nextDouble();
                Vert = new vertex[n];
                //gets vertex for each item
                for (int i = 0; i < n; i++) {
                    Vert[i] = new vertex(i, s.nextDouble(), s.nextDouble());
                }
                //displays each vertex
                for (int i = 0; i < n; i++) {
                    Vert[i].displayVertex();
                }
                s.close();
            } catch (IOException e) {
                System.out.println(e);
            }
            build(maxRange,Vert,n);
        }


    void build(double max_range, vertex[] v,int n){
        //array of linklist with n array spots
        PQ_LL[] ll  = new PQ_LL[n];
        //g is our vertex
        for (int i = 0; i < n; i++){
            for (int j = 0; j < n; j++) {
                if (ll[i] == null){
                    //creates a new Linklist if an array spot is avlaible
                    ll[i] = new PQ_LL();
                }
                if (i != j  & check(v[i],v[j]) <= max_range) {
                    ed = new edge(v[i], v[j], check(v[i],v[j]));
                   // ed.displayEdge();
                    ll[i].sortedAdd(ed);
                }
            }

        }

        menu(ll,max_range,Vert);


    }


    int minKey(double key[], Boolean mstSet[], int V)
    {
        // Initialize min value
        double min = Integer.MAX_VALUE;
        int min_index = -1;

        for (int x = 0; x < V; x++)
            if (mstSet[x] == false && key[x] < min) {
                min = key[x];
                min_index = x;
            }

        return min_index;
    }

    // print the constructed MST
    // stored in parent[]
    void printMST(int parent[], double graph[][], int V)
    {
        System.out.println("Edge \tWeight");
        for (int i = 1; i < V; i++)
            System.out.println(parent[i] + " - " + i + "\t"
                    + graph[i][parent[i]]);
    }

    // Function to construct and print MST for a graph
    void primMST(double graph[][],PQ_LL[] ll)
    {
        int V = ll.length;
        // Array to store constructed MST
        int parent[] = new int[V];
        double key[] = new double[V];
        Boolean mstSet[] = new Boolean[V];
        // Initialize all keys as INFINITE
        for (int i = 0; i < V; i++) {
            key[i] = Integer.MAX_VALUE;
            mstSet[i] = false;
        }
        key[0] = 0; // Make key 0 so that this vertex is
        // picked as first vertex
        parent[0] = -1; // First node
        for (int count = 0; count < V - 1; count++) {
            int u = minKey(key, mstSet,V);
            mstSet[u] = true;
            for (int x = 0; x < V; x++)
                if (graph[u][x] != 0 && mstSet[x] == false && graph[u][x] < key[x]) {
                    parent[x] = u;
                    key[x] = graph[u][x];
                }
        }
        // print the constructed MST
        printMST(parent, graph,V);
    }


    double check(vertex a, vertex b){
        double x1 = a.getX();
        double y1 = a.getY();
        double x2 = b.getX();
        double y2 = b.getY();
        double pt1 = Math.pow((x2-x1),2);
        double pt2 = Math.pow((y2-y1),2);
        return Math.sqrt(pt1 + pt2);
    }

    void move(PQ_LL[] ll , int Vec,double newx,double newy,double max){
            //TODO: HAVE edit save to file
        //deletes self aj list
        ll[Vec].clear();

        //Goes through list and deletes edges in AJ
        for (int i =0; i < ll.length; i++){
            int x = 0;
            while ( x != ll[i].size()) {
                if ( ll[i].size() > 0 & Vert[Vec].equals( ll[i].getedge(x).getDest())) {
                    if (ll[i].getedge(x).next == null){
                        ll[i].remove(x);
                        break;
                    }
                    ll[i].remove(x);
                }
                x++;
            }
        }
           //replaces V
        Vert[Vec].setData(Vec,newx,newy);
            //re adds dajacny list
        for (int i =0; i< ll.length; i++){
            if ( i!=Vec &   check(Vert[Vec],Vert[i]) <= max) {
                ed = new edge(Vert[Vec], Vert[i], check(Vert[Vec], Vert[i]));
                //ed.displayEdge();
                ll[Vec].sortedAdd(ed);
            }
        }


//TODO: SIMPLFY
        //adds edges in other sections
        for (int i =0; i< ll.length; i++){
            if (Vec != i  & check(Vert[Vec],Vert[i]) <= max) {
                ed = new edge(Vert[i], Vert[Vec], check(Vert[Vec], Vert[i]));
                //ed.displayEdge();
                ll[i].sortedAdd(ed);
            }
        }
        display(ll);
    }

    void display(PQ_LL[] ll){
            for (int i =0; i < ll.length; i++){
                System.out.println(i);
                int x = 0;
                while ( x != ll[i].size()) {
                    ll[i].getedge(x).displayEdge();
                    x++;
                }
            }
    }
    void displayone(PQ_LL[] ll, int i){
        int x = 0;
        while ( x != ll[i].size()) {
            ll[i].getedge(x).displayEdge();
            x++;
        }
    }

    void dfs(int v,PQ_LL[] ll,vertex[] vert){
        boolean[] visited = new boolean[ll.length];
        Stack<vertex> s = new Stack<vertex>();


        s.push(vert[v]);

        while (!s.isEmpty()){
            v = s.peek().getId();
            Vert[v] =  s.pop();

            if (!visited[v]){

                visited[v] = true;
                System.out.println(v);

                for (int i =0; i < ll[v].size(); i++){
                    if (!visited[ll[v].getedge(i).getDest().getId()]){
                        s.push(ll[v].getedge(i).getDest());
                    }
                }
            }

        }



    }

    void bfs(int v, PQ_LL[] ll,vertex[] vert) {
        //ask for a give vechile displays reachable items
        boolean[] visited = new boolean[ll.length];

        vertex w, temp;
        Queue<Integer> Q = new LinkedList<Integer>();

        visited[v] = true;
        Q.add(v);

        while (!Q.isEmpty() ) {

            v = Q.poll();
            System.out.println(v);

            for (int i = 0; i < ll[v].size(); i++) {

                if (!visited[ll[v].getedge(i).getDest().getId()]) {
                    Q.add(ll[v].getedge(i).getDest().getId());
                    visited[ll[v].getedge(i).getDest().getId()] = true;
                }

            }

        }
    }

    void OLDprim(int v, PQ_LL[] ll,vertex[] vert){
        ArrayList<edge> t = new ArrayList<>();
//        boolean[] visited = new boolean[ll.length];
        ArrayList<Integer> tv = new ArrayList<Integer>();
        double[] w = new double[ll.length];
        Arrays.fill(w, Double.MAX_VALUE);
        //Arrays.fill(tv,Integer.MAX_VALUE);
        tv.add(v);

        int n = ll.length;
        double minw = Double.MAX_VALUE;

        int x = 1;
            while (tv.size() < (n -1)){
                for (int i =0; i < ll[v].size(); i++){


            }
//                for (int i =0; i < ll[v].size(); i++){
//                    //todo: cehck if not visited
//                    if (ll[v].getedge(i).getWeight() < minw){
//                        minw = ll[v].getedge(i).getWeight();
//                    }
//                }

            }
            if (t.size() < n-1){
                System.out.println("No spanning Tree");
            }

    }






    static double[][] convert(PQ_LL[] ll)
    {
        // creates matrix for prim
        double [][]matrix = new double[ll.length][ll.length];



        //-1 will be ignored by prim
        for(int i = 0; i < ll.length; i++)
        {
            for(int j = 0; j < ll[i].size(); j++){
                    matrix[i][ll[i].getedge(j).getDest().getId()] = ll[i].getedge(j).getWeight();
            }
        }
        return matrix;
    }

    static void printMatrix(double[][] adj, int V)
    {
        for(int i = 0; i < V; i++)
        {
            for(int j = 0; j < V; j++)
            {
                System.out.print(adj[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    double minweight(int v, PQ_LL[] ll,vertex[] vert){
            double minw = 9999999;
            int Imin;
            for (int i =0; i < ll[v].size(); i++){
                //todo: cehck if not visited
                if (ll[v].getedge(i).getWeight() < minw){
                    minw = ll[v].getedge(i).getWeight();
                }
            }
            return minw;
     }







    void Dijkstra_Algorithm(PQ_LL[] ll, int start, int dest,vertex[] Vert){
            int x = Vert.length;
            //TODO create, your too tired double check code when you get rest, remember to write down dates and thigns tuat need clarfifcation
        boolean[] visted = new boolean[ll.length];
        double[] Distance = new double[ll.length];
        int[] path = new int[ll.length];

        visted[start] = true;
        double mx = Double.MAX_VALUE;

        Arrays.fill(Distance, Double.MAX_VALUE);
        Distance[start] = 0;
        for (int i =0; i < ll[start].size(); i++){
            Distance[ll[start].getedge(i).getDest().getId()] =  ll[start].getedge(i).getWeight();
        }

        //smalles node in adjacent list
      path[start] = start;
        for (int i =0; i < ll[start].size(); i++){
            path[ll[start].getedge(i).getDest().getId()] =  start;
        }


        int current = start;
        int mxid =0;

        System.out.println("a");
        //part 2 iteration
        while (!visted[dest]){

            for(x =0; x< ll[current].size(); x++){
                if ( !visted[ll[current].getedge(x).getDest().getId()] & ll[current].getedge(x).getWeight() < mx ){
                    mx = ll[current].getedge(x).getWeight();
                    mxid = ll[current].getedge(x).getDest().getId();

                }
            }
            current = mxid;

            visted[mxid] = true;
            for (int j =0 ;j < ll[mxid].size(); j++){
                if (Distance[j] + ll[mxid].getedge(j).getWeight() < Distance[mxid]){
                    Distance[mxid] = Distance[j] + ll[mxid].getedge(j).getWeight();
                    path[mxid] = j;
                    System.out.println("ab");
                    break;
                }

            }




        }

    }

    int smallnodeinadj(PQ_LL[] ll,int x, double[] Distance, boolean[] visited,vertex[] path){
        int min_id = x;
        double min_distance = Double.MAX_VALUE;
//LOOP IS FINE HERE
        int all = 0;
        for (int i = 0; i < ll[x].size(); i++){
            //System.out.println(i);
            Distance[ll[x].getedge(i).getDest().getId()] = ll[x].getedge(i).getWeight();

            if(ll[x].getedge(i).getWeight() < min_distance & !visited[i]){
                min_distance = ll[x].getedge(i).getWeight();
                min_id = i;
            }
            //write somethig if it cheked all its children
        }
        //todo: REFORMAT IN CLEVERER WAY
        //checks if children were aleady viited to acoid infit loop
        for (int j =0; j < ll[x].size();j++){
            if (visited[ll[x].getedge(j).getDest().getId()]){
                all++;
            }
        }
        if (all == ll[x].size()){
            min_id = path[x].getId();
        }

        return min_id;

    }



    void DA(PQ_LL[] ll, int start, int dest,vertex[] Vert) {
        boolean[] visted = new boolean[ll.length];
        double[] Distance = new double[ll.length];
        int[] path = new int[ll.length];

        //0 is visted
        visted[start] = true;
        //0 distisance is 0 others are infinite
        Arrays.fill(Distance, Double.MAX_VALUE);
        Distance[start] = 0;
        //0's adjacent edges dest distance are marked as its edge weights
        for (int i = 0; i < ll[start].size(); i++) {
            Distance[ll[start].getedge(i).getDest().getId()] = ll[start].getedge(i).getWeight();

        }
        //0 path is 0
        path[start] = start;
        //0s's adjacent path are marked as 0
        for (int i = 0; i < ll[start].size(); i++) {
            path[ll[start].getedge(i).getDest().getId()] = start;
        }
        //gives us a base to find the current id we are on
        int current = start;
        //starts loop until 4 is ccisted
        while (!visted[dest]) {
            int[] tempcca;

            current = small(ll, current, Distance, visted, path);
            //System.out.println(current);
            visted[current] = true;

            int all = 0;
            for (int i = 0; i < ll[current].size(); i++) {
                tempcca = new int[ll[current].size()];
                if (!visted[ll[current].getedge(i).getDest().getId()]) {
                    //if distance 1 + edge(1,2).weight is less than distance 2 it saves the lesser distance path and weight
                    if (Distance[current] + ll[current].getedge(i).getWeight() < Distance[ll[current].getedge(i).getDest().getId()]) {
                        Distance[ll[current].getedge(i).getDest().getId()] = Distance[current] + ll[current].getedge(i).getWeight();
                        path[ll[current].getedge(i).getDest().getId()] = current;
                    } else {
                        tempcca[i] = ll[current].getedge(i).getDest().getId();
                        all++;
                        //add to temp cant change array
                    }

                }
                else{
                    tempcca[i] = ll[current].getedge(i).getDest().getId();
                    all++;
                }
                //if I go thorugh entire adjacent path without changine anything i must go back to ccurretns parent
                if (all >= ll[current].size()){
                    break;
                }

            }
            System.out.println(current);

//                if (ll[current].size() == all){
//                 current = path[current];
//               }
////
//
        }
    }
    int small(PQ_LL[] ll,int x, double[] Distance, boolean[] visited,int[] path) {
        int min_id = x;
        double min_distance = Double.MAX_VALUE;

        //gets adjacent id with smallest weight and not visted
        for (int i = 0; i < ll[x].size(); i++) {
            //if adjecents weight is less than min dist and not marked visted
            if(!visited[i] & ll[x].getedge(i).getWeight() < min_distance ){
                //sets min distance
                min_distance = ll[x].getedge(i).getWeight();
                min_id = i;
            }
        }
        return min_id;
    }

    void menu(PQ_LL[] ll, double max_range, vertex[] Vert){
            int cho =0;
        int temp;
        Scanner sc = new Scanner(System.in);
            while (cho != 8){
                options();
                cho = sc.nextInt();
                sc.nextLine();
                switch (cho) {
                    case 1:
                        //Display all edges
                        display(ll);
                        break;
                    case 2:
                        //disaplad adjacent vechiles
                        System.out.println("Please enter your Vehicle");
                         temp = sc.nextInt();
                         sc.nextLine();
                         displayone(ll, temp);
                        break;
                    case 3:
                        System.out.println("Please enter your Vehicle you wish to move");
                         int vec = sc.nextInt();
                         sc.nextLine();
                         System.out.println("Please enter the where in the x axis you'd like to go");
                         double x = sc.nextInt();
                        sc.nextLine();
                        System.out.println("Please enter the where in the y axis you'd like to go");
                        double y = sc.nextInt();
                        sc.nextLine();
                        move(ll,vec,x,y,max_range);

                        //move a vechile
                        break;
                    case 4:
                        System.out.println("Please enter your Vehicle");
                        temp = sc.nextInt();
                        sc.nextLine();
                        dfs(temp,ll,Vert);
                        //dfs
                        break;
                    case 5:
                        System.out.println("Please enter your Vehicle");
                        temp = sc.nextInt();
                        sc.nextLine();
                        bfs(temp,ll,Vert);
                        //bfs
                        break;
                    case 6:
                        double[][] adjMatrix = convert(ll);
                        //printMatrix(adjMatrix, ll.length);
                        primMST(adjMatrix,ll);
                        //mst
                        break;
                    case 7:
                        //shoretest path
                        System.out.println("Please enter your Vehicle source");
                        temp = sc.nextInt();
                        sc.nextLine();
                        System.out.println("Please enter your Vehicle destination");
                        int dest = sc.nextInt();
                        sc.nextLine();
                        DA(ll,temp,dest,Vert);
                        break;
                    case 8:
                        //exit
                        System.out.print("Thank you for taking part, goodbye!");
                        break;
                    default:
                        System.out.println("Sorry, but" + cho + " is not a valid input.");
                        break;
                }
            }




    }

    void options(){
            System.out.println("1: Display all edges ");
            System.out.println("2: Display Adjacent Vehicles");
            System.out.println("3: Move a Vehicles");
            System.out.println("4: DFS");
            System.out.println("5: BFS");
            System.out.println("6: MST");
            System.out.println("7: Shortest Path (DOES NOT WORK)");
            System.out.println("8: Quit");

    }



    public static void main(String[] args){
        Main a= new Main();
        a.txt_Import();


    }


}

