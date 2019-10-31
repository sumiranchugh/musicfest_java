package outsideapi;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class FestivalsAPI {
    String name;
    List<BandAPI> bands = new ArrayList<BandAPI>();
}
