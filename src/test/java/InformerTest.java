import org.junit.Test;
import ru.explosivegamex.gardevoir.util.Inform;

import java.util.HashMap;
import java.util.Map;

public class InformerTest {

    @Test
    public void replaceVarsInString() {
        String example = "Gamer %player% is %status%.";
        String handledExample = "Gamer thebadomon is online.";

        Map<String, String> replacements = new HashMap<>();
        replacements.put("player", "thebadomon");
        replacements.put("status", "online");

        assert Inform.replaceTokens(example, replacements).equals(handledExample);
    }
}
