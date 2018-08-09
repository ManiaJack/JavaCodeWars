import java.util.Arrays;

/**
 * Created by ManiaJack on 2018/7/26
 * https://www.codewars.com/kata/ranking-poker-hands
 * 比较德州扑克两手牌之间的大小
 */
public class PokerHand {
    public enum Result { TIE, WIN, LOSS }
    boolean suit;
    int[] points = new int[13];
    int hand;
    int[] order = new int[5];

    public static void main(String[] args){
        PokerHand test = new PokerHand("3D 2H 3H 2C 2D");
        System.out.println(test.suit + " " + test.hand);
        System.out.println(Arrays.toString(test.order));
        System.out.println(Arrays.toString(test.points));
    }

    PokerHand(String hand){
        this.suit = true;
        String[] cards = hand.split(" ");
        char baseSuit = cards[0].charAt(1);
        for(String card: cards){
            char suit = card.charAt(1);
            if(suit != baseSuit){
                this.suit = false; // 判断是否同花
            }
            // 对于点数计数
            switch(card.charAt(0)){
                case 'T': this.points[8]++;break;
                case 'J': this.points[9]++;break;
                case 'Q': this.points[10]++;break;
                case 'K': this.points[11]++;break;
                case 'A': this.points[12]++;break;
                default: this.points[(int)card.charAt(0) - 50]++;
            }
        }

        int max = 0;
        int maxAt = 0;
        int max2 = 0;
        int max2At = 0;
        int minAt = -1;
        int[] order = new int[5];
        int cnt = 0;
        for(int i = 0; i < 13; i++){
            if(this.points[i] > max){
                max2 = max;
                max = this.points[i];
                max2At = maxAt;
                maxAt = i;
                if(minAt == -1){minAt = i;}
            }else if(this.points[i] == max){
                max2At = maxAt; maxAt = i; max2 = max;
            }else if(this.points[i] > max2){max2 = this.points[i]; max2At = i;}
            if(this.points[i] == 1){
                order[cnt++] = i;
            }
        }
        if(max2 > 1){order[cnt++] = max2At;}
        if(max > 1){
            if(maxAt == 0) {
                order[cnt] = -1;
            }else{order[cnt] = maxAt;}
            }
        int count = 0;
        for(int i = 4; i >= 0; i--){
            if(order[i] != 0){
                if(order[i] != -1) {
                    this.order[count++] = order[i];
                }else{this.order[count++] = 0;}
            }
        }

        // Four of a kind
        if(max == 4){this.hand = 8;}
        // Full house OR Three of a kind
        else if(max == 3){
            if(max2 == 2){this.hand = 7;}
            else{this.hand = 4;}
        }
        // Two pairs OR Pair
        else if(max == 2){
            if(max2 == 2){this.hand = 3;}
            else{this.hand = 2;}
        }
        // Straight OR Straight flush OR Royal flush
        else if(maxAt - minAt == 4 || (maxAt == 12 && max2At == 3)){
            if(this.suit){
                if(minAt == 8){this.hand = 10;}
                else{this.hand = 9;}
            }else{this.hand = 5;}
        }
        else if(this.suit){this.hand = 6;}
        else{this.hand = 1;}
    }

    public Result compareWith(PokerHand hand){
        // 先比较牌型大小
        if(this.hand > hand.hand){return Result.WIN;}
        if(this.hand < hand.hand){return Result.LOSS;}
        // 如果牌型相同，依次比较牌的点数大小
        for(int i = 0; i < 5; i++){
            if(this.order[i] > hand.order[i]){return Result.WIN;}
            if(this.order[i] < hand.order[i]){return Result.LOSS;}
        }
        return Result.TIE;
    }
}
