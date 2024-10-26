package com.example.ruleengine.RuleEngine.controller;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.ruleengine.RuleEngine.model.ASTNode;
import com.example.ruleengine.RuleEngine.model.Rule;
import com.example.ruleengine.RuleEngine.service.RuleService;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/rules")
public class RuleController {

    @Autowired
    private RuleService ruleService;

    // Endpoint for evaluating the rules
    @PostMapping("/evaluate")
    public ResponseEntity<Boolean> createAndEvaluateRule(@RequestBody Map<String, Object> payload) {
        // Extract the payload details
        List<String> ruleStrings = (List<String>) payload.get("rules");
        String operator = (String) payload.get("operator");
        Map<String, Object> userData = (Map<String, Object>) payload.get("userData");

        if (ruleStrings == null || operator == null || userData == null) {
            return ResponseEntity.badRequest().body(false); // Return a bad request if the payload is incomplete
        }

        // Generate AST nodes from the rules
        List<ASTNode> astNodes = ruleStrings.stream()
                .map(ruleService::createASTFromRule)
                .collect(Collectors.toList());

        // Combine AST nodes into a single AST structure
        ASTNode combinedRule = ruleService.combineMultipleRules(operator, astNodes);

        // Evaluate the AST using the user data
        boolean result = ruleService.evaluateAST(combinedRule, userData);

        // Save the combined rule to the database
        Rule ruleEntity = new Rule();
        ruleEntity.setRuleName("Combined Rule");
        ruleEntity.setConditions(String.join(" " + operator + " ", ruleStrings)); // Store conditions
        ruleEntity.setAction("Evaluated");
        ruleService.createRule(ruleEntity);

        // Return the evaluation result
        return ResponseEntity.ok(result);
    }

    // Endpoint for generating the AST structure
    @PostMapping("/generate-ast")
    public ResponseEntity<ASTNode> generateASTStructure(@RequestBody Map<String, Object> payload) {
        // Extract rules and operator from the payload
        List<String> ruleStrings = (List<String>) payload.get("rules");
        String operator = (String) payload.get("operator");

        // Validate the payload
        if (ruleStrings == null || operator == null) {
            return ResponseEntity.badRequest().build(); // Return a bad request if payload is incomplete
        }

        // Create AST nodes from the rules
        List<ASTNode> astNodes = ruleStrings.stream()
                .map(ruleService::createASTFromRule)
                .collect(Collectors.toList());

        // Combine AST nodes into a single AST structure
        ASTNode combinedAST = ruleService.combineMultipleRules(operator, astNodes);

        // Return the combined AST structure
        return ResponseEntity.ok(combinedAST);
    }
}
