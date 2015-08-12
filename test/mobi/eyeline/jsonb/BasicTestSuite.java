package mobi.eyeline.jsonb;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Created by Artem Voronov on 11.08.2015.
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
        LexerTest.class,
        ParserTest.class,
        UnmarshallerTest.class,
        LoadTest.class
})
public class BasicTestSuite {
}
