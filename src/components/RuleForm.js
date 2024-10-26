import React, { useState } from 'react';
import { evaluateRules, generateAST } from '../services/ruleservices';
import Tree from 'react-d3-tree';  // Import the tree visualization component

const RuleForm = () => {
    const [rules, setRules] = useState([{ id: 1, rule: '' }]);
    const [operator, setOperator] = useState('AND');
    const [userData, setUserData] = useState({ age: '', salary: '' });
    const [result, setResult] = useState(null);
    const [error, setError] = useState(null);
    const [ast, setAst] = useState(null);

    const handleRuleChange = (id, newRule) => {
        setRules(rules.map(rule => rule.id === id ? { ...rule, rule: newRule } : rule));
    };

    const addNewRule = () => {
        setRules([...rules, { id: rules.length + 1, rule: '' }]);
    };

    const validateForm = () => {
        if (userData.age === '' || userData.salary === '' || rules.some(r => r.rule === '')) {
            setError('Please fill in all fields.');
            return false;
        }

        const rulePattern = /^.+ (>|<|=) .+$/;
        for (const rule of rules) {
            if (!rulePattern.test(rule.rule)) {
                setError(`Invalid rule syntax: "${rule.rule}". Use format "field > value".`);
                return false;
            }
        }

        setError(null);
        return true;
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        if (!validateForm()) return;

        const ruleStrings = rules.map(r => r.rule);
        const payload = { rules: ruleStrings, operator, userData };

        try {
            const evaluationResult = await evaluateRules(payload);
            setResult(evaluationResult);

            const generatedAst = await generateAST(ruleStrings, operator, userData);
            setAst(generatedAst);
        } catch (error) {
            console.error('Error submitting form:', error);
        }
    };

    // Transform AST data into a format compatible with react-d3-tree
    const transformASTToTree = (node) => {
        if (!node) return null;
        const treeNode = {
            name: node.value || node.type,
            children: []
        };
        if (node.left) {
            treeNode.children.push(transformASTToTree(node.left));
        }
        if (node.right) {
            treeNode.children.push(transformASTToTree(node.right));
        }
        return treeNode;
    };

    return (
        <div className="container">
            <form onSubmit={handleSubmit}>
                <h2>Rule Evaluation Form</h2>
                {error && <p className="error">{error}</p>}
                {rules.map((ruleItem) => (
                    <div key={ruleItem.id}>
                        <label>Rule {ruleItem.id}:</label>
                        <input
                            type="text"
                            value={ruleItem.rule}
                            onChange={(e) => handleRuleChange(ruleItem.id, e.target.value)}
                            placeholder="e.g., age > 18"
                        />
                    </div>
                ))}
                <button type="button" onClick={addNewRule}>Add New Rule</button>

                <label>Operator:</label>
                <select value={operator} onChange={(e) => setOperator(e.target.value)}>
                    <option value="AND">AND</option>
                    <option value="OR">OR</option>
                </select>

                <label>Age:</label>
                <input
                    type="number"
                    value={userData.age}
                    onChange={(e) => setUserData({ ...userData, age: e.target.value })}
                />

                <label>Salary:</label>
                <input
                    type="number"
                    value={userData.salary}
                    onChange={(e) => setUserData({ ...userData, salary: e.target.value })}
                />

                <button type="submit">Evaluate</button>
                
                {result !== null && (
                    <p>Evaluation Result: {result ? 'True' : 'False'}</p>
                )}
            </form>

            {ast && (
                <div className="ast-container">
                    {/* <h3>Generated AST:</h3> */}
                    <div id="treeWrapper" style={{ width: '50%', height: '300px' }}>
                        <Tree data={transformASTToTree(ast)} orientation="vertical" />
                    </div>
                </div>
            )}
        </div>
    );
};

export default RuleForm;
