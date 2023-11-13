package TEST_API;

import java.util.Scanner;

public class study {

    // int c = 100;
    // int b = 100;
    // int a = 50;

    // study(){
    //     this(110,200);
    // }

    // study(int a){
    //     this.c = a;
    // }

    // study(int a, int b){
    //     this.a = a;
    //     this.b = b;
    // }

    // int getA() {
    //     return a;
    // }

    // int getB() {
    //     return b;
    // }

    // int getC(){
    //     return c;
    // }
    
}

class note extends study {

    // int c = 10;

    // Scanner sc = new Scanner(System.in);

    // note(){
    //     this(111);
    // }

    // note(int c){
    //     this.c = c;
    // }
    note(){

        Scanner sc = new Scanner(System.in);
        int testcase_num = sc.nextInt();

        for(int a = 0; a<testcase_num; a++){

            int box_num = sc.nextInt();
            int [][] box = new int[3][];
            box[0] = new int[box_num];
            box[1] = new int[box_num];
            box[2] = new int[box_num];
    
            for(int b = 0; b<box_num; b++){

                String test_numline = sc.nextLine();
                System.out.println(test_numline);
                String[] input_num = test_numline.split("\\s");

                for(int c = 0; c<input_num.length; c++){
                    //box[c][b] = Integer.valueOf(input_num[c]).intValue();
                    System.out.println(input_num[c]);
                }

            }
                

        }
        sc.close();
    }
    
}