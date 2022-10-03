import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * this is a Driver for the bracket class that produces a menu option for loading rounds and finding the opponents of the given player per rounds and writes a json string to Bracket.json
 */
public class Driver {

    public static void main(String[] args) throws FileNotFoundException {

        int numCompetitors = 128;
        Bracket w = new Bracket(numCompetitors);
        Scanner choice = new Scanner(System.in);

        StringBuilder optionString = new StringBuilder();
        optionString.append("Enter a choice\n1. Load next round results\n2. Print Matches for Player ...\n3. Quit");
        System.out.println(optionString.toString());

        int chosenOption = choice.nextInt();
        int counterOfOption1 = 0 ;
        int depth = (int) (Math.log(numCompetitors*2) / Math.log(2));

        while(chosenOption != 3) {
            if(chosenOption == 1) {
                if(counterOfOption1 > depth-1) {
                    System.out.println("No more matches to load. Tournament over.");
                }
                else {
                    counterOfOption1++;
                    w.loadResults(counterOfOption1, "round-" + counterOfOption1 + ".txt");
                    System.out.println("round-" + counterOfOption1 + ".txt\nLoading matches for round " + counterOfOption1);

                    File f = new File("Bracket.json");
                    PrintWriter out = new PrintWriter(f);
                    out.println(w.toJSON());
                    out.flush();
                    out.close();


                }
            }
            else if(chosenOption == 2) {
                System.out.println("Enter player name below :");
                choice.nextLine();
                String playerName = choice.nextLine().trim();
                System.out.println(w.getMatchesForPlayer(playerName));
            }
            System.out.println(optionString.toString());
            chosenOption = choice.nextInt();
        }
    }
}
