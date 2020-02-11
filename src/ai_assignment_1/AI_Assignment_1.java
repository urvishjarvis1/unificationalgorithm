/**
 * This program is the implementation of unification algorithm with occurs check.
 * The basic idea behind this algorithm is to unify two given predicates with each other.
 * Input of the algorithm is two expression which are asked when the program gets executed.
 * Output of this program will be true if the passed expressions are unifiable and program will print the solutions of unification.
 * Output will show false for following conditions: 1) Syntax error. 2) Occurs Check. 3) Not Unifiable.
 */
package ai_assignment_1;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Stack;

/**
 * @Class AI_Assignment_1
 * @Class members variables
 * expression1,expression2,tempMap,resultMap,failed_due_to_occurance,failed_due_to_syntax
 * @author urvis
 */
public class AI_Assignment_1 {

    /**
     * expression1 -> stores the first expression passed by user. expression2 ->
     * stores the first expression passed by user.
     */
    public Stack<Character> expression1, expression2;

    /**
     * tempMap->Stores the intermediate result of the unification solutions.
     * resultMap-> Stores the final result of unification solutions.
     */
    public Map<String, String> tempMap, resultMap;

    /**
     * These variable will decide the output of error
     */
    boolean failed_due_to_occurance = false,//true -> if the error due to occurance 
            failed_due_to_syntax = false;// true -> if the syntax error in given expression.

    /**
     * @constructor for class.
     */
    public AI_Assignment_1() {
        this.tempMap = new HashMap<String, String>();
        this.resultMap = new HashMap<String, String>();
        this.expression1 = new Stack<Character>();
        this.expression2 = new Stack<Character>();
    }

    /**
     * Takes expressions passed by user and separates the string input by chars
     * and pushed it into the stacks.
     *
     * @param exper1 -> expression passed by user in string
     * @param exper2 -> expression passed by user in string
     */
    private void saparateExpressions(String exper1, String exper2) {

        String[] splitedexper = exper1.split("(?)", -1);
        for (String character : splitedexper) {
            char[] chars = new char[1];
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
            char[] chars = new char[1];
            if (character.length() > 0) {
                character.getChars(0, character.length(), chars, 0);
                for (char c : chars) {
                    if (c != ' ') {
                        expression2.push(c);
                    }
                }
            }
        }
        Collections.reverse(expression1);
        Collections.reverse(expression2);
    }

    /**
     * Unification algorithm which takes two expression as stacks and compare
     * character by character to unify the terms.
     *
     * @return ans_of_unification.
     */
    private boolean unification() {
        System.out.println("Unification started!!");

        while (!expression1.isEmpty() && !expression2.isEmpty() && !failed_due_to_occurance) {
            char var1 = expression1.peek();
            char var2 = expression2.peek();
            if (var1 == var2) {
                pop_Stack();
            } else {
                /*If both are variables */
                if (Character.isUpperCase(var1) && Character.isUpperCase(var2)) {
                    add_In_Result(1);
                } /*if second expression is constant of function*/ else if (Character.isUpperCase(var1) && Character.isLowerCase(var2)) {
                    add_In_Result(2);
                } /*if first expression is constant of function*/ else if (Character.isLowerCase(var1) && Character.isUpperCase(var2)) {
                    add_In_Result(3);
                } else {
                    return false;
                }
            }
        }
        /**
         * if one the stack is not empty then it is a syntax error.
         */
        if (!expression1.isEmpty() || !expression2.isEmpty()) {
            failed_due_to_syntax = true;
            return false;
        }

        return !failed_due_to_occurance;
    }

    /**
     * Pops the char from both stack.
     */
    void pop_Stack() {
        expression1.pop();
        expression2.pop();
    }

    /**
     * adding the substitution in the resultMap
     *
     * @param type = based on above logic
     */
    private void add_In_Result(int type) {
        String tempValue = "", key, value;
        int count = 0;
        switch (type) {
            case 1:
                key = expression1.pop().toString();
                value = expression2.pop().toString();
                tempMap.put(key, value);
                resultMap.put(key, value);
                search_For_Subsitution(1);
                break;
            case 2:
                tempValue = tempValue + expression2.pop();
                if (!expression2.isEmpty()) {
                    count = 0;
                    /*Chechking for function*/
                    if (expression2.peek() == '(') {
                        while (!expression2.isEmpty() && expression2.peek() != ')') {
                            if (expression2.peek() == '(') {
                                count++;
                            }
                            tempValue = tempValue + expression2.pop();
                        }
                        while (count > 0) {
                            tempValue = tempValue + expression2.pop();
                            count--;
                        }
                    }
                }
                /**
                 * occurs check.
                 */
                String unified_Value = expression1.pop().toString();
                if (!tempValue.contains(unified_Value)) {
                    tempMap.put(unified_Value, tempValue);
                    resultMap.put(unified_Value, tempValue);
                    search_For_Subsitution(2);
                } else {
                    failed_due_to_occurance = true;
                }

                break;
            case 3:
                tempValue = tempValue + expression1.pop();
                if (!expression1.isEmpty()) {
                    /*Chechking for function*/
                    if (expression1.peek() == '(') {
                        while (!expression1.isEmpty() && expression1.peek() != ')') {
                            tempValue = tempValue + expression1.pop();
                        }
                        tempValue = tempValue + expression1.pop();
                    }
                }
                /**
                 * occurs check.
                 */
                unified_Value = expression2.pop().toString();
                if (!tempValue.contains(unified_Value)) {
                    tempMap.put(tempValue, unified_Value);
                    resultMap.put(unified_Value, tempValue);
                    search_For_Subsitution(3);
                } else {
                    failed_due_to_occurance = true;
                }
                break;

            default:
        }

    }

    /**
     * This function will substitute the value to the expressions
     *
     * @param specialCase = special case when the right side of result needs to
     * be substitute in the expression.
     */
    private void search_For_Subsitution(int specialCase) {
        for (String subsituteValue : tempMap.keySet()) {
            int position = expression1.search(subsituteValue.charAt(0));

            if (position != -1) {
                for (int i = 0; i < subsituteValue.length(); i++) {
                    expression1.remove(expression1.size() - position);
                }
                String value_for_Substituion_reverse = new StringBuilder(tempMap.get(subsituteValue)).reverse().toString();
                for (Character substitution : value_for_Substituion_reverse.toCharArray()) {
                    expression1.insertElementAt(substitution, expression1.size() - position + 1);
                }
            }
        }
        /**
         * Special case *
         */
        if (specialCase == 3) {
            for (String subsituteValue : tempMap.values()) {
                int position = expression2.search(subsituteValue.charAt(0));
                if (position != -1) {
                    expression2.remove(expression2.size() - position);
                    String value_for_Substituion_reverse = new StringBuilder(getKey(subsituteValue, tempMap)).reverse().toString();
                    for (Character substitution : value_for_Substituion_reverse.toCharArray()) {
                        expression2.insertElementAt(substitution, expression2.size() - position + 1);
                    }
                }
            }
        }
    }

    /**
     * This function to simplify the results with the new solutions.
     * i.e.backtracking through pervious solutions to replace the new values.
     */
    private void replace_In_Results() {
        for (String key : resultMap.keySet()) {
            for (String value : resultMap.values()) {
                if (value.contains(key)) {
                    String updationkey = getKey(value, resultMap);
                    String updationValue = value.replace(key, resultMap.get(key));
                    if (updationkey != null) {
                        resultMap.put(updationkey, updationValue);
                    }
                }
            }
        }
    }

    /**
     * Function for getting a key of passed value from HashMap.
     *
     * @param value = Value for which the key need to returned
     * @param resultMap = HashMap
     * @return key of the passed value from passed HashMap.
     */
    private String getKey(String value, Map<String, String> resultMap) {
        for (String key : resultMap.keySet()) {
            if (value.equals(resultMap.get(key))) {
                return key;
            }
        }
        return null;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        char continue_check = 'y';
        String exper1,exper2;
        Scanner sc = new Scanner(System.in);
        while (continue_check == 'y') {
            System.out.println("Please enter Expression 1:");
            exper1 = sc.nextLine();
            System.out.println("Please enter Expression 2:");
            exper2 = sc.nextLine();

            AI_Assignment_1 ai = new AI_Assignment_1();

            ai.saparateExpressions(exper1, exper2);

            boolean ans_of_unification = ai.unification();

            System.out.println("\nThe ans of unification is: " + ans_of_unification);

            if (ans_of_unification) {
                System.out.println("\nExperssion 1 and 2 are unifiable");
            } else {
                System.out.println("\nExperssion 1 and 2 are not unifiable");
            }
            if (ans_of_unification) {
                ai.replace_In_Results();
                System.out.println("\nSolutions:");
                for (String entry : ai.resultMap.keySet()) {
                    System.out.println(entry + ":" + ai.resultMap.get(entry));
                }
            } else {
                if (ai.failed_due_to_occurance) {
                    System.out.println("\nUnification failed due to occurs check!");
                } else if (ai.failed_due_to_syntax) {
                    System.out.println("\nUnification failed due to syntax error!");
                }
            }
            System.out.println("Do you want to continue?(y/n)");
            continue_check = sc.nextLine().charAt(0);
            System.out.println("\n");
        }
    }

}
