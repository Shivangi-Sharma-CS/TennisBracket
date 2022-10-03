import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * The solution for A1 is complete
 */
public class Bracket {

    private String[] arr;
    private int sizeOfTree;
    private int numCompetitors;

    /**
     Constructor
     @param numCompetitors the number of competitors in the tournament
     **/
    public Bracket(int numCompetitors) {
        arr = new String[numCompetitors*2];
        sizeOfTree = numCompetitors*2;
        this.numCompetitors = numCompetitors;
        fillTreeWithDash(1);
    }

    /**
     Return a String of the matches for a given player. Each match should appear on its own line and should only include the opponent name.
     The String starts with the first round match (opponent) for the player, round 2 (if applicable) is on line 2, etc.
     @return the string for the opponents in each line starting from round 1 and so on.
     **/
    public String getMatchesForPlayer(String player) {
        StringBuilder matches = new StringBuilder();
        int playerExists = 0;
        for(int i = arr.length-1; i > 0; i--) {
            if(arr[i].equals(player)) {
                playerExists = i;
                break;
            }
        }

        while ((playerExists > 0) && (arr[playerExists].equals(player))) {
            if(playerExists % 2 == 0) {
                matches.append(arr[playerExists + 1]).append("\n");
                playerExists = playerExists/2;
            }
            else {
                matches.append(arr[playerExists - 1]).append("\n");
                playerExists = playerExists/2;
            }
        }
        return matches.toString();
    }

    /**
     Load the results for a given round into the Bracket
     @param round - the round in the tournament being loaded
     @param resultsFile - the file where the results for the given round can be found
     **/
    public void loadResults(int round, String resultsFile) {
        try {
            int startIndex = sizeOfTree - playersPlaying(round);
            File f = new File(resultsFile);
            Scanner s = new Scanner(f);
            int i = startIndex;
            while(s.hasNextLine()) {
                arr[i] = s.nextLine();
                i++;
                sizeOfTree--;
            }
        }
        catch (FileNotFoundException e) {
            e.getMessage();
        }
    }

    /**
     Return the bracket in JSON format
     @return the string for the binary tree in a json format
     **/
    public String toJSON() {
        return preOrderTraversal(1);
    }

    /***************************************************************************************************************************************
     * Helper methods
     ***************************************************************************************************************************************/

    /**
     * This creates an empty tree with dashes in them
     * @param index the index should be 1 to recursively fill left and right subtree
     */
    private void fillTreeWithDash(int index) {
        if(index > arr.length-1) {
            return;
        }
        else {
            arr[index] = "-";
            fillTreeWithDash(2*index);
            fillTreeWithDash(2*index + 1);
        }
    }

    /**
     * this method calculates the number of players in a round
     * @param round the round of the tournament
     * @return the number of players playing in the round
     */
    private int playersPlaying(int round) {
        int playersPlaying = numCompetitors*2;
        for (int i = 1 ; i <= round ; i++) {
            playersPlaying = playersPlaying/2;
        }
        return playersPlaying;
    }

    /**
     * this method traverses in pre-order format and returns the string in json string format
     * @param index the index should be 1 to start the traversal and recursively fill left subtree and right subtree in a json string format
     * @return the json string
     */
    /*
        json = {"name":<name>} or {"name":<name>,"children":[LC,RC]}
     */
    private String preOrderTraversal(int index) {
        if(index > arr.length-1) {
            return "";
        }
        else {
            String jsonString = "{ \"name\": " + "\"" + arr[index] + "\"";

            if( (2*index > arr.length-1 )) {
                return jsonString + "}";
            }
            else {
                return jsonString + ",\"children\":[" + preOrderTraversal(2*index) + "," + preOrderTraversal(2*index + 1) + "]}";
            }
        }
    }

}
