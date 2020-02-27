import com.tests.add;
import org.junit.Test;

import static org.junit.Assert.*;

public class addTest {

    @Test
    public void add1() {
        assertEquals(5, add.add1(2,3));
    }
}