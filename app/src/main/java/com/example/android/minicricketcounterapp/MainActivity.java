package com.example.android.minicricketcounterapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

/**
 * This is score board for mini cricket.
 * Instructions:
 * 1. Game starts with a toss, random generator selects the winning team.
 * 2. Each team has 6 balls, at every ball you can either score 1,4 or 6 runs,
 *    or the player can be out.
 * 3. And at each ball played the scored will be updated.
 * 4. Once the 6 balls are over or player is out, next team starts the turn.
 * 5. Once both the teams are played the score is updated and winner is declared.
 * 6. If there's a tie, tie breaker is placed, 1 ball is given to each team and winner is selected.
 * 7. At any point of the time game can be reset by clicking the Reset button.
 */
public class MainActivity extends AppCompatActivity {

    private static int teamAScore = 0;
    private static int teamBScore = 0;
    private static int teamABalls =6;
    private static int teamBBalls =6;
    boolean teamATurn = false;
    boolean teamBTurn = false;
    boolean toss = false;
    boolean isTie = false;
    private String teamA = "teamA";
    private String teamB = "teamB";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setEnabled(false, false);
        Toast.makeText(this, "Please click Toss", Toast.LENGTH_SHORT).show();

    }

    /**
     * Update teams score when team hits single run(s).
     *
     * @param v - view of the layout.
     */
    public void singleRun(View v) {
        String team = null;
        if (v.getId() == R.id.single_teamA) {
            team = teamA;

        } else if (v.getId() == R.id.single_teamB) {
            team = teamB;
        }
        updateScores(team, 1);
    }

    /**
     * Update teams score when team hits four runs.
     *
     * @param v - view of the layout.
     */
    public void fourRuns(View v) {
        String team = null;
        if (v.getId() == R.id.four_teamA) {
            team = teamA;

        } else if (v.getId() == R.id.four_teamB) {
            team = teamB;
        }
        updateScores(team, 4);
    }

    /**
     * Update teams score when team hits six runs.
     *
     * @param v - view of the layout.
     */
    public void sixRuns(View v) {
        String team = null;
        if (v.getId() == R.id.six_teamA) {
            team = teamA;

        } else if (v.getId() == R.id.six_teamB) {
            team = teamB;
        }
        updateScores(team, 6);

    }

    /**
     * Update scores and totals when the player is out.
     *
     * @param v - view of the layout.
     */
    public void out(View v) {
        if (v.getId() == R.id.out_teamA) {
            Toast.makeText(this, "Team A your player is out so game over", Toast.LENGTH_SHORT).show();
        } else if (v.getId() == R.id.out_teamB) {
            Toast.makeText(this, "Team B your player is out so game over", Toast.LENGTH_SHORT).show();
        }

        nextTurn();
    }

    /**
     * Team is selected by random generator.
     * @param v - view of the layout.
     */
    public void toss(View v) {
        String[] arr = {"teamA", "teamB"};
        Random random = new Random();
        int select = random.nextInt(arr.length);
        String randomSelected = arr[select];
        Toast.makeText(this, randomSelected.toUpperCase() + " won the toss, lets start the game", Toast.LENGTH_SHORT).show();

        if (randomSelected.equals(teamA)) {
            teamATurn = true;
            setEnabled(true, false);
        } else if (randomSelected.equals(teamB)) {
            teamBTurn = true;
            setEnabled(false, true);
        }
        toss = true;
    }

    /**
     * Resets all the parameters for both the teams
     *
     * @param v - view of the layout.
     */
    public void resetAll(View v) {
        if (toss || isTie) {
            reset();
        } else {
            Toast.makeText(this, "Board is reset, lets Toss.", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Resets all the parameters of the game.
     */
    private void reset() {
        setEnabled(true, true);
        teamAScore = 0;
        teamBScore = 0;
        if (isTie) {
            teamABalls =1;
            teamBBalls =1;
        } else {
            teamABalls =6;
            teamBBalls =6;
        }
        teamA = "teamA";
        teamB = "teamB";
        toss = false;
        teamATurn = false;
        teamBTurn = false;
        TextView scoreTeamA = (TextView) findViewById(R.id.score_teamA);
        scoreTeamA.setText("Balls: " + teamABalls + "\n Total: " + teamAScore);

        TextView scoreTeamB = (TextView) findViewById(R.id.score_teamB);
        scoreTeamB.setText("Balls: " + teamBBalls + "\n Total: " + teamBScore);

        if(!isTie) {
            Toast.makeText(this, "Lets start the game, TOSS", Toast.LENGTH_SHORT).show();
        }
        if(isTie) {
            Toast.makeText(this, "Team A please play, remember there's only 1 ball.", Toast.LENGTH_SHORT).show();
            setEnabled(true, false);
            teamATurn = true;
        } else {
            setEnabled(false, false);
        }
    }

    /**
     * Update and display scores on board
     */
    private void updateScores(String teamName, int runs) {
        if (teamName.equals(teamA)) {
            teamAScore = teamAScore + runs;
            teamABalls = teamABalls - 1;
            TextView scoreTeamA = (TextView) findViewById(R.id.score_teamA);
            scoreTeamA.setText("Balls: " + teamABalls + "\nTotal: " + teamAScore);
            if (teamABalls == 0) {
                Toast.makeText(this, teamName.toUpperCase() + "'s game is over.", Toast.LENGTH_SHORT).show();
                nextTurn();
                return;
            }

        } else if (teamName.equals(teamB)) {
            teamBScore = teamBScore +runs;
            teamBBalls = teamBBalls - 1;
            TextView scoreTeamB = (TextView) findViewById(R.id.score_teamB);
            scoreTeamB.setText("Balls: " + teamBBalls + "\nTotal: " + teamBScore);
            if (teamBBalls == 0) {
                Toast.makeText(this, teamName.toUpperCase() + "'s game is over.", Toast.LENGTH_SHORT).show();
                nextTurn();
                return;
            }

        }

    }

    /**
     * Decides the next turn.
     *
     */
    private void nextTurn() {
        if (!teamATurn) {
            setEnabled(true, false);
            Toast.makeText(this, "Team A get ready to play", Toast.LENGTH_SHORT).show();
            teamATurn = true;
        } else if (!teamBTurn) {
            setEnabled(false, true);
            Toast.makeText(this, "Team B get ready to play", Toast.LENGTH_SHORT).show();
            teamBTurn = true;
        } else {
            Toast.makeText(this, "Both the teams have played, lets wait for results.", Toast.LENGTH_SHORT).show();
            setEnabled(false, false);
            results();

        }

    }

    /**
     * Updates the results
     */
    private void results() {
        if (teamAScore > teamBScore) {
            Toast.makeText(this, "Team A has won, Congratulations!!! \nReset the game to play more", Toast.LENGTH_SHORT).show();
        } else if (teamAScore == teamBScore) {
            Toast.makeText(this, "Its a tie, lets go for a tie breaker", Toast.LENGTH_SHORT).show();
            tieBreaker();

        } else if (teamAScore < teamBScore) {
            Toast.makeText(this, "Team B has won, Congratulations!!! \n" +
                    "Reset the game to play more", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Sets the tie breaker.
     */
    private void tieBreaker() {
        isTie = true;
        reset();

    }

    /**
     * Enables/disables the buttons.
     * @param stateA - sets the teamA buttons/board on/off.
     * @param stateB - sets the teamB buttons/board on/off.
     */
    private void setEnabled(boolean stateA, boolean stateB) {
        LinearLayout layoutA = (LinearLayout) findViewById(R.id.linear_layout_teamA);
        for (int i = 0; i < layoutA.getChildCount(); i++) {
            View child = layoutA.getChildAt(i);
            if(child.isClickable()) {
                child.setEnabled(stateA);
            }
        }

        LinearLayout layoutB = (LinearLayout) findViewById(R.id.linear_layout_teamB);
        for (int i = 0; i < layoutB.getChildCount(); i++) {
            View child = layoutB.getChildAt(i);
            if(child.isClickable()) {
                child.setEnabled(stateB);
            }
        }
    }
}
