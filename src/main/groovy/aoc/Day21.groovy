package aoc

import java.util.function.BinaryOperator

/**
 * Day 21: Monkey Math
 *
 * @see <a href="https://adventofcode.com/2122/day/21">AOC 2122 Day 21</a>
 */
class Day21 {
    private static final String X = "x"
    private static final String HUMAN = "humn"
    private static final String ROOT = "root"

    long doMath(String fileName, boolean part1) {
        def monkeys = [:]
        getClass().getClassLoader().getResource(fileName).splitEachLine(": ") {
            monkeys[it[0]] = it[1]
        }

        if (part1) return getMonkeyValue(ROOT, monkeys)

        monkeys[HUMAN] = X

        def equation = getEquation(ROOT, monkeys)
        equation.operation = Operation.EQUALITY

        return solveForX(equation)
    }

    long getMonkeyValue(String monkeyName, LinkedHashMap<String, Object> monkeys) {
        def monkeyObject = monkeys[monkeyName]

        if (monkeyObject instanceof String) {
            def monkeyString = monkeyObject as String

            if (monkeyString.isBigDecimal()) {
                monkeys[monkeyName] = new BigDecimal(monkeyString)
                return monkeys[monkeyName]
            } else { // operation
                def params = monkeyString.split(" ")
                def value1 = getMonkeyValue(params[0], monkeys)
                def value2 = getMonkeyValue(params[2], monkeys)

                def result
                if (params[1] == "+") {
                    result = value1 + value2
                } else if (params[1] == "-") {
                    result = value1 - value2
                } else if (params[1] == "/") {
                    result = value1 / value2
                } else {
                    result = value1 * value2
                }

                monkeys[monkeyName] = result
                return result
            }
        } else { // already an integer
            return monkeyObject
        }
    }

    private Equation getEquation(String monkeyName, LinkedHashMap<String, Object> monkeys) {
        def monkeyObject = monkeys[monkeyName]

        if (monkeyObject instanceof String) {
            def monkeyString = monkeyObject as String

            if (monkeyString.isBigDecimal()) {
                return new Equation(new BigDecimal(monkeyString), Operation.IDENTITY, 0)
            } else if (monkeyString == X) {
                return new Equation(monkeyString, Operation.IDENTITY, 0)
            } else { // operation
                def params = monkeyString.split(" ")
                def operand1 = getEquation(params[0], monkeys)
                def operand2 = getEquation(params[2], monkeys)

                def operation
                if (params[1] == "+") {
                    operation = Operation.ADDITION
                } else if (params[1] == "-") {
                    operation = Operation.SUBTRACTION
                } else if (params[1] == "/") {
                    operation = Operation.DIVISION
                } else {
                    operation = Operation.MULTIPLICATION
                }

                return new Equation(operand1, operation, operand2)
            }
        }
    }

    long solveForX(Equation equation) {
        if (equation.operand1 == X) return equation.operand2
        if (equation.operand2 == X) return equation.operand1

        // One side should be a number
        BigDecimal value
        Equation sideWithX
        if (equation.operand1 instanceof BigDecimal) {
            value = equation.operand1
            sideWithX = equation.operand2
        } else {
            value = equation.operand2
            sideWithX = equation.operand1
        }

        // Apply inverse operation
        switch (sideWithX.operation) {
            case Operation.ADDITION:
                if (sideWithX.operand1 instanceof BigDecimal) {
                    return solveForX(new Equation(sideWithX.operand2, Operation.EQUALITY, value - sideWithX.operand1 as BigDecimal))
                } else {
                    return solveForX(new Equation(sideWithX.operand1, Operation.EQUALITY, value - sideWithX.operand2 as BigDecimal))
                }
            case Operation.SUBTRACTION:
                return solveForX(new Equation(sideWithX.operand1, Operation.EQUALITY, new Equation(sideWithX.operand2, Operation.ADDITION, value)))
            case Operation.MULTIPLICATION:
                if (sideWithX.operand1 instanceof BigDecimal) {
                    return solveForX(new Equation(sideWithX.operand2, Operation.EQUALITY, value / sideWithX.operand1 as BigDecimal))
                } else {
                    return solveForX(new Equation(sideWithX.operand1, Operation.EQUALITY, value / sideWithX.operand2 as BigDecimal))
                }
            case Operation.DIVISION:
                return solveForX(new Equation(sideWithX.operand1, Operation.EQUALITY, new Equation(sideWithX.operand2, Operation.MULTIPLICATION, value)))
        }
    }

    class Equation {
        def operand1
        Operation operation
        def operand2

        Equation(operand1, Operation operation, operand2) {
            this.operand1 = reduce(operand1)
            this.operation = operation
            this.operand2 = reduce(operand2)

            if (this.operand1 instanceof BigDecimal && this.operand2 instanceof BigDecimal) {
                this.operand1 = operation.getOperator().apply(this.operand1, this.operand2)
                this.operation = Operation.IDENTITY
                this.operand2 = 0
            }
        }

        def reduce(def operand) {
            if (operand instanceof Equation) {
                if (operand.operation == Operation.IDENTITY) return reduce(operand.operand1)

                operand.operand1 = reduce(operand.operand1)
                operand.operand2 = reduce(operand.operand2)

                if (operand.operand1 instanceof BigDecimal && operand.operand2 instanceof BigDecimal) {
                    return operand.operation.getOperator().apply(operand.operand1, operand.operand2)
                }
            }

            return operand
        }

        @Override
        String toString() {
            if (operation == Operation.IDENTITY) return operand1
            return "(" + operand1 + " " + operation.getSymbol() + " " + operand2 + ')';
        }
    }

    enum Operation {
        ADDITION("+", (a, b) -> a + b),
        SUBTRACTION("-", (a, b) -> a - b),
        MULTIPLICATION("*", (a, b) -> a * b),
        DIVISION("/", (a, b) -> a / b),
        EQUALITY("=", null),
        IDENTITY("identity", (a, b) -> a)

        private final String symbol
        private final BinaryOperator operator

        Operation(String symbol, BinaryOperator operator) {
            this.operator = operator
            this.symbol = symbol
        }

        String getSymbol() {
            return symbol
        }

        BinaryOperator getOperator() {
            return operator
        }
    }
}
