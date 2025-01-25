import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class Hangman {
    private static final Random random = new Random();
    private static final Scanner scanner = new Scanner(System.in);
    private static int mistakeCounter = 0;
    private static boolean playMore = true;
    static final List<Character> wrongCharsList = new ArrayList<>();

    public static void main(String[] args) throws Exception {
        play();
    }

    public static void play() throws IOException {
        while (playMore) {
            mistakeCounter = 0;
            startGameLoop();
            char answer;
            do {
                System.out.println("Хотите сыграть еще раз? Да/Нет:");
                answer = getInputFromConsole();
                if (answer == 'Н') {
                    playMore = false;
                }
            } while (answer != 'Д' && answer != 'Н');
        }
        scanner.close();
    }

    public static void startGameLoop() throws IOException {
        wrongCharsList.clear();
        System.out.println("Попробуйте угадать слово! Введите букву, которая содержится в загаданном слове:");
        checkGameState();
    }

    public static void checkGameState() throws IOException {
        char[] wordForGuessingChars = readRandomWordFromFile();
        char[] hidedWord = hideRandomWord(wordForGuessingChars);
        while (mistakeCounter < 6) {
            if (Arrays.equals(wordForGuessingChars, hidedWord)) {
                System.out.println("Вы победили!");
                return;
            }
            comparePlayerInputWithGuessingWord(wordForGuessingChars, hidedWord);
            printHangman(mistakeCounter);
            System.out.println(hidedWord);
        }

        if (mistakeCounter == 6) {
            System.out.println("Вы проиграли! Правильное слово : " + new String(wordForGuessingChars));
        }
    }

    public static void comparePlayerInputWithGuessingWord(char[] wordForGuessingChars, char[] hidedWord) {
        printWrongCharsList();
        char playerGuessing = getInputFromConsole();
        while (!validateInput(playerGuessing) || wrongCharsList.contains(playerGuessing)) {
            System.out.println("Введите букву русского алфавита:");
            playerGuessing = getInputFromConsole();
        }
        boolean isGuessCorrect = false;
        for (int i = 0; i < wordForGuessingChars.length; i++) {
            if (playerGuessing == wordForGuessingChars[i]) {
                hidedWord[i] = playerGuessing;
                isGuessCorrect = true;
            }
        }
        if (!isGuessCorrect) {
            mistakeCounter = mistakeCounter + 1;
            wrongCharsList.add(playerGuessing);

        }
    }

    public static char getInputFromConsole() {
        return scanner.next().toUpperCase().charAt(0);
    }

    public static char[] readRandomWordFromFile() throws IOException {
        List<String> listOfWords = Files.readAllLines(Paths.get("dictionary.txt"));
        int randomIdx = random.nextInt(listOfWords.size());
        String guessWord = listOfWords.get(randomIdx);

        return guessWord.toUpperCase().toCharArray();
    }

    public static char[] hideRandomWord(char[] wordForGuessingChars) {
        char[] hidedWord = new char[wordForGuessingChars.length];
        for (int i = 0; i < wordForGuessingChars.length; i++) {
            hidedWord[i] = '_';
        }
        System.out.println(hidedWord);
        return hidedWord;
    }

    public static boolean validateInput(char playerGuessing) {
        return ((playerGuessing >= 'А' && playerGuessing <= 'Я') || playerGuessing == 'Ё');
    }

    public static void printHangman(int mistake) {
        switch (mistake) {
            case 0:
                System.out.println("_________");
                System.out.println("|/      |");
                System.out.println("|");
                System.out.println("| ");
                System.out.println("|");
                System.out.println("|_______");
                break;
            case 1:
                System.out.println("_________");
                System.out.println("|/      |");
                System.out.println("|       O");
                System.out.println("|");
                System.out.println("|");
                System.out.println("|_______");
                break;
            case 2:
                System.out.println("_________");
                System.out.println("|/      |");
                System.out.println("|       O");
                System.out.println("|       |");
                System.out.println("|");
                System.out.println("|_______");
                break;
            case 3:
                System.out.println("_________");
                System.out.println("|/      |");
                System.out.println("|       O");
                System.out.println("|      /|");
                System.out.println("|");
                System.out.println("|_______");
                break;
            case 4:
                System.out.println("_________");
                System.out.println("|/      |");
                System.out.println("|       O");
                System.out.println("|      /|\\");
                System.out.println("|");
                System.out.println("|_______");
                break;
            case 5:
                System.out.println("_________");
                System.out.println("|/      |");
                System.out.println("|       O");
                System.out.println("|      /|\\");
                System.out.println("|      / ");
                System.out.println("|_______");
                break;
            case 6:
                System.out.println("_________");
                System.out.println("|/      |");
                System.out.println("|       O");
                System.out.println("|      /|\\");
                System.out.println("|      / \\");
                System.out.println("|_______");
                break;
        }

    }

    public static void printWrongCharsList() {
        if (mistakeCounter > 0) {
            System.out.println("Неправильные буквы: " + wrongCharsList);
        }
    }

}
