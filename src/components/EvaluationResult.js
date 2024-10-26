import React from 'react';

const EvaluationResult = ({ result }) => {
    return (
        <div>
            <h3>Evaluation Result</h3>
            <p>{result ? 'The rule conditions are met!' : 'The rule conditions are not met.'}</p>
        </div>
    );
};

export default EvaluationResult;
