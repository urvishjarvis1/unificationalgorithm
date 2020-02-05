/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ai_assignment_1;

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
    public Map<Character, String> resultMap;

    private void saparateExpressions(String exper1, String exper2) {

        String[] splitedexper = exper1.split("(?)", -1);
        for (String character : splitedexper) {
            expression1.push(character.charAt(0));
        }

        splitedexper = exper2.split("(?)", -1);
        for (String character : splitedexper) {
            expression2.push(character.charAt(0));
        }
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
        System.out.println("Please enter Expr1:f");
        String exper1 = sc.nextLine();
        System.out.println("Please enter Expr2:");
        String exper2 = sc.nextLine();
        AI_Assignment_1 ai = new AI_Assignment_1();
        //String[] expr1=saparateExpression(exper1);
        //String[] expr2=saparateExpression(exper2);
        //boolean ans=ai.unification(expr1, expr2);
        System.out.println("The ans of unification is:1: " + ai.noOfArgument(exper1) + " 2:" + ai.noOfArgument(exper2));
    }

    private boolean unification() {
        while (!expression1.isEmpty() || !expression2.isEmpty()) {
            if (expression1.peek() == expression2.peek()) {
                pop_Stack();
            } else {
                char var1 = expression1.peek();
                char var2 = expression2.peek();
                if (Character.isUpperCase(var1) && Character.isUpperCase(var2)) {
                    if (var1 == var2) {
                        pop_Stack();
                    } else {
                        add_In_Result(1);
                    }
                } else if (Character.isUpperCase(var1) && Character.isLowerCase(var2)) {
                    add_In_Result(2);
                } else {
                    if (var1 == var2) {
                        pop_Stack();
                    }
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
                resultMap.put(expression1.pop(), expression2.pop().toString());
                search_For_Subsitution();
                break;
            case 2:
                tempValue=tempValue+expression2.pop();
                if(expression2.peek()=='('){
                    while(expression2.peek()!=')'){
                        tempValue=tempValue+expression2.pop();
                    }
                }
                resultMap.put(expression1.pop(),tempValue);tempValue
                
                
            default:
                System.out.println("Print solution in last");
        }

    }

    private void search_For_Subsitution() {
        for (Character subsituteValue : resultMap.keySet()) {
            int position = expression1.search(subsituteValue);
            if (position != -1) {
                expression1.remove(position);
                for (Character substitution : resultMap.get(subsituteValue).toCharArray()) {
                    expression1.insertElementAt(substitution, position);
                }

            }
        }
    }

}
