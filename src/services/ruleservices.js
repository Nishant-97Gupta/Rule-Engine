const EVALUATE_API_URL = 'http://localhost:8080/api/rules/evaluate';
const AST_API_URL = 'http://localhost:8080/api/rules/generate-ast';

// Function to evaluate rules
export const evaluateRules = async (payload) => {
    try {
        const response = await fetch(EVALUATE_API_URL, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(payload),
        });
        const data = await response.json();
        return data;
    } catch (error) {
        console.error('Error evaluating rules:', error);
        return null;
    }
};

// Function to fetch AST structure via POST request
export const generateAST = async (rules, operator, userData) => {
    try {
        const payload = {
            rules, // Array of rule strings
            operator, // Operator (AND/OR)
            userData // The user data object (age, salary, etc.)
        };

        // Send POST request with the payload
        const response = await fetch(AST_API_URL, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(payload), // Send payload as a JSON string
        });

        const data = await response.json();
        return data; // Return the fetched AST structure
    } catch (error) {
        console.error('Error fetching AST:', error);
        return null;
    }
};
