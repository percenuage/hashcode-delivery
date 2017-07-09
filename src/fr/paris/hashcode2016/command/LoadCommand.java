package fr.paris.hashcode2016.command;

import fr.paris.hashcode2016.model.Drone;
import fr.paris.hashcode2016.model.ProductType;

/**
 * Created by Axel on 13/02/2016.
 */
public class LoadCommand implements Command {

    private Drone drone;

    public LoadCommand(Drone drone) {
        this.drone = drone;
    }

    @Override
    public void execute() {
        drone.load(null);
    }
}
