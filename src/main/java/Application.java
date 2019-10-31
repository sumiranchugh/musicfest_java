import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import outsideapi.BandAPI;
import outsideapi.FestivalsAPI;
import outsideapi.LabelsAPI;
import types.Band;
import types.Fest;
import types.RecordLabel;

import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Application {

  public static void main(String[] args) {
    Application application = new Application();
    try (FileReader reader = new FileReader(Objects.requireNonNull(application.getClass().getClassLoader().getResource("api.json")).getFile())) {
      application.mapData(application, reader);
    } catch (IOException e) {
      e.printStackTrace();
    }

  }

  private void mapData(Application application, FileReader reader) throws IOException {
    ObjectMapper mapper = new ObjectMapper();
    List<FestivalsAPI> festivalsAPIS = mapper.readValue(reader, new TypeReference<List<FestivalsAPI>>() {});
    Map<String, Band> bands = new HashMap<String, Band>();
    Map<String, RecordLabel> labels = new HashMap<String, RecordLabel>();
    festivalsAPIS.forEach(festivalsAPI -> {
      Fest fest = Fest.builder().name(festivalsAPI.getName()).build();
      application.buildLabels(bands, labels, festivalsAPI, fest);
    });
    for (RecordLabel label : labels.values()) {
      System.out.println(label);
    }
  }

  private void buildLabels(Map<String, Band> bands, Map<String, RecordLabel> labels, FestivalsAPI festivalsAPI, Fest fest) {
    festivalsAPI.getBands().forEach(bandAPI -> {
      mapFestToBands(bands, fest, bandAPI);
      bandAPI.getLabels().forEach(labelsAPI ->{
        mapBandsToLabels(bands, labels, bandAPI, labelsAPI);
      });
    });
  }

  private void mapBandsToLabels(Map<String, Band> bands, Map<String, RecordLabel> labels, BandAPI bandAPI, LabelsAPI labelsAPI) {
    if (labels.containsKey(labelsAPI.getName())){
      labels.get(labelsAPI.getName()).getBands().add(bands.get(bandAPI.getName()));
    } else {
      Set<Band> bandSet = new HashSet<>();
      bandSet.add(bands.get(bandAPI.getName()));
      RecordLabel label = RecordLabel.builder().name(labelsAPI.getName()).bands(bandSet).build();
      labels.put(label.getName(), label);
    }
  }

  private void mapFestToBands(Map<String, Band> bands, Fest fest, BandAPI bandAPI) {
    if (bands.containsKey(bandAPI.getName())){
      Band band = bands.get(bandAPI.getName());
      band.getFests().add(fest);
    } else {
      Set<Fest> fests = new HashSet<>();
      fests.add(fest);
      Band band = Band.builder().name(bandAPI.getName()).fests(fests).build();
      bands.put(band.getName(), band);
    }
  }
}
