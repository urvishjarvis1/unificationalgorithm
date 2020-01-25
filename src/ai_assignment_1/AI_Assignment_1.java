/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ai_assignment_1;

import java.util.Scanner;

/**
 *
 * @author urvis
 */
public class AI_Assignment_1 {
    /**
     * Function for checking the argument count of given input
     * @param exps = given input
     * @return (int) number of arguments.
     */
    private int noOfArgument(String exps){
        int _count=0;
        boolean flag=true;
        for(int i=0; i<exps.length();i++){
            if(Character.isUpperCase(exps.charAt(i))&&flag){
                _count++;
            }else if(Character.isLowerCase(exps.charAt(i))){
                if(exps.charAt(i+1)=='('){
                    flag=false;
                    i++;
                   break;
                }else{
                    _count++;
                }
            }else if(exps.charAt(i)==')'){
                flag=true;
                i++;
                break;
            }
        }
     return _count;    
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Scanner sc=new Scanner(System.in);
        String exper=sc.nextLine();
        AI_Assignment_1 ai=new AI_Assignment_1();
        System.out.println("number of arg "+ai.noOfArgument(exper));
    }
    
    private boolean unification(String expr1,String expr2){
        
        return false;
    }
    
}
