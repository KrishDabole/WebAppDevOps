import React, { useState } from 'react';
import Login from './auth/Login';
import Register from './auth/Register';
import Calculator from './Calculator';

function App(){
  const [token, setToken] = useState(localStorage.getItem('token'));
  return (
    <div style={{maxWidth:900, margin:'20px auto', fontFamily:'Arial, sans-serif'}}>
      <header style={{display:'flex', justifyContent:'space-between', alignItems:'center'}}>
        <h2>Tax Calculator</h2>
        <div>{token ? <button onClick={()=>{ localStorage.removeItem('token'); setToken(null); }}>Logout</button> : null}</div>
      </header>
      {!token ? (
        <div style={{display:'flex',gap:20}}>
          <div style={{flex:1}}>
            <h3>Login</h3>
            <Login onSuccess={(t)=>{ localStorage.setItem('token', t); setToken(t); }} />
          </div>
          <div style={{flex:1}}>
            <h3>Register</h3>
            <Register onSuccess={(t)=>{ localStorage.setItem('token', t); setToken(t); }} />
          </div>
        </div>
      ) : (
        <Calculator token={token} />
      )}
    </div>
  );
}
export default App;
