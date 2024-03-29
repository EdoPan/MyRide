package it.dsmt.myRide.controller;
import com.rqlite.NodeUnavailableException;
import com.rqlite.Rqlite;
import com.rqlite.RqliteFactory;

public class DBController {
    private static DBController instance = null;
    private Rqlite connection = null;

    public static  DBController getInstance(){
        if (instance == null) {
            try{
                instance = new DBController();
            } catch (NodeUnavailableException e){
                System.out.println(e.getMessage());
            }
        }
        return instance;
    }

    public Rqlite getConnection(){
        return DBController.getInstance().connection;
    }

    public DBController () throws NodeUnavailableException {
        String protocol = System.getenv("RQLITE_PROTOCOL");
        String ip = System.getenv("RQLITE_IP");
        int port = Integer.parseInt(System.getenv("RQLITE_PORT"));
        this.connection = RqliteFactory.connect(protocol, ip, port);
    }
}