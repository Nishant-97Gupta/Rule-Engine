import React from 'react';
import RuleForm from './components/RuleForm';

const App = () => {
    const appStyle = {
        minHeight: '100vh',
        backgroundImage: `url('${process.env.PUBLIC_URL}/background_image.jpg')`,
        backgroundSize: 'cover',
        backgroundPosition: 'center',
        backgroundRepeat: 'no-repeat',
        display: 'flex',
        justifyContent: 'center',
        alignItems: 'center',
        flexDirection: 'column',
        color: 'white',
        textAlign: 'center',
    };

    const formStyle = {
        width: '100%',
        maxWidth: '600px',
        padding: '20px',
        backgroundColor: 'rgba(255, 255, 255, 0.8)',
        borderRadius: '10px',
    };

    return (
        <div style={appStyle}>
            <h1>Rule Engine</h1>
            <div style={formStyle}>
                <RuleForm />
            </div>
        </div>
    );
};

export default App;
