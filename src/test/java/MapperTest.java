import org.junit.jupiter.api.Test;
import outsideapi.BandAPI;
import outsideapi.FestivalsAPI;
import outsideapi.LabelsAPI;
import types.Band;
import types.Fest;
import types.RecordLabel;

import java.util.Arrays;
import java.util.Collection;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class MapperTest {

  Mapper mapper = new Mapper();
  @Test
  void mapData() {
    LabelsAPI label1 = new LabelsAPI("label1");
    LabelsAPI label2 = new LabelsAPI("label2");
    LabelsAPI label3 = new LabelsAPI("label3");
    BandAPI band1 = new BandAPI("band1",Arrays.asList(label1));
    BandAPI band2 = new BandAPI("band1",Arrays.asList(label2));
    BandAPI band3 = new BandAPI("band1",Arrays.asList(label3));
    FestivalsAPI fest1 = new FestivalsAPI("fest1",Arrays.asList(band1, band2, band3));
    Collection<RecordLabel> recordLabels = mapper.processFestivalsList(Arrays.asList(fest1));
    Fest fest = Fest.builder().name("fest1").build();
    Band bands1 = Band.builder().name("band1").fests(Set.of(fest)).build();
    Band bands2 = Band.builder().name("band2").fests(Set.of(fest)).build();
    Band bands3 = Band.builder().name("band3").fests(Set.of(fest)).build();
    RecordLabel recordLabel = RecordLabel.builder().name("label1").bands(Set.of(bands1)).build();
    RecordLabel recordLabel2 = RecordLabel.builder().name("label2").bands(Set.of(bands2)).build();
    RecordLabel recordLabel3 = RecordLabel.builder().name("label3").bands(Set.of(bands3)).build();
    assertEquals(recordLabels.size(),3);
    assertArrayEquals(recordLabels.toArray(),new RecordLabel[]{recordLabel, recordLabel2, recordLabel3});
  }

  @Test
  void mapData_empty() {

    FestivalsAPI fest1 = new FestivalsAPI();
    Collection<RecordLabel> recordLabels = mapper.processFestivalsList(Arrays.asList(fest1));

    assertEquals(recordLabels.size(),0);

  }
}