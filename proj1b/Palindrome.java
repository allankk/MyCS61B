public class Palindrome {

    public Deque<Character> wordToDeque(String word){

        Deque<Character> chardeque = new LinkedListDeque<>();

        for (int i = 0; i < word.length(); i++){
            chardeque.addLast(word.charAt(i));
        }

        return chardeque;
    }

    public boolean isPalindrome(String word) {
        Deque<Character> first = new LinkedListDeque<>();
        Deque<Character> second = new LinkedListDeque<>();

        for (int i = 0; i < word.length(); i++){
            first.addFirst(word.charAt(i));
            second.addLast(word.charAt(i));
        }

        return isReverseWord(first, second);
    }

    public boolean isPalindrome(String word, CharacterComparator cc) {
        // returns true if palindrome is off by one character

        for (int i = 0; i < word.length() / 2; i++) {
            if (cc.equalChars(word.charAt(i), word.charAt(word.length() - 1 - i))){
            } else {
                return false;
            }
        }
        return true;
    }

    public boolean isReverseWord(Deque<Character> first, Deque<Character> second) {
        if (first.isEmpty() && second.isEmpty()){
            return true;
        } else if (first.removeFirst().equals(second.removeFirst())) {
            return isReverseWord(first, second);
        } else {
            return false;
        }
    }


}
