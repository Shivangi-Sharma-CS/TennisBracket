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
     Load the results for a given round into the Bracket
     @param round - the round in the tournament being loaded
     @param resultsFile - the file where the results for the given round can be found
     **/
    public void loadResults(int round, String resultsFile) {
        try {
            int startIndex = sizeOfTree - ((numCompetitors*2) / (int)(Math.pow(2, round)));
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
     Return a String of the matches for a given player. Each match should appear on its own line and should only include the opponent name.
     The String starts with the first round match (opponent) for the player, round 2 (if applicable) is on line 2, etc.
     @return the string for the opponents in each line starting from round 1 and so on.
     **/
    public String getMatchesForPlayer(String player) {
        StringBuilder opponents = new StringBuilder();
        return postOrderTraversal(1, player, opponents).replace("null", "");
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


    /**
     * This method does post order traversal of the tree to find the opponents of the given player
     * @param index the starting index for the traversal
     * @param player the player name whose opponents needs to be searched
     * @param opponents the opponents string returned for the recursive method
     * @return the string of opponents played by the player
     */
    private String postOrderTraversal(int index, String player, StringBuilder opponents) {
        if(index > arr.length-1) {
            opponents.append("");
        }
        else {
            postOrderTraversal(2*index, player, opponents);
            postOrderTraversal(2*index + 1, player, opponents);
            if(arr[index].equals(player) && index%2==0) opponents.append(arr[index+1]).append(" ");
            else if(arr[index].equals(player) && index%2!=0) opponents.append(arr[index-1]).append(" ");
        }
        return opponents.toString();
    }


}
