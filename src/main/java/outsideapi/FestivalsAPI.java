package outsideapi;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FestivalsAPI {
    String name;
    List<BandAPI> bands = new ArrayList<BandAPI>();
}
