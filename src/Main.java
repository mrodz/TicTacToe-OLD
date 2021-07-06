/**
 * <b>This is the main file for my implementation of TicTacToe.</b>
 * <p>
 *     It is a functional <u>command prompt application</u>.
 * </p>
 * @version COMPLETE
 */
public class Main {
    private static int gamesPlayed = 1;
    private static final java.util.Scanner scanner = new java.util.Scanner(System.in).useLocale(java.util.Locale.US);
    private static final Game game = new Game();

    /**
     * Main method, runs {@link #menu()} and handles exceptions.
     */
    public static void main(String... args) {
        try {
            doStuff();
        } catch (Throwable throwable) {
            System.out.printf("%nGAME CRASH -%nSomething went wrong :(%nReason: %s%n", throwable.getMessage());
        }
    }

    private static void doStuff() {
        System.out.println("Welcome to...\n");
        Intro.menu();

        for (int i = gamesPlayed; i < 100_000; i++, gamesPlayed++) {
            System.out.printf("~ This is game #%d:%n%n", gamesPlayed);
            System.out.println(Intro.getLogo());
            System.out.println("Enter a command (start ... ..., exit, back) below...");
            Participant[] players = menu();

            Intro.clearScreen(100);
            System.out.println(Intro.getLogo());
            System.out.printf("\t\tStarting game! %s vs. %s%n%n", players[0].type.getName(), players[1].type.getName());
            game.play(players[0], players[1]);

            Intro.waitForInput();
            Intro.clearScreen(50);
        }
        System.out.println("You've played 100,000 rounds of TicTacToe! Take a break.");
    }

    static class Intro {
        enum Logo {
            L1(" _______ _        _______           _______         "),
            L2("|__   __(_)      |__   __|         |__   __|        "),
            L3("   | |   _  ___     | | __ _  ___     | | ___   ___ "),
            L4("   | |  | |/ __|    | |/ _` |/ __|    | |/ _ \\ / _ \\"),
            L5("   | |  | | (__     | | (_| | (__     | | (_) |  __/"),
            L6("   |_|  |_|\\___|    |_|\\__,_|\\___|    |_|\\___/ \\___|");

            String ASCII;
            Logo(String ASCII) {
                this.ASCII = ASCII;
            }
        }

        private static void menu() {
            System.out.println(getLogo());
            System.out.println("\t\t\tType: go - Start the game\n\t\t\tType: help - View the tutorial\n\t\t\tType: " +
                    "exit - Quit Game");
            System.out.print("\nType your selection here: ");
            String s = scanner.next();
            if (s.equalsIgnoreCase("GO")) {
                clearScreen(100);
                scanner.nextLine();
                return;
            } else if (s.equalsIgnoreCase("HELP")) {
                clearScreen(100);
                intro();
                clearScreen(100);
            } else if (s.equalsIgnoreCase("EXIT")) {
                System.out.println("\n\n\n~ Exiting...\nGoodbye! Thanks for playing.");
                System.exit(1);
            } else {
                System.out.printf("Sorry, I couldn't understand '%s'%n", s);
            }
            menu();
        }

        protected static String getLogo() {
            StringBuilder str = new StringBuilder();
            java.util.Arrays.stream(Logo.values()).forEach(l -> str.append(l.ASCII).append("\n"));
            return str.toString();
        }

        private static void waitForInput() {
            System.out.print("Press ENTER to continue:");
            scanner.nextLine();
        }

        private static void intro() {
            System.out.printf("%s%n", getLogo());
            System.out.println("Hello! This is the documentation for this version of TicTacToe.\n");
            scanner.nextLine();
            waitForInput();
            System.out.println("\n\n§1. COMMANDS -\n" +
                    "While in the main menu, you can type commands that tell the game what to do.\n" +
                    "Every command follows the syntax: <command name> <parameter1> <parameter2>\n" +
                    "\t^ There are two (2) available commands: \"start\" and \"exit\"\n" +
                    "\t\t 1. start : This command indicates that you'd like to start a game, and requires\n" +
                    "\t\t            two additional parameters. The first is which player type will play\n" +
                    "\t\t            as 'X', and the second is which player will play as 'O'.\n" +
                    "\t\t 2. exit  : This command will exit the game.\n\n" +
                    "Proper command examples:\n" +
                    "\t - start user hard\n" +
                    "\t - start medium easy\n" +
                    "\t - start user user\n" +
                    "\t - exit\n"
            );
            waitForInput();
            System.out.println("\n\n§2. PLAYERS -\n" +
                    "There are four types of \"Players\" in this version:\n" +
                    "\t 1. user   : This instance can be controlled by a human; the strategy is up to you!\n" +
                    "\t 2. easy   : This is the easiest of the bots, and it can only make random moves.\n" +
                    "\t 3. medium : This bot will prioritize a move to win if it can immediately do so, but\n" +
                    "\t             it can also try to block the opponent if they have two in a row. If none\n" +
                    "\t             of these conditions apply, it will just make a random move.\n" +
                    "\t 4. hard   : This is the hardest bot, and it uses an algorithm to guarantee at least a tie.\n"
            );
            waitForInput();
            System.out.println(
                    "\n\n§3. COORDINATES -\n" +
                            "The coordinate system works as follows:\n\n" +
                            "\tExample Grid:\n\n" +
                            "\t\t     1 2 3   C\n" +
                            "\t\t   ---------\n" +
                            "\t\t1  | _ _ _ |\n" +
                            "\t\t2  | _ _ _ |\n" +
                            "\t\t3  | _ _ _ |\n" +
                            "\t\t   ---------\n" +
                            "\t\tR\n\nCoordinates take the form of Row (R), Column (C). (Commas are not required)\n" +
                            "\tValid plots:\n" +
                            "\t\t - 1 3\n" +
                            "\t\t - 2 2\n" +
                            "\t\t - 3, 2\n" +
                            "\t\t - 1, 1\n"
            );
            waitForInput();
            System.out.println(
                    "\n\n§4. RULES\n" +
                            "Standard TicTacToe rules apply (Get three placements in a row to win, one plot per turn).\n" +
                            "Good luck, enjoy!\n\nThis is the end of the tutorial.\n"
            );
            waitForInput();
        }
        protected static void clearScreen(int lines) {
            for (int i = 0; i < lines; i++) System.out.println();
        }
    }


    /**
     * <b>Used to get a user's input and cast the players.</b>
     * @return an <tt>array</tt> containing the correct casting of the user's query
     */
    private static Participant[] menu() throws IllegalStateException {
        Participant p1, p2;

        System.out.println();
        System.out.print("~ Your input command: ");
        String[] input = scanner.nextLine().split("\\s++");

        if (input[0].equalsIgnoreCase("exit")) {
            System.out.println("\n~ Exiting...\nGoodbye! Thanks for playing.");
            System.exit(1);
        }

        if (input[0].equalsIgnoreCase("back")) {
            Intro.clearScreen(100);
            doStuff();
        }

        if (input[0].equalsIgnoreCase("start") && input.length != 3) {
            System.out.println("Bad Parameters! The 'start' command needs to be followed by two instances (X & O).\n" +
                    "\t\t^ Example : start user hard");
            return menu();
        }

        if (input[0].equalsIgnoreCase("start")) {
            switch (input[1]) {
                case "easy": p1 = new EasyAI(); break;
                case "medium": p1 = new MediumAI(); break;
                case "hard": p1 = new HardAI(); break;
                case "user": p1 = new Player(); break;
                default:
                    System.out.printf("'%s' is not a valid player! (choice of 'user', 'easy', 'medium' & 'hard')%n", input[1]);
                    return menu();
            }
            switch (input[2]) {
                case "easy": p2 = new EasyAI(); break;
                case "medium": p2 = new MediumAI(); break;
                case "hard": p2 = new HardAI(); break;
                case "user": p2 = new Player(); break;
                default:
                    System.out.printf("'%s' is not a valid player! (choice of 'user', 'easy', 'medium' & 'hard')%n", input[2]);
                    return menu();
            }
            return new Participant[] {p1, p2};
        } else {
            System.out.printf("Unknown command -> '%s'. See the HELP section in the main menu for more instructions!%n", input[0]);
            return menu();
        }
    }
}

/**
 * <p>
 *     <b>Class that all instances of a player inherit from.</b>
 * </p>
 * <p>
 *     Contains a static {@link #grid} variable to be used throughout the game.
 * </p>
 * <p>
 *     It also allows for a symbol (X or O) to be assigned to each object.
 * </p>
 *
 */
abstract class Participant {
    /**The type of implementation of this class*/
    protected Implementation type;

    /**Global board used throughout the game*/
    protected static Grid grid;

    /**Unique character used to identify each player.*/
    protected char symbol;

    /**
     * <b>Set's a player's symbol.</b>
     * @implNote Use either X or O
     */
    protected void setSymbol(char symbol) {
        assert symbol == 'X' || symbol == 'O' : "The symbol parameter must be set to either 'X' or 'O'!";
        this.symbol = symbol;
    }

    /**
     * Returns the global {@link #grid Grid} object
     */
    protected Grid getGrid() {
        return grid;
    }

    /**
     * <b>This method will loop through <u>one</u> move in Tic-Tac-Toe</b>
     * <p>
     *     Once implemented, it should:
     * </p>
     * <p>
     *     For AI:
     * </p>
     * <ul>
     *     <li>Analyze the grid using {@link AI#assessGrid()}</li>
     *     <li>Print the move's information using {@link AI#getPlacementMessage(String, int...)}</li>
     *     <li>Plot the coordinates on the grid</li>
     * </ul>
     * For a User:
     * <ul>
     *     <li>Read a user's input from the {@link System#in}</li>
     *     <li>Handle any user error</li>
     *     <li>Plot the coordinates on the grid</li>
     * </ul>
     * @implNote Not to be confused with {@link Game#play(Participant, Participant)}!! That method
     * will play an entire game, while this is a single turn.
     * @see Game#play(Participant, Participant)
     */
    abstract void go();
}

/**
 * <b>A <u>human</u> player in HyperSkill's Tic-Tac-Toe</b>
 * <p>
 *     Logic is up for the user to decide, and they are free to play
 *     however they'd like.
 * </p>
 */
class Player extends Participant {
    private static final java.util.Scanner scanner = new java.util.Scanner(System.in).useLocale(java.util.Locale.US);

    Player(Grid g) {
        super.type = Implementation.PLAYER;
        grid = g;
    }

    Player() {
        this(new Grid());
    }

    /**
     * Made for a stage for the project on HyperSkill
     * @deprecated <b><u>since stage #1</b></u>
     * @return a grid built from the user's input.
     */
    @Deprecated
    protected Grid askInput() {
        System.out.println("Enter the cells:");
        try {
            return new Grid(scanner.nextLine());
        } catch (IllegalArgumentException e) {
            System.out.println("Please try again!\n");
            askInput();
        } finally {
            grid.print();
        }
        return new Grid("_________");
    }
    /**
     * <p>
     *     <b>This method will complete one round of Tic-Tac-Toe.</b>
     * </p>
     * <p>It will:</p>
     * <ul>
     *     <li>Gather a user's input from the {@link System#in} using a scanner</li>
     *     <li>Make sure it is a viable input</li>
     *     <li>Plot the input on the global {@link Participant#grid}</li>
     * </ul>
     * @see Participant#go()
     */
    @Override
    protected void go() {
        try {
            System.out.printf("%n%n%s >> It's %c's turn! Please enter the coordinates:%n", this.type.getName(), this.symbol);
            String[] first = scanner.nextLine().replaceAll(",", "").split("\\s+");
            System.out.println();

            if (first.length != 2) throw new IllegalArgumentException("Wrong input!");

            for (String str : first) {
                if (!str.matches("\\d++")) throw new IllegalArgumentException("You should enter numbers!");
            }

            int[] input = {Integer.parseInt(first[0]), Integer.parseInt(first[1])};
            grid.plot(input, this.symbol);
        } catch (IllegalStateException | IllegalArgumentException exception) {
            if (exception.getMessage().contains("only enter two")) {
                System.out.println("You entered too many coordinates! Try again.");
            } else if (exception.getMessage().contains(") is full")) {
                System.out.printf("This cell is occupied! Choose another one! (%c, %c)%n",
                        exception.getMessage().charAt(10), exception.getMessage().charAt(13));
            } else if (exception.getMessage().contains("within the range")) {
                System.out.println("Coordinates should be from 1 to 3!");
            } else if (exception.getMessage().equalsIgnoreCase("You should enter numbers!")) {
                System.out.println("You should enter numbers!");
            } else if (exception.getMessage().contains("Wrong input")) {
                System.out.println("Wrong input! Please try again.");
            }
            go();
        }
    }
}

/**
 * Different levels of difficulty for the Tic-Tac-Toe bots.
 */
enum Difficulty {
    /**@see EasyAI*/
    EASY,
    /**@see MediumAI*/
    MEDIUM,
    /**@see HardAI*/
    HARD
}

/**
 * Superclass for all <b>AI</b> (ie. {@link EasyAI}, {@link MediumAI}) used in this project.
 */
abstract class AI extends Participant {
    protected Difficulty difficulty;

    /**
     * Constructor to initialize the {@link #grid} field.
     * @param g {@link Grid} to be used as the starting position of the game.
     */
    AI(Grid g) {
        grid = java.util.Objects.requireNonNull(g);
    }

    /**
     * Default constructor for the {@link AI} class -> Initializes a blank grid
     * @implNote By changing the input field, you can modify the starting board position.
     */
    AI() {
        this(new Grid("_________"));
    }

    /**
     * Plots a symbol at the specified coordinates on {@link Participant#grid}.
     * @param input is an int array that contains an x and a y value.
     * @param symbol is the symbol that will be placed at said coordinates.
     */
    void plotGrid(int[] input, char symbol) {
        grid.plot(input, symbol);
    }

    /**
     * @deprecated - {@link #getPlacementMessage(String, int...)} gives a more in-depth analysis.
     * @return a formatted message containing the bot difficulty
     */
    @Deprecated
    String getPlacementMessage() {
        return String.format("%n%nMaking move level \"%s\"%n%n", this.difficulty.name());
    }

    /**
     * @deprecated - {@link #getPlacementMessage(String, int...)} gives a more in-depth analysis.
     * @param coords takes a vararg that contains the move coordinates.
     * @return a formatted String containing the bot difficulty and the coordinates
     * the move was plotted at.
     * @throws IllegalArgumentException when there are not exactly <b>two</b> coordinates present.
     */
    @Deprecated
    String getPlacementMessage(int... coords) throws IllegalArgumentException {
        if (coords.length != 2) throw new IllegalArgumentException("You entered too many coordinates!");
        return String.format("%n%nMaking move level \"%s\" at %d, %d%n%n", this.difficulty.name(), coords[0], coords[1]);
    }

    /**
     * This method is used to make it clear when AI is playing.
     * @param moveInfo a String that gives the user more information about the move.
     * @param coords takes a vararg that contains the move coordinates.
     * @throws IllegalArgumentException when there are not exactly <b>two</b> coordinates present.
     * @return a formatted String containing the bot difficulty, the coordinates the move was plotted
     * at, and bonus information.
     */
    String getPlacementMessage(String moveInfo, int... coords) throws IllegalArgumentException {
        if (coords.length != 2) throw new IllegalArgumentException(String.
                format("You entered the wrong amount of coordinates! (Should be 2, you entered %d)", coords.length));
        return String.format("%n%n%s >> Making move level \"%s\" at %d, %d (%s)%n", this.type.getName(),
                this.difficulty.name(), coords[0], coords[1], moveInfo);
    }

    /**
     * <p>
     *     This method will complete one round of Tic-Tac-Toe.
     * </p>
     * <p>It will:</p>
     * <ul>
     *     <li>Use the {@link #assessGrid()} method to pick the best placement using the logic
     *     specific to the implementation of the bot.</li>
     *     <li>Use the {@link #plotGrid(int[], char)} to update the grid with the correct placement</li>
     *     <li>Print the formatted {@link String} produced by the {@link #getPlacementMessage(String, int...)}</li>
     * </ul>
     * @see Participant#go()
     */
    @Override
    abstract void go();

    /**
     * Will analyze the global {@link Participant#grid} and choose where would be the best move.
     * @implNote Should return values in <u>human</u> form! (ie. their array index + 1)
     * @return an int[] array containing successful coordinates to be used in {@link #plotGrid(int[], char)}.
     */
    @SuppressWarnings("unused")
    abstract int[] assessGrid();
}

/**
 * <p>
 *     <b>The <u>easy</u> difficulty for HyperSkill's Tic-Tac-Toe</b>
 * </p>
 * In this variant:
 * <ul>
 *     <li>The bot will make random moves, with <u>no</u> predefined logic.</li>
 * </ul>
 * @see AI
 */
class EasyAI extends AI {
    EasyAI() {
        super();
        super.type = Implementation.BOT_EASY;
        super.difficulty = Difficulty.EASY;
    }

    /**
     * This variation of {@link AI#assessGrid() assessGrid()} will create an {@code int[]} array containing {@link java.util.Random
     * randomly generated} x and y values.
     * @return an int[] array containing successful coordinates.
     * @see AI#assessGrid()
     */
    @Override
    public int[] assessGrid() {
        java.util.Random random = new java.util.Random();
        char[][] tg = grid.toCharArray();
        while (true) {
            int x = random.nextInt(3), y = random.nextInt(3);
            if (tg[x][y] == '_') {
                return new int[]{++x, ++y};
            }
        }
    }

    @Override
    void go() {
        int[] i = assessGrid();
        System.out.println(getPlacementMessage("Random Moves", i));
        plotGrid(i, this.symbol);
    }
}


/**
 * <p>
 *     <b>The <u>medium</u> difficulty for HyperSkill's Tic-Tac-Toe</b>
 * </p>
 * <p>
 *     In this variant:
 *     <ul>
 *         <li>If the bot already has two in a row and can win with one further move, it does so.</li>
 *         <li>If its opponent can win with one move, it plays the move necessary to block this.</li>
 *         <li>Otherwise, it makes a random move.</li>
 *     </ul>
 * </p>
 * @see AI
 */

class MediumAI extends AI {

    static class Move {
        private final Urgency urgency;

        public Move(Urgency urgency) {
            this.urgency = urgency;
        }

        public int getWeight() {
            return urgency.weight;
        }
    }

    protected Move[][] Straights = new Move[2][this.getGrid().toCharArray().length];
    protected Move[] Diagonals = new Move[2];

    /**
     * @see MediumAI
     */
    MediumAI() {
        super();
        super.type = Implementation.BOT_MEDIUM;
        super.difficulty = Difficulty.MEDIUM;
    }

    /**
     * @see #Straights
     * @see #Diagonals
     */
    protected void readGrid() {
        char[][] tg = this.getGrid().toCharArray();
        for (int i = 0; i < tg.length; i++) {
            int[][] values = {
                    {0, 0},
                    {0, 0}
            };
            int[] blanks = {0, 0};
            for (int b = 0; b < tg[i].length; b++) {
                switch (tg[i][b]) {
                    case 'X':
                        values[0][0]++;
                        break;
                    case 'O':
                        values[0][1]++;
                        break;
                    case '_':
                        blanks[0]++;
                        break;
                    default:
                        throw new IllegalStateException(String.format("The character at grid index %d, %d is invalid! " +
                                "(Found '%c', should be either 'X', 'O', or '_')", i, b, tg[i][b]));
                }
                switch (tg[b][i]) {
                    case 'X':
                        values[1][0]++;
                        break;
                    case 'O':
                        values[1][1]++;
                        break;
                    case '_':
                        blanks[1]++;
                        break;
                    default:
                        throw new IllegalStateException(String.format("The character at grid index %d, %d is invalid! " +
                                "(Found '%c', should be either 'X', 'O', or '_')", b, i, tg[b][i]));
                }
            }
            Straights[0][i] = new Move(
                    blanks[0] == 1 && ((values[0][0] == 2 && this.symbol == 'X') || (values[0][1] == 2 && this.symbol == 'O'))
                            ? Urgency.DIRE_WIN
                            : blanks[0] == 1 && ((values[0][0] == 2 && this.symbol != 'X') || (values[0][1] == 2 && this.symbol != 'O'))
                            ? Urgency.DIRE_LOSS
                            : Urgency.NOT_DIRE
            );

            Straights[1][i] = new Move(
                    blanks[1] == 1 && ((values[1][0] == 2 && this.symbol == 'X') || (values[1][1] == 2 && this.symbol == 'O'))
                            ? Urgency.DIRE_WIN
                            : blanks[1] == 1 && ((values[1][0] == 2 && this.symbol != 'X') || (values[1][1] == 2 && this.symbol != 'O'))
                            ? Urgency.DIRE_LOSS
                            : Urgency.NOT_DIRE
            );
        }
        for (int i = 0; i < 2; i++) {
            int[][] values = {
                    {0, 0},
                    {0, 0}
            };
            int[] blanks = {0, 0};
            for (int b = 0, c = tg[b].length - 1; b < tg.length && c >= 0; b++, c--) {
                switch (tg[b][b]) {
                    case 'X':
                        values[0][0]++;
                        break;
                    case 'O':
                        values[0][1]++;
                        break;
                    case '_':
                        blanks[0]++;
                        break;
                    default:
                        throw new IllegalStateException(String.format("The character at grid index %d, %d is invalid! " +
                                "(Found '%c', should be either 'X', 'O', or '_')", b, b, tg[b][b]));
                }

                switch (tg[c][b]) {
                    case 'X':
                        values[1][0]++;
                        break;
                    case 'O':
                        values[1][1]++;
                        break;
                    case '_':
                        blanks[1]++;
                        break;
                    default:
                        throw new IllegalStateException(String.format("The character at grid index %d, %d is invalid! " +
                                "(Found '%c', should be either 'X', 'O', or '_')", c, b, tg[c][b]));
                }
            }
            Diagonals[i] = new Move(
                    blanks[i] == 1 && ((values[i][0] == 2 && this.symbol == 'X') || (values[i][1] == 2 && this.symbol == 'O'))
                            ? Urgency.DIRE_WIN
                            : blanks[i] == 1 && ((values[i][0] == 2 && this.symbol != 'X') || (values[i][1] == 2 && this.symbol != 'O'))
                            ? Urgency.DIRE_LOSS
                            : Urgency.NOT_DIRE
            );
        }
    }

    /**
     * This variation of {@link AI#assessGrid() assessGrid()} will create an {@code int[]} array containing x and y
     * values that follow {@link MediumAI this rule}.
     * @return an int[] array containing successful coordinates.
     * @see AI#assessGrid()
     */
    @Override
    int[] assessGrid() {
        char[][] tg = this.getGrid().toCharArray();
        readGrid();

        //Straights
        for (int i = 0; i < 6; i++) {
            boolean horizontal = i < 3;
            int tempI = horizontal ? i : i - 3;
            if (Straights[horizontal ? 0 : 1][tempI].getWeight() == Urgency.DIRE_WIN.weight) {
                for (int x = 0; x < tg.length; x++) {
                    if (tg[horizontal ? tempI : x][horizontal ? x : tempI] == '_') {
                        tempI++;
                        x++;
                        return new int[] {horizontal ? tempI : x, horizontal ? x : tempI};
                    }
                }
            }
        }
        for (int i = 0; i < 6; i++) {
            boolean horizontal = i < 3;
            int tempI = horizontal ? i : i - 3;
            if (Straights[horizontal ? 0 : 1][tempI].getWeight() == Urgency.DIRE_LOSS.weight) {
                for (int x = 0; x < tg.length; x++) {
                    if (tg[horizontal ? tempI : x][horizontal ? x : tempI] == '_') {
                        tempI++;
                        x++;
                        return new int[] {horizontal ? tempI : x, horizontal ? x : tempI};
                    }
                }
            }
        }

        //Diagonals
        for (int i = 0; i < 2; i++) {
            if (Diagonals[i].getWeight() == Urgency.DIRE_WIN.weight) {
                for (int b = 0, c = tg[b].length - 1; b < tg.length && c >= 0; b++, c--) {
                    if (tg[i == 0 ? b : c][b] == '_') {
                        c++;
                        b++;
                        return new int[] {i == 0 ? b : c, b};
                    }
                }
            }
        }

        for (int i = 0; i < 2; i++) {
            if (Diagonals[i].getWeight() == Urgency.DIRE_LOSS.weight) {
                for (int b = 0, c = tg[b].length - 1; b < tg.length && c >= 0; b++, c--) {
                    if (tg[i == 0 ? b : c][b] == '_') {
                        c++;
                        b++;
                        return new int[] {i == 0 ? b : c, b};
                    }
                }
            }
        }

        //No placement detected:
        java.util.Random random = new java.util.Random();
        int savedX, savedY;
        do {
            savedX = random.nextInt(tg.length);
            savedY = random.nextInt(tg.length);
        } while (tg[savedX][savedY] != '_');

        return new int[] {++savedX, ++savedY};
    }

    @Override
    void go() {
        int[] i = assessGrid();
        System.out.println(getPlacementMessage("Medium Difficulty", i));
        plotGrid(i, this.symbol);
    }
}

/**
 * <b>The <u>hardest</u> difficulty for HyperSkill's TicTacToe</b>
 * <p>
 *     In this variant, the bot cannot loose. It uses the MiniMax algorithm to find the best move!
 * </p>
 * @see AI
 */
class HardAI extends AI {
    public HardAI() {
        super();
        super.type = Implementation.BOT_HARD;
        super.difficulty = Difficulty.HARD;
    }

    @Override
    int[] assessGrid() {
        return new MinimaxEngine(grid, this.symbol).simulate();
    }

    @Override
    void go() {
        int[] i = assessGrid();
        System.out.println(getPlacementMessage("Hard Difficulty", i));
        System.out.println();
        plotGrid(i, this.symbol);
    }
}

class Game {
    final static String GAME_OVER = "  _____                         ____                 _ \n" +
            " / ____|                       / __ \\               | |\n" +
            "| |  __  __ _ _ __ ___   ___  | |  | |_   _____ _ __| |\n" +
            "| | |_ |/ _` | '_ ` _ \\ / _ \\ | |  | \\ \\ / / _ \\ '__| |\n" +
            "| |__| | (_| | | | | | |  __/ | |__| |\\ V /  __/ |  |_|\n" +
            " \\_____|\\__,_|_| |_| |_|\\___|  \\____/  \\_/ \\___|_|  (_)";

    /**
     * <p>
     *     <b>This method will complete one game of Tic-Tac-Toe.</b>
     * </p>
     * <p>
     *     It will:
     * </p>
     * <ul>
     *     <li>Use the cast {@link Participant#go()} method</li>
     *     <li>Print the Tic-Tac-Toe grid</li>
     *     <li>Print a message once the game has ended</li>
     * </ul>
     * <p></p>
     * @param player1 is represented by the {@code Character} 'X'
     * @param player2 is represented by the {@code Character} 'O'
     */
    void play(Participant player1, Participant player2) {
        System.out.println();
        player1.getGrid().print();
        player1.setSymbol('X');
        player2.setSymbol('O');
        while (true) {
            player1.go();
            player1.getGrid().print();
            if (!player1.getGrid().checkStatus().contains("Game not finished!")) {
                break;
            }
            player2.go();
            player2.getGrid().print();
            if (!player2.getGrid().checkStatus().contains("Game not finished!")) {
                break;
            }
        }
        System.out.printf("%n%n%s%n%n\t\t\t\t\t\t%s%n%n", GAME_OVER, player1.getGrid().checkStatus());
    }
}

class Grid {
    private final char[][] grid;
    int xS, oS, blanks;

    public Grid(String input) {
        this.grid = stringToGrid(input);
    }
    public Grid() {
        this.grid = stringToGrid("_________");
    }

    /**
     * @deprecated - replaced by {@link #plot(int[], char)}
     * @param x is the x value the character c will be placed at.
     * @param y is the y value the character c will be placed at.
     * @param c is the character symbol to be placed.
     */
    @Deprecated
    void updateGrid(int x, int y, char c) {
        this.grid[x][y] = c;
    }

    /**
     * Counts the number of X's, O's, and Blanks are present on the grid.
     */
    private void count() {
        xS = 0;
        oS = 0;
        blanks = 0;
        for (char[] a : this.grid) {
            for (char c : a) {
                switch (c) {
                    case 'X':
                        xS++;
                        break;
                    case 'O':
                        oS++;
                        break;
                    case '_':
                        blanks++;
                        break;
                }
            }
        }
    }

    /**
     * <b>Plots a character at the specified coordinates.</b>
     */
    @SuppressWarnings("SameParameterValue")
    public void plot(int[] coords, char symbol) {
        if (coords.length != 2) {
            throw new IllegalArgumentException("You can only enter two coordinates!");
        }
        if (coords[0] < 1 || coords[0] > 3 || coords[1] < 1 || coords[1] > 3/*!(coords[0] >= 1 && coords[0] <= 3 && coords[1] >= 1 && coords[1] <= 3)*/) {
            throw new IllegalArgumentException("The value of the coordinates must fit within the range of 1-3\n" +
                    String.format("Your input: %d, %d", coords[0], coords[1]));
        }

        if (grid[coords[0] - 1][coords[1] - 1] != '_') {
            throw new IllegalStateException(String.format("The slot (%d, %d) is full! Found '%c', expected '_'",
                    coords[0], coords[1], grid[coords[0] - 1][coords[1] - 1]));
        }
        this.grid[coords[0] - 1][coords[1] - 1] = symbol;
        count();
    }

    /**
     * <b>This method calculates the current state of the game.</b>
     * @return
     * <ul>
     *     <li>X wins</li>
     *     <li>O wins</li>
     *     <li>Draw</li>
     *     <li>Game not finished!</li>
     * </ul>
     */
    public String checkStatus() {
        for (int i = 0; i < 3; i++) {
            if (grid[i][0] == grid[i][1] && grid[i][1] == grid[i][2] && grid[i][1] != '_') {
                return String.format("%c wins", grid[i][1]);
            }
        }
        for (int i = 0; i < 3; i++) {
            if (grid[0][i] == grid[1][i] && grid[1][i] == grid[2][i] && grid[1][i] != '_') {
                return String.format("%c wins", grid[1][i]);
            }
        }
        if (((grid[0][0] == grid[1][1] && grid[1][1] == grid[2][2]) || (grid[2][0] == grid[1][1] &&
                grid[1][1] == grid[0][2])) && grid[1][1] != '_') {
            return String.format("%c wins", grid[1][1]);
        }
        return blanks == 0 ? "Draw" : "Game not finished!";
    }

    /**
     * Calculate whether the game has concluded.
     * @return whether the game is over (ie. has a definite winner).
     */
    public boolean isGameOver() {
        char[][] tg = grid;

        //Straights
        for (int i = 0; i < 3; i++) {
            if ((tg[i][0] == tg[i][1] && tg[i][1] == tg[i][2] && tg[i][1] != '_')
                    || (tg[0][i] == tg[1][i] && tg[1][i] == tg[2][i] && tg[1][i] != '_')) {
                return true;
            }
        }
        //Diagonals
        if (((tg[0][0] == tg[1][1] && tg[1][1] == tg[2][2]) || (tg[2][0] == tg[1][1] &&
                tg[1][1] == tg[0][2])) && tg[1][1] != '_') {
            return true;
        }
        //Is the Game still active
        for (char[] cL : tg) for (char c : cL) if (c == '_') return false;

        return true;
    }

    /**
     * <b>A method that takes in a string and outputs a char[][] array.</b>
     * @param str requires a string with a length of 9 characters.
     * @return a char[][] array that contains the elements of the string.
     * @throws IllegalArgumentException if the string is <b>not</b> 9 characters long.
     */
    private char[][] stringToGrid(String str) throws IllegalArgumentException {
        char[][] res = new char[3][3];
        if (str.length() != 9) {
            throw new IllegalArgumentException("This string must be 9 characters long!");
        }
        for (int i = 0, c = 0; i < 3; i++) {
            for (int b = 0; b < 3; b++, c++) {
                res[i][b] = str.charAt(c);
            }
        }
        return res;
    }

    @Override
    public String toString() {
        return toString(this);
    }

    public static String toString(Grid grid) {
        char[][] chars = grid.toCharArray();
        StringBuilder str = new StringBuilder();
        for (char[] cL : chars) for (char c : cL) str.append(c);
        return str.toString();
    }

    /**
     * <b>Prints a fancy grid with the format:</b>
     * <pre>
     *     ---------
     *     | _ _ _ |
     *     | _ _ _ |
     *     | _ _ _ |
     *     ---------
     * </pre>
     * @implNote Used frequently
     */
    public void print() {
        System.out.println("---------");
        for (char[] a : this.grid) {
            System.out.print("| ");
            for (char c : a) System.out.printf("%c ", c);
            System.out.println('|');
        }
        System.out.print("---------");
    }

    public static Grid copyOf(Grid grid) {
        char[][] cg = grid.toCharArray();
        StringBuilder s = new StringBuilder();
        for (char[] cL : cg) for (char c : cL) s.append(c);
        return new Grid(s.toString());
    }

    public char[][] toCharArray() {
        char[][] destination = new char[this.grid.length][this.grid[0].length];
        for (int i = 0; i < destination.length; i++) {
            System.arraycopy(this.grid[i], 0, destination[i], 0, destination[i].length);
        }
        return destination;
    }
}


enum Implementation {
    /**@see Player*/
    PLAYER("PLAYER"),
    /**@see EasyAI*/
    BOT_EASY("EASY BOT"),
    /**@see MediumAI*/
    BOT_MEDIUM("MEDIUM BOT"),
    /**@see HardAI*/
    BOT_HARD("HARD BOT");

    /**Used in {@link AI#getPlacementMessage(String, int...)} & {@link Player#go()}*/
    private final String name;

    Implementation(String name) {
        this.name = name;
    }

    protected String getName() {
        return name;
    }
}


class MinimaxEngine {
    /**This is the base grid, ie. the current state of the game <u>before</u> the algorithm is run.*/
    Grid baseGrid;

    /**The AI's symbol -> The player to be maximized.*/
    char symbol;

    /**The opponent's symbol -> The player to be minimized.*/
    char oppSymbol;

    /**
     * <b>Constructor to initialize {@link #baseGrid} & {@link #symbol}</b>
     * @param grid valid {@link Grid} to serve as the base for calculations.
     * @param symbol the <u>maximizing</u> symbol.
     */
    public MinimaxEngine(Grid grid, char symbol) {
        baseGrid = Grid.copyOf(grid);
        this.symbol = symbol;
        this.oppSymbol = this.symbol == 'X' ? 'O' : 'X';
    }

    /**
     * <p>
     *     <b>This is the front for</b> {@link #minimax(Grid, boolean)}
     * </p>
     * <p>
     *     Using the MiniMax algorithm, this engine will work out the best move from the
     *     board stored as {@link #baseGrid}
     * </p>
     * @return an {@code int[]} with a length of 2, containing the proper X and Y values of the best plot.
     */
    public int[] simulate() {
        int bestScore = Integer.MIN_VALUE;
        int[] move = new int[2];
        char[][] board = baseGrid.toCharArray();
        for (int i = 0, c = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++, c++) {
                if (board[i][j] == '_') {
                    int score = minimax(new Grid(stringWithCharAt(baseGrid.toString(), c, this.symbol)), false);
                    if (score > bestScore) {
                        bestScore = score;
                        move = new int[] {i + 1, j + 1};
                    }
                }
            }
        }
        return move;
    }

    /**
     * <b>Analyses the state of the board and returns its weight using the
     * <a href="https://www.youtube.com/watch?v=l-hh51ncgDI" target="_blank";><u>MiniMax Algorithm</u></a></b>
     * @param position the state of the game to be evaluated; in this case, its a {@link Grid} value.
     * @param isMaximizing whether the simulation is running as the AI or as its opponent.
     *                     Used to set the symbol placed on the imaginary grid.
     *                     ({@code true} if it is the bot, {@code false} if its an opp.)
     * @return an {@link Integer} containing the weight of a move.
     * @see #simulate()
     * @see <a href="https://www.youtube.com/watch?v=trKjYdBASyQ" target="_blank";>Example of a MiniMax Implementation in TicTacToe</a>
     * <!-- Sebastian Lague did a great job explaining this concept; would recommend! -->
     */
    private int minimax(Grid position, boolean isMaximizing) {
        if (position.isGameOver()) {
            return analyzePosition(position);
        }

        int bestScore;
        if (isMaximizing) {
            bestScore = Integer.MIN_VALUE;
            for (Grid g : getPositionsFrom(position, this.symbol)) {
                int score = minimax(g, false);
                bestScore = Math.max(score, bestScore);
            }
        } else {
            bestScore = Integer.MAX_VALUE;
            for (Grid g : getPositionsFrom(position, this.oppSymbol)) {
                int score = minimax(g, true);
                bestScore = Math.min(score, bestScore);
            }
        }
        return bestScore;
    }

    /**
     * <b>Gets every possible placement on a {@link Grid}</b>
     * @param position the <u>original</u> base grid to be analyzed
     * @param nextPlayer the player who's character will be placed in every available slot
     * @return an {@code array} of {@link Grid Grids} that contains every possible placement with a depth of 1.
     */
    private Grid[] getPositionsFrom(Grid position, char nextPlayer) {
        java.util.ArrayList<Grid> tg = new java.util.ArrayList<>();
        final char[][] baseChars = position.toCharArray();
        final String baseAsString = Grid.toString(position);
        for (int i = 0, c = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++, c++) {
                if (baseChars[i][j] == '_')
                    tg.add(new Grid(stringWithCharAt(baseAsString, c, nextPlayer)));
            }
        }
        return tg.toArray(new Grid[0]);
    }

    /**
     * <b>Resource used to build grids.</b>
     * @param target the original string to be modified
     * @param index the index of the character to be switched
     * @param replacement the {@code char} to be inserted
     * @return a modified {@link String}
     */
    private String stringWithCharAt(String target, int index, char replacement) {
        char[] chars = target.toCharArray();
        chars[index] = replacement;
        StringBuilder str = new StringBuilder();
        for (char c : chars) str.append(c);
        return str.toString();
    }

    /**
     * <b>Analyzes the result of a game.</b>
     * @param position the grid to be analyzed
     * @return The {@link Scores Score} that corresponds with the current state of the board.
     * @throws AssertionError the game is still being played when this method is called.
     */
    private int analyzePosition(Grid position) throws AssertionError {
        assert position.isGameOver() : "This method checks for the end result of a board; the supplied grid is still being played!";
        if (isWin(position, this.symbol)) return Scores.WIN.value;
        if (isWin(position, this.oppSymbol)) return Scores.LOSS.value;
        return Scores.TIE.value;
    }

    /**
     * <b>Used to determine whether the game is a definite win for either player.</b><br/>
     * <i>A win is either 3 in a row or a trapped grid.</i>
     * @param position the position ({@link Grid}) to be analyzed.
     * @param me {@code boolean} value that determines if three of <i>our</i> symbols is a win ({@code true}), or if
     *                          three of the opponent's symbols is a win ({@code false}).
     * @return {@code boolean} value -> has the party defined in <b><tt>boolean me</tt></b> won the game?
     * @implNote isWin(position, true) will return true if the AI has won the game. isWin(position, false) will return
     *                          true if the opponent has won the game.
     */
    private boolean isWin(Grid position, char me) {
        char[][] grid = position.toCharArray();
        for (int i = 0; i < grid.length; i++) {
            if ((grid[i][0] == grid[i][1] && grid[i][1] == grid[i][2] && grid[i][1] == me)
                    || (grid[0][i] == grid[1][i] && grid[1][i] == grid[2][i] && grid[1][i] == me)) return true;
        }
        return ((grid[0][0] == grid[1][1] && grid[1][1] == grid[2][2] && grid[1][1] == me)
                || (grid[0][2] == grid[1][1] && grid[1][1] == grid[2][0] && grid[1][1] == me));
    }
}

/**
 * Used in the {@link MinimaxEngine} to analyze a grid's value.
 */
enum Scores {
    /**Three in a row*/
    WIN(+10),
    /**Opponent has three in a row*/
    LOSS(-10),
    /**Game is over with no winner*/
    TIE(0);

    /**Weight*/
    int value;

    Scores(int value) {
        this.value = value;
    }
}

/**
 * Used in {@link MediumAI} to determine whether a move is dire or not dire.
 */
enum Urgency {
    /**Immediate win*/
    DIRE_WIN(3),

    /**Immediate loss*/
    DIRE_LOSS(2),

    /**No pressure to do a special move. Used in {@link MediumAI}*/
    NOT_DIRE(1);

    /**Weight of the move, used to judge stuff*/
    public int weight;

    Urgency(int weight) {
        this.weight = weight;
    }
}