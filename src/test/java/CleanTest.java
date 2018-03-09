import net.karlmartens.docfx.Clean;
import org.junit.Assert;
import org.junit.Test;

import java.io.InputStream;
import java.nio.file.Path;
import java.util.List;

public class CleanTest {

    @Test
    public void testParseMetadata() throws Exception {
        StringBuilder expected = new StringBuilder();
        expected.append("dev/sdk/framework\n");
        expected.append("dev/sdk/core\n");


        try (InputStream in = getClass().getResourceAsStream("example.json")) {
            List<Path> paths = Clean.parseMetadata(in);

            StringBuilder actual = new StringBuilder();
            for (Path p : paths) {
                actual.append(p.toString()).append("\n");
            }
            Assert.assertEquals("Metadata Output paths", expected.toString(), actual.toString());
        }
    }

    @Test
    public void testParseBuild() throws Exception {
        try (InputStream in = getClass().getResourceAsStream("example.json")) {
            Path path = Clean.parseBuild(in);
            Assert.assertEquals("Build Output path", "_site", path.toString());
        }
    }

}
