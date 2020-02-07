/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ai_assignment_1;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Stack;

/**
 *
 * @author urvis
 */
public class AI_Assignment_1 {

    public Stack<Character> expression1, expression2;
    public Map<String, String> resultMap;

    private void saparateExpressions(String exper1, String exper2) {

        String[] splitedexper = exper1.split("(?)", -1);
        for (String character : splitedexper) {
            System.out.println(character);
            char[] chars=new char[1];
            if (character.length() > 0) {
                character.getChars(0, character.length(), chars, 0);
                for (char c : chars) {
                    if (c != ' ') {
                        expression1.push(c);
                    }
                }
            }
        }

        splitedexper = exper2.split("(?)", -1);
        for (String character : splitedexper) {
            System.out.println(character +" lenght:"+character.length());
            char[] chars=new char[1];
            if (character.length() > 0) {
                character.getChars(0, character.length(), chars, 0);
                for (char c : chars) {
                    if (c != ' ') {
                        expression2.push(c);
                    }
                }
            }
        }
        System.out.println("Expression pushed!");
        Collections.reverse(expression1);
        Collections.reverse(expression2);
        System.out.println("Stack 1"+Arrays.asList(expression1));
        System.out.println("Stack 2"+Arrays.asList(expression2));
    }

    public AI_Assignment_1() {
        this.resultMap = new HashMap<>();
        this.expression1 = new Stack<>();
        this.expression2 = new Stack<>();
    }

    /**
     * Function for checking the argument count of given input
     *
     * @param exps = given input
     * @return (int) number of arguments.
     */
    private int noOfArgument(String exps) {
        int _count = 0;
        boolean flag = true;
        for (int i = 0; i < exps.length(); i++) {
            if (Character.isUpperCase(exps.charAt(i)) && flag) {
                _count++;
            } else if (Character.isLowerCase(exps.charAt(i))) {
                if (exps.charAt(i + 1) == '(') {
                    if (Character.isLowerCase(exps.charAt(i + 2))) {
                        flag = false;
                        break;
                    }
                } else {
                    _count++;
                }
            } else if (exps.charAt(i) == ')') {
                flag = true;
                if (i < exps.length()) {
                    if (exps.charAt(i + 1) == ',' || exps.charAt(i + 1) == ')') {
                        _count++;
                    }
                }
                break;
            }
        }
        return _count;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Please enter Expr1:");
        String exper1 = sc.nextLine();
        System.out.println("Please enter Expr2:");
        String exper2 = sc.nextLine();
        AI_Assignment_1 ai = new AI_Assignment_1();
        //String[] expr1=saparateExpression(exper1);
        //String[] expr2=saparateExpression(exper2);
        //boolean ans=ai.unification(expr1, expr2);
        ai.saparateExpressions(exper1,exper2);
        System.out.println("The ans of unification is: "+ai.unification());
        if(ai.unification())
            System.out.println("Solutions:\n");
            for (Map.Entry<String, String> entry : ai.resultMap.entrySet()) {
                System.out.println(entry.getKey() + ":" + entry.getValue());
            }
    }

    private boolean unification() {
        System.out.println("Unification started!!");
        while (!expression1.isEmpty() && !expression2.isEmpty()) {
            System.out.println("inside while");
            char var1 = expression1.peek();
            char var2 = expression2.peek();
            System.out.println("var1:"+var1+" var2:"+var2);
            if (var1==var2) {
                System.out.println("both are same");
                pop_Stack();
            } else {
                if (Character.isUpperCase(var1) && Character.isUpperCase(var2)) {                   
                        System.out.println("both are different");
                        add_In_Result(1);             
                } else if (Character.isUpperCase(var1) && Character.isLowerCase(var2)) {
                    System.out.println("one is upper 2nd is lower");
                    add_In_Result(2);
                } else if(Character.isLowerCase(var1) && Character.isUpperCase(var2)){
                    System.out.println("one is lower and 2nd is upper ");
                    add_In_Result(3);
                }else{
                    return false;
                }
            }
        }
        if (!expression1.isEmpty() || !expression2.isEmpty()) {
            return false;
        }
        return true;
    }

    void pop_Stack() {
        expression1.pop();
        expression2.pop();
    }

    private void add_In_Result(int type) {
        String tempValue="";
        switch (type) {
            case 1:
                resultMap.put(expression1.pop().toString(), expression2.pop().toString());
                search_For_Subsitution();
                break;
            case 2:
                tempValue=tempValue+expression2.pop();
                if(expression2.peek()=='('){
                    System.out.println("expression 2 peek"+expression2.peek());
                    while(!expression2.isEmpty()&&expression2.peek()!=')'){
                        System.out.println("solutions peek"+expression2.peek());
                        tempValue=tempValue+expression2.pop();
                        System.out.println("tempvalue"+tempValue);                       
                    }
                    tempValue=tempValue+expression2.pop();
                    System.out.println("tempvalue"+tempValue);
                }
                System.out.println("final rsult:"+expression1.peek()+":"+tempValue);
                resultMap.put(expression1.pop().toString(),tempValue);
                search_For_Subsitution();
                break;
            case 3:
                tempValue=tempValue+expression1.pop();
                System.out.println("peek"+expression1.peek());
                if(expression1.peek()=='('){
                    System.out.println("expression 1 peek"+expression1.peek());
                    while(!expression1.isEmpty()&&expression1.peek()!=')'){
                        System.out.println("solutions peek"+expression1.peek());
                        tempValue=tempValue+expression1.pop();
                        System.out.println("tempvalue"+tempValue);                        
                    }
                    tempValue=tempValue+expression1.pop();
                    System.out.println("tempvalue"+tempValue);
                }
                System.out.println("final rsult:"+expression2.peek()+":"+tempValue);
                resultMap.put(tempValue,expression2.pop().toString());
                search_For_Subsitution();
                break;
                
            default:
                System.out.println("Print solution in last");
        }

    }

    private void search_For_Subsitution() {
        boolean firstOccurance=true;
        System.out.println("exper1:"+Arrays.asList(expression1));
        for (String subsituteValue : resultMap.keySet()) {
            
            int position = expression1.search(subsituteValue.charAt(0));
            System.out.println("substitute value"+subsituteValue+" Position:"+position);
            if (position != -1) {
                System.out.println("removing!!");
                expression1.remove(expression1.size()-position);
                System.out.println("exper1:"+Arrays.asList(expression1));
                for (Character substitution : resultMap.get(subsituteValue).toCharArray()) {
                    expression1.insertElementAt(substitution, expression1.size()-position+1);
                }
            }
            firstOccurance=false;
        }
        System.out.println("exper1:"+Arrays.asList(expression1));
    }

}
