package org.jenkinsci.plugins.casc.yaml;

import org.kohsuke.accmod.Restricted;
import org.kohsuke.accmod.restrictions.NoExternalUse;
import org.yaml.snakeyaml.reader.StreamReader;

import java.io.IOException;
import java.lang.reflect.Field;

/**
 * Hack StreamReader to track the source file/url configuration node have been parsed from
 * @author <a href="mailto:nicolas.deloof@gmail.com">Nicolas De Loof</a>
 */
@Restricted(NoExternalUse.class)
public class StreamReaderWithSource extends StreamReader {

    public StreamReaderWithSource(YamlSource source) throws IOException {
        super(source.read());
        try {
            final Field f = StreamReader.class.getDeclaredField("name");
            f.setAccessible(true);
            f.set(this, source.source());
        } catch (NoSuchFieldException | IllegalAccessException e) {
            // Can't track origin, maybe due to SecurityManager ?
            // never mind
        }
    }
}
