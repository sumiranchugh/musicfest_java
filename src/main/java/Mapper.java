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

public class Mapper {
    Map<String, Band> bands = new HashMap<String, Band>();
    Map<String, RecordLabel> labels = new HashMap<String, RecordLabel>();


    public Collection<RecordLabel> mapData(FileReader reader) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        List<FestivalsAPI> festivalsAPIS = mapper.readValue(reader, new TypeReference<List<FestivalsAPI>>() {});
        return processFestivalsList(festivalsAPIS);
    }

    public Collection<RecordLabel> processFestivalsList(List<FestivalsAPI> festivalsAPIS) {

        festivalsAPIS.forEach(festivalsAPI -> {
            Fest fest = Fest.builder().name(festivalsAPI.getName()).build();
            buildLabels(festivalsAPI, fest);
        });
        return labels.values();
    }

    private void buildLabels( FestivalsAPI festivalsAPI, Fest fest) {
        festivalsAPI.getBands().forEach(bandAPI -> {
            mapFestToBands(fest, bandAPI);
            bandAPI.getLabels().forEach(labelsAPI ->{
                mapBandsToLabels(bandAPI, labelsAPI);
            });
        });
    }

    private void mapBandsToLabels( BandAPI bandAPI, LabelsAPI labelsAPI) {
        if (labels.containsKey(labelsAPI.getName())){
            Set<Band> bandSet = labels.get(labelsAPI.getName()).getBands();
            for (Band b : bandSet){
                if (b.getName().equals(bandAPI.getName()))
                    continue;
                bandSet.add(this.bands.get(bandAPI.getName()));
            }
        } else {
            Set<Band> bandSet = new HashSet<>();
            bandSet.add(bands.get(bandAPI.getName()));
            RecordLabel label = RecordLabel.builder().name(labelsAPI.getName()).bands(bandSet).build();
            labels.put(label.getName(), label);
        }
    }

    private void mapFestToBands( Fest fest, BandAPI bandAPI) {
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
