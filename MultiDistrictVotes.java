import java.util.Scanner;

public class MultiDistrictVotes {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        final int NUM_DISTRICTS = 2; // Constant for the number of districts

        // Arrays to store district data
        String[] districtNames = new String[NUM_DISTRICTS];
        int[] totalSeats = new int[NUM_DISTRICTS];
        int[] numParties = new int[NUM_DISTRICTS];
        String[][] partyNames = new String[NUM_DISTRICTS][];
        int[][] votes = new int[NUM_DISTRICTS][];

        // Step 1: Collect data for all districts
        for (int d = 0; d < NUM_DISTRICTS; d++) {
            System.out.print("Enter the name of district " + (d + 1) + ": ");
            districtNames[d] = scanner.nextLine();

            System.out.print("Enter the total seats for " + districtNames[d] + ": ");
            totalSeats[d] = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            System.out.print("Enter number of parties in " + districtNames[d] + ": ");
            numParties[d] = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            // Initialize arrays for parties and votes
            partyNames[d] = new String[numParties[d]];
            votes[d] = new int[numParties[d]];

            // Input party names
            for (int p = 0; p < numParties[d]; p++) {
                System.out.print("Enter party name for party " + (p + 1) + ": ");
                partyNames[d][p] = scanner.nextLine();
            }

            // Input votes for each party
            for (int p = 0; p < numParties[d]; p++) {
                System.out.print("Enter number of votes for party " + partyNames[d][p] + ": ");
                votes[d][p] = scanner.nextInt();
                scanner.nextLine(); // Consume newline
            }

            System.out.println("----------------------------------");
        }

        // Step 2: Process data and calculate results for each district
        for (int d = 0; d < NUM_DISTRICTS; d++) {
            System.out.println("----------------------------------");
            System.out.println("District: " + districtNames[d]);
            System.out.println("----------------------------------");

            // Calculate total votes and 5% threshold
            double totalVotes = 0;
            for (int p = 0; p < numParties[d]; p++) {
                totalVotes += votes[d][p];
            }
            double fivePercent = totalVotes * 0.05;

            int disqualifiedCount = 0;
            int qualifiedVotes = 0;
            double[] votePercentage = new double[numParties[d]];
            int[] seatAllocation = new int[numParties[d]];

            // Determine qualified/disqualified parties
            for (int p = 0; p < numParties[d]; p++) {
                System.out.println("Party " + partyNames[d][p] + ": Votes = " + votes[d][p]);
                if (votes[d][p] < fivePercent) {
                    System.out.println(" - This party is disqualified.");
                    disqualifiedCount++;
                } else {
                    System.out.println(" - This party is qualified.");
                    qualifiedVotes += votes[d][p];
                    votePercentage[p] = (double) votes[d][p] / totalVotes * 100;
                }
            }

            // Output stats for the district
            System.out.println("----------------------------------");
            System.out.println("Total votes across all parties: " + totalVotes);
            System.out.println("5% of total votes: " + fivePercent);
            System.out.println("Number of disqualified parties: " + disqualifiedCount);
            System.out.println("Total votes of qualified parties: " + qualifiedVotes);
            System.out.println("----------------------------------");

            // Allocate seats proportionally to qualified parties
            int totalAllocatedSeats = 0;
            for (int p = 0; p < numParties[d]; p++) {
                if (votes[d][p] >= fivePercent) {
                    seatAllocation[p] = (int) ((double) votes[d][p] / qualifiedVotes * totalSeats[d]);
                    totalAllocatedSeats += seatAllocation[p];
                }
            }

            // Check if there are remaining seats to be allocated
            if (totalAllocatedSeats < totalSeats[d]) {
                int remainingSeats = totalSeats[d] - totalAllocatedSeats;

                if (remainingSeats > 0) {
                    double highestVotePercentage = -1;
                    int highestVoteIndex = -1;

                    // Find the party with the highest vote percentage among the qualified parties
                    for (int p = 0; p < numParties[d]; p++) {
                        if (votes[d][p] >= fivePercent && votePercentage[p] > highestVotePercentage) {
                            highestVotePercentage = votePercentage[p];
                            highestVoteIndex = p;
                        }
                    }

                    // Allocate 1 remaining seat to the party with the highest vote percentage
                    if (highestVoteIndex != -1) {
                        seatAllocation[highestVoteIndex]++;
                        System.out.println("Party " + partyNames[d][highestVoteIndex] + " gets the remaining 1 seat.");
                    }
                }
            }

            // Output seat allocation for the district
            System.out.println("Seat Allocation:");
            for (int p = 0; p < numParties[d]; p++) {
                if (votes[d][p] >= fivePercent) {
                    System.out.println("Party " + partyNames[d][p] + ": " + seatAllocation[p] + " seats");
                }
            }

            System.out.println("----------------------------------");
        }

        scanner.close();
    }
}
