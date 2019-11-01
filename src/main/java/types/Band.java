package types;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@Builder
public class Band {

    String name;
//    @EqualsAndHashCode.Exclude
    Set<Fest> fests = new HashSet<>();
}
