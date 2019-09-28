package spell;

public class Node implements INode {
    Node[] letters;
    int wordCount;
    @Override
    public int getValue() {
        return wordCount;
    }

    public Node() {
        letters = new Node[26];
        wordCount = 0;
    }

    public int addWord(String word, int i, int numNewNodes) {
        char currLetter = word.charAt(i);
        if (word.length() == i + 1) {       //if the length of the word = the index we're at, this is the end of the word.
            if (letters[currLetter - 'a'] == null) {
                letters[currLetter - 'a'] = new Node();
                numNewNodes++;
                letters[currLetter - 'a'].wordCount++;
            }
            else {
                letters[currLetter - 'a'].wordCount++;
            }
        }
        else {
            if (letters[currLetter - 'a'] == null) {    //if this is a new path, make a new Node
                letters[currLetter - 'a'] = new Node();
                numNewNodes++;
                i++;
                return letters[currLetter - 'a'].addWord(word, i, numNewNodes);
            } else {                                    //if this path has been used before, go down that path
                i++;
                return letters[currLetter - 'a'].addWord(word, i, numNewNodes);
            }
        }
        return numNewNodes;
    }

    public Node find(String word, int i) {
        char currLetter = word.charAt(i);
        if (letters[currLetter - 'a'] == null) {
            return null;
        }
        else if (i + 1 == word.length() && letters[currLetter - 'a'].wordCount != 0) {
                return letters[currLetter - 'a'];
        }
        else if (i + 1 == word.length()) {
            return null;
        }
        else {
            return letters[currLetter - 'a'].find(word, (i + 1));
        }

    }

    public void recursiveToString(String wordSoFar, StringBuilder fullList) {
        if (wordCount != 0) {
            fullList.append(wordSoFar + '\n');
        }
        for (int i = 0; i < 26; i++) {
            if (letters[i] != null) {
                letters[i].recursiveToString((wordSoFar + (char)(i+'a')),fullList);
            }
        }
        return;
    }

    public boolean recursiveEquals(Node otherTrieNode, boolean isEqual) {

        for (int i = 0; i < 26; i++) {
            if (letters[i] == null && otherTrieNode.letters[i] == null) {
                // do nothing, isEqual remains, moves on to next index
            }
            else if (letters[i] != null && otherTrieNode.letters[i] != null) {
                if (letters[i].wordCount == otherTrieNode.letters[i].wordCount) {
                    isEqual = letters[i].recursiveEquals(otherTrieNode.letters[i], isEqual);
                }
                else {
                    isEqual = false;
                }
            }
            else {
                isEqual = false;
            }
        }
        return isEqual;
    }

    public int recursiveHash(int toReturn) {

        for (int i = 0; i < 26; i++) {
            if (this.wordCount != 0) {
                toReturn = toReturn + (i * 31);
            }
            if (letters[i] == null) {
                //toReturn = toReturn + (i * 5);
            }
            else {
                toReturn = toReturn + (i*31);
                toReturn = toReturn + letters[i].recursiveHash(toReturn);
            }
        }
        return toReturn;
    }

    public int getWordCount(String word, int i) {
        char currLetter = word.charAt(i);
        if (letters[currLetter - 'a'] == null) {
            return 0;
        }
        else if (i + 1 == word.length() && letters[currLetter - 'a'].wordCount != 0) {
            return letters[currLetter - 'a'].wordCount;
        }
        else if (i + 1 == word.length()) {
            return 0;
        }
        else {
            return letters[currLetter - 'a'].getWordCount(word, (i + 1));
        }
    }
}
