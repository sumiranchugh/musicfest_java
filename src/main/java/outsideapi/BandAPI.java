package outsideapi;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class BandAPI {

    String name;
    List<LabelsAPI> labels = new ArrayList<LabelsAPI>();
}
