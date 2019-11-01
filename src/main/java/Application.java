

import types.RecordLabel;

import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Application {

  public static void main(String[] args) {
    Mapper mapper = new Mapper();
    try (FileReader reader = new FileReader(Objects.requireNonNull(mapper.getClass().getClassLoader().getResource("api.json")).getFile())) {
      Collection<RecordLabel> recordLabels = mapper.mapData(reader);
      for (RecordLabel recordLabel : recordLabels) {
        System.out.println(recordLabel);
      }
    } catch (IOException e) {
      e.printStackTrace();
    }

  }


}
