import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class BracketTests {

    @Test
    public void twoBracketRound1() {

        Bracket b = new Bracket(2);

        b.loadResults(1, "test-1.txt");
        String s = b.toJSON();
        System.out.println(s);
        s = s.replaceAll("\\s","");
        s = s.replaceAll("\\r","");
        s = s.replaceAll("\\n","");
        assertEquals(s, "{\"name\":\"-\",\"children\":[{\"name\":\"A\"},{\"name\":\"B\"}]}");
    }

    @Test
    public void twoBracketRound2() {
        Bracket b = new Bracket(2);

        b.loadResults(1, "test-1.txt");
        b.loadResults(2, "test-2.txt");
        String s = b.toJSON();
        System.out.println(s);
        s = s.replaceAll("\\s","");
        s = s.replaceAll("\\r","");
        s = s.replaceAll("\\n","");
        assertEquals(s, "{\"name\":\"A\",\"children\":[{\"name\":\"A\"},{\"name\":\"B\"}]}");
    }
}
