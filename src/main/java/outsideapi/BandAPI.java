package outsideapi;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BandAPI {

    String name;
    List<LabelsAPI> labels = new ArrayList<LabelsAPI>();
}
