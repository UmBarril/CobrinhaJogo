package cobrinha.jogo;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Persistance {
    static final String path = "./leaderboard.txt";

    public static List<Player> readLeaderboard() {
        List<Player> leaderboard = new ArrayList<>();
        try(BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line;
            while((line = br.readLine()) != null) {
                String[] splitLine = line.split(":");
                leaderboard.add(new Player(splitLine[0], Integer.parseInt(splitLine[1].trim())));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return leaderboard;
    }
    public static void updateLeaderboard(Player player) {
        // read and replace
        List<String> stringBuffer = new ArrayList<>();
        boolean isNewPlayer = true;
        try(BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line;
            while((line = br.readLine()) != null) {
                if(line.equals("")) {
                    continue;
                }
                if(line.startsWith(player.getName())) {
                    isNewPlayer = false;
                    stringBuffer.add(player.toString());
                } else {
                    stringBuffer.add(line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(isNewPlayer) {
            stringBuffer.add(player.toString());
        }
        // write
        try(BufferedWriter bw = new BufferedWriter(new FileWriter(path))) {
            for(String line : stringBuffer) {
                bw.write(line);
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
