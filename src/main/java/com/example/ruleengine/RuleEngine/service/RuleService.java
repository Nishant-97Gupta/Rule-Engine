package com.example.ruleengine.RuleEngine.service;

import com.example.ruleengine.RuleEngine.model.Rule;
import com.example.ruleengine.RuleEngine.model.ASTNode;
import com.example.ruleengine.RuleEngine.repo.RuleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class RuleService {

    @Autowired
    private RuleRepository ruleRepository;

    // CRUD Operations
    public List<Rule> getAllRules() {
        return ruleRepository.findAll();
    }

    public Optional<Rule> getRuleById(Long id) {
        return ruleRepository.findById(id);
    }

    public Rule createRule(Rule rule) {
        return ruleRepository.save(rule);
    }

    public Rule updateRule(Long id, Rule updatedRule) {
        return ruleRepository.findById(id).map(rule -> {
            rule.setRuleName(updatedRule.getRuleName());
            rule.setConditions(updatedRule.getConditions());
            rule.setAction(updatedRule.getAction());
            return ruleRepository.save(rule);
        }).orElseThrow(() -> new RuntimeException("Rule not found with ID: " + id));
    }

    public void deleteRule(Long id) {
        if (!ruleRepository.existsById(id)) {
            throw new RuntimeException("Rule not found with ID: " + id);
        }
        ruleRepository.deleteById(id);
    }

    // AST Logic Integration

    // Create AST from rule conditions
    public ASTNode createASTFromRule(String ruleString) {
        if (ruleString == null || ruleString.isEmpty()) {
            throw new IllegalArgumentException("Rule string cannot be null or empty");
        }

        if (ruleString.contains("AND")) {
            String[] parts = ruleString.split("AND");
            ASTNode left = new ASTNode("operand", parts[0].trim());
            ASTNode right = new ASTNode("operand", parts[1].trim());
            return new ASTNode("AND", left, right);
        } else if (ruleString.contains("OR")) {
            String[] parts = ruleString.split("OR");
            ASTNode left = new ASTNode("operand", parts[0].trim());
            ASTNode right = new ASTNode("operand", parts[1].trim());
            return new ASTNode("OR", left, right);
        } else {
            return new ASTNode("operand", ruleString.trim());
        }
    }

    // Combine multiple AST nodes using AND/OR operator
    public ASTNode combineMultipleRules(String operator, List<ASTNode> rules) {
        if (operator == null || (!operator.equals("AND") && !operator.equals("OR"))) {
            throw new IllegalArgumentException("Invalid operator. Must be 'AND' or 'OR'");
        }

        if (rules == null || rules.isEmpty()) {
            throw new IllegalArgumentException("No rules to combine");
        }

        ASTNode combinedAST = rules.get(0);

        for (int i = 1; i < rules.size(); i++) {
            combinedAST = new ASTNode(operator, combinedAST, rules.get(i));
        }

        return combinedAST;
    }

    // Evaluate the AST
    public boolean evaluateAST(ASTNode node, Map<String, Object> userData) {
        if (node == null) {
            throw new IllegalArgumentException("AST node cannot be null");
        }

        // Evaluate operand nodes (leaf nodes)
        if ("operand".equals(node.getType())) {
            String[] conditionParts = node.getValue().split(" ");
            if (conditionParts.length < 3) {
                throw new IllegalArgumentException("Invalid condition format in rule");
            }

            String field = conditionParts[0];
            String operator = conditionParts[1];
            String value = conditionParts[2];

            // Handle user-provided data
            Object userValue = userData.get(field);

            if (userValue == null) {
                throw new IllegalArgumentException("No data found for field: " + field);
            }

            int intUserValue;
            if (userValue instanceof String) {
                try {
                    intUserValue = Integer.parseInt((String) userValue);
                } catch (NumberFormatException e) {
                    throw new IllegalArgumentException("Invalid number format for field: " + field);
                }
            } else if (userValue instanceof Integer) {
                intUserValue = (Integer) userValue;
            } else {
                throw new IllegalArgumentException("Unsupported data type for field: " + field);
            }

            // Perform comparison based on the operator
            switch (operator) {
                case ">":
                    return intUserValue > Integer.parseInt(value);
                case "<":
                    return intUserValue < Integer.parseInt(value);
                case "=":
                    return intUserValue == Integer.parseInt(value);
                default:
                    throw new IllegalArgumentException("Unsupported operator: " + operator);
            }
        }

        // Recursive case for AND/OR operations
        if ("AND".equals(node.getType())) {
            return evaluateAST(node.getLeft(), userData) && evaluateAST(node.getRight(), userData);
        } else if ("OR".equals(node.getType())) {
            return evaluateAST(node.getLeft(), userData) || evaluateAST(node.getRight(), userData);
        }

        throw new IllegalArgumentException("Unsupported AST node type: " + node.getType());
    }
}
